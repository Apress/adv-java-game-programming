     package com.croftsoft.core.util.state;

     import java.util.*;

     import com.croftsoft.core.util.queue.*;
     import com.croftsoft.core.util.queue.ListQueue;

     /*********************************************************************
     *
     * Broadcasts the latest object state changes to StateListeners
     * using Queues and QueuePullers for buffering.
     * 
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   2000-04-30
     *********************************************************************/

     public class  QueuedStateMulticaster implements StateMulticaster
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /** QueuePullers keyed by StateListeners. */
     private Map  queuePullerMap;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  QueuedStateMulticaster ( Map  queuePullerMap )
     //////////////////////////////////////////////////////////////////////
     {
       this.queuePullerMap = queuePullerMap;
     }

     /*********************************************************************
     * this ( new HashMap ( ) );
     *********************************************************************/
     public  QueuedStateMulticaster ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new HashMap ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( State  state )
     //////////////////////////////////////////////////////////////////////
     {
       QueuePuller [ ]  queuePullers = null;

       synchronized ( queuePullerMap )
       {
         queuePullers = ( QueuePuller [ ] )
           queuePullerMap.values ( ).toArray ( new QueuePuller [ 0 ] );
       }

       for ( int  i = 0; i < queuePullers.length; i++ )
       {
         queuePullers [ i ].replace ( state );
       }
     }

     public synchronized boolean  addStateListener (
       StateListener  stateListener )
     //////////////////////////////////////////////////////////////////////
     {
       QueuePuller  queuePuller = new QueuePuller (
         new ListQueue ( new LinkedList ( ) ),
         new StateListenerConsumer ( stateListener ) );

       if ( !queuePullerMap.containsKey ( stateListener ) )
       {
         queuePullerMap.put ( stateListener, queuePuller );
         queuePuller.start ( );
         return true;
       }
       else
       {
         return false;
       }
     }

     public synchronized boolean  removeStateListener (
       StateListener  stateListener )
     //////////////////////////////////////////////////////////////////////
     {
       QueuePuller  queuePuller
         = ( QueuePuller ) queuePullerMap.remove ( stateListener );
       if ( queuePuller == null ) return false;
       queuePuller.stop ( );
       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
