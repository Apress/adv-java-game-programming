     package com.croftsoft.ajgp.anim;

     import java.awt.Graphics2D;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * ComponentAnimator implementation example.
     *
     * @version
     *   2003-05-06
     * @since
     *   2003-05-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ExampleAnimator
       implements ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final String  text;

     private final int     deltaX;

     private final int     deltaY;

     //

     private int  x;

     private int  y;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ExampleAnimator (
       String  text,
       int     deltaX,
       int     deltaY )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.text = text );

       this.deltaX = deltaX;

       this.deltaY = deltaY;
     }

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