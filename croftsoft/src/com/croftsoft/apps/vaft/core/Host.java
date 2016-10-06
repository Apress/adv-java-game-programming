     package com.croftsoft.apps.vaft.core;

     import java.net.MalformedURLException;
     import java.rmi.*;
     import java.rmi.server.*;
     import java.util.Enumeration;
     import java.util.Vector;

     import com.croftsoft.core.role.actor.*;

     /*********************************************************************
     * <P>
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1998-04-26
     *********************************************************************/

     public class  Host extends UnicastRemoteObject implements
       Actor, ActorRemote, HostRemote, SwapRemote, ShutdownRemote
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private String    rmi_object_name;
     private String    password;
     private HostList  hostList;
     private Thread    hostList_Thread;
     private Vector    thread_Vector;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Instantiates a Host then binds it to the registry.
     *********************************************************************/
     public static Host  bind_Host (
       String  rmi_object_name, String  password, HostList  hostList )
       throws AlreadyBoundException, MalformedURLException, RemoteException
     //////////////////////////////////////////////////////////////////////
     {
       Host  host = new Host ( rmi_object_name, password, hostList );
//Naming.bind ( rmi_object_name, host );
       Naming.rebind ( rmi_object_name, host );
       return host;
     }

     public  Host (
       String  rmi_object_name, String  password, HostList  hostList )
       throws RemoteException
     //////////////////////////////////////////////////////////////////////
     {
       this.rmi_object_name = rmi_object_name;
       this.password        = password;
       this.hostList        = hostList;

       hostList_Thread = new Thread ( hostList );
       hostList_Thread.start ( );

       this.thread_Vector = new Vector ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public HostList  getHostList ( ) { return hostList; }

     //////////////////////////////////////////////////////////////////////
     // Interface methods
     //////////////////////////////////////////////////////////////////////

     public String [ ]  getRoles ( )
     //////////////////////////////////////////////////////////////////////
     {
       return new String [ ] {
         "com.orbs.pub.agent.role.Actor",
         "com.orbs.pub.agent.role.ActorRemote",
         "com.orbs.pub.app.agent.vaft.core.HostRemote",
         "com.orbs.pub.app.agent.vaft.core.SwapRemote" };
     }

     public String [ ]  getRolesRemote ( ) throws RemoteException
     //////////////////////////////////////////////////////////////////////
     {
       return getRoles ( );
     }

     public void  host ( Actor  actor ) throws RemoteException
     //////////////////////////////////////////////////////////////////////
     {
       String [ ]  roles = actor.getRoles ( );
       if ( roles == null ) return;
       for ( int  i = 0; i < roles.length; i++ )
       {
         if ( "java.lang.Runnable".equals ( roles [ i ] ) )
         {
           Thread  thread = new Thread ( ( Runnable ) actor );
           thread_Vector.addElement ( thread );
           thread.start ( );
           break;
         }
       }
     }

     public HostList  swap ( HostList  hostList ) throws RemoteException
     //////////////////////////////////////////////////////////////////////
     {
       return this.hostList.swap ( hostList );
     }

     public synchronized boolean  shutdown ( String  password )
       throws RemoteException
     //////////////////////////////////////////////////////////////////////
     {
       if ( this.password != null )
       {
         if ( !this.password.equals ( password ) ) return false;
       }

       try { Naming.unbind ( rmi_object_name ); }
       catch ( Exception  ex ) { }

       hostList.setRunning ( false );
       hostList_Thread.interrupt ( );

       Enumeration  e = thread_Vector.elements ( );
       while ( e.hasMoreElements ( ) )
       {
         Thread  thread = ( Thread ) e.nextElement ( );
         try { thread.stop ( ); } catch ( Exception  ex ) { }
       }
       thread_Vector = null;

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
