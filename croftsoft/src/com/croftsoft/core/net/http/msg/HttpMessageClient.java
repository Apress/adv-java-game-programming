     package com.croftsoft.core.net.http.msg;

     import java.net.*;
     import java.util.*;

     import com.croftsoft.core.io.Encoder;
     import com.croftsoft.core.io.Parser;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.role.Consumer;
     import com.croftsoft.core.util.queue.ListQueue;
     import com.croftsoft.core.util.queue.Queue;
     import com.croftsoft.core.util.queue.QueuePuller;

     /*********************************************************************
     * An HttpMessagePoller/Pusher facade.
     *
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     * @version
     *   2003-06-12
     * @since
     *   2000-04-23
     *********************************************************************/

     public final class  HttpMessageClient
       implements Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Queue              incomingQueue;

     private final Queue              outgoingQueue;

     private final HttpMessagePoller  httpMessagePoller;

     private final HttpMessagePusher  httpMessagePusher;

     private final QueuePuller        incomingQueuePuller;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor method.
     *
     * @param  consumer
     *
     *   May be null.
     *********************************************************************/
     public  HttpMessageClient (
       URL       url,
       String    userAgent,
       Encoder   encoder,
       Parser    parser,
       String    contentType,  // "application/x-www-form-urlencoded"
       byte [ ]  requestBytes,
       Consumer  consumer,
       long      pollingPeriodMin,
       long      pollingPeriodMax,
       long      pollingPeriodInit,
       double    pollingPeriodMult,
       double    pollingPeriodDivi,
       long      pollingPeriodIncr )
     //////////////////////////////////////////////////////////////////////
     {
       incomingQueue = new ListQueue ( );

       outgoingQueue = new ListQueue ( );

       httpMessagePoller = new HttpMessagePoller (
         url,
         userAgent,
         contentType,
         requestBytes,
         parser,
         incomingQueue,
         pollingPeriodMin,
         pollingPeriodMax,
         pollingPeriodInit,
         pollingPeriodMult,
         pollingPeriodDivi,
         pollingPeriodIncr );

       httpMessagePusher = new HttpMessagePusher (
         outgoingQueue,
         incomingQueue,
         url,
         userAgent,
         contentType,
         encoder,
         parser );

       if ( consumer != null )
       {
         incomingQueuePuller = new QueuePuller ( incomingQueue, consumer );
       }
       else
       {
         incomingQueuePuller = null;
       }
     }

     /*********************************************************************
     * Convenience constructor method.
     *********************************************************************/
     public  HttpMessageClient (
       URL       url,
       String    userAgent,
       Encoder   encoder,
       Parser    parser,
       String    contentType,  // "application/x-www-form-urlencoded"
       byte [ ]  requestBytes,
       Consumer  consumer )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         url,
         userAgent,
         encoder,
         parser,
         contentType,
         requestBytes,
         consumer,
         HttpMessagePoller.DEFAULT_POLLING_PERIOD_MIN,
         HttpMessagePoller.DEFAULT_POLLING_PERIOD_MAX,
         HttpMessagePoller.DEFAULT_POLLING_PERIOD_INIT,
         HttpMessagePoller.DEFAULT_POLLING_PERIOD_MULT,
         HttpMessagePoller.DEFAULT_POLLING_PERIOD_DIVI,
         HttpMessagePoller.DEFAULT_POLLING_PERIOD_INCR );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public Queue  getIncomingQueue ( ) { return incomingQueue; }

     public Queue  getOutgoingQueue ( ) { return outgoingQueue; }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setRequestBytes ( byte [ ]  requestBytes )
     //////////////////////////////////////////////////////////////////////
     {
       httpMessagePoller.setRequestBytes ( requestBytes );
     }

     //////////////////////////////////////////////////////////////////////
     // lifecycle methods
     //////////////////////////////////////////////////////////////////////

     public synchronized void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( incomingQueuePuller != null )
       {
         incomingQueuePuller.init ( );
       }

       httpMessagePoller.init ( );

       httpMessagePusher.init ( );
     }

     public synchronized void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( incomingQueuePuller != null )
       {
         incomingQueuePuller.start ( );
       }

       httpMessagePoller.start ( );

       httpMessagePusher.start ( );
     }

     public synchronized void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       httpMessagePoller.stop ( );

       httpMessagePusher.stop ( );

       if ( incomingQueuePuller != null )
       {
         incomingQueuePuller.stop ( );
       }
     }

     public synchronized void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       httpMessagePoller.destroy ( );

       httpMessagePusher.destroy ( );

       if ( incomingQueuePuller != null )
       {
         incomingQueuePuller.destroy ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // Queue methods
     //////////////////////////////////////////////////////////////////////

     public boolean  append ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       return outgoingQueue.append ( o );
     }

     public void  replace ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       outgoingQueue.replace ( o );
     }

     public Object  poll ( )
     //////////////////////////////////////////////////////////////////////
     {
       return incomingQueue.poll ( );
     }

     public Object  pull ( )
       throws InterruptedException
     //////////////////////////////////////////////////////////////////////
     {
       return incomingQueue.pull ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
