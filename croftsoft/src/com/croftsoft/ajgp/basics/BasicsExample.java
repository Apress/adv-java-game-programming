     package com.croftsoft.ajgp.basics;
     
     import java.applet.Applet;
     import java.applet.AudioClip;
     import java.awt.Color;
     import java.awt.Cursor;
     import java.awt.Dimension;
     import java.awt.Font;
     import java.awt.Graphics2D;
     import java.awt.Point;
     import java.awt.Rectangle;
     import java.awt.event.ComponentAdapter;
     import java.awt.event.ComponentEvent;
     import java.awt.event.KeyAdapter;
     import java.awt.event.KeyEvent;
     import java.awt.event.MouseAdapter;
     import java.awt.event.MouseEvent;
     import java.awt.event.MouseMotionAdapter;
     import java.util.Random;
     import javax.swing.Icon;
     import javax.swing.ImageIcon;
     import javax.swing.JComponent;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.animation.AnimatedApplet;
     import com.croftsoft.core.animation.AnimationInit;

     /*********************************************************************
     * Game programming basics example.
     *
     * @version
     *   2003-11-06
     * @since
     *   2003-10-05
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  BasicsExample
       extends AnimatedApplet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  VERSION
       = "2003-11-06";

     private static final String  TITLE
       = "CroftSoft Basics";

     private static final String  APPLET_INFO
       = "\n" + TITLE + "\n"
       + "Version " + VERSION + "\n"
       + CroftSoftConstants.COPYRIGHT + "\n"
       + CroftSoftConstants.DEFAULT_LICENSE + "\n"
       + CroftSoftConstants.HOME_PAGE + "\n";

     //

     private static final Color      BACKGROUND_COLOR
       = Color.BLACK;

     private static final Cursor     CURSOR
       = new Cursor ( Cursor.CROSSHAIR_CURSOR );

     private static final Font       FONT
       = new Font ( "Arioso", Font.BOLD, 20 );

     private static final Double     FRAME_RATE
       = new Double ( 30.0 );

     private static final Dimension  FRAME_SIZE
       = new Dimension ( 600, 400 );

     private static final String     SHUTDOWN_CONFIRMATION_PROMPT
       = "Close " + TITLE + "?";

     //

     private static final String  MEDIA_DIR = "media/basics/";

     private static final String  AUDIO_FILENAME
       = MEDIA_DIR + "drip.wav";

     private static final String  IMAGE_FILENAME
       = MEDIA_DIR + "croftsoft.png";

     //

     private static final long    RANDOM_SEED = 0L;

     private static final Color   BALL_COLOR = Color.RED;

     private static final Color   SCORE_COLOR = Color.GREEN;

     private static final int     VELOCITY = 3;

     //////////////////////////////////////////////////////////////////////
     // final instance variables
     //////////////////////////////////////////////////////////////////////

     private final Rectangle  componentBounds;

     private final Random     random;

     //

     private final Rectangle  ballRectangle;

     private final Rectangle  targetRectangle;

     //////////////////////////////////////////////////////////////////////
     // non-final instance variables
     //////////////////////////////////////////////////////////////////////

     private boolean    componentResized;

     private KeyEvent   keyEvent;

     private Point      mousePoint;

     private boolean    mousePressed;

     //

     private AudioClip  audioClip;

     private Icon       icon;

     //

     private boolean    rolling;

     private int        score;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       launch ( new BasicsExample ( ) );
     }

     private static AnimationInit  createAnimationInit ( )
     //////////////////////////////////////////////////////////////////////
     {
       AnimationInit  animationInit = new AnimationInit ( );

       animationInit.setAppletInfo ( APPLET_INFO );

       animationInit.setCursor ( CURSOR );

       animationInit.setFont ( FONT );

       animationInit.setFrameIconFilename ( IMAGE_FILENAME );

       animationInit.setFrameRate ( FRAME_RATE );

       animationInit.setFrameSize ( FRAME_SIZE );

       animationInit.setFrameTitle ( TITLE );

       animationInit.setShutdownConfirmationPrompt (
         SHUTDOWN_CONFIRMATION_PROMPT );

       return animationInit;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  BasicsExample ( )
     //////////////////////////////////////////////////////////////////////
     {
       super ( createAnimationInit ( ) );

       componentBounds = new Rectangle ( );

       random = new Random ( RANDOM_SEED );

       animatedComponent.addComponentListener (
         new ComponentAdapter ( )
         {
           public void  componentResized (
             ComponentEvent  componentEvent )
           {
             componentResized = true;
           }
         } );

       animatedComponent.addKeyListener (
         new KeyAdapter ( )
         {
           public void  keyPressed  ( KeyEvent  ke )
           {
             keyEvent = ke;
           }
         } );

       animatedComponent.addMouseListener (
         new MouseAdapter ( )
         {
           public void  mousePressed ( MouseEvent  mouseEvent )
           {
             mousePressed = true;
           }
         } );

       animatedComponent.addMouseMotionListener (
         new MouseMotionAdapter ( )
         {
           public void  mouseMoved ( MouseEvent  mouseEvent )
           {
             mousePoint = mouseEvent.getPoint ( );
           }
         } );

       ballRectangle   = new Rectangle ( );

       targetRectangle = new Rectangle ( );
     }

     //////////////////////////////////////////////////////////////////////
     // interface Lifecycle methods
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       super.init ( );

       animatedComponent.requestFocus ( );

       componentResized = true;

       ClassLoader  classLoader = getClass ( ).getClassLoader ( );

       audioClip = Applet.newAudioClip (
         classLoader.getResource ( AUDIO_FILENAME ) );

       icon = new ImageIcon (
         classLoader.getResource ( IMAGE_FILENAME ) );

       ballRectangle.width    = icon.getIconWidth  ( );

       ballRectangle.height   = icon.getIconHeight ( );

       targetRectangle.width  = icon.getIconWidth  ( );

       targetRectangle.height = icon.getIconHeight ( );
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       if ( componentResized )
       {
         componentResized = false;

         component.repaint ( );

         component.getBounds ( componentBounds );

         if ( !rolling )
         {
           ballRectangle.y = componentBounds.height - ballRectangle.height;
         }
       }

       boolean  rollRequested = false;

       if ( mousePressed )
       {
         mousePressed = false;

         rollRequested = true;
       }

       int  ballMove = 0;

       if ( keyEvent != null )
       {
         int  keyCode = keyEvent.getKeyCode ( );

         switch ( keyCode )
         {
           case KeyEvent.VK_SPACE:

             rollRequested = true;

             break;

           case KeyEvent.VK_LEFT:
           case KeyEvent.VK_KP_LEFT:

             ballMove = -1;

             break;

           case KeyEvent.VK_RIGHT:
           case KeyEvent.VK_KP_RIGHT:

             ballMove = 1;

             break;
         }

         keyEvent = null;

         mousePoint = null;
       }

       if ( mousePoint != null )
       {
         if ( mousePoint.x
           < ballRectangle.x + ballRectangle.width / 2 - VELOCITY)
         {
           ballMove = -1;
         }
         else if ( mousePoint.x
           > ballRectangle.x + ballRectangle.width / 2 + VELOCITY )
         {
           ballMove = 1;
         }
         else
         {
           mousePoint = null;
         }
       }

       if ( rollRequested )
       {
         if ( !rolling )
         {
           audioClip.play ( );

           rolling = true;
         }
       }

       component.repaint ( targetRectangle );

       if ( rolling )
       {
         component.repaint ( ballRectangle );

         ballRectangle.y -= VELOCITY;

         boolean  reset = false;

         if ( targetRectangle.intersects ( ballRectangle ) )
         {
           reset = true;

           targetRectangle.x = -targetRectangle.width;

           audioClip.play ( );

           score++;

           component.repaint ( );
         }
         else if ( ballRectangle.y + ballRectangle.height < 0 )
         {
           reset = true;

           if ( score > 0 )
           {
             score--;
           }

           component.repaint ( );
         }

         if ( reset )
         {
           ballRectangle.y = componentBounds.height - ballRectangle.height;

           rolling = false;
         }

         component.repaint ( ballRectangle );
       }
       else if ( ballMove != 0 )
       {
         component.repaint ( ballRectangle );

         ballRectangle.x += ballMove * VELOCITY;

         if ( ballRectangle.x < 0 )
         {
           ballRectangle.x = 0;
         }

         if ( ballRectangle.x
           > componentBounds.width - ballRectangle.width )
         {
           ballRectangle.x
             = componentBounds.width - ballRectangle.width;
         }

         component.repaint ( ballRectangle );
       }

       if ( score > 1 )
       {
         targetRectangle.x += random.nextInt ( score ) * VELOCITY;
       }
       else
       {
         targetRectangle.x += VELOCITY;
       }

       if ( targetRectangle.x >= componentBounds.width )
       {
         targetRectangle.x = -targetRectangle.width;
       }

       component.repaint ( targetRectangle );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       graphics.setColor ( BACKGROUND_COLOR );

       graphics.fill ( componentBounds );

       icon.paintIcon ( component, graphics, targetRectangle.x, 0 );

       graphics.setColor ( BALL_COLOR );

       graphics.fillOval (
         ballRectangle.x,
         ballRectangle.y,
         ballRectangle.width,
         ballRectangle.height );

       graphics.setColor ( SCORE_COLOR );

       graphics.drawString (
         "Score:  " + Integer.toString ( score ),
         0, componentBounds.height );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
