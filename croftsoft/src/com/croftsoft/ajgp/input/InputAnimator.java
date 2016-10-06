     package com.croftsoft.ajgp.input;

     import java.awt.Color;
     import java.awt.Graphics2D;
     import java.awt.Point;
     import java.awt.event.KeyAdapter;
     import java.awt.event.KeyEvent;
     import java.awt.event.MouseAdapter;
     import java.awt.event.MouseEvent;
     import java.awt.event.MouseMotionAdapter;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * User input ComponentAnimator implementation example.
     *
     * @version
     *   2003-09-29
     * @since
     *   2003-09-29
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  InputAnimator
       implements ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final Color   BACKGROUND_COLOR = Color.BLACK;

     private static final Color   FOREGROUND_COLOR = Color.GREEN;

     private static final String  TEXT = "CroftSoft Input Example";

     //

     private KeyEvent  keyEvent;

     private Point     mousePoint;

     private boolean   mousePressed;

     private int       x;

     private int       y;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  InputAnimator ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       component.addMouseListener (
         new MouseAdapter ( )
         {
           public void  mousePressed ( MouseEvent  mouseEvent )
           {
             mousePressed = true;
           }
         } );

       component.addMouseMotionListener (
         new MouseMotionAdapter ( )
         {
           public void  mouseMoved ( MouseEvent  mouseEvent )
           {
             mousePoint = mouseEvent.getPoint ( );
           }
         } );

       component.addKeyListener (
         new KeyAdapter ( )
         {
           public void  keyPressed  ( KeyEvent  keyEvent )
           {
             InputAnimator.this.keyEvent = keyEvent;
           }
         } );

       component.requestFocus ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       boolean  repaintRequired = false;

       if ( mousePoint != null )
       {
         repaintRequired = true;

         x = mousePoint.x;

         y = mousePoint.y;

         mousePoint = null;
       }

       if ( keyEvent != null )
       {
         int  keyCode = keyEvent.getKeyCode ( );

         switch ( keyCode )
         {
           case KeyEvent.VK_DOWN:
           case KeyEvent.VK_KP_DOWN:

             repaintRequired = true;

             y++;

             break;

           case KeyEvent.VK_UP:
           case KeyEvent.VK_KP_UP:

             repaintRequired = true;

             y--;

             break;

           case KeyEvent.VK_LEFT:
           case KeyEvent.VK_KP_LEFT:

             repaintRequired = true;

             x--;

             break;

           case KeyEvent.VK_RIGHT:
           case KeyEvent.VK_KP_RIGHT:

             repaintRequired = true;

             x++;

             break;
         }

         keyEvent = null;
       }

       if ( repaintRequired )
       {
         component.repaint ( );
       }
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       graphics.setColor ( BACKGROUND_COLOR );

       graphics.fillRect ( 0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE );

       graphics.setColor ( FOREGROUND_COLOR );

       graphics.drawString ( TEXT, x, y );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }