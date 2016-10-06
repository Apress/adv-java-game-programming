     package com.croftsoft.apps.zombie;
     
     import java.awt.*;
     import java.awt.event.*;
     import javax.swing.*;

     import com.croftsoft.core.gui.FrameLib;
     import com.croftsoft.core.lang.ClassLib;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.animation.AnimatedComponent;

     /*********************************************************************
     * Zombie Hunter.
     *
     * @version
     *   2003-02-18
     * @since
     *   2002-02-25
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Zombie
       extends JApplet
       implements Lifecycle, ZombieConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {
       
     private AnimatedComponent  animatedComponent;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       JFrame  jFrame = new JFrame ( FRAME_TITLE );

       try
       {
         jFrame.setIconImage ( ClassLib.getResourceAsImage (
           Zombie.class, FRAME_ICON_FILENAME ) );
       }
       catch ( Exception  ex )
       {
       }

       Zombie  zombie = new Zombie ( );

       jFrame.setContentPane ( zombie );

       FrameLib.launchJFrameAsDesktopApp (
         jFrame,
         new Lifecycle [ ] { zombie },
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

       animatedComponent
         = new ZombieReanimator ( ).getAnimatedComponent ( );

       Container  contentPane = getContentPane ( );

       contentPane.setLayout ( new BorderLayout ( ) );

       contentPane.add ( animatedComponent, BorderLayout.CENTER );

       validate ( );

       animatedComponent.init ( );
     }

     public void  start   ( ) { animatedComponent.start   ( ); }

     public void  stop    ( ) { animatedComponent.stop    ( ); }

     public void  destroy ( ) { animatedComponent.destroy ( ); }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
