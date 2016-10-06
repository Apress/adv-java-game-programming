     package com.croftsoft.core.net.http.msg;

     import java.io.*;
     import java.net.*;

     import com.croftsoft.core.io.Parser;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.math.MathConstants;
     import com.croftsoft.core.net.http.HttpLib;
     import com.croftsoft.core.util.loop.FixedDelayLoopGovernor;
     import com.croftsoft.core.util.loop.Loopable;
     import com.croftsoft.core.util.loop.Looper;
     import com.croftsoft.core.util.queue.Queue;

     /*********************************************************************
     * Polls a server for messages and downloads them to a local queue.
     *
     * @version
     *   2003-06-12
     * @since
     *   2000-04-27
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  HttpMessagePoller
       implements Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final long     DEFAULT_POLLING_PERIOD_MIN
       = MathConstants.MILLISECONDS_PER_SECOND;

     public static final long     DEFAULT_POLLING_PERIOD_MAX
       = MathConstants.MILLISECONDS_PER_DAY;
                                
     public static final long     DEFAULT_POLLING_PERIOD_INIT
       = DEFAULT_POLLING_PERIOD_MIN;

     public static final double   DEFAULT_POLLING_PERIOD_MULT
       = 1.1;

     public static final double   DEFAULT_POLLING_PERIOD_DIVI
       = 1.1;

     public static final long     DEFAULT_POLLING_PERIOD_INCR
       = MathConstants.MILLISECONDS_PER_SECOND;

     public static final String   THREAD_NAME = "HttpMessagePoller";

     public static final int      THREAD_PRIORITY = Thread.MIN_PRIORITY;

     public static final boolean  USE_DAEMON_THREAD = true;

     //

     private static final boolean  DEBUG = false;

     //

     private final URL     url;

     private final String  userAgent;

     private final String  contentType;

     private final Parser  parser;

     private final Looper  looper;

     private final Queue   incomingQueue;

     private final long    pollingPeriodMin;

     private final long    pollingPeriodMax;

     private final double  pollingPeriodMult;

     private final double  pollingPeriodDivi;

     private final long    pollingPeriodIncr;

     //

     private byte [ ]  requestBytes;

     private long      pollingPeriod;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * All polling period values are in milliseconds.
     *
     * @param  pollingPeriodIncr
     *
     *   Minimum incremental change whenever the polling period is
     *   increased or decreased, regardless of the pollingPeriodMult
     *   or pollingPeriodDivi value.
     *********************************************************************/
     public  HttpMessagePoller (
       URL       url,
       String    userAgent,
       String    contentType,
       byte [ ]  requestBytes,
       Parser    parser,
       Queue     incomingQueue,
       long      pollingPeriodMin,
       long      pollingPeriodMax,
       long      pollingPeriodInit,
       double    pollingPeriodMult,
       double    pollingPeriodDivi,
       long      pollingPeriodIncr )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.url           = url           );

       NullArgumentException.check ( this.userAgent     = userAgent     );

       NullArgumentException.check ( this.contentType   = contentType   );

       NullArgumentException.check ( this.parser        = parser        );

       NullArgumentException.check ( this.incomingQueue = incomingQueue );

       setRequestBytes ( requestBytes );

       if ( pollingPeriodMin < 0 )
       {
         throw new IllegalArgumentException ( "pollingPeriodMin < 0" );
       }

       if ( pollingPeriodMax < pollingPeriodMin )
       {
         throw new IllegalArgumentException (
           "pollingPeriodMax < pollingPeriodMin" );
       }

       if ( pollingPeriodInit < pollingPeriodMin )
       {
         throw new IllegalArgumentException (
           "pollingPeriodInit < pollingPeriodMin" );
       }

       if ( pollingPeriodInit > pollingPeriodMax )
       {
         throw new IllegalArgumentException (
           "pollingPeriodInit > pollingPeriodMax" );
       }

       if ( pollingPeriodMult < 1.0 )
       {
         throw new IllegalArgumentException ( "pollingPeriodMult < 1.0" );
       }

       if ( pollingPeriodDivi < 1.0 )
       {
         throw new IllegalArgumentException ( "pollingPeriodDivi < 1.0" );
       }

       if ( pollingPeriodIncr < 0 )
       {
         throw new IllegalArgumentException ( "pollingPeriodIncr < 0" );
       }

       looper = new Looper (
         new Loopable ( )
         {
           public boolean  loop ( )
           {
             return HttpMessagePoller.this.loop ( );
           }
         },
         new FixedDelayLoopGovernor ( pollingPeriodInit, 0 ),
         null,
         THREAD_NAME,
         THREAD_PRIORITY,
         USE_DAEMON_THREAD );

       this.pollingPeriodMin  = pollingPeriodMin;

       this.pollingPeriodMax  = pollingPeriodMax;

       this.pollingPeriodMult = pollingPeriodMult;

       this.pollingPeriodDivi = pollingPeriodDivi;

       this.pollingPeriodIncr = pollingPeriodIncr;

       pollingPeriod = pollingPeriodInit;
     }

     /*********************************************************************
     * Convenience constructor using default polling period values.
     *********************************************************************/
     public  HttpMessagePoller (
       URL       url,
       String    userAgent,
       String    contentType,
       byte [ ]  requestBytes,
       Parser    contentParser,
       Queue     incomingQueue )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         url,
         userAgent,
         contentType,
         requestBytes,
         contentParser,
         incomingQueue,
         DEFAULT_POLLING_PERIOD_MIN,
         DEFAULT_POLLING_PERIOD_MAX,
         DEFAULT_POLLING_PERIOD_INIT,
         DEFAULT_POLLING_PERIOD_MULT,
         DEFAULT_POLLING_PERIOD_DIVI,
         DEFAULT_POLLING_PERIOD_INCR );
     }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setRequestBytes ( byte [ ]  requestBytes )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.requestBytes = requestBytes );
     }

     public synchronized long  setPollingPeriod ( long  pollingPeriod )
     //////////////////////////////////////////////////////////////////////
     {
       if ( pollingPeriod < pollingPeriodMin )
       {
         pollingPeriod = pollingPeriodMin;
       }

       if ( pollingPeriod > pollingPeriodMax )
       {
         pollingPeriod = pollingPeriodMax;
       }

       this.pollingPeriod = pollingPeriod;

       looper.setLoopGovernor (
         new FixedDelayLoopGovernor ( pollingPeriod, 0 ) );

       return pollingPeriod;       
     }

     //////////////////////////////////////////////////////////////////////
     // lifecycle methods
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
     // private methods
     //////////////////////////////////////////////////////////////////////

     private boolean  loop ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         Object  response = HttpLib.post ( 
           url,
           requestBytes,
           userAgent,
           contentType,
           parser );

         if ( response != null )
         {
           incomingQueue.append ( response );

           decreasePollingPeriod ( );
         }
         else
         {
           increasePollingPeriod ( );
         }

         return true;
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         increasePollingPeriod ( );

         return true;
       }
     }

     private void  increasePollingPeriod ( )
     //////////////////////////////////////////////////////////////////////
     {
       long  newPollingPeriod
         = ( long ) ( pollingPeriod * pollingPeriodMult );

       if ( newPollingPeriod < pollingPeriod + pollingPeriodIncr )
       {
         newPollingPeriod = pollingPeriod + pollingPeriodIncr;
       }

       if ( newPollingPeriod > pollingPeriodMax )
       {
         newPollingPeriod = pollingPeriodMax;
       }

       if ( pollingPeriod != newPollingPeriod )
       {
         if ( DEBUG )
         {
           System.out.println (
             "Increasing polling period from " + pollingPeriod
             + " to " + newPollingPeriod + " milliseconds." );
         }

         looper.setLoopGovernor (
           new FixedDelayLoopGovernor ( newPollingPeriod, 0 ) );

         pollingPeriod = newPollingPeriod;
       }
     }

     private void  decreasePollingPeriod ( )
     //////////////////////////////////////////////////////////////////////
     {
       long  newPollingPeriod
         = ( long ) ( pollingPeriod / pollingPeriodDivi );

       if ( newPollingPeriod > pollingPeriod - pollingPeriodIncr )
       {
         newPollingPeriod = pollingPeriod - pollingPeriodIncr;
       }

       if ( newPollingPeriod < pollingPeriodMin )
       {
         newPollingPeriod = pollingPeriodMin;
       }

       if ( pollingPeriod != newPollingPeriod )
       {
         if ( DEBUG )
         {
           System.out.println (
             "Decreasing polling period from " + pollingPeriod
             + " to " + newPollingPeriod + " milliseconds." );
         }

         looper.setLoopGovernor (
           new FixedDelayLoopGovernor ( newPollingPeriod, 0 ) );

         pollingPeriod = newPollingPeriod;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
