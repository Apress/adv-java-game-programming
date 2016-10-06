     package com.croftsoft.apps.mars.model.seri;

     import java.awt.*;
     import java.awt.geom.Ellipse2D;
     import java.util.Random;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.geom.Circle;
     import com.croftsoft.core.math.geom.PointXY;

     import com.croftsoft.apps.mars.model.Obstacle;
     import com.croftsoft.apps.mars.model.World;

     /*********************************************************************
     * An obstacle.
     *
     * @version
     *   2003-05-11
     * @since
     *   2003-03-30
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SeriObstacle
       extends SeriModel
       implements Obstacle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private static final double  Z          = 0.2;

     private static final double  DRIFT_RATE = 1.0;

     private static final double  SPEED_MAX  = 1.0;

     //

     private final World      world;

     private final Circle     circle;

     private final Random     random;

     private final Rectangle  driftBounds;

     private final double     radiusMin;

     //

     private boolean  active;

     private boolean  updated;

     private double   velocityX;

     private double   velocityY;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  SeriObstacle (
       World      world,
       double     centerX,
       double     centerY,
       double     radius,
       double     radiusMin,
       Random     random,
       Rectangle  driftBounds )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.world = world );

       circle = new Circle ( centerX, centerY, radius );

       this.radiusMin = radiusMin;

       NullArgumentException.check ( this.random = random );

       NullArgumentException.check ( this.driftBounds = driftBounds );

       active = true;

       updated = true;
     }

     //////////////////////////////////////////////////////////////////////
     // interface Model methods
     //////////////////////////////////////////////////////////////////////

     public boolean  isActive  ( ) { return active;  }

     public Shape    getShape  ( ) { return circle;  }

     public boolean  isUpdated ( ) { return updated; }

     public double   getZ      ( ) { return Z;       }

     public void  setCenter (
       double  x,
       double  y )
     //////////////////////////////////////////////////////////////////////
     {
       circle.setCenter ( x, y );       
     }

     public void  prepare ( )
     //////////////////////////////////////////////////////////////////////
     {
       updated = false;
     }

     public void  update ( double  timeDelta )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !active )
       {
         return;
       }

       velocityX
         += DRIFT_RATE * timeDelta * ( 2.0 * random.nextDouble ( ) - 1.0 );

       velocityY
         += DRIFT_RATE * timeDelta * ( 2.0 * random.nextDouble ( ) - 1.0 );

       if ( velocityX > SPEED_MAX )
       {
         velocityX = SPEED_MAX;
       }
       else if ( velocityX < -SPEED_MAX )
       {
         velocityX = -SPEED_MAX;
       }

       if ( velocityY > SPEED_MAX )
       {
         velocityY = SPEED_MAX;
       }
       else if ( velocityY < -SPEED_MAX )
       {
         velocityY = -SPEED_MAX;
       }

       PointXY  center = circle.getCenter ( );

       double  oldCenterX = center.getX ( );

       double  oldCenterY = center.getY ( );

       double  destinationX = oldCenterX + velocityX * timeDelta;

       double  destinationY = oldCenterY + velocityY * timeDelta;

       double  radius = circle.getRadius ( );

       double  minX = driftBounds.getX      ( ) + radius;

       double  minY = driftBounds.getY      ( ) + radius;

       double  maxX = driftBounds.getWidth  ( ) - radius;

       double  maxY = driftBounds.getHeight ( ) - radius;

       if ( destinationX < minX )
       {
         destinationX = minX;

         velocityX = -velocityX;
       }
       else if ( destinationX > maxX )
       {
         destinationX = maxX;

         velocityX = -velocityX;
       }

       if ( destinationY < minY )
       {
         destinationY = minY;

         velocityY = -velocityY;
       }
       else if ( destinationY > maxY )
       {
         destinationY = maxY;

         velocityY = -velocityY;
       }

       if ( ( destinationX != oldCenterX )
         || ( destinationY != oldCenterY ) )
       {
         circle.setCenter ( destinationX, destinationY );

         if ( world.isBlocked ( this ) )
         {
           circle.setCenter ( oldCenterX, oldCenterY );
         }       
         else
         {
           updated = true;
         }         
       }
     }

     //////////////////////////////////////////////////////////////////////
     // interface Damageable method
     //////////////////////////////////////////////////////////////////////

     public void  addDamage ( double  damage )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !active
         || ( damage == 0.0 ) )
       {
         return;
       }

       updated = true;

       double  radius = circle.getRadius ( ) - damage;

       if ( radius < radiusMin )
       {
         active = false;
       }
       else
       {
         circle.setRadius ( radius );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // interface Obstacle methods
     //////////////////////////////////////////////////////////////////////

     public void  setActive ( boolean  active )
     //////////////////////////////////////////////////////////////////////
     {
       this.active = active;
     }

     public void  setRadius ( double  radius )
     //////////////////////////////////////////////////////////////////////
     {
       circle.setRadius ( radius );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }