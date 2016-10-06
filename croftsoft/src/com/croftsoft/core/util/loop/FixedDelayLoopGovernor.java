     package com.croftsoft.core.util.loop;

     import com.croftsoft.core.math.MathConstants;

     /*********************************************************************
     * Maintains a periodic rate by stalling the loop by a fixed delay.
     *
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     * @version
     *   2003-05-22
     * @since
     *   2002-03-07
     *********************************************************************/

     public final class  FixedDelayLoopGovernor
       implements LoopGovernor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final long  delayMillis;

     private final int   delayNanos;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  FixedDelayLoopGovernor (
       long  delayMillis,
       int   delayNanos )
     //////////////////////////////////////////////////////////////////////
     {
       this.delayMillis = delayMillis;

       this.delayNanos  = delayNanos;
     }

     /*********************************************************************
     * Constructs a LoopGovernor with the specified target frequency.
     *
     * @param  frequency
     *
     *   The targeted loop frequency in loops per second.
     *********************************************************************/
     public  FixedDelayLoopGovernor ( double  frequency )
     //////////////////////////////////////////////////////////////////////
     {
       if ( frequency <= 0.0 )
       {
         throw new IllegalArgumentException ( "frequency <= 0.0" );
       }

       long  periodNanos
         = ( long ) ( MathConstants.NANOSECONDS_PER_SECOND / frequency );

       delayMillis
         = periodNanos / MathConstants.NANOSECONDS_PER_MILLISECOND;

       delayNanos = ( int )
         ( periodNanos % MathConstants.NANOSECONDS_PER_MILLISECOND );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Stalls the current Thread by the fixed delay period.
     *********************************************************************/
     public void  govern ( )
       throws InterruptedException
     //////////////////////////////////////////////////////////////////////
     {
       Thread.sleep ( delayMillis, delayNanos );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
