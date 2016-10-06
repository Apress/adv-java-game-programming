     package com.croftsoft.apps.sprite;

     import java.awt.Rectangle;
     import javax.swing.Icon;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.Clock;
     import com.croftsoft.core.animation.ComponentUpdater;
     import com.croftsoft.core.animation.sprite.IconSprite;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Switches the Sprite Icon based upon heading changes.
     *
     * @version
     *   2002-03-23
     * @since
     *   2002-02-24
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ImpactUpdater
       implements ComponentUpdater
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final IconSprite  iconSprite;

     private final Icon        normalIcon;

     private final Icon        impactIcon;

     private final long        impactDuration;

     private final Clock       clock;

     private final Rectangle   repaintRegion;

     //

     private double   oldHeading;

     private long     lastImpactTime;

     private boolean  isImpacted;

     private Icon     icon;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ImpactUpdater (
       IconSprite  iconSprite,
       Icon        normalIcon,
       Icon        impactIcon,
       long        impactDuration,
       Clock       clock )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.iconSprite = iconSprite );

       NullArgumentException.check ( this.normalIcon = normalIcon );

       NullArgumentException.check ( this.impactIcon = impactIcon );

       this.impactDuration = impactDuration;

       NullArgumentException.check ( this.clock      = clock      );

       repaintRegion = new Rectangle ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       long  updateTimeNanos = clock.currentTimeNanos ( );

       if ( iconSprite.getHeading ( ) != oldHeading )
       {
         oldHeading = iconSprite.getHeading ( );

         icon = impactIcon;

         lastImpactTime = updateTimeNanos;

         isImpacted = true;
       }
       else if ( isImpacted
         && ( updateTimeNanos >= lastImpactTime + impactDuration ) )
       {
         icon = normalIcon;

         isImpacted = false;
       }
       else
       {
         return;
       }

       if ( icon == iconSprite.getIcon ( ) )
       {
         return;
       }

       iconSprite.getPaintBounds ( repaintRegion );

       component.repaint ( repaintRegion );

       iconSprite.setIcon ( icon );

       iconSprite.getPaintBounds ( repaintRegion );

       component.repaint ( repaintRegion );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
