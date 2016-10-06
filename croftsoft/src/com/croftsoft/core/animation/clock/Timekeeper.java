     package com.croftsoft.core.animation.clock;

     import com.croftsoft.core.animation.Clock;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.MathConstants;

     /*********************************************************************
     * Calculates the time delta.
     *
     * @version
     *   2003-09-10
     * @since
     *   2003-04-02
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Timekeeper
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private Clock   clock;

     private double  timeFactor;

     private long    previousTimeNanos;

     private double  timeDelta;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Timekeeper (
       Clock   clock,
       double  timeFactor )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.clock = clock );

       this.timeFactor = timeFactor;

       previousTimeNanos = clock.currentTimeNanos ( );
     }

     public  Timekeeper ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( SystemClock.INSTANCE, 1.0 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public double  getTimeFactor ( ) { return timeFactor; }

     public void  setTimeFactor ( double  timeFactor )
     //////////////////////////////////////////////////////////////////////
     {
       this.timeFactor = timeFactor;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( )
     //////////////////////////////////////////////////////////////////////
     {
       long  currentTimeNanos = clock.currentTimeNanos ( );

       long  timeDeltaNanos = currentTimeNanos - previousTimeNanos;

       previousTimeNanos = currentTimeNanos;

       timeDelta = MathConstants.SECONDS_PER_NANOSECOND
         * timeDeltaNanos * timeFactor;
     }

     public double  getTimeDelta ( )
     //////////////////////////////////////////////////////////////////////
     {
       return timeDelta;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }