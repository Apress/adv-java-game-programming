     package com.croftsoft.core.net.http;

     import java.io.*;
     import java.net.*;
     import java.util.*;

     import com.croftsoft.core.io.Parser;

     /*********************************************************************
     * Somewhat of a replacement for HttpURLConnection.
     *
     * <p />
     *
     * @version
     *   2003-05-19
     * @since
     *   2000-04-27
     * @author
     *   <a href="http://www.alumni.caltech.edu/~croft/">David W. Croft</a>
     *********************************************************************/

     public final class  HttpConnection
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final int  HTTP_ACCEPTED   = 202;

     public static final int  HTTP_NO_CONTENT = 204;

     private final String     host;

     private final int        port;

     private final String     path;

     private final String     userAgent;

     private final String     contentType;

     private       byte [ ]   requestBytes;

     private final Parser     contentParser;

// need to modify so that Headers used during outgoing posts;
// don't forget to clear!

     private final Hashtable  headerHashtable;

     private       int        responseCode;

     private       Object     parsedContent;

     private       Socket     socket;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  HttpConnection (
       String    host,
       int       port,
       String    path,
       String    userAgent,
       String    contentType,
       byte [ ]  requestBytes,
       Parser    contentParser )
     //////////////////////////////////////////////////////////////////////
     {
       if ( host == null )
       {
         throw new IllegalArgumentException ( "null host" );
       }

       if ( port == -1 )
       {
         port = 80;
       }

       if ( path == null )
       {
         throw new IllegalArgumentException ( "null path" );
       }

       if ( userAgent == null )
       {
         throw new IllegalArgumentException ( "null userAgent" );
       }

       if ( contentType == null )
       {
         throw new IllegalArgumentException ( "null contentType" );
       }

       this.host          = host;

       this.port          = port;

       this.path          = path;

       this.userAgent     = userAgent;

       this.contentType   = contentType;

       this.requestBytes  = requestBytes;

       this.contentParser = contentParser;

       headerHashtable    = new Hashtable ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  post ( )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       // Save a reference to requestBytes in case it gets changed.

       byte [ ]  requestBytes = this.requestBytes;

       OutputStream  out    = null;

       InputStream   in     = null;

       try
       {
         socket = new Socket ( host, port );

         out = socket.getOutputStream ( );

         StringBuffer  stringBuffer = new StringBuffer ( );

         stringBuffer.append ( "POST " + path + " HTTP/1.0\r\n" );

         stringBuffer.append ( "Connection: Keep-Alive\r\n" );

         stringBuffer.append ( "User-Agent: " + userAgent + "\r\n" );

// What was that other one?  Webmaster?

         stringBuffer.append ( "Content-type: " + contentType + "\r\n" );

         if ( requestBytes != null )
         {
           stringBuffer.append (
             "Content-length: " + requestBytes.length + "\r\n" );
         }

         stringBuffer.append ( "\r\n" );

// US-ASCII was here
         out.write ( stringBuffer.toString ( ).getBytes ( ) );

         if ( requestBytes != null )
         {
           out.write ( requestBytes );
         }

         out.flush ( );

         in = socket.getInputStream ( );

         parseResponse ( in );
       }
       finally
       {
         try
         {
           if ( in != null )
           {
             in.close ( );
           }
         }
         catch ( Exception  ex )
         {
           ex.printStackTrace ( );
         }

         try
         {
           if ( out != null )
           {
             out.close ( );
           }
         }
         catch ( Exception  ex )
         {
           ex.printStackTrace ( );
         }

// keep-alive?

         try
         {
           if ( socket != null )
           {
             socket.close ( );
           }
         }
         catch ( Exception  ex )
         {
           ex.printStackTrace ( );
         }
       }
     }

     public void  abort ( )
     //////////////////////////////////////////////////////////////////////
     {
//System.out.println ( "HttpConnection.abort()" );

       try
       {
         if ( socket != null )
         {
           socket.close ( );
         }
       }
       catch ( Exception  ex )
       {
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int  getResponseCode ( ) { return responseCode; }

     public Object  getParsedContent ( ) { return parsedContent; }

     public Hashtable  getHeaderHashtable ( ) { return headerHashtable; }

     public void  setRequestBytes ( byte [ ]  requestBytes )
     //////////////////////////////////////////////////////////////////////
     {
       this.requestBytes = requestBytes;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private void  parseResponse ( InputStream  inputStream )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       headerHashtable.clear ( );

       if ( inputStream == null )
       {
         responseCode = -1;

         return;
       }

       byte [ ]  statusBytes = new byte [ 12 ];

       int  bytesRead = inputStream.read ( statusBytes );

       if ( bytesRead < 12 )
       {
         responseCode = -1;

         return;
       }

// US-ASCII was here
       String  statusString = new String ( statusBytes );

// weak
       if ( !statusString.startsWith ( "HTTP/1." ) )
       {
         responseCode = -1;

         return;
       }

       try
       {
         responseCode = Integer.parseInt ( statusString.substring ( 9 ) );
       }
       catch ( NumberFormatException  ex )
       {
         responseCode = -1;

         return;
       }

       readLine ( inputStream );

       String  line;

       while ( ( line = readLine ( inputStream ) ) != null )
       {
         if ( line.equals ( "" ) )
         {
           break;
         }

         int  index = line.indexOf ( ":" );

         if ( index < 1 )
         {
           responseCode = -1;

           return;
         }

         String  headerName = line.substring ( 0, index );

         if ( line.length ( ) > index + 2 )
         {
           String  headerValue = line.substring ( index + 2 ).trim ( );

//System.out.println ( "HttpConnection:  (\"" + headerName + "\",\""
//  + headerValue + "\")" );

           headerHashtable.put ( headerName, headerValue );
         }
         else
         {
           responseCode = -1;

           return;
         }
       }

       if ( line == null )
       {
         return;
       }

       if ( contentParser == null )
       {
         return;
       }

       if ( ( responseCode == HTTP_ACCEPTED   )
         || ( responseCode == HTTP_NO_CONTENT ) )
       {
         return;
       }

       String  contentLengthStr
         = ( String ) headerHashtable.get ( "Content-Length" );

       int  contentLength;

       if ( contentLengthStr == null )
       {
         contentLength = -1;
       }
       else
       {
         try
         {
           contentLength = Integer.parseInt ( contentLengthStr );
         }
         catch ( NumberFormatException  ex )
         {
           responseCode = -1;

           return;
         }
       }

       if ( contentLength == 0 )
       {
         return;
       }

       parsedContent = contentParser.parse ( inputStream, contentLength );
     }

// don't want to buffer but do want to have readLine capability;
// DataInputStream.readLine deprecated, BufferedReader buffers

// only works with "\r\n"; note:  no pushback

     private static String  readLine ( InputStream  inputStream )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       // looking for "\r\n"

       ByteArrayOutputStream  byteArrayOutputStream
         = new ByteArrayOutputStream ( );

       int  state = 0;

       while ( true )
       {
         int  c = inputStream.read ( );

         if ( c < 0 )
         {
// encoding?

           String  s = byteArrayOutputStream.toString ( );

           if ( s.length ( ) < 1 )
           {
             return null;
           }

           return s;
         }

         if ( c == '\r' )
         {
           if ( state == 0 )
           {
             state = 1;
           }
           else
           {
             state = 0;
           }
         }
         else if ( c == '\n' )
         {
           if ( state == 1 )
           {
// encoding?

             String  s = byteArrayOutputStream.toString ( );

             // trim off the "\r"

             return s.substring ( 0, s.length ( ) - 1 );
           }
           else
           {
             state = 0;
           }
         }
         else
         {
           state = 0;
         }

         byteArrayOutputStream.write ( c );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
