     package com.croftsoft.core.animation.updater;

     import java.awt.*;
     import java.awt.event.*;
     import javax.swing.*;

     import com.croftsoft.core.animation.ComponentUpdater;

     /*********************************************************************
     * Scrolls the view when the mouse is on the edges.
     *
     * @version
     *   2003-07-05
     * @since
     *   2003-03-08
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  EdgeScrollUpdater
       implements ComponentUpdater
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final static int  DEFAULT_SCROLL_RATE = 1;

     private final static int  EDGE_SIZE_DIVIDER   = 10;

     //

     private final int        width;

     private final int        height;

     private final Dimension  edgeSize;

     private final Rectangle  bounds;

     private final int        scrollRate;

     private final boolean    wrapAround;

     //

     private Point  mousePoint;

     private int    translateX;

     private int    translateY;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  EdgeScrollUpdater (
       JComponent  component,
       int         width,
       int         height,
       Dimension   edgeSize,
       int         scrollRate,
       boolean     wrapAround )
     //////////////////////////////////////////////////////////////////////
     {
       this.width    = width;

       this.height   = height;

       this.edgeSize = edgeSize;

       if ( scrollRate < 1 )
       {
         throw new IllegalArgumentException ( "scrollRate < 1" );
       }

       this.scrollRate = scrollRate;

       this.wrapAround = wrapAround;

       bounds = new Rectangle ( );

       component.addMouseMotionListener (
         new MouseMotionAdapter ( )
         {
           public void  mouseMoved ( MouseEvent  mouseEvent )
           {
             mousePoint = mouseEvent.getPoint ( );
           }
         } );
     }

     public  EdgeScrollUpdater (
       JComponent  component,
       int         width,
       int         height )
     //////////////////////////////////////////////////////////////////////
     {
       this ( component, width, height, null, DEFAULT_SCROLL_RATE, false );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int  getTranslateX ( ) { return translateX; }

     public int  getTranslateY ( ) { return translateY; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       if ( mousePoint == null )
       {
         return;
       }

       component.getBounds ( bounds );

       int  x = mousePoint.x;

       int  y = mousePoint.y;

       boolean  onEdge = false;

       int  edgeWidth;

       int  edgeHeight;

       if ( edgeSize == null )
       {
         edgeWidth  = bounds.width  / EDGE_SIZE_DIVIDER;

         edgeHeight = bounds.height / EDGE_SIZE_DIVIDER;
       }
       else
       {
         edgeWidth  = edgeSize.width;

         edgeHeight = edgeSize.height;
       }

       if ( x < edgeWidth )
       {
         if ( wrapAround || ( translateX < 0 ) )
         {
           onEdge = true;

           translateX += scrollRate;

           if ( translateX > 0 )
           {
             if ( wrapAround )
             {
               translateX -= width;
             }
             else
             {
               translateX = 0;
             }
           }
         }
       }
       else if ( x > bounds.width - edgeWidth )
       {
         if ( wrapAround || ( translateX > -( width - bounds.width ) ) )
         {
           onEdge = true;

           translateX -= scrollRate;
         }
       }
         
       if ( translateX < -( width - bounds.width ) )
       {
         if ( wrapAround )
         {
           translateX += width;
         }
         else
         {
           translateX = -( width - bounds.width );
         }
       }

       if ( y < edgeHeight )
       {
         if ( wrapAround || ( translateY < 0 ) )
         {
           onEdge = true;

           translateY += scrollRate;

           if ( translateY > 0 )
           {
             if ( wrapAround )
             {
               translateY -= height;
             }
             else
             {
               translateY = 0;
             }
           }
         }
       }
       else if ( y > bounds.height - edgeHeight )
       {
         if ( wrapAround || ( translateY > -( height - bounds.height ) ) )
         {
           onEdge = true;

           translateY -= scrollRate;
         }
       }

       if ( translateY < -( height - bounds.height ) )
       {
         if ( wrapAround )
         {
           translateY += height;
         }
         else
         {
           translateY = -( height - bounds.height );
         }
       }

       if ( !onEdge )
       {
         mousePoint = null;
       }
       else
       {
         component.repaint ( );
       }
     }

     public void  translate ( Graphics  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       graphics.translate ( translateX, translateY );
     }

     public void  translate ( Point  point )
     //////////////////////////////////////////////////////////////////////
     {
       point.x += translateX;

       point.y += translateY;
     }

     public void  translateReverse ( Graphics  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       graphics.translate ( -translateX, -translateY );
     }

     public void  translateReverse ( Point  point )
     //////////////////////////////////////////////////////////////////////
     {
       point.x -= translateX;

       point.y -= translateY;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }