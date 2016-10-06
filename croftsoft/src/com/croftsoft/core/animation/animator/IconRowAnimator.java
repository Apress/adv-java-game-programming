     package com.croftsoft.core.animation.animator;

     import java.awt.*;
     import java.awt.event.*;
     import java.awt.geom.*;
     import javax.swing.*;

     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Displays a quantity as a row of icons.
     *
     * <p>
     * Useful for display number of remaining lives, ammo, population, etc.
     * If the area to display the icons is too small, the icon spacing
     * will be adjusted so that icons overlap each other to fit.
     * </p>
     *
     * @version
     *   2003-03-28
     * @since
     *   2003-03-28
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  IconRowAnimator
       implements ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final AffineTransform  affineTransform;

     private final Rectangle        newRectangle;

     private final Rectangle        repaintArea;

     private final Rectangle        componentBounds;

     //

     private boolean    rectangleIsRelative;

     private Rectangle  oldPaintArea;

     private Rectangle  newPaintArea;

     private Icon       icon;

     private int        count;

     private double     iconSpacing;

     private boolean    updateRequired;

     private int        x;

     private int        y;

     private int        width;

     private int        height;

     private int        newCount;

     private Icon       newIcon;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     private  IconRowAnimator (
       Icon        icon,
       int         count,
       Rectangle   rectangle,
       JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       affineTransform = new AffineTransform ( );

       componentBounds = new Rectangle ( );

       oldPaintArea    = new Rectangle ( );

       newPaintArea    = new Rectangle ( );

       repaintArea     = new Rectangle ( );

       newRectangle    = new Rectangle ( );

       setIcon ( icon );

       setRectangle ( rectangle );

       setCount ( count );

       update ( component );

       if ( ( rectangle == null )
         && ( component != null ) )
       {
         component.addComponentListener (
           new ComponentAdapter ( )
           {
             public void  componentResized (
               ComponentEvent  componentEvent )
             {
               updateRequired = true;
             }
           } );
       }
     }

     /*********************************************************************
     * Draws the icon row in the rectangle.
     *********************************************************************/
     public  IconRowAnimator (
       Icon       icon,
       int        count,
       Rectangle  rectangle )
     //////////////////////////////////////////////////////////////////////
     {
       this ( icon, count, rectangle, null );

       NullArgumentException.check ( rectangle );
     }

     /*********************************************************************
     * Draws the icon row at the bottom left of the component.
     *********************************************************************/
     public  IconRowAnimator (
       Icon        icon,
       int         count,
       JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       this ( icon, count, null, component );

       NullArgumentException.check ( component );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int  getCount ( ) { return count; }

     public Icon  getIcon ( ) { return icon; }

     public void  getRectangle ( Rectangle  rectangle )
     //////////////////////////////////////////////////////////////////////
     {
       rectangle.setBounds ( x, y, width, height );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setCount ( int  count )
     //////////////////////////////////////////////////////////////////////
     {
       if ( count < 0 )
       {
         throw new IllegalArgumentException ( "count < 0" );
       }

       if ( this.count == count )
       {
         return;
       }

       newCount = count;

       updateRequired = true;
     }

     public void  setIcon ( Icon  icon )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( icon );

       newIcon = icon;

       updateRequired = true;
     }

     public void  setRectangle ( Rectangle  rectangle )
     //////////////////////////////////////////////////////////////////////
     {
       rectangleIsRelative = rectangle == null;

       if ( !rectangleIsRelative )
       {
         newRectangle.setBounds ( rectangle );
       }

       updateRequired = true;
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !updateRequired )
       {
         return;
       }

       updateRequired = false;

       icon  = newIcon;

       count = newCount;

       int  iconWidth  = icon.getIconWidth  ( );

       int  iconHeight = icon.getIconHeight ( );

       if ( rectangleIsRelative && ( component != null ) )
       {
         component.getBounds ( componentBounds );

         x = 1;

         y = componentBounds.height - iconHeight - 1;

         width = componentBounds.width / 3;

         height = iconHeight;
       }
       else
       {
         x      = newRectangle.x;

         y      = newRectangle.y;

         width  = newRectangle.width;

         height = newRectangle.height;
       }

       if ( ( count > 0         )
         && ( width > iconWidth ) )
       {
         iconSpacing = ( width - iconWidth ) / ( double ) count;

         if ( iconSpacing > iconWidth )
         {
           iconSpacing = iconWidth;
         }

         newPaintArea.setBounds (
           x, y,
           ( int ) ( iconSpacing * ( count - 1 ) ) + iconWidth + 1,
           iconHeight );
       }
       else
       {
         iconSpacing = 0.0;

         newPaintArea.setBounds ( x, y, iconWidth, iconHeight );
       }

       if ( component != null )
       {
         Rectangle2D.union ( oldPaintArea, newPaintArea, repaintArea );

         component.repaint ( repaintArea );

         Rectangle  swapRectangle = oldPaintArea;

         oldPaintArea = newPaintArea;

         newPaintArea = swapRectangle;
       }       
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
/*
       graphics.setColor ( Color.RED );

       graphics.drawRect (
         oldPaintArea.x,
         oldPaintArea.y,
         oldPaintArea.width,
         oldPaintArea.height );

       graphics.setColor ( Color.BLUE );

       graphics.drawRect (
         newPaintArea.x,
         newPaintArea.y,
         newPaintArea.width,
         newPaintArea.height );

       graphics.setColor ( Color.MAGENTA );

       graphics.drawRect (
         repaintArea.x,
         repaintArea.y,
         repaintArea.width,
         repaintArea.height );
*/

       AffineTransform  oldAffineTransform
         = graphics.getTransform ( );

       affineTransform.setToTranslation ( iconSpacing, 0.0 );

       for ( int  i = 0; i < count; i++ )
       {
         icon.paintIcon ( component, graphics, x, y );

         graphics.transform ( affineTransform );
       }

       graphics.setTransform ( oldAffineTransform );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }