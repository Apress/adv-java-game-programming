     package com.croftsoft.apps.chat.client;

     import java.io.*;
     import java.net.*;
     import java.util.*;

     import com.croftsoft.core.io.SerializableCoder;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.math.MathConstants;
     import com.croftsoft.core.net.http.HttpLib;
     import com.croftsoft.core.net.http.msg.HttpMessageClient;
     import com.croftsoft.core.role.Consumer;
     import com.croftsoft.core.security.Authentication;
     import com.croftsoft.core.util.consumer.NullConsumer;
     import com.croftsoft.core.util.consumer.QueueConsumer;
     import com.croftsoft.core.util.loop.Loopable;
     import com.croftsoft.core.util.loop.Looper;
     import com.croftsoft.core.util.queue.ListQueue;
     import com.croftsoft.core.util.queue.Queue;
     import com.croftsoft.core.util.queue.QueuePuller;

     import com.croftsoft.apps.chat.ChatConstants;
     import com.croftsoft.apps.chat.event.CreateModelEvent;
     import com.croftsoft.apps.chat.event.Event;
     import com.croftsoft.apps.chat.event.MoveEvent;
     import com.croftsoft.apps.chat.event.NullEvent;
     import com.croftsoft.apps.chat.event.RemoveModelEvent;
     import com.croftsoft.apps.chat.event.TalkEvent;
     import com.croftsoft.apps.chat.request.CreateModelRequest;
     import com.croftsoft.apps.chat.request.PullRequest;
     import com.croftsoft.apps.chat.request.ViewRequest;
     import com.croftsoft.apps.chat.response.CreateModelResponse;
     import com.croftsoft.apps.chat.response.CreateUserResponse;
     import com.croftsoft.apps.chat.response.UnknownUserResponse;
     import com.croftsoft.apps.chat.response.ViewResponse;
     import com.croftsoft.apps.chat.server.ChatServer;

     /*********************************************************************
     * Chat client.
     *
     * @version
     *   2003-08-16
     * @since
     *   2003-06-10
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ChatClient
       implements Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final boolean  DEBUG = true;

     //////////////////////////////////////////////////////////////////////
     // network constants
     //////////////////////////////////////////////////////////////////////

     private static final String  USER_AGENT
       = "Chat/1.0";

     private static final long    POLLING_PERIOD_MIN
       = 0;

     private static final long    POLLING_PERIOD_MAX
       = MathConstants.MILLISECONDS_PER_DAY;

     private static final long    POLLING_PERIOD_INIT
       = POLLING_PERIOD_MIN;

     private static final double  POLLING_PERIOD_MULT
       = 2.0;

     private static final double  POLLING_PERIOD_DIVI
       = Double.POSITIVE_INFINITY;

     private static final long    POLLING_PERIOD_INCR
       = MathConstants.MILLISECONDS_PER_SECOND;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private final Authentication     authentication;

     private final PullRequest        pullRequest;

     private final Queue              eventQueue;

     private final Queue              requestQueue;

     private final ChatServer         chatServer;

     private final Looper             looper;

     private final HttpMessageClient  httpMessageClient;

     private final Map                classToConsumerMap;

     private final QueuePuller        queuePuller;

     //

     private long  eventIndex;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ChatClient (
       Authentication  authentication,
       URL             servletURL )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.authentication = authentication );

       pullRequest = new PullRequest ( authentication );

       if ( servletURL == null )
       {
         requestQueue = new ListQueue ( );

         chatServer = new ChatServer ( );

         queuePuller = new QueuePuller (
           requestQueue,
           new Consumer ( )
           {
             public void  consume ( Object  o )
             {
               ChatClient.this.consume ( chatServer.serve ( o ) );
             }
           } );

         looper = new Looper (
           new Loopable ( )
           {
             public boolean  loop ( )
             {
               return ChatClient.this.loop ( );
             }
           } );

         httpMessageClient = null;
       }
       else
       {
         httpMessageClient = new HttpMessageClient (
           servletURL,
           USER_AGENT,
           SerializableCoder.INSTANCE,
           SerializableCoder.INSTANCE,
           HttpLib.APPLICATION_OCTET_STREAM, // contentType
           createRequestBytes ( pullRequest ),
           new Consumer ( )
           {
             public void  consume ( Object  o )
             {
               ChatClient.this.consume ( o );
             }
           },
           POLLING_PERIOD_MIN,
           POLLING_PERIOD_MAX,
           POLLING_PERIOD_INIT,
           POLLING_PERIOD_MULT,
           POLLING_PERIOD_DIVI,
           POLLING_PERIOD_INCR );

         requestQueue = httpMessageClient.getOutgoingQueue ( );

         chatServer = null;

         looper = null;

         queuePuller = null;
       }

       eventQueue = new ListQueue ( );

       Consumer  eventConsumer = new QueueConsumer ( eventQueue );

       classToConsumerMap = new HashMap ( );

       classToConsumerMap.put (
         UnknownUserResponse.class,
         NullConsumer.INSTANCE );

       classToConsumerMap.put (
         CreateModelResponse.class,
         NullConsumer.INSTANCE );

       classToConsumerMap.put (
         CreateUserResponse.class,
         new CreateUserConsumer  (
           requestQueue,
           authentication,
           ChatConstants.DEFAULT_AVATAR_TYPE,
           ChatConstants.DEFAULT_AVATAR_X,
           ChatConstants.DEFAULT_AVATAR_Y ) );

       classToConsumerMap.put (
         CreateModelEvent.class,
         eventConsumer );

       classToConsumerMap.put (
         MoveEvent.class,
         eventConsumer );

       classToConsumerMap.put (
         NullEvent.class,
         NullConsumer.INSTANCE );

       classToConsumerMap.put (
         RemoveModelEvent.class,
         eventConsumer );

       classToConsumerMap.put (
         TalkEvent.class,
         eventConsumer );

       classToConsumerMap.put (
         ViewResponse.class,
         eventConsumer );

       eventIndex = new Random ( ).nextLong ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( chatServer != null )
       {
         chatServer.init ( );
         
         looper.init ( );

         queuePuller.init ( );
       }
       else
       {
         httpMessageClient.init ( );
       }
     }

     public void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( chatServer != null )
       {
         looper.start ( );

         queuePuller.start ( );
       }
       else
       {
         httpMessageClient.start ( );
       }
     }

     public void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( chatServer != null )
       {
         looper.stop ( );

         queuePuller.stop ( );
       }
       else
       {
         httpMessageClient.stop ( );
       }
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( chatServer != null )
       {
         chatServer.destroy ( );
         
         looper.destroy ( );

         queuePuller.destroy ( );
       }
       else
       {
         httpMessageClient.destroy ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Queue  getEventQueue   ( ) { return eventQueue;   }

     public Queue  getRequestQueue ( ) { return requestQueue; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private boolean  loop ( )
     //////////////////////////////////////////////////////////////////////
     {
       Object  response = chatServer.serve ( pullRequest );

       if ( response != null )
       {
         consume ( response );
       }

       return true;
     }

     private void  consume ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       if ( DEBUG )
       {
         System.out.println ( "ChatClient.consume(" + o + ")" );
       }

       if ( o instanceof Event )
       {
         long  eventIndex = ( ( Event ) o ).getEventIndex ( );

         if ( eventIndex == this.eventIndex )
         {
           return;
         }

         if ( eventIndex == this.eventIndex + 1 )
         {
           this.eventIndex = eventIndex;
         }
         else
         {
           requestQueue.replace ( new ViewRequest ( authentication ) );
         }
       }
       else if ( o instanceof ViewResponse )
       {
         this.eventIndex = ( ( ViewResponse ) o ).getEventIndex ( );
       }

       Consumer  consumer
         = ( Consumer ) classToConsumerMap.get ( o.getClass ( ) );

       if ( consumer == null )
       {
         System.out.println ( "ChatClient:  unknown message:  " + o );

         System.out.println ( "Suspending ChatClient..." );

         stop ( );
       }
       else
       {
         try
         {
           consumer.consume ( o );
         }
         catch ( Exception  ex )
         {
           ex.printStackTrace ( );

           System.out.println ( "Suspending ChatClient..." );

           stop ( );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private byte [ ]  createRequestBytes ( Serializable  serializable )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         return SerializableCoder.INSTANCE.encode ( serializable );
       }
       catch ( Exception  ex )
       {
         // this should never happen if constants are OK

         ex.printStackTrace ( );

         throw
          ( RuntimeException ) new RuntimeException ( ).initCause ( ex );
       }
     }
/*
     private void  processMoveEvent ( MoveEvent  moveEvent )
     //////////////////////////////////////////////////////////////////////
     {
         MoveEvent  moveEvent = ( MoveEvent ) o;

         ChatModel  chatModel
           = chatWorld.getChatModel ( moveEvent.getModelId ( ) );

         if ( chatModel == null )
         {
           return;
         }

         PointXY  origin = moveEvent.getOrigin ( );

         if ( origin != null )
         {
           chatModel.setCenter ( origin.getX ( ), origin.getY ( ) );
         }

         chatModel.setDestination ( moveEvent.getDestination ( ) );

         return;
       }
     }

     private void  processUserUnknownResponse (
       UserUnknownResponse  userUnknownResponse )
     //////////////////////////////////////////////////////////////////////
     {
       requestQueue.replace (
         new CreateUserRequest ( authentication ) );
     }
*/

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
