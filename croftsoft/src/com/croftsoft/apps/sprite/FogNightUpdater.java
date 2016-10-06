     package com.croftsoft.apps.sprite;

     import java.awt.Color;
     import java.awt.Rectangle;
     import java.util.Random;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.Clock;
     import com.croftsoft.core.animation.ComponentUpdater;
     import com.croftsoft.core.animation.painter.ColorPainter;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Updates scene shading for fog and night and changes brick color.
     *
     * @version
     *   2002-12-05
     * @since
     *   2002-02-20
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  FogNightUpdater
       implements ComponentUpdater
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  NANOSECONDS_PER_SECOND = 1000000000L;

     private final ColorPainter  fogNightPainter;

     private final ColorPainter  brickColorPainter;

     private final long          fullCyclePeriodNanos;

     private final Clock         clock;

     private final Color [ ]     fogColors;

     private final Color [ ]     nightColors;

     private final Random        brickColorRandom;

     //

     private long  offsetTime;

     private int   oldIndex;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  FogNightUpdater (
       ColorPainter  fogNightPainter,
       ColorPainter  brickColorPainter,
       int           fullCyclePeriod,
       Clock         clock )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.fogNightPainter = fogNightPainter );

       NullArgumentException.check (
         this.brickColorPainter = brickColorPainter );

       if ( fullCyclePeriod < 1 )
       {
         throw new IllegalArgumentException ( "fullCyclePeriod < 1" );
       }

       NullArgumentException.check ( this.clock = clock );

       this.fullCyclePeriodNanos
         = NANOSECONDS_PER_SECOND * fullCyclePeriod;

       nightColors = new Color [ 256 ];

       fogColors   = new Color [ 256 ];

       for ( int  alpha = 0; alpha < 256; alpha++ )
       {
         nightColors [ alpha ] = new Color (   0,   0,   0, alpha );

         fogColors   [ alpha ] = new Color ( 255, 255, 255, alpha );
       }

       brickColorRandom = new Random ( );

       oldIndex = -1; // out of range, force an immediate Color update

       reset ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  reset ( )
     //////////////////////////////////////////////////////////////////////
     {
       offsetTime = -1;
     }

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       long  updateTimeNanos = clock.currentTimeNanos ( );

       if ( offsetTime < 0 )
       {
         offsetTime = updateTimeNanos;
       }

       int  index = ( int )
         ( ( ( ( updateTimeNanos - offsetTime ) % fullCyclePeriodNanos )
         * ( 256 * 6 ) ) / fullCyclePeriodNanos );

       if ( index == oldIndex )
       {
         return;
       }

       if ( index < oldIndex )
       {
         brickColorPainter.setColor (
           new Color (
             brickColorRandom.nextInt ( 256 ),
             brickColorRandom.nextInt ( 256 ),
             brickColorRandom.nextInt ( 256 ) ) );
       }

       oldIndex = index;

       int  phase = index / 256;

       index = index % 256;

       Color  fogNightColor = null;

       if ( phase == 0 )
       {
         //  going from night to day

         fogNightColor = nightColors [ 255 - index ];
       }
       else if ( phase == 1 )
       {
         // day

         fogNightColor = nightColors [ 0 ];
       }       
       else if ( phase == 2 )
       {
         // going from day to fog

         fogNightColor = fogColors [ index ];
       }
       else if ( phase == 3 )
       {
         // going from fog to day

         fogNightColor = fogColors [ 255 - index ];
       }
       else if ( phase == 4 )
       {
         // day

         fogNightColor = fogColors [ 0 ];
       }       
       else if ( phase == 5 )
       {
         // going from day to night

         fogNightColor = nightColors [ index ];
       }

       fogNightPainter.setColor ( fogNightColor );

       component.repaint ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
