     package com.croftsoft.apps.vaft.util.broadcast;

     import java.util.*;

     /*********************************************************************
     * <P>
     * Used to broadcast BroadcastEvent objects to BroadcastListener
     * objects.
     * <P>
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1998-04-05
     *********************************************************************/

     public class  Broadcast implements Broadcaster
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private Broadcaster  broadcaster;
     private Vector       listeners;

     //////////////////////////////////////////////////////////////////////
     // Constructor method
     //////////////////////////////////////////////////////////////////////

     public  Broadcast ( Broadcaster  broadcaster )
     //////////////////////////////////////////////////////////////////////
     {
       this.broadcaster = broadcaster;
       listeners = new Vector ( );
     }

     //////////////////////////////////////////////////////////////////////
     // Access methods
     //////////////////////////////////////////////////////////////////////

     public Broadcaster  getBroadcaster ( ) { return broadcaster; }
     public Vector       getListeners   ( ) { return listeners  ; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getBroadcastName ( )
     //////////////////////////////////////////////////////////////////////
     {
       return broadcaster.toString ( );
     }

     public synchronized boolean  addBroadcastListener (
       BroadcastListener  bl )
     //////////////////////////////////////////////////////////////////////
     {
       if ( listeners.contains ( bl ) ) return false;
       listeners.addElement ( bl );
       return true;
     }

     public boolean  removeBroadcastListener ( BroadcastListener  bl )
     //////////////////////////////////////////////////////////////////////
     {
       return listeners.removeElement ( bl );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  broadcast ( String  announcement )
     //////////////////////////////////////////////////////////////////////
     {
       broadcast ( new BroadcastEvent ( broadcaster, announcement ) );
     }

     protected synchronized void  broadcast ( BroadcastEvent  be )
     //////////////////////////////////////////////////////////////////////
     {
       Enumeration  e = listeners.elements ( );
       while ( e.hasMoreElements ( ) )
       {
         BroadcastListener  broadcastListener
           = ( BroadcastListener ) e.nextElement ( );
         broadcastListener.broadcastSent ( be );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
