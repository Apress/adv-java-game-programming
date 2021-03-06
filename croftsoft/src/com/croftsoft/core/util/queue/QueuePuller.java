     package com.croftsoft.core.util.queue;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.role.Consumer;
     import com.croftsoft.core.util.loop.Loopable;
     import com.croftsoft.core.util.loop.Looper;

     /*********************************************************************
     * Pulls objects out of the queue in a separate thread.
     *
     * @version
     *   2003-05-27
     * @since
     *   1998-11-23
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  QueuePuller
       implements Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Queue     queue;

     private final Consumer  consumer;

     private final Looper    looper;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  QueuePuller (
       Queue     queue,
       Consumer  consumer )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.queue    = queue    );

       NullArgumentException.check ( this.consumer = consumer );

       looper = new Looper (
         new Loopable ( )
         {
           public boolean  loop ( )
           {
             return QueuePuller.this.loop ( );
           }
         } );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       looper.init ( );
     }

     public void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       looper.start ( );
     }

     public void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       looper.stop ( );
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       looper.destroy ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  append ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       return queue.append ( o );
     }

     public Object  replace ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       return queue.replace ( o );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private boolean  loop ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         consumer.consume ( queue.pull ( ) );
       }
       catch ( InterruptedException  ex )
       {
       }

       return true;      
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
