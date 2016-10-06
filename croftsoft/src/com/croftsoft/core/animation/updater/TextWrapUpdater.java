     package com.croftsoft.core.animation.updater;

     import java.awt.Rectangle;
     import javax.swing.JComponent;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.animation.*;
     import com.croftsoft.core.animation.sprite.TextSprite;

     /*********************************************************************
     * Slides text from one side to the other and then wraps back again.
     *
     * @version
     *   2002-03-23
     * @since
     *   2002-02-22
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  TextWrapUpdater
       implements ComponentUpdater
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final TextSprite  textSprite;

     private final int         deltaX;

     private final int         deltaY;

     private final Rectangle   textWrapArea;

     private final boolean     useComponentBounds;

     private final Rectangle   paintBounds;

     private final Rectangle   newPaintBounds;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * @param  textWrapArea
     *
     *   If the textWrapArea is null, component.getBounds() will be used.
     *********************************************************************/
     public  TextWrapUpdater (
       TextSprite  textSprite,
       int         deltaX,
       int         deltaY,
       Rectangle   textWrapArea )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.textSprite = textSprite );

       this.deltaX = deltaX;

       this.deltaY = deltaY;

       if ( textWrapArea == null )
       {
         this.textWrapArea = new Rectangle ( );

         useComponentBounds = true;
       }
       else
       {
         this.textWrapArea = textWrapArea;

         useComponentBounds = false;
       }

       paintBounds = new Rectangle ( );

       newPaintBounds = new Rectangle ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       if ( ( deltaX == 0 )
         && ( deltaY == 0 ) )
       {
         return;
       }

       if ( useComponentBounds )
       {
         component.getBounds ( textWrapArea );
       }

       textSprite.getPaintBounds ( paintBounds );

       int  x = paintBounds.x + deltaX;

       int  y = paintBounds.y + deltaY;

       boolean  wrapped = false;

       if ( x <= textWrapArea.x - paintBounds.width )
       {
         x = textWrapArea.x + textWrapArea.width - 1;

         wrapped = true;
       }
       else if ( x >= textWrapArea.x + textWrapArea.width )
       {
         x = textWrapArea.x - paintBounds.width + 1;

         wrapped = true;
       }

       if ( y <= textWrapArea.y - paintBounds.height )
       {
         y = textWrapArea.y + textWrapArea.height + paintBounds.height - 1;

         wrapped = true;
       }
       else if (
         y >= textWrapArea.y + textWrapArea.height + paintBounds.height )
       {
         y = textWrapArea.y - paintBounds.height + 1;

         wrapped = true;
       }

       textSprite.setX ( x + textSprite.getX ( ) - paintBounds.x );

       textSprite.setY ( y + textSprite.getY ( ) - paintBounds.y );

       if ( !wrapped )
       {
         textSprite.getPaintBounds ( newPaintBounds );

         paintBounds.add ( newPaintBounds );
       }

       component.repaint ( paintBounds );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
