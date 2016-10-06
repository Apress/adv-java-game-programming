     package com.croftsoft.core.animation.animator;

     import java.awt.*;
     import java.awt.geom.*;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.awt.font.FontLib;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.MathConstants;

     /*********************************************************************
     * Samples and displays the frame rate.
     *
     * @version
     *   2003-07-07
     * @since
     *   2003-02-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  FrameRateAnimator
       implements ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  SAMPLE_PERIOD_IN_MILLIS
       = 10 * MathConstants.MILLISECONDS_PER_SECOND;

     private static final int   MAX_FRAME_RATE = 999;

     //

     private final Color      color;

     private final Font       font;

     private final Rectangle  repaintBounds;

     private final float      x;

     private final float      y;

     //

     private boolean  disabled;

     private boolean  oldDisabled;

     private long     frameCount;

     private long     lastUpdateTime;

     private double   frameRate;

     private String   frameRateString;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  FrameRateAnimator (
       Color        color,
       Font         font,
       Rectangle2D  textLayoutBounds )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.color = color );

       NullArgumentException.check ( this.font  = font  );

       NullArgumentException.check ( textLayoutBounds );

       x = ( float ) textLayoutBounds.getX ( );

       y = ( float ) -textLayoutBounds.getY ( );

       repaintBounds = textLayoutBounds.getBounds ( );

       repaintBounds.y = 0;

       frameRateString = "???";
     }

     public  FrameRateAnimator (
       Component  component,
       Color      color )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         color,
         component.getFont ( ),
         FontLib.getTextLayoutBounds (
           component, Integer.toString ( MAX_FRAME_RATE ) ) );
     }

     public  FrameRateAnimator ( Component  component )
     //////////////////////////////////////////////////////////////////////
     {
       this ( component, component.getForeground ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       if ( oldDisabled != disabled )
       {
         component.repaint ( );

         oldDisabled = disabled;
       }

       if ( disabled )
       {
         return;
       }

       long  updateTime = System.currentTimeMillis ( );

       frameCount++;

       long  timeDelta = updateTime - lastUpdateTime;

       if ( timeDelta < SAMPLE_PERIOD_IN_MILLIS )
       {
         return;
       }

       frameRate = frameCount * MathConstants.MILLISECONDS_PER_SECOND
         / ( double ) timeDelta;

       if ( frameRate > MAX_FRAME_RATE )
       {
         frameRateString = ">>>";
       }
       else
       {
         frameRateString = Long.toString ( Math.round ( frameRate ) );
       }

       frameCount = 0;

       lastUpdateTime = updateTime;

       component.repaint ( repaintBounds );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       if ( disabled )
       {
         return;
       }

       graphics.setColor ( color );

       graphics.setFont ( font );

       graphics.drawString ( frameRateString, x, y );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public double  getFrameRate ( )
     //////////////////////////////////////////////////////////////////////
     {
       return frameRate;
     }

     public void  toggle ( )
     //////////////////////////////////////////////////////////////////////
     {
       disabled = !disabled;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
