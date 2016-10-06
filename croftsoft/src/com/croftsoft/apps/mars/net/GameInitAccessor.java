     package com.croftsoft.apps.mars.net;

     import java.awt.Color;

     import com.croftsoft.apps.mars.model.seri.SeriAmmoDump;

     /*********************************************************************
     * Accessor interface for GameInit.
     *
     * @version
     *   2003-05-30
     * @since
     *   2003-05-30
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  GameInitAccessor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public double  getTimeFactorDefault ( );

     public long    getRandomSeed        ( );

     public double  getInitialPlayerX    ( );

     public double  getInitialPlayerY    ( );

     public int     getAmmoDumps         ( );

     public int     getObstacles         ( );

     public int     getWorldWidth        ( );

     public int     getWorldHeight       ( );

     public Color   getFriendColor       ( );

     public Color   getEnemyColor        ( );

     public double  getTimeDeltaMax      ( );

     public int     getAttemptsMax       ( );

     public double  getObstacleRadiusMax ( );

     public double  getObstacleRadiusMin ( );

     public long    getPlayerTimeout     ( );

     public double  getAmmoDumpGrowth    ( );

     public double  getAmmoDumpMax       ( );

     public double  getAmmoDumpExplosion ( );

     public double  getAmmoDumpZ         ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
