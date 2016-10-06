     package com.croftsoft.core.gui;

     import java.awt.*;
     import java.awt.event.*;
     import java.security.AccessControlException;
     import javax.swing.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Toggles full-screen mode using ALT-ENTER.
     *
     * @version
     *   2003-07-23
     * @since
     *   2003-02-19
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  FullScreenToggler
       extends AbstractAction
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  ACTION_KEY_TOGGLE_FULLSCREEN
       = "com.croftsoft.core.gui.FullScreenToggler";

     //

     private final Window  window;

     //////////////////////////////////////////////////////////////////////
     // static methods
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       JFrame  jFrame = new JFrame ( "Press ALT-ENTER to toggle" );

       jFrame.setDefaultCloseOperation (
         WindowConstants.DO_NOTHING_ON_CLOSE );

       jFrame.addWindowListener ( new ShutdownWindowAdapter ( ) );

       WindowLib.centerOnScreen ( jFrame, 0.8 );

       toggle ( jFrame, true );

       monitor ( jFrame );

       jFrame.show ( );
     }

     public static boolean  monitor ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( component );

       Window  window = WindowLib.getParentWindow ( component );

       if ( window == null )
       {
         return false;
       }

       KeyStroke  keyStroke = KeyStroke.getKeyStroke (
         KeyEvent.VK_ENTER, InputEvent.ALT_MASK, false );

       InputMap  inputMap = component.getInputMap (
         JComponent.WHEN_IN_FOCUSED_WINDOW );

       inputMap.put ( keyStroke, ACTION_KEY_TOGGLE_FULLSCREEN );

       inputMap = component.getInputMap ( JComponent.WHEN_FOCUSED );

       inputMap.put ( keyStroke, ACTION_KEY_TOGGLE_FULLSCREEN );

       inputMap = component.getInputMap (
         JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT );

       inputMap.put ( keyStroke, ACTION_KEY_TOGGLE_FULLSCREEN );

       component.getActionMap ( ).put (
         ACTION_KEY_TOGGLE_FULLSCREEN,
         new FullScreenToggler ( window ) );

       return true;
     }

     public static void  monitor ( JFrame  jFrame )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( jFrame );

       monitor ( jFrame.getRootPane ( ) );
     }

     public static void  toggle (
       Window   window,
       boolean  fullScreen )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( window );

       GraphicsConfiguration  graphicsConfiguration
         = window.getGraphicsConfiguration ( );

       GraphicsDevice  graphicsDevice
         = graphicsConfiguration.getDevice ( );

       if ( fullScreen )
       {
         try
         {
           graphicsDevice.setFullScreenWindow ( window );

           window.validate ( );

           window.repaint ( );
         }
         catch ( AccessControlException  ex )
         {
         }
       }
       else
       {
         try
         {
           graphicsDevice.setFullScreenWindow ( null );

           window.validate ( );

           window.repaint ( );
         }
         catch ( AccessControlException  ex )
         {
         }
       }
     }

     public static void  toggle ( Window  window )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( window );
       
       GraphicsConfiguration  graphicsConfiguration
         = window.getGraphicsConfiguration ( );

       GraphicsDevice  graphicsDevice
         = graphicsConfiguration.getDevice ( );

       toggle ( window, graphicsDevice.getFullScreenWindow ( ) != window );
     }

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  FullScreenToggler ( Window  window )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.window = window );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  toggle ( )
     //////////////////////////////////////////////////////////////////////
     {
       toggle ( window );
     }

     public void  toggle ( boolean  fullScreen )
     //////////////////////////////////////////////////////////////////////
     {
       toggle ( window, fullScreen );
     }

     public void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       toggle ( window );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }