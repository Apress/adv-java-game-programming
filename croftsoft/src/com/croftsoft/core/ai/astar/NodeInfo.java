     package com.croftsoft.core.ai.astar;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * A* algorithm node information.
     *
     * @version
     *   2003-05-09
     * @since
     *   2002-04-21
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  NodeInfo
       implements Comparable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Object  node;

     //

     private double    costFromStart;

     private NodeInfo  parentNodeInfo;

     private double    totalCost;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  NodeInfo ( Object  node )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.node = node );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public double    getCostFromStart  ( ) { return costFromStart;  }

     public Object    getNode           ( ) { return node;           }

     public NodeInfo  getParentNodeInfo ( ) { return parentNodeInfo; }

     public double    getTotalCost      ( ) { return totalCost;      }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setCostFromStart ( double  costFromStart )
     //////////////////////////////////////////////////////////////////////
     {
       this.costFromStart = costFromStart;
     }

     public void  setParentNodeInfo ( NodeInfo  parentNodeInfo )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.parentNodeInfo = parentNodeInfo );
     }

     public void  setTotalCost ( double  totalCost )
     //////////////////////////////////////////////////////////////////////
     {
       this.totalCost = totalCost;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int  compareTo ( Object  other )
     //////////////////////////////////////////////////////////////////////
     {
       NodeInfo  otherNodeInfo = ( NodeInfo ) other;

       double  otherTotalCost = otherNodeInfo.totalCost;

       if ( totalCost < otherTotalCost )
       {
         return -1;
       }

       if ( totalCost > otherTotalCost )
       {
         return 1;
       }

       return 0;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }