     package com.croftsoft.apps.vaft.app;

     import java.io.*;
     import java.net.MalformedURLException;
     import java.rmi.*;
     import java.util.*;

     import com.croftsoft.apps.vaft.core.*;
     import com.croftsoft.apps.vaft.security.HostSecurityManager;
     import com.croftsoft.core.net.http.WebServer;

     /*********************************************************************
     * <P>
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   2000-04-30
     *********************************************************************/

     public class  VAFT
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  CODEBASE_PROPERTY
       = "java.rmi.server.codebase";

     private Config        config;
     private WebServer     webServer;
     private Host          host;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
       throws AlreadyBoundException,
              MalformedURLException,
              RemoteException,
              UnknownHostException
     //////////////////////////////////////////////////////////////////////
     {
       System.setSecurityManager ( new HostSecurityManager ( ) );

       Config  config = Config.parse ( args, System.out );
       if ( config == null ) return;

       if ( config.getUse_control_panel ( ) )
         new ControlPanel ( config );
       else
         new VAFT ( config );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     protected  VAFT ( Config  config )
       throws AlreadyBoundException,
              MalformedURLException,
              RemoteException,
              UnknownHostException
     //////////////////////////////////////////////////////////////////////
     {
       this.config = config;
       start_WebServer ( );
       start_Host      ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     protected WebServer  getWebServer ( ) { return webServer; }
     protected Host       getHost      ( ) { return host;      }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     protected void  start_WebServer ( )
     //////////////////////////////////////////////////////////////////////
     {
       Properties  properties = System.getProperties ( );
       String  codebase
         = "http://" + config.getWeb_server_name ( ) + ":"
         + config.getWeb_server_port ( ) + "/";
       properties.put ( CODEBASE_PROPERTY, codebase );
       this.webServer = new WebServer (
         config.getWeb_server_root ( ),
         config.getWeb_server_port ( ) );
       new Thread ( webServer ).start ( );
     }

     protected void  start_Host ( )
       throws AlreadyBoundException,
              MalformedURLException,
              RemoteException,
              UnknownHostException
     //////////////////////////////////////////////////////////////////////
     {
       HostInfo  self = new HostInfo (
         config.getRmi_server_name ( ),
         config.getRmi_server_port ( ),
         config.getRmi_object_name ( ),
         new String [ ] { "com.orbs.pub.app.agent.vaft.core.SwapRemote" }
         );

       HostList  hostList = new HostList (
         self, config.createSeed ( ), config.getSwap_delay ( ) );

       // null password
       host = Host.bind_Host (
         config.getRmi_object_name ( ), null, hostList );
     }

     protected void  shutdown ( )
     //////////////////////////////////////////////////////////////////////
     {
       try { host.shutdown ( null ); } catch ( Exception  e ) { }
       try { webServer.shutdown ( ); } catch ( Exception  e ) { }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
