     package com.croftsoft.core.animation.animator;

     import java.awt.*;
     import java.awt.event.*;
     import javax.swing.*;

     import com.croftsoft.core.animation.ComponentAnimator;

     /*********************************************************************
     * Animates an Icon at the mouse position.
     *
     * @version
     *   2003-07-12
     * @since
     *   2002-02-26
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  CursorAnimator
       implements ComponentAnimator, MouseListener, MouseMotionListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Rectangle  oldPaintBounds;

     private final Rectangle  newPaintBounds;

     //

     private Icon     currentIcon;

     private Icon     nextIcon;

     private Icon     mouseReleasedIcon;

     private Icon     mousePressedIcon;

     private int      hotSpotX;

     private int      hotSpotY;

     private int      x;

     private int      y;

     private boolean  updated;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  CursorAnimator (
       Icon       mouseReleasedIcon,
       Icon       mousePressedIcon,
       Point      hotSpot,
       Component  component )
     //////////////////////////////////////////////////////////////////////
     {
       setMouseReleasedIcon ( mouseReleasedIcon, hotSpot );

       setMousePressedIcon  ( mousePressedIcon , hotSpot );

       oldPaintBounds = new Rectangle ( );

       newPaintBounds = new Rectangle ( );

       if ( component != null )
       {
         component.addMouseMotionListener ( this );

         component.addMouseListener       ( this );

         component.setCursor ( new Cursor ( Cursor.CROSSHAIR_CURSOR ) );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public Icon  getMouseReleasedIcon ( ) { return mouseReleasedIcon; }

     public Icon  getMousePressedIcon  ( ) { return mousePressedIcon;  }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setMouseReleasedIcon (
       Icon   mouseReleasedIcon,
       Point  hotSpot )
     //////////////////////////////////////////////////////////////////////
     {
       this.mouseReleasedIcon = mouseReleasedIcon;

       updateHotSpot ( hotSpot );
     }

     public void  setMousePressedIcon (
       Icon   mousePressedIcon,
       Point  hotSpot )
     //////////////////////////////////////////////////////////////////////
     {
       this.mousePressedIcon = mousePressedIcon;

       updateHotSpot ( hotSpot );
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !updated )
       {
         return;
       }

       updated = false;

       currentIcon = nextIcon;

       if ( currentIcon != null )
       {
         newPaintBounds.x      = x;

         newPaintBounds.y      = y;

         newPaintBounds.width  = currentIcon.getIconWidth  ( );

         newPaintBounds.height = currentIcon.getIconHeight ( );
       }
       else
       {
         newPaintBounds.width = 0;
       }

       component.repaint ( oldPaintBounds );

       component.repaint ( newPaintBounds );

       oldPaintBounds.setBounds ( newPaintBounds );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       if ( currentIcon != null )
       {
         currentIcon.paintIcon ( component, graphics, x, y );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // interface MouseListener methods
     //////////////////////////////////////////////////////////////////////

     public void  mouseClicked ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  mouseEntered ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
       updated = true;

       nextIcon = mouseReleasedIcon;
     }

     public void  mouseExited ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
       updated = true;

       nextIcon = null;
     }

     public void  mousePressed ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
       updated = true;

       nextIcon = mousePressedIcon;       
     }

     public void  mouseReleased ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
       updated = true;

       nextIcon = mouseReleasedIcon;
     }

     //////////////////////////////////////////////////////////////////////
     // interface MouseMotionListener methods
     //////////////////////////////////////////////////////////////////////

     public void  mouseDragged ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
       mouseMoved ( mouseEvent );
     }

     public void  mouseMoved ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
       if ( currentIcon == null )
       {
         return;
       }       

       updated = true;

       x = mouseEvent.getX ( ) - hotSpotX;

       y = mouseEvent.getY ( ) - hotSpotY;       
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private void  updateHotSpot ( Point  hotSpot )
     //////////////////////////////////////////////////////////////////////
     {
       updated = true;

       if ( hotSpot != null )
       {
         hotSpotX = hotSpot.x;

         hotSpotY = hotSpot.y;
       }
       else if ( mouseReleasedIcon != null )
       {
         hotSpotX = mouseReleasedIcon.getIconWidth  ( ) / 2;

         hotSpotY = mouseReleasedIcon.getIconHeight ( ) / 2;
       }
       else if ( mousePressedIcon != null )
       {
         hotSpotX = mousePressedIcon.getIconWidth  ( ) / 2;

         hotSpotY = mousePressedIcon.getIconHeight ( ) / 2;
       }
       else
       {
         hotSpotX = 0;

         hotSpotY = 0;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }