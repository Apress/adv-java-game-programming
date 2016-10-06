     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import java.net.*;
     import javax.swing.*;
     import javax.swing.event.*;
     import javax.swing.text.*;
     import javax.swing.text.html.*;

     import com.croftsoft.core.gui.GuiCreator;
     import com.croftsoft.core.gui.LogPanel;
     import com.croftsoft.core.gui.PairsPanel;
     import com.croftsoft.core.jnlp.JnlpProxy;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.ObjectLib;
     import com.croftsoft.core.lang.Pair;
     import com.croftsoft.core.util.pubsub.Subscriber;

     /*********************************************************************
     * Main Agoracast GUI panel.
     *
     * <p />
     *
     * @version
     *   2002-02-03
     * @since
     *   2001-07-06
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastPanel
       extends JPanel
       implements AgoracastConstants, HyperlinkListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final AgoracastMediator       agoracastMediator;

     private final PairsPanel              configPairsPanel;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastPanel (
       AgoracastMediator  agoracastMediator,
       String             documentationText,
       Frame              parentFrame )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ), true ); // isDoubleBuffered

       NullArgumentException.check ( 
         this.agoracastMediator = agoracastMediator );

       AgoracastLib.setColor ( this, agoracastMediator );

       Color  panelBackgroundColor
         = agoracastMediator.getPanelBackgroundColor ( );

       Color  textFieldBackgroundColor
         = agoracastMediator.getTextFieldBackgroundColor ( );

/*
       JMenuBar  jMenuBar = new JMenuBar ( );

       jMenuBar.add ( new JMenu ( "About" ) );

       add ( jMenuBar, BorderLayout.NORTH );
*/

       JTabbedPane  jTabbedPane = new JTabbedPane ( );

       configPairsPanel = new PairsPanel (
         createConfigPairs ( ),
         AgoracastConstants.CONFIG_HELP_TEXT,
         new ChangeListener ( )
         {
           public void  stateChanged ( ChangeEvent  changeEvent )
           {
             handleConfigUpdate ( );
           }
         },
         true,
         panelBackgroundColor,
         textFieldBackgroundColor );

       final JEditorPane  jEditorPane
         = GuiCreator.createHtmlPane ( documentationText, this );

       new Thread (
         new Runnable ( )
         {
           public void  run ( )
           {
             try
             {
               URL  documentationURL = new URL ( DOCUMENTATION_URL );

               jEditorPane.setPage ( documentationURL );
             }
             catch ( Exception  ex )
             {
               ex.printStackTrace ( );
             }             
           }
         } ).start ( );

       jTabbedPane.addTab (
         "Documentation", new JScrollPane ( jEditorPane ) );

       jTabbedPane.addTab ( "Configuration", configPairsPanel );

       jTabbedPane.addTab ( "Browse",
         new AgoracastBrowsePanel ( agoracastMediator ) );

       jTabbedPane.addTab ( "Defaults",
         new AgoracastDefaultsPanel ( agoracastMediator ) );

       jTabbedPane.addTab ( "Post",
         new AgoracastPostPanel ( agoracastMediator ) );

       AgoracastLogPanel  agoracastLogPanel = new AgoracastLogPanel (
         agoracastMediator, LOG_TEXT_LENGTH_MAX, panelBackgroundColor );

       jTabbedPane.addTab ( "Log", new JScrollPane ( agoracastLogPanel ) );

       add ( jTabbedPane, BorderLayout.CENTER );

       agoracastMediator.setJTabbedPane ( jTabbedPane );

       agoracastMediator.setTabEnabled ( TAB_INDEX_LOG, false );

       agoracastMediator.setLog ( agoracastLogPanel );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  hyperlinkUpdate ( HyperlinkEvent  e )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         if ( e.getEventType ( ) == HyperlinkEvent.EventType.ACTIVATED )
         {
           JEditorPane  jEditorPane = ( JEditorPane ) e.getSource ( );

           if ( e instanceof HTMLFrameHyperlinkEvent )
           {
             HTMLDocument  htmlDocument
               = ( HTMLDocument ) jEditorPane.getDocument ( );

             htmlDocument.processHTMLFrameHyperlinkEvent (
               ( HTMLFrameHyperlinkEvent ) e );
           }
           else
           {
             URL  url = e.getURL ( );

             if ( !JnlpProxy.showDocument ( url ) )
             {
               System.out.println ( url );
             }
           }
         }
       }
       catch ( Exception  ex )
       {
         agoracastMediator.getLog ( ).record ( ex );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  handleConfigUpdate ( )
     //////////////////////////////////////////////////////////////////////
     {
       String  email     = configPairsPanel.getText ( CONFIG_EMAIL     );

       String  server    = configPairsPanel.getText ( CONFIG_SERVER    );

       String  username  = configPairsPanel.getText ( CONFIG_USERNAME  );

       String  newsgroup = configPairsPanel.getText ( CONFIG_NEWSGROUP );

       agoracastMediator.setEmail     ( email     );

       agoracastMediator.setServer    ( server    );

       agoracastMediator.setUsername  ( username  );

       agoracastMediator.setNewsgroup ( newsgroup );

       configPairsPanel.setText ( createConfigPairs ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private Pair [ ]  createConfigPairs ( )
     //////////////////////////////////////////////////////////////////////
     {
       return new Pair [ ]
       {
         new Pair ( CONFIG_SERVER   , agoracastMediator.getServer    ( ) ),
         new Pair ( CONFIG_EMAIL    , agoracastMediator.getEmail     ( ) ),
         new Pair ( CONFIG_USERNAME , agoracastMediator.getUsername  ( ) ),
         new Pair ( CONFIG_NEWSGROUP, agoracastMediator.getNewsgroup ( ) )
       };
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }