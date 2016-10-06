     package com.croftsoft.core.net.http;

     import java.io.*;
     import java.net.*;
     import java.util.*;

     /*********************************************************************
     * <P>
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   2000-04-23
     *********************************************************************/

     public class  PrintServer implements Runnable {
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
System.out.println ( "PrintServer starting..." );

       String  root = "./";

       int     port = 80;

       new Thread ( new PrintServer ( root, port ) ).start ( );
     }

     public void  shutdown ( )
     //////////////////////////////////////////////////////////////////////
     {
       running = false;

       try { serverSocket.close ( ); } catch ( Exception  e ) { }
     }

     public  PrintServer ( String  root, int  port )
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

         int  index = 0;

         while ( running )
         {
           Socket  clientSocket = serverSocket.accept ( );

           InputStream  in = clientSocket.getInputStream ( );

           int  c;

           while ( ( c = in.read ( ) ) > -1 )
           {
             System.out.print ( ( char ) c );
           }

           OutputStream  out = clientSocket.getOutputStream ( );

System.out.println ( "writing..." );

           out.write ( "HTTP/1.0 202\r\n".getBytes ( "US-ASCII" ) );

System.out.println ( "flushing..." );

           out.flush ( );

System.out.println ( "closing..." );

           out.close ( );

System.out.println ( "closing socket..." );

           clientSocket.close ( );

           index++;
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
