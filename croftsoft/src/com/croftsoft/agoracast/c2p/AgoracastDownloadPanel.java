     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import java.util.*;
     import javax.swing.*;

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
     *   2001-09-12
     * @since
     *   2001-07-31
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastDownloadPanel
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

     private NntpSocket  nntpSocket;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastDownloadPanel (
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

     public synchronized void  download ( )
     //////////////////////////////////////////////////////////////////////
     {
       new Thread ( this ).start ( );
     }

     public synchronized void  run ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         jTextArea.setText (
           DateFormatLib.toIsoDateFormat ( new Date ( ) )
           + " Downloading messages..." );

         cancelButton.setText ( "Cancel" );

         String  server = agoracastMediator.getServer ( );

         String  newsgroupName = agoracastMediator.getNewsgroup ( );

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
             Newsgroup  newsgroup = Newsgroup.parse ( responseCode );

             AgoracastDatabase  agoracastDatabase
               = agoracastMediator.getAgoracastDatabase ( );

             AgoracastNewsrc  agoracastNewsrc
               = agoracastMediator.getAgoracastNewsrc ( );

             long  lastRead = agoracastNewsrc.getLastArticleNumber (
               server, newsgroupName );

             long  newsgroupLastArticle = newsgroup.getLastArticle ( );

             if ( lastRead > newsgroupLastArticle )
             {
               lastRead = -1;
             }

             if ( newsgroupLastArticle
               > lastRead + AgoracastConstants.DOWNLOAD_MAX )
             {
               lastRead
                 = newsgroupLastArticle - AgoracastConstants.DOWNLOAD_MAX;
             }

             responseCode = nntpSocket.command ( NntpConstants.COMMAND_HEAD
               + ( lastRead > -1 ? " " + ( lastRead + 1 ) : "" ) );

             boolean  hasNext = true;

             while ( hasNext )
             {
               // 221 n <a> article retrieved - head follows

               if ( responseCode.startsWith ( "221 " ) )
               {

System.out.println ( "response:  " + responseCode );

                 StringTokenizer  stringTokenizer
                   = new StringTokenizer ( responseCode, " " );

                 stringTokenizer.nextToken ( );

                 long  articleNumber
                   = Long.parseLong ( stringTokenizer.nextToken ( ) );

                 String  articleId = stringTokenizer.nextToken ( );

                 BufferedReader  bufferedReader
                   = nntpSocket.getBufferedReader ( );

                 UsenetMessage  usenetMessage
                   = UsenetMessage.parse ( bufferedReader );

                 String  subject
                   = usenetMessage.getHeader ( UsenetMessage.HEADER_SUBJECT );

                 subject = StringLib.trimToNull ( subject );

                 if ( subject != null
                   && subject.toLowerCase ( ).startsWith (
                     AgoracastConstants.SUBJECT_PREFIX_LOWER_CASE ) )
                 {
                   responseCode
                     = nntpSocket.command ( NntpConstants.COMMAND_BODY );

                   // 222 n <a> article retrieved - body follows

                   if ( responseCode.startsWith ( "222" ) )
                   {
                     Pair [ ]  pairs = parseBody (
                       UsenetMessage.parseBody ( bufferedReader ) );

                     if ( ( pairs != null )
                       && ( pairs.length > 0 ) )
                     {
                       agoracastDatabase.add ( new AgoracastData (
                         newsgroupName, articleId, pairs ) );
                     }
                   }
                 }

                 agoracastNewsrc.setLastArticleNumber (
                   server, newsgroupName, articleNumber );
               }

               responseCode
                 = nntpSocket.command ( NntpConstants.COMMAND_NEXT );

               // 223 n a article retrieved - request text separately
               //     (n = article number, a = unique article id)
               // 412 no newsgroup selected
               // 420 no current article has been selected
               // 421 no next article in this group
           
               if ( !responseCode.startsWith ( "223" ) )
               {
                 hasNext = false;
               }
               else
               {
                 responseCode
                   = nntpSocket.command ( NntpConstants.COMMAND_HEAD );
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

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

// cut-and-paste from AgoracastSendPanel, make reusable, see LogPanel

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

     private static final Pair [ ]  parseBody ( String  body )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( body );

       java.util.List  pairList = new ArrayList ( );

       String [ ]  lines = StringLib.toStringArray ( body );

       for ( int  i = 0; i < lines.length; i++ )
       {
         Pair  pair = parseLine ( lines [ i ] );

         if ( pair == null )
         {
           break;
         }

         pairList.add ( pair );
       }

       return ( Pair [ ] ) pairList.toArray ( new Pair [ ] { } );
     }

     private static final Pair  parseLine ( String  line )
     //////////////////////////////////////////////////////////////////////
     {
       int  index = line.indexOf ( ':' );

       if ( index < 0 )
       {
         return null;
       }

       String  name
         = StringLib.trimToNull ( line.substring ( 0, index  ) );

       String  value
         = StringLib.trimToNull ( line.substring ( index + 1 ) );

       if ( ( name  == null )
         || ( value == null ) )
       {
         return null;
       }

// the following could be much more efficient

       while ( name.endsWith ( "." ) )
       {
         name = name.substring ( 0, name.length ( ) - 1 ).trim ( );
       }

       if ( "".equals ( name ) )
       {
         return null;
       }

       return new Pair ( name, value );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }