     package com.croftsoft.ajgp.grap;
     
     import java.awt.*;
     import java.awt.image.BufferStrategy;
     import java.awt.event.*;
     import java.io.*;
     import java.security.*;
     import javax.swing.*;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.animation.AnimatedComponent;
     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.animation.ComponentPainter;
     import com.croftsoft.core.animation.animator.FrameRateAnimator;
     import com.croftsoft.core.animation.animator.NullComponentAnimator;
     import com.croftsoft.core.animation.animator.TileAnimator;
     import com.croftsoft.core.animation.component
       .BufferStrategyAnimatedComponent;
     import com.croftsoft.core.animation.painter.ColorPainter;
     import com.croftsoft.core.animation.painter.NullComponentPainter;
     import com.croftsoft.core.awt.image.ImageLib;
     import com.croftsoft.core.gui.BufferCapabilitiesLib;
     import com.croftsoft.core.gui.DisplayModeLib;
     import com.croftsoft.core.gui.FullScreenToggler;
     import com.croftsoft.core.gui.GraphicsDeviceLib;
     import com.croftsoft.core.gui.WindowLib;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;

     /*********************************************************************
     * Demonstration of full screen exclusive mode.
     *
     * @version
     *   2003-08-01
     * @since
     *   2003-02-24
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  FullScreenDemo
       extends JApplet
       implements ComponentAnimator, Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {
       
     //////////////////////////////////////////////////////////////////////
     // Applet constants
     //////////////////////////////////////////////////////////////////////

     private static final String  VERSION
       = "2003-08-01";

     private static final String  TITLE
       = "CroftSoft Full Screen Demo";

     private static final String  INFO
       = "\n" + TITLE
       + "\n" + CroftSoftConstants.COPYRIGHT
       + "\n" + CroftSoftConstants.HOME_PAGE
       + "\n" + "Version " + VERSION
       + "\n";

     //////////////////////////////////////////////////////////////////////
     // animation constants
     //////////////////////////////////////////////////////////////////////

     private static final DisplayMode [ ]  DESIRED_DISPLAY_MODES
       = new DisplayMode [ ] {
         new DisplayMode ( 640, 480, 8, 85 ),
         new DisplayMode ( 640, 480, 8, 75 ),
         new DisplayMode ( 640, 480, 8, 70 ),
         new DisplayMode ( 640, 480, 8,  0 ),
         new DisplayMode ( 640, 480, 0,  0 ),
         new DisplayMode ( 800, 600, 8, 85 ),
         new DisplayMode ( 800, 600, 8, 75 ),
         new DisplayMode ( 800, 600, 8, 70 ),
         new DisplayMode ( 800, 600, 8,  0 ),
         new DisplayMode ( 800, 600, 0,  0 ) };

     private static final int     BUFFER_COUNT = 2;

     private static final String  MEDIA_DIR    = "media/fullscreen/";

     private static final String  TILE_IMAGE_FILENAME
       = "clear_brick_32x32.png";

     //////////////////////////////////////////////////////////////////////
     // instance variables
     //////////////////////////////////////////////////////////////////////

     private final BufferStrategy  bufferStrategy;

     //

     private AnimatedComponent  animatedComponent;

     private ComponentPainter   brickColorPainter;

     private ComponentAnimator  brickTileAnimator;

     private ComponentAnimator  frameRateAnimator;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( INFO );

       JFrame  jFrame = new JFrame ( );

       jFrame.setUndecorated ( true );

       GraphicsConfiguration  graphicsConfiguration
         = jFrame.getGraphicsConfiguration ( );

       GraphicsDevice  graphicsDevice
         = graphicsConfiguration.getDevice ( );

       System.out.println ( "Initial display mode:" );

       DisplayModeLib.print ( graphicsDevice.getDisplayMode ( ) );

       BufferStrategy  bufferStrategy = null;

       try
       {
         graphicsDevice.setFullScreenWindow ( jFrame );

         jFrame.validate ( );

         jFrame.repaint  ( );

         System.out.println ( "\nisDisplayChangeSupported:  "
           + graphicsDevice.isDisplayChangeSupported ( ) );

         DisplayMode [ ]  desiredDisplayModes = DESIRED_DISPLAY_MODES;

         if ( args.length == 4 )
         {
           desiredDisplayModes = new DisplayMode [ ] {
             new DisplayMode (
               Integer.parseInt ( args [ 0 ] ),
               Integer.parseInt ( args [ 1 ] ),
               Integer.parseInt ( args [ 2 ] ),
               Integer.parseInt ( args [ 3 ] ) ) };
         }

         boolean  displayModeChanged
           = GraphicsDeviceLib.changeDisplayMode (
           graphicsDevice, desiredDisplayModes );

         if ( displayModeChanged )
         {
           System.out.println ( "\nNew display mode:" );

           DisplayModeLib.print ( graphicsDevice.getDisplayMode ( ) );
         }

         if ( graphicsDevice.isFullScreenSupported ( ) )
         {
           System.out.println ( "\nFull-screen exclusive mode supported" );

           jFrame.setIgnoreRepaint ( true );

           jFrame.createBufferStrategy ( BUFFER_COUNT );

           bufferStrategy = jFrame.getBufferStrategy ( );

           BufferCapabilitiesLib.print (
             bufferStrategy.getCapabilities ( ) );
         }
         else
         {
           System.out.println (
             "\nFull-screen exclusive mode unsupported" );
         }
       }
       catch ( AccessControlException  ex )
       {
         System.out.println ( "\nFull-screen exclusive mode not allowed" );
       }

       FullScreenDemo  fullScreenDemo
         = new FullScreenDemo ( bufferStrategy );

       jFrame.setContentPane ( fullScreenDemo );

       jFrame.show ( );

       fullScreenDemo.init  ( );

       fullScreenDemo.start ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  FullScreenDemo ( BufferStrategy  bufferStrategy )
     //////////////////////////////////////////////////////////////////////
     {
       this.bufferStrategy = bufferStrategy;

       brickColorPainter = NullComponentPainter .INSTANCE;

       brickTileAnimator = NullComponentAnimator.INSTANCE;

       frameRateAnimator = NullComponentAnimator.INSTANCE;
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
       if ( bufferStrategy == null )
       {
         animatedComponent = new AnimatedComponent ( this );
       }
       else
       {
         animatedComponent
           = new BufferStrategyAnimatedComponent ( this, bufferStrategy );
       }

       animatedComponent.addMouseListener (
         new MouseAdapter ( )
         {
           public void  mousePressed ( MouseEvent  mouseEvent )
           {
             FullScreenToggler.toggle (
               WindowLib.getParentWindow ( animatedComponent ), false );

             System.exit ( 0 );
           }
         } );

       Container  contentPane = getContentPane ( );

       contentPane.setLayout ( new BorderLayout ( ) );

       contentPane.add ( animatedComponent, BorderLayout.CENTER );

       validate ( );

       animatedComponent.init ( );

       brickColorPainter = new ColorPainter ( Color.RED );

       try
       {
         Image  brickTileImage = ImageLib.loadAutomaticImage (
           MEDIA_DIR + TILE_IMAGE_FILENAME,
           Transparency.BITMASK,
           animatedComponent,
           getClass ( ).getClassLoader ( ),
           null ); // dimension

         Icon  brickTileIcon = new ImageIcon ( brickTileImage );

         brickTileAnimator = new TileAnimator (
           0, 0, brickTileIcon, ( Shape ) null,  1, 1 );
       }
       catch ( IOException  ex )
       {
         ex.printStackTrace ( );
       }

       frameRateAnimator = new FrameRateAnimator ( this, Color.ORANGE );
     }

     public void  start   ( ) { animatedComponent.start   ( ); }

     public void  stop    ( ) { animatedComponent.stop    ( ); }

     public void  destroy ( ) { animatedComponent.destroy ( ); }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       brickTileAnimator.update ( component );

       frameRateAnimator.update ( component );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics2D )
     //////////////////////////////////////////////////////////////////////
     {
       brickColorPainter.paint ( component, graphics2D );

       brickTileAnimator.paint ( component, graphics2D );

       frameRateAnimator.paint ( component, graphics2D );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }