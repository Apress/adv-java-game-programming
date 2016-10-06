     package com.croftsoft.core.animation.clock;

     import com.croftsoft.core.animation.Clock;
     import com.croftsoft.core.math.MathConstants;

     /*********************************************************************
     * Uses the system clock to provide the current time in nanoseconds.
     *
     * @version
     *   2003-04-02
     * @since
     *   2002-03-09
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SystemClock
       implements Clock
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final SystemClock  INSTANCE = new SystemClock ( );

     public long  currentTimeNanos ( )
     //////////////////////////////////////////////////////////////////////
     {
       return MathConstants.NANOSECONDS_PER_MILLISECOND
         * System.currentTimeMillis ( );
     }

     private  SystemClock ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
