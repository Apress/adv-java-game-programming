     package com.croftsoft.apps.shooter;
     
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
     import com.croftsoft.core.animation.painter.SpacePainter;
     import com.croftsoft.core.animation.sprite.IconSprite;
     import com.croftsoft.core.animation.sprite.TextSprite;
     import com.croftsoft.core.awt.image.ImageLib;
     import com.croftsoft.core.gui.LifecycleWindowListener;
     import com.croftsoft.core.lang.ClassLib;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.util.loop.FixedDelayLoopGovernor;

     /*********************************************************************
     * Main Shooter class.
     *
     * @version
     *   2003-07-23
     * @since
     *   2002-03-23
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Shooter
       extends JApplet
       implements ComponentAnimator, Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {
       
     //////////////////////////////////////////////////////////////////////
     // Applet constants
     //////////////////////////////////////////////////////////////////////

     private static final String  VERSION
       = "2003-07-17";

     private static final String  TITLE
       = "CroftSoft Shooter";

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
     private static final double  FRAME_RATE     = 50.0;

     private static final double  ATTACKER_DENSITY = 4.0 / ( 600 * 400 );

     /** pixels per frame */
     private static final double  ATTACKER_VELOCITY = 100.0 / FRAME_RATE;

     private static final String  MEDIA_DIR = "media/shooter/";

     private static final String  SHOOTER_REST_IMAGE_FILENAME
       = MEDIA_DIR + "shooter_rest.png";

     private static final String  SHOOTER_BANG_IMAGE_FILENAME
       = MEDIA_DIR + "shooter_bang.png";

     private static final String  SHOOTER_BOOM_IMAGE_FILENAME
       = MEDIA_DIR + "shooter_boom.png";

     private static final String  BANG_AUDIO_FILENAME
       = MEDIA_DIR + "bang.wav";

     private static final String  EXPLODE_AUDIO_FILENAME
       = MEDIA_DIR + "explode.wav";

     private static final Color   BACKGROUND_COLOR
       = Color.BLACK;

     //////////////////////////////////////////////////////////////////////
     // instance variables
     //////////////////////////////////////////////////////////////////////

     private AnimatedComponent   animatedComponent;

     private ShooterSprite       shooterSprite;

     private AttackerSprite [ ]  attackerSprites;

     private long                highScore;

     private TextSprite          scoreTextSprite;

     private Random              random;

     private AudioClip           explodeAudioClip;

     private SpacePainter        spacePainter;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       JFrame  jFrame = new JFrame ( FRAME_TITLE );

       try
       {
         jFrame.setIconImage ( ClassLib.getResourceAsImage (
           Shooter.class, FRAME_ICON_FILENAME ) );
       }
       catch ( Exception  ex )
       {
       }

       Shooter  shooter = new Shooter ( );

       jFrame.setContentPane ( shooter );

       LifecycleWindowListener.launchFrameAsDesktopApp (
         jFrame,
         new Lifecycle [ ] { shooter },
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

       animatedComponent.setLoopGovernor (
         new FixedDelayLoopGovernor ( FRAME_RATE ) );

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

       try
       {
         Image  shooterRestImage = loadAutomaticImage (
           SHOOTER_REST_IMAGE_FILENAME, Transparency.BITMASK );

         Image  shooterBangImage = loadAutomaticImage (
           SHOOTER_BANG_IMAGE_FILENAME, Transparency.BITMASK );

         Image  shooterBoomImage = loadAutomaticImage (
           SHOOTER_BOOM_IMAGE_FILENAME, Transparency.BITMASK );

         URL  bangAudioURL = getClass ( ).getClassLoader ( )
           .getResource ( BANG_AUDIO_FILENAME );

         AudioClip  bangAudioClip = Applet.newAudioClip ( bangAudioURL );

         shooterSprite = new ShooterSprite (
           shooterRestImage,
           shooterBangImage,
           shooterBoomImage,
           bangAudioClip,
           this );

         animatedComponent.addMouseListener ( shooterSprite );

         animatedComponent.addMouseMotionListener ( shooterSprite );
       }
       catch ( IOException  ex )
       {
         ex.printStackTrace ( );
       }

       // attackers

       attackerSprites = new AttackerSprite [ 0 ];

       explodeAudioClip = Applet.newAudioClip (
         getClass ( ).getClassLoader ( ).getResource (
         EXPLODE_AUDIO_FILENAME ) );

       random = new Random ( );

//     shooterBounds = new Rectangle ( );

       scoreTextSprite = new TextSprite ( TITLE );

       scoreTextSprite.setX ( 20 );

       scoreTextSprite.setY ( 20 );

       scoreTextSprite.setColor ( Color.RED );

       spacePainter = new SpacePainter ( );
     }

     public void  start   ( ) { animatedComponent.start   ( ); }

     public void  stop    ( ) { animatedComponent.stop    ( ); }

     public void  destroy ( ) { animatedComponent.destroy ( ); }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public AttackerSprite [ ]  getAttackerSprites ( )
     //////////////////////////////////////////////////////////////////////
     {
       return attackerSprites;
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       component.repaint ( );

       AttackerSprite [ ]  attackerSprites = this.attackerSprites;

       for ( int  i = 0; i < attackerSprites.length; i++ )
       {
         attackerSprites [ i ].update ( component );
       }

       Shape  shooterCollisionShape = shooterSprite.getCollisionShape ( );

       if ( shooterCollisionShape != null )
       {
         Rectangle2D  shooterCollisionRectangle2D
           = shooterCollisionShape.getBounds2D ( );

         for ( int  i = 0; i < attackerSprites.length; i++ )
         {
           Shape  attackerCollisionShape
             = attackerSprites [ i ].getCollisionShape ( );

           if ( attackerCollisionShape != null )
           {
             if ( attackerCollisionShape.intersects (
               shooterCollisionRectangle2D ) )
             {
               shooterSprite.setHit ( );

               attackerSprites [ i ].setHit ( );

               break;
             }
           }
         }
       }

       shooterSprite.update ( component );

       long  score = shooterSprite.getScore ( );

       highScore = score > highScore ? score : highScore;

       scoreTextSprite.setText (
         "High Score:  " + highScore + "  Score:  " + score );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       spacePainter.paint ( component, graphics );

       AttackerSprite [ ]  attackerSprites = this.attackerSprites;

       for ( int  i = 0; i < attackerSprites.length; i++ )
       {
         attackerSprites [ i ].paint ( component, graphics );
       }

       shooterSprite.paint ( component, graphics );

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

     private void  resetBounds ( )
     //////////////////////////////////////////////////////////////////////
     {
       shooterSprite.resetScore ( );

       int  attackerCount = ( int ) Math.round (
         ATTACKER_DENSITY
         * animatedComponent.getWidth  ( )
         * animatedComponent.getHeight ( ) );

       attackerSprites = new AttackerSprite [ attackerCount ];

       for ( int  i = 0; i < attackerSprites.length; i++ )
       {
         AttackerSprite  attackerSprite = new AttackerSprite (
           explodeAudioClip,
           random,
           ATTACKER_VELOCITY );

         attackerSprite.setY ( Double.POSITIVE_INFINITY );

         attackerSprites [ i ] = attackerSprite;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
