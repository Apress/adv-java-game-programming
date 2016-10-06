     package com.croftsoft.apps.shooter;

     import java.applet.*;
     import java.awt.*;
     import java.awt.event.*;
     import java.awt.geom.*;
     import java.util.Random;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.animation.*;
     import com.croftsoft.core.animation.painter.NullComponentPainter;
     import com.croftsoft.core.animation.sprite.*;
     import com.croftsoft.core.animation.updater.NullComponentUpdater;

     /*********************************************************************
     * An IconSprite subclass representing a Shooter attacker.
     *
     * @version
     *   2003-07-11
     * @since
     *   2002-03-23
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  AttackerSprite
       extends AbstractSprite
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final int   ATTACKER_WIDTH        = 32;

     private static final int   ATTACKER_HEIGHT       = 32;

     private static final int   ATTACKER_HALF_WIDTH  = ATTACKER_WIDTH  / 2;

     private static final int   ATTACKER_HALF_HEIGHT = ATTACKER_HEIGHT / 2;

     private static final long  EXPLODE_PERIOD_MILLIS = 1000;

     //

     private final AudioClip  explodeAudioClip;

     private final Random     random;

     private final Rectangle  paintBounds;

     //

     private Color    color;

     private long     explodeTimeMillis;

     private boolean  hit;

     private boolean  exploding;

     private int      componentHeight;

     private int      componentWidth;

     private double   averageVelocity;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////


     public  AttackerSprite (
       AudioClip  explodeAudioClip,
       Random     random,
       double     averageVelocity )
     //////////////////////////////////////////////////////////////////////
     {
       super ( 0.0, 0.0, 0.0, 0.0, 0.0,
         NullComponentUpdater.INSTANCE,
         NullComponentPainter.INSTANCE );

       NullArgumentException.check (
         this.explodeAudioClip = explodeAudioClip );

       NullArgumentException.check ( this.random = random );

       paintBounds = new Rectangle (
         0, 0, ATTACKER_WIDTH, ATTACKER_HEIGHT );       

       setY ( Double.POSITIVE_INFINITY );

       color = new Color (
         random.nextInt ( 256 ),
         random.nextInt ( 256 ),
         random.nextInt ( 256 ) );

       this.averageVelocity = averageVelocity;
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public void  getPaintBounds ( Rectangle  paintBounds )
     //////////////////////////////////////////////////////////////////////
     {
       paintBounds.setBounds ( this.paintBounds );
     }

     public Shape  getCollisionShape ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( exploding )
       {
         return null;
       }

       return paintBounds;
     }

     public boolean  intersectsLine (
       double  x1,
       double  y1,
       double  x2,
       double  y2 )
     //////////////////////////////////////////////////////////////////////
     {
       return paintBounds.intersectsLine ( x1, y1, x2, y2 );
     }

     public boolean  isHit ( ) { return hit; }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setX ( double  x )
     //////////////////////////////////////////////////////////////////////
     {
       super.setX ( x );

       paintBounds.x = ( int ) Math.round ( x ) - ATTACKER_HALF_HEIGHT;
     }

     public void  setY ( double  y )
     //////////////////////////////////////////////////////////////////////
     {
       super.setY ( y );

       paintBounds.y = ( int ) Math.round ( y ) - ATTACKER_HALF_WIDTH;
     }

     public void  setHit ( )
     //////////////////////////////////////////////////////////////////////
     {
       hit = true;
     }

     public void  reset ( )
     //////////////////////////////////////////////////////////////////////
     {
       hit = false;

       exploding = false;

       double  centerX = componentWidth  / 2.0;

       double  centerY = componentHeight / 2.0;

       double  radius = centerX > centerY ? centerX : centerY;

       double  angle = 2.0 * Math.PI * random.nextDouble ( );

       double  x = centerX + radius * Math.cos ( angle );

       double  y = centerY + radius * Math.sin ( angle );

       setX ( x );

       setY ( y );

       setHeading ( Math.atan2 ( ( centerY - y ), ( centerX - x ) ) );

       setVelocity ( ( 0.5 + random.nextDouble ( ) ) * averageVelocity );

       color = new Color (
         random.nextInt ( 256 ),
         random.nextInt ( 256 ),
         random.nextInt ( 256 ) );
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       long  updateTimeMillis = System.currentTimeMillis ( );

       double  x = getX ( );

       double  y = getY ( );

       double  velocity = getVelocity ( );

       double  heading  = getHeading  ( );

       x += ( velocity * Math.cos ( heading ) );

       y += ( velocity * Math.sin ( heading ) );

       componentWidth  = component.getWidth  ( );

       componentHeight = component.getHeight ( );

       if ( ( y >=  2.0 * componentHeight )
         || ( y <= -1.0 * componentHeight )
         || ( x >=  2.0 * componentWidth  )
         || ( x <= -1.0 * componentWidth  ) )
       {
         reset ( );
       }
       else
       {
         setX ( x );

         setY ( y );
       }

       if ( exploding )
       {
         if ( updateTimeMillis
           >= explodeTimeMillis + EXPLODE_PERIOD_MILLIS )
         {
           reset ( );
         }

         color = new Color (
           random.nextInt ( 256 ),
           random.nextInt ( 256 ),
           random.nextInt ( 256 ) );
       }
       else if ( hit )
       {
         explodeAudioClip.play ( );

         color = new Color (
           random.nextInt ( 256 ),
           random.nextInt ( 256 ),
           random.nextInt ( 256 ) );

         explodeTimeMillis = updateTimeMillis;
         
         exploding = true;
       }
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       graphics.setColor ( color );

       graphics.fillRect (
         paintBounds.x,
         paintBounds.y,
         ATTACKER_WIDTH,
         ATTACKER_HEIGHT );

       graphics.setColor ( Color.WHITE );

       graphics.drawRect (
         paintBounds.x,
         paintBounds.y,
         ATTACKER_WIDTH,
         ATTACKER_HEIGHT );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
