     package com.croftsoft.core.util;

     import com.croftsoft.core.lang.lifecycle.Resumable;

     /*********************************************************************
     * Used for timing events with millisecond precision.
     *
     * @version
     *   2001-07-03
     * @since
     *   2001-07-03
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Stopwatch
       implements Resumable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private long     elapsedTime;

     private boolean  isTicking;

     private long     startTime;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Returns the elapsed time in milliseconds.
     *********************************************************************/
     public synchronized long  getElapsedTime ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( isTicking )
       {
         return System.currentTimeMillis ( ) - startTime;
       }
       else
       {
         return elapsedTime;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  reset ( )
     //////////////////////////////////////////////////////////////////////
     {
       elapsedTime = 0;

       isTicking = false;
     }

     public synchronized void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( isTicking )
       {
         throw new IllegalStateException ( "already started" );
       }

       isTicking = true;

       startTime = System.currentTimeMillis ( );
     }

     public synchronized void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !isTicking )
       {
         throw new IllegalStateException ( "not started" );
       }

       elapsedTime = System.currentTimeMillis ( ) - startTime; 

       isTicking = false;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }