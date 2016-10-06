     package com.croftsoft.apps.mars.net;

     import java.io.*;
     import java.net.*;
     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.Commissionable;
     import com.croftsoft.core.math.MathConstants;
     import com.croftsoft.core.role.Server;
     import com.croftsoft.core.util.loop.FixedDelayLoopGovernor;
     import com.croftsoft.core.util.loop.Loopable;
     import com.croftsoft.core.util.loop.Looper;

     import com.croftsoft.apps.mars.ai.TankOperator;
     import com.croftsoft.apps.mars.model.World;
     import com.croftsoft.apps.mars.net.request.FireRequest;
     import com.croftsoft.apps.mars.net.request.MoveRequest;
     import com.croftsoft.apps.mars.net.request.Request;
     import com.croftsoft.apps.mars.net.request.ViewRequest;

     /*********************************************************************
     * Mars server.
     *
     * @version
     *   2003-06-13
     * @since
     *   2003-05-13
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  MarsServer
       implements Commissionable, Server
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final boolean  DEBUG           = true;

     private static final double   UPDATE_RATE     = 30.0;

     private static final long     SAMPLE_PERIOD   = 10 * 1000;

     private static final long     REQUEST_TIMEOUT = 10 * 1000;

     //

     private final String  primaryFilename;

     private final String  fallbackFilename;

     private final Looper  looper;

     //

     private GameInitAccessor  gameInitAccessor;

     private NetGame           netGame;

     private long              count;

     private long              startTime;

     private long              lastRequestTime;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  MarsServer (
       String  primaryFilename,
       String  fallbackFilename )
     //////////////////////////////////////////////////////////////////////
     {
       this.primaryFilename  = primaryFilename;

       this.fallbackFilename = fallbackFilename;

       setGameInitAccessor ( GameInit.createDefaultGameInit ( ) );

       looper = new Looper (
         new Loopable ( )
         {
           public boolean  loop ( )
           {
             return MarsServer.this.loop ( );
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

     public void  setGameInitAccessor (
       GameInitAccessor  gameInitAccessor )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.gameInitAccessor = gameInitAccessor );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( primaryFilename != null )
       {
         try
         {
           netGame = NetGame.load (
             gameInitAccessor, primaryFilename, fallbackFilename );
         }
         catch ( FileNotFoundException  ex )
         {
         }
         catch ( Exception  ex )
         {
           ex.printStackTrace ( );
         }
       }

       if ( netGame == null )
       {
         netGame = new NetGame ( gameInitAccessor );
       }

       startTime = System.currentTimeMillis ( );

       lastRequestTime = startTime;

       looper.init  ( );
     }

     public Object  serve ( Object  requestObject )
     //////////////////////////////////////////////////////////////////////
     {
       synchronized ( this )
       {
         lastRequestTime = System.currentTimeMillis ( );

         if ( DEBUG )
         {
           ++count;
         }
       }

       if ( !( requestObject instanceof Request ) )
       {
         throw new IllegalArgumentException ( );
       }

       looper.start ( );

       Request  request = ( Request ) requestObject;

       String  playerName = request.getPlayerName ( );

       if ( playerName == null )
       {
         return netGame.getGameData ( );
       }

       Player  player = netGame.getPlayer ( playerName );

       if ( player == null )
       {
         return netGame.getGameData ( );
       }

       player.setLastRequestTime ( System.currentTimeMillis ( ) );

       if ( request instanceof ViewRequest )
       {
         GameData  gameData = player.getGameData ( );

         if ( gameData == null )
         {
           gameData = netGame.getGameData ( );
         }

         return gameData;
       }

       TankOperator  tankOperator
         = player.getSeriTank ( ).getTankOperator ( );

       if ( request instanceof MoveRequest )
       {
         MoveRequest  moveRequest = ( MoveRequest ) request;

         tankOperator.go ( moveRequest.getDestination ( ) );

         return null;
       }

       if ( request instanceof FireRequest )
       {
         tankOperator.fire ( );

         return null;
       }

       throw new IllegalArgumentException ( );
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       looper.stop ( );

       looper.destroy ( );

       try
       {
         synchronized ( this )
         {
           netGame.save ( primaryFilename, fallbackFilename );
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private synchronized boolean  loop ( )
     //////////////////////////////////////////////////////////////////////
     {
       netGame.update ( );

       long  currentTime = System.currentTimeMillis ( );

       if ( DEBUG )
       {
         if ( currentTime >= startTime + SAMPLE_PERIOD )
         {
           System.out.println ( "requests per second:  "
             + ( MathConstants.MILLISECONDS_PER_SECOND * count )
             / ( currentTime - startTime ) );

           startTime = currentTime;

           count = 0;
         }
       }

       if ( currentTime >= lastRequestTime + REQUEST_TIMEOUT )
       {
         if ( DEBUG )
         {
           System.out.println ( "MarsServer game loop pausing..." );
         }

         return false;
       }

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
