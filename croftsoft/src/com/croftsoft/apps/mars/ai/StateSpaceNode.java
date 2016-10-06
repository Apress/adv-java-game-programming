     package com.croftsoft.apps.mars.ai;

     import com.croftsoft.core.math.geom.Point2DD;
     import com.croftsoft.core.math.geom.PointXY;

     /*********************************************************************
     * Used with the A* path-finding implementation.
     *
     * @version
     *   2003-04-29
     * @since
     *   2003-04-29
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  StateSpaceNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Point2DD  point2DD;

     //

     private double  heading;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  StateSpaceNode (
       PointXY  pointXY,
       double   heading )
     //////////////////////////////////////////////////////////////////////
     {
       point2DD = new Point2DD ( );

       setPointXY ( pointXY );

       setHeading ( heading );
     }

     public  StateSpaceNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       point2DD = new Point2DD ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public double   getHeading ( ) { return heading;  }

     public PointXY  getPointXY ( ) { return point2DD; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  set ( StateSpaceNode  stateSpaceNode )
     //////////////////////////////////////////////////////////////////////
     {
       setHeading ( stateSpaceNode.getHeading ( ) );

       setPointXY ( stateSpaceNode.getPointXY ( ) );
     }

     public void  setHeading ( double  heading )
     //////////////////////////////////////////////////////////////////////
     {
       while ( heading < 0.0 )
       {
         heading += 2.0 * Math.PI;
       }

       while ( heading > 2.0 * Math.PI )
       {
         heading -= 2.0 * Math.PI;
       }

       this.heading = heading;         
     }

     public void  setPointXY ( PointXY  pointXY )
     //////////////////////////////////////////////////////////////////////
     {
       point2DD.setXY ( pointXY );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public double  distance ( StateSpaceNode  otherStateSpaceNode )
     //////////////////////////////////////////////////////////////////////
     {
       return point2DD.distance ( otherStateSpaceNode.point2DD );
     }

     public double  rotation ( StateSpaceNode  otherStateSpaceNode )
     //////////////////////////////////////////////////////////////////////
     {
// this needs to be fixed

       double  otherHeading = otherStateSpaceNode.heading;

       double  headingDelta = otherHeading - heading;

       if ( headingDelta < -Math.PI )
       {
         headingDelta = ( otherHeading + 2.0 * Math.PI ) - heading;
       }
       else if ( headingDelta > Math.PI )
       {
         headingDelta = ( otherHeading - 2.0 * Math.PI ) - heading;
       }

       return headingDelta;
     }

     public String  toString ( )
     //////////////////////////////////////////////////////////////////////
     {
       return point2DD.toString ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }