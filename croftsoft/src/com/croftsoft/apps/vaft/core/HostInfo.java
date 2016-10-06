     package com.croftsoft.apps.vaft.core;

     import java.io.*;
     import java.net.InetAddress;
     import java.rmi.*;

     /*********************************************************************
     * <P>
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1998-04-26
     *********************************************************************/

     public class  HostInfo extends ActorRemoteInfo implements Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     //////////////////////////////////////////////////////////////////////
     // Static methods
     //////////////////////////////////////////////////////////////////////

     public static HostRemote  getHostRemote ( String  url )
       throws java.net.MalformedURLException,
              NotBoundException,
              RemoteException
     //////////////////////////////////////////////////////////////////////
     {
       return ( HostRemote ) Naming.lookup ( url );
     }

     public static SwapRemote  getSwapRemote ( String  url )
       throws java.net.MalformedURLException,
              NotBoundException,
              RemoteException
     //////////////////////////////////////////////////////////////////////
     {
       return ( SwapRemote ) Naming.lookup ( url );
     }

     //////////////////////////////////////////////////////////////////////
     // Constructor method
     //////////////////////////////////////////////////////////////////////

     public  HostInfo (
       String      rmi_server_name,
       int         rmi_server_port,
       String      rmi_object_name,
       String [ ]  roles )
     //////////////////////////////////////////////////////////////////////
     {
       super ( rmi_server_name, rmi_server_port, rmi_object_name, roles );
     }

     //////////////////////////////////////////////////////////////////////
     // Access methods
     //////////////////////////////////////////////////////////////////////

     public String      getRmi_server_name ( ) { return rmi_server_name; }
     public int         getRmi_server_port ( ) { return rmi_server_port; }
     public String      getRmi_object_name ( ) { return rmi_object_name; }
     public String [ ]  getRoles           ( ) { return roles;           }

     //////////////////////////////////////////////////////////////////////
     // Convenience methods
     //////////////////////////////////////////////////////////////////////

     public HostRemote  getHostRemote ( )
       throws java.net.MalformedURLException,
              NotBoundException,
              RemoteException
     //////////////////////////////////////////////////////////////////////
     {
       return getHostRemote ( toURL ( ) );
     }

     public SwapRemote  getSwapRemote ( )
       throws java.net.MalformedURLException,
              NotBoundException,
              RemoteException
     //////////////////////////////////////////////////////////////////////
     {
       return getSwapRemote ( toURL ( ) );
     }

     public HostList  swap ( HostList  hostList )
       throws java.net.MalformedURLException,
              NotBoundException,
              RemoteException
     //////////////////////////////////////////////////////////////////////
     {
       return getSwapRemote ( ).swap ( hostList );
     }

     public String  toURL ( )
     //////////////////////////////////////////////////////////////////////
     {
       return "rmi://" + rmi_server_name
         + ":" + rmi_server_port
         + "/" + rmi_object_name;
     }

     public String  toString ( )
     //////////////////////////////////////////////////////////////////////
     {
       return toURL ( );
     }

     /*********************************************************************
     * Returns true if the host address, rmi_server_port, and
     * rmi_object_name are all the same.  Returns false if hostInfo is
     * null.
     *********************************************************************/
     public boolean  matches ( HostInfo  hostInfo )
       throws java.net.UnknownHostException
     //////////////////////////////////////////////////////////////////////
     {
       if ( hostInfo == null ) return false;
       return ( hostInfo.rmi_server_port == rmi_server_port )
         && hostInfo.rmi_object_name.equals ( rmi_object_name )
         && hostInfo.getHostAddress ( ).equals ( getHostAddress ( ) );
     }

     public String  getHostAddress ( )
       throws java.net.UnknownHostException
     //////////////////////////////////////////////////////////////////////
     {
       return InetAddress.getByName ( rmi_server_name ).getHostAddress ( );
     }

     /*********************************************************************
     * If the rmi_server_name is "localhost", converts it to a String
     * representing the IP address number.
     *********************************************************************/
     public synchronized void  convertLocalHostToAddress ( )
       throws java.net.UnknownHostException
     //////////////////////////////////////////////////////////////////////
     {
       if ( rmi_server_name.toLowerCase ( ).equals ( "localhost" ) )
       {
         rmi_server_name = InetAddress.getLocalHost ( ).getHostAddress ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
