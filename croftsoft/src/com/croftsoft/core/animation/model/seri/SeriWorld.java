     package com.croftsoft.core.animation.model.seri;

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

     import com.croftsoft.core.animation.model.Impassable;
     import com.croftsoft.core.animation.model.Model;
     import com.croftsoft.core.animation.model.ModelAccessor;
     import com.croftsoft.core.animation.model.World;

     /*********************************************************************
     * A World implementation.
     *
     * @version
     *   2003-06-18
     * @since
     *   2003-04-03
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  SeriWorld
       implements World, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     protected final ArrayKeeper  modelArrayKeeper;

     //

     private final Point2DD        center;

     private final java.util.List  modelList;

     //

     private boolean  cleared;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SeriWorld ( )
     //////////////////////////////////////////////////////////////////////
     {
       modelArrayKeeper = new StableArrayKeeper ( new Model [ 0 ] );

       center = new Point2DD ( );

       modelList = new ArrayList ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  clear ( )
     //////////////////////////////////////////////////////////////////////
     {
       modelArrayKeeper.setArray ( new Model [ 0 ] );

       cleared = true;
     }

     public void  remove ( Model  model )
     //////////////////////////////////////////////////////////////////////
     {
       modelArrayKeeper.remove ( model );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

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

     public Model [ ]  getModels ( Class  c )
     //////////////////////////////////////////////////////////////////////
     {
       if ( c == null )
       {
         return ( Model [ ] ) modelArrayKeeper.getArray ( );
       }

       return ( Model [ ] ) modelArrayKeeper.getArray ( c );
     }

     public Model [ ]  getModels (
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

     public Model [ ]  getModels (
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

     public Model  getModelClosest (
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
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public boolean  isCleared ( ) { return cleared; }

     public void  prepare ( )
     //////////////////////////////////////////////////////////////////////
     {
       Model [ ]  models = getModels ( );

       for ( int  i = 0; i < models.length; i++ )
       {
         models [ i ].prepare ( );
       }

       cleared = false;
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
     }
