     package com.croftsoft.apps.vaft.app;

     import java.net.InetAddress;

     import com.croftsoft.apps.vaft.core.HostInfo;

     /*********************************************************************
     * <P>
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1998-04-26
     *********************************************************************/

     public class  Defaults
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  INFO
       = "VAFT/0.0";

     public static final boolean  USE_CONTROL_PANEL
       = false;
     public static final String  RMI_SERVER_NAME
       = getLocalHostAddress ( );
     public static final int     RMI_SERVER_PORT
// Change when Java 1.2 comes out.
       = 1099;
//     = java.rmi.Registry.REGISTRY_PORT;
     public static final String  RMI_OBJECT_NAME
       = "VAFT";
     public static final String  WEB_SERVER_NAME
       = getLocalHostAddress ( );
     public static final int     WEB_SERVER_PORT
       = 1968;
     public static final String  WEB_SERVER_ROOT
       = ".";
     public static final String  PEER_LIST_PATH
       = "peers.txt";

     public static final String    SEED_SERVER_NAME
       = "www.lpwv.org";
     public static final int       SEED_SERVER_PORT
       = RMI_SERVER_PORT;
     public static final String    SEED_OBJECT_NAME
       = RMI_OBJECT_NAME;

     public static final long  SWAP_DELAY = 1000 * 60 * 60;

     public static final String  AGENT_DIR
       = ".";

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private Defaults ( ) { }

     private static String  getLocalHostAddress ( )
     //////////////////////////////////////////////////////////////////////
     {
       String  address = null;
       try { address = InetAddress.getLocalHost ( ).getHostAddress ( ); }
       catch ( Exception  ex ) { }
       return address;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
