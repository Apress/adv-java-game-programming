     package com.croftsoft.core.util.loop;

     import com.croftsoft.core.animation.Clock;
     import com.croftsoft.core.animation.clock.SystemClock;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.MathConstants;

     /*********************************************************************
     * Sets the delay by sampling the calling frequency over time.
     *
     * @version
     *   2003-05-22
     * @since
     *   2002-03-13
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SamplerLoopGovernor
       implements LoopGovernor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final long  DEFAULT_SAMPLE_PERIOD_NANOS
       = 3 * MathConstants.NANOSECONDS_PER_SECOND;

     public static final long  DEFAULT_AVERAGING_SAMPLES_MAX = 2;

     //

     private final long   periodNanos;

     private final long   samplePeriodNanos;

     private final long   averagingSamplesMax;

     private final Clock  clock;

     //

     private long  lastTimeNanos;

     private long  count;

     private long  totalDelayNanos;

     private long  delayMillis;

     private int   delayNanos;

     private long  averagingSamples;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Constructs a LoopGovernor with the specified target frequency.
     *
     * @param  frequency
     *
     *   The targeted loop frequency in loops per second.
     *********************************************************************/
     public  SamplerLoopGovernor (
       double  frequency,
       long    samplePeriodNanos,
       long    averagingSamplesMax,
       Clock   clock )
     //////////////////////////////////////////////////////////////////////
     {
       if ( frequency <= 0.0 )
       {
         throw new IllegalArgumentException ( "frequency <= 0.0" );
       }

       periodNanos = ( long )
         ( MathConstants.NANOSECONDS_PER_SECOND / frequency );

       totalDelayNanos = periodNanos;

       delayMillis
         = totalDelayNanos / MathConstants.NANOSECONDS_PER_MILLISECOND;

       delayNanos = ( int )
         ( totalDelayNanos % MathConstants.NANOSECONDS_PER_MILLISECOND );

       if ( samplePeriodNanos < 2 * periodNanos )
       {
         throw new IllegalArgumentException (
           "samplePeriodNanos < 2 * periodNanos:  " 
           + samplePeriodNanos + " < " + ( 2 * periodNanos ) );
       }

       this.samplePeriodNanos = samplePeriodNanos;

       if ( averagingSamplesMax < 1 )
       {
         throw new IllegalArgumentException ( "averagingSamplesMax < 1" );
       }

       this.averagingSamplesMax = averagingSamplesMax;

       NullArgumentException.check ( this.clock = clock );
     }

     public  SamplerLoopGovernor (
       double  frequency,
       Clock   clock )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         frequency,
         DEFAULT_SAMPLE_PERIOD_NANOS,
         DEFAULT_AVERAGING_SAMPLES_MAX,
         clock );
     }

     public  SamplerLoopGovernor ( double  frequency )
     //////////////////////////////////////////////////////////////////////
     {
       this ( frequency, SystemClock.INSTANCE );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  govern ( )
       throws InterruptedException
     //////////////////////////////////////////////////////////////////////
     {
       Thread.sleep ( delayMillis, delayNanos );

       count++;

       long  currentTimeNanos = clock.currentTimeNanos ( );

       if ( currentTimeNanos < lastTimeNanos + samplePeriodNanos )
       {
         return;
       }

       long  measuredSamplePeriodNanos = currentTimeNanos - lastTimeNanos;

       long  estimatedPeriodNanos = measuredSamplePeriodNanos / count;

       count = 0;

       lastTimeNanos = currentTimeNanos;
         
       long  estimatedNonDelayTimeNanos
         = estimatedPeriodNanos - totalDelayNanos;

       long  newDelayNanos = periodNanos - estimatedNonDelayTimeNanos;

       if ( newDelayNanos < 0 )
       {
         if ( measuredSamplePeriodNanos == currentTimeNanos )
         {
           return;
         }

         newDelayNanos = 0;
       }

       if ( averagingSamples < averagingSamplesMax )
       {
         averagingSamples++;
       }

       totalDelayNanos
         += ( newDelayNanos - totalDelayNanos ) / averagingSamples;

       delayMillis
         = totalDelayNanos / MathConstants.NANOSECONDS_PER_MILLISECOND;

       delayNanos = ( int )
         ( totalDelayNanos % MathConstants.NANOSECONDS_PER_MILLISECOND );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
