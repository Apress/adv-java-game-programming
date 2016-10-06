     package com.croftsoft.core.net;

     import java.io.*;
     import java.net.*;
     import java.util.*;

// Need to drop thread priority

     /*********************************************************************
     * PortServer creates a java.net.ServerSocket which accepts connections
     * and passes them on to com.orbs.net.SocketServer threads.
     * <P>
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1997-04-19
     *********************************************************************/

     public final class  PortServer implements Runnable {
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private static final String  COPYRIGHT
       = "PortServer (C) Copyright 1997 David Wallace Croft";

     private static final int  DEFAULT_PORT = 1234;

     private static final String  HELP
       = "java com.orbs.net.PortServer <classname> <port> <filename>\n"
       + "\n"
       + "<classname> must designate a class that implements\n"
       + "com.orbs.net.SocketServer.\n"
       + "\n"
       + "If <port> is not specified, the default value of\n"
       + DEFAULT_PORT + " will be used.\n"
       + "\n"
       + "If <shutdown_semaphore_filename> is specified and the file\n"
       + "does not exist, the program will not start.  If the file is\n"
       + "deleted, shutdown will occur immediately after the next\n"
       + "connection.\n";

     private Class         serverClass;
     private int           port;
     private File          semaphore;

     private boolean       shutdown = false;
     private ServerSocket  serverSocket;
     private Thread        thread;
     private long          nextUniqueID = 0;

     private Vector        thread_Vector = new Vector ( );
     private Vector        socket_Vector = new Vector ( );
     private Vector        server_Vector = new Vector ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     *********************************************************************/
     public static final void  main ( String [ ]  args ) {
     //////////////////////////////////////////////////////////////////////
       System.out.println ( COPYRIGHT );
       try {
         Class  serverClass = Class.forName ( args [ 0 ] );
         int  port = DEFAULT_PORT;
         if ( args.length > 1 ) port = Integer.parseInt ( args [ 1 ] );
         File  semaphore = null;
         if ( args.length > 2 ) semaphore = new File ( args [ 2 ] );
         new PortServer ( serverClass, port, semaphore );
       } catch ( Throwable  t ) {
         t.printStackTrace ( );
         System.out.println ( HELP );
         System.out.println ( t.getMessage ( ) );
         return;
       }
     }

     public  PortServer (
       Class  serverClass, int  port, File  semaphore ) {
     //////////////////////////////////////////////////////////////////////
       boolean  found = false;
       Class [ ]  interfaces = serverClass.getInterfaces ( );
       for ( int  i = 0; i < interfaces.length; i++ ) {
         if ( interfaces [ i ].getName ( ).equals (
           "com.croftsoft.core.net.SocketServer" ) ) {
           found = true;
           break;
         }
       }
       if ( !found ) {
         throw new RuntimeException (
           "Class that implements com.orbs.net.SocketServer required." );
       }
       if ( ( port < 0 ) || ( port > 65535 ) ) {
         throw new RuntimeException (
           "Port number must be between 0 and 65535 inclusive." );
       }
       if ( ( semaphore != null ) && !semaphore.exists ( ) ) {
         throw new RuntimeException (
           "Semaphore file must be exist upon startup." );
       }

       this.serverClass = serverClass;
       this.port        = port;
       this.semaphore   = semaphore;

       ( thread = new Thread ( this ) ).start ( );
     }

     public final void  run ( ) {
     //////////////////////////////////////////////////////////////////////
       try {
         serverSocket = new ServerSocket ( port );
         while ( !shutdown ) {
           if ( ( semaphore != null ) && !semaphore.exists ( ) ) {
             throw new Exception ( "Semaphore file does not exist." );
           }
           synchronized ( thread_Vector ) {
             Enumeration  e = thread_Vector.elements ( );
             while ( e.hasMoreElements ( ) ) {
               Thread  serverThread = ( Thread ) e.nextElement ( );
               if ( !serverThread.isAlive ( ) ) {
                 terminate ( serverThread );
               }
             }
           }
           Socket  socket = serverSocket.accept ( );
           SocketServer  socketServer
             = ( SocketServer ) serverClass.newInstance ( );
           socketServer.setPortServer ( this );
           socketServer.setSocket ( socket );
           socketServer.setUniqueID ( getNextUniqueID ( ) );
           Thread  serverThread = new Thread ( socketServer );
           add ( socketServer, serverThread, socket );
           serverThread.start ( );
         }
       } catch ( Throwable  t ) { terminate ( t ); }
     }

     private synchronized final void  add (
       SocketServer  socketServer, Thread  serverThread, Socket  socket ) {
     //////////////////////////////////////////////////////////////////////
       server_Vector.addElement ( socketServer );
       thread_Vector.addElement ( serverThread );
       socket_Vector.addElement ( socket       );
     }

     public synchronized final void  terminate ( Throwable  t ) {
     //////////////////////////////////////////////////////////////////////
       if ( shutdown ) return;
       shutdown = true;
       if ( t != null ) t.printStackTrace ( );
       Enumeration  e = thread_Vector.elements ( );
       while ( e.hasMoreElements ( ) ) {
         terminate ( ( Thread ) e.nextElement ( ) );
       }
       try { serverSocket.close ( ); } catch ( Throwable  t1 ) { }
       try { thread.stop ( ); } catch ( Throwable  t1 ) { }
     }

     public synchronized final void  terminate (
       SocketServer  socketServer ) {
     //////////////////////////////////////////////////////////////////////
       int  i = server_Vector.indexOf ( socketServer );
       terminate ( i );
     }

     private synchronized final void  terminate ( Thread  thread ) {
     //////////////////////////////////////////////////////////////////////
       int  i = thread_Vector.indexOf ( thread );
       terminate ( i );
     }

     private synchronized final void  terminate ( int  i ) {
     //////////////////////////////////////////////////////////////////////
       if ( i < 0 ) return;
       try { ( ( Socket ) socket_Vector.elementAt ( i ) ).close ( ); }
       catch ( Throwable  t1 ) { }
       try { ( ( Thread ) thread_Vector.elementAt ( i ) ).stop ( ); }
       catch ( Throwable  t2 ) { }
       try {
         server_Vector.removeElementAt ( i );
         socket_Vector.removeElementAt ( i );
         thread_Vector.removeElementAt ( i );
       } catch ( Throwable  t ) {
         t.printStackTrace ( );
       }
     }

     public final Vector  clone_server_Vector ( ) {
     //////////////////////////////////////////////////////////////////////
       return ( Vector ) server_Vector.clone ( );
     }

     private final synchronized long  getNextUniqueID ( ) {
     //////////////////////////////////////////////////////////////////////
       return nextUniqueID++;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
