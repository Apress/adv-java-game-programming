     package com.croftsoft.core.animation.clock;

     import com.croftsoft.core.animation.Clock;

     /*********************************************************************
     * A Clock with higher resolution than the system clock.
     *
     * <p>
     * The system clock on some platforms has a granularity as poor as
     * 50 or 60 milliseconds.  For some applications, such as high
     * frequency animation, this is insufficient.  Film quality animation
     * of 24 frames per second, for example, has a period of 41.667
     * milliseconds.  This class provides high resolution timing with a
     * granularity of one nanosecond.
     * </p>
     *
     * <p>
     * The current implementation samples the system clock each time the
     * method currentTimeNanos() is called.  The period between method
     * calls is then estimated using round robin windowed averaging.
     * Assuming the method is called fairly periodically within a loop,
     * the returned value will be the previous value plus the updated
     * period estimate whenever the system clock has not been increased.
     * </p>
     *
     * @see
     *   SystemClock
     *
     * @version
     *   2002-12-01
     * @since
     *   2002-03-11
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  HiResClock
       implements Clock
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final int  SAMPLES_LENGTH = 1000;

     //

     private long [ ]  samples = new long [ SAMPLES_LENGTH ];

     private long      lastClockTimeNanos;

     private int       index;

     private long      estimatedTimeNanos;

     private boolean   arrayFilled;

     private long      estimatedPeriodNanos;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public long  currentTimeNanos ( )
     //////////////////////////////////////////////////////////////////////
     {
       long  clockTimeNanos = 1000000L * System.currentTimeMillis ( );

       // round robin windowed averaging

       if ( arrayFilled )
       {
         estimatedPeriodNanos
           = ( clockTimeNanos - samples [ index ] ) / SAMPLES_LENGTH;
       }
       else
       {
         if ( index == 0 )
         {
           estimatedPeriodNanos = 0;
         }
         else
         { 
           estimatedPeriodNanos
             = ( clockTimeNanos - samples [ 0 ] ) / index;
         }

         if ( index == SAMPLES_LENGTH - 1 )
         {
           arrayFilled = true;
         }
       }

       if ( ( estimatedPeriodNanos > 100000000L )
         || ( clockTimeNanos > lastClockTimeNanos + 100000000L ) )
       {
         estimatedPeriodNanos = 0;

         index = 0;

         arrayFilled = false;
       }

       samples [ index ] = clockTimeNanos;

       index = ( index + 1 ) % SAMPLES_LENGTH;

       if ( clockTimeNanos > lastClockTimeNanos )
       {
         if ( clockTimeNanos > estimatedTimeNanos )
         {
           estimatedTimeNanos = clockTimeNanos;
         }
       }
       else
       {
         estimatedTimeNanos += estimatedPeriodNanos;
       }

       lastClockTimeNanos = clockTimeNanos;

/*
System.out.print ( clockTimeNanos );

System.out.print ( " " );

System.out.print ( estimatedPeriodNanos );

System.out.print ( " " );

System.out.println ( estimatedTimeNanos );
*/

       return estimatedTimeNanos;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
