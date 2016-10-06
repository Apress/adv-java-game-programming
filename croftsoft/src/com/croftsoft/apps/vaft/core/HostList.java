     package com.croftsoft.apps.vaft.core;

     import java.io.*;
     import java.rmi.*;
     import java.util.*;

     import com.croftsoft.apps.vaft.util.broadcast.*;

     /*********************************************************************
     * <P>
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1998-04-26
     *********************************************************************/

     public class  HostList
       implements Cloneable, Runnable, Serializable, Broadcaster
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String   BROADCAST_NAME = "HostList";

     private transient HostInfo   self;
     private transient HostInfo   seed;
     private transient long       swap_delay;
     private transient boolean    running = false;
     private transient Broadcast  broadcast;

     private Vector               v;

     //////////////////////////////////////////////////////////////////////
     // Constructor method
     //////////////////////////////////////////////////////////////////////

     public  HostList ( HostInfo  self, HostInfo  seed, long  swap_delay )
     //////////////////////////////////////////////////////////////////////
     {
       this.self       = self;
       this.seed       = seed;
       this.swap_delay = swap_delay;

       if ( self != null )
       {
         try { self.convertLocalHostToAddress ( ); }
         catch ( java.net.UnknownHostException  uhex ) { }
       }
       if ( seed != null )
       {
         try { seed.convertLocalHostToAddress ( ); }
         catch ( java.net.UnknownHostException  uhex ) { }
       }

       v = new Vector ( );
       if ( self != null ) v.addElement ( self );
       if ( seed != null ) v.addElement ( seed );

       broadcast = new Broadcast ( this );
     }

     //////////////////////////////////////////////////////////////////////
     // Access methods
     //////////////////////////////////////////////////////////////////////

     public boolean  getRunning ( )
       { return running; }

     public void     setRunning ( boolean  running )
       { this.running = running; }

     public HostInfo  getHostInfoSelf ( ) { return self; }
     public HostInfo  getHostInfoSeed ( ) { return seed; }

     /*********************************************************************
     * Returns a shallow clone.
     *********************************************************************/
     public Vector  getHostInfoList ( ) { return ( Vector ) v.clone ( ); }

     //////////////////////////////////////////////////////////////////////
     // Public instance methods
     //////////////////////////////////////////////////////////////////////

     public synchronized HostList  swap ( HostList  hostList )
     //////////////////////////////////////////////////////////////////////
     {
       broadcast ( "swapping HostList with remote Host..." );
       if ( hostList == null ) return this;
       merge ( hostList );

       // Give the peers that it does not know about.
       Enumeration  e = v.elements ( );
       HostList  c = null;
       while ( e.hasMoreElements ( ) )
       {
         HostInfo  hostInfo = ( HostInfo ) e.nextElement ( );
         try
         {
           if ( !hostList.isKnown ( hostInfo ) )
           {
             if ( c == null )
             {
               c = new HostList ( hostInfo, null, swap_delay );
             }
             else c.v.addElement ( hostInfo );
           }
         }
         catch ( java.net.UnknownHostException  uhex ) { }
       }
       return c;
     }

     public void  run ( )
     //////////////////////////////////////////////////////////////////////
     {
       running = true;
       while ( running )
       {
         broadcast ( "Periodic swap begins at " + new Date ( ) );
         swap ( );
         broadcast ( "Periodic swap ends at " + new Date ( ) );
         broadcast ( "Next periodic swap in "
           + swap_delay + " milliseconds" );
         try { Thread.currentThread ( ).sleep ( swap_delay ); }
         catch ( InterruptedException  ex )
         {
           broadcast ( "Sleep interrupted" );
         }
       }
       broadcast ( "Exiting swap loop" );
     }

     public Object  clone ( )
     //////////////////////////////////////////////////////////////////////
     {
       return clone ( );
     }

     //////////////////////////////////////////////////////////////////////
     // Private instance methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Contact each remote Host and swap HostLists.
     * Remove those Hosts which do not respond properly from my HostList.
     *********************************************************************/
     public synchronized void  swap ( )
     //////////////////////////////////////////////////////////////////////
     {
       Vector  c = getHostInfoList ( );
       if ( c.size ( ) < 2 )
       {
         try
         {
           if ( !isKnown ( seed ) ) c.addElement ( seed );
         }
         catch ( java.net.UnknownHostException  uhex ) { }
       }
       Enumeration  e = c.elements ( );
       while ( e.hasMoreElements ( ) )
       {
         HostInfo  hostInfo = ( HostInfo ) e.nextElement ( );
         if ( hostInfo == self ) continue;
         try
         {
           broadcast ( "Attempting to swap HostLists with "
             + hostInfo + "." );
           merge ( hostInfo.swap ( this ) );
         }
         catch ( ConnectException  cex )
         {
           v.removeElement ( hostInfo );
         }
         catch ( Exception  ex )
         {
//         ex.printStackTrace ( );
           v.removeElement ( hostInfo );
         }
       }
     }

     /*********************************************************************
     * Get the peers that I do not already know about.
     *********************************************************************/
     private synchronized void  merge ( HostList  hostList )
     //////////////////////////////////////////////////////////////////////
     {
       if ( hostList == null ) return;
       Enumeration  e = hostList.v.elements ( );
       while ( e.hasMoreElements ( ) )
       {
         HostInfo  hostInfo = ( HostInfo ) e.nextElement ( );
         if ( hostInfo.getRmi_server_name ( ).toLowerCase ( ).equals (
           "localhost" ) ) continue;
         try
         {
           if ( !isKnown ( hostInfo ) ) v.addElement ( hostInfo );
         }
         catch ( java.net.UnknownHostException  uhex ) { }
       }
     }

     public synchronized boolean  isKnown ( HostInfo  hostInfo )
       throws java.net.UnknownHostException
     //////////////////////////////////////////////////////////////////////
     {
       Enumeration  e = v.elements ( );
       while ( e.hasMoreElements ( ) )
       {
         if ( hostInfo.matches ( ( HostInfo ) e.nextElement ( ) ) )
           return true;
       }
       return false;
     }

     //////////////////////////////////////////////////////////////////////
     // Broacaster interface methods
     //////////////////////////////////////////////////////////////////////

     public String  getBroadcastName ( ) { return BROADCAST_NAME; }

     public boolean  addBroadcastListener (
       BroadcastListener  broadcastListener )
     //////////////////////////////////////////////////////////////////////
     {
       return broadcast.addBroadcastListener ( broadcastListener );
     }

     public boolean  removeBroadcastListener (
       BroadcastListener  broadcastListener )
     //////////////////////////////////////////////////////////////////////
     {
       return broadcast.removeBroadcastListener ( broadcastListener );
     }

     public void  broadcast ( String  s )
     //////////////////////////////////////////////////////////////////////
     {
       broadcast.broadcast ( s );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
