     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import java.util.*;
     import javax.swing.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.net.news.NntpConstants;
     import com.croftsoft.core.net.news.NntpLib;
     import com.croftsoft.core.net.news.NntpSocket;
     import com.croftsoft.core.net.news.UsenetMessage;
     import com.croftsoft.core.text.DateFormatLib;
     import com.croftsoft.core.util.log.Log;

     /*********************************************************************
     *
     * <p />
     *
     * @version
     *   2001-08-02
     * @since
     *   2001-07-30
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastSendPanel
       extends JPanel
       implements ActionListener, Log, Runnable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final AgoracastMediator    agoracastMediator;

     private final AgoracastPostPanel   agoracastPostPanel;

     private final JTextArea            jTextArea;

     private final JButton              cancelButton;

     //

     private UsenetMessage   usenetMessage;

     private NntpSocket      nntpSocket;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastSendPanel (
       AgoracastMediator   agoracastMediator,
       AgoracastPostPanel  agoracastPostPanel )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ), true ); // isDoubleBuffered

       NullArgumentException.check (
         this.agoracastMediator = agoracastMediator );

       NullArgumentException.check (
         this.agoracastPostPanel = agoracastPostPanel );

       AgoracastLib.setColor ( this, agoracastMediator );

       add ( new JScrollPane ( jTextArea = new JTextArea ( ) ),
         BorderLayout.CENTER );

       jTextArea.setEditable ( false );

       jTextArea.setFont ( AgoracastConstants.LOG_FONT );

       cancelButton = new JButton ( "Cancel" );

       cancelButton.addActionListener ( this );

       add ( cancelButton, BorderLayout.SOUTH );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  send ( UsenetMessage  usenetMessage )
     //////////////////////////////////////////////////////////////////////
     {
       this.usenetMessage = usenetMessage;

       new Thread ( this ).start ( );
     }

     public synchronized void  run ( )
     //////////////////////////////////////////////////////////////////////
     {
       jTextArea.setText (
         DateFormatLib.toIsoDateFormat ( new Date ( ) )
         + " Sending message..." );

       cancelButton.setText ( "Cancel" );

       try
       {
         post ( );
       }
       catch ( Throwable  throwable )
       {
         record ( throwable );
       }

       cancelButton.setText ( "Done" );  
     }

     public void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       if ( nntpSocket != null )
       {
         try
         {
           nntpSocket.close ( );
         }
         catch ( IOException  ex )
         {
         }
       }

       agoracastPostPanel.cancel ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  record ( String  message )
     //////////////////////////////////////////////////////////////////////
     {
       jTextArea.append ( '\n' + message );

       trim ( );     
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

     private synchronized void  trim ( )
     //////////////////////////////////////////////////////////////////////
     {
       String  text = jTextArea.getText ( );

       int  textLength = text.length ( );

       if ( textLength > AgoracastConstants.LOG_TEXT_LENGTH_MAX )
       {
         jTextArea.setText ( text.substring (
           textLength - AgoracastConstants.LOG_TEXT_LENGTH_MAX ) );
       }
     }

     private synchronized void  post ( )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       String  nntpServer = agoracastMediator.getServer ( );

       String  responseCode = null;

       try
       {
         nntpSocket = new NntpSocket ( nntpServer, this );

         responseCode = nntpSocket.getResponseCode ( );

         if ( !nntpSocket.getPostingAllowed ( ) )
         {
           throw new IOException ( responseCode );
         }

         responseCode = nntpSocket.command ( NntpConstants.COMMAND_POST );

         if ( responseCode.startsWith ( "480" ) )
         {
           // 480 Authentication Required

           AgoracastLib.authenticate ( nntpSocket, agoracastMediator );

           // throws SecurityException if it fails

           responseCode = nntpSocket.command ( NntpConstants.COMMAND_POST );
         }

         if ( !responseCode.startsWith ( "340" ) )
         {
           throw new IOException ( responseCode );
         }

         responseCode = nntpSocket.command ( usenetMessage.toString ( ) );

         if ( !responseCode.startsWith ( "240" ) )
         {
           throw new IOException ( responseCode );
         }

         responseCode = nntpSocket.command_QUIT ( );
       }
       finally
       {
         if ( nntpSocket != null )
         {
           nntpSocket.close ( );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }