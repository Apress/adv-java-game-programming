     package com.croftsoft.core.animation.animator;

     import java.awt.Graphics2D;
     import java.io.Serializable;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.ComponentAnimator;

     /*********************************************************************
     * Animates text.
     *
     * @version
     *   2002-03-31
     * @since
     *   2002-03-31
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  TextAnimator
       implements ComponentAnimator, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private String  text;

     private int     deltaX;

     private int     deltaY;

     private int     x;

     private int     y;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  TextAnimator ( )
     //////////////////////////////////////////////////////////////////////
     {
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int     getDeltaX ( ) { return deltaX; }

     public int     getDeltaY ( ) { return deltaY; }

     public String  getText   ( ) { return text;   }

     public int     getX      ( ) { return x;      }

     public int     getY      ( ) { return y;      }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setDeltaX ( int     deltaX ) { this.deltaX = deltaX; }

     public void  setDeltaY ( int     deltaY ) { this.deltaY = deltaY; }

     public void  setText   ( String  text   ) { this.text   = text;   }

     public void  setX      ( int     x      ) { this.x      = x;      }

     public void  setY      ( int     y      ) { this.y      = y;      }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       x += deltaX;

       y += deltaY;

       int  componentWidth  = component.getWidth  ( );

       int  componentHeight = component.getHeight ( );

       if ( x > componentWidth )
       {
         x = 0;
       }
       else if ( x < 0 )
       {
         x = componentWidth;
       }
       
       if ( y > componentHeight )
       {
         y = 0;
       }
       else if ( y < 0 )
       {
         y = componentHeight;
       }

       component.repaint ( );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       graphics.setColor ( component.getForeground ( ) );

       graphics.drawString ( text, x, y );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }