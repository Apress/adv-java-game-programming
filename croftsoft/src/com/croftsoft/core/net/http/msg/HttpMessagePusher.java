     package com.croftsoft.core.net.http.msg;

     import java.io.*;
     import java.net.*;

     import com.croftsoft.core.io.Encoder;
     import com.croftsoft.core.io.Parser;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.net.http.HttpLib;
     import com.croftsoft.core.util.loop.FixedDelayLoopGovernor;
     import com.croftsoft.core.util.loop.Loopable;
     import com.croftsoft.core.util.loop.Looper;
     import com.croftsoft.core.util.queue.Queue;

     /*********************************************************************
     * Pushes outgoing messages to the server using HTTP.
     * 
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     * @version
     *   2003-05-26
     * @since
     *   2000-04-23
     *********************************************************************/

     public final class  HttpMessagePusher
       implements Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final long     MINIMUM_DELAY     = 100;

     public static final String   THREAD_NAME       = "HttpMessagePusher";

     public static final int      THREAD_PRIORITY   = Thread.MIN_PRIORITY;

     public static final boolean  USE_DAEMON_THREAD = true;

     //

     private final Queue    outgoingQueue;

     private final Queue    incomingQueue;

     private final Encoder  encoder;

     private final Looper   looper;

     private final URL      url;

     private final String   userAgent;

     private final String   contentType;

     private final Parser   parser;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  HttpMessagePusher (
       Queue    outgoingQueue,
       Queue    incomingQueue,
       URL      url,
       String   userAgent,
       String   contentType,
       Encoder  encoder,
       Parser   parser )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.outgoingQueue = outgoingQueue );

       NullArgumentException.check ( this.incomingQueue = incomingQueue );

       NullArgumentException.check ( this.userAgent     = userAgent     );

       NullArgumentException.check ( this.contentType   = contentType   );

       NullArgumentException.check ( this.encoder       = encoder       );

       NullArgumentException.check ( this.parser        = parser        );

       NullArgumentException.check ( this.url           = url           );

       looper = new Looper (
         new Loopable ( )
         {
           public boolean  loop ( )
           {
             return HttpMessagePusher.this.loop ( );
           }
         },
         new FixedDelayLoopGovernor ( MINIMUM_DELAY, 0 ),
         null,
         THREAD_NAME,
         THREAD_PRIORITY,
         USE_DAEMON_THREAD );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       looper.init ( );
     }

     public void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       looper.start ( );
     }

     public void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       looper.stop ( );
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       looper.destroy ( );
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private boolean  loop ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         Object  request = outgoingQueue.pull ( );

         Object  response = HttpLib.post (
           url,
           encoder.encode ( request ),
           userAgent,
           contentType,
           parser );

         if ( response != null )
         {
           incomingQueue.append ( response );
         }
       }
       catch ( InterruptedException  ex )
       {
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         return false;
       }

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
