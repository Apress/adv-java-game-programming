     package com.croftsoft.core.ai.astar;

     import com.croftsoft.core.math.geom.PointXY;

     /*********************************************************************
     * Tests whether a point in space is available as an adjacent node.
     *
     * @version
     *   2003-04-22
     * @since
     *   2003-04-22
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  SpaceTester
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public boolean  isSpaceAvailable ( PointXY  pointXY );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }