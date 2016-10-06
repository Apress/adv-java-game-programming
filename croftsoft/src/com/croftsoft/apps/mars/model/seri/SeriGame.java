     package com.croftsoft.apps.mars.model.seri;

     import java.awt.*;
     import java.io.Serializable;
     import java.util.*;

     import com.croftsoft.core.animation.clock.HiResClock;
     import com.croftsoft.core.animation.clock.Timekeeper;
     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.apps.mars.ai.DefaultTankOperator;
     import com.croftsoft.apps.mars.ai.PlayerTankOperator;
     import com.croftsoft.apps.mars.ai.TankOperator;
     import com.croftsoft.apps.mars.model.AmmoDump;
     import com.croftsoft.apps.mars.model.Game;
     import com.croftsoft.apps.mars.model.Obstacle;
     import com.croftsoft.apps.mars.model.Tank;
     import com.croftsoft.apps.mars.model.TankAccessor;
     import com.croftsoft.apps.mars.model.World;
     import com.croftsoft.apps.mars.model.WorldAccessor;

     /*********************************************************************
     * A Serializable implementation of Game.
     *
     * @version
     *   2003-09-10
     * @since
     *   2003-04-03
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SeriGame
       implements Game, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     public static final double  DEFAULT_TIME_FACTOR         = 1.0;

     public static final long    DEFAULT_RANDOM_SEED         = 1968L;

     public static final double  DEFAULT_INITIAL_PLAYER_X    = 20.0;

     public static final double  DEFAULT_INITIAL_PLAYER_Y    = 20.0;

     public static final int     DEFAULT_AMMO_DUMPS          = 3;

     public static final int     DEFAULT_OBSTACLES           = 6;

     public static final int     DEFAULT_WORLD_WIDTH         = 600;

     public static final int     DEFAULT_WORLD_HEIGHT        = 400;

     public static final Color   DEFAULT_FRIEND_COLOR        = Color.BLUE;

     public static final Color   DEFAULT_ENEMY_COLOR         = Color.RED;

     public static final double  DEFAULT_TIME_DELTA_MAX      = 0.2;

     public static final int     DEFAULT_ATTEMPTS_MAX        = 10;

     public static final double  DEFAULT_OBSTACLE_RADIUS_MAX = 60.0;

     public static final double  DEFAULT_OBSTACLE_RADIUS_MIN = 10.0;

     //

     private final double  timeFactorDefault;

     private final long    randomSeed;

     private final double  initialPlayerX;

     private final double  initialPlayerY;

     private final int     ammoDumps;

     private final int     obstacles;

     private final int     worldWidth;

     private final int     worldHeight;

     private final Color   friendColor;

     private final Color   enemyColor;

     private final double  timeDeltaMax;

     private final int     attemptsMax;

     private final double  obstacleRadiusMax;

     private final double  obstacleRadiusMin;

     //

     private final Random        actionsRandom;

     private final Random        contentRandom;

     private final TankOperator  playerTankOperator;

     private final Timekeeper    timekeeper;

     private final World         world;

     //

     private int   level;

     private Tank  playerTank;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       SeriGame  seriGame = new SeriGame ( );

       int  level = 0;

       while ( true )
       {
         if ( seriGame.getLevel ( ) != level )
         {
           level = seriGame.getLevel ( );

           System.out.println ( new Date ( ) + ":  Level " + level );
         }

         seriGame.update ( );         
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SeriGame (
       double               timeFactorDefault,
       long                 randomSeed,
       double               initialPlayerX,
       double               initialPlayerY,
       int                  ammoDumps,
       int                  obstacles,
       int                  worldWidth,
       int                  worldHeight,
       Color                friendColor,
       Color                enemyColor,
       double               timeDeltaMax,
       int                  attemptsMax,
       double               obstacleRadiusMax,
       double               obstacleRadiusMin,
       SeriAmmoDump.Shared  seriAmmoDumpShared )
     //////////////////////////////////////////////////////////////////////
     {
       this.timeFactorDefault = timeFactorDefault;

       this.randomSeed        = randomSeed;

       this.initialPlayerX    = initialPlayerX;

       this.initialPlayerY    = initialPlayerY;

       this.ammoDumps         = ammoDumps;

       this.obstacles         = obstacles;

       this.worldWidth        = worldWidth;

       this.worldHeight       = worldHeight;

       NullArgumentException.check ( this.friendColor = friendColor );

       NullArgumentException.check ( this.enemyColor  = enemyColor  );

       this.timeDeltaMax      = timeDeltaMax;

       this.attemptsMax       = attemptsMax;

       this.obstacleRadiusMax = obstacleRadiusMax;

       this.obstacleRadiusMin = obstacleRadiusMin;

       timekeeper
         = new Timekeeper ( new HiResClock ( ), timeFactorDefault );

       contentRandom = new Random ( randomSeed );

       // The random seed for actionsRandom is set in createLevel().

       actionsRandom = new Random ( );

       world = new SeriWorld (
         actionsRandom,
         seriAmmoDumpShared );

       DefaultTankOperator  defaultTankOperator
         = new DefaultTankOperator ( actionsRandom );

       playerTankOperator = new PlayerTankOperator ( defaultTankOperator );

       level = 1;

       createLevel ( );
     }

     public  SeriGame ( )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         DEFAULT_TIME_FACTOR,
         DEFAULT_RANDOM_SEED,
         DEFAULT_INITIAL_PLAYER_X,
         DEFAULT_INITIAL_PLAYER_Y,
         DEFAULT_AMMO_DUMPS,
         DEFAULT_OBSTACLES,
         DEFAULT_WORLD_WIDTH,
         DEFAULT_WORLD_HEIGHT,
         DEFAULT_FRIEND_COLOR,
         DEFAULT_ENEMY_COLOR,
         DEFAULT_TIME_DELTA_MAX,
         DEFAULT_ATTEMPTS_MAX,
         DEFAULT_OBSTACLE_RADIUS_MAX,
         DEFAULT_OBSTACLE_RADIUS_MIN,
         new SeriAmmoDump.Shared ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public Iterator       getPath               ( ) {
                                   return playerTankOperator.getPath ( ); }

     public int            getLevel              ( ) { return level;      }

     public TankAccessor   getPlayerTankAccessor ( ) { return playerTank; }

     public Tank           getPlayerTank         ( ) { return playerTank; }

     public Timekeeper     getTimekeeper         ( ) { return timekeeper; }

     public WorldAccessor  getWorldAccessor      ( ) { return world;      }

     public double  getTimeFactorDefault  ( ) { return timeFactorDefault; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( )
     //////////////////////////////////////////////////////////////////////
     {
       world.prepare ( );

       timekeeper.update ( );

       double  timeDelta = timekeeper.getTimeDelta ( );

       if ( timeDelta > timeDeltaMax )
       {
         timeDelta = timeDeltaMax;
       }

       world.update ( timeDelta );

       Tank [ ]  tanks = world.getTanks ( );

       for ( int  i = 0; i < tanks.length; i++ )
       {
         Tank  tank = tanks [ i ];

         if ( !tank.isActive ( ) )
         {
           for ( int  j = 0; j < attemptsMax; j++ )
           {
             tank.initialize (
               worldWidth  * actionsRandom.nextDouble ( ),
               worldHeight * actionsRandom.nextDouble ( ) );

             if ( !world.isBlocked ( tank ) )
             {
               break;
             }
           }
         }         
       }

       Obstacle [ ]  obstacles = world.getObstacles ( );

       for ( int  i = 0; i < obstacles.length; i++ )
       {
         if ( obstacles [ i ].isActive ( ) )
         {
           return;
         }         
       }

       level++;

       createLevel ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private void  createLevel ( )
     //////////////////////////////////////////////////////////////////////
     {
       actionsRandom.setSeed ( level );

       world.clear ( );

       playerTank = world.createTank (
         initialPlayerX,
         initialPlayerY,
         friendColor );

       playerTankOperator.setTankConsole ( playerTank );

       playerTank.setTankOperator ( playerTankOperator );

       Rectangle  driftBounds
         = new Rectangle ( 0, 0, worldWidth, worldHeight );

       for ( int  i = 0; i < obstacles; i++ )
       {
         double  radius = ( obstacleRadiusMax - obstacleRadiusMin )
           * contentRandom.nextDouble ( ) + obstacleRadiusMin;

         Obstacle  obstacle = world.createObstacle (
           worldWidth  * contentRandom.nextDouble ( ),
           worldHeight * contentRandom.nextDouble ( ),
           radius,
           obstacleRadiusMin,
           driftBounds );

         for ( int  j = 0; j < attemptsMax; j++ )
         {
           if ( !world.isBlocked ( obstacle ) )
           {
             break;
           }

           obstacle.setCenter (
             worldWidth  * contentRandom.nextDouble ( ),
             worldHeight * contentRandom.nextDouble ( ) );
         }
       }

       for ( int  i = 0; i < ammoDumps; i++ )
       {
         AmmoDump  ammoDump = world.createAmmoDump (
           worldWidth  * contentRandom.nextDouble ( ),
           worldHeight * contentRandom.nextDouble ( ) );

         for ( int  j = 0; j < attemptsMax; j++ )
         {
           if ( !world.isBlocked ( ammoDump ) )
           {
             break;
           }

           ammoDump.setCenter (
             worldWidth  * contentRandom.nextDouble ( ),
             worldHeight * contentRandom.nextDouble ( ) );
         }
       }

       for ( int  i = 0; i < level; i++ )
       {
         Tank  tank = world.createTank (
           ( i + 1 ) * worldWidth,
           ( i + 1 ) * worldHeight,
           enemyColor );
       }

       for ( int  i = 0; i < level - 1; i++ )
       {
         Tank  tank = world.createTank (
           -( i + 10 ) * worldWidth,
           -( i + 10 ) * worldHeight,
           friendColor );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }