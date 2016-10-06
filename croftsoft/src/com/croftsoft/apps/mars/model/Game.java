     package com.croftsoft.apps.mars.model;

     import com.croftsoft.core.animation.clock.Timekeeper;

     import com.croftsoft.apps.mars.ai.TankOperator;

     /*********************************************************************
     * Interface for a Game object.
     *
     * @version
     *   2003-04-17
     * @since
     *   2003-04-14
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  Game
       extends GameAccessor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public Tank        getPlayerTank         ( );

     public double      getTimeFactorDefault  ( );

     public Timekeeper  getTimekeeper         ( );

     //

     public void  update ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }