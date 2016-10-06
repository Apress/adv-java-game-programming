     package com.croftsoft.apps.backpropxor;

     import java.awt.Choice;
     import java.awt.Color;
     import java.awt.Font;
     import java.awt.Graphics;
     import java.awt.Rectangle;
     import java.awt.event.*;
     import java.text.DecimalFormat;
     import javax.swing.*;

     import com.croftsoft.core.gui.ShutdownWindowListener;
     import com.croftsoft.core.gui.FrameLib;
     import com.croftsoft.core.gui.plot.PlotLib;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.math.*;

     /*********************************************************************
     * A Backpropagation neural network learning algorithm demonstration.
     *
     * @version
     *   2002-02-28
     * @since
     *   1996
     * @author
     *   <a href="http://www.alumni.caltech.edu/~croft/">David W. Croft</a>
     *********************************************************************/

     public class  BackpropXor
       extends JPanel
       implements ActionListener, ItemListener, Lifecycle, Runnable,
         BackpropXorConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private Matrix  l0_activations      = new Matrix ( 3, 1 );
     private Matrix  l1_activations      = new Matrix ( 2, 1 );
     private Matrix  l2_inputs           = new Matrix ( 3, 1 );
     private Matrix  l2_activations      = new Matrix ( 1, 1 );
     private Matrix  l1_weights          = new Matrix ( 3, 2 );
     private Matrix  l2_weights          = new Matrix ( 3, 1 );
     private Matrix  l1_weighted_sums    = new Matrix ( 2, 1 );
     private Matrix  l2_weighted_sums    = new Matrix ( 1, 1 );
     private double  output_desired      = 0.0;
     private double  output_error        = 0.0;
     private Matrix  l2_local_gradient   = new Matrix ( 1, 1 );
     private double  learning_rate       = 0.1;
     private double  momentum_constant   = 0.9;
     private Matrix  l2_weights_delta    = new Matrix ( 3, 1 );
     private Matrix  l2_weights_momentum = new Matrix ( 3, 1 );
     private Matrix  sum_weighted_deltas = new Matrix ( 2, 1 );
     private Matrix  l1_local_gradients  = new Matrix ( 2, 1 );
     private Matrix  l1_weights_delta    = new Matrix ( 3, 2 );
     private Matrix  l1_weights_momentum = new Matrix ( 3, 2 );

     //

     private long        total_samples = 0;

     private int         iteration = 0;
     private double [ ]  squared_errors = new double [ ITERATIONS_PER_EPOCH ];
     private int         epoch = 0;
     private int         epochs_max = 1000;
     private double [ ]  epoch_rms_error = new double [ epochs_max ];

     private double [ ] [ ]  samples = new double [ ITERATIONS_PER_EPOCH ] [ 3 ];

     //

     private Rectangle  r;
     private Rectangle  rs;

     //

     private Thread   runner;

     private boolean  pleaseStop = false;

     private boolean  isPaused;

     //

     private JComboBox   functionComboBox;

     private JTextField  learning_rate_TextField;

     private JLabel      learning_rate_Label;

     private JTextField  momentum_TextField;

     private JLabel      momentum_Label;

     private JButton     randomize_Button;

     private JButton     reset_Button;

     private JButton     pause_Button;

     //

     private int     function_selected;

     private String  function_String;

     private int  y2;

     private int  y4;

     private DecimalFormat  decimalFormat
       = new DecimalFormat ( "0.000000" );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       JFrame  jFrame = new JFrame ( TITLE );

/*
       try
       {
         jFrame.setIconImage ( ClassLib.getResourceAsImage (
           BackpropXor.class, FRAME_ICON_FILENAME ) );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
*/

       BackpropXor  backpropXor = new BackpropXor ( );

       jFrame.setContentPane ( backpropXor );

       FrameLib.launchJFrameAsDesktopApp (
         jFrame,
         new Lifecycle [ ] { backpropXor },
         FRAME_SIZE,
         SHUTDOWN_CONFIRMATION_PROMPT );
     }

     //////////////////////////////////////////////////////////////////////
     // interface Lifecycle methods
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       setFont ( new Font ( "Times New Roman", Font.PLAIN, FONT_SIZE ) );

       function_selected = INITIAL_FUNCTION;

       function_String = FUNCTION_NAMES [ function_selected ];

       functionComboBox = new JComboBox ( FUNCTION_NAMES );

       functionComboBox.setSelectedIndex ( function_selected );

       learning_rate_TextField = new JTextField ( "" + learning_rate, 4 );

       learning_rate_Label = new JLabel ( "Learning Rate" );

       momentum_TextField = new JTextField ( "" + momentum_constant, 4 );

       momentum_Label = new JLabel ( "Momentum Factor" );

       add ( functionComboBox );
       add ( momentum_Label );
       add ( momentum_TextField );
       add ( learning_rate_Label );
       add ( learning_rate_TextField );

       JPanel  buttonPanel = new JPanel ( );

       randomize_Button = new JButton ( "Randomize Weights" );

       reset_Button = new JButton ( "Reset Strip" );

       pause_Button = new JButton ( "  Pause  " );

       buttonPanel.add ( randomize_Button );
       
       buttonPanel.add ( pause_Button );
       
       buttonPanel.add ( reset_Button );

       add ( buttonPanel );

       functionComboBox       .addItemListener   ( this );

       momentum_TextField     .addActionListener ( this );

       learning_rate_TextField.addActionListener ( this );

       randomize_Button       .addActionListener ( this );

       pause_Button           .addActionListener ( this );

       reset_Button           .addActionListener ( this );

       r = new Rectangle (
         10, 10 + Y, SIZE.width - 100, SIZE.height - 350 );

       y2 = 30 + Y + r.height;

       y4 = y2 + YTAB * 8;

       rs = new Rectangle (
         10 + r.width + 10, 10 + Y,
         SIZE.height - 350, SIZE.height - 350 );

       randomize_weights ( );
     }

     public void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( ( runner == null ) && !isPaused )
       {
         pleaseStop = false;

         runner = new Thread ( this );

         runner.setPriority ( runner.getPriority ( ) - 1 );

         runner.setDaemon ( true );

         runner.start ( );
       }
     }

     public void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       pleaseStop = true;
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       stop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  itemStateChanged ( ItemEvent  itemEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = itemEvent.getSource ( );

       if ( source == functionComboBox )
       {
         function_selected = functionComboBox.getSelectedIndex ( );

         function_String = ( String ) functionComboBox.getSelectedItem ( );
       }
     }

     public void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = actionEvent.getSource ( );

       if ( source == momentum_TextField )
       {
         momentum_constant = Double.valueOf (
           momentum_TextField.getText ( ) ).doubleValue ( );
       }
       else if ( source == learning_rate_TextField )
       {
         learning_rate = Double.valueOf (
           learning_rate_TextField.getText ( ) ).doubleValue ( );
       }
       else if ( source == randomize_Button )
       {
         randomize_weights ( );
       }
       else if ( source == reset_Button )
       {
         total_samples = 0;

         iteration = 0;

         epoch = 0;
       }
       else if ( source == pause_Button )
       {
         if ( isPaused )
         {
           isPaused = false;

           pause_Button.setText ( " Pause " );

           start ( );           
         }
         else
         {
           isPaused = true;

           pause_Button.setText ( "Continue" );

           stop ( );
         }
       }
     }

     public void  paintComponent ( Graphics g )
     //////////////////////////////////////////////////////////////////////
     {
       super.paintComponent ( g );

       plot_epochs ( r, g, epoch_rms_error );

       plotGraph  ( g, samples );

       g.drawString ( "Inputs", 10 + 0 * XTAB, 10 + 0 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l0_activations.data    [ 0 ] [ 0 ] ), 10 + 0 * XTAB, 10 + 1 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l0_activations.data    [ 1 ] [ 0 ] ), 10 + 0 * XTAB, 10 + 2 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l0_activations.data    [ 2 ] [ 0 ] ), 10 + 0 * XTAB, 10 + 3 * YTAB + y2 );

       g.drawString ( "Weights", 10 + 1 * XTAB, 10 + 0 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l1_weights.data        [ 0 ] [ 0 ] ), 10 + 1 * XTAB, 10 + 1 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l1_weights.data        [ 1 ] [ 0 ] ), 10 + 1 * XTAB, 10 + 2 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l1_weights.data        [ 2 ] [ 0 ] ), 10 + 1 * XTAB, 10 + 3 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l1_weights.data        [ 0 ] [ 1 ] ), 10 + 1 * XTAB, 10 + 4 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l1_weights.data        [ 1 ] [ 1 ] ), 10 + 1 * XTAB, 10 + 5 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l1_weights.data        [ 2 ] [ 1 ] ), 10 + 1 * XTAB, 10 + 6 * YTAB + y2 );

       g.drawString ( "Weighted Sums", 10 + 2 * XTAB, 10 + 0 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l1_weighted_sums.data  [ 0 ] [ 0 ] ), 10 + 2 * XTAB, 10 + 1 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l1_weighted_sums.data  [ 1 ] [ 0 ] ), 10 + 2 * XTAB, 10 + 2 * YTAB + y2 );
                                                                                     
       g.drawString ( "Hidden", 10 + 3 * XTAB, 10 + 0 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l2_inputs.data         [ 0 ] [ 0 ] ), 10 + 3 * XTAB, 10 + 1 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l2_inputs.data         [ 1 ] [ 0 ] ), 10 + 3 * XTAB, 10 + 2 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l2_inputs.data         [ 2 ] [ 0 ] ), 10 + 3 * XTAB, 10 + 3 * YTAB + y2 );

       g.drawString ( "Weights", 10 + 4 * XTAB, 10 + 0 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l2_weights.data        [ 0 ] [ 0 ] ), 10 + 4 * XTAB, 10 + 1 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l2_weights.data        [ 1 ] [ 0 ] ), 10 + 4 * XTAB, 10 + 2 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l2_weights.data        [ 2 ] [ 0 ] ), 10 + 4 * XTAB, 10 + 3 * YTAB + y2 );

       g.drawString ( "Weighted Sum", 10 + 5 * XTAB, 10 + 0 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l2_weighted_sums.data  [ 0 ] [ 0 ] ), 10 + 5 * XTAB, 10 + 1 * YTAB + y2 );

       g.drawString ( "Output", 10 + 6 * XTAB, 10 + 0 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( l2_activations.data    [ 0 ] [ 0 ] ), 10 + 6 * XTAB, 10 + 1 * YTAB + y2 );

       g.drawString ( "Output Desired", 10 + 6 * XTAB, 10 + 2 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( output_desired ), 10 + 6 * XTAB, 10 + 3 * YTAB + y2 );

       g.drawString ( "Output Error", 10 + 6 * XTAB, 10 + 4 * YTAB + y2 );
       g.drawString ( decimalFormat.format ( output_error ), 10 + 6 * XTAB, 10 + 5 * YTAB + y2 );

       g.drawString ( "Iterations", 10 + 7 * XTAB,  10 + 0 * YTAB + y2 );
       g.drawString ( "" + total_samples, 10 + 7 * XTAB,  10 + 1 * YTAB + y2 );

       g.drawString ( "Function", 10 + 7 * XTAB,  10 + 2 * YTAB + y2 );
       g.drawString ( function_String, 10 + 7 * XTAB,  10 + 3 * YTAB + y2 );

       g.drawString ( "L2 Gradient", 10 + 0 * XTAB, 0 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l2_local_gradient.data [ 0 ] [ 0 ] ), 10 + 0 * XTAB, 1 * YTAB + y4 );

       g.drawString ( "W2 Deltas", 10 + 1 * XTAB, 0 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l2_weights_delta.data  [ 0 ] [ 0 ] ), 10 + 1 * XTAB, 1 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l2_weights_delta.data  [ 1 ] [ 0 ] ), 10 + 1 * XTAB, 2 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l2_weights_delta.data  [ 2 ] [ 0 ] ), 10 + 1 * XTAB, 3 * YTAB + y4 );

       g.drawString ( "W2 Momentum", 10 + 2 * XTAB, 0 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l2_weights_momentum.data [ 0 ] [ 0 ] ), 10 + 2 * XTAB, 1 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l2_weights_momentum.data [ 1 ] [ 0 ] ), 10 + 2 * XTAB, 2 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l2_weights_momentum.data [ 2 ] [ 0 ] ), 10 + 2 * XTAB, 3 * YTAB + y4 );

       g.drawString ( "Sum W Deltas", 10 + 3 * XTAB, 0 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( sum_weighted_deltas.data [ 0 ] [ 0 ] ), 10 + 3 * XTAB, 1 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( sum_weighted_deltas.data [ 1 ] [ 0 ] ), 10 + 3 * XTAB, 2 * YTAB + y4 );

       g.drawString ( "L1 Gradients", 10 + 4 * XTAB, 0 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l1_local_gradients.data [ 0 ] [ 0 ] ), 10 + 4 * XTAB, 1 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l1_local_gradients.data [ 1 ] [ 0 ] ), 10 + 4 * XTAB, 2 * YTAB + y4 );

       g.drawString ( "W1 Deltas", 10 + 5 * XTAB, 0 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l1_weights_delta.data  [ 0 ] [ 0 ] ), 10 + 5 * XTAB, 1 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l1_weights_delta.data  [ 1 ] [ 0 ] ), 10 + 5 * XTAB, 2 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l1_weights_delta.data  [ 2 ] [ 0 ] ), 10 + 5 * XTAB, 3 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l1_weights_delta.data  [ 0 ] [ 1 ] ), 10 + 5 * XTAB, 4 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l1_weights_delta.data  [ 1 ] [ 1 ] ), 10 + 5 * XTAB, 5 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l1_weights_delta.data  [ 2 ] [ 1 ] ), 10 + 5 * XTAB, 6 * YTAB + y4 );

       g.drawString ( "W1 Momentum", 10 + 6 * XTAB, 0 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l1_weights_momentum.data [ 0 ] [ 0 ] ), 10 + 6 * XTAB, 1 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l1_weights_momentum.data [ 1 ] [ 0 ] ), 10 + 6 * XTAB, 2 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l1_weights_momentum.data [ 2 ] [ 0 ] ), 10 + 6 * XTAB, 3 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l1_weights_momentum.data [ 0 ] [ 1 ] ), 10 + 6 * XTAB, 4 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l1_weights_momentum.data [ 1 ] [ 1 ] ), 10 + 6 * XTAB, 5 * YTAB + y4 );
       g.drawString ( decimalFormat.format ( l1_weights_momentum.data [ 2 ] [ 1 ] ), 10 + 6 * XTAB, 6 * YTAB + y4 );
     }

     public void  run ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {

       long  lastRepaintTime = 0;

       while ( !pleaseStop )
       {
         if ( iteration == 0 )
         {
           long  currentTime = System.currentTimeMillis ( );

           if ( currentTime >= lastRepaintTime + REPAINT_PERIOD )
           {
             lastRepaintTime = currentTime;

             repaint ( );
           }
         }

         l2_weights = l2_weights.add ( l2_weights_delta );

//         l2_weights = l2_weights.clip ( -1.0, 1.0 );

         l1_weights = l1_weights.add ( l1_weights_delta );

//         l1_weights = l1_weights.clip ( -1.0, 1.0 );

         l0_activations = l0_activations.randomizeUniform ( 0.0, 1.0 );

         l0_activations.data [ 0 ] [ 0 ] = 1.0;

         samples [ iteration ] [ 0 ] = l0_activations.data [ 1 ] [ 0 ];

         samples [ iteration ] [ 1 ] = l0_activations.data [ 2 ] [ 0 ];

         l1_weighted_sums = Matrix.multiply (
           l1_weights.transpose ( ), l0_activations );

         l1_activations = l1_weighted_sums.sigmoid ( );

         l2_inputs.data [ 0 ] [ 0 ] = 1.0;

         l2_inputs.data [ 1 ] [ 0 ] = l1_activations.data [ 0 ] [ 0 ];

         l2_inputs.data [ 2 ] [ 0 ] = l1_activations.data [ 1 ] [ 0 ];

         l2_weighted_sums = Matrix.multiply (
           l2_weights.transpose ( ), l2_inputs );

         l2_activations = l2_weighted_sums.sigmoid ( );

         samples [ iteration ] [ 2 ] = l2_activations.data [ 0 ] [ 0 ];

         output_desired = target_function ( l0_activations );

         output_error = output_desired - l2_activations.data [ 0 ] [ 0 ];

         l2_local_gradient = l2_weighted_sums.sigmoidDerivative ( );

         l2_local_gradient = l2_local_gradient.multiply ( output_error );

// I'm not sure about the transpose below here.

         l2_weights_delta
           = Matrix.multiply ( l2_inputs, l2_local_gradient.transpose ( ) );

         l2_weights_delta = l2_weights_delta.multiply ( learning_rate );

         l2_weights_delta = l2_weights_delta.add ( l2_weights_momentum );

         l2_weights_momentum
           = l2_weights_delta.multiply ( momentum_constant );

// needs transpose or sum below?

         sum_weighted_deltas = l2_weights.submatrix ( 1, 2, 0, 0 );

         sum_weighted_deltas = Matrix.multiply (
           sum_weighted_deltas, l2_local_gradient );

         l1_local_gradients = new Matrix (
           l1_activations.rows, l1_activations.cols, 1.0 );

         l1_local_gradients = l1_local_gradients.subtract ( l1_activations );

         l1_local_gradients = Matrix.multiplyPairwise (
           l1_activations, l1_local_gradients );

         l1_local_gradients = Matrix.multiplyPairwise (
           l1_local_gradients, sum_weighted_deltas );

         l1_weights_delta = Matrix.multiply (
           l0_activations, l1_local_gradients.transpose ( ) );

         l1_weights_delta = l1_weights_delta.multiply ( learning_rate );

         l1_weights_delta = l1_weights_delta.add ( l1_weights_momentum );

         l1_weights_momentum
           = l1_weights_delta.multiply ( momentum_constant );

         squared_errors [ iteration ] = output_error * output_error;

         iteration++;

         total_samples++;

         if ( iteration % ITERATIONS_PER_EPOCH == 0 )
         {
           epoch++;

           if ( epoch >= epochs_max ) epoch = 1;

           iteration = 0;

           epoch_rms_error [ epoch - 1 ] = 0.0;

           for ( int index_iteration = 0;
                     index_iteration < ITERATIONS_PER_EPOCH;
                     index_iteration++ )
           {
             epoch_rms_error [ epoch - 1 ]
               += squared_errors [ index_iteration ];
           }

           epoch_rms_error [ epoch - 1 ] /= ( double ) ITERATIONS_PER_EPOCH;

           epoch_rms_error [ epoch - 1 ]
             = Math.sqrt ( epoch_rms_error [ epoch - 1 ] );
         }

         Thread.sleep ( 0 );
       }

       repaint ( );

       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
       finally
       {
         runner = null;
       }
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private void  plot_epochs (
       Rectangle  r, Graphics  g, double [ ]  epochs )
     //////////////////////////////////////////////////////////////////////
     {
       g.setColor ( java.awt.Color.black );
       g.fillRect ( r.x, r.y, r.width, r.height );
       g.setColor ( java.awt.Color.white );
       g.drawRect ( r.x, r.y, r.width, r.height / 2 );
//       g.clipRect ( r.x, r.y, r.width, r.height );
       for ( int index_epoch = 1;
                 index_epoch <= epoch;
                 index_epoch++ )
       {
         PlotLib.xy ( java.awt.Color.red,
           ( double ) index_epoch, epochs [ index_epoch - 1 ],
           r, g, 1.0, ( double ) epoch, 0.0, 1.0, OVAL_SIZE, true );
       }
//       g.clipRect ( 0, 0, SIZE.width, SIZE.height );
       g.setColor ( java.awt.Color.white );
       g.drawRect ( r.x, r.y, r.width, r.height );
       g.setColor ( this.getForeground ( ) );
     }

     private void  plotGraph ( Graphics  g, double [ ] [ ]  samples )
     //////////////////////////////////////////////////////////////////////
     {
       g.setColor ( Color.black );

       g.fillRect ( rs.x, rs.y, rs.width, rs.height );

       for ( int index_iteration = 0;
         index_iteration < ITERATIONS_PER_EPOCH;
         index_iteration++ )
       {
         PlotLib.xy (
           samples [ index_iteration ] [ 2 ] >= 0.5
             ? Color.green : Color.red,
           samples [ index_iteration ] [ 0 ],
           samples [ index_iteration ] [ 1 ],
           rs, g, 0.0, 1.0, 0.0, 1.0, OVAL_SIZE );
       }

       g.setColor ( Color.white );

       g.drawRect ( rs.x, rs.y, rs.width, rs.height );

       g.setColor ( this.getForeground ( ) );
     }

     private void  randomize_weights ( )
     //////////////////////////////////////////////////////////////////////
     {
       l1_weights = l1_weights.randomizeUniform ( -1.0, 1.0 );

       l2_weights = l2_weights.randomizeUniform ( -1.0, 1.0 );
     }

     private double  target_function ( Matrix  inputs )
     //////////////////////////////////////////////////////////////////////
     {
       long  a = Math.round ( inputs.data [ 1 ] [ 0 ] );

       long  b = Math.round ( inputs.data [ 2 ] [ 0 ] );

       long  bit_num = 2 * b + a;

       long  mask = 1 << bit_num;

       long  masked = function_selected & mask;

       return ( masked == mask ) ? 1.0 : 0.0;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
