     package com.croftsoft.apps.mars.ai;

     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.geom.Point2DD;
     import com.croftsoft.core.math.geom.PointXY;
     import com.croftsoft.core.math.geom.ShapeLib;
     import com.croftsoft.core.util.NullIterator;

     /*********************************************************************
     * Default TankOperator implementation.
     *
     * @version
     *   2003-05-12
     * @since
     *   2003-04-17
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  PlayerTankOperator
       implements TankOperator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /** milliseconds */
     private static final long  AUTO_PILOT_DELAY = 15 * 1000;

     //

     private final TankOperator    autoPilotTankOperator;

     private final List            pathList;

     private final StateSpaceNode  stateSpaceNode;

     //

     private TankConsole  tankConsole;

     private boolean      autoPilotMode;

     private boolean      fireRequested;

     private PointXY      destination;

     private boolean      destinationRequested;

     private long         lastInputTime;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  PlayerTankOperator ( TankOperator  autoPilotTankOperator )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.autoPilotTankOperator = autoPilotTankOperator );

       pathList = new ArrayList ( 1 );

       stateSpaceNode = new StateSpaceNode ( );

       pathList.add ( stateSpaceNode );

       lastInputTime = System.currentTimeMillis ( );
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
       destinationRequested = true;

       this.destination = destination;

       stateSpaceNode.setPointXY ( destination );
     }

     public Iterator  getPath ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( autoPilotMode )
       {
         return autoPilotTankOperator.getPath ( );
       }

       if ( destination == null )
       {
         return NullIterator.INSTANCE;
       }

       return pathList.iterator ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setTankConsole ( TankConsole  tankConsole )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.tankConsole = tankConsole );

       autoPilotTankOperator.setTankConsole ( tankConsole );
     }

     public void  update ( double  timeDelta )
     //////////////////////////////////////////////////////////////////////
     {
       if ( fireRequested || destinationRequested )
       {
         autoPilotMode = false;

         lastInputTime = System.currentTimeMillis ( );

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
       else if ( autoPilotMode )
       {
         autoPilotTankOperator.update ( timeDelta );
       }
       else if (
         System.currentTimeMillis ( ) > lastInputTime + AUTO_PILOT_DELAY )
       {
         autoPilotMode = true;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }