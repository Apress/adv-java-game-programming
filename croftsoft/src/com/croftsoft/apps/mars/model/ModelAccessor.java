     package com.croftsoft.apps.mars.model;

     import java.awt.Shape;

     /*********************************************************************
     * The base interface for the model of a game world entity.
     *
     * @version
     *   2003-04-16
     * @since
     *   2003-03-30
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  ModelAccessor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public boolean  isActive  ( );

     public Shape    getShape  ( );

     public boolean  isUpdated ( );

     public double   getZ      ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }