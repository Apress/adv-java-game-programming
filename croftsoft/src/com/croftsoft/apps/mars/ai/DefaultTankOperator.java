     package com.croftsoft.apps.mars.ai;

     import java.io.Serializable;
     import java.util.*;

     import com.croftsoft.core.ai.astar.AStar;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.geom.Circle;
     import com.croftsoft.core.math.geom.Point2DD;
     import com.croftsoft.core.math.geom.PointXY;
     import com.croftsoft.core.math.geom.ShapeLib;

     /*********************************************************************
     * Default TankOperator implementation.
     *
     * @version
     *   2003-05-10
     * @since
     *   2003-03-21
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  DefaultTankOperator
       implements TankOperator, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     /** Probability of firing during one second of time. */
     private static final double  FIRING_PROBABILITY = 1.0;

     /** Probability of drifting during one second of time. */
     private static final double  DRIFT_PROBABILITY  = 0.1;

     private static final int     A_STAR_LOOPS = 100;

     private static final double  STEP_SIZE = 10.0;

     private static final int     DIRECTIONS = 8;

     //

     private final Point2DD          center;

     private final Point2DD          destination;

     private final Random            random;

     private final AStar             aStar;

     private final TankCartographer  tankCartographer;

     private final StateSpaceNode    startStateSpaceNode;

     //

     private TankConsole  tankConsole;

     private PointXY      enemyCenter;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  DefaultTankOperator ( Random  random )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.random = random );

       center      = new Point2DD ( );

       destination = new Point2DD ( );

       tankCartographer = new TankCartographer ( STEP_SIZE, DIRECTIONS );

       aStar = new AStar ( tankCartographer );

       startStateSpaceNode = new StateSpaceNode ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  fire ( )
     //////////////////////////////////////////////////////////////////////
     {
// do something here
     }

     public void  go ( PointXY  destination )
     //////////////////////////////////////////////////////////////////////
     {
// do something here
     }

     public void  setTankConsole ( TankConsole  tankConsole )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.tankConsole = tankConsole );

       tankCartographer.setTankConsole ( tankConsole );
     }

     public void  update ( double  timeDelta )
     //////////////////////////////////////////////////////////////////////
     {
       ShapeLib.getCenter ( tankConsole.getShape ( ), center );

       enemyCenter = tankConsole.getClosestEnemyTankCenter ( );

       tankConsole.rotateTurret ( enemyCenter );

       if ( tankConsole.getAmmo ( ) < 1.0 )
       {
         PointXY  ammoDumpCenter
           = tankConsole.getClosestAmmoDumpCenter ( );

         if ( ammoDumpCenter != null )
         {
           tankConsole.go ( getFirstStep ( ammoDumpCenter ) );
         }

         return;
       }

       if ( enemyCenter != null )
       {         
         tankConsole.go ( getFirstStep ( enemyCenter ) );

         if ( random.nextDouble ( ) < timeDelta * FIRING_PROBABILITY )
         {
           tankConsole.fire ( );
         }

         return;
       }

       if ( random.nextDouble ( ) < timeDelta * DRIFT_PROBABILITY )
       {
         destination.setXY (
           center.x + 2 * random.nextDouble ( ) - 1,
           center.y + 2 * random.nextDouble ( ) - 1 );
         
         tankConsole.go ( destination );
       }

       if ( random.nextDouble ( ) < timeDelta * FIRING_PROBABILITY )
       {
         tankConsole.fire ( );
       }
     }

     public Iterator  getPath ( ) { return aStar.getPath ( ); }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private PointXY  getFirstStep ( PointXY  destination )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( destination );

       startStateSpaceNode.setPointXY ( center );

       startStateSpaceNode.setHeading ( tankConsole.getBodyHeading ( ) );

       tankCartographer.setStartStateSpaceNode ( startStateSpaceNode );

       aStar.reset ( startStateSpaceNode );

       tankCartographer.setGoalPointXY ( destination );

       for ( int  i = 0; i < A_STAR_LOOPS; i++ )
       {
         if ( !aStar.loop ( ) )
         {
           break;
         }
       }

       if ( !aStar.isGoalFound ( ) )
       {
         return destination;
       }

       StateSpaceNode  stateSpaceNode
         = ( StateSpaceNode ) aStar.getFirstStep ( );

       if ( stateSpaceNode == null )
       {
         return destination;
       }

       return stateSpaceNode.getPointXY ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }