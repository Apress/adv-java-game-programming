     package com.croftsoft.apps.chat;
     
     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import java.net.*;
     import java.util.*;
     import javax.swing.*;

     import com.croftsoft.core.gui.LifecycleWindowListener;
     import com.croftsoft.core.io.SerializableCoder;
     import com.croftsoft.core.lang.lifecycle.LifecycleLib;
     import com.croftsoft.core.math.MathConstants;
     import com.croftsoft.core.net.http.HttpLib;
     import com.croftsoft.core.net.http.msg.HttpMessageClient;
     import com.croftsoft.core.role.Consumer;
     import com.croftsoft.core.security.Authentication;
     import com.croftsoft.core.util.queue.Queue;

     import com.croftsoft.apps.chat.client.ChatClient;
     import com.croftsoft.apps.chat.controller.ChatController;
     import com.croftsoft.apps.chat.view.ChatPanel;

     /*********************************************************************
     * Chat applet with 2D sprite avatars.
     *
     * @version
     *   2003-06-17
     * @since
     *   2000-04-24
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ChatApplet
       extends JApplet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     //////////////////////////////////////////////////////////////////////
     // frame constants
     //////////////////////////////////////////////////////////////////////

     private static final String     FRAME_ICON_FILENAME          = null;

     private static final Dimension  FRAME_SIZE                   = null;

     private static final String     SHUTDOWN_CONFIRMATION_PROMPT = null;

     private static final boolean    USE_FULL_SCREEN_TOGGLER      = true;

     //////////////////////////////////////////////////////////////////////
     // network constants
     //////////////////////////////////////////////////////////////////////

     private static final String  SERVLET_PATH  = "servlet";

     //////////////////////////////////////////////////////////////////////
     // instance variables
     //////////////////////////////////////////////////////////////////////

     private ChatClient  chatClient;

     private ChatPanel   chatPanel;

     //////////////////////////////////////////////////////////////////////
     // static methods
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       LifecycleWindowListener.launchAppletAsDesktopApp (
         new ChatApplet ( ),
         ChatConstants.TITLE,
         FRAME_ICON_FILENAME,
         ChatApplet.class.getClassLoader ( ),
         USE_FULL_SCREEN_TOGGLER,
         FRAME_SIZE,
         SHUTDOWN_CONFIRMATION_PROMPT );
     }

     //////////////////////////////////////////////////////////////////////
     // overridden applet methods
     //////////////////////////////////////////////////////////////////////

     public String  getAppletInfo ( ) { return ChatConstants.INFO; }

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( getAppletInfo ( ) );

       // model

       Authentication  authentication = getAuthentication ( );

       chatClient = new ChatClient ( authentication, getServletURL ( ) );

       // controller

       ChatController  chatController = new ChatController (
         authentication, chatClient.getRequestQueue ( ) );

       // view

       chatPanel = new ChatPanel (
         chatClient.getEventQueue ( ) , chatController );

       setJMenuBar ( chatPanel.getMenuBar ( ) );

       Container  contentPane = getContentPane ( );

       contentPane.setLayout ( new BorderLayout ( ) );

       contentPane.add ( chatPanel, BorderLayout.CENTER );

       LifecycleLib.init ( chatPanel );

       validate ( );

       LifecycleLib.init ( chatClient );
     }

     public void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       LifecycleLib.start ( chatPanel  );

       LifecycleLib.start ( chatClient );
     }

     public void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       LifecycleLib.stop ( chatClient );

       LifecycleLib.stop ( chatPanel  );
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       LifecycleLib.destroy ( chatClient );

       LifecycleLib.destroy ( chatPanel  );
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private static Authentication  getAuthentication ( )
     //////////////////////////////////////////////////////////////////////
     {
       String  username = Long.toString ( new Random ( ).nextLong ( ) );

       return new Authentication ( username, "password" );
     }

     private URL  getServletURL ( )
     //////////////////////////////////////////////////////////////////////
     {
       URL  codeBaseURL = null;

       try
       {
         codeBaseURL = getCodeBase ( );
       }
       catch ( Exception  ex )
       {
       }

       try
       {
         if ( codeBaseURL != null )
         {
           return new URL ( codeBaseURL, SERVLET_PATH );
         }
         else
         {
           return null;
         }
       }
       catch ( MalformedURLException  ex )
       {
         // this should never happen if SERVLET_PATH is good

         ex.printStackTrace ( );

         return null;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
