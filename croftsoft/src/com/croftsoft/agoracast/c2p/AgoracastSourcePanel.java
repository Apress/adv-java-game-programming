     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import java.util.*;
     import javax.swing.*;
     import javax.swing.table.*;

     import com.croftsoft.core.gui.ButtonPanel2;
     import com.croftsoft.core.gui.LabeledFieldsPanel2;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Pair;
     import com.croftsoft.core.lang.StringLib;
     import com.croftsoft.core.net.news.Newsgroup;
     import com.croftsoft.core.net.news.NntpConstants;
     import com.croftsoft.core.net.news.NntpSocket;
     import com.croftsoft.core.net.news.UsenetMessage;
     import com.croftsoft.core.text.DateFormatLib;
     import com.croftsoft.core.util.log.Log;

     /*********************************************************************
     *
     * <p />
     *
     * @version
     *   2001-08-13
     * @since
     *   2001-07-31
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastSourcePanel
       extends JPanel
       implements ActionListener, Log, Runnable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final AgoracastMediator     agoracastMediator;

     private final AgoracastBrowsePanel  agoracastBrowsePanel;

     private final JTextArea             jTextArea;

     private final JButton               cancelButton;

     //

     private AgoracastData  agoracastData;

     private NntpSocket     nntpSocket;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastSourcePanel (
       AgoracastMediator     agoracastMediator,
       AgoracastBrowsePanel  agoracastBrowsePanel )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ), true ); // isDoubleBuffered

       NullArgumentException.check (
         this.agoracastMediator = agoracastMediator );

       NullArgumentException.check (
         this.agoracastBrowsePanel = agoracastBrowsePanel );

       AgoracastLib.setColor ( this, agoracastMediator );

       add ( new JScrollPane (
         jTextArea = new JTextArea ( ) ), BorderLayout.CENTER );

       jTextArea.setFont ( AgoracastConstants.LOG_FONT );

       cancelButton = new JButton ( "Cancel" );

       cancelButton.addActionListener ( this );

       add ( cancelButton, BorderLayout.SOUTH );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  viewSource ( AgoracastData  agoracastData )
     //////////////////////////////////////////////////////////////////////
     {
       this.agoracastData = agoracastData;

       new Thread ( this ).start ( );
     }

     public synchronized void  run ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         String  articleId = agoracastData.getArticleId ( );

         jTextArea.setText (
           DateFormatLib.toIsoDateFormat ( new Date ( ) )
           + " Downloading article " + articleId + "..." );

         cancelButton.setText ( "Cancel" );

// should we get this from the agoracastData instead?

         String  server = agoracastMediator.getServer ( );

         String  newsgroupName = agoracastData.getNewsgroup ( );

         nntpSocket = new NntpSocket ( server, this );

         // 200 server ready - posting allowed
         // 201 server ready - no posting allowed

         if ( nntpSocket.getResponseCode ( ).startsWith ( "20" ) )
         {
           String  responseCode = nntpSocket.command (
             NntpConstants.COMMAND_GROUP + " " + newsgroupName );

           if ( responseCode.startsWith ( "480" ) )
           {
             // 480 Authentication Required

             AgoracastLib.authenticate ( nntpSocket, agoracastMediator );

             // throws SecurityException if it fails

             responseCode = nntpSocket.command (
               NntpConstants.COMMAND_GROUP + " " + newsgroupName );
           }

           // 211 n f l s group selected
           //   (n = estimated number of articles in group,
           //   f = first article number in the group,
           //   l = last article number in the group,
           //   s = name of the group.)
           // 411 no such news group

           if ( responseCode.startsWith ( "211" ) )
           {
             responseCode = nntpSocket.command (
               NntpConstants.COMMAND_ARTICLE + " " + articleId );

             // 220 n <a> article retrieved - head and body follow
             //    (n = article number, <a> = message-id)
             // 221 n <a> article retrieved - head follows
             // 222 n <a> article retrieved - body follows
             // 223 n <a> article retrieved - request text separately
             // 412 no newsgroup has been selected
             // 420 no current article has been selected
             // 423 no such article number in this group
             // 430 no such article found

             if ( responseCode.startsWith ( "220" ) )
             {
               BufferedReader  bufferedReader
                 = nntpSocket.getBufferedReader ( );

               String  line;

               while ( true )
               {
                 line = bufferedReader.readLine ( );

                 if ( ".".equals ( line )
                   || ( line == null ) )
                 {
                   break;
                 }

                 record ( line );
               }
             }
           }
         }

         nntpSocket.command ( NntpConstants.COMMAND_QUIT );
       }
       catch ( Exception  ex )
       {
         record ( ex );
       }
       finally
       {
         if ( nntpSocket != null )
         {
           try
           {
             nntpSocket.close ( );

           }
           catch ( Exception  ex )
           {
             record ( ex );
           }
         }

         cancelButton.setText ( "Done" );  
       }
     }

     public void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         Object  source = actionEvent.getSource ( );

         if ( source == cancelButton )
         {
           if ( nntpSocket != null )
           {
// Is this hanging?

             nntpSocket.close ( );
           }

           agoracastBrowsePanel.showTable ( );
         }
       }
       catch ( Exception  ex )
       {
         record ( ex );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

// cut-and-paste from AgoracastSendPanel and AgoracastDownloadPanel,
// make reusable, see LogPanel

     public synchronized void  record ( String  message )
     //////////////////////////////////////////////////////////////////////
     {
       jTextArea.append ( '\n' + message );

       String  text = jTextArea.getText ( );

       int  textLength = text.length ( );

       if ( textLength > AgoracastConstants.LOG_TEXT_LENGTH_MAX )
       {
         jTextArea.setText ( text.substring (
           textLength - AgoracastConstants.LOG_TEXT_LENGTH_MAX ) );
       }
     }

     public synchronized void  record ( Throwable  throwable )
     //////////////////////////////////////////////////////////////////////
     {
       record ( '\n' + throwable.toString ( ) );
     }


     public synchronized void  record (
       String  message, Throwable  throwable )
     //////////////////////////////////////////////////////////////////////
     {
       record ( '\n' + message + '\n' + throwable.toString ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }