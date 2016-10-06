     package com.croftsoft.apps.mars.net;

     import java.awt.Color;
     import java.io.*;

     import com.croftsoft.core.beans.XmlBeanCoder;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Testable;

     import com.croftsoft.apps.mars.model.seri.SeriAmmoDump;

     /*********************************************************************
     * Initializer for Mars Game.
     *
     * @version
     *   2003-06-12
     * @since
     *   2003-04-30
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  GameInit
       implements GameInitAccessor, Serializable, Testable
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

     public static final long    DEFAULT_PLAYER_TIMEOUT      = 10 * 1000;

     public static final double  DEFAULT_AMMO_DUMP_GROWTH
       = SeriAmmoDump.Shared.DEFAULT_AMMO_GROWTH_RATE;

     public static final double  DEFAULT_AMMO_DUMP_MAX
       = SeriAmmoDump.Shared.DEFAULT_AMMO_MAX;

     public static final double  DEFAULT_AMMO_DUMP_EXPLOSION
       = SeriAmmoDump.Shared.DEFAULT_EXPLOSION_FACTOR;

     public static final double  DEFAULT_AMMO_DUMP_Z
       = SeriAmmoDump.Shared.DEFAULT_Z;

     //

     private double               timeFactorDefault;

     private long                 randomSeed;

     private double               initialPlayerX;

     private double               initialPlayerY;

     private int                  ammoDumps;

     private int                  obstacles;

     private int                  worldWidth;

     private int                  worldHeight;

     private Color                friendColor;

     private Color                enemyColor;

     private double               timeDeltaMax;

     private int                  attemptsMax;

     private double               obstacleRadiusMax;

     private double               obstacleRadiusMin;

     private long                 playerTimeout;

     private double               ammoDumpGrowth;

     private double               ammoDumpMax;

     private double               ammoDumpExplosion;

     private double               ammoDumpZ;

     //////////////////////////////////////////////////////////////////////
     // test methods
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       if ( args.length > 0 )
       {
         createTemplateXmlFile ( args [ 0 ] );
       }
       else
       {
         System.out.println ( test ( args ) );
       }
     }

     public static boolean  test ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         final int  TEST_WORLD_WIDTH = 2 * DEFAULT_WORLD_WIDTH;

         GameInit  gameInit1 = createDefaultGameInit ( );

         System.out.println (
           "world width = " + gameInit1.getWorldWidth ( ) );

         gameInit1.setWorldWidth ( TEST_WORLD_WIDTH );

         ByteArrayOutputStream  byteArrayOutputStream
           = new ByteArrayOutputStream ( );

         XmlBeanCoder.encodeAsXml ( gameInit1, byteArrayOutputStream );

         byte [ ]  xmlBytes = byteArrayOutputStream.toByteArray ( );

         System.out.println ( new String ( xmlBytes ) );

         GameInit  gameInit2
           = ( GameInit ) XmlBeanCoder.decodeFromXml ( xmlBytes );

         System.out.println (
           "world width = " + gameInit2.getWorldWidth ( ) );

         return gameInit2.equals ( gameInit1 );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         return false;
       }
     }

     //////////////////////////////////////////////////////////////////////
     // other static methods
     //////////////////////////////////////////////////////////////////////

     public static GameInit  createDefaultGameInit ( )
     //////////////////////////////////////////////////////////////////////
     {
       GameInit  gameInit = new GameInit ( );

       gameInit.setTimeFactorDefault  ( DEFAULT_TIME_FACTOR           );

       gameInit.setRandomSeed         ( DEFAULT_RANDOM_SEED           );

       gameInit.setInitialPlayerX     ( DEFAULT_INITIAL_PLAYER_X      );

       gameInit.setInitialPlayerY     ( DEFAULT_INITIAL_PLAYER_Y      );

       gameInit.setAmmoDumps          ( DEFAULT_AMMO_DUMPS            );

       gameInit.setObstacles          ( DEFAULT_OBSTACLES             );

       gameInit.setWorldWidth         ( DEFAULT_WORLD_WIDTH           );

       gameInit.setWorldHeight        ( DEFAULT_WORLD_HEIGHT          );

       gameInit.setFriendColor        ( DEFAULT_FRIEND_COLOR          );

       gameInit.setEnemyColor         ( DEFAULT_ENEMY_COLOR           );

       gameInit.setTimeDeltaMax       ( DEFAULT_TIME_DELTA_MAX        );

       gameInit.setAttemptsMax        ( DEFAULT_ATTEMPTS_MAX          );

       gameInit.setObstacleRadiusMax  ( DEFAULT_OBSTACLE_RADIUS_MAX   );

       gameInit.setObstacleRadiusMin  ( DEFAULT_OBSTACLE_RADIUS_MIN   );

       gameInit.setPlayerTimeout      ( DEFAULT_PLAYER_TIMEOUT        );

       gameInit.setAmmoDumpGrowth     ( DEFAULT_AMMO_DUMP_GROWTH      );

       gameInit.setAmmoDumpMax        ( DEFAULT_AMMO_DUMP_MAX         );

       gameInit.setAmmoDumpExplosion  ( DEFAULT_AMMO_DUMP_EXPLOSION   );

       gameInit.setAmmoDumpZ          ( DEFAULT_AMMO_DUMP_Z           );

       return gameInit;
     }

     public static void createTemplateXmlFile ( String  filename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       XmlBeanCoder.saveToXmlFile ( createDefaultGameInit ( ), filename );
     }

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  GameInit ( )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public  GameInit ( GameInitAccessor  gameInitAccessor )
     //////////////////////////////////////////////////////////////////////
     {
       setTimeFactorDefault ( gameInitAccessor.getTimeFactorDefault  ( ) );

       setRandomSeed        ( gameInitAccessor.getRandomSeed         ( ) );

       setInitialPlayerX    ( gameInitAccessor.getInitialPlayerX     ( ) );

       setInitialPlayerY    ( gameInitAccessor.getInitialPlayerY     ( ) );

       setAmmoDumps         ( gameInitAccessor.getAmmoDumps          ( ) );

       setObstacles         ( gameInitAccessor.getObstacles          ( ) );

       setWorldWidth        ( gameInitAccessor.getWorldWidth         ( ) );

       setWorldHeight       ( gameInitAccessor.getWorldHeight        ( ) );

       setFriendColor       ( gameInitAccessor.getFriendColor        ( ) );

       setEnemyColor        ( gameInitAccessor.getEnemyColor         ( ) );

       setTimeDeltaMax      ( gameInitAccessor.getTimeDeltaMax       ( ) );

       setAttemptsMax       ( gameInitAccessor.getAttemptsMax        ( ) );

       setObstacleRadiusMax ( gameInitAccessor.getObstacleRadiusMax  ( ) );

       setObstacleRadiusMin ( gameInitAccessor.getObstacleRadiusMin  ( ) );

       setPlayerTimeout     ( gameInitAccessor.getPlayerTimeout      ( ) );

       setAmmoDumpGrowth    ( gameInitAccessor.getAmmoDumpGrowth     ( ) );

       setAmmoDumpMax       ( gameInitAccessor.getAmmoDumpMax        ( ) );

       setAmmoDumpExplosion ( gameInitAccessor.getAmmoDumpExplosion  ( ) );

       setAmmoDumpZ         ( gameInitAccessor.getAmmoDumpZ          ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public double  getTimeFactorDefault ( ) { return timeFactorDefault; }

     public long    getRandomSeed        ( ) { return randomSeed;        }

     public double  getInitialPlayerX    ( ) { return initialPlayerX;    }

     public double  getInitialPlayerY    ( ) { return initialPlayerY;    }

     public int     getAmmoDumps         ( ) { return ammoDumps;         }

     public int     getObstacles         ( ) { return obstacles;         }

     public int     getWorldWidth        ( ) { return worldWidth;        }

     public int     getWorldHeight       ( ) { return worldHeight;       }

     public Color   getFriendColor       ( ) { return friendColor;       }

     public Color   getEnemyColor        ( ) { return enemyColor;        }

     public double  getTimeDeltaMax      ( ) { return timeDeltaMax;      }

     public int     getAttemptsMax       ( ) { return attemptsMax;       }

     public double  getObstacleRadiusMax ( ) { return obstacleRadiusMax; }

     public double  getObstacleRadiusMin ( ) { return obstacleRadiusMin; }

     public long    getPlayerTimeout     ( ) { return playerTimeout;     }

     public double  getAmmoDumpGrowth    ( ) { return ammoDumpGrowth;    }

     public double  getAmmoDumpMax       ( ) { return ammoDumpMax;       }

     public double  getAmmoDumpExplosion ( ) { return ammoDumpExplosion; }

     public double  getAmmoDumpZ         ( ) { return ammoDumpZ;         }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setTimeFactorDefault ( double  timeFactorDefault )
     //////////////////////////////////////////////////////////////////////
     {
       this.timeFactorDefault = timeFactorDefault;
     }

     public void  setRandomSeed ( long  randomSeed )
     //////////////////////////////////////////////////////////////////////
     {
       this.randomSeed = randomSeed;
     }

     public void  setInitialPlayerX ( double  initialPlayerX )
     //////////////////////////////////////////////////////////////////////
     {
       this.initialPlayerX = initialPlayerX;
     }

     public void  setInitialPlayerY ( double  initialPlayerY )
     //////////////////////////////////////////////////////////////////////
     {
       this.initialPlayerY = initialPlayerY;
     }

     public void  setAmmoDumps ( int  ammoDumps )
     //////////////////////////////////////////////////////////////////////
     {
       this.ammoDumps = ammoDumps;
     }

     public void  setObstacles ( int  obstacles )
     //////////////////////////////////////////////////////////////////////
     {
       this.obstacles = obstacles;
     }

     public void  setWorldWidth ( int  worldWidth )
     //////////////////////////////////////////////////////////////////////
     {
       this.worldWidth = worldWidth;
     }

     public void  setWorldHeight ( int  worldHeight )
     //////////////////////////////////////////////////////////////////////
     {
       this.worldHeight = worldHeight;
     }

     public void  setFriendColor ( Color  friendColor )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.friendColor = friendColor );
     }

     public void  setEnemyColor ( Color  enemyColor )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.enemyColor = enemyColor );
     }

     public void  setTimeDeltaMax ( double  timeDeltaMax )
     //////////////////////////////////////////////////////////////////////
     {
       this.timeDeltaMax = timeDeltaMax;
     }

     public void  setAttemptsMax ( int  attemptsMax )
     //////////////////////////////////////////////////////////////////////
     {
       this.attemptsMax = attemptsMax;
     }

     public void  setObstacleRadiusMax ( double  obstacleRadiusMax )
     //////////////////////////////////////////////////////////////////////
     {
       this.obstacleRadiusMax = obstacleRadiusMax;
     }

     public void  setObstacleRadiusMin ( double  obstacleRadiusMin )
     //////////////////////////////////////////////////////////////////////
     {
       this.obstacleRadiusMin = obstacleRadiusMin;
     }

     public void  setPlayerTimeout ( long  playerTimeout )
     //////////////////////////////////////////////////////////////////////
     {
       this.playerTimeout = playerTimeout;
     }

     public void  setAmmoDumpGrowth ( double  ammoDumpGrowth )
     //////////////////////////////////////////////////////////////////////
     {
       this.ammoDumpGrowth = ammoDumpGrowth;
     }

     public void  setAmmoDumpMax ( double  ammoDumpMax )
     //////////////////////////////////////////////////////////////////////
     {
       this.ammoDumpMax = ammoDumpMax;
     }

     public void  setAmmoDumpExplosion ( double  ammoDumpExplosion )
     //////////////////////////////////////////////////////////////////////
     {
       this.ammoDumpExplosion = ammoDumpExplosion;
     }

     public void  setAmmoDumpZ ( double  ammoDumpZ )
     //////////////////////////////////////////////////////////////////////
     {
       this.ammoDumpZ = ammoDumpZ;
     }

     //////////////////////////////////////////////////////////////////////
     // overridden object methods
     //////////////////////////////////////////////////////////////////////

     public boolean  equals ( Object  other )
     //////////////////////////////////////////////////////////////////////
     {
       if ( other == null )
       {
         return false;
       }

       if ( other.getClass ( ) != getClass ( ) )
       {
         return false;
       }

       GameInit  otherGameInit = ( GameInit ) other;

       return
            ( timeFactorDefault == otherGameInit.timeFactorDefault )
         && ( randomSeed        == otherGameInit.randomSeed        )
         && ( initialPlayerX    == otherGameInit.initialPlayerX    )
         && ( initialPlayerY    == otherGameInit.initialPlayerY    )
         && ( ammoDumps         == otherGameInit.ammoDumps         )
         && ( obstacles         == otherGameInit.obstacles         )
         && ( worldWidth        == otherGameInit.worldWidth        )
         && ( worldHeight       == otherGameInit.worldHeight       )
         && friendColor.equals ( otherGameInit.friendColor )
         && enemyColor.equals  ( otherGameInit.enemyColor  )
         && ( timeDeltaMax      == otherGameInit.timeDeltaMax      )
         && ( attemptsMax       == otherGameInit.attemptsMax       )
         && ( obstacleRadiusMax == otherGameInit.obstacleRadiusMax )
         && ( obstacleRadiusMin == otherGameInit.obstacleRadiusMin )
         && ( playerTimeout     == otherGameInit.playerTimeout     )
         && ( ammoDumpGrowth    == otherGameInit.ammoDumpGrowth    )
         && ( ammoDumpMax       == otherGameInit.ammoDumpMax       )
         && ( ammoDumpExplosion == otherGameInit.ammoDumpExplosion )
         && ( ammoDumpZ         == otherGameInit.ammoDumpZ         );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
