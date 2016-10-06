     package com.croftsoft.apps.dodger;
     
     import java.applet.*;
     import java.awt.*;
     import java.awt.event.*;
     import java.awt.geom.*;
     import java.io.*;
     import java.net.URL;
     import java.util.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.animation.AnimatedComponent;
     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.animation.Sprite;
     import com.croftsoft.core.animation.animator.TileAnimator;
     import com.croftsoft.core.animation.clock.SystemClock;
     import com.croftsoft.core.animation.sprite.IconSprite;
     import com.croftsoft.core.animation.sprite.TextSprite;
     import com.croftsoft.core.applet.AppletLib;
     import com.croftsoft.core.awt.image.ImageLib;
     import com.croftsoft.core.gui.LifecycleWindowListener;
     import com.croftsoft.core.io.SerializableLib;
     import com.croftsoft.core.jnlp.JnlpLib;
     import com.croftsoft.core.lang.ClassLib;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;

     /*********************************************************************
     * Main Dodger class.
     *
     * @version
     *   2003-09-29
     * @since
     *   2002-03-18
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Dodger
       extends JApplet
       implements ComponentAnimator, Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {
       
     //////////////////////////////////////////////////////////////////////
     // Applet constants
     //////////////////////////////////////////////////////////////////////

     private static final String  VERSION
       = "2003-09-29";

     private static final String  TITLE
       = "CroftSoft Dodger";

     private static final String  INFO
       = "\n" + TITLE + "\n"
       + CroftSoftConstants.COPYRIGHT + "\n"
       + CroftSoftConstants.HOME_PAGE + "\n"
       + "Version " + VERSION + "\n";

     //////////////////////////////////////////////////////////////////////
     // Frame constants
     //////////////////////////////////////////////////////////////////////

     private static final String  FRAME_TITLE
       = TITLE;

     private static final String  FRAME_ICON_FILENAME
       = "/images/croftsoft.png";
       
     private static final Dimension  FRAME_SIZE
       = null;

     private static final String  SHUTDOWN_CONFIRMATION_PROMPT
       = "Close " + TITLE + "?";

     //////////////////////////////////////////////////////////////////////
     // animation constants
     //////////////////////////////////////////////////////////////////////

     /** frames per second */
     private static final double  FRAME_RATE    = 60.0;

     /** pixels per second */
     private static final double  FLOW_RATE     = 100.0;

     private static final int  PIXELS_PER_FRAME
       = ( int ) Math.round ( FLOW_RATE / FRAME_RATE );

     private static final int     MAX_OBSTACLES = 200;

     private static final double  OBSTACLE_DENSITY
       = 1 / ( 5.0 * 32 * 32 );

     private static final String  MEDIA_DIR = "media/dodger/";

     private static final String  DODGER_GREEN_IMAGE_FILENAME
       = MEDIA_DIR + "dodger_green.png";

     private static final String  DODGER_YELLOW_IMAGE_FILENAME
       = MEDIA_DIR + "dodger_yellow.png";

     private static final String  DODGER_RED_IMAGE_FILENAME
       = MEDIA_DIR + "dodger_red.png";

     private static final String  DODGER_BANG_IMAGE_FILENAME
       = MEDIA_DIR + "dodger_bang.png";

     private static final String  DODGER_BOOM_IMAGE_FILENAME
       = MEDIA_DIR + "dodger_boom.png";

     private static final String  BANG_AUDIO_FILENAME
       = MEDIA_DIR + "bang.wav";

     private static final String  OBSTACLE_EXPLODE_AUDIO_FILENAME
       = MEDIA_DIR + "explode.wav";

     private static final Color   BACKGROUND_COLOR
       = Color.WHITE;

     //////////////////////////////////////////////////////////////////////
     // persistence constants
     //////////////////////////////////////////////////////////////////////

     private static final String  FILE_CONTENTS_SPEC = "DodgerData";

     private static final String  LATEST_DATA_FILENAME
       = ".croftsoft"
       + File.separator + "dodger"
       + File.separator + "dodger01.dat";

     private static final String  BACKUP_DATA_FILENAME
       = ".croftsoft"
       + File.separator + "dodger"
       + File.separator + "dodger02.dat";

     private static final String  PERSISTENCE_KEY = FILE_CONTENTS_SPEC;

     //////////////////////////////////////////////////////////////////////
     // instance variables
     //////////////////////////////////////////////////////////////////////

     private AnimatedComponent   animatedComponent;

     private Rectangle           bounds;

     private DodgerSprite        dodgerSprite;

     private ObstacleSprite [ ]  obstacleSprites;

     private Rectangle           dodgerBounds;

     private long                highScore;

     private TextSprite          scoreTextSprite;

     private Random              random;

     private AudioClip           obstacleExplodeAudioClip;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       JFrame  jFrame = new JFrame ( FRAME_TITLE );

       try
       {
         jFrame.setIconImage ( ClassLib.getResourceAsImage (
           Dodger.class, FRAME_ICON_FILENAME ) );
       }
       catch ( Exception  ex )
       {
       }

       Dodger  dodger = new Dodger ( );

       jFrame.setContentPane ( dodger );

       LifecycleWindowListener.launchFrameAsDesktopApp (
         jFrame,
         new Lifecycle [ ] { dodger },
         FRAME_SIZE,
         SHUTDOWN_CONFIRMATION_PROMPT );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getAppletInfo ( )
     //////////////////////////////////////////////////////////////////////
     {
       return INFO;
     }

     //////////////////////////////////////////////////////////////////////
     // interface Lifecycle methods
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( INFO );

       animatedComponent = new AnimatedComponent ( this, FRAME_RATE );

       animatedComponent.addComponentListener (
         new ComponentAdapter ( )
         {
           public void  componentResized ( ComponentEvent  componentEvent )
           {
             resetBounds ( );
           }
         } );

       Container  contentPane = getContentPane ( );

       contentPane.setLayout ( new BorderLayout ( ) );

       contentPane.add ( animatedComponent, BorderLayout.CENTER );

       animatedComponent.setCursor (
         new Cursor ( Cursor.CROSSHAIR_CURSOR ) );

       animatedComponent.init ( );

       bounds = animatedComponent.getBounds ( );

       try
       {
         Icon  dodgerGreenIcon = new ImageIcon ( loadAutomaticImage (
           DODGER_GREEN_IMAGE_FILENAME, Transparency.BITMASK ) );

         Icon  dodgerYellowIcon = new ImageIcon ( loadAutomaticImage (
           DODGER_YELLOW_IMAGE_FILENAME, Transparency.BITMASK ) );

         Icon  dodgerRedIcon = new ImageIcon ( loadAutomaticImage (
           DODGER_RED_IMAGE_FILENAME, Transparency.BITMASK ) );

         Icon  dodgerBangIcon = new ImageIcon ( loadAutomaticImage (
           DODGER_BANG_IMAGE_FILENAME, Transparency.BITMASK ) );

         Icon  dodgerBoomIcon = new ImageIcon ( loadAutomaticImage (
           DODGER_BOOM_IMAGE_FILENAME, Transparency.BITMASK ) );

         URL  bangAudioURL = getClass ( ).getClassLoader ( )
           .getResource ( BANG_AUDIO_FILENAME );

         AudioClip  bangAudioClip = Applet.newAudioClip ( bangAudioURL );

         dodgerSprite = new DodgerSprite (
           dodgerGreenIcon,
           dodgerYellowIcon,
           dodgerRedIcon,
           dodgerBangIcon,
           dodgerBoomIcon,
           bangAudioClip,
           bounds );

         animatedComponent.addMouseListener ( dodgerSprite );

         animatedComponent.addMouseMotionListener ( dodgerSprite );

         animatedComponent.addKeyListener ( dodgerSprite );
       }
       catch ( IOException  ex )
       {
         ex.printStackTrace ( );
       }

       animatedComponent.requestFocus ( );

       // obstacles

       obstacleSprites = new ObstacleSprite [ 0 ];

       obstacleExplodeAudioClip = Applet.newAudioClip (
         getClass ( ).getClassLoader ( ).getResource (
         OBSTACLE_EXPLODE_AUDIO_FILENAME ) );

       random = new Random ( );

       dodgerBounds = new Rectangle ( );

       scoreTextSprite = new TextSprite ( TITLE );

       scoreTextSprite.setX ( 20 );

       scoreTextSprite.setY ( 20 );

       scoreTextSprite.setColor ( Color.RED );

       try
       {
         DodgerData  dodgerData = ( DodgerData ) SerializableLib.load (
           LATEST_DATA_FILENAME,
           BACKUP_DATA_FILENAME,
           FILE_CONTENTS_SPEC,
           ( Applet ) this,
           PERSISTENCE_KEY,
           ( ClassLoader ) null,
           ( String ) null );

         if ( dodgerData != null )
         {
           highScore = dodgerData.highScore;
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
     }

     public void  start ( ) { animatedComponent.start ( ); }

     public void  stop  ( ) { animatedComponent.stop  ( ); }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         SerializableLib.save (
           new DodgerData ( highScore ),
           LATEST_DATA_FILENAME,
           BACKUP_DATA_FILENAME,
           FILE_CONTENTS_SPEC,
           ( Applet ) this,
           PERSISTENCE_KEY );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }

       animatedComponent.destroy ( );
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       ObstacleSprite [ ]  obstacleSprites = this.obstacleSprites;

       for ( int  i = 0; i < obstacleSprites.length; i++ )
       {
         obstacleSprites [ i ].update ( component );
       }

       Shape  dodgerCollisionShape = dodgerSprite.getCollisionShape ( );

       if ( dodgerCollisionShape != null )
       {
         Rectangle2D  dodgerCollisionRectangle2D
           = dodgerCollisionShape.getBounds2D ( );

         for ( int  i = 0; i < obstacleSprites.length; i++ )
         {
           Shape  obstacleCollisionShape
             = obstacleSprites [ i ].getCollisionShape ( );

           if ( obstacleCollisionShape != null )
           {
             if ( obstacleCollisionShape.intersects (
               dodgerCollisionRectangle2D ) )
             {
               dodgerSprite.setHit ( );

               obstacleSprites [ i ].setHit ( );

               break;
             }
           }
         }
       }

       dodgerSprite.update ( component );

       if ( dodgerSprite.isShooting ( ) )
       {
         shoot ( );
       }

       long  score = dodgerSprite.getScore ( );

       highScore = score > highScore ? score : highScore;

       scoreTextSprite.setText (
         "High Score:  " + highScore + "  Score:  " + score );

       component.repaint ( );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       graphics.setColor ( BACKGROUND_COLOR );

       graphics.fillRect ( 0, 0, bounds.width, bounds.height );

       ObstacleSprite [ ]  obstacleSprites = this.obstacleSprites;

       for ( int  i = 0; i < obstacleSprites.length; i++ )
       {
         obstacleSprites [ i ].paint ( component, graphics );
       }

       dodgerSprite.paint ( component, graphics );

       scoreTextSprite.paint ( component, graphics );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private Image  loadAutomaticImage (
       String  imageFilename,
       int     transparency )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return ImageLib.loadAutomaticImage (
         imageFilename,
         transparency,
         animatedComponent,
         getClass ( ).getClassLoader ( ),
         null );
     }

     private void  shoot ( )
     //////////////////////////////////////////////////////////////////////
     {
       Rectangle2D  shootArea = dodgerSprite.getShootArea ( );

       int  index = -1;

       ObstacleSprite [ ]  obstacleSprites = this.obstacleSprites;

       for ( int  i = 0; i < obstacleSprites.length; i++ )
       {
         Sprite  obstacleSprite = obstacleSprites [ i ];

         Shape  obstacleCollisionShape
           = obstacleSprite.getCollisionShape ( );

         if ( obstacleCollisionShape != null )
         {
           if ( obstacleCollisionShape.intersects ( shootArea ) )
           {
             if ( ( index < 0 )
               || ( obstacleSprites [ index ].getY ( )
               < obstacleSprite.getY ( ) ) )
             {
               index = i;
             }
           }
         }
       }

       if ( index > -1 )
       {
         obstacleSprites [ index ].setHit ( );
       }
     }

     private void  resetBounds ( )
     //////////////////////////////////////////////////////////////////////
     {
       dodgerSprite.resetScore ( );

       animatedComponent.getBounds ( bounds );

       int  obstacleCount = ( int ) Math.round (
         OBSTACLE_DENSITY * bounds.width * bounds.height );

       obstacleSprites = new ObstacleSprite [ obstacleCount ];

       for ( int  i = 0; i < obstacleSprites.length; i++ )
       {
         ObstacleSprite  obstacleSprite = new ObstacleSprite (
           obstacleExplodeAudioClip,
           random,
           bounds,
           PIXELS_PER_FRAME );

         obstacleSprite.setY ( Double.POSITIVE_INFINITY );

         obstacleSprites [ i ] = obstacleSprite;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }