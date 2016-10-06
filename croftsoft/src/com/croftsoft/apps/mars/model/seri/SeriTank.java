     package com.croftsoft.apps.mars.model.seri;

     import java.awt.Color;
     import java.awt.Shape;
     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.geom.Circle;
     import com.croftsoft.core.math.geom.Point2DD;
     import com.croftsoft.core.math.geom.PointXY;
     import com.croftsoft.core.util.ArrayKeeper;

     import com.croftsoft.apps.mars.ai.TankOperator;
     import com.croftsoft.apps.mars.model.AmmoDump;
     import com.croftsoft.apps.mars.model.Damageable;
     import com.croftsoft.apps.mars.model.Impassable;
     import com.croftsoft.apps.mars.model.Model;
     import com.croftsoft.apps.mars.model.Tank;
     import com.croftsoft.apps.mars.model.TankAccessor;
     import com.croftsoft.apps.mars.model.World;

     /*********************************************************************
     * The default tank model implementation.
     *
     * @version
     *   2003-04-29
     * @since
     *   2003-03-20
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SeriTank
       extends SeriModel
       implements Tank
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private static final double  Z = 1.0;

     /** radians per second */
     private static final double  TURRET_ROTATION_SPEED
       = 2.0 * Math.PI / 2.0;

     /** radians per second */
     private static final double  BODY_ROTATION_SPEED
       = 2.0 * Math.PI / 6.0;

     /** meters per second */
     private static final double  TANK_SPEED = 30.0;

     /** meters per second */
     private static final double  RADIUS = 25.0;

     private static final int     MAX_AMMO = 30;

     private static final double  DAMAGE_MAX = 2.0;

     //

     private static final int  INITIAL_AMMO = ( int ) DAMAGE_MAX + 1;

     //

     private final World     world;

     private final Circle    circle;

     private final Circle    testCircle;

     private final Color     color;

     private final Point2DD  targetPoint2DD;

     //

     private boolean       active;

     private int           ammo;

     private AmmoDump [ ]  ammoDumps;

     private double        bodyHeading;

     private double        damage;

     private PointXY       destination;

     private boolean       dryFiring;

     private boolean       firing;

     private boolean       sparking;

     private TankOperator  tankOperator;

     private double        turretHeading;

     private boolean       updated;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  SeriTank (
       World         world,
       double        centerX,
       double        centerY,
       Color         color )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.world = world );

       NullArgumentException.check ( this.color = color );

       circle = new Circle ( 0.0, 0.0, RADIUS );

       testCircle = new Circle ( 0.0, 0.0, RADIUS );

       initialize ( centerX, centerY );

       ammoDumps = new AmmoDump [ 0 ];

       targetPoint2DD = new Point2DD ( );
     }

     public void  initialize (
       double  centerX,
       double  centerY )
     //////////////////////////////////////////////////////////////////////
     {
       ammo = INITIAL_AMMO;

       damage = 0.0;

       prepare ( );

       active  = true;

       updated = true;

       circle.setCenter ( centerX, centerY );
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
       updated   = false;

       firing    = false;

       dryFiring = false;

       sparking  = false;
     }

     public void  update ( double  timeDelta )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !active )
       {
         return;
       }

       tankOperator.update ( timeDelta );

       updateAmmo ( );

       updatePosition ( timeDelta );

       updateTurretHeading ( timeDelta );
     }

     //////////////////////////////////////////////////////////////////////
     // interface Damageable method
     //////////////////////////////////////////////////////////////////////

     public void  addDamage ( double  newDamage )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !active
         || ( newDamage == 0.0 ) )
       {
         return;
       }

       updated = true;

       sparking = true;

       damage += newDamage;

       if ( damage > DAMAGE_MAX )
       {
         active = false;
       }
     }

     //////////////////////////////////////////////////////////////////////
     // interface TankAccessor methods
     //////////////////////////////////////////////////////////////////////

     public int      getAmmo              ( ) { return ammo;          }

     public double   getBodyHeading       ( ) { return bodyHeading;   }

     public double   getBodyRotationSpeed ( ) {
       return BODY_ROTATION_SPEED; }

     public Color    getColor             ( ) { return color;         }

     public double   getDamage            ( ) { return damage;        }

     public boolean  isDryFiring          ( ) { return dryFiring;     }

     public boolean  isFiring             ( ) { return firing;        }

     public double   getRadius            ( ) { return RADIUS;        }

     public boolean  isSparking           ( ) { return sparking;      }

     public TankOperator
       getTankOperator ( ) { return tankOperator; }

     public double   getTankSpeed         ( ) { return TANK_SPEED;    }

     public double   getTurretHeading     ( ) { return turretHeading; }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setAmmo ( int  ammo )
     //////////////////////////////////////////////////////////////////////
     {
       if ( ammo < 0 )
       {
         throw new IllegalArgumentException ( "ammo < 0:  " + ammo );
       }

       this.ammo = ammo;
     }

     public void  setTankOperator ( TankOperator  tankOperator )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.tankOperator = tankOperator );
     }

     //////////////////////////////////////////////////////////////////////
     // interface TankConsole methods
     //////////////////////////////////////////////////////////////////////

     public PointXY  getClosestAmmoDumpCenter ( )
     //////////////////////////////////////////////////////////////////////
     {
       return world.getClosestAmmoDumpCenter ( circle.getCenter ( ) );
     }

     public PointXY  getClosestEnemyTankCenter ( )
     //////////////////////////////////////////////////////////////////////
     {
       return world.getClosestEnemyTankCenter (
         circle.getCenter ( ), color );
     }

     public boolean  isSpaceAvailable ( PointXY  center )
     //////////////////////////////////////////////////////////////////////
     {
       testCircle.setCenter ( center );

       Iterator  iterator = world.getImpassables ( testCircle, this );

       while ( iterator.hasNext ( ) )
       {
         if ( ammo >= 1 )
         {        
           Impassable  impassable = ( Impassable ) iterator.next ( );

           if ( impassable instanceof TankAccessor )
           {
             TankAccessor  tankAccessor = ( TankAccessor ) impassable;

             if ( !tankAccessor.getColor ( ).equals ( color ) )
             {
               continue;
             }
           }
         }

         return false;
       }

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  go ( PointXY  destination )
     //////////////////////////////////////////////////////////////////////
     {
       this.destination = destination;
     }

     public void  fire ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !active || firing || dryFiring )
       {
         return;
       }
         
       updated = true;

       if ( ammo < 1 )
       {
         dryFiring = true;

         return;
       }

       ammo--;

       firing = true;

       PointXY  center = circle.getCenter ( );

       double  bulletOriginX
         = center.getX ( ) + ( RADIUS + 3.0 ) * Math.cos ( turretHeading );

       double  bulletOriginY
         = center.getY ( ) + ( RADIUS + 3.0 ) * Math.sin ( turretHeading );

       world.fireBullet ( bulletOriginX, bulletOriginY, turretHeading );
     }

     public void  rotateTurret ( PointXY  targetPointXY )
     //////////////////////////////////////////////////////////////////////
     {
       targetPoint2DD.setXY ( targetPointXY );
     }

     //////////////////////////////////////////////////////////////////////
     // private update methods
     //////////////////////////////////////////////////////////////////////

     private void  updateAmmo ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( ammo >= MAX_AMMO )
       {
         return;
       }

       int  ammoNeeded = MAX_AMMO - ammo;

       ammoDumps = world.getAmmoDumps ( circle.getCenter ( ), ammoDumps );

       for ( int  i = 0; i < ammoDumps.length; i++ )
       {
         AmmoDump  ammoDump = ammoDumps [ i ];

         if ( ammoDump == null )
         {
           break;
         }

         double  dumpAmmo = ammoDump.getAmmo ( );

         if ( ammoNeeded <= dumpAmmo )
         {
           ammo = MAX_AMMO;

           ammoDump.setAmmo ( dumpAmmo - ammoNeeded );

           break;
         }
         else
         {
           ammo += ( int ) dumpAmmo;

           ammoDump.setAmmo ( dumpAmmo - ( int ) dumpAmmo );

           ammoNeeded = MAX_AMMO - ammo;
         }
       }
     }

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

/*
       if ( ( Math.abs ( deltaX ) < 0.5 )
         && ( Math.abs ( deltaY ) < 0.5 ) )
       {
         return;
       }
*/

       double  aimHeading = Math.atan2 ( deltaY, deltaX );

       if ( aimHeading < 0.0 )
       {
         aimHeading += 2.0 * Math.PI;
       }

       double  newBodyHeading = rotateTowardHeading (
         bodyHeading,
         aimHeading,
         timeDelta * BODY_ROTATION_SPEED );

       if ( newBodyHeading != bodyHeading )
       {
         updated = true;

         bodyHeading = newBodyHeading;
       }

       if ( bodyHeading == aimHeading )
       {
         double  moveX = timeDelta * TANK_SPEED * Math.cos ( bodyHeading );

         double  moveY = timeDelta * TANK_SPEED * Math.sin ( bodyHeading );

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

         if ( world.isBlocked ( this ) )
         {
           circle.setCenter ( centerX, centerY );

           if ( world.isBlocked ( this ) )
           {
             circle.setCenter ( newX, newY );

             updated = true;
           }
         }
         else
         {
           updated = true;
         }
       }
     }

     private void  updateTurretHeading ( double  timeDelta )
     //////////////////////////////////////////////////////////////////////
     {
       PointXY  center = circle.getCenter ( );

       double  centerX = center.getX ( );

       double  centerY = center.getY ( );

       double  desiredTurretHeading = Math.atan2 (
         targetPoint2DD.y - centerY,
         targetPoint2DD.x - centerX );

       if ( desiredTurretHeading < 0.0 )
       {
         desiredTurretHeading += 2.0 * Math.PI;
       }

       double  newTurretHeading = rotateTowardHeading (
         turretHeading,
         desiredTurretHeading,
         timeDelta * TURRET_ROTATION_SPEED );

       if ( newTurretHeading != turretHeading )
       {
         updated = true;

         turretHeading = newTurretHeading;
       }
     }

     private double  rotateTowardHeading (
       double  currentHeading,
       double  targetHeading,
       double  rotationSpeed )
     //////////////////////////////////////////////////////////////////////
     {
       double  newHeading;

       double  deltaHeading = targetHeading - currentHeading;

       if ( deltaHeading < -Math.PI )
       {
         newHeading = currentHeading + rotationSpeed;

         if ( newHeading >= 2.0 * Math.PI )
         {
           newHeading -= 2.0 * Math.PI;
         }
       }
       else if ( deltaHeading < -rotationSpeed )
       {
         newHeading = currentHeading - rotationSpeed;
       }
       else if ( deltaHeading <= rotationSpeed )
       {
         newHeading = targetHeading;
       }
       else if ( deltaHeading < Math.PI )
       {
         newHeading = currentHeading + rotationSpeed;
       }
       else
       {
         newHeading = currentHeading - rotationSpeed;

         if ( newHeading < 0.0 )
         {
           newHeading += 2.0 * Math.PI;
         }
       }

       return newHeading;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
