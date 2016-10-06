     package com.croftsoft.apps.mars.model;

     import com.croftsoft.apps.mars.ai.TankConsole;
     import com.croftsoft.apps.mars.ai.TankOperator;

     /*********************************************************************
     * A mobile armored tank.
     *
     * @version
     *   2003-04-22
     * @since
     *   2003-04-14
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  Tank
       extends Model, TankAccessor, TankConsole, Damageable, Impassable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  initialize (
       double  centerX,
       double  centerY );

     public TankOperator  getTankOperator ( );

     public void  setTankOperator ( TankOperator  tankOperator );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
