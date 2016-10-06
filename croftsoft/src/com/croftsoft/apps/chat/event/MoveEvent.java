     package com.croftsoft.apps.chat.event;

     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.core.animation.model.ModelId;
     import com.croftsoft.core.math.geom.PointXY;
     import com.croftsoft.core.math.geom.Point2DD;

     /*********************************************************************
     * A model is moving.
     *
     * @version
     *   2003-06-18
     * @since
     *   2003-06-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  MoveEvent
       extends AbstractEvent
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final ModelId  modelId;

     private final PointXY  origin;

     private final PointXY  destination;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  MoveEvent (
       ModelId  modelId,
       PointXY  origin,
       PointXY  destination )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.modelId = modelId );

       if ( origin != null )
       {
         origin = new Point2DD ( origin );
       }

       this.origin = origin;

       if ( destination != null )
       {
         destination = new Point2DD ( destination );
       }

       this.destination = destination;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public ModelId  getModelId     ( ) { return modelId;     }

     public PointXY  getOrigin      ( ) { return origin;      }

     public PointXY  getDestination ( ) { return destination; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  equals ( Object  other )
     //////////////////////////////////////////////////////////////////////
     {
       if ( other == null )
       {
         return false;
       }

       if ( other.getClass ( ) != MoveEvent.class )
       {
         return false;
       }

       MoveEvent  that = ( MoveEvent ) other;

       return this.modelId.equals ( that.modelId );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
