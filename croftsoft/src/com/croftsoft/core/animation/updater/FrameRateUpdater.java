     package com.croftsoft.core.animation.updater;

     import javax.swing.JComponent;

     import com.croftsoft.core.animation.ComponentUpdater;

     /*********************************************************************
     * Samples and prints the frame rate.
     *
     * @version
     *   2003-07-05
     * @since
     *   2003-02-12
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  FrameRateUpdater
       implements ComponentUpdater
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final double  MILLISECONDS_PER_SECOND = 1000.0;

     private static final long    SAMPLE_PERIOD_IN_MILLIS = 10000;

     //

     private final boolean  printFrameRate;

     //

     private long    frameCount;

     private double  frameRate;

     private long    lastUpdateTime;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  FrameRateUpdater ( boolean  printFrameRate )
     //////////////////////////////////////////////////////////////////////
     {
       this.printFrameRate = printFrameRate;
     }

     public  FrameRateUpdater ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( false );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       long  updateTime = System.currentTimeMillis ( );

       frameCount++;

       long  timeDelta = updateTime - lastUpdateTime;

       if ( timeDelta < SAMPLE_PERIOD_IN_MILLIS )
       {
         return;
       }

       if ( timeDelta > 1.5 * SAMPLE_PERIOD_IN_MILLIS )
       {
         lastUpdateTime = updateTime;

         frameCount = 0;

         return;
       }

       frameRate = frameCount * MILLISECONDS_PER_SECOND / timeDelta;

       frameCount = 0;

       lastUpdateTime = updateTime;

       if ( printFrameRate )
       {
         System.out.println ( "Frames per second:  " + frameRate );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public double  getFrameRate ( )
     //////////////////////////////////////////////////////////////////////
     {
       return frameRate;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }