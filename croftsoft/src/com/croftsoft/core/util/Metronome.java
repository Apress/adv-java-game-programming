     package com.croftsoft.core.util;

     import java.util.Timer;
     import java.util.TimerTask;

     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Runs tasks periodically.
     *
     * @version
     *   2002-02-27
     * @since
     *   2001-03-06
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Metronome
       implements Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private       Runnable   runnable;

     private       long       periodInMilliseconds;

     private final boolean    useDaemonThread;

     private       boolean    isStarted = false;

     private       Timer      timer;

     private       TimerTask  timerTask;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Metronome (
       Runnable  runnable,
       long      periodInMilliseconds,
       boolean   useDaemonThread )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.runnable = runnable );

       this.periodInMilliseconds = periodInMilliseconds;

       this.useDaemonThread = useDaemonThread;

       if ( periodInMilliseconds < 0 )
       {
         throw new IllegalArgumentException (
           "periodInMilliseconds < 0 " );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  resetPeriodInMilliseconds (
       long  periodInMilliseconds )
     //////////////////////////////////////////////////////////////////////
     {
       if ( periodInMilliseconds < 0 )
       {
         throw new IllegalArgumentException (
           "periodInMilliseconds < 0 " );
       }

       this.periodInMilliseconds = periodInMilliseconds;

       if ( isStarted )
       {
         stop ( );
 
         start ( );
       }
     }

     public synchronized void  resetRunnable ( Runnable  runnable )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.runnable = runnable );

       if ( isStarted )
       {
         stop ( );
 
         start ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       timer = new Timer ( useDaemonThread );
     }

     public synchronized void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       isStarted = true;

       timerTask = new TimerTask ( ) {
         public void  run ( ) { runnable.run ( ); } };

       // schedule() seems to max out at just under 20 tasks per second.
       // Possibly due to a 50 ms system clock resolution?

       timer.schedule ( timerTask, 0, periodInMilliseconds );
     }

     public synchronized void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       isStarted = false;

       if ( timerTask != null )
       {
         timerTask.cancel ( );
       }

       timerTask = null;
     }

     public synchronized void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       stop ( );

       try
       {
         if ( timer != null )
         {
           timer.cancel ( );
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }

       timer = null;

       runnable = null;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
