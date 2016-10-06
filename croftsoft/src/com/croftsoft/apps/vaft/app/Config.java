     package com.croftsoft.apps.vaft.app;

     import java.io.*;
     import java.util.StringTokenizer;

     import com.croftsoft.apps.vaft.core.HostInfo;

     /*********************************************************************
     * <P>
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1998-04-26
     *********************************************************************/

     public class  Config implements Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private boolean  use_control_panel;
     private String   rmi_server_name;
     private int      rmi_server_port;
     private String   rmi_object_name;
     private String   web_server_name;
     private int      web_server_port;
     private String   web_server_root;
     private String   peer_list_path;
     private String   seed_server_name;
     private int      seed_server_port;
     private String   seed_object_name;
     private long     swap_delay;
     private String   agent_dir;

     public static final String  USE_CONTROL_PANEL = "USE_CONTROL_PANEL";
     public static final String  RMI_SERVER_NAME   = "RMI_SERVER_NAME";
     public static final String  RMI_SERVER_PORT   = "RMI_SERVER_PORT";
     public static final String  RMI_OBJECT_NAME   = "RMI_OBJECT_NAME";
     public static final String  WEB_SERVER_NAME   = "WEB_SERVER_NAME";
     public static final String  WEB_SERVER_PORT   = "WEB_SERVER_PORT";
     public static final String  WEB_SERVER_ROOT   = "WEB_SERVER_ROOT";
     public static final String  PEER_LIST_PATH    = "PEER_LIST_PATH";
     public static final String  SEED_SERVER_NAME  = "SEED_SERVER_NAME";
     public static final String  SEED_SERVER_PORT  = "SEED_SERVER_PORT";
     public static final String  SEED_OBJECT_NAME  = "SEED_OBJECT_NAME";
     public static final String  SWAP_DELAY        = "SWAP_DELAY";
     public static final String  AGENT_DIR         = "AGENT_DIR";

     //////////////////////////////////////////////////////////////////////
     // Static methods
     //////////////////////////////////////////////////////////////////////

     public static void  help ( PrintStream  printStream )
     //////////////////////////////////////////////////////////////////////
     {
       Config  default_Config = new Config (
         Defaults.USE_CONTROL_PANEL,
         Defaults.RMI_SERVER_NAME  ,
         Defaults.RMI_SERVER_PORT  ,
         Defaults.RMI_OBJECT_NAME  ,
         Defaults.WEB_SERVER_NAME  ,
         Defaults.WEB_SERVER_PORT  ,
         Defaults.WEB_SERVER_ROOT  ,
         Defaults.PEER_LIST_PATH   ,
         Defaults.SEED_SERVER_NAME ,
         Defaults.SEED_SERVER_PORT ,
         Defaults.SEED_OBJECT_NAME ,
         Defaults.SWAP_DELAY       ,
         Defaults.AGENT_DIR        );
       printStream.println ( Defaults.INFO + "\n"
         + "Argument...:  Default\n" + display ( default_Config ) );
     }

     public static String  display ( Config  config )
     //////////////////////////////////////////////////////////////////////
     {
       return
           USE_CONTROL_PANEL + "...:" + config.use_control_panel + "\n"
         + RMI_SERVER_NAME + ".....:" + config.rmi_server_name   + "\n"
         + RMI_SERVER_PORT + ".....:" + config.rmi_server_port   + "\n"
         + RMI_OBJECT_NAME + ".....:" + config.rmi_object_name   + "\n"
         + WEB_SERVER_NAME + ".....:" + config.web_server_name   + "\n"
         + WEB_SERVER_PORT + ".....:" + config.web_server_port   + "\n"
         + WEB_SERVER_ROOT + ".....:" + config.web_server_root   + "\n"
         + PEER_LIST_PATH + "......:" + config.peer_list_path    + "\n"
         + SEED_SERVER_NAME + "....:" + config.seed_server_name  + "\n"
         + SEED_SERVER_PORT + "....:" + config.seed_server_port  + "\n"
         + SEED_OBJECT_NAME + "....:" + config.seed_object_name  + "\n"
         + SWAP_DELAY + "..........:" + config.swap_delay        + "\n"
         + AGENT_DIR + "...........:" + config.agent_dir         + "\n";
     }

     //////////////////////////////////////////////////////////////////////
     // Factory method
     //////////////////////////////////////////////////////////////////////

     public static Config  parse (
       String [ ]  args, PrintStream  printStream )
     //////////////////////////////////////////////////////////////////////
     {
       if ( ( args == null ) || ( args.length == 0 ) )
       {
         Config.help ( printStream );
         return null;
       }
         
       boolean  use_control_panel = Defaults.USE_CONTROL_PANEL;
       String   rmi_server_name   = Defaults.RMI_SERVER_NAME;
       int      rmi_server_port   = Defaults.RMI_SERVER_PORT;
       String   rmi_object_name   = Defaults.RMI_OBJECT_NAME;
       String   web_server_name   = Defaults.WEB_SERVER_NAME;
       int      web_server_port   = Defaults.WEB_SERVER_PORT;
       String   web_server_root   = Defaults.WEB_SERVER_ROOT;
       String   peer_list_path    = Defaults.PEER_LIST_PATH;
       String   seed_server_name  = Defaults.SEED_SERVER_NAME;
       int      seed_server_port  = Defaults.SEED_SERVER_PORT;
       String   seed_object_name  = Defaults.SEED_OBJECT_NAME;
       long     swap_delay        = Defaults.SWAP_DELAY;
       String   agent_dir         = Defaults.AGENT_DIR;

       try
       {
         for ( int  i = 0; i < args.length; i++ )
         {
           String  argument_name  = args [ i ];
           String  argument_value = args [ ++i ];

           argument_name = argument_name.toUpperCase ( );

           if ( argument_name.equals ( USE_CONTROL_PANEL ) )
             use_control_panel
               = Boolean.valueOf ( argument_value ).booleanValue ( );
           else if ( argument_name.equals ( RMI_SERVER_NAME ) )
             rmi_server_name = argument_value;
           else if ( argument_name.equals ( RMI_SERVER_PORT ) )
             rmi_server_port = Integer.parseInt ( argument_value );
           else if ( argument_name.equals ( RMI_OBJECT_NAME ) )
             rmi_object_name = argument_value;
           else if ( argument_name.equals ( WEB_SERVER_NAME ) )
             web_server_name = argument_value;
           else if ( argument_name.equals ( WEB_SERVER_PORT ) )
             web_server_port = Integer.parseInt ( argument_value );
           else if ( argument_name.equals ( WEB_SERVER_ROOT ) )
             web_server_root = argument_value;
           else if ( argument_name.equals ( PEER_LIST_PATH ) )
             peer_list_path = argument_value;
           else if ( argument_name.equals ( SEED_SERVER_NAME ) )
             seed_server_name = argument_value;
           else if ( argument_name.equals ( SEED_SERVER_PORT ) )
             seed_server_port = Integer.parseInt ( argument_value );
           else if ( argument_name.equals ( SEED_OBJECT_NAME ) )
             seed_object_name = argument_value;
           else if ( argument_name.equals ( SWAP_DELAY ) )
             swap_delay = Long.parseLong ( argument_value );
           else if ( argument_name.equals ( AGENT_DIR ) )
             agent_dir = argument_value;
           else
             throw new IllegalArgumentException ( args [ i ] );
         }
       }
       catch ( IllegalArgumentException  ex )
       {
         if ( printStream == null ) throw ex;
         Config.help ( printStream );
         printStream.println ( ex.getMessage ( ) );
         return null;
       }

       return new Config (
         use_control_panel,
         rmi_server_name,
         rmi_server_port,
         rmi_object_name,
         web_server_name,
         web_server_port,
         web_server_root,
         peer_list_path,
         seed_server_name,
         seed_server_port,
         seed_object_name,
         swap_delay,
         agent_dir );
     }

     //////////////////////////////////////////////////////////////////////
     // Constructor method
     //////////////////////////////////////////////////////////////////////

     public  Config (
       boolean  use_control_panel,
       String   rmi_server_name,
       int      rmi_server_port,
       String   rmi_object_name,
       String   web_server_name,
       int      web_server_port,
       String   web_server_root,
       String   peer_list_path,
       String   seed_server_name,
       int      seed_server_port,
       String   seed_object_name,
       long     swap_delay,
       String   agent_dir )
     //////////////////////////////////////////////////////////////////////
     {
       this.use_control_panel = use_control_panel;
       this.rmi_server_name   = rmi_server_name;
       this.rmi_server_port   = rmi_server_port;
       this.rmi_object_name   = rmi_object_name;
       this.web_server_name   = web_server_name;
       this.web_server_port   = web_server_port;
       this.web_server_root   = web_server_root;
       this.peer_list_path    = peer_list_path;
       this.seed_server_name  = seed_server_name;
       this.seed_server_port  = seed_server_port;
       this.seed_object_name  = seed_object_name;
       this.swap_delay        = swap_delay;
       this.agent_dir         = agent_dir;
     }

     //////////////////////////////////////////////////////////////////////
     // Access methods
     //////////////////////////////////////////////////////////////////////

     public boolean  getUse_control_panel ( ) { return use_control_panel; }
     public String   getRmi_server_name   ( ) { return rmi_server_name  ; }
     public int      getRmi_server_port   ( ) { return rmi_server_port  ; }
     public String   getRmi_object_name   ( ) { return rmi_object_name  ; }
     public String   getWeb_server_name   ( ) { return web_server_name  ; }
     public int      getWeb_server_port   ( ) { return web_server_port  ; }
     public String   getWeb_server_root   ( ) { return web_server_root  ; }
     public String   getPeer_list_path    ( ) { return peer_list_path   ; }
     public String   getSeed_server_name  ( ) { return seed_server_name ; }
     public int      getSeed_server_port  ( ) { return seed_server_port ; }
     public String   getSeed_object_name  ( ) { return seed_object_name ; }
     public long     getSwap_delay        ( ) { return swap_delay       ; }
     public String   getAgent_dir         ( ) { return agent_dir        ; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  display ( )
     //////////////////////////////////////////////////////////////////////
     {
       return display ( this );
     }

     public HostInfo  createSeed ( )
     //////////////////////////////////////////////////////////////////////
     {
       return new HostInfo (
         seed_server_name, seed_server_port, seed_object_name,
         new String [ ] { "com.orbs.pub.app.agent.vaft.core.SwapRemote" }
         );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
