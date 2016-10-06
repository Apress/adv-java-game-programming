     package com.croftsoft.apps.mars.ai;

     import java.util.*;

     import com.croftsoft.core.ai.astar.Cartographer;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.geom.Point2DD;
     import com.croftsoft.core.math.geom.PointXY;

     /*********************************************************************
     * Used with the Tank A* implementation.
     *
     * @version
     *   2003-05-10
     * @since
     *   2003-03-21
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  TankCartographer
       implements Cartographer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final double          initStepSize;

     private final int             directions;

     private final StateSpaceNode  startStateSpaceNode;

     private final StateSpaceNode  goalStateSpaceNode;

     private final List            adjacentList;

     //

     private TankConsole  tankConsole;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  TankCartographer (
       double  initStepSize,
       int     directions )
     //////////////////////////////////////////////////////////////////////
     {
       this.initStepSize   = initStepSize;

       this.directions     = directions;

       startStateSpaceNode = new StateSpaceNode ( );

       goalStateSpaceNode  = new StateSpaceNode ( );

       adjacentList        = new ArrayList ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setGoalPointXY ( PointXY  goalPointXY )
     //////////////////////////////////////////////////////////////////////
     {
       goalStateSpaceNode.setPointXY ( goalPointXY );
     }

     public void  setStartStateSpaceNode (
       StateSpaceNode  startStateSpaceNode )
     //////////////////////////////////////////////////////////////////////
      {
       this.startStateSpaceNode.set ( startStateSpaceNode );
     }

     public void  setTankConsole ( TankConsole  tankConsole )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.tankConsole = tankConsole );
     }

     //////////////////////////////////////////////////////////////////////
     // interface Cartographer methods
     //////////////////////////////////////////////////////////////////////

     public double  estimateCostToGoal ( Object  node )
     //////////////////////////////////////////////////////////////////////
     {
       return getCostToAdjacentNode ( node, goalStateSpaceNode );
     }

     public Iterator  getAdjacentNodes ( Object  node )
     //////////////////////////////////////////////////////////////////////
     {
       StateSpaceNode  stateSpaceNode = ( StateSpaceNode ) node;

       adjacentList.clear ( );

       double  distanceToGoal
         = stateSpaceNode.distance ( goalStateSpaceNode  );

       double  distanceFromStart
         = stateSpaceNode.distance ( startStateSpaceNode );

       double  stepSize
         = ( ( int ) ( distanceFromStart / initStepSize ) ) * initStepSize;

       stepSize = Math.max ( stepSize, initStepSize );

       if ( distanceToGoal <= stepSize )
       {
         adjacentList.add ( goalStateSpaceNode );

         return adjacentList.iterator ( );
       }

       PointXY  pointXY = stateSpaceNode.getPointXY ( );

       double  x = pointXY.getX ( );

       double  y = pointXY.getY ( );

       PointXY  goalPointXY = goalStateSpaceNode.getPointXY ( );

       double  headingToGoal = Math.atan2 (
         goalPointXY.getY ( ) - y,
         goalPointXY.getX ( ) - x );

       for ( int  i = 0; i < directions; i++ )
       {
         double  heading = headingToGoal + i * 2.0 * Math.PI / directions;

         StateSpaceNode  adjacentStateSpaceNode = new StateSpaceNode (
           new Point2DD (
             x + stepSize * Math.cos ( heading ),
             y + stepSize * Math.sin ( heading ) ),
           heading );

         if ( tankConsole.isSpaceAvailable (
           adjacentStateSpaceNode.getPointXY ( ) ) )
         {
           adjacentList.add ( adjacentStateSpaceNode );
         }
       }
       
       return adjacentList.iterator ( );
     }

     public double  getCostToAdjacentNode (
       Object  fromNode,
       Object  toNode )
     //////////////////////////////////////////////////////////////////////
     {
       StateSpaceNode  fromStateSpaceNode = ( StateSpaceNode ) fromNode;

       StateSpaceNode  toStateSpaceNode   = ( StateSpaceNode ) toNode;

       double  rotation = fromStateSpaceNode.rotation ( toStateSpaceNode );

       rotation = Math.abs ( rotation );

       double  bodyRotationSpeed = tankConsole.getBodyRotationSpeed ( );

       double  rotationTime = rotation / bodyRotationSpeed;

       double  travelTime = calculateTravelTime ( fromNode, toNode );

       double  totalTime = travelTime + rotationTime;

       return totalTime;
     }

     public boolean  isGoalNode ( Object  node )
     //////////////////////////////////////////////////////////////////////
     {
       return 
         goalStateSpaceNode.distance ( ( StateSpaceNode ) node ) == 0.0;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private double  calculateTravelTime (
       Object  fromNode,
       Object  toNode )
     //////////////////////////////////////////////////////////////////////
     {
       StateSpaceNode  fromStateSpaceNode = ( StateSpaceNode ) fromNode;

       StateSpaceNode  toStateSpaceNode   = ( StateSpaceNode ) toNode;

       double  distance = fromStateSpaceNode.distance ( toStateSpaceNode );

       return distance / tankConsole.getTankSpeed ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }