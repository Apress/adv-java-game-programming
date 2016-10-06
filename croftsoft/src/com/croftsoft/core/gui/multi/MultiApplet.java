     package com.croftsoft.core.gui.multi;

     import java.applet.*;
     import java.awt.*;
     import java.io.*;
     import java.net.*;
     import java.util.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.awt.image.ImageLib;
     import com.croftsoft.core.gui.FullScreenToggler;
     import com.croftsoft.core.gui.LifecycleWindowListener;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Pair;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;

     /*********************************************************************
     * An applet that contains multiple applets.
     *
     * @version
     *   2003-08-02
     * @since
     *   2002-02-25
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  MultiApplet
       extends JApplet
       implements Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  DEFAULT_NEWS_NAME = "News";

     //

     private final String    appletInfo;

     private final Pair [ ]  appletPairs;

     private final String    newsName;

     private final String    newsHTML;

     private final String    newsPage;

     //

     private JTabbedPane      jTabbedPane;

     private Component        appletComponent;

     private boolean          isStarted;

     private int              index;

     private MultiAppletStub  multiAppletStub;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Test method.
     *********************************************************************/
     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       launch (
         CroftSoftConstants.DEFAULT_APPLET_INFO,
         new Pair [ ] {
           new Pair ( "Applet1", "javax.swing.JApplet" ),
           new Pair ( "Applet2", "javax.swing.JApplet" ) },
         DEFAULT_NEWS_NAME,
         ( String ) null,
         CroftSoftConstants.HOME_PAGE,
         "CroftSoft MultiApplet",
         CroftSoftConstants.FRAME_ICON_FILENAME,
         MultiApplet.class.getClassLoader ( ),
         ( Dimension ) null,
         "Close CroftSoft MultiApplet?" );
     }

     public static void  launch (
       String       appletInfo,
       Pair [ ]     appletPairs,
       String       newsName,
       String       newsHTML,
       String       newsPage,
       String       frameTitle,
       String       frameIconFilename,
       ClassLoader  frameIconClassLoader,
       Dimension    frameSize,
       String       shutdownConfirmationPrompt )
     //////////////////////////////////////////////////////////////////////
     {
       JFrame  jFrame = new JFrame ( frameTitle );

       try
       {
         // Using ImageLib.loadBufferedImage()
         // since ImageIO.read(URL) buggy

         Image  iconImage = ImageLib.loadBufferedImage (
           frameIconFilename, frameIconClassLoader );

         if ( iconImage != null )
         {
           jFrame.setIconImage ( iconImage );
         }
       }
       catch ( Exception  ex )
       {
       }

       MultiApplet  multiApplet = new MultiApplet (
         appletInfo, appletPairs, newsName, newsHTML, newsPage );

       jFrame.setContentPane ( multiApplet );

       FullScreenToggler.monitor ( jFrame );

       LifecycleWindowListener.launchFrameAsDesktopApp (
         jFrame,
         new Lifecycle [ ] { multiApplet },
         frameSize,
         shutdownConfirmationPrompt );
     }

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  MultiApplet (
       String    appletInfo,
       Pair [ ]  appletPairs,
       String    newsName,
       String    newsHTML,
       String    newsPage )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.appletInfo  = appletInfo  );

       NullArgumentException.check ( this.appletPairs = appletPairs );

       NullArgumentException.check ( this.newsName    = newsName    );

       this.newsHTML = newsHTML;

       this.newsPage = newsPage;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getAppletInfo ( )
     //////////////////////////////////////////////////////////////////////
     {
       return appletInfo;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       Container  contentPane = getContentPane ( );

       contentPane.setLayout ( new BorderLayout ( ) );

       jTabbedPane = new JTabbedPane (
         JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT );

       contentPane.add ( jTabbedPane, BorderLayout.CENTER );

       jTabbedPane.add (
         new MultiAppletNews ( newsHTML, newsPage, this ), newsName );

       for ( int  i = 0; i < appletPairs.length; i++ )
       {
         jTabbedPane.add ( new JPanel ( ), appletPairs [ i ].name );
       }

       jTabbedPane.addChangeListener (
         new ChangeListener ( )
         {
           public void  stateChanged ( ChangeEvent  changeEvent )
           {
             handleStateChange ( );
           }
         } );

       multiAppletStub = new MultiAppletStub ( this );
     }

     public void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       multiAppletStub.setActive ( true );

       try
       {
         if ( appletComponent instanceof Applet )
         {
           ( ( Applet ) appletComponent ).start ( );
         }
         else if ( appletComponent instanceof Lifecycle )
         {
           ( ( Lifecycle ) appletComponent ).start ( );
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }

       isStarted = true;
     }

     public void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       multiAppletStub.setActive ( false );

       try
       {
         if ( appletComponent instanceof Applet )
         {
           ( ( Applet ) appletComponent ).stop ( );
         }
         else if ( appletComponent instanceof Lifecycle )
         {
           ( ( Lifecycle ) appletComponent ).stop ( );
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }

       isStarted = false;
     }

     public synchronized void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         if ( appletComponent instanceof Applet )
         {
           ( ( Applet ) appletComponent ).destroy ( );
         }
         else if ( appletComponent instanceof Lifecycle )
         {
           ( ( Lifecycle ) appletComponent ).destroy ( );
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private void  handleStateChange ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( isStarted )
       {
         stop ( );
       }

       if ( index > 0 )
       {
         jTabbedPane.setComponentAt ( index, new JPanel ( ) );

         destroy ( );

         appletComponent = null;

         System.gc ( );
       }

       index = jTabbedPane.getSelectedIndex ( );

       if ( index > 0 )
       {
         try
         {
           appletComponent = ( Component ) Class.forName (
             appletPairs [ index - 1 ].value ).newInstance ( );

           if ( appletComponent instanceof Applet )
           {
             ( ( Applet ) appletComponent ).setStub ( multiAppletStub );
           }

           if ( appletComponent instanceof JComponent )
           {
             FullScreenToggler.monitor ( ( JComponent ) appletComponent );
           }

           jTabbedPane.setComponentAt ( index, appletComponent );

           try
           {
             if ( appletComponent instanceof Applet )
             {
               ( ( Applet ) appletComponent ).init ( );
             }
             else if ( appletComponent instanceof Lifecycle )
             {
               ( ( Lifecycle ) appletComponent ).init ( );
             }
           }
           catch ( Exception  ex )
           {
             ex.printStackTrace ( );
           }
           
           start ( );
         }
         catch ( Exception  ex )
         {
           ex.printStackTrace ( );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
