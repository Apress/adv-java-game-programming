     package com.croftsoft.apps.mars.view;

     import java.applet.*;
     import java.awt.*;
     import java.awt.geom.*;
     import java.awt.image.*;
     import java.io.*;
     import java.net.*;
     import javax.swing.*;

     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.awt.image.ImageCache;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.geom.Point2DD;
     import com.croftsoft.core.math.geom.PointXY;
     import com.croftsoft.core.math.geom.ShapeLib;
     import com.croftsoft.core.media.sound.AudioClipCache;

     import com.croftsoft.apps.mars.model.TankAccessor;

     /*********************************************************************
     * The tank view.
     *
     * @version
     *   2003-07-17
     * @since
     *   2003-01-23
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  TankAnimator
       extends ModelAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  TANK_BODY_IMAGE_FILENAME
       = "tank_body.png";

     private static final String  TANK_TREAD_IMAGE_FILENAME_PREFIX
       = "tank_tread_";

     private static final String  TANK_TREAD_IMAGE_FILENAME_POSTFIX
       = ".png";

     private static final String  BANG_AUDIO_FILENAME
       = "bang.wav";

     private static final String  DRY_FIRE_AUDIO_FILENAME
       = "dry_fire.wav";

     private static final int  TREAD_LENGTH = 5;

     private static final String  TANK_TURRET_IMAGE_FILENAME
       = "tank_turret.png";

     private static final String [ ]  TANK_TREAD_IMAGE_FILENAMES
       = createTankTreadImageFilenames ( );

     //

     private final AudioClipCache   audioClipCache;

     private final ImageCache       imageCache;

     private final AffineTransform  affineTransform;

     private final Point2DD         newCenter;

     private final Point2DD         oldCenter;

     //

     private final double  TANK_RADIUS;

     private final int     TANK_WIDTH;

     private final int     TANK_HEIGHT;

//   private final int     REPAINT_SIZE;

     //

     private double   oldBodyHeading;

     private double   oldTurretHeading;

     private boolean  oldSparking;

     private int      treadOffsetLeft;

     private int      treadOffsetRight;

     private boolean  viewHasChanged;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *********************************************************************/
     public  TankAnimator (
       TankAccessor    tankAccessor,
       ImageCache      imageCache,
       AudioClipCache  audioClipCache )
     //////////////////////////////////////////////////////////////////////
     {
       super ( tankAccessor );

       NullArgumentException.check ( this.imageCache = imageCache );

       NullArgumentException.check (
         this.audioClipCache = audioClipCache );

       affineTransform = new AffineTransform ( );

       newCenter = new Point2DD ( );

       oldCenter = new Point2DD ( );

       TANK_RADIUS  = tankAccessor.getRadius ( );

       TANK_WIDTH   = 2 * ( int ) TANK_RADIUS;

       TANK_HEIGHT  = 2 * ( int ) TANK_RADIUS;

//     REPAINT_SIZE = 2 * ( int ) TANK_RADIUS;
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       super.update ( component );

       TankAccessor  tankAccessor = ( TankAccessor ) modelAccessor;

       if ( tankAccessor.isFiring ( ) )
       {
         audioClipCache.play ( BANG_AUDIO_FILENAME );
       }
       else if ( tankAccessor.isDryFiring ( ) )
       {
         audioClipCache.play ( DRY_FIRE_AUDIO_FILENAME );
       }

       boolean  treadsMoving = true;

       ShapeLib.getCenter ( tankAccessor.getShape ( ), newCenter );

       double  bodyHeading = tankAccessor.getBodyHeading ( );

       if ( Math.abs ( oldBodyHeading - bodyHeading )
         < 2.0 * Math.PI / 1000.0 )
       {
         if ( newCenter.distanceXY ( oldCenter ) < 0.25 )
         {
           treadsMoving = false;
         }
         else
         {
           treadOffsetLeft  = ( treadOffsetLeft  + 1 ) % TREAD_LENGTH;

           treadOffsetRight = ( treadOffsetRight + 1 ) % TREAD_LENGTH;
         }
       }
       else
       {
// use accessor state instead

         if ( turningRight ( oldBodyHeading, bodyHeading ) )
         {
           treadOffsetLeft  = ( treadOffsetLeft + 1 ) % TREAD_LENGTH;

           treadOffsetRight = ( treadOffsetRight + TREAD_LENGTH - 1 )
             % TREAD_LENGTH;
         }
         else
         {
           treadOffsetLeft = ( treadOffsetLeft + TREAD_LENGTH - 1 )
             % TREAD_LENGTH;
 
           treadOffsetRight  = ( treadOffsetRight  + 1 ) % TREAD_LENGTH;
         }

         oldBodyHeading = bodyHeading;
       }

       oldCenter.setXY ( newCenter );

       double  turretHeading = tankAccessor.getTurretHeading ( );

       boolean  sparking = tankAccessor.isSparking ( );

// use the update flag instead

       viewHasChanged
         = treadsMoving
         || ( turretHeading != oldTurretHeading )
         || ( sparking      != oldSparking      );

       oldTurretHeading = turretHeading;

       oldSparking      = sparking;
     }

     protected void  getRepaintRectangle ( Rectangle  repaintRectangle )
     //////////////////////////////////////////////////////////////////////
     {
       ShapeLib.getCenter ( modelAccessor.getShape ( ), newCenter );

       double  radius = 1.5 * TANK_RADIUS;

       int  diameter = ( int ) ( 2.0 * radius ) + 1;

       repaintRectangle.setBounds (
         ( int ) ( newCenter.x - radius ) - 2,
         ( int ) ( newCenter.y - radius ) - 2,
         diameter + 1,
         diameter + 1 );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !modelAccessor.isActive ( ) )
       {
         return;
       }

       TankAccessor  tankAccessor = ( TankAccessor ) modelAccessor;

       ShapeLib.getCenter ( tankAccessor.getShape ( ), newCenter );

       double  x = newCenter.x;

       double  y = newCenter.y;

       double  adjustedHeading = tankAccessor.getBodyHeading ( );

       affineTransform.setToTranslation ( x, y );

       affineTransform.rotate ( adjustedHeading );

       affineTransform.translate (
         -TANK_WIDTH / 2, -TANK_HEIGHT / 2 );

       try
       {
         for ( int  i = 0; i < TANK_WIDTH / TREAD_LENGTH; i++ )
         {
           Image  tankTreadImage = imageCache.get (
             TANK_TREAD_IMAGE_FILENAMES [ treadOffsetLeft ] );
         
           graphics.drawImage ( tankTreadImage, affineTransform, null );

           affineTransform.translate ( TREAD_LENGTH, 0 );
         }

         affineTransform.setToTranslation ( x, y );

         affineTransform.rotate ( adjustedHeading );

         affineTransform.translate (
           -TANK_WIDTH / 2, -TANK_HEIGHT / 2 + 40 );

         for ( int  i = 0; i < TANK_WIDTH / TREAD_LENGTH; i++ )
         {
           Image  tankTreadImage = imageCache.get (
             TANK_TREAD_IMAGE_FILENAMES [ treadOffsetRight ] );
         
           graphics.drawImage ( tankTreadImage, affineTransform, null );

           affineTransform.translate ( TREAD_LENGTH, 0 );
         }

         affineTransform.setToTranslation ( x, y );

         affineTransform.rotate ( adjustedHeading );

         affineTransform.translate (
           -TANK_WIDTH / 2, -TANK_HEIGHT / 2 + 10 );

         Image  tankBodyImage
           = imageCache.get ( TANK_BODY_IMAGE_FILENAME );

         graphics.drawImage ( tankBodyImage, affineTransform, null );

         affineTransform.setToTranslation ( x, y );

         affineTransform.rotate ( tankAccessor.getTurretHeading ( ) );

         affineTransform.translate ( -TANK_WIDTH / 2, -TANK_HEIGHT / 2 );

         Image  tankTurretImage
           = imageCache.get ( TANK_TURRET_IMAGE_FILENAME );

         graphics.drawImage ( tankTurretImage, affineTransform, null );
       }
       catch ( IOException  ex )
       {
         ex.printStackTrace ( );
       }

       graphics.setColor ( tankAccessor.getColor ( ) );

       graphics.drawOval ( ( int ) x - 2, ( int ) y - 2, 4, 4 );

       if ( tankAccessor.isSparking ( ) )
       {
         graphics.setColor ( Color.RED );

         graphics.fillRect ( ( int ) x - 5, ( int ) y - 5, 10, 10 );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private static String [ ]  createTankTreadImageFilenames ( )
     //////////////////////////////////////////////////////////////////////
     {
       String [ ]  tankTreadImageFilenames = new String [ TREAD_LENGTH ];

       for ( int  i = 0; i < TREAD_LENGTH; i++ )
       {
         tankTreadImageFilenames [ i ]
           = TANK_TREAD_IMAGE_FILENAME_PREFIX
           + i
           + TANK_TREAD_IMAGE_FILENAME_POSTFIX;
       }

       return tankTreadImageFilenames;
     }

     private static boolean  turningRight (
       double  oldHeading,
       double  newHeading )
     //////////////////////////////////////////////////////////////////////
     {
       if ( oldHeading > newHeading )
       {
         return oldHeading - newHeading > Math.PI;
       }
       else
       {
         return newHeading - oldHeading < Math.PI;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
