     package com.croftsoft.core.animation.model;

     import java.awt.Shape;

     /*********************************************************************
     * The base interface for the model of a game world entity.
     *
     * @version
     *   2003-06-07
     * @since
     *   2003-03-30
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  ModelAccessor
       extends Comparable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public boolean  isActive   ( );

     public double   getCenterX ( );

     public double   getCenterY ( );

     public ModelId  getModelId ( );

     public Shape    getShape   ( );

     public boolean  isUpdated  ( );

     public double   getZ       ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
