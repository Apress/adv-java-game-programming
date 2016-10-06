     package com.croftsoft.apps.chat.model.seri;

     import java.awt.Shape;

     import com.croftsoft.core.animation.model.ModelId;
     import com.croftsoft.core.animation.model.seri.SeriModel;
     import com.croftsoft.core.math.geom.Circle;
     import com.croftsoft.core.math.geom.Point2DD;
     import com.croftsoft.core.math.geom.PointXY;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.role.Consumer;

     import com.croftsoft.apps.chat.ChatConstants;
     import com.croftsoft.apps.chat.event.MoveEvent;
     import com.croftsoft.apps.chat.model.ChatModel;
     import com.croftsoft.apps.chat.model.ChatWorld;

     /*********************************************************************
     * The base class for a game world object Model.
     *
     * @version
     *   2003-06-17
     * @since
     *   2003-06-05
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  SeriChatModel
       extends SeriModel
       implements ChatModel
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final String  avatarType;

     private final Circle  circle;

     //

     private transient ChatWorld  chatWorld;

     private transient Consumer   eventConsumer;

     //

     private PointXY  destination;

     private boolean  updated;

     private boolean  active;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SeriChatModel (
       ModelId    modelId,
       Consumer   eventConsumer,
       ChatWorld  chatWorld,
       String     avatarType,
       double     x,
       double     y )
     //////////////////////////////////////////////////////////////////////
     {
       super ( modelId );

       setEventConsumer ( eventConsumer );

       NullArgumentException.check ( this.chatWorld = chatWorld );

       NullArgumentException.check ( this.avatarType = avatarType );

       circle = new Circle ( x, y, ChatConstants.RADIUS );

       active = true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String   getAvatarType ( ) { return avatarType; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  isActive   ( ) { return active;  }

     public double   getCenterX ( ) { return circle.getCenterX ( ); }

     public double   getCenterY ( ) { return circle.getCenterY ( ); }

     public Shape    getShape   ( ) { return circle;  }

     public boolean  isUpdated  ( ) { return updated; }

     public double   getZ       ( ) { return 0.0;     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setActive ( boolean  active )
     //////////////////////////////////////////////////////////////////////
     {
       this.active = active;
     }

     public void  setDestination ( PointXY  destination )
     //////////////////////////////////////////////////////////////////////
     {
       if ( destination != null )
       {
         destination = new Point2DD ( destination );
       }

       this.destination = destination;

       eventConsumer.consume (
         new MoveEvent ( modelId, circle.getCenter ( ), destination ) );
     }

     public void  setEventConsumer ( Consumer  eventConsumer )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.eventConsumer = eventConsumer );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setCenter (
       double  x,
       double  y )
     //////////////////////////////////////////////////////////////////////
     {
       circle.setCenter ( x, y );

       updated = true;
     }

     public void  prepare ( )
     //////////////////////////////////////////////////////////////////////
     {
       updated = false;
     }

     public void  update ( double  timeDelta )
     //////////////////////////////////////////////////////////////////////
     {
       updatePosition ( timeDelta );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private void  updatePosition ( double  timeDelta )
     //////////////////////////////////////////////////////////////////////
     {
       if ( destination == null )
       {
         return;
       }

       PointXY  center = circle.getCenter ( );

       double  centerX = center.getX ( );

       double  centerY = center.getY ( );

       double  deltaX = destination.getX ( ) - centerX;

       double  deltaY = destination.getY ( ) - centerY;

       if ( ( deltaX == 0.0 )
         && ( deltaY == 0.0 ) )
       {
         destination = null;

         return;
       }

       double  aimHeading = Math.atan2 ( deltaY, deltaX );

       if ( aimHeading < 0.0 )
       {
         aimHeading += 2.0 * Math.PI;
       }

       double  moveX
         = timeDelta * ChatConstants.SPEED * Math.cos ( aimHeading );

       double  moveY
         = timeDelta * ChatConstants.SPEED * Math.sin ( aimHeading );

       if ( Math.abs ( moveX ) > Math.abs ( deltaX ) )
       {
         moveX = deltaX;
       }
             
       if ( Math.abs ( moveY ) > Math.abs ( deltaY ) )
       {
         moveY = deltaY;
       }

       double  newX = centerX + moveX;

       double  newY = centerY + moveY;

       circle.setCenter ( newX, newY );

       if ( ( chatWorld == null )
         || !chatWorld.isBlocked ( circle, this ) )
       {
         updated = true;
       }
       else
       {
         circle.setCenter ( centerX, centerY );

         if ( chatWorld.isBlocked ( circle, this ) )
         {
           circle.setCenter ( newX, newY );

           updated = true;
         }
         else
         {
           setDestination ( null );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
