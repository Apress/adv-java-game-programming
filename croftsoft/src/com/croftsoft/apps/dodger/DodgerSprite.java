     package com.croftsoft.apps.dodger;

     import java.applet.*;
     import java.awt.*;
     import java.awt.event.*;
     import java.awt.geom.*;
     import java.beans.*;
     import java.util.Random;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.animation.*;
     import com.croftsoft.core.animation.sprite.IconSprite;

     /*********************************************************************
     * An IconSprite subclass representing the Dodger.
     *
     * @version
     *   2003-07-12
     * @since
     *   2002-03-18
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  DodgerSprite
       extends IconSprite
       implements KeyListener, MouseInputListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  IDLE_TIMEOUT_MILLIS         = 60 * 1000;

     private static final long  INIT_RECHARGE_PERIOD_MILLIS = 1;

     private static final long  BANG_PERIOD_MILLIS          = 300;

     private static final long  RECOVERY_PERIOD_MILLIS      = 1000;

     //

     private static final int   STATE_GREEN  = 0;

     private static final int   STATE_YELLOW = 1;

     private static final int   STATE_RED    = 2;

     private static final int   STATE_BANG   = 3;

     private static final int   STATE_BOOM   = 4;

     //

     private final Icon         greenIcon;

     private final Icon         yellowIcon;

     private final Icon         redIcon;

     private final Icon         bangIcon;

     private final Icon         boomIcon;

     private final AudioClip    bangAudioClip;

     private final Rectangle    bounds;

     private final int          dodgerWidth;

     private final int          dodgerHalfWidth;

     private final int          dodgerHalfHeight;

     private final int          dodgerVerticalOffset;

     private final Rectangle2D  shootArea;

     private final Rectangle    paintBounds;

     //

     private long     rechargePeriodMillis;

     private boolean  bang;

     private long     bangTimeMillis;

     private Point    mousePoint;

     private boolean  shooting;

     private long     score;

     private long     lastMoveTimeMillis;

     private int      state;

     private long     boomTimeMillis;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////


     public  DodgerSprite (
       Icon       greenIcon,
       Icon       yellowIcon,
       Icon       redIcon,
       Icon       bangIcon,
       Icon       boomIcon,
       AudioClip  bangAudioClip,
       Rectangle  bounds )
     //////////////////////////////////////////////////////////////////////
     {
       super ( 0.0, 0.0, 0.0, 0.0, 0.0, null, greenIcon );

       NullArgumentException.check ( this.greenIcon     = greenIcon     );

       NullArgumentException.check ( this.yellowIcon    = yellowIcon    );

       NullArgumentException.check ( this.redIcon       = redIcon       );

       NullArgumentException.check ( this.bangIcon      = bangIcon      );

       NullArgumentException.check ( this.boomIcon      = boomIcon      );

       NullArgumentException.check ( this.bangAudioClip = bangAudioClip );

       NullArgumentException.check ( this.bounds        = bounds        );

       dodgerWidth = greenIcon.getIconWidth ( );

       dodgerHalfWidth  = dodgerWidth / 2;

       dodgerHalfHeight = greenIcon.getIconHeight ( ) / 2;

       dodgerVerticalOffset = 3 * dodgerHalfHeight;

       shootArea = new Rectangle2D.Double ( );

       paintBounds = new Rectangle ( );

       setX ( Double.POSITIVE_INFINITY );

       rechargePeriodMillis = INIT_RECHARGE_PERIOD_MILLIS;
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public long  getScore ( ) { return score; }

     public boolean  isShooting ( ) { return shooting; }

     public Rectangle2D  getShootArea ( ) { return shootArea; }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  resetScore ( )
     //////////////////////////////////////////////////////////////////////
     {
       score = 0;
     }

     public void  setHit ( )
     //////////////////////////////////////////////////////////////////////
     {
       rechargePeriodMillis = INIT_RECHARGE_PERIOD_MILLIS;

       score = 0;

       setIcon ( boomIcon );

       state = STATE_BOOM;

       boomTimeMillis = System.currentTimeMillis ( );
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       long  updateTimeMillis = System.currentTimeMillis ( );

       if ( updateTimeMillis >= lastMoveTimeMillis + IDLE_TIMEOUT_MILLIS )
       {
         setX ( Integer.MAX_VALUE );

         return;
       }

       score++;

       shooting = false;

       if ( bang
         && ( updateTimeMillis >= bangTimeMillis + rechargePeriodMillis ) )
       {
         shooting = true;

         rechargePeriodMillis = 2 * rechargePeriodMillis;

         shootArea.setRect ( getX ( ), 0, dodgerWidth, getY ( ) );

         bangTimeMillis = updateTimeMillis;

         bangAudioClip.play ( );

         if ( state != STATE_BOOM )
         {
           state = STATE_BANG;

           setIcon ( bangIcon );
         }
       }
       else if ( state == STATE_BOOM )
       {
         if ( updateTimeMillis >= boomTimeMillis + RECOVERY_PERIOD_MILLIS )
         {
           setIcon ( greenIcon );

           state = STATE_GREEN;
         }
       }
       else if ( ( state == STATE_BANG   )
              || ( state == STATE_RED    )
              || ( state == STATE_YELLOW ) )
       {
         if ( updateTimeMillis >= bangTimeMillis + rechargePeriodMillis )
         {
           setIcon ( greenIcon );

           state = STATE_GREEN;
         }
         else if (
           updateTimeMillis >= bangTimeMillis + rechargePeriodMillis / 2 )
         {
           setIcon ( yellowIcon );

           state = STATE_YELLOW;
         }
         else if (
           updateTimeMillis >= bangTimeMillis + BANG_PERIOD_MILLIS )
         {
           setIcon ( redIcon );

           state = STATE_RED;
         }
       }

       double  x = getX ( );

       double  velocity = getVelocity ( );

       Point  mousePoint = this.mousePoint;

       if ( mousePoint != null )
       {
         double  delta = mousePoint.x - dodgerHalfWidth - x;

         if ( delta > 0 )
         {
           if ( velocity > delta / 2 )
           {
             velocity = delta / 2;
           }
           else if ( velocity < 0 )
           {
             velocity = velocity / 2 + 1;
           }
           else
           {
             velocity++;
           }
         }
         else if ( delta < 0 )
         {
           if ( velocity < delta / 2 )
           {
             velocity = delta / 2;
           }
           else if ( velocity > 0 )
           {
             velocity = velocity / 2 - 1;
           }
           else
           {
             velocity--;
           }
         }
         else
         {
           velocity = 0;
         }

         x += velocity;

         x = x < -dodgerHalfWidth ? -dodgerHalfWidth : x;

         x = x > bounds.width - dodgerHalfWidth
           ? bounds.width - dodgerHalfWidth : x;

         setX ( x );

         setY ( bounds.height - dodgerVerticalOffset );

         setVelocity ( velocity );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

/*
     public void  paint (
       JComponent  component,
       Graphics2D  graphics,
       int         offsetX,
       int         offsetY )
     //////////////////////////////////////////////////////////////////////
     {
       super.paint ( component, graphics, offsetX, offsetY );

       if ( shielded )
       {
         graphics.setColor ( Color.BLACK );

         getPaintBounds ( paintBounds );

         graphics.drawRect (
           paintBounds.x,
           paintBounds.y,
           paintBounds.width,
           paintBounds.height );
       }
     }
*/

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

       lastMoveTimeMillis = System.currentTimeMillis ( );
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

       lastMoveTimeMillis = System.currentTimeMillis ( );
     }

     public void  mousePressed ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
       bang = true;

       lastMoveTimeMillis = System.currentTimeMillis ( );
     }

     public void  mouseReleased ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
       bang = false;

       lastMoveTimeMillis = System.currentTimeMillis ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  keyPressed ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
       bang = true;

       lastMoveTimeMillis = System.currentTimeMillis ( );
     }
     
     public void  keyReleased ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
       bang = false;

       lastMoveTimeMillis = System.currentTimeMillis ( );
     }
     
     public void  keyTyped ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }
     
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
