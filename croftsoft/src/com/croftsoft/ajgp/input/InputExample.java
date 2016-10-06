     package com.croftsoft.ajgp.input;
     
     import java.awt.*;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.animation.AnimatedApplet;
     import com.croftsoft.core.animation.AnimationInit;

     /*********************************************************************
     * User input example
     *
     * @version
     *   2003-09-29
     * @since
     *   2003-09-29
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  InputExample
       extends AnimatedApplet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  VERSION
       = "2003-09-29";

     private static final String  TITLE
       = "CroftSoft Input Example";

     private static final String  APPLET_INFO
       = "\n" + TITLE + "\n"
       + CroftSoftConstants.COPYRIGHT + "\n"
       + CroftSoftConstants.HOME_PAGE + "\n"
       + "Version " + VERSION + "\n"
       + CroftSoftConstants.DEFAULT_LICENSE + "\n";

     //////////////////////////////////////////////////////////////////////
     // animation constants
     //////////////////////////////////////////////////////////////////////

     private static final Font  FONT
       = new Font ( "Arioso", Font.BOLD, 20 );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       launch ( new InputExample ( ) );
     }

     private static AnimationInit  createAnimationInit ( )
     //////////////////////////////////////////////////////////////////////
     {
       AnimationInit  animationInit = new AnimationInit ( );

       animationInit.setAppletInfo ( APPLET_INFO );

       animationInit.setFont ( FONT );

       animationInit.setFrameTitle ( TITLE );

       animationInit.setShutdownConfirmationPrompt ( null );

       return animationInit;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  InputExample ( )
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

       addComponentAnimator ( new InputAnimator ( animatedComponent ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }