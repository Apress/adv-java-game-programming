     package com.croftsoft.core.animation.model;

     import java.awt.*;
     import java.util.*;

     import com.croftsoft.core.math.geom.PointXY;

     /*********************************************************************
     * Provides methods for manipulating the Models in the game.
     *
     * @version
     *   2003-06-05
     * @since
     *   2003-04-14
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  World
       extends WorldAccessor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  clear ( );

     public void  remove ( Model  model );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  prepare ( );

     public void  update ( double  timeDelta );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Gets an active Model that contains the point.
     *
     * @param  classes
     *
     *   The returned Model will be an instance of one of the classes.
     *
     * @param  model
     *
     *   The Model to ignore, usually the one calling this method.
     *********************************************************************/
     public Model  getModel (
       PointXY    pointXY,
       Class [ ]  classes,
       Model      model );

     public Model [ ]  getModels ( );

     /*********************************************************************
     * Determines whether an active Impassable overlaps the model shape.
     *********************************************************************/
     public boolean  isBlocked ( Model  model );

     /*********************************************************************
     * Determines whether an active Impassable overlaps the shape.
     *
     * @param  model
     *
     *   The model to ignore.
     *********************************************************************/
     public boolean  isBlocked (
       Shape  shape,
       Model  model );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
