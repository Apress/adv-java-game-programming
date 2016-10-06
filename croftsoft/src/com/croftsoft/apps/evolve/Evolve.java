     package com.croftsoft.apps.evolve;
     
     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import java.util.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.awt.font.FontLib;
     import com.croftsoft.core.gui.FrameLib;
     import com.croftsoft.core.gui.plot.PlotLib;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.animation.*;
     import com.croftsoft.core.animation.component.*;
     import com.croftsoft.core.util.loop.*;

     /*********************************************************************
     * Main Evolve class.
     *
     * @version
     *   2003-07-23
     * @since
     *   1996-09-01
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Evolve
       extends JApplet
       implements
         ActionListener, ChangeListener, ComponentListener, MouseListener,
         Lifecycle, ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  VERSION = "2002-03-13";

     private static final String  TITLE = "CroftSoft Evolve";

     private static final String  INFO
       = "\n" + TITLE + "\n"
       + "Copyright 2002 CroftSoft Inc\n"
       + "http://www.croftsoft.com/\n"
       + "Version " + VERSION + "\n";

     private static final double  FRAME_RATE = 1.0;

     private static final Dimension  FRAME_SIZE  = null;

     private static final String  FRAME_ICON_FILENAME = "/images/david.png";

     private static final String  SHUTDOWN_CONFIRMATION_PROMPT
       = "Close " + TITLE + "?";

     private static final String  FONT_NAME = "TimesRoman";

     private static final int     FONT_STYLE = Font.BOLD;

     //

     private static final int  SPACE_WIDTH       = 100;

     private static final int  SPACE_HEIGHT      = 100;

     private static final int  BUGS_MAX          = 10000;

     private static final int  GENES_MAX         = 8;

     private static final int  BABY_ENERGY       = 10;

     private static final int  BIRTH_ENERGY      = 30;

     private static final int  BIRTH_ENERGY_COST = 20;

     private static final int  FLORA_ENERGY      = 20;

     private static final int  MAX_ENERGY        = 60;

     private static final int  MOVE_COST         = 1;

     private static final int  EDEN_WIDTH        = 2;

     private static final int  EDEN_HEIGHT       = 2;

     private static final int  EDEN_X0           = ( SPACE_WIDTH  - EDEN_WIDTH  ) / 2;

     private static final int  EDEN_Y0           = ( SPACE_HEIGHT - EDEN_HEIGHT ) / 2;

     private static final int  EDEN_X1           = EDEN_X0 + EDEN_WIDTH  - 1;

     private static final int  EDEN_Y1           = EDEN_Y0 + EDEN_HEIGHT - 1;

     private static final int  MIN_GROWTH_RATE   = 0;

     private static final int  MAX_GROWTH_RATE   = SPACE_WIDTH * SPACE_HEIGHT;

     private static final int  SPINNER_STEP_SIZE = 1;

     private static final int  TEXT_MARGIN       = 10;

     private static final int  INIT_GROWTH_RATE  = 10;

     //

     private static final Color  CRUISER_COLOR = Color.RED;

     private static final Color  NORMAL_COLOR  = Color.MAGENTA;

     private static final Color  TWIRLER_COLOR = Color.BLUE;

     //

     private Random           random;

     private Bug [ ]          bugs;

     private int              flora_growth_rate = INIT_GROWTH_RATE;

     private int              bugs_alive;

     private int              time = 0;

     private boolean [ ] [ ]  flora_present;

     //

     private Rectangle           bounds;

     private AnimatedComponent   animatedComponent;

     //

     private JButton             resetButton;

     private JButton             droughtButton;

     private JCheckBox           edenCheckBox;

     private SpinnerNumberModel  growthRateSpinnerNumberModel;

     //////////////////////////////////////////////////////////////////////
     // static methods
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( INFO );

       JFrame  jFrame = new JFrame ( TITLE );

       try
       {
         FrameLib.setIconImage ( jFrame, FRAME_ICON_FILENAME );
       }
       catch ( IOException  ex )
       {
       }

       Evolve  evolve = new Evolve ( );

       jFrame.setContentPane ( evolve );

       FrameLib.launchJFrameAsDesktopApp (
         jFrame,
         new Lifecycle [ ] { evolve },
         FRAME_SIZE,
         SHUTDOWN_CONFIRMATION_PROMPT );
     }

     //////////////////////////////////////////////////////////////////////
     // overridden Applet methods
     //////////////////////////////////////////////////////////////////////

     public String  getAppletInfo ( ) { return INFO; }

     //////////////////////////////////////////////////////////////////////
     // interface Lifecycle methods
     //////////////////////////////////////////////////////////////////////

     public synchronized void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       Container  contentPane = getContentPane ( );

       contentPane.setLayout ( new BorderLayout ( ) );

       //

       bounds = new Rectangle ( );

       animatedComponent
         = new BufferedAnimatedComponent ( this, FRAME_RATE );

       animatedComponent.setLoopGovernor (
         new FixedDelayLoopGovernor ( FRAME_RATE ) );

       animatedComponent.addComponentListener ( this );

       animatedComponent.addMouseListener ( this );

       animatedComponent.init ( );

       contentPane.add ( animatedComponent, BorderLayout.CENTER );

       //

       JPanel  southPanel = new JPanel ( );

       contentPane.add ( southPanel, BorderLayout.SOUTH );

       //

       resetButton = new JButton ( "Reset" );

       resetButton.addActionListener ( this );

       southPanel.add ( resetButton );

       //

       droughtButton = new JButton ( "Blight" );

       droughtButton.addActionListener ( this );

       southPanel.add ( droughtButton );

       //

       edenCheckBox = new JCheckBox ( "Eden", null, true );

       southPanel.add ( edenCheckBox );

       //

       southPanel.add (
         new JLabel ( "Food Growth Rate", SwingConstants.RIGHT ) );

       growthRateSpinnerNumberModel = new SpinnerNumberModel (
         flora_growth_rate,
         MIN_GROWTH_RATE,
         MAX_GROWTH_RATE,
         SPINNER_STEP_SIZE );

       growthRateSpinnerNumberModel.addChangeListener ( this );

       southPanel.add ( new JSpinner ( growthRateSpinnerNumberModel ) );

       //

       random = new Random ( );

       bugs = new Bug [ BUGS_MAX ];

       flora_present = new boolean [ SPACE_WIDTH ] [ SPACE_HEIGHT ];

       reset ( );
     }

     public void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       animatedComponent.start ( );
     }

     public synchronized void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       animatedComponent.stop ( );
     }

     public synchronized void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       animatedComponent.destroy ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       moveBugs ( );

       growFlora ( );

       component.repaint ( );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  g )
     //////////////////////////////////////////////////////////////////////
     {
       g.setColor ( Color.BLACK );

       g.fillRect ( 0, 0, bounds.width, bounds.height );

       plotFlora ( g );

       plotBugs ( g );

       g.setColor ( Color.WHITE );

       g.drawString (
         createStatusString ( bugs_alive, time, genesAverageString ( ) ),
        bounds.x + TEXT_MARGIN,
        bounds.y + bounds.height - TEXT_MARGIN );
     }

     //////////////////////////////////////////////////////////////////////
     // Listener interface methods
     //////////////////////////////////////////////////////////////////////

     public void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = actionEvent.getSource ( );

       if ( source == resetButton )
       {
         reset ( );
       }
       else if ( source == droughtButton )
       {
         setAllFloraPresent ( false );
       }
     }

     public void  componentResized ( ComponentEvent  componentEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = componentEvent.getSource ( );

       if ( source == animatedComponent )
       {
         animatedComponent.getBounds ( bounds );

         FontLib.setMaxFont (
           animatedComponent,
           createStatusString (
             BUGS_MAX, GENES_MAX - 1, genesAverageString ( ) ),
           FONT_NAME,
           FONT_STYLE,
           bounds.width - 2 * TEXT_MARGIN,
           bounds.height );
       }
     }

     public void  mousePressed ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Point  bugLocation = PlotLib.graphics_to_plot_transform (
         mouseEvent.getPoint ( ), bounds, animatedComponent.getGraphics ( ),
         0, SPACE_WIDTH, 0, SPACE_HEIGHT );

       createNewBug ( bugLocation.x, bugLocation.y );
     }

     public void  stateChanged ( ChangeEvent  changeEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = changeEvent.getSource ( );

       if ( source == growthRateSpinnerNumberModel )
       {
         flora_growth_rate = ( ( Integer )
           growthRateSpinnerNumberModel.getValue ( ) ).intValue ( );
       }
     }

     public void  componentHidden ( ComponentEvent  componentEvent ) { }

     public void  componentMoved  ( ComponentEvent  componentEvent ) { }

     public void  componentShown  ( ComponentEvent  componentEvent ) { }

     public void  mouseClicked  ( MouseEvent  mouseEvent ) { }

     public void  mouseEntered  ( MouseEvent  mouseEvent ) { }

     public void  mouseExited   ( MouseEvent  mouseEvent ) { }

     public void  mouseReleased ( MouseEvent  mouseEvent ) { }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private void  reset ( )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < BUGS_MAX; i++ )
       {
         createNewBug ( SPACE_WIDTH / 2, SPACE_HEIGHT / 2, i );
       }
      
       setAllFloraPresent ( true );

       edenCheckBox.setSelected ( true );

       growthRateSpinnerNumberModel.setValue (
         new Integer ( INIT_GROWTH_RATE ) );
     }

     private void  createNewBug ( int  x, int  y )
     //////////////////////////////////////////////////////////////////////
     {
       int  i = indexOfFirstDeadBug ( );

       if ( i > -1 )
       {
         createNewBug ( x, y, i );
       }
     }

     private void  createNewBug ( int  x, int  y, int  index )
     //////////////////////////////////////////////////////////////////////
     {
       Bug  bug = bugs [ index ];

       if ( bug == null )
       {
         bug = new Bug ( );

         bug.genesX = new boolean [ GENES_MAX ];

         bug.genesY = new boolean [ GENES_MAX ];

         bugs [ index ] = bug;
       }

       bug.x = x;

       bug.y = y;

       bug.energy = BABY_ENERGY;

       for ( int  j = 0; j < GENES_MAX; j++ )
       {
         bug.genesX [ j ] = random.nextBoolean ( );

         bug.genesY [ j ] = random.nextBoolean ( );
       }

       setBugColor ( bug );
     }

     private void  setBugColor ( Bug  bug )
     //////////////////////////////////////////////////////////////////////
     {
       int  xcount = 0;

       int  ycount = 0;

       for ( int  i = 0; i < GENES_MAX; i++ )
       {
         if ( bug.genesX [ i ] )
         {
           xcount++;
         }

         if ( bug.genesY [ i ] )
         {
           ycount++;
         }
       }

       Color  color = NORMAL_COLOR;

       if ( ( xcount == GENES_MAX / 2 )
         && ( ycount == GENES_MAX / 2 ) )
       {
         color = TWIRLER_COLOR;
       }
       else if (
            ( xcount == 0 )
         || ( xcount == GENES_MAX )
         || ( ycount == 0 )
         || ( ycount == GENES_MAX ) )
       {
         color = CRUISER_COLOR;
       }

       bug.color = color;
     }

     private static String  createStatusString (
       int     bugs_alive,
       int     time,
       String  genesAverageString )
     //////////////////////////////////////////////////////////////////////
     {
       return "Alive:  " + bugs_alive
         + "    Time:  " + time + "    "
         + "Average Movement Genes  " + genesAverageString;
     }

     private void  giveBirth ( Bug  parentBug )
     //////////////////////////////////////////////////////////////////////
     {
       int  index = indexOfFirstDeadBug ( );

       if ( index < 0 )
       {
         return;
       }

       parentBug.energy -= BIRTH_ENERGY_COST;

       Bug  babyBug = bugs [ index ];

       babyBug.energy = BABY_ENERGY;

       babyBug.x = parentBug.x;

       babyBug.y = parentBug.y;

       for ( int  i = 0; i < GENES_MAX; i++ )
       {
         babyBug.genesX [ i ] = parentBug.genesX [ i ];

         babyBug.genesY [ i ] = parentBug.genesY [ i ];
       }

       int  mutantGene = random.nextInt ( GENES_MAX );

       if ( random.nextBoolean ( ) )
       {
         babyBug.genesX [ mutantGene ] = !parentBug.genesX [ mutantGene ];
       }
       else
       {
         babyBug.genesY [ mutantGene ] = !parentBug.genesY [ mutantGene ];
       }

       setBugColor ( babyBug );
     }

     private int  indexOfFirstDeadBug ( )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < bugs.length; i++ )
       {
         if ( bugs [ i ].energy <= 0 )
         {
           return i;
         }
       }

       return -1;
     }

     private void  moveBugs ( )
     //////////////////////////////////////////////////////////////////////
     {
       time++;

       if ( time >= GENES_MAX )
       {
         time = 0;
       }

       for ( int  i = 0; i < bugs.length; i++ )
       {
         Bug  bug = bugs [ i ];

         int  x = bug.x;

         int  y = bug.y;

         if ( bug.energy > 0 )
         {
           if ( flora_present [ x ] [ y ] )
           {
             flora_present [ x ] [ y ] = false;

             bug.energy += FLORA_ENERGY;

             if ( bug.energy > MAX_ENERGY )
             {
               bug.energy = MAX_ENERGY;
             }
           }

           if ( bug.energy >= BIRTH_ENERGY )
           {
             giveBirth ( bug );
           }

           if ( random.nextBoolean ( ) )
           {
             if ( bug.genesX [ time ] ) 
             {
               x++;
             }
             else
             {
               x--;
             }

             if ( x < 0 )
             {
               x = SPACE_WIDTH - 1;
             }
             else if ( x >= SPACE_WIDTH )
             {
               x = 0;
             }

             bug.x = x;
           }

           if ( random.nextBoolean ( ) )
           {
             if ( bug.genesY [ time ] ) 
             {
               y++;
             }
             else
             {
               y--;
             }

             if ( y < 0 )
             {
               y = SPACE_HEIGHT - 1;
             }
             else if ( y >= SPACE_HEIGHT )
             {
               y = 0;
             }

             bug.y = y;
           }

           bug.energy -= MOVE_COST;
         }
       }
     }

     private void  growFlora ( )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < flora_growth_rate; i++ )
       {
         // randomly position food flora

         int  x = random.nextInt ( SPACE_WIDTH  );

         int  y = random.nextInt ( SPACE_HEIGHT );

         flora_present [ x ] [ y ] = true;
       }

       // Replenishing the Garden of Eden.

       if ( edenCheckBox.isSelected ( ) )
       {
         for ( int  x = EDEN_X0; x <= EDEN_X1; x++ )
         {
           for ( int  y = EDEN_Y0; y <= EDEN_Y1; y++ )
           {
             flora_present [ x ] [ y ] = true;
           }
         }
       }
     }

     private void  setAllFloraPresent ( boolean  floraPresent )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  x = 0; x < SPACE_WIDTH; x++ )
       {
         for ( int  y = 0; y < SPACE_HEIGHT; y++ )
         {
           flora_present [ x ] [ y ] = floraPresent;
         }
       }
     }

     private String  genesAverageString ( )
     //////////////////////////////////////////////////////////////////////
     {
       long    x_sum, y_sum;

       String  gene_x_String = "X:  ";

       String  gene_y_String = "Y:  ";

       bugs_alive = 0;

       for ( int i = 0; i < bugs.length; i++ )
       {
         if ( bugs [ i ].energy > 0 )
         {
           bugs_alive++;
         }
       }

       for ( int  i = 0; i < GENES_MAX; i++ )
       {
         x_sum = 0;
         
         y_sum = 0;

         for ( int  j = 0; j < bugs.length; j++ )
         {
           Bug  bug = bugs [ j ];

           if ( bug.energy > 0 )
           {
             if ( bug.genesX [ i ] )
             {
               x_sum++;
             }

             if ( bug.genesY [ i ] )
             {
               y_sum++;
             }
           }
         }

         if ( ( double ) x_sum / ( double ) bugs_alive >= 0.5 )
         {
           gene_x_String += "1";
         }
         else
         {
           gene_x_String += "0";
         }

         if ( ( double ) y_sum / ( double ) bugs_alive >= 0.5 )
         {
           gene_y_String += "1";
         }
         else
         {
           gene_y_String += "0";
         }
       }

       return gene_x_String + "    " + gene_y_String;
     }

     private void  plotBugs ( Graphics  g )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < bugs.length; i++ )
       {
         Bug  bug = bugs [ i ];

         if ( bug.energy > 0 )
         {
           PlotLib.xy (
             bug.color,
             bug.x + 0.5,
             bug.y + 0.5,
             bounds, g,
             0, SPACE_WIDTH, 0, SPACE_HEIGHT,
             1, true );
         }
       }
     }

     private void  plotFlora ( Graphics  g )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  x = 0; x < SPACE_WIDTH; x++ )
       {
         for ( int  y = 0; y < SPACE_HEIGHT; y++ )
         {
           if ( flora_present [ x ] [ y ] )
           {
             PlotLib.xy (
               Color.GREEN,
               x + 0.5,
               y + 0.5,
               bounds, g,
               0, SPACE_WIDTH, 0, SPACE_HEIGHT,
               1, true );
           }
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
