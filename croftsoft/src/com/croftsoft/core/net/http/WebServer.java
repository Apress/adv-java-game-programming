     package com.croftsoft.core.net.http;

     import java.io.*;
     import java.net.*;
     import java.util.*;

     /*********************************************************************
     * Nasty quick hack of a web server.
     *
     * <P>
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1999-12-24
     *********************************************************************/

     public class  WebServer implements Runnable {
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private static final String  CRLF   = "\r\n";

     private static final String  SERVER = "FileServer/1.0";


     private final String        root;

     private final int           port;

     private       boolean       running;

     private       ServerSocket  serverSocket;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       String  root = "./";

       int     port = 80;

       new Thread ( new WebServer ( root, port ) ).start ( );
     }

     public void  shutdown ( )
     //////////////////////////////////////////////////////////////////////
     {
       running = false;

       try { serverSocket.close ( ); } catch ( Exception  e ) { }
     }

     public  WebServer ( String  root, int  port )
     //////////////////////////////////////////////////////////////////////
     {
       this.root = root;

       this.port = port;
     }

     public void  run ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         serverSocket = new ServerSocket ( port );

         running = true;

         while ( running )
         {
           Socket  clientSocket = serverSocket.accept ( );

           System.out.println ( clientSocket.getInetAddress ( ) );

           BufferedReader  in = new BufferedReader (
             new InputStreamReader ( clientSocket.getInputStream ( ) ) );

           String  line = in.readLine ( );

           StringTokenizer  tokenizer = new StringTokenizer ( line );

           tokenizer.nextToken ( );

           String  filename = tokenizer.nextToken ( );

           System.out.println ( filename );

           while ( ( line = in.readLine ( ) ) != null )
             System.out.println ( line );

System.out.println ( "Done reading" );

           PrintWriter  out = new PrintWriter (
             new BufferedWriter ( new OutputStreamWriter (
             clientSocket.getOutputStream ( ) ) ) );

           File  file = new File ( root + filename );

           if ( !file.exists ( ) )
           {
System.out.println ( "File does not exist." );
             out.print ( "HTTP/1.0 404 Not Found" + CRLF );
             out.print ( "Date: Mon, 09 Mar 1998 01:13:44 GMT" + CRLF );
             out.print ( "Server: " + SERVER + CRLF );
             out.print ( "Content-type: text/html" + CRLF );
             out.print ( CRLF );
             out.println ( "<HEAD><TITLE>404 Not Found</TITLE></HEAD>" );
             out.println ( "<BODY><H1>404 Not Found</H1>" );
             out.println ( "File \"" + filename + "\" was not found.<P>" );
             out.println ( "</BODY></HTML>" );
           }
           else
           {
             out.print ( "HTTP/1.0 200 Document follows" + CRLF );
             out.print ( "Date: Mon, 09 Mar 1998 00:42:07 GMT" + CRLF );
             out.print ( "Server: " + SERVER + CRLF );
             out.print ( "Content-type: text/plain" + CRLF );
             out.print ( "Last-modified: Sun, 08 Mar 1998 23:41:34 GMT" + CRLF );
// file.lastModified ( )
             out.print ( "Content-length: " + file.length ( ) + CRLF );
             out.print ( CRLF );

             BufferedInputStream  in_file
               = new BufferedInputStream ( new FileInputStream ( file ) );

             int  c;

             while ( ( c = in_file.read ( ) ) != -1 )
             {
               out.write ( c );
             }

             in_file.close ( );
           }

           out.flush ( );

           clientSocket.close ( );
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }

       try
       {
         serverSocket.close ( );
       }
       catch ( Exception  e )
       {
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
