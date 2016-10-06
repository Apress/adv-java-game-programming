     package com.croftsoft.apps.mars.net.request;

     import com.croftsoft.core.math.geom.Point2DD;
     import com.croftsoft.core.math.geom.PointXY;

     /*********************************************************************
     * Request to fire tank.
     *
     * @version
     *   2003-05-13
     * @since
     *   2003-05-13
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  MoveRequest
       extends AbstractRequest
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final Point2DD  destination;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  MoveRequest ( String  playerName )
     //////////////////////////////////////////////////////////////////////
     {
       super ( playerName );

       destination = new Point2DD ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public PointXY  getDestination ( ) { return destination; }

     public void  setDestination (
       double  x,
       double  y )
     //////////////////////////////////////////////////////////////////////
     {
       destination.x = x;

       destination.y = y;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }