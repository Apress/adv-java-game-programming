     package com.croftsoft.core.animation.model;

     /*********************************************************************
     * The base interface for the model of a game world entity.
     *
     * @version
     *   2003-06-05
     * @since
     *   2003-03-30
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  Model
       extends ModelAccessor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  setCenter (
       double  x,
       double  y );

     public void  prepare ( );

     public void  update ( double  timeDelta );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }