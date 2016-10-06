     package com.croftsoft.core.gui;

     import java.awt.*;
     import java.awt.event.*;
     import javax.swing.*;

     import com.croftsoft.core.awt.image.ImageLib;
     import com.croftsoft.core.gui.FullScreenToggler;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.AppletLifecycle;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.lang.lifecycle.LifecycleLib;

     /*********************************************************************
     * Calls the lifecycle methods in response to windowing events.
     *
     * <p>
     * When the window is activated, calls the start() methods.
     * When the window is deactivated, calls the stop() methods.
     * The first time the window is activated, the init() method
     * will be called.
     * </p>
     *
     * <p>
     * Performs the following upon the window closing event:
     * <ol>
     * <li> Prompts for shutdown confirmation.
     * <li> Calls the window hide() method.
     * <li> Calls the stop and destroy() method, in array order, of each of
     *      the Lifecycle instances passed via the constructor argument.
     *      Any exceptions are caught, printed, and ignored.
     * <li> Calls the window dispose() method.
     * <li> Calls System.exit(0).
     * </ol>
     * </p>
     *
     * <p>
     * Example:
     * <code>
     * <pre>
     * jFrame.setDefaultCloseOperation (
     *   javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE );
     *
     * jFrame.addWindowListener (
     *   new LifecycleWindowListener ( this, "Exit Program?" ) );
     * </pre>
     * </code>
     * </p>
     *
     * @version
     *   2003-08-02
     * @since
     *   2002-12-24
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  LifecycleWindowListener
       implements WindowListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Lifecycle [ ]  lifecycles;

     private final String         shutdownConfirmationPrompt;

     private final String         shutdownConfirmationTitle;

     //

     private boolean  initialized;

     //////////////////////////////////////////////////////////////////////
     // Static methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Test and demonstration method.
     *********************************************************************/
     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       launchFrameAsDesktopApp (
         new JFrame ( "Test" ),
         new Lifecycle [ ] {
           new Lifecycle ( )
           {
             public void  init    ( ) { System.out.println ( "init"    ); }
             public void  start   ( ) { System.out.println ( "start"   ); }
             public void  stop    ( ) { System.out.println ( "stop"    ); }
             public void  destroy ( ) { System.out.println ( "destroy" ); }
           } },
         null, // frameSize
         "Exit Test?" );
     }

     /*********************************************************************
     * Associates a LifecycleWindowListener with the JFrame and launches.
     *
     * <p>
     * In order to create a flexible JFrame subclass that can be run either
     * as a stand-alone desktop application by itself or as a pop-up frame
     * called from a currently running program, you will need to maintain
     * the logic for handling these different modes of operation outside of
     * the subclass itself.  This method provides the logic for running a
     * JFrame subclass as an independent desktop application.
     * </p>
     *
     * <p>
     * This method associates a LifecycleWindowAdapter with the JFrame which
     * will call the lifecycle methods in response to windowing events.
     * It then launches the JFrame on the center of the screen.
     * </p>
     *
     * @see
     *   Lifecycle
     *
     * @param  lifecycles
     *
     *   An optional array of Lifecycle objects to initialized upon
     *   startup, started upon window activation, stopped upon window
     *   deactivation, and destroyed upon shutdown.  May be null.
     *
     * @param  frameSize
     *
     *   If null, a default value will be used based upon screen size.
     *
     * @param  shutdownConfirmationPrompt
     *
     *   If null, no confirmation prompt will be given.
     *********************************************************************/
     public static void  launchFrameAsDesktopApp (
       JFrame               jFrame,
       final Lifecycle [ ]  lifecycles,
       Dimension            frameSize,
       String               shutdownConfirmationPrompt )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( jFrame );

       jFrame.setDefaultCloseOperation (
         WindowConstants.DO_NOTHING_ON_CLOSE );

       jFrame.addWindowListener ( new LifecycleWindowListener (
         lifecycles, shutdownConfirmationPrompt ) );

       if ( frameSize != null )
       {
         WindowLib.centerOnScreen ( jFrame, frameSize );
       }
       else
       {
         WindowLib.centerOnScreen ( jFrame, 0.8 );
       }

       jFrame.show ( );
     }

     public static void  launchAppletAsDesktopApp (
       JApplet      applet,
       String       frameTitle,
       String       frameIconFilename,
       ClassLoader  classLoader,
       boolean      useFullScreenToggler,
       Dimension    frameSize,
       String       shutdownConfirmationPrompt )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( applet );

       if ( frameTitle == null )
       {
         frameTitle = "";
       }

       JFrame  jFrame = new JFrame ( frameTitle );

       if ( frameIconFilename != null )
       {
         try
         {
           jFrame.setIconImage ( ImageLib.loadBufferedImage (
             frameIconFilename, classLoader ) );
         }
         catch ( Exception  ex )
         {
         }
       }

       jFrame.setContentPane ( applet );

       if ( useFullScreenToggler )
       {
         FullScreenToggler.monitor ( jFrame );
       }

       LifecycleWindowListener.launchFrameAsDesktopApp (
         jFrame,
         new Lifecycle [ ] { new AppletLifecycle ( applet ) },
         frameSize,
         shutdownConfirmationPrompt );
     }

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *
     * @param  lifecycles
     *   May be null.
     * @param  shutdownConfirmationPrompt
     *   If null, no shutdown confirmation prompt dialog will be given.
     * @param  shutdownConfirmationTitle
     *   If null, the shutdownConfirmationPrompt value will be used.
     *********************************************************************/
     public  LifecycleWindowListener (
       Lifecycle [ ]  lifecycles,
       String         shutdownConfirmationPrompt,
       String         shutdownConfirmationTitle )
     //////////////////////////////////////////////////////////////////////
     {
       this.lifecycles                 = lifecycles;

       this.shutdownConfirmationPrompt = shutdownConfirmationPrompt;

       this.shutdownConfirmationTitle  = shutdownConfirmationTitle;
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * <code>
     * <pre>
     * this ( lifecycles, shutdownConfirmationPrompt, null );
     * </pre>
     * </code>
     *********************************************************************/
     public  LifecycleWindowListener (
       Lifecycle [ ]  lifecycles,
       String         shutdownConfirmationPrompt )
     //////////////////////////////////////////////////////////////////////
     {
       this ( lifecycles, shutdownConfirmationPrompt, null );
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * <code>
     * <pre>
     * this (
     *   new Lifecycle [ ] { lifecycle },
     *   shutdownConfirmationPrompt );
     * </pre>
     * </code>
     *********************************************************************/
     public  LifecycleWindowListener (
       Lifecycle  lifecycle,
       String     shutdownConfirmationPrompt )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         new Lifecycle [ ] { lifecycle },
         shutdownConfirmationPrompt );
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * <code>
     * <pre>
     * this ( lifecycles, null );
     * </pre>
     * </code>
     *********************************************************************/
     public  LifecycleWindowListener ( Lifecycle [ ]  lifecycles )
     //////////////////////////////////////////////////////////////////////
     {
       this ( lifecycles, null );
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * <code>
     * <pre>
     * this ( new Lifecycle [ ] { lifecycle } );
     * </pre>
     * </code>
     *********************************************************************/
     public  LifecycleWindowListener ( Lifecycle  lifecycle )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new Lifecycle [ ] { lifecycle } );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  windowActivated ( WindowEvent  windowEvent )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !initialized )
       {
         LifecycleLib.init ( lifecycles );

         initialized = true;
       }

       LifecycleLib.start ( lifecycles );
     }

     public void  windowClosed ( WindowEvent  windowEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  windowClosing ( WindowEvent  windowEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Window  window = windowEvent.getWindow ( );

       if ( shutdownConfirmationPrompt != null )
       {
         int  confirm = JOptionPane.showOptionDialog ( window,
           shutdownConfirmationPrompt, 
           shutdownConfirmationTitle != null
             ? shutdownConfirmationTitle : shutdownConfirmationPrompt,
           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
           null, null, null );

         if ( confirm != JOptionPane.YES_OPTION )
         {
           return;
         }
       }

       window.hide ( );

       if ( shutdownConfirmationPrompt == null )
       {
         LifecycleLib.stop ( lifecycles );
       }

       LifecycleLib.destroy ( lifecycles );

       window.dispose ( );

       System.exit ( 0 );
     }

     public void  windowDeactivated ( WindowEvent  windowEvent )
     //////////////////////////////////////////////////////////////////////
     {
       LifecycleLib.stop ( lifecycles );
     }

     public void  windowDeiconified ( WindowEvent  windowEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  windowIconified ( WindowEvent  windowEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  windowOpened ( WindowEvent  windowEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
