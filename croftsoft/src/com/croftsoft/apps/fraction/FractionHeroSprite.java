     package com.croftsoft.apps.fraction;

     import java.applet.*;
     import java.awt.*;
     import java.awt.event.*;
     import java.awt.geom.*;
     import java.util.Random;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * The Fraction hero sprite.
     *
     * @version
     *   2003-02-12
     * @since
     *   2002-04-28
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  FractionHeroSprite
       implements MouseInputListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private int        x;

     private int        y;

     private int        diameter;

     private int        radius;

     private int        verticalMovementX;

     private int        horizontalMovementY;

     private Point      mousePoint;

     private Color [ ]  colors;

     private int        colorOffset;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *********************************************************************/
     public  FractionHeroSprite ( )
     //////////////////////////////////////////////////////////////////////
     {
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public int  getX ( ) { return x; }

     public int  getY ( ) { return y; }

     public int  getHorizontalMovementY ( ) { return horizontalMovementY; }

     public int  getVerticalMovementX   ( ) { return verticalMovementX;   }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setDiameter ( int  diameter )
     //////////////////////////////////////////////////////////////////////
     {
       this.diameter = diameter;

       this.radius = diameter / 2;

       colors = new Color [ diameter ];

       for ( int  i = 0; i < colors.length; i++ )
       {
         colors [ i ] = new Color (
           ( 256 / diameter ) * ( i + 1 ) - 1,
           ( ( 256 / diameter ) * ( i + 1 ) - 1 ) / 4,
           ( ( 256 / diameter ) * ( i + 1 ) - 1 ) / 2 );
       }
     }

     public void  setX ( int  x ) { this.x = x; }

     public void  setY ( int  y ) { this.y = y; }

     public void  setHorizontalMovementY ( int  horizontalMovementY )
     //////////////////////////////////////////////////////////////////////
     {
       this.horizontalMovementY = horizontalMovementY;
     }

     public void  setVerticalMovementX ( int  verticalMovementX )
     //////////////////////////////////////////////////////////////////////
     {
       this.verticalMovementX = verticalMovementX;
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       if ( mousePoint == null )
       {
         return;
       }

       int  newX = x;

       int  newY = y;

       int  deltaX = mousePoint.x - x;

       int  deltaY = mousePoint.y - y + radius;

       if ( deltaX > 0 )
       {
         newX++;
       }
       else if ( deltaX < 0 )
       {
         newX--;
       }

       if ( deltaY > 0 )
       {
         newY++;
       }
       else if ( deltaY < 0 )
       {
         newY--;
       }

       if ( Math.abs ( deltaX ) > Math.abs ( deltaY ) )
       {
         if ( y == horizontalMovementY )
         {
           x = newX;
         }

         if ( x == verticalMovementX )
         {
           y = newY;
         }
       }
       else
       {
         if ( x == verticalMovementX )
         {
           y = newY;
         }

         if ( y == horizontalMovementY )
         {
           x = newX;
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       colorOffset = ( colorOffset + 1 ) % colors.length;

       for ( int  i = diameter; i > 0; i-- )
       {
         graphics.setColor (
           colors [ ( i - 1 + colorOffset ) % colors.length ] );

         graphics.fillOval ( x - i / 2, y - diameter, i, diameter );
       }
     }

     public void   getPaintBounds ( Rectangle  paintBounds )
     //////////////////////////////////////////////////////////////////////
     {
       paintBounds.x = x - diameter / 2 - 1;

       paintBounds.y = y - diameter - 1;

       paintBounds.width = diameter + 2;

       paintBounds.height = diameter + 2;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  mouseClicked ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  mouseDragged ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
       mousePoint = mouseEvent.getPoint ( );
     }

     public void  mouseEntered ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  mouseExited ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  mouseMoved ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
       mousePoint = mouseEvent.getPoint ( );
     }

     public void  mousePressed ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  mouseReleased ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
