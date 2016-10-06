     package com.croftsoft.core.gui.multi;

     import java.applet.*;
     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import java.net.*;
     import javax.swing.*;
     import javax.swing.event.*;
     import javax.swing.text.*;
     import javax.swing.text.html.*;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.jnlp.JnlpLib;

     /*********************************************************************
     * Downloads and displays the latest news web page.
     *
     * <p>
     * For a partial explanation of the source code, please see
     * Kim Topley, <i>Core Swing Advanced Programming</i>,
     * Chapter 4 "JEditorPane and the Swing HTML Package", 2000.
     * </p>
     *
     * @version
     *   2003-05-04
     * @since
     *   2002-02-27
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  MultiAppletNews
       extends JPanel
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  DEFAULT_NEWS_HTML
       = "<html><body><pre>"
       + CroftSoftConstants.DEFAULT_ATTRIBUTION_NOTICE
       + "</pre></body></html>";

     //

     private final AppletContext  appletContext;

     private final JEditorPane    jEditorPane;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *
     * @param  applet
     *
     *   May be null.
     *********************************************************************/
     public  MultiAppletNews (
       String  newsHTML,
       String  newsPage,
       Applet  applet )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ) );

       AppletContext  appletContext = null;

       try
       {
         appletContext = applet.getAppletContext ( );
       }
       catch ( Exception  ex )
       {
       }

       this.appletContext = appletContext;

       if ( newsHTML == null )
       {
         if ( newsPage != null )
         {
           newsHTML
             = "<html><body>"
             + "Loading "
             + newsPage
             + "..."
             + "</body></html>";
         }
         else
         {
           newsHTML = DEFAULT_NEWS_HTML;
         }
       }

       jEditorPane = new JEditorPane ( "text/html", newsHTML );

       jEditorPane.setEditable ( false );

       jEditorPane.setCaretPosition ( 0 );

       jEditorPane.addHyperlinkListener (
         new HyperlinkListener ( )
         {
           public void  hyperlinkUpdate ( HyperlinkEvent  hyperlinkEvent )
           {
             processHyperlinkEvent ( hyperlinkEvent );
           }
         } );

       add ( new JScrollPane ( jEditorPane ), BorderLayout.CENTER );

       if ( newsPage != null )
       {
         try
         {
           final URL  newsURL = new URL ( newsPage );

           new Thread (
             new Runnable ( )
             {
               public void  run ( )
               {
                 try
                 {
                   jEditorPane.setPage ( newsURL );
                 }
                 catch ( Exception  ex )
                 {
                   ex.printStackTrace ( );
                 }             
               }
             } ).start ( );
         }
         catch ( MalformedURLException  ex )
         {
           ex.printStackTrace ( );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private void  processHyperlinkEvent ( HyperlinkEvent  hyperlinkEvent )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         if ( hyperlinkEvent.getEventType ( )
           == HyperlinkEvent.EventType.ACTIVATED )
         {
           if ( hyperlinkEvent instanceof HTMLFrameHyperlinkEvent )
           {
             HTMLDocument  htmlDocument
               = ( HTMLDocument ) jEditorPane.getDocument ( );

             htmlDocument.processHTMLFrameHyperlinkEvent (
               ( HTMLFrameHyperlinkEvent ) hyperlinkEvent );
           }
           else
           {
             URL  url = hyperlinkEvent.getURL ( );

             if ( appletContext != null )
             {
               appletContext.showDocument ( url, "_blank" );
             }             
             else
             {
               try
               {
                 JnlpLib.showDocument ( url );
               }
               catch ( UnsupportedOperationException  ex )
               {
                 jEditorPane.setPage ( url );
               }
             }
           }
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }