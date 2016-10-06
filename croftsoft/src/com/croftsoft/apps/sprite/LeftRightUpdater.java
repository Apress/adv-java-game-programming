     package com.croftsoft.apps.sprite;

     import java.awt.Rectangle;
     import javax.swing.Icon;
     import javax.swing.JComponent;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.animation.ComponentUpdater;
     import com.croftsoft.core.animation.sprite.IconSprite;

     /*********************************************************************
     * Switches the Icon based upon the Sprite heading.
     *
     * @version
     *   2002-03-23
     * @since
     *   2002-02-24
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  LeftRightUpdater
       implements ComponentUpdater
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final IconSprite  iconSprite;

     private final Icon        lookLeftIcon;

     private final Icon        lookRightIcon;

     private final Rectangle   repaintRegion;

     //

     private double  oldHeading;

     private Icon    oldIcon;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  LeftRightUpdater (
       IconSprite  iconSprite,
       Icon        lookLeftIcon,
       Icon        lookRightIcon )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.iconSprite    = iconSprite    );

       NullArgumentException.check ( this.lookLeftIcon  = lookLeftIcon  );

       NullArgumentException.check ( this.lookRightIcon = lookRightIcon );

       repaintRegion = new Rectangle ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       double  heading = iconSprite.getHeading ( );

       if ( heading == oldHeading )
       {
         return;
       }

       oldHeading = heading;

       Icon  icon = lookRightIcon;

       if ( ( heading >     Math.PI / 2 )
         && ( heading < 3 * Math.PI / 2 ) )
       {
         icon = lookLeftIcon;
       }

       if ( icon == oldIcon )
       {
         return;
       }

       iconSprite.getPaintBounds ( repaintRegion );

       component.repaint ( repaintRegion );

       iconSprite.setIcon ( icon );

       oldIcon = icon;

       iconSprite.getPaintBounds ( repaintRegion );

       component.repaint ( repaintRegion );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
