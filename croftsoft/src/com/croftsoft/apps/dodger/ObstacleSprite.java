     package com.croftsoft.apps.dodger;

     import java.applet.*;
     import java.awt.*;
     import java.awt.event.*;
     import java.awt.geom.*;
     import java.util.Random;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.animation.*;
     import com.croftsoft.core.animation.sprite.*;

     /*********************************************************************
     * An IconSprite subclass representing a Dodger obstacle.
     *
     * @version
     *   2002-03-23
     * @since
     *   2002-03-18
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  ObstacleSprite
       extends AbstractSprite
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final int   OBSTACLE_WIDTH        = 32;

     private static final int   OBSTACLE_HEIGHT       = 32;

     private static final long  EXPLODE_PERIOD_MILLIS = 1000;

     //

     private final AudioClip  explodeAudioClip;

     private final Random     random;

     private final Rectangle  bounds;

     private final int        pixelsPerFrame;

     private final Rectangle  paintBounds;

     //

     private Color    color;

     private long     explodeTimeMillis;

     private boolean  hit;

     private boolean  exploding;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////


     public  ObstacleSprite (
       AudioClip  explodeAudioClip,
       Random     random,
       Rectangle  bounds,
       int        pixelsPerFrame )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.explodeAudioClip = explodeAudioClip );

       NullArgumentException.check ( this.random = random );

       NullArgumentException.check ( this.bounds = bounds );

       this.pixelsPerFrame = pixelsPerFrame;

       paintBounds = new Rectangle (
         0, 0, OBSTACLE_WIDTH, OBSTACLE_HEIGHT );       

       setY ( Double.POSITIVE_INFINITY );

       color = new Color (
         random.nextInt ( 256 ),
         random.nextInt ( 256 ),
         random.nextInt ( 256 ) );
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

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setX ( double  x )
     //////////////////////////////////////////////////////////////////////
     {
       super.setX ( x );

       paintBounds.x = ( int ) Math.round ( x );
     }

     public void  setY ( double  y )
     //////////////////////////////////////////////////////////////////////
     {
       super.setY ( y );

       paintBounds.y = ( int ) Math.round ( y );
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

       setX ( -OBSTACLE_WIDTH
         + random.nextInt ( bounds.width + OBSTACLE_WIDTH ) );

       setY ( -random.nextInt ( bounds.height ) - OBSTACLE_HEIGHT );

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

       double  y = getY ( );

       y += pixelsPerFrame;

       if ( y >= bounds.height )
       {
         reset ( );
       }
       else
       {
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
         OBSTACLE_WIDTH,
         OBSTACLE_HEIGHT );

       graphics.setColor ( Color.BLACK );

       graphics.drawRect (
         paintBounds.x,
         paintBounds.y,
         OBSTACLE_WIDTH,
         OBSTACLE_HEIGHT );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
