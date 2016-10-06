     package com.croftsoft.core.net.http;

     import java.io.*;
     import java.net.*;

     import com.croftsoft.core.io.Parser;
     import com.croftsoft.core.io.StringCoder;

     /*********************************************************************
     * HTTP operations.
     *
     * @version
     *   2003-06-05
     * @since
     *   1999-12-19
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  HttpLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  APPLICATION_OCTET_STREAM
       = "application/octet-stream";

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println (
         post (
           new URL ( args [ 0 ] ),
           "get".getBytes ( StringCoder.US_ASCII ),
           "HttpLib/1.0",
           "text/plain",
           new StringCoder  ( StringCoder.US_ASCII ) ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static Object  post (
       URL       url,
       byte [ ]  bytes,
       String    userAgent,
       String    contentType,
       Parser    parser )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       HttpURLConnection  httpURLConnection
         = ( HttpURLConnection ) url.openConnection ( );

       httpURLConnection.setRequestMethod ( "POST" );

       if ( userAgent != null )
       {
         httpURLConnection.setRequestProperty ( "User-Agent", userAgent );
       }

       if ( contentType != null )
       {
         httpURLConnection.setRequestProperty (
           "Content-Type", contentType );
       }

       httpURLConnection.setRequestProperty (
         "Content-Length", Integer.toString ( bytes.length ) );

       httpURLConnection.setDoOutput ( true );

       OutputStream  outputStream = httpURLConnection.getOutputStream ( );

       BufferedOutputStream  bufferedOutputStream
         = new BufferedOutputStream ( outputStream );

       bufferedOutputStream.write ( bytes );

       bufferedOutputStream.close ( );

       if ( parser != null )
       {
         if ( httpURLConnection.getResponseCode ( )
           == HttpURLConnection.HTTP_OK )
         {
           int  contentLength = httpURLConnection.getContentLength ( );

           InputStream  inputStream = httpURLConnection.getInputStream ( );

           BufferedInputStream  bufferedInputStream
             = new BufferedInputStream ( inputStream );

           Object  object
             = parser.parse ( bufferedInputStream, contentLength );

           bufferedInputStream.close ( );

           return object;
         }
       }

       return null;
     }

     public static int  post (
       String    host,
       String    path,
       byte [ ]  bytes )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return post ( host, 80, path, "HttpLib/1.00",
         "application/x-www-form-urlencoded", bytes );
     }

     public static int  post (
       String    host,
       int       port,
       String    path,
       String    userAgent,
       byte [ ]  bytes )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return post ( host, port, path, userAgent,
         "application/x-www-form-urlencoded", bytes );
     }

     public static int  post (
       String    host,
       int       port,
       String    path,
       String    userAgent,
       String    contentType,
       byte [ ]  bytes )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       HttpConnection  httpConnection = new HttpConnection (
         host, port, path,userAgent, contentType, bytes, null );

       httpConnection.post ( );

       return httpConnection.getResponseCode ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  HttpLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
