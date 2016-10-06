     package com.croftsoft.core.util.state;

     import java.rmi.*;
     import java.rmi.server.UnicastRemoteObject;

     /*********************************************************************
     *
     * An adapter for StateMulticasters of remote States.
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1998-12-05
     *********************************************************************/

     public class  StateMulticasterProxy
       extends UnicastRemoteObject implements StateMulticasterRemote
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private StateMulticaster  stateMulticaster;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       String  name = "StateMulticasterProxy";

       if ( args.length > 0 ) name = args [ 0 ];

       try
       {
         Naming.rebind ( name, new StateMulticasterProxy (
           new QueuedStateMulticaster ( ) ) );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  StateMulticasterProxy ( StateMulticaster  stateMulticaster )
       throws RemoteException
     //////////////////////////////////////////////////////////////////////
     {
       this.stateMulticaster = stateMulticaster;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( State  state )
       throws RemoteException
     //////////////////////////////////////////////////////////////////////
     {
       stateMulticaster.update ( state );
     }

     public boolean  addStateListener ( StateListener  stateListener )
       throws RemoteException
     //////////////////////////////////////////////////////////////////////
     {
       return stateMulticaster.addStateListener ( stateListener );
     }

     public boolean  removeStateListener ( StateListener  stateListener )
       throws RemoteException
     //////////////////////////////////////////////////////////////////////
     {
       return stateMulticaster.removeStateListener ( stateListener );
     }


     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
