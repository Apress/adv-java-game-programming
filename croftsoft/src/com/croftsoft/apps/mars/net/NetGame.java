     package com.croftsoft.apps.mars.net;

     import java.awt.*;
     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.animation.clock.HiResClock;
     import com.croftsoft.core.animation.clock.Timekeeper;
     import com.croftsoft.core.io.SerializableLib;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.util.queue.ListQueue;
     import com.croftsoft.core.util.queue.Queue;

     import com.croftsoft.apps.mars.ai.DirectedTankOperator;
     import com.croftsoft.apps.mars.ai.TankOperator;
     import com.croftsoft.apps.mars.model.*;
     import com.croftsoft.apps.mars.model.seri.SeriAmmoDump;
     import com.croftsoft.apps.mars.model.seri.SeriTank;
     import com.croftsoft.apps.mars.model.seri.SeriWorld;

     /*********************************************************************
     * The game state and behavior.
     *
     * @version
     *   2003-09-10
     * @since
     *   2003-04-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  NetGame
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final SeriWorld   seriWorld;

     private final Random      actionsRandom;

     private final Random      contentRandom;

     private final Timekeeper  timekeeper;

     private final Queue       newPlayerNameQueue;

     private final Map         nameToPlayerMap;

     // frequently used GameInitAccessor values

     private final double      timeDeltaMax;

     private final long        playerTimeout;

     private final int         attemptsMax;

     private final int         worldWidth;

     private final int         worldHeight;

     private final double      obstacleRadiusMax;

     private final double      obstacleRadiusMin;

     //

     private GameData   gameData;

     private SeriWorld  copySeriWorld;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( test ( args ) );
     }

     public static boolean  test ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         new NetGame ( ).update ( );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         return false;
       }

       return true;
     }

     public static NetGame  load (
       GameInitAccessor  gameInitAccessor,
       String            primaryFilename,
       String            backupFilename )
       throws ClassNotFoundException, IOException
     //////////////////////////////////////////////////////////////////////
     {
       SeriWorld  seriWorld = ( SeriWorld )
         SerializableLib.load ( primaryFilename, backupFilename );

       return new NetGame ( gameInitAccessor, seriWorld );
     }

     //////////////////////////////////////////////////////////////////////
     // public constructor methods
     //////////////////////////////////////////////////////////////////////

     public  NetGame ( GameInitAccessor  gameInitAccessor )
     //////////////////////////////////////////////////////////////////////
     {
       this ( gameInitAccessor, ( SeriWorld ) null );
     }

     public  NetGame ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( ( GameInitAccessor ) null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public GameData  getGameData ( )
     //////////////////////////////////////////////////////////////////////
     {
       return gameData;
     }

     public Player  getPlayer ( String  playerName )
     //////////////////////////////////////////////////////////////////////
     {
       Player  player = ( Player ) nameToPlayerMap.get ( playerName );

       if ( player == null )
       {
         newPlayerNameQueue.replace ( playerName );
       }

       return player;
     }

     public void  save (
       String  primaryFilename,
       String  backupFilename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       SerializableLib.save ( seriWorld, primaryFilename, backupFilename );
     }

     public void  update ( )
     //////////////////////////////////////////////////////////////////////
     {
       seriWorld.prepare ( );

       timekeeper.update ( );

       double  timeDelta = timekeeper.getTimeDelta ( );

       if ( timeDelta > timeDeltaMax )
       {
         timeDelta = timeDeltaMax;
       }

       seriWorld.update ( timeDelta );

       // restore and relocate destroyed obstacles

       Obstacle [ ]  obstacles = seriWorld.getObstacles ( );

       for ( int  i = 0; i < obstacles.length; i++ )
       {
         Obstacle  obstacle = obstacles [ i ];

         if ( !obstacle.isActive ( ) )
         {
           resetObstacle ( obstacle );
         }         
       }

       // create list of owned tanks and disconnected players

       java.util.List  tankList = new ArrayList ( );

       java.util.List  removeList = new ArrayList ( );

       Iterator  iterator = nameToPlayerMap.values ( ).iterator ( );

       long  currentTimeMillis = System.currentTimeMillis ( );

       while ( iterator.hasNext ( ) )
       {
         Player  player = ( Player ) iterator.next ( );

         tankList.add ( player.getSeriTank ( ) );

         long  lastRequestTime = player.getLastRequestTime ( );

         if ( currentTimeMillis >= lastRequestTime + playerTimeout )
         {
           removeList.add ( player );
         }
       }

       // remove disconnected players and their tanks

       iterator = removeList.iterator ( );

       while ( iterator.hasNext ( ) )
       {
         Player  player = ( Player ) iterator.next ( );

         seriWorld.remove ( player.getSeriTank ( ) );

         nameToPlayerMap.remove ( player.getName ( ) );
       }

       // remove unowned tanks and reactivate dead player tanks

       Tank [ ]  tanks = seriWorld.getTanks ( );

       for ( int  i = 0; i < tanks.length; i++ )
       {
         Tank  tank = tanks [ i ];

         if ( !tankList.contains ( tank ) )
         {
           seriWorld.remove ( tank );
         }
         else if ( !tank.isActive ( ) )
         {
           for ( int  j = 0; j < attemptsMax; j++ )
           {
             tank.initialize (
               worldWidth  * actionsRandom.nextDouble ( ),
               worldHeight * actionsRandom.nextDouble ( ) );

             if ( !seriWorld.isBlocked ( tank ) )
             {
               break;
             }
           }
         }         
       }

       // create new players and their tanks

       while ( true )
       {
         String  newPlayerName = ( String ) newPlayerNameQueue.poll ( );

         if ( newPlayerName == null )
         {
           break;
         }

         Player  player = getPlayer ( newPlayerName );

         if ( player != null )
         {
           continue;
         }

         Tank  playerTank = seriWorld.createTank (
           0.0, 0.0,
           new Color (
             actionsRandom.nextInt ( 256 ),
             actionsRandom.nextInt ( 256 ),
             actionsRandom.nextInt ( 256 ) ) );

         for ( int  j = 0; j < attemptsMax; j++ )
         {
           playerTank.initialize (
             worldWidth  * actionsRandom.nextDouble ( ),
             worldHeight * actionsRandom.nextDouble ( ) );

           if ( !seriWorld.isBlocked ( playerTank ) )
           {
             break;
           }
         }

         playerTank.setTankOperator (
           new DirectedTankOperator ( playerTank ) );

         player = new Player ( newPlayerName, ( SeriTank ) playerTank );

         nameToPlayerMap.put ( newPlayerName, player );
       }

       // update snapshots

       try
       {
         copySeriWorld = ( SeriWorld ) SerializableLib.copy ( seriWorld );
       }
       catch ( IOException  ex )
       {
         // This normally will never happen.

         throw ( RuntimeException )
           new RuntimeException ( ).initCause ( ex );
       }

       gameData = new GameData ( copySeriWorld, null );

       iterator = nameToPlayerMap.values ( ).iterator ( );

       while ( iterator.hasNext ( ) )
       {
         Player  player = ( Player ) iterator.next ( );

         try
         {
           SeriTank  copySeriTank = ( SeriTank )
             SerializableLib.copy ( player.getSeriTank ( ) );

           player.setGameData (
             new GameData ( copySeriWorld, copySeriTank ) );
         }
         catch ( IOException  ex )
         {
           // This normally will never happen.

           throw ( RuntimeException )
             new RuntimeException ( ).initCause ( ex );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private  NetGame (
       GameInitAccessor  gameInitAccessor,
       SeriWorld         seriWorld )
     //////////////////////////////////////////////////////////////////////
     {
       if ( gameInitAccessor == null )
       {
         gameInitAccessor = GameInit.createDefaultGameInit ( );
       }

       long  randomSeed = gameInitAccessor.getRandomSeed ( );

       actionsRandom = new Random ( randomSeed );

       contentRandom = new Random ( randomSeed );

       // frequently used GameInitAccessor values

       timeDeltaMax      = gameInitAccessor.getTimeDeltaMax      ( );

       playerTimeout     = gameInitAccessor.getPlayerTimeout     ( );

       attemptsMax       = gameInitAccessor.getAttemptsMax       ( );

       worldWidth        = gameInitAccessor.getWorldWidth        ( );

       worldHeight       = gameInitAccessor.getWorldHeight       ( );

       obstacleRadiusMax = gameInitAccessor.getObstacleRadiusMax ( );

       obstacleRadiusMin = gameInitAccessor.getObstacleRadiusMin ( );

       if ( seriWorld == null )
       {
         seriWorld = new SeriWorld (
           actionsRandom,
           new SeriAmmoDump.Shared (
             gameInitAccessor.getAmmoDumpGrowth    ( ),
             gameInitAccessor.getAmmoDumpMax       ( ),
             gameInitAccessor.getAmmoDumpExplosion ( ),
             gameInitAccessor.getAmmoDumpZ         ( ) ) );

         this.seriWorld = seriWorld;

         for ( int  i = 0; i < gameInitAccessor.getAmmoDumps ( ); i++ )
         {
           AmmoDump  ammoDump = seriWorld.createAmmoDump (
             worldWidth  * contentRandom.nextDouble ( ),
             worldHeight * contentRandom.nextDouble ( ) );

           for ( int  j = 0; j < attemptsMax; j++ )
           {
             if ( !seriWorld.isBlocked ( ammoDump ) )
             {
               break;
             }

             ammoDump.setCenter (
               worldWidth  * contentRandom.nextDouble ( ),
               worldHeight * contentRandom.nextDouble ( ) );
           }
         }

         Rectangle  driftBounds
           = new Rectangle ( 0, 0, worldWidth, worldHeight );

         for ( int  i = 0; i < gameInitAccessor.getObstacles ( ); i++ )
         {
           Obstacle  obstacle = seriWorld.createObstacle (
             0.0,
             0.0,
             0.0,
             obstacleRadiusMin,
             driftBounds );

           resetObstacle ( obstacle );
         }
       }
       else
       {
         this.seriWorld = seriWorld;
       }

       timekeeper = new Timekeeper (
         new HiResClock ( ), gameInitAccessor.getTimeFactorDefault ( ) );

       newPlayerNameQueue = new ListQueue ( );

       nameToPlayerMap = new HashMap ( );

       try
       {
         copySeriWorld = ( SeriWorld ) SerializableLib.copy ( seriWorld );
       }
       catch ( IOException  ex )
       {
         // This normally will never happen.

         throw ( RuntimeException )
           new RuntimeException ( ).initCause ( ex );
       }

       gameData = new GameData ( copySeriWorld, null );
     }

     private void  resetObstacle ( Obstacle  obstacle )
     //////////////////////////////////////////////////////////////////////
     {
       obstacle.setActive ( true );

       for ( int  j = 0; j < attemptsMax; j++ )
       {
         obstacle.setCenter (
           worldWidth  * contentRandom.nextDouble ( ),
           worldHeight * contentRandom.nextDouble ( ) );

         if ( !seriWorld.isBlocked ( obstacle ) )
         {
           break;
         }
       }

       double  radius = ( obstacleRadiusMax - obstacleRadiusMin )
         * contentRandom.nextDouble ( ) + obstacleRadiusMin;

       obstacle.setRadius ( radius );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
