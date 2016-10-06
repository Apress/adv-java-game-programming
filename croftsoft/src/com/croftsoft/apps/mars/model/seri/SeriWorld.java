     package com.croftsoft.apps.mars.model.seri;

     import java.awt.*;
     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.geom.Point2DD;
     import com.croftsoft.core.math.geom.PointXY;
     import com.croftsoft.core.math.geom.ShapeLib;
     import com.croftsoft.core.util.ArrayKeeper;
     import com.croftsoft.core.util.ArrayLib;
     import com.croftsoft.core.util.StableArrayKeeper;

     import com.croftsoft.apps.mars.ai.DefaultTankOperator;
     import com.croftsoft.apps.mars.ai.TankOperator;

     import com.croftsoft.apps.mars.model.AmmoDump;
     import com.croftsoft.apps.mars.model.AmmoDumpAccessor;
     import com.croftsoft.apps.mars.model.Bullet;
     import com.croftsoft.apps.mars.model.Damageable;
     import com.croftsoft.apps.mars.model.Game;
     import com.croftsoft.apps.mars.model.Impassable;
     import com.croftsoft.apps.mars.model.Model;
     import com.croftsoft.apps.mars.model.ModelAccessor;
     import com.croftsoft.apps.mars.model.Obstacle;
     import com.croftsoft.apps.mars.model.Tank;
     import com.croftsoft.apps.mars.model.TankAccessor;
     import com.croftsoft.apps.mars.model.World;

     /*********************************************************************
     * A World implementation.
     *
     * @version
     *   2003-05-11
     * @since
     *   2003-04-03
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SeriWorld
       implements World, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final Random               random;

     private final SeriAmmoDump.Shared  seriAmmoDumpShared;

     private final ArrayKeeper          modelArrayKeeper;

     private final Point2DD             center;

     private final java.util.List       modelList;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SeriWorld (
       Random               random,
       SeriAmmoDump.Shared  seriAmmoDumpShared )
     //////////////////////////////////////////////////////////////////////
     {
       modelArrayKeeper = new StableArrayKeeper ( new Model [ 0 ] );

       center = new Point2DD ( );

       NullArgumentException.check ( this.random = random );

       NullArgumentException.check (
         this.seriAmmoDumpShared = seriAmmoDumpShared );

       modelList = new ArrayList ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  clear ( )
     //////////////////////////////////////////////////////////////////////
     {
       modelArrayKeeper.setArray ( new Model [ 0 ] );
     }

     public AmmoDump  createAmmoDump (
       double  centerX,
       double  centerY )
     //////////////////////////////////////////////////////////////////////
     {
       SeriAmmoDump  seriAmmoDump = new SeriAmmoDump (
         this,
         centerX,
         centerY,
         seriAmmoDumpShared.ammoMax,
         seriAmmoDumpShared );

       modelArrayKeeper.insert ( seriAmmoDump );

       return seriAmmoDump;
     }

     public Bullet  createBullet (
       double  originX,
       double  originY,
       double  heading )
     //////////////////////////////////////////////////////////////////////
     {
       SeriBullet  seriBullet
         = new SeriBullet ( this, originX, originY, heading );

       modelArrayKeeper.insert ( seriBullet );

       return seriBullet;
     }

     public Obstacle  createObstacle (
       double     centerX,
       double     centerY,
       double     radius,
       double     radiusMin,
       Rectangle  driftBounds )
     //////////////////////////////////////////////////////////////////////
     {
       SeriObstacle  seriObstacle = new SeriObstacle (
         this, centerX, centerY, radius, radiusMin, random, driftBounds );

       modelArrayKeeper.insert ( seriObstacle );

       return seriObstacle;
     }

     public Tank  createTank (
       double  centerX,
       double  centerY,
       Color   color )
     //////////////////////////////////////////////////////////////////////
     {
       SeriTank  seriTank = new SeriTank ( this, centerX, centerY, color );

       TankOperator  tankOperator = new DefaultTankOperator ( random );

       seriTank.setTankOperator ( tankOperator );

       tankOperator.setTankConsole ( seriTank );

       modelArrayKeeper.insert ( seriTank );

       return seriTank;
     }

     public void  remove ( Model  model )
     //////////////////////////////////////////////////////////////////////
     {
       modelArrayKeeper.remove ( model );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public AmmoDump [ ]  getAmmoDumps (
       PointXY       pointXY,
       AmmoDump [ ]  ammoDumps )
     //////////////////////////////////////////////////////////////////////
     {
       return ( AmmoDump [ ] ) getModels (
         pointXY,
         ammoDumps,
         AmmoDump.class );
     }

     public Damageable [ ]  getDamageables (
       Shape           shape,
       Damageable [ ]  damageables )
     //////////////////////////////////////////////////////////////////////
     {
       return ( Damageable [ ] ) getModels (
         shape,
         damageables,
         Damageable.class );
     }

     public Impassable [ ]  getImpassables ( )
     //////////////////////////////////////////////////////////////////////
     {
       return ( Impassable [ ] )
         modelArrayKeeper.getArray ( Impassable.class );
     }

     public Iterator  getImpassables (
       Shape  shape,
       Model  model )
     //////////////////////////////////////////////////////////////////////
     {
       modelList.clear ( );

       Impassable [ ]  impassables = getImpassables ( );

       for ( int  i = 0; i < impassables.length; i++ )
       {
         Impassable  impassable = impassables [ i ];

         if ( ( impassable != model )
           && impassable.isActive ( )
           && ShapeLib.intersects ( shape, impassable.getShape ( ) ) )
         {
           modelList.add ( impassable );
         }
       }

       return modelList.iterator ( );
     }


     public Model [ ]  getModels ( )
     //////////////////////////////////////////////////////////////////////
     {
       return ( Model [ ] ) modelArrayKeeper.getArray ( );
     }

     public ModelAccessor [ ]  getModelAccessors (
       ModelAccessor [ ]  modelAccessors )       
     //////////////////////////////////////////////////////////////////////
     {
       return getModelAccessors ( ( Shape ) null, modelAccessors );
     }

     public ModelAccessor [ ]  getModelAccessors (
       Shape              shape,
       ModelAccessor [ ]  modelAccessors )       
     //////////////////////////////////////////////////////////////////////
     {
       Model [ ]  allModels = getModels ( );

       if ( shape == null )
       {
         return allModels;
       }

       NullArgumentException.check ( modelAccessors );

       int  index = 0;

       for ( int  i = 0; i < allModels.length; i++ )
       {
         Model  model = allModels [ i ];

         if ( ShapeLib.intersects ( shape, model.getShape ( ) ) )
         {
           if ( index < modelAccessors.length )
           {
             modelAccessors [ index ] = model;
           }
           else
           {
             modelAccessors = ( ModelAccessor [ ] )
               ArrayLib.append ( modelAccessors, model );
           }

           index++;
         }
       }

       if ( index < modelAccessors.length )
       {
         modelAccessors [ index ] = null;
       }

       return modelAccessors;
     }

     public Obstacle [ ]  getObstacles ( )
     //////////////////////////////////////////////////////////////////////
     {
       return ( Obstacle [ ] )
         modelArrayKeeper.getArray ( Obstacle.class );
     }

     public Tank [ ]  getTanks ( )
     //////////////////////////////////////////////////////////////////////
     {
       return ( Tank [ ] ) modelArrayKeeper.getArray ( Tank.class );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  prepare ( )
     //////////////////////////////////////////////////////////////////////
     {
       Model [ ]  models = getModels ( );

       for ( int  i = 0; i < models.length; i++ )
       {
         models [ i ].prepare ( );
       }
     }

     public void  update ( double  timeDelta )
     //////////////////////////////////////////////////////////////////////
     {
       Model [ ]  models = getModels ( );

       for ( int  i = 0; i < models.length; i++ )
       {
         models [ i ].update ( timeDelta );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  fireBullet (
       double  originX,
       double  originY,
       double  heading )
     //////////////////////////////////////////////////////////////////////
     {
       boolean  bulletFired = false;

       Bullet [ ]  bullets = ( Bullet [ ] )
         modelArrayKeeper.getArray ( Bullet.class );

       for ( int  i = 0; i < bullets.length; i++ )
       {
         Bullet  bullet = bullets [ i ];

         if ( !bullet.isActive  ( )
           && !bullet.isUpdated ( ) )
         {
           bullet.fire ( originX, originY, heading );

           bulletFired = true;

           break;
         }
       }

       if ( !bulletFired )
       {
         createBullet ( originX, originY, heading );
       }
     }

     public boolean  isBlockedByObstacle ( Shape  shape )
     //////////////////////////////////////////////////////////////////////
     {
       Obstacle [ ]  obstacles = getObstacles ( );

       for ( int  i = 0; i < obstacles.length; i++ )
       {
         Obstacle  obstacle = obstacles [ i ];

         if ( obstacle.isActive ( )
           && ShapeLib.intersects ( shape, obstacle.getShape ( ) ) )
         {
           return true;
         }
       }

       return false;
     }

     public boolean  isBlocked (
       Shape  shape,
       Model  model )
     //////////////////////////////////////////////////////////////////////
     {
       Impassable [ ]  impassables = getImpassables ( );

       for ( int  i = 0; i < impassables.length; i++ )
       {
         Impassable  impassable = impassables [ i ];

         if ( ( impassable != model )
           && impassable.isActive ( )
           && ShapeLib.intersects ( shape, impassable.getShape ( ) ) )
         {
           return true;
         }
       }

       return false;
     }

     public boolean  isBlocked ( Model  model )
     //////////////////////////////////////////////////////////////////////
     {
       return isBlocked ( model.getShape ( ), model );
     }

     public PointXY  getClosestAmmoDumpCenter ( PointXY  pointXY )
     //////////////////////////////////////////////////////////////////////
     {
       AmmoDumpAccessor  ammoDumpAccessor = ( AmmoDumpAccessor )
         getModelClosest ( pointXY, AmmoDumpAccessor.class, null );

       if ( ammoDumpAccessor != null )
       {
         return
           ShapeLib.getCenter ( ammoDumpAccessor.getShape ( ), center );
       }

       return null;
     }

     public PointXY  getClosestEnemyTankCenter (
        PointXY  pointXY,
        Color    friendColor )
     //////////////////////////////////////////////////////////////////////
     {
       double  closestDistance = Double.POSITIVE_INFINITY;

       int  index = -1;

       TankAccessor [ ]  tankAccessors = ( TankAccessor [ ] )
         modelArrayKeeper.getArray ( TankAccessor.class );

       for ( int  i = 0; i < tankAccessors.length; i++ )
       {
         TankAccessor  tankAccessor = tankAccessors [ i ];

         if ( tankAccessor.isActive ( )
           && !tankAccessor.getColor ( ).equals ( friendColor ) )
         {
           double  distance = pointXY.distanceXY (
             ShapeLib.getCenter ( tankAccessor.getShape ( ), center ) );


           if ( distance < closestDistance )
           {
             closestDistance = distance;

             index = i;
           }
         }
       }

       if ( index > -1 )
       {
         return ShapeLib.getCenter (
           tankAccessors [ index ].getShape ( ), center );
       }

       return null;
     }

     public Model  getModel (
       PointXY    pointXY,
       Class [ ]  classes,
       Model      model )
     //////////////////////////////////////////////////////////////////////
     {
       double  x = pointXY.getX ( );

       double  y = pointXY.getY ( );

       Model [ ]  models = getModels ( );

       for ( int  i = 0; i < models.length; i++ )
       {
         Model  otherModel = models [ i ];

         if ( ( otherModel != model )
           && otherModel.isActive ( )
           && otherModel.getShape ( ).contains ( x, y ) )
         {
           for ( int  j = 0; j < classes.length; j++ )
           {
             if ( classes [ j ].isInstance ( otherModel ) )
             {
               return otherModel;
             }
           }
         }        
       }

       return null;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private Model [ ]  getModels ( Class  c )
     //////////////////////////////////////////////////////////////////////
     {
       if ( c == null )
       {
         return ( Model [ ] ) modelArrayKeeper.getArray ( );
       }

       return ( Model [ ] ) modelArrayKeeper.getArray ( c );
     }

     private Model [ ]  getModels (
       PointXY    pointXY,
       Model [ ]  models,
       Class      c )
     //////////////////////////////////////////////////////////////////////
     {
       Model [ ]  allModels = getModels ( c );

       if ( pointXY == null )
       {
         return allModels;
       }

       NullArgumentException.check ( models );

       double  x = pointXY.getX ( );

       double  y = pointXY.getY ( );

       int  index = 0;

       for ( int  i = 0; i < allModels.length; i++ )
       {
         Model  model = allModels [ i ];

         if ( model.isActive ( )
           && model.getShape ( ).contains ( x, y ) )
         {
           if ( index < models.length )
           {
             models [ index ] = model;
           }
           else
           {
             models = ( Model [ ] ) ArrayLib.append ( models, model );
           }

           index++;
         }
       }

       if ( index < models.length )
       {
         models [ index ] = null;
       }

       return models;
     }

     private Model [ ]  getModels (
       Shape      shape,
       Model [ ]  models,
       Class      c )
     //////////////////////////////////////////////////////////////////////
     {
       Model [ ]  allModels = getModels ( c );

       if ( shape == null )
       {
         return allModels;
       }

       NullArgumentException.check ( models );

       int  index = 0;

       for ( int  i = 0; i < allModels.length; i++ )
       {
         Model  model = allModels [ i ];

         if ( model.isActive ( )
           && ShapeLib.intersects ( shape, model.getShape ( ) ) )
         {
           if ( index < models.length )
           {
             models [ index ] = model;
           }
           else
           {
             models = ( Model [ ] ) ArrayLib.append ( models, model );
           }

           index++;
         }
       }

       if ( index < models.length )
       {
         models [ index ] = null;
       }

       return models;
     }

     private Damageable [ ]  getDamageables ( )
     //////////////////////////////////////////////////////////////////////
     {
       return ( Damageable [ ] )
         modelArrayKeeper.getArray ( Damageable.class );
     }

     private Model  getModelClosest (
       PointXY  pointXY,
       Class    c,
       Model    model )
     //////////////////////////////////////////////////////////////////////
     {
       int  index = -1;

       double  closestDistance = Double.POSITIVE_INFINITY;

       Model [ ]  models = ( Model [ ] ) modelArrayKeeper.getArray ( );

       for ( int  i = 0; i < models.length; i++ )
       {
         Model  otherModel = models [ i ];

         if ( ( otherModel != model )
           && otherModel.isActive ( )
           && c.isInstance ( otherModel ) )
         {
           double  distance = ShapeLib.getCenter (
             otherModel.getShape ( ), center ).distanceXY ( pointXY );

           if ( distance < closestDistance )
           {
             closestDistance = distance;

             index = i;           
           }
         }
       }

       if ( index > -1 )
       {
         return models [ index ];
       }

       return null;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }