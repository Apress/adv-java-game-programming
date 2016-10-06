     package com.croftsoft.apps.mars.ai;

     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.geom.PointXY;
     import com.croftsoft.core.util.NullIterator;

     /*********************************************************************
     * TankOperator implementation that requires direction.
     *
     * @version
     *   2003-05-13
     * @since
     *   2003-04-17
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

// this should not have to be serializable

     public final class  DirectedTankOperator
       implements TankOperator, java.io.Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private TankConsole  tankConsole;

     private boolean      fireRequested;

     private PointXY      destination;

     private boolean      destinationRequested;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  DirectedTankOperator ( TankConsole  tankConsole )
     //////////////////////////////////////////////////////////////////////
     {
       setTankConsole ( tankConsole );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  fire ( )
     //////////////////////////////////////////////////////////////////////
     {
       fireRequested = true;
     }

     public void  go ( PointXY  destination )
     //////////////////////////////////////////////////////////////////////
     {
// should null destination be allowed to force stop?

       destinationRequested = true;

       this.destination = destination;
     }

     public Iterator  getPath ( )
     //////////////////////////////////////////////////////////////////////
     {
       return NullIterator.INSTANCE;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setTankConsole ( TankConsole  tankConsole )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.tankConsole = tankConsole );
     }

     public void  update ( double  timeDelta )
     //////////////////////////////////////////////////////////////////////
     {
       if ( fireRequested || destinationRequested )
       {
         if ( fireRequested )
         {
           tankConsole.fire ( );

           fireRequested = false;
         }

         if ( destinationRequested )
         {
           tankConsole.go ( destination );

           tankConsole.rotateTurret ( destination );

           destinationRequested = false;
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }