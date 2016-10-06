     package com.croftsoft.apps.sprite;

     import java.awt.Graphics2D;
     import javax.swing.JComponent;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.animation.ComponentPainter;
     import com.croftsoft.core.animation.ComponentUpdater;
     import com.croftsoft.core.animation.sprite.TextSprite;

     /*********************************************************************
     * Monitors and updates the frame rate scrolling text.
     *
     * @version
     *   2002-03-23
     * @since
     *   2002-02-21
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  FrameRateSampler
       implements ComponentPainter, ComponentUpdater
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final TextSprite   textSprite;

     private final long         samplePeriod;

     private final String       prefix;

     //

     private long  frameCount;

     private long  lastUpdateTime;

     private long  lastFrameRate;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  FrameRateSampler (
       TextSprite  textSprite,
       long        samplePeriod,
       String      prefix )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.textSprite = textSprite );

       NullArgumentException.check ( this.prefix      = prefix      );

       this.samplePeriod = samplePeriod;

       lastFrameRate = -1L;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       increaseFrameCount ( );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       increaseFrameCount ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private void  increaseFrameCount ( )
     //////////////////////////////////////////////////////////////////////
     {
       long  updateTime = System.currentTimeMillis ( );

       frameCount++;

       long  timeDelta = updateTime - lastUpdateTime;

       if ( timeDelta < samplePeriod )
       {
         return;
       }

       long  frameRate = Math.round ( frameCount * 1000.0 / timeDelta );

       frameCount = 0;

       lastUpdateTime = updateTime;

       if ( frameRate != lastFrameRate )
       {
         textSprite.setText ( prefix + frameRate );

         lastFrameRate = frameRate;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
