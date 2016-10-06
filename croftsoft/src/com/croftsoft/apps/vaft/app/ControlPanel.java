     package com.croftsoft.apps.vaft.app;

     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.gui.list.*;
     import com.orbs.open1.mit.io.Serialize;
     import com.croftsoft.core.role.actor.Actor;

     import com.croftsoft.apps.vaft.core.*;
     import com.croftsoft.apps.vaft.util.broadcast.*;

     /*********************************************************************
     * <P>
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1998-04-26
     *********************************************************************/

     public class  ControlPanel extends Frame
       implements ActionListener, BroadcastListener, ItemListener,
       WindowListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private Config    config;
     private VAFT      vaft;
     private TextArea  textArea;

     private MenuItem          menuItem_VAFT_Launch;
     private MenuItem          menuItem_VAFT_Shutdown;
     private MenuItem          menuItem_HostList_Display;
     private MenuItem          menuItem_HostList_Update;
     private MenuItem          menuItem_Configure_Display;
     private CheckboxMenuItem  menuItem_Monitor_HostList;

     private DoubleListDialog  doubleListDialog;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public ControlPanel ( Config  config )
     //////////////////////////////////////////////////////////////////////
     {
       this.config = config;

       addWindowListener ( this );

       setTitle ( config.getRmi_object_name ( ) );
       setResizable ( true );

       // VAFT Menu

       Menu  menu_VAFT = new Menu ( "VAFT" );

       menuItem_VAFT_Launch = new MenuItem ( "Launch" );
       menuItem_VAFT_Launch.addActionListener ( this );
       menu_VAFT.add ( menuItem_VAFT_Launch );

       menuItem_VAFT_Shutdown = new MenuItem ( "Shutdown" );
       menuItem_VAFT_Shutdown.addActionListener ( this );
       menu_VAFT.add ( menuItem_VAFT_Shutdown );

       // HostList Menu

       Menu  menu_HostList = new Menu ( "HostList" );

       menuItem_HostList_Display = new MenuItem ( "Display" );
       menuItem_HostList_Display.addActionListener ( this );
       menu_HostList.add ( menuItem_HostList_Display );

       menuItem_HostList_Update = new MenuItem ( "Update" );
       menuItem_HostList_Update.addActionListener ( this );
       menu_HostList.add ( menuItem_HostList_Update );

       // Configure Menu

       Menu  menu_Configure = new Menu ( "Configure" );
       menuItem_Configure_Display
         = new MenuItem ( "Display" );
       menuItem_Configure_Display.addActionListener ( this );
       menu_Configure.add ( menuItem_Configure_Display );

       // Monitor Menu

       Menu  menu_Monitor = new Menu ( "Monitor" );
       menuItem_Monitor_HostList = new CheckboxMenuItem ( "HostList" );
       menuItem_Monitor_HostList.addItemListener ( this );
       menu_Monitor.add ( menuItem_Monitor_HostList );

       MenuBar  menuBar = new MenuBar ( );
       setMenuBar ( menuBar );
       menuBar.add ( menu_VAFT      );
       menuBar.add ( menu_Configure );
       menuBar.add ( menu_HostList  );
       menuBar.add ( menu_Monitor   );

       textArea = new TextArea ( );
       textArea.setEditable ( false );
       textArea.setFont ( Font.getFont ( "monospaced" ) );
       add ( textArea );

       Toolkit  toolkit = Toolkit.getDefaultToolkit ( );
       Dimension  dimension = toolkit.getScreenSize ( );
       setBounds ( dimension.width / 4, dimension.height / 4,
         dimension.width / 2, dimension.height / 2 );
       show ( );

       display ( Defaults.INFO + "\n" + config.display ( ) );

       try
       {
         display ( "Starting VAFT..." );
         vaft = new VAFT ( config );
         monitor_HostList ( true );
         display ( "...VAFT created and bound." );
       }
       catch ( Exception  ex ) { display ( ex ); }
     }

     public synchronized void  shutdown ( )
     //////////////////////////////////////////////////////////////////////
     {
       display ( "Shutdown." );
       if ( vaft != null ) vaft.shutdown ( );
       vaft = null;
     }

     public synchronized void  monitor_HostList ( boolean  b )
     //////////////////////////////////////////////////////////////////////
     {
       menuItem_Monitor_HostList.setState ( b );
       HostList  hostList = vaft.getHost ( ).getHostList ( );
       if ( b )
       {
         hostList.addBroadcastListener ( this );
         display ( "Monitoring of HostList toggled on." );
       }
       else
       {
         hostList.removeBroadcastListener ( this );
         display ( "Monitoring of HostList toggled off." );
       }
     }

     public void  broadcastSent ( BroadcastEvent  be )
     //////////////////////////////////////////////////////////////////////
     {
       display ( be.toString ( ) );
     }

     public void  actionPerformed ( ActionEvent  e )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         Object  source = e.getSource ( );
         if ( source == menuItem_Configure_Display )
         {
           display ( config.display ( ) );
         }
         else if ( source == menuItem_HostList_Display )
         {
           command_HostList_Display ( );
         }
         else if ( source == menuItem_HostList_Update )
         {
           command_HostList_Update ( );
         }
         else if ( source == menuItem_VAFT_Launch )
         {
           command_Launch ( );
         }
         else if ( source == menuItem_VAFT_Shutdown )
         {
           shutdown ( );
         }
       }
       catch ( Exception  ex ) { display ( ex ); }
     }

     public void  itemStateChanged ( ItemEvent  e )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         if ( e.getSource ( ) == menuItem_Monitor_HostList )
         {
           monitor_HostList (
             menuItem_Monitor_HostList.getState ( ) );
         }
       }
       catch ( Exception  ex ) { display ( ex ); }
     }

     public void  windowClosing ( WindowEvent  e )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         shutdown ( );
         dispose ( );
         System.exit ( 0 );
       }
       catch ( Exception  ex ) { display ( ex ); }
     }

     public void  display ( String  s )
     //////////////////////////////////////////////////////////////////////
     {
       textArea.append ( s + "\n" );
     }

     public void  display ( Exception  ex )
     //////////////////////////////////////////////////////////////////////
     {
       StringWriter  stringWriter = new StringWriter ( );
       PrintWriter  printWriter = new PrintWriter ( stringWriter );
       ex.printStackTrace ( printWriter );
       display ( "Exception:  " + stringWriter.toString ( ) );
     }

     public synchronized void  command_Launch ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( doubleListDialog != null ) doubleListDialog.dispose ( );

       String [ ]  items_left = fetchAgentList ( );

       Vector  list
         = vaft.getHost ( ).getHostList ( ).getHostInfoList ( );
       String [ ]  items_right = new String [ list.size ( ) ];
       for ( int  i = 0; i < items_right.length; i++ )
       {
         items_right [ i ]
           = ( ( HostInfo ) list.elementAt ( i ) ).toURL ( );
       }

       Toolkit  toolkit = Toolkit.getDefaultToolkit ( );
       Dimension  d = toolkit.getScreenSize ( );

       doubleListDialog = new DoubleListDialog (
         this, "Launch Window", false,
         true, true, items_left, items_right, "Launch", "Abort",
         d.width / 4, 0, d.width / 2, d.height / 4,
         new DoubleListDialogListener ( )
         {
           public synchronized void  doubleListDialogAccept (
             String [ ]  items_left, String [ ]  items_right )
           {
             if ( ( items_left == null ) || ( items_right == null ) )
               return;
             launch ( items_left, items_right );
           }
         } );
     }

     public String [ ]  fetchAgentList ( )
     //////////////////////////////////////////////////////////////////////
     {
       String  agent_dir = config.getAgent_dir ( );
       String [ ]  files = new File ( agent_dir, "." ).list ( );
       Vector  v = new Vector ( );
       for ( int  i = 0; i < files.length; i++ )
       {
         if ( files [ i ].endsWith ( ".actor" ) )
         {
           v.addElement ( files [ i ] );
         }
       }
       String [ ]  agents = new String [ v.size ( ) ];
       for ( int  i = 0; i < agents.length; i++ )
       {
         agents [ i ] = ( String ) v.elementAt ( i );
       }
       return agents;
     }

     public void  launch ( String [ ]  agents, String [ ]  hosts )
     //////////////////////////////////////////////////////////////////////
     {
       String  agent_dir = config.getAgent_dir ( );
       try
       {
         for ( int  i = 0; i < agents.length; i++ )
         {
           try
           {
             Actor  actor = ( Actor ) Serialize.load (
               agent_dir + File.separator + agents [ i ] );
             for ( int  j = 0; j < hosts.length; j++ )
             {
               try
               {
                 display ( "Launching " + actor + " to "
                   + hosts [ j ] + "..." );
                 HostInfo.getHostRemote ( hosts [ j ] ).host ( actor );
                 display ( "..." + actor + " launched to "
                   + hosts [ j ] + "." );
               }
               catch ( Exception  ex ) { display ( ex ); }
             }
           }
           catch ( Exception  ex ) { display ( ex ); }
         }
       }
       catch ( Exception  ex ) { display ( ex ); }
     }

     public void  command_HostList_Display ( )
     //////////////////////////////////////////////////////////////////////
     {
       HostList  hostList = vaft.getHost ( ).getHostList ( );

       HostInfo  self = hostList.getHostInfoSelf ( );
       HostInfo  seed = hostList.getHostInfoSeed ( );
       Vector    list = hostList.getHostInfoList ( );

       display ( "Self:  " + self );
       display ( "Seed:  " + seed );
       display ( "List:  " + list.size ( ) );
       Enumeration  e = list.elements ( );
       while ( e.hasMoreElements ( ) )
       {
         display ( "Host:  " + ( HostInfo ) e.nextElement ( ) );
       }
     }

     public void  command_HostList_Update ( )
     //////////////////////////////////////////////////////////////////////
     {
       HostList  hostList = vaft.getHost ( ).getHostList ( );
       hostList.swap ( );
       command_HostList_Display ( );
     }

     public void  windowActivated   ( WindowEvent  e ) { }
     public void  windowClosed      ( WindowEvent  e ) { }
     public void  windowDeactivated ( WindowEvent  e ) { }
     public void  windowDeiconified ( WindowEvent  e ) { }
     public void  windowIconified   ( WindowEvent  e ) { }
     public void  windowOpened      ( WindowEvent  e ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
