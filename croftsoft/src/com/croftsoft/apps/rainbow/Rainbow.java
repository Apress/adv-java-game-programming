     package com.croftsoft.apps.rainbow;
     
     import java.awt.*;
     import java.awt.event.*;
     import java.util.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.animation.AnimatedComponent;
     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.animation.collector.SimpleRepaintCollector;
     import com.croftsoft.core.animation.factory.DefaultAnimationFactory;
     import com.croftsoft.core.gui.FrameLib;
     import com.croftsoft.core.lang.ClassLib;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;

     /*********************************************************************
     * Main Rainbow class.
     *
     * @version
     *   2003-07-23
     * @since
     *   2002-03-18
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Rainbow
       extends JApplet
       implements ComponentAnimator, Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {
       
     //////////////////////////////////////////////////////////////////////
     // Applet constants
     //////////////////////////////////////////////////////////////////////

     private static final String  VERSION
       = "2003-07-23";

     private static final String  TITLE
       = "CroftSoft Rainbow";

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

     private static final double  FRAME_RATE       = 100.0;

     private static final int     COLOR_WIDTH      = 64;

     private static final int     COLOR_HEIGHT     = 64;

     private static final Color   BACKGROUND_COLOR = Color.WHITE;

     //////////////////////////////////////////////////////////////////////
     // instance variables
     //////////////////////////////////////////////////////////////////////

     private AnimatedComponent  animatedComponent;

     private Random             random;

     private Color              color;

     private int                x;

     private int                y;

     private Point              mousePoint;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       JFrame  jFrame = new JFrame ( FRAME_TITLE );

       try
       {
         jFrame.setIconImage ( ClassLib.getResourceAsImage (
           Rainbow.class, FRAME_ICON_FILENAME ) );
       }
       catch ( Exception  ex )
       {
       }

       Rainbow  rainbow = new Rainbow ( );

       jFrame.setContentPane ( rainbow );

       FrameLib.launchJFrameAsDesktopApp (
         jFrame,
         new Lifecycle [ ] { rainbow },
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

       animatedComponent = new AnimatedComponent (
         this,
         new SimpleRepaintCollector ( ),
         DefaultAnimationFactory.INSTANCE.createLoopGovernor (
           FRAME_RATE ) );

       Container  contentPane = getContentPane ( );

       contentPane.setLayout ( new BorderLayout ( ) );

       contentPane.add ( animatedComponent, BorderLayout.CENTER );

       animatedComponent.init ( );

       color = Color.WHITE;

       animatedComponent.addMouseMotionListener (
         new MouseMotionAdapter ( )
         {
           public void  mouseMoved ( MouseEvent  mouseEvent )
           {
             mousePoint = mouseEvent.getPoint ( );
           }
         } );

       animatedComponent.addMouseListener (
         new MouseAdapter ( )
         {
           public void  mouseExited ( MouseEvent  mouseEvent )
           {
             mousePoint = null;
           }

           public void  mousePressed ( MouseEvent  mouseEvent )
           {
             repaint ( );
           }
         } );

       animatedComponent.setCursor (
         new Cursor ( Cursor.CROSSHAIR_CURSOR ) );

       random = new Random ( );
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
       Point  mousePoint = this.mousePoint;

       if ( mousePoint != null )
       {
         color = new Color (
           random.nextInt ( 256 ),
           random.nextInt ( 256 ),
           random.nextInt ( 256 ) );

         x = mousePoint.x - COLOR_WIDTH  / 2;

         y = mousePoint.y - COLOR_HEIGHT / 2;

         component.repaint ( x, y, COLOR_WIDTH, COLOR_HEIGHT );
       }
       else
       {
         x = Integer.MIN_VALUE;
       }
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       graphics.setColor ( BACKGROUND_COLOR );

       graphics.fillRect (
         0, 0, component.getWidth ( ), component.getHeight ( ) );

       graphics.setColor ( color );

       graphics.fillRect ( x, y, COLOR_WIDTH, COLOR_HEIGHT );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
