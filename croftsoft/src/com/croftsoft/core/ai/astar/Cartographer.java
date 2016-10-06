     package com.croftsoft.core.ai.astar;

     import java.util.Iterator;

     /*********************************************************************
     * An A* algorithm map maker.
     *
     * @version
     *   2003-04-29
     * @since
     *   2002-04-21
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  Cartographer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public double  estimateCostToGoal ( Object  node );

     public Iterator  getAdjacentNodes ( Object  node );      

     public double  getCostToAdjacentNode (
       Object  fromNode,
       Object  toNode );

     public boolean  isGoalNode ( Object  node );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }