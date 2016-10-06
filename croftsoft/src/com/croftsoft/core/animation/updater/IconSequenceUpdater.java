     package com.croftsoft.core.animation.updater;

     import java.awt.Rectangle;
     import javax.swing.Icon;
     import javax.swing.JComponent;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.animation.Clock;
     import com.croftsoft.core.animation.ComponentUpdater;
     import com.croftsoft.core.animation.sprite.IconSprite;

     /*********************************************************************
     * Rotates through a sequence of Icons.
     *
     * @version
     *   2003-07-11
     * @since
     *   2002-03-15
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  IconSequenceUpdater
       implements ComponentUpdater
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final IconSprite  iconSprite;

     private final Icon [ ]    icons;

     private final long        framePeriodNanos;

     private final Clock       clock;

     private final Rectangle   oldPaintBounds;

     private final Rectangle   newPaintBounds;

     //

     private long  lastUpdateTimeNanos;

     private int   index;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  IconSequenceUpdater (
       IconSprite  iconSprite,
       Icon [ ]    icons,
       long        framePeriodNanos,
       Clock       clock )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.iconSprite = iconSprite );

       NullArgumentException.check ( this.icons      = icons      );

       this.framePeriodNanos = framePeriodNanos;

       NullArgumentException.check ( this.clock      = clock      );

       oldPaintBounds = new Rectangle ( );

       newPaintBounds = new Rectangle ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       long  updateTimeNanos = clock.currentTimeNanos ( );

       if ( updateTimeNanos < lastUpdateTimeNanos + framePeriodNanos )
       {
         return;
       }

       lastUpdateTimeNanos = updateTimeNanos;

       iconSprite.getPaintBounds ( oldPaintBounds );

       iconSprite.setIcon ( icons [ index ] );

       iconSprite.getPaintBounds ( newPaintBounds );

       newPaintBounds.add ( oldPaintBounds );

       component.repaint ( newPaintBounds );

       index = ( index + 1 ) % icons.length;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }