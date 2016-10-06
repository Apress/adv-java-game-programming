     package com.croftsoft.core.ai.astar;

     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.geom.Point2DD;
     import com.croftsoft.core.math.geom.PointXY;

     /*********************************************************************
     * Grid cartographer for continuous space.
     *
     * The nodes are spaced equally apart in the 8 cardinal directions.
     *
     * @version
     *   2003-05-10
     * @since
     *   2003-04-21
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  GridCartographer
       implements Cartographer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final double  DEFAULT_STEP_SIZE = 1.0;

     //

     private final List         adjacentList;

     private final double       stepSize;

     private final SpaceTester  spaceTester;

     //

     private PointXY  goalPointXY;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  GridCartographer (
       SpaceTester  spaceTester,
       double       stepSize )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.spaceTester = spaceTester );

       this.stepSize = stepSize;

       adjacentList = new ArrayList ( );
     }

     public  GridCartographer ( SpaceTester  spaceTester )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         spaceTester,
         DEFAULT_STEP_SIZE );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

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

       if ( distanceToGoal <= stepSize )
       {
         adjacentList.add ( goalPointXY );

         return adjacentList.iterator ( );
       }

       double  x = pointXY.getX ( );

       double  y = pointXY.getY ( );

       for ( int  ix = -1; ix < 2; ix++ )
       {
         for ( int  iy = -1; iy < 2; iy++ )
         {
           if ( ( ix == 0 )
             && ( iy == 0 ) )
           {
             continue;
           }

           PointXY  step = new Point2DD (
             ( ( int ) ( x / stepSize ) + ix ) * stepSize,
             ( ( int ) ( y / stepSize ) + iy ) * stepSize );

           if ( spaceTester.isSpaceAvailable ( step ) )
           {
             adjacentList.add ( step );
           }
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