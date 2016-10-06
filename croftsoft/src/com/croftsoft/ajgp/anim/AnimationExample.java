     package com.croftsoft.ajgp.anim;
     
     import java.awt.*;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.animation.AnimatedApplet;
     import com.croftsoft.core.animation.AnimationInit;
     import com.croftsoft.core.animation.painter.ColorPainter;

     /*********************************************************************
     * Animation Example
     *
     * @version
     *   2003-05-08
     * @since
     *   2003-05-08
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AnimationExample
       extends AnimatedApplet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  VERSION
       = "2003-05-08";

     private static final String  TITLE
       = "CroftSoft Animation Example";

     private static final String  APPLET_INFO
       = "\n" + TITLE + "\n"
       + CroftSoftConstants.COPYRIGHT + "\n"
       + CroftSoftConstants.HOME_PAGE + "\n"
       + "Version " + VERSION + "\n"
       + CroftSoftConstants.DEFAULT_LICENSE + "\n";

     //////////////////////////////////////////////////////////////////////
     // animation constants
     //////////////////////////////////////////////////////////////////////

     private static final Color  BACKGROUND_COLOR = Color.BLACK;

     private static final int    DELTA_X = 1;

     private static final int    DELTA_Y = 1;

     private static final Font   FONT
       = new Font ( "Arioso", Font.BOLD, 20 );

     private static final Color  FOREGROUND_COLOR = Color.RED;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       launch ( new AnimationExample ( ) );
     }

     private static AnimationInit  createAnimationInit ( )
     //////////////////////////////////////////////////////////////////////
     {
       AnimationInit  animationInit = new AnimationInit ( );

       animationInit.setAppletInfo ( APPLET_INFO );

       animationInit.setBackgroundColor ( BACKGROUND_COLOR );

       animationInit.setFont ( FONT );

       animationInit.setForegroundColor ( FOREGROUND_COLOR );

       animationInit.setFrameTitle ( TITLE );

       animationInit.setShutdownConfirmationPrompt ( null );

       return animationInit;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AnimationExample ( )
     //////////////////////////////////////////////////////////////////////
     {
       super ( createAnimationInit ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     // interface Lifecycle methods
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       super.init ( );

       addComponentPainter ( new ColorPainter ( ) );

       addComponentAnimator (
         new ExampleAnimator ( TITLE, DELTA_X, DELTA_Y ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }