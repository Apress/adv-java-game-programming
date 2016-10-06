     package com.croftsoft.apps.mars.model.seri;

     import java.awt.Shape;
     import java.io.Serializable;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.geom.Circle;

     import com.croftsoft.apps.mars.model.AmmoDump;
     import com.croftsoft.apps.mars.model.Damageable;
     import com.croftsoft.apps.mars.model.World;

     /*********************************************************************
     * An ammunition dump.
     *
     * @version
     *   2003-05-12
     * @since
     *   2003-03-30
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SeriAmmoDump
       extends SeriModel
       implements AmmoDump
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //////////////////////////////////////////////////////////////////////
     // static member class
     //////////////////////////////////////////////////////////////////////

     public static final class  Shared
       implements Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     public static final double  DEFAULT_AMMO_GROWTH_RATE =  0.5;

     public static final double  DEFAULT_AMMO_MAX         = 30.0;

     public static final double  DEFAULT_EXPLOSION_FACTOR =  3.0;

     public static final double  DEFAULT_Z                =  0.1;

     //

     public final double  ammoGrowthRate;

     public final double  ammoMax;

     public final double  explosionFactor;

     public final double  z;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Shared (
       double  ammoGrowthRate,
       double  ammoMax,
       double  explosionFactor,
       double  z )
     //////////////////////////////////////////////////////////////////////
     {
       this.ammoGrowthRate  = ammoGrowthRate;

       this.ammoMax         = ammoMax;

       this.explosionFactor = explosionFactor;

       this.z               = z;
     }

     public  Shared ( )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         DEFAULT_AMMO_GROWTH_RATE,
         DEFAULT_AMMO_MAX,
         DEFAULT_EXPLOSION_FACTOR,
         DEFAULT_Z );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }

     //////////////////////////////////////////////////////////////////////
     // instance variables
     //////////////////////////////////////////////////////////////////////

     private final Circle  circle;

     private final Shared  shared;

     private final World   world;

     private final Circle  explosionCircle;

     //

     private double          ammo;

     private Damageable [ ]  damageables;

     private boolean         exploding;

     private boolean         updated;

     //////////////////////////////////////////////////////////////////////
     // constructor method
     //////////////////////////////////////////////////////////////////////

     public  SeriAmmoDump (
       World   world,
       double  centerX,
       double  centerY,
       double  ammo,
       Shared  shared )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.world = world );

       circle = new Circle ( );

       setCenter ( centerX, centerY );

       setAmmo ( ammo );

       NullArgumentException.check ( this.shared = shared );

       damageables = new Damageable [ 0 ];

       explosionCircle = new Circle ( );
     }

     //////////////////////////////////////////////////////////////////////
     // interface ModelAccessor methods
     //////////////////////////////////////////////////////////////////////

     public boolean  isActive  ( ) { return true;     }

     public Shape    getShape  ( ) { return circle;   }

     public boolean  isUpdated ( ) { return updated;  }

     public double   getZ      ( ) { return shared.z; }

     //////////////////////////////////////////////////////////////////////
     // interface Model methods
     //////////////////////////////////////////////////////////////////////

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

       exploding = false;
     }

     public void  update ( double  timeDelta )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !exploding )
       {
         double  newAmmo = ammo + timeDelta * shared.ammoGrowthRate;

         if ( newAmmo > shared.ammoMax )
         {
           newAmmo = shared.ammoMax;
         }
         else if ( newAmmo < 0.0 )
         {
           newAmmo = 0.0;
         }

         setAmmo ( newAmmo );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // interface Damageable method
     //////////////////////////////////////////////////////////////////////

     public void  addDamage ( double  damage )
     //////////////////////////////////////////////////////////////////////
     {
       if ( exploding )
       {
         return;
       }

       updated = true;

       exploding = true;

       explosionCircle.setCenter ( circle.getCenter ( ) );

       explosionCircle.setRadius ( shared.explosionFactor * ammo );

       damageables
         = world.getDamageables ( explosionCircle, damageables );

       for ( int  i = 0; i < damageables.length; i++ )
       {
         Damageable  damageable = damageables [ i ];

         if ( damageable == null )
         {
           break;
         }

         damageable.addDamage ( ammo );
       }

       setAmmo ( 0.0 );
     }

     //////////////////////////////////////////////////////////////////////
     // interface AmmoDumpAccessor methods
     //////////////////////////////////////////////////////////////////////

     public double   getAmmo           ( ) { return ammo;            }

     public boolean  isExploding       ( ) { return exploding;       }

     public Shape    getExplosionShape ( ) { return explosionCircle; }

     //////////////////////////////////////////////////////////////////////
     // interface AmmoDump method
     //////////////////////////////////////////////////////////////////////

     public void  setAmmo ( double  ammo )
     //////////////////////////////////////////////////////////////////////
     {
       if ( this.ammo == ammo )
       {
         return;
       }

       this.ammo = ammo;

       updated = true;

       if ( ammo > 0.0 )
       {
         circle.setRadius ( ammo );
       }
       else
       {
         circle.setRadius ( 0.0 );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }