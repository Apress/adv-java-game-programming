     package com.croftsoft.core.net.news;

     import java.io.*;
     import java.net.*;
     import java.util.*;

     import com.croftsoft.core.lang.Pair;
     import com.croftsoft.core.lang.StringLib;
     import com.croftsoft.core.security.Authentication;
     import com.croftsoft.core.security.Identifier;
     import com.croftsoft.core.security.PreIdentifier;
     import com.croftsoft.core.util.log.Log;
     import com.croftsoft.core.util.log.PrintStreamLog;

     /*********************************************************************
     * Static library methods for NNTP (RFC 977).
     *
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</A>
     * @version
     *   2001-08-03
     * @since
     *   2001-07-23
     *********************************************************************/

     public final class  NntpLib
       implements NntpConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Posts a test message to a test newsgroup.
     *********************************************************************/
     public static void  main ( String [ ]  args )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       String  nntpServer = args [ 0 ];

       String  username   = args [ 1 ];

       String  password   = args [ 2 ];

       UsenetMessage  usenetMessage = new UsenetMessage (
         args [ 3 ],          // from
         "alt.test",          // newsgroup
         "test",              // subject
         "This is a test." ); // body

       post (
         nntpServer,
         new PreIdentifier ( username, password ),
         usenetMessage,
         new PrintStreamLog ( System.out ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Sends username and password.
     *
     * Synchronizes on the NntpSocket.
     *********************************************************************/
     public static String  authenticate (
       NntpSocket  nntpSocket,
       String      username,
       String      password )
       throws IOException, SecurityException
     //////////////////////////////////////////////////////////////////////
     {
       synchronized ( nntpSocket )
       {

       String  responseCode
         = nntpSocket.command ( COMMAND_AUTHINFO + " USER " + username );

       if ( responseCode.startsWith ( "502" ) )
       {
         // 502 Authentication Failed

         throw new SecurityException ( responseCode );
       }

       // 381 More Authentication Required

       Log  log = nntpSocket.getLog ( );

       nntpSocket.setLog ( null );

       responseCode
         = nntpSocket.command ( COMMAND_AUTHINFO + " PASS " + password );

       nntpSocket.setLog ( log );

       if ( responseCode.startsWith ( "502" ) )
       {
         // 502 Authentication Failed

         throw new SecurityException ( responseCode );
       }

       // 281 Authentication Accepted

       return responseCode;

       }
     }

     /*********************************************************************
     * Replaces lines that start with "." with "..",
     * appends CR_LF to each line, then
     * adds a final line of "." + CR_LF.
     *********************************************************************/
     public static StringBuffer  encodeLines ( String [ ]  lines )
     //////////////////////////////////////////////////////////////////////
     {
       StringBuffer  stringBuffer = new StringBuffer ( );

       for ( int  i = 0; i < lines.length; i++ )
       {
         String  line = lines [ i ];

         if ( line.startsWith ( "." ) )
         {
           stringBuffer.append ( "." );
         }

         stringBuffer.append ( line );

         stringBuffer.append ( CR_LF );
       }

       stringBuffer.append ( "." + CR_LF );

       return stringBuffer;
     }

     /*********************************************************************
     * Encodes a message body in preparation for an NNTP POST transmission.
     *
     * <pre>
     * return encodeLines (
     *   StringLib.toStringArray ( messageBody ) ).toString ( );
     * </pre>
     *********************************************************************/
     public static String  encode ( String  messageBody )
     //////////////////////////////////////////////////////////////////////
     {
       return encodeLines (
         StringLib.toStringArray ( messageBody ) ).toString ( );
     }

     /*********************************************************************
     * Replaces lines that start with ".." with ".".
     *********************************************************************/
     public static void  decodeLines ( String [ ]  lines )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < lines.length; i++ )
       {
         lines [ i ] = decodeLine ( lines [ i ] );
       }
     }

     /*********************************************************************
     * Replaces starting ".." with ".".
     *********************************************************************/
     public static String  decodeLine ( String  line )
     //////////////////////////////////////////////////////////////////////
     {
       if ( line.startsWith ( ".." ) )
       {
         return line.substring ( 1 );
       }

       return new String ( line );
     }

     public static String  post (
       String         nntpServer,
       Identifier     identifier,
       UsenetMessage  usenetMessage,
       Log            log )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       String  responseCode = null;

       NntpSocket  nntpSocket = null;

       try
       {
         nntpSocket = new NntpSocket ( nntpServer, log );

         nntpSocket.setSoLinger ( true, 30 ); // timeout after 30 seconds

         responseCode = nntpSocket.getResponseCode ( );

         if ( !nntpSocket.getPostingAllowed ( ) )
         {
           throw new IOException ( responseCode );
         }

         responseCode = nntpSocket.command ( NntpConstants.COMMAND_POST );

         if ( responseCode.startsWith ( "480" )
           && ( identifier != null ) )
         {
           // 480 Authentication Required

           Authentication  authentication
             = identifier.getAuthentication ( );

           if ( authentication != null )
           {
             responseCode = authenticate (
               nntpSocket,
               authentication.getUsername ( ),
               authentication.getPassword ( ) );

             responseCode
               = nntpSocket.command ( NntpConstants.COMMAND_POST );
           }
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

       return responseCode;
     }

/*
     public static String  post (
       String      nntpServer,
       Identifier  identifier,
       Pair [ ]    messageHeaderPairs,
       String      messageBody,
       Log         log ) 
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       UsenetMessage  usenetMessage = new UsenetMessage ( );

       for ( int  i = 0; i < messageHeaderPairs.length; i++ )
       {
         Pair  headerPair = messageHeaderPairs [ i ];

         usenetMessage.setHeader ( headerPair.name, headerPair.value );
       }

       usenetMessage.setBody ( messageBody );

       return NntpLib.post (
         nntpServer, username, password, usenetMessage, log );
     }
*/

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  NntpLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
