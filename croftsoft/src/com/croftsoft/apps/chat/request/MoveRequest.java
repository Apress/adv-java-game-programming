     package com.croftsoft.apps.chat.request;

     import com.croftsoft.core.math.geom.Point2DD;
     import com.croftsoft.core.math.geom.PointXY;
     import com.croftsoft.core.security.Authentication;

     /*********************************************************************
     * A request to move an avatar.
     *
     * @version
     *   2003-06-20
     * @since
     *   2003-06-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  MoveRequest
       extends CoalesceableRequest
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private PointXY  destination;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  MoveRequest (
       Authentication  authentication,
       PointXY         destination )
     //////////////////////////////////////////////////////////////////////
     {
       super ( authentication );

       if ( destination != null )
       {
         destination = new Point2DD ( destination );
       }

       this.destination = destination;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public PointXY  getDestination ( ) { return destination; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
