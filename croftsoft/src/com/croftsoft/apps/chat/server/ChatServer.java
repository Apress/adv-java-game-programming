     package com.croftsoft.apps.chat.server;

     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.Commissionable;
     import com.croftsoft.core.math.MathConstants;
     import com.croftsoft.core.role.Consumer;
     import com.croftsoft.core.role.Server;
     import com.croftsoft.core.security.Authentication;
     import com.croftsoft.core.util.ClassMapServer;
     import com.croftsoft.core.util.loop.FixedDelayLoopGovernor;
     import com.croftsoft.core.util.loop.Loopable;
     import com.croftsoft.core.util.loop.Looper;

     import com.croftsoft.apps.chat.ChatConstants;
     import com.croftsoft.apps.chat.model.ChatGame;
     import com.croftsoft.apps.chat.model.seri.SeriChatGame;
     import com.croftsoft.apps.chat.request.CreateUserRequest;
     import com.croftsoft.apps.chat.request.PullRequest;
     import com.croftsoft.apps.chat.request.Request;
     import com.croftsoft.apps.chat.response.UnknownUserResponse;
     import com.croftsoft.apps.chat.server.CreateUserServer;
     import com.croftsoft.apps.chat.user.User;
     import com.croftsoft.apps.chat.user.UserStore;
     import com.croftsoft.apps.chat.user.seri.SeriUserStore;

     /*********************************************************************
     * Chat server.
     *
     * @version
     *   2003-06-18
     * @since
     *   2003-06-04
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ChatServer
       implements Commissionable, Server
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final boolean  DEBUG = true;

     private static final long  SAMPLE_PERIOD
       = 10 * MathConstants.MILLISECONDS_PER_SECOND;

     private static final double  UPDATE_RATE = 30.0;

     //

     private final UserStore         userStore;

     private final ChatGame          chatGame;

     private final Looper            looper;

     private final CreateUserServer  createUserServer;

     private final PullServer        pullServer;

     //

     private long  count;

     private long  startTime;

     private long  lastRequestTime;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ChatServer ( )
     //////////////////////////////////////////////////////////////////////
     {
       userStore = new SeriUserStore ( );

       chatGame = new SeriChatGame ( userStore );

       createUserServer = new CreateUserServer ( userStore );

       pullServer = new PullServer ( ChatConstants.QUEUE_PULL_TIMEOUT );

       looper = new Looper (
         new Loopable ( )
         {
           public boolean  loop ( )
           {
             return ChatServer.this.loop ( );
           }
         },
         new FixedDelayLoopGovernor ( UPDATE_RATE ),
         null,
         ( String ) null,
         Thread.MIN_PRIORITY,
         true );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       startTime = System.currentTimeMillis ( );

       lastRequestTime = startTime;

       looper.init ( );
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       looper.stop ( );

       looper.destroy ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Object  serve ( Object  requestObject )
     //////////////////////////////////////////////////////////////////////
     {
       if ( DEBUG )
       {
         System.out.println ( "ChatServer.serve(" + requestObject + ")" );
       }

       synchronized ( this )
       {
         lastRequestTime = System.currentTimeMillis ( );

         if ( DEBUG )
         {
           ++count;
         }
       }

       looper.start ( );

       if ( requestObject instanceof CreateUserRequest )
       {
         CreateUserRequest  createUserRequest
           = ( CreateUserRequest ) requestObject;

         return createUserServer.serve ( createUserRequest );
       }

       if ( !( requestObject instanceof Request ) )
       {
         throw new IllegalArgumentException ( );
       }

       Request  request = ( Request ) requestObject;

       Authentication  authentication = request.getAuthentication ( );

       User  user = userStore.getUser ( authentication );

       if ( user == null )
       {
         if ( request instanceof PullRequest )
         {
           return createUserServer.serve (
             new CreateUserRequest ( authentication ) );
         }

         return new UnknownUserResponse ( request );
       }

       user.updateLastRequestTime ( ); 

       if ( request instanceof PullRequest )
       {
         return pullServer.serve ( user, request );
       }

       user.getRequestQueue ( ).replace ( request );

       return null;
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private boolean  loop ( )
     //////////////////////////////////////////////////////////////////////
     {
       chatGame.update ( );

       long  currentTime = System.currentTimeMillis ( );

       synchronized ( this )
       {
         if ( DEBUG )
         {
           if ( currentTime >= startTime + SAMPLE_PERIOD )
           {
             System.out.println ( "requests per second:  "
               + ( MathConstants.MILLISECONDS_PER_SECOND * count )
               / ( double ) ( currentTime - startTime ) );

             startTime = currentTime;

             count = 0;
           }
         }

         if ( currentTime
           >= lastRequestTime + ChatConstants.REQUEST_TIMEOUT )
         {
           if ( DEBUG )
           {
             System.out.println ( "ChatServer game loop pausing..." );
           }

           return false;
         }
       }

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
