     package com.croftsoft.core.ai.astar;

     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.geom.Point2DD;
     import com.croftsoft.core.math.geom.PointXY;

     /*********************************************************************
     * Gradient grid cartographer for continuous space.
     *
     * The adjacent nodes are spaced farther apart as you move away from
     * the starting point.
     *
     * @version
     *   2003-05-10
     * @since
     *   2003-04-21
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  GradientCartographer
       implements Cartographer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final double  DEFAULT_INIT_STEP_SIZE = 1.0;

     public static final int     DEFAULT_DIRECTIONS     = 8;

     //

     private final List         adjacentList;

     private final double       initStepSize;

     private final SpaceTester  spaceTester;

     private final int          directions;

     //

     private PointXY  startPointXY;

     private PointXY  goalPointXY;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  GradientCartographer (
       SpaceTester  spaceTester,
       double       initStepSize,
       int          directions )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.spaceTester = spaceTester );

       this.initStepSize = initStepSize;

       this.directions   = directions;

       adjacentList = new ArrayList ( );
     }

     public  GradientCartographer ( SpaceTester  spaceTester )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         spaceTester,
         DEFAULT_INIT_STEP_SIZE,
         DEFAULT_DIRECTIONS );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setStartPointXY ( PointXY  startPointXY )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.startPointXY = startPointXY );
     }

     public void  setGoalPointXY ( PointXY  goalPointXY )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.goalPointXY = goalPointXY );
     }

     //////////////////////////////////////////////////////////////////////
     // interface Cartographer methods
     //////////////////////////////////////////////////////////////////////

     public double  estimateCostToGoal ( Object  node )
     //////////////////////////////////////////////////////////////////////
     {
       return ( ( PointXY ) node ).distanceXY ( goalPointXY );
     }

     public Iterator  getAdjacentNodes ( Object  node )
     //////////////////////////////////////////////////////////////////////
     {
       PointXY  pointXY = ( PointXY ) node;

       adjacentList.clear ( );

       double  distanceToGoal = pointXY.distanceXY ( goalPointXY );

       double  distanceFromStart = pointXY.distanceXY ( startPointXY );

       double  stepSize
         = ( ( int ) ( distanceFromStart / initStepSize ) ) * initStepSize;

       stepSize = Math.max ( stepSize, initStepSize );

       if ( distanceToGoal <= stepSize )
       {
         adjacentList.add ( goalPointXY );

         return adjacentList.iterator ( );
       }

       double  x = pointXY.getX ( );

       double  y = pointXY.getY ( );

       double  headingToGoal = Math.atan2 (
         goalPointXY.getY ( ) - y,
         goalPointXY.getX ( ) - x );

       for ( int  i = 0; i < directions; i++ )
       {
         double  heading = headingToGoal + i * 2.0 * Math.PI / directions;

         PointXY  step = new Point2DD (
           x + stepSize * Math.cos ( heading ),
           y + stepSize * Math.sin ( heading ) );

         if ( spaceTester.isSpaceAvailable ( step ) )
         {
           adjacentList.add ( step );
         }
       }

       return adjacentList.iterator ( );
     }

     public double  getCostToAdjacentNode (
       Object  fromNode,
       Object  toNode )
     //////////////////////////////////////////////////////////////////////
     {       
       return ( ( PointXY ) fromNode ).distanceXY ( ( PointXY ) toNode );
     }

     public boolean  isGoalNode ( Object  node )
     //////////////////////////////////////////////////////////////////////
     {
       return goalPointXY.distanceXY ( ( PointXY ) node ) == 0.0;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }