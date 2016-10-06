     package com.croftsoft.core.ai.astar;

     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * The A* algorithm.
     *
     * @version
     *   2003-05-09
     * @since
     *   2002-04-21
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AStar
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Cartographer  cartographer;

     private final List          openNodeInfoSortedList;

     private final Map           nodeToNodeInfoMap;

     //

     private NodeInfo  bestNodeInfo;

     private double    bestTotalCost;

     private NodeInfo  goalNodeInfo;

     private boolean   listEmpty;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AStar ( Cartographer  cartographer )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.cartographer = cartographer );

       nodeToNodeInfoMap = new HashMap ( );

       openNodeInfoSortedList = new LinkedList ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  isGoalFound ( ) { return goalNodeInfo != null; }

     public boolean  isListEmpty ( ) { return listEmpty; }

     public Iterator  getPath ( )
     //////////////////////////////////////////////////////////////////////
     {
       List  pathList = new LinkedList ( );

       NodeInfo  nodeInfo = goalNodeInfo;

       if ( nodeInfo == null )
       {
         nodeInfo = bestNodeInfo;
       }

       while ( nodeInfo != null )
       {
         NodeInfo  parentNodeInfo = nodeInfo.getParentNodeInfo ( );

         if ( parentNodeInfo != null )
         {
           pathList.add ( 0, nodeInfo.getNode ( ) );
         }

         nodeInfo = parentNodeInfo;
       }

       return pathList.iterator ( );
     }

     public Object  getFirstStep ( )
     //////////////////////////////////////////////////////////////////////
     {
       NodeInfo  nodeInfo = goalNodeInfo;

       if ( nodeInfo == null )
       {
         nodeInfo = bestNodeInfo;
       }

       Object node = null;

       while ( nodeInfo != null )
       {
         NodeInfo  parentNodeInfo = nodeInfo.getParentNodeInfo ( );

         if ( parentNodeInfo != null )
         {
           node = nodeInfo.getNode ( );
         }

         nodeInfo = parentNodeInfo;
       }

       return node;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  reset ( Object  startNode )
     //////////////////////////////////////////////////////////////////////
     {
       goalNodeInfo = null;

       listEmpty = false;

       openNodeInfoSortedList.clear ( );

       nodeToNodeInfoMap.clear ( );

       NodeInfo  nodeInfo = new NodeInfo ( startNode );

       nodeToNodeInfoMap.put ( startNode, nodeInfo );

       openNodeInfoSortedList.add ( nodeInfo );

       bestTotalCost = Double.POSITIVE_INFINITY;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  loop ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( openNodeInfoSortedList.isEmpty ( ) )
       {
         listEmpty = true;

         return false;
       }

       NodeInfo  nodeInfo
         = ( NodeInfo ) openNodeInfoSortedList.remove ( 0 );

       Object  node = nodeInfo.getNode ( );

       if ( cartographer.isGoalNode ( node ) )
       {
         if ( ( goalNodeInfo == null )
           || ( nodeInfo.getCostFromStart ( )
             < goalNodeInfo.getCostFromStart ( ) ) )
         {
           goalNodeInfo = nodeInfo;
         }         

         return false;
       }

       Iterator  iterator = cartographer.getAdjacentNodes ( node );

       while ( iterator.hasNext ( ) )
       {
         Object  adjacentNode = iterator.next ( );

         double  newCostFromStart
           = nodeInfo.getCostFromStart ( )
           + cartographer.getCostToAdjacentNode ( node, adjacentNode );

         NodeInfo  adjacentNodeInfo
           = ( NodeInfo ) nodeToNodeInfoMap.get ( adjacentNode );

         if ( adjacentNodeInfo == null )
         {
           adjacentNodeInfo = new NodeInfo ( adjacentNode );

           nodeToNodeInfoMap.put ( adjacentNode, adjacentNodeInfo );

           openNodeInfoSortedList.add ( adjacentNodeInfo );
         }
         else if (
           adjacentNodeInfo.getCostFromStart ( ) <= newCostFromStart )
         {
           continue;
         }

         adjacentNodeInfo.setParentNodeInfo ( nodeInfo );

         adjacentNodeInfo.setCostFromStart ( newCostFromStart );

         double  totalCost = newCostFromStart
           + cartographer.estimateCostToGoal ( adjacentNode );

         adjacentNodeInfo.setTotalCost ( totalCost );

         if ( totalCost < bestTotalCost )
         {
           bestNodeInfo = adjacentNodeInfo;

           bestTotalCost = totalCost;
         }

         Collections.sort ( openNodeInfoSortedList );
       }

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }