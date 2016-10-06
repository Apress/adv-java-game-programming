     package com.croftsoft.apps.mars.model.seri;

     import java.awt.Shape;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.geom.Circle;
     import com.croftsoft.core.math.geom.PointXY;
     import com.croftsoft.core.util.ArrayKeeper;

     import com.croftsoft.apps.mars.model.Bullet;
     import com.croftsoft.apps.mars.model.Damageable;
     import com.croftsoft.apps.mars.model.Impassable;
     import com.croftsoft.apps.mars.model.Model;
     import com.croftsoft.apps.mars.model.World;

     /*********************************************************************
     * A bullet.
     *
     * @version
     *   2003-04-15
     * @since
     *   2003-04-01
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SeriBullet
       extends SeriModel
       implements Bullet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private static final Class [ ]  TARGET_CLASSES
       = new Class [ ] { Damageable.class, Impassable.class };

     private static final double  DAMAGE   = 1.0;

     private static final double  VELOCITY = 90.0;

     private static final double  RADIUS =   3.0;

     private static final double  RANGE  = 200.0;

     private static final double  Z      =   2.0;

     //

     private final World   world;

     private final Circle  circle;

     //

     private boolean  active;

     private double   distance;

     private double   heading;

     private double   originX;

     private double   originY;

     private boolean  updated;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SeriBullet (
       World   world,
       double  originX,
       double  originY,
       double  heading )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.world = world );

       circle = new Circle ( 0.0, 0.0, RADIUS );

       fire ( originX, originY, heading );
     }

     //////////////////////////////////////////////////////////////////////
     // interface Model methods
     //////////////////////////////////////////////////////////////////////

     public boolean  isActive  ( ) { return active;  }

     public Shape    getShape  ( ) { return circle;  }

     public boolean  isUpdated ( ) { return updated; }

     public double   getZ      ( ) { return Z;       }

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

       updated = true;

       distance += timeDelta * VELOCITY;

       if ( ( distance > RANGE )
         || ( distance < 0.0   ) )
       {
         active = false;

         return;
       }

       circle.setCenter (
         originX + distance * Math.cos ( heading ),
         originY + distance * Math.sin ( heading ) );

       PointXY  center = circle.getCenter ( );

       Model  model
         = world.getModel ( circle.getCenter ( ), TARGET_CLASSES, this );

       if ( model != null )
       {
         active = false;

         if ( model instanceof Damageable )
         {
           ( ( Damageable ) model ).addDamage ( DAMAGE );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  fire (
       double  originX,
       double  originY,
       double  heading )
     //////////////////////////////////////////////////////////////////////
     {
       this.originX = originX;

       this.originY = originY;

       this.heading = heading;

       distance     = 0.0;

       active       = true;

       updated      = true;

       circle.setCenter ( originX, originY );
     }

     public void  setCenter (
       double  x,
       double  y )
     //////////////////////////////////////////////////////////////////////
     {
       circle.setCenter ( x, y );       
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }