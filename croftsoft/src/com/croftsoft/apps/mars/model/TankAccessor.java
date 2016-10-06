     package com.croftsoft.apps.mars.model;

     import java.awt.Color;

     /*********************************************************************
     * The default tank model implementation.
     *
     * @version
     *   2003-04-29
     * @since
     *   2003-04-14
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  TankAccessor
       extends ModelAccessor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public int      getAmmo              ( );

     public double   getBodyHeading       ( );

     public Color    getColor             ( );

     public double   getDamage            ( );

     public boolean  isDryFiring          ( );

     public boolean  isFiring             ( );

     public double   getRadius            ( );

     public boolean  isSparking           ( );

     public double   getTurretHeading     ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
