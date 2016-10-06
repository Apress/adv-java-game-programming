     package com.croftsoft.core.util.loop;

     import com.croftsoft.core.animation.updater.FrameRateUpdater;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.MathConstants;

     /*********************************************************************
     * Uses windowed averaging to estimate the target loop delay.
     *
     * The window size starts off small to grows to some maximum size.
     * Any pause greater than one second will cause a window size reset.
     *
     * @version
     *   2003-11-04
     * @since
     *   2003-09-29
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  WindowedLoopGovernor
       implements LoopGovernor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final int   DEFAULT_MAX_WINDOW_SIZE = 100;

     private static final long  DEFAULT_RESET_TIME_NANOS
       = MathConstants.NANOSECONDS_PER_SECOND;

     //

     private final long      periodNanos;

     private final int       maxWindowSize;

     private final long      resetTimeNanos;

     private final long [ ]  nonDelayTimes;

     //

     private int   index;

     private int   windowSize;

     private long  delayMillis;

     private int   delayNanos;

     private long  previousTimeNanos;

     private long  totalDelayNanos;

     private long  sumNonDelayTimes;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       LoopGovernor  loopGovernor = new WindowedLoopGovernor ( 85.0 );

       FrameRateUpdater  frameRateUpdater = new FrameRateUpdater ( true );

       while ( true )
       {
         loopGovernor.govern ( );

         frameRateUpdater.update ( null );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *********************************************************************/
     public  WindowedLoopGovernor (
       long   periodNanos,
       int    maxWindowSize,
       long   resetTimeNanos )
     //////////////////////////////////////////////////////////////////////
     {
       if ( periodNanos < 1 )
       {
         throw new IllegalArgumentException ( "periodNanos < 1" );
       }

       this.periodNanos = periodNanos;

       if ( maxWindowSize < 1 )
       {
         throw new IllegalArgumentException ( "maxWindowSize < 1" );
       }

       this.maxWindowSize = maxWindowSize;

       if ( resetTimeNanos < 1 )
       {
         throw new IllegalArgumentException ( "resetTimeNanos < 1" );
       }

       this.resetTimeNanos = resetTimeNanos;

       nonDelayTimes = new long [ maxWindowSize ];

       delayMillis
         = periodNanos / MathConstants.NANOSECONDS_PER_MILLISECOND;

       delayNanos = ( int )
         ( periodNanos % MathConstants.NANOSECONDS_PER_MILLISECOND );

       totalDelayNanos = periodNanos;
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * @param  frequency
     *
     *   The targeted loop frequency in loops per second.
     *********************************************************************/
     public  WindowedLoopGovernor ( double  frequency )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         ( long ) ( MathConstants.NANOSECONDS_PER_SECOND / frequency ),
         DEFAULT_MAX_WINDOW_SIZE,
         DEFAULT_RESET_TIME_NANOS );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  govern ( )
       throws InterruptedException
     //////////////////////////////////////////////////////////////////////
     {
       long  currentTimeNanos = System.currentTimeMillis ( )
         * MathConstants.NANOSECONDS_PER_MILLISECOND;

       long  nonDelayTime
         = currentTimeNanos - previousTimeNanos - totalDelayNanos;

       previousTimeNanos = currentTimeNanos;

       long  oldNonDelayTime = nonDelayTimes [ index ];

       nonDelayTimes [ index ] = nonDelayTime;

       sumNonDelayTimes += nonDelayTime;

       index = ( index + 1 ) % maxWindowSize;

       if ( nonDelayTime > resetTimeNanos )
       {
         windowSize = 0;

         sumNonDelayTimes = 0;

         Thread.sleep ( delayMillis, delayNanos );

         return;
       }

       if ( windowSize == maxWindowSize )
       {
         sumNonDelayTimes -= oldNonDelayTime;
       }
       else
       {
         windowSize++;
       }

       long  averageNonDelayTime = sumNonDelayTimes / windowSize;

       totalDelayNanos = periodNanos - averageNonDelayTime;

       if ( totalDelayNanos < 0 )
       {
         totalDelayNanos = 0;
       }

       delayMillis
         = totalDelayNanos / MathConstants.NANOSECONDS_PER_MILLISECOND;

       delayNanos = ( int )
         ( totalDelayNanos % MathConstants.NANOSECONDS_PER_MILLISECOND );

       Thread.sleep ( delayMillis, delayNanos );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }