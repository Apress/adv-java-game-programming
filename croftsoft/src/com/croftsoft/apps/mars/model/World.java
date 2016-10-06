     package com.croftsoft.apps.mars.model;

     import java.awt.*;
     import java.util.*;

     import com.croftsoft.core.math.geom.PointXY;

     /*********************************************************************
     * Provides methods for manipulating the Models in the game.
     *
     * @version
     *   2003-05-11
     * @since
     *   2003-04-14
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  World
       extends WorldAccessor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  clear ( );

     public AmmoDump  createAmmoDump (
       double  centerX,
       double  centerY );

     public Obstacle  createObstacle (
       double     centerX,
       double     centerY,
       double     radius,
       double     radiusMin,
       Rectangle  driftBounds );

     public Tank  createTank (
       double  centerX,
       double  centerY,
       Color   color );

     public void  remove ( Model  model );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  prepare ( );

     public void  update ( double  timeDelta );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Fires a Bullet from the origin.
     *********************************************************************/
     public void  fireBullet (
       double  originX,
       double  originY,
       double  heading );

     /*********************************************************************
     * Gets all active AmmoDumps that contain the point.
     *********************************************************************/
     public AmmoDump [ ]  getAmmoDumps (
       PointXY       pointXY,
       AmmoDump [ ]  ammoDumps );

     /*********************************************************************
     * Gets all active and inactive Obstacles in the World.
     *********************************************************************/
     public Obstacle [ ]  getObstacles ( );

     /*********************************************************************
     * Gets all active and inactive Tanks in the World.
     *********************************************************************/
     public Tank [ ]  getTanks ( );

     /*********************************************************************
     * Gets the center of the active AmmoDump that is closest to the point.
     *********************************************************************/
     public PointXY  getClosestAmmoDumpCenter ( PointXY  pointXY );

     /*********************************************************************
     * Gets the center of the closest active tank that is not of the color.
     *********************************************************************/
     public PointXY  getClosestEnemyTankCenter (
        PointXY  pointXY,
        Color    friendColor );

     /*********************************************************************
     * Gets all active Damageables that overlap the shape.
     *********************************************************************/
     public Damageable [ ]  getDamageables (
       Shape           shape,
       Damageable [ ]  damageables );

     /*********************************************************************
     * Gets all active Impassables that overlap the shape.
     *********************************************************************/
     public Iterator  getImpassables (
       Shape  shape,
       Model  model );

     /*********************************************************************
     * Gets an active Model that contains the point.
     *
     * @param  classes
     *
     *   The returned Model will be an instance of one of the classes.
     *
     * @param  model
     *
     *   The Model to ignore, usually the one calling this method.
     *********************************************************************/
     public Model  getModel (
       PointXY    pointXY,
       Class [ ]  classes,
       Model      model );

     /*********************************************************************
     * Determines whether an active Impassable overlaps the model shape.
     *********************************************************************/
     public boolean  isBlocked ( Model  model );

     /*********************************************************************
     * Determines whether an active Impassable overlaps the shape.
     *
     * @param  model
     *
     *   The model to ignore.
     *********************************************************************/
     public boolean  isBlocked (
       Shape  shape,
       Model  model );

     public boolean  isBlockedByObstacle ( Shape  shape );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }