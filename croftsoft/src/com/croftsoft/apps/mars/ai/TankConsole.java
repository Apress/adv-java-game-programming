     package com.croftsoft.apps.mars.ai;

     import java.awt.Shape;

     import com.croftsoft.core.ai.astar.SpaceTester;
     import com.croftsoft.core.math.geom.PointXY;

     import com.croftsoft.apps.mars.model.TankAccessor;

     /*********************************************************************
     * The tank console.
     *
     * @version
     *   2003-04-29
     * @since
     *   2003-03-29
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  TankConsole
       extends TankAccessor, SpaceTester
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public int     getAmmo              ( );

     public double  getBodyHeading       ( );

     public double  getBodyRotationSpeed ( );

     public Shape   getShape             ( );

     public double  getTankSpeed         ( );

     public double  getTurretHeading     ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public PointXY  getClosestAmmoDumpCenter  ( );

     public PointXY  getClosestEnemyTankCenter ( );

     //

     public void  fire ( );

     public void  go ( PointXY  destination );

     public void  rotateTurret ( PointXY  targetPointXY );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
