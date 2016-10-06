     package com.croftsoft.apps.insight;

     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import javax.swing.*;

     import com.croftsoft.core.awt.font.FontLib;
     import com.croftsoft.core.gui.FrameLib;
     import com.croftsoft.core.gui.plot.PlotLib;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.math.RandomLib;
     import com.croftsoft.core.animation.*;
     import com.croftsoft.core.animation.component.*;

     /*********************************************************************
     * Goblins hunt kobolds in the dark using a neural network.
     *
     * @version
     *   2002-03-23
     * @since
     *   1996-09-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  Insight
       extends JApplet
       implements ActionListener, ComponentListener, ItemListener,
         Lifecycle, ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  VERSION = "2002-03-23";

     private static final String  TITLE = "CroftSoft Insight";

     private static final String  INFO
       = "\n" + TITLE + "\n"
       + "Copyright 2002 CroftSoft Inc\n"
       + "http://www.croftsoft.com/\n"
       + "Version " + VERSION + "\n";

     private static final double     FRAME_RATE = 2.0;

     private static final Dimension  FRAME_SIZE  = null;

     private static final String  FRAME_ICON_FILENAME = "/images/david.png";

     private static final String  SHUTDOWN_CONFIRMATION_PROMPT
       = "Close " + TITLE + "?";

     private static final String  FONT_NAME = "TimesRoman";

     private static final int     FONT_STYLE = Font.BOLD;

     //

     private static final int  ETHER  = 0;

     private static final int  WALL   = 1;

     private static final int  GOBLIN = 2;

     private static final int  KOBOLD = 3;

     private static final int  ELEMENT_COUNT = 4;

     private static final Color [ ]  ELEMENT_COLOR = {
       Color.BLACK,
       Color.GRAY,
       Color.MAGENTA,
       Color.GREEN };

     private static final String [ ]  ELEMENT_NAME = {
       "empty",
       "wall",
       "goblin",
       "kobold" };

     //

     private static final Point [ ]  DIRECTIONS = {
       new Point ( -1,  1 ),
       new Point (  0,  1 ),
       new Point (  1,  1 ),
       new Point ( -1,  0 ),
       new Point (  1,  0 ),
       new Point ( -1, -1 ),
       new Point (  0, -1 ),
       new Point (  1, -1 ) };

     private static final int  GOBLIN_BRAIN_OUTPUTS = DIRECTIONS.length;

     private static final int [ ]  neurons_per_layer
       = { 64, GOBLIN_BRAIN_OUTPUTS };

     //

     private static final int  BATTLEFIELD_WIDTH  = 100;

     private static final int  BATTLEFIELD_HEIGHT = 100;

     //

     private static final int  BORDER_WALL_COUNT
       = 2 * BATTLEFIELD_WIDTH
       + 2 * BATTLEFIELD_HEIGHT - 4; // overlap at 4 corners

     private static final int  WALL_COUNT   = BORDER_WALL_COUNT + 100;

     private static final int  GOBLIN_COUNT = 100;

     private static final int  KOBOLD_COUNT = 100;

     //

     private Thing [ ]      goblins;

     private Thing [ ]      kobolds;

     private Thing [ ]      walls;

     private Thing [ ] [ ]  space_contents;

     //

     private int   goblins_alive_count = GOBLIN_COUNT;

     private int   kobolds_alive_count = KOBOLD_COUNT;

     private long  goblins_killed = 0;

     private long  kobolds_killed = 0;

     private long  goblins_killed_by_kobolds = 0;

     private long  kobolds_killed_by_goblins = 0;

     //

     private boolean       goblin_learning_on = false;

     private int [ ]       direction_button_element;

     private Matrix        goblin_brain_inputs;

     private Backprop_Net  goblin_brain;

     private int           goblin_best_direction;

     //

     private AnimatedComponent  animatedComponent;

     private JCheckBox       learningOnCheckBox;

     private JButton         scrambleBrainsButton;

     private JButton [ ]     directionButtons;

     //

     private boolean      isGoblinsMove = true;

     private Rectangle    bounds; // animatedComponent bounds

     private Font         font;

     //////////////////////////////////////////////////////////////////////
     // static methods
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       JFrame  jFrame = new JFrame ( TITLE );

       try
       {
         FrameLib.setIconImage ( jFrame, FRAME_ICON_FILENAME );
       }
       catch ( IOException  ex )
       {
       }

       Insight  insight = new Insight ( );

       jFrame.setContentPane ( insight );

       FrameLib.launchJFrameAsDesktopApp (
         jFrame,
         new Lifecycle [ ] { insight },
         FRAME_SIZE,
         SHUTDOWN_CONFIRMATION_PROMPT );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getAppletInfo ( ) { return INFO; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( INFO );

       Container  contentPane = getContentPane ( );

       contentPane.setLayout ( new BorderLayout ( ) );

       //

       bounds = new Rectangle ( );

       animatedComponent
         = new AnimatedComponent ( this, FRAME_RATE );

       animatedComponent.addComponentListener ( this );

       animatedComponent.init ( );

       contentPane.add ( animatedComponent, BorderLayout.CENTER );

       //

       JPanel  southPanel = new JPanel ( new GridBagLayout ( ) );

       contentPane.add ( southPanel, BorderLayout.SOUTH );

       //

       JPanel  infoPanel = new JPanel ( new GridLayout ( 2, 1 ) );

       GridBagConstraints  gridBagConstraints = new GridBagConstraints ( );

       gridBagConstraints.weightx = 0.0;

       southPanel.add ( infoPanel, gridBagConstraints );

       //

       learningOnCheckBox = new JCheckBox (
         "Goblin Learning On", goblin_learning_on );

       learningOnCheckBox.setHorizontalAlignment ( SwingConstants.RIGHT );

       learningOnCheckBox.setHorizontalTextPosition ( SwingConstants.LEFT );

       learningOnCheckBox.addItemListener ( this );

       infoPanel.add ( learningOnCheckBox );

       //

       scrambleBrainsButton
         = new JButton ( "Scramble Goblin Brains" );

       scrambleBrainsButton.addActionListener ( this );

       infoPanel.add ( scrambleBrainsButton );

       //

       JPanel  brainPanel = new JPanel ( new GridLayout ( 3, 3 ) );

       gridBagConstraints.weightx = 1.0;

       southPanel.add ( brainPanel, gridBagConstraints );

       //

       directionButtons = new JButton [ DIRECTIONS.length ];

       direction_button_element = new int [ DIRECTIONS.length ];

       direction_button_element [ 1 ] = WALL;

       direction_button_element [ 3 ] = KOBOLD;

       direction_button_element [ 4 ] = GOBLIN;

       for ( int  i = 0; i < DIRECTIONS.length; i++ )
       {
         directionButtons [ i ]
           = new JButton (
           ELEMENT_NAME [ direction_button_element [ i ] ]
           + ":  000%" );

         if ( i == 4 )
         {
           JLabel  goblinLabel = new JLabel (
             "Goblin", SwingConstants.CENTER );

           goblinLabel.setBackground ( ELEMENT_COLOR [ GOBLIN ] );

           brainPanel.add ( goblinLabel );
         }

         brainPanel.add ( directionButtons [ i ] );

         directionButtons [ i ].addActionListener ( this );

         Color  backgroundColor
           = ELEMENT_COLOR [ direction_button_element [ i ] ];

         Color  foregroundColor = Color.BLACK;

         if ( backgroundColor == Color.BLACK )
         {
           foregroundColor = Color.WHITE;
         }

         directionButtons [ i ].setBackground ( backgroundColor );

         directionButtons [ i ].setForeground ( foregroundColor );
       }

       //

       space_contents = new Thing [ BATTLEFIELD_WIDTH  ]
                                  [ BATTLEFIELD_HEIGHT ];

       // place outer border walls

       walls = new Thing [ WALL_COUNT ];

       for ( int  i = 0; i < BATTLEFIELD_WIDTH; i++ )
       {
         int  x = i;

         int  y = 0;

         Thing  wall = new Thing ( WALL, x, y );

         walls [ i ] = wall;

         space_contents [ x ] [ y ] = wall;
       }

       for ( int  i = 0; i < BATTLEFIELD_WIDTH; i++ )
       {
         int  x = i;

         int  y = BATTLEFIELD_HEIGHT - 1;

         Thing  wall = new Thing ( WALL, x, y );

         walls [ BATTLEFIELD_WIDTH + i ] = wall;

         space_contents [ x ] [ y ] = wall;
       }

       for ( int  i = 0; i < BATTLEFIELD_HEIGHT - 2; i++ )
       {
         int  x = 0;

         int  y = i + 1;

         Thing  wall = new Thing ( WALL, x, y );

         walls [ 2 * BATTLEFIELD_WIDTH + i ] = wall;

         space_contents [ x ] [ y ] = wall;
       }

       for ( int  i = 0; i < BATTLEFIELD_HEIGHT - 2; i++ )
       {
         int  x = BATTLEFIELD_WIDTH - 1;

         int  y = i + 1;

         Thing  wall = new Thing ( WALL, x, y );

         walls [ 2 * BATTLEFIELD_WIDTH + BATTLEFIELD_HEIGHT - 2 + i ]
           = wall;

         space_contents [ x ] [ y ] = wall;
       }

       // randomly place inner walls

       for ( int  i = BORDER_WALL_COUNT; i < WALL_COUNT; i++ )
       {
         putThingInRandomEmptySpace (
           walls [ i ] = new Thing ( WALL ) );
       }

       // randomly place goblins

       goblins = new Thing [ GOBLIN_COUNT ];

       for ( int  i = 0; i < GOBLIN_COUNT; i++ )
       {
         putThingInRandomEmptySpace (
           goblins [ i ] = new Thing ( GOBLIN ) );
       }

       // randomly place kobolds

       kobolds = new Thing [ KOBOLD_COUNT ];

       for ( int  i = 0; i < KOBOLD_COUNT; i++ )
       {
         putThingInRandomEmptySpace (
           kobolds [ i ] = new Thing ( KOBOLD ) );
       }

       //

       goblin_brain_inputs
         = new Matrix ( ELEMENT_COUNT * DIRECTIONS.length, 1 );

       goblin_brain = new Backprop_Net (
         goblin_brain_inputs.rows, neurons_per_layer, true, true );
     }

     public synchronized void  start ( )
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
       if ( isGoblinsMove )
       {
         // Move the goblins

         for ( int  i = 0; i < goblins.length; i++ )
         {
           Thing  goblin = goblins [ i ];

           if ( goblin.isAlive )
           {
             moveGoblin ( goblin );
           }
         }

         direction_button_update ( );

         isGoblinsMove = false;
       }
       else
       {
         // Move the kobolds

         for ( int  i = 0; i < kobolds.length; i++ )
         {
           Thing  kobold = kobolds [ i ];

           if ( kobold.isAlive )
           {
             moveKobold ( kobold );
           }
         }

         isGoblinsMove = true;
       }

       component.repaint ( );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  g )
     //////////////////////////////////////////////////////////////////////
     {
       component.getBounds ( bounds );

       // paint the background

       g.setColor ( Color.BLACK );

       g.fillRect ( bounds.x, bounds.y, bounds.width, bounds.height );

       // paint the kobolds, goblins, and walls

       plotThings ( bounds, g, ELEMENT_COLOR [ KOBOLD ], kobolds );

       plotThings ( bounds, g, ELEMENT_COLOR [ GOBLIN ], goblins );

       plotThings ( bounds, g, ELEMENT_COLOR [ WALL   ], walls   );

       // paint the status

       g.setColor ( java.awt.Color.white );

       g.setFont ( font );

       g.drawString (
         createStatusString (
           kobolds_killed,
           goblins_killed,
           kobolds_killed_by_goblins,
           goblins_killed_by_kobolds ),
         bounds.x + 10, bounds.y + bounds.height - 10 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  itemStateChanged ( ItemEvent  itemEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  item = itemEvent.getItem ( );

       if ( item == learningOnCheckBox )
       {
         goblin_learning_on = learningOnCheckBox.isSelected ( );

         resetStats ( );
       }       
     }

     public void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = actionEvent.getSource ( );

       if ( source == scrambleBrainsButton )
       {
         goblin_brain.weights_randomize_uniform ( -1.0, 1.0 );

         resetStats ( );
       }
       else if ( source instanceof JButton )
       {
         int  buttonIndex = -1;

         JButton  button = null;

         for ( int  i = 0; i < directionButtons.length; i++ )
         {
           if ( source == directionButtons [ i ] )
           {
             buttonIndex = i;

             button = ( JButton ) source;

             break;
           }
         }

         direction_button_element [ buttonIndex ]++;

         if ( direction_button_element [ buttonIndex ] >= ELEMENT_COUNT )
         {
           direction_button_element [ buttonIndex ] = 0;
         }

         Color  backgroundColor
           = ELEMENT_COLOR [ direction_button_element [ buttonIndex ] ];

         Color  foregroundColor = Color.BLACK;

         if ( backgroundColor == Color.BLACK )
         {
           foregroundColor = Color.WHITE;
         }

         button.setBackground ( backgroundColor );

         button.setForeground ( foregroundColor );

         button.setText (
           "" + ELEMENT_NAME [ direction_button_element [
           buttonIndex ] ] );
       }       
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentListener methods
     //////////////////////////////////////////////////////////////////////

     public void  componentResized ( ComponentEvent  componentEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = componentEvent.getSource ( );

       if ( source == animatedComponent )
       {
         Rectangle  bounds = animatedComponent.getBounds ( );

         FontLib.setMaxFont (
           animatedComponent,
           createStatusString ( 99999, 99999, 99999, 99999 ),
           FONT_NAME,
           FONT_STYLE,
           bounds.width,
           bounds.height );       
       }
     }

     public void  componentHidden ( ComponentEvent  componentEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  componentMoved ( ComponentEvent  componentEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  componentShown ( ComponentEvent  componentEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private void  resetStats ( )
     //////////////////////////////////////////////////////////////////////
     {
       kobolds_killed = 0;

       goblins_killed = 0;

       kobolds_killed_by_goblins = 0;

       goblins_killed_by_kobolds = 0;
     }

     private static String  createStatusString (
       long  kobolds_killed,
       long  goblins_killed,
       long  kobolds_killed_by_goblins,
       long  goblins_killed_by_kobolds )
     //////////////////////////////////////////////////////////////////////
     {
       return "Dead Kobolds:  "
         + kobolds_killed
         + "    "
         + "Dead Goblins:  " + goblins_killed
         + "    "
         + "Kobolds killed by Goblins:  " + kobolds_killed_by_goblins
         + "    "
         + "Goblins killed by Kobolds:  " + goblins_killed_by_kobolds;
     }

     private void  direction_button_update ( )
     //////////////////////////////////////////////////////////////////////
     {
       Matrix  direction_probabilities
         = calculateDirectionProbabilities (
         direction_button_element );

       for ( int i = 0; i < directionButtons.length; i++ )
       {
         directionButtons [ i ].setText (
           "" + ELEMENT_NAME [ direction_button_element [ i ] ]
           + ":  "
           + ( int ) ( direction_probabilities.data [ i ] [ 0 ]
           * 100.0 ) + "%" );
       }
     }

     private Matrix  calculateDirectionProbabilities (
       int [ ]  typeVision )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < typeVision.length; i++ )
       {
         for ( int  j = 0; j < ELEMENT_COUNT; j++ )
         {
           goblin_brain_inputs.data
             [ ELEMENT_COUNT * i + j ] [ 0 ]
             = ( typeVision [ i ] == j ) ? 1.0 : 0.0;
         }
       }

       Matrix  leanings = goblin_brain.forward_pass ( goblin_brain_inputs );

       return leanings.divide ( leanings.sum ( ) );
     }

     private Point  goblin_move_direction ( Thing  goblin )
     //////////////////////////////////////////////////////////////////////
     {
       int [ ]  typeVision = new int [ DIRECTIONS.length ];

       for ( int  i = 0; i < typeVision.length; i++ )
       {
         typeVision [ i ] = ETHER;

         int  x = goblin.x + DIRECTIONS [ i ].x;

         int  y = goblin.y + DIRECTIONS [ i ].y;

         if ( ( x >= 0 )
           && ( x < space_contents.length )
           && ( y >= 0 )
           && ( y < space_contents [ 0 ].length ) )
         {
           Thing  content = space_contents [ x ] [ y ];

           if ( content != null )
           {
             typeVision [ i ] = content.type;
           }
         }
       }

       Matrix  direction_probabilities
         = calculateDirectionProbabilities ( typeVision );

       double  sum = 0.0;

       double  r = Croft_Random.uniform ( 0.0, 1.0 );

       for ( int i = 0; i < direction_probabilities.rows; i++ )
       {
         if ( r < sum + direction_probabilities.data [ i ] [ 0 ] )
         {
           goblin_best_direction = i;

           break;
         }

         sum += direction_probabilities.data [ i ] [ 0 ];
       }

       return new Point (
         DIRECTIONS [ goblin_best_direction ].x,
         DIRECTIONS [ goblin_best_direction ].y );
     }

     private void  goblin_move_reinforce ( double  target )
     //////////////////////////////////////////////////////////////////////
     {
       Matrix  desired_directions = new Matrix (
         goblin_brain.outputs.m [ goblin_brain.layers - 1 ] );

       if ( goblin_learning_on )
       {
// why are we using supervised instead of reinforcement learning?

         for ( int  i = 0; i < DIRECTIONS.length; i++ )
         {
           desired_directions.data [ i ] [ 0 ] = 1.0 - target;
         }

         desired_directions.data [ goblin_best_direction ] [ 0 ] = target;

         goblin_brain.backward_pass ( desired_directions );

         goblin_brain.weights_update ( );
       }
     }

     private void  moveGoblin ( Thing  goblin )
     //////////////////////////////////////////////////////////////////////
     {
       Point  move_direction = goblin_move_direction ( goblin );

       int  x = goblin.x + move_direction.x;

       int  y = goblin.y + move_direction.y;

       boolean  abortMove
         =  ( x < 0 )
         || ( x >= BATTLEFIELD_WIDTH  )
         || ( y < 0 )
         || ( y >= BATTLEFIELD_HEIGHT );

       if ( !abortMove )
       {
         Thing  content = space_contents [ x ] [ y ];

         if ( content == null )
         {
         }
         else if ( content.type == KOBOLD )
         {
           killThing ( content );

           goblin_move_reinforce ( 1.0 );

           kobolds_killed_by_goblins++;
         }
         else if ( content.type == GOBLIN )
         {
           killThing ( content );

           goblin_move_reinforce ( 0.0 );
         }
         else if ( content.type == WALL )
         {
           killThing ( goblin );

           goblin_move_reinforce ( 0.0 );

           abortMove = true;
         }
         else
         {
           throw new RuntimeException ( "unknown Thing type" );
         }
       }

       if ( !abortMove )
       {
         space_contents [ goblin.x ] [ goblin.y ] = null;

         goblin.x = x;

         goblin.y = y;

         space_contents [ x ] [ y ] = goblin;
       }
     }

     private void  moveKobold ( Thing  kobold )
     //////////////////////////////////////////////////////////////////////
     {
       // move randomly

       int  rd = ( int ) RandomLib.roll ( 1, 8, -1 );

       Point  move_direction
         = new Point ( DIRECTIONS [ rd ].x, DIRECTIONS [ rd ].y );

       int  x = kobold.x + move_direction.x;

       int  y = kobold.y + move_direction.y;

       boolean  abortMove
         =  ( x < 0 )
         || ( x >= BATTLEFIELD_WIDTH  )
         || ( y < 0 )
         || ( y >= BATTLEFIELD_HEIGHT );

       if ( !abortMove )
       {
         Thing  content = space_contents [ x ] [ y ];

         if ( content == null )
         {
         }
         else if ( content.type == GOBLIN )
         {
           killThing ( content );

           goblins_killed_by_kobolds++;
         }
         else if ( content.type == KOBOLD )
         {
           killThing ( content );
         }
         else if ( content.type == WALL )
         {
           killThing ( kobold );

           abortMove = true;
         }
         else
         {
           throw new RuntimeException ( "unknown Thing type" );
         }
       }

       if ( !abortMove )
       {
         space_contents [ kobold.x ] [ kobold.y ] = null;

         kobold.x = x;

         kobold.y = y;

         space_contents [ x ] [ y ] = kobold;
       }
     }

     private void  killThing ( Thing  thing )
     //////////////////////////////////////////////////////////////////////
     {
       if ( thing.type == GOBLIN )
       {
         goblins_killed++;
       }
       else if ( thing.type == KOBOLD )
       {
         kobolds_killed++;
       }

       space_contents [ thing.x ] [ thing.y ] = null;

       // spawn a replacement

// why do we bother with isAlive flag?

       putThingInRandomEmptySpace ( thing );
     }

     private void  putThingInRandomEmptySpace ( Thing  thing )
     //////////////////////////////////////////////////////////////////////
     {
       int  x;

       int  y;

       while ( true )
       {
         x = ( int ) RandomLib.roll ( 1, BATTLEFIELD_WIDTH , -1 );

         y = ( int ) RandomLib.roll ( 1, BATTLEFIELD_HEIGHT, -1 );

         if ( space_contents [ x ] [ y ] == null )
         {
           break;
         }
       }

       space_contents [ x ] [ y ] = thing;

       thing.x = x;

       thing.y = y;
     }

     private void  plotThings (
       Rectangle  bounds,
       Graphics   graphics,
       Color      color,
       Thing [ ]  things )
     //////////////////////////////////////////////////////////////////////
     {
       if ( things == null )
       {
         return;
       }

       for ( int  i = 0; i < things.length; i++ )
       {
         Thing  thing = things [ i ];

// Since they regenerate, is isAlive always true?

         if ( thing.isAlive )
         {
           PlotLib.xy ( color, thing.x + 0.5, thing.y + 0.5,
             bounds, graphics,
             0, BATTLEFIELD_WIDTH, 0, BATTLEFIELD_HEIGHT, 1, true );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
