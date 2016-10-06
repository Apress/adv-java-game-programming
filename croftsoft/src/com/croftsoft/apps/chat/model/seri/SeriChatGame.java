     package com.croftsoft.apps.chat.model.seri;

     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.animation.clock.SystemClock;
     import com.croftsoft.core.animation.clock.Timekeeper;
     import com.croftsoft.core.animation.model.Model;
     import com.croftsoft.core.animation.model.ModelId;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.role.Consumer;
     import com.croftsoft.core.util.queue.Queue;

     import com.croftsoft.apps.chat.ChatConstants;
     import com.croftsoft.apps.chat.model.ChatGame;
     import com.croftsoft.apps.chat.model.ChatWorld;
     import com.croftsoft.apps.chat.model.ChatWorldAccessor;
     import com.croftsoft.apps.chat.model.seri.SeriChatWorld;
     import com.croftsoft.apps.chat.request.CreateModelRequest;
     import com.croftsoft.apps.chat.request.KillRequest;
     import com.croftsoft.apps.chat.request.MoveRequest;
     import com.croftsoft.apps.chat.request.Request;
     import com.croftsoft.apps.chat.request.TalkRequest;
     import com.croftsoft.apps.chat.request.ViewRequest;
     import com.croftsoft.apps.chat.response.UnknownRequestResponse;
     import com.croftsoft.apps.chat.server.CreateModelServer;
     import com.croftsoft.apps.chat.server.KillServer;
     import com.croftsoft.apps.chat.server.MoveServer;
     import com.croftsoft.apps.chat.server.TalkServer;
     import com.croftsoft.apps.chat.server.RequestServer;
     import com.croftsoft.apps.chat.server.ViewServer;
     import com.croftsoft.apps.chat.user.User;
     import com.croftsoft.apps.chat.user.UserId;
     import com.croftsoft.apps.chat.user.UserStore;
     import com.croftsoft.apps.chat.user.seri.SeriUserStore;

     /*********************************************************************
     * A Serializable ChatGame implementation.
     *
     * @version
     *   2003-09-10
     * @since
     *   2003-06-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SeriChatGame
       implements Serializable, ChatGame
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private static final double  TIME_DELTA_MAX = 0.2;

     private static final double  TIME_FACTOR    = 1.0;

     //

     private final UserStore   userStore;

     private final ChatWorld   chatWorld;

     private final Timekeeper  timekeeper;

     private final Map         classToRequestServerMap;

     //////////////////////////////////////////////////////////////////////
     // static methods
     //////////////////////////////////////////////////////////////////////

/*
     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       Queue  requestQueue = new ListQueue ( );

       ChatGame  chatGame = new SeriChatGame (
         requestQueue,
         new Consumer ( )
         {
           public void  consume ( Object  o )
           {
             System.out.println ( o );
           }
         } );

       final String  USERNAME = "Test User";

       requestQueue.append ( new CreateRequest ( USERNAME ) );

       int  x = 0;

       int  y = 0;

       while ( true )
       {
         chatGame.update ( );

         requestQueue.append ( new MoveRequest ( USERNAME, x, y ) );

         x++;

         y++;
       }
     }
*/

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  SeriChatGame ( UserStore  userStore )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.userStore = userStore );

       Consumer  eventConsumer = 
         new Consumer ( )
         {
           public void  consume ( Object  o )
           {
             broadcast ( o );
           }
         };

       chatWorld = new SeriChatWorld ( eventConsumer );

       timekeeper = new Timekeeper ( SystemClock.INSTANCE, TIME_FACTOR );

       classToRequestServerMap = new HashMap ( );

/*
       classToRequestServerMap.put (
         KillRequest.class,
         new KillServer ( chatWorld ) );
*/

       classToRequestServerMap.put (
         CreateModelRequest.class,
         new CreateModelServer ( chatWorld ) );

       classToRequestServerMap.put (
         MoveRequest.class,
         new MoveServer ( chatWorld ) );

       classToRequestServerMap.put (
         TalkRequest.class,
         new TalkServer ( eventConsumer ) );

       classToRequestServerMap.put (
         ViewRequest.class,
         new ViewServer ( ( SeriChatWorld ) chatWorld ) );
     }

     public  SeriChatGame ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new SeriUserStore ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public ChatWorldAccessor  getChatWorldAccessor ( )
     //////////////////////////////////////////////////////////////////////
     {
       return chatWorld;
     }

     public ChatWorld  getChatWorld ( )
     //////////////////////////////////////////////////////////////////////
     {
       return chatWorld;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( )
     //////////////////////////////////////////////////////////////////////
     {
       chatWorld.prepare ( );

       Model [ ]  models = chatWorld.getModels ( );

       for ( int  i = 0; i < models.length; i++ )
       {
         Model  model = models [ i ];

         if ( !model.isActive ( ) )
         {
           chatWorld.removeModel ( model.getModelId ( ) );
         }
       }

       UserId [ ]  userIds = userStore.getUserIds ( );

       for ( int  i = 0; i < userIds.length; i++ )
       {
         User  user = userStore.getUser ( userIds [ i ] );

         if ( user == null )
         {
           continue;
         }

         if ( System.currentTimeMillis ( )
           >= user.getLastRequestTime ( ) + ChatConstants.USER_TIMEOUT )
         {
           removeUser ( user );

           continue;
         }

         Queue  requestQueue = user.getRequestQueue ( );

         Request  request = ( Request ) requestQueue.poll ( );

         if ( request == null )
         {
           continue;
         }

         RequestServer  requestServer = ( RequestServer )
           classToRequestServerMap.get ( request.getClass ( ) );

         if ( requestServer == null )
         {
           queue ( user, new UnknownRequestResponse ( request ) );
         }

         Object  response = requestServer.serve ( user, request );

         if ( response != null )
         {
           queue ( user, response );
         }
       }

       timekeeper.update ( );

       double  timeDelta = timekeeper.getTimeDelta ( );

       if ( timeDelta > TIME_DELTA_MAX )
       {
         timeDelta = TIME_DELTA_MAX;
       }

       chatWorld.update ( timeDelta );
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private void  queue (
       User    user,
       Object  message )
     //////////////////////////////////////////////////////////////////////
     {
       Queue  messageQueue = user.getMessageQueue ( );

       try
       {
         messageQueue.replace ( message );
       }
       catch ( IndexOutOfBoundsException  ex )
       {
         removeUser ( user );
       }
     }

     private void  broadcast ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       UserId [ ]  userIds = userStore.getUserIds ( );

       for ( int  i = 0; i < userIds.length; i++ )
       {
         User  user = userStore.getUser ( userIds [ i ] );

         if ( user == null )
         {
           continue;
         }

         queue ( user, o );
       }
     }

     private void  removeUser ( User  user )
     //////////////////////////////////////////////////////////////////////
     {
       userStore.removeUser ( user.getUserId ( ) );

       ModelId  modelId = user.getModelId ( );

       if ( modelId != null )
       {
         chatWorld.removeModel ( modelId );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
