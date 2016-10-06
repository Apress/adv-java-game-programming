     package com.croftsoft.apps.mars.ai;

     import java.util.*;

     import com.croftsoft.core.math.geom.PointXY;

     /*********************************************************************
     * Operates a tank.
     *
     * @version
     *   2003-04-30
     * @since
     *   2003-03-30
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  TankOperator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  fire ( );

     public void  go ( PointXY  destination );

     public void  setTankConsole ( TankConsole  tankConsole );

     public void  update ( double  timeDelta );

     public Iterator  getPath ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }