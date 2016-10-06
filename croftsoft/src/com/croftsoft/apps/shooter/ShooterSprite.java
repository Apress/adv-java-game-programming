     package com.croftsoft.apps.shooter;

     import java.applet.*;
     import java.awt.*;
     import java.awt.event.*;
     import java.awt.geom.*;
     import java.util.Random;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.animation.*;
     import com.croftsoft.core.animation.painter.NullComponentPainter;
     import com.croftsoft.core.animation.sprite.*;
     import com.croftsoft.core.animation.updater.NullComponentUpdater;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.MathLib;

     /*********************************************************************
     * An IconSprite subclass representing the Attacker.
     *
     * @version
     *   2003-07-11
     * @since
     *   2002-03-23
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  ShooterSprite
       extends AbstractSprite
       implements MouseInputListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  IDLE_TIMEOUT_MILLIS    = 60 * 1000;

     private static final long  RECHARGE_PERIOD_MILLIS = 100;

     private static final long  BANG_PERIOD_MILLIS     = 300;

     private static final long  RECOVERY_PERIOD_MILLIS = 1000;

     //

     private static final int   STATE_REST = 0;

     private static final int   STATE_BANG = 1;

     private static final int   STATE_BOOM = 2;

     //

     private final Image            restImage;

     private final Image            bangImage;

     private final Image            boomImage;

     private final AudioClip        bangAudioClip;

     private final Rectangle        paintBounds;

     private final AffineTransform  affineTransform;

     private final Shooter          shooter;

     //

     private boolean  bang;

     private long     bangTimeMillis;

     private Point    mousePoint;

     private boolean  shooting;

     private long     lastMoveTimeMillis;

     private int      state;

     private long     boomTimeMillis;

     private Image    image;

     private Point2D  rayPoint2D;

     private long     score;

     private int      targetX;

     private int      targetY;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////


     public  ShooterSprite (
       Image      restImage,
       Image      bangImage,
       Image      boomImage,
       AudioClip  bangAudioClip,
       Shooter    shooter )
     //////////////////////////////////////////////////////////////////////
     {
       super ( 0.0, 0.0, 0.0, 0.0, 0.0,
         NullComponentUpdater.INSTANCE,
         NullComponentPainter.INSTANCE );

       NullArgumentException.check ( this.restImage     = restImage     );

       NullArgumentException.check ( this.bangImage     = bangImage     );

       NullArgumentException.check ( this.boomImage     = boomImage     );

       NullArgumentException.check ( this.bangAudioClip = bangAudioClip );

       NullArgumentException.check ( this.shooter       = shooter       );

       paintBounds = new Rectangle ( );

       affineTransform = new AffineTransform ( );

       image = restImage;

       rayPoint2D = new Point2D.Double ( );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public long  getScore ( ) { return score; }

     public Shape  getCollisionShape ( )
     //////////////////////////////////////////////////////////////////////
     {
       return paintBounds;
     }

     public void  getPaintBounds ( Rectangle  paintBounds )
     //////////////////////////////////////////////////////////////////////
     {
       paintBounds.setBounds ( this.paintBounds );
     }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setHit ( )
     //////////////////////////////////////////////////////////////////////
     {
       image = boomImage;

       state = STATE_BOOM;

       boomTimeMillis = System.currentTimeMillis ( );

       score = 0;
     }

     public void  resetScore ( )
     //////////////////////////////////////////////////////////////////////
     {
       score = 0;
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
         setX ( Integer.MAX_VALUE / 2 );

         paintBounds.x = Integer.MAX_VALUE / 2;

         score = 0;

         return;
       }

       shooting = false;

       if ( bang &&
         ( updateTimeMillis >= bangTimeMillis + RECHARGE_PERIOD_MILLIS ) )
       {
         shooting = true;

         bangTimeMillis = updateTimeMillis;

         bangAudioClip.play ( );

         if ( state != STATE_BOOM )
         {
           state = STATE_BANG;

           image = bangImage;
         }

         shoot ( component );
       }

       if ( state == STATE_BOOM )
       {
         if ( updateTimeMillis >= boomTimeMillis + RECOVERY_PERIOD_MILLIS )
         {
           image = restImage;

           state = STATE_REST;
         }
       }
       else if ( state == STATE_BANG )
       {
         if ( updateTimeMillis >= bangTimeMillis + RECHARGE_PERIOD_MILLIS )
         {
           image = restImage;

           state = STATE_REST;
         }
       }

       double  x = component.getWidth  ( ) / 2;

       double  y = component.getHeight ( ) / 2;

       setX ( x );

       setY ( y );

       paintBounds.setRect ( x - 16.0, y - 16.0, 32.0, 32.0 );

       Point  mousePoint = this.mousePoint;

       if ( mousePoint != null )
       {
         double  deltaX = mousePoint.x - x;

         double  deltaY = mousePoint.y - y;

         setHeading ( Math.atan2 ( deltaY, deltaX ) );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       double  x = getX ( );

       double  y = getY ( );

       if ( shooting )
       {
         graphics.setColor ( Color.RED );

         graphics.drawLine (
           ( int ) Math.round ( getX ( ) ),
           ( int ) Math.round ( getY ( ) ),
           targetX,
           targetY );
       }

       affineTransform.setToTranslation ( x, y );

       affineTransform.rotate ( getHeading ( ) + Math.PI / 2 );

       affineTransform.translate ( -16, -16 );

       graphics.drawImage ( image, affineTransform, null );
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

     private void  shoot ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       double  heading = getHeading ( );

       double  x1 = getX ( );

       double  y1 = getY ( );

       int  width  = component.getWidth  ( );

       int  height = component.getHeight ( );

       int  rayLength = width > height ? width : height;

       MathLib.toRectangular ( rayLength, heading, rayPoint2D );

       double  x2 = x1 + rayPoint2D.getX ( );

       double  y2 = y1 + rayPoint2D.getY ( );

       int  index = -1;

       AttackerSprite [ ]  attackerSprites
         = shooter.getAttackerSprites ( );

       for ( int  i = 0; i < attackerSprites.length; i++ )
       {
         AttackerSprite  attackerSprite = attackerSprites [ i ];

         if ( attackerSprite.intersectsLine ( x1, y1, x2, y2 ) )
         {
           index = i;

           if ( !attackerSprite.isHit ( ) )
           {
             attackerSprite.setHit ( );
   
             score++;
           }

           targetX = ( int ) Math.round ( attackerSprite.getX ( ) );

           targetY = ( int ) Math.round ( attackerSprite.getY ( ) );

           break;
         }
       }

       if ( index < 0 )
       {
         targetX = ( int ) Math.round ( x2 );

         targetY = ( int ) Math.round ( y2 );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
