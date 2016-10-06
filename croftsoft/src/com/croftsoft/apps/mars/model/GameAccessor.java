     package com.croftsoft.apps.mars.model;

     import java.util.*;

     /*********************************************************************
     * Accessor interface for a Game object.
     *
     * @version
     *   2003-04-30
     * @since
     *   2003-04-14
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  GameAccessor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public int            getLevel              ( );

     public Iterator       getPath               ( );

     public TankAccessor   getPlayerTankAccessor ( );

     public WorldAccessor  getWorldAccessor      ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }