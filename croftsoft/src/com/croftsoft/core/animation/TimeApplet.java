     package com.croftsoft.core.animation;

     import java.awt.Graphics2D;
     import java.util.Date;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.AnimatedApplet;

     /*********************************************************************
     * Example AnimatedApplet implementation.
     *
     * @version
     *   2003-03-07
     * @since
     *   2003-03-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  TimeApplet
       extends AnimatedApplet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       launch ( new TimeApplet ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       component.repaint ( );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics2D )
     //////////////////////////////////////////////////////////////////////
     {
       graphics2D.setColor ( getBackground ( ) );

       graphics2D.fillRect ( 0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE );

       graphics2D.setColor ( getForeground ( ) );

       graphics2D.drawString ( new Date ( ).toString ( ), 0, 10 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
