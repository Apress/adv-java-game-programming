     package com.croftsoft.apps.vaft.core;

     import java.io.*;
     import java.rmi.*;

     import com.croftsoft.core.role.actor.ActorRemote;

     /*********************************************************************
     * <P>
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1998-04-26
     *********************************************************************/

     public class  ActorRemoteInfo implements Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public String      rmi_server_name;
     public int         rmi_server_port;
     public String      rmi_object_name;
     public String [ ]  roles;

     //////////////////////////////////////////////////////////////////////
     // Static methods
     //////////////////////////////////////////////////////////////////////

     public static ActorRemote  getActorRemote ( String  url )
       throws java.net.MalformedURLException,
              NotBoundException,
              RemoteException
     //////////////////////////////////////////////////////////////////////
     {
       return ( ActorRemote ) Naming.lookup ( url );
     }

     //////////////////////////////////////////////////////////////////////
     // Constructor method
     //////////////////////////////////////////////////////////////////////

     public  ActorRemoteInfo (
       String      rmi_server_name,
       int         rmi_server_port,
       String      rmi_object_name,
       String [ ]  roles )
     //////////////////////////////////////////////////////////////////////
     {
       this.rmi_server_name = rmi_server_name;
       this.rmi_server_port = rmi_server_port;
       this.rmi_object_name = rmi_object_name;
       this.roles           = roles;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
