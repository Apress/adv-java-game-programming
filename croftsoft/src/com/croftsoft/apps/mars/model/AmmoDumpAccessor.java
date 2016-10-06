     package com.croftsoft.apps.mars.model;

     import java.awt.Shape;

     /*********************************************************************
     * An ammo dump.
     *
     * @version
     *   2003-04-17
     * @since
     *   2003-04-14
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  AmmoDumpAccessor
       extends ModelAccessor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public double   getAmmo           ( );

     public boolean  isExploding       ( );

     public Shape    getExplosionShape ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }