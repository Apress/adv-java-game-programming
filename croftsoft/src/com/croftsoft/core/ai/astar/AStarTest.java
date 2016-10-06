     package com.croftsoft.core.ai.astar;

     import java.awt.Point;
     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Testable;

     /*********************************************************************
     * An A* algorithm test.
     *
     * @version
     *   2003-05-09
     * @since
     *   2002-04-21
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AStarTest
       implements Cartographer, Testable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final int  MIN_X = -10;

     private static final int  MIN_Y = -10;

     private static final int  MAX_X =  10;

     private static final int  MAX_Y =  10;

     //

     private final Set    blockedSet;

     private final Point  goalPoint;

     private final Point  jumpPoint;

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
         // Finds its way around a wall.

         AStarTest  test1 = new AStarTest (
           new Point ( 4, 0 ),
           new Point [ ] {
             new Point ( 1,  1 ),
             new Point ( 1,  0 ),
             new Point ( 1, -1 ) } );

         // Trapped by walls.

         AStarTest  test2 = new AStarTest (
           new Point ( 4, 0 ),
           new Point [ ] {
             new Point ( -1, -1 ),
             new Point ( -1,  0 ),
             new Point ( -1,  1 ),
             new Point (  0, -1 ),
             new Point (  0,  1 ),
             new Point (  1, -1 ),
             new Point (  1,  0 ),
             new Point (  1,  1 ) } );

         // Goal enclosed by walls.

         AStarTest  test3 = new AStarTest (
           new Point ( 5, 0 ),
           new Point [ ] {
             new Point ( 4, -1 ),
             new Point ( 4,  0 ),
             new Point ( 4,  1 ),
             new Point ( 5, -1 ),
             new Point ( 5,  1 ),
             new Point ( 6, -1 ),
             new Point ( 6,  0 ),
             new Point ( 6,  1 ) } );

         // Goal enclosed by walls but teleport jump available.

         AStarTest  test4 = new AStarTest (
           new Point ( 4, 0 ),
           new Point [ ] {
             new Point ( 3, -1 ),
             new Point ( 3,  0 ),
             new Point ( 3,  1 ),
             new Point ( 4, -1 ),
             new Point ( 4,  1 ),
             new Point ( 5, -1 ),
             new Point ( 5,  0 ),
             new Point ( 5,  1 ) },
           new Point ( MIN_X, MIN_Y ) );

         // Goal clear on right but teleport jump on left closer.

         AStarTest  test5 = new AStarTest (
           new Point ( MAX_X, 0 ),
           new Point [ 0 ],
           new Point ( -3, 0 ) );

         return test ( test1 )
           &&   test ( test2 )
           &&   test ( test3 )
           &&   test ( test4 )
           &&   test ( test5 );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         return false;
       }
     }

     public static boolean  test ( AStarTest  test )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( "Testing..." );

       AStar  aStar = new AStar ( test );

       aStar.reset ( new Point ( 0, 0 ) );

       while ( true )
       {
         aStar.loop ( );

         if ( aStar.isListEmpty ( ) )
         {
           break;
         }
       }

       System.out.println ( "goalFound:  " + aStar.isGoalFound ( ) );

       Iterator  iterator = aStar.getPath ( );

       while ( iterator.hasNext ( ) )
       {
         System.out.println ( iterator.next ( ) );
       }

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AStarTest (
       Point      goalPoint,
       Point [ ]  blockedPoints,
       Point      jumpPoint )
     //////////////////////////////////////////////////////////////////////
     {
       this.goalPoint = goalPoint;

       blockedSet = new HashSet ( );

       for ( int  i = 0; i < blockedPoints.length; i++ )
       {
         blockedSet.add ( blockedPoints [ i ] );
       }

       this.jumpPoint = jumpPoint;
     }

     public  AStarTest (
       Point      goalPoint,
       Point [ ]  blockedPoints )
     //////////////////////////////////////////////////////////////////////
     {
       this ( goalPoint, blockedPoints, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public double  estimateCostToGoal ( Object  node )
     //////////////////////////////////////////////////////////////////////
     {
       return getCostToAdjacentNode ( node, goalPoint );
     }

     public Iterator  getAdjacentNodes ( Object  node )
     //////////////////////////////////////////////////////////////////////
     {
       Point  nodePoint = ( Point ) node;

       int  x = nodePoint.x;

       int  y = nodePoint.y;

       List  list = new ArrayList ( );

       if ( nodePoint.equals ( jumpPoint ) )
       {
         list.add ( goalPoint );

         return list.iterator ( );
       }

       for ( int  offsetX = -1; offsetX < 2; offsetX++ )
       {
         for ( int  offsetY = -1; offsetY < 2; offsetY++ )
         {
           if ( ( offsetX == 0 )
             && ( offsetY == 0 ) )
           {
             continue;
           }

           int  newX = x + offsetX;

           int  newY = y + offsetY;

           if ( ( newX < MIN_X )
             || ( newY < MIN_Y )
             || ( newX > MAX_X )
             || ( newY > MAX_Y ) )
           {
             continue;
           }

           Point  point = new Point ( newX, newY );

           if ( !blockedSet.contains ( point ) )
           {
             list.add ( point );
           }
         }
       }

       return list.iterator ( );
     }

     public double  getCostToAdjacentNode (
       Object  fromNode,
       Object  toNode )
     //////////////////////////////////////////////////////////////////////
     {
       if ( fromNode.equals ( jumpPoint ) )
       {
         return 0.0;
       }

       return ( ( Point ) fromNode ).distance ( ( Point ) toNode );
     }

     public boolean  isGoalNode ( Object  node )
     //////////////////////////////////////////////////////////////////////
     {
       return ( ( Point ) node ).equals ( goalPoint );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }