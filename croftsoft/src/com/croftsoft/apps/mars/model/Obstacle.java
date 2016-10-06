     package com.croftsoft.apps.mars.model;

     /*********************************************************************
     * An obstacle.
     *
     * @version
     *   2003-05-11
     * @since
     *   2003-04-14
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  Obstacle
       extends Model, ObstacleAccessor, Damageable, Impassable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  setActive ( boolean  active );

     public void  setRadius ( double   radius );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }