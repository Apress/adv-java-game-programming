     package com.croftsoft.core.animation.updater;

     import java.awt.Rectangle;
     import java.awt.Shape;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.Clock;
     import com.croftsoft.core.animation.ComponentUpdater;
     import com.croftsoft.core.animation.Sprite;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.MathConstants;

     /*********************************************************************
     * Bounces a Sprite off the walls of a Rectangle.
     *
     * @version
     *   2003-07-11
     * @since
     *   2002-02-15
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  BounceUpdater
       implements ComponentUpdater
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Sprite     sprite;

     private final Rectangle  bounds;

     private final Clock      clock;

     private final Rectangle  collisionBounds;

     private final Rectangle  newPaintBounds;

     private final Rectangle  oldPaintBounds;

     //

     private long  lastUpdateTimeNanos;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  BounceUpdater (
       Sprite     sprite,
       Rectangle  bounds,
       Clock      clock )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.sprite = sprite );

       NullArgumentException.check ( this.bounds = bounds );

       NullArgumentException.check ( this.clock  = clock  );

       collisionBounds = new Rectangle ( );

       newPaintBounds  = new Rectangle ( );

       oldPaintBounds  = new Rectangle ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       long  updateTimeNanos = clock.currentTimeNanos ( );

       if ( updateTimeNanos == lastUpdateTimeNanos )
       {
         return;
       }

       double  timeDelta
         = ( updateTimeNanos - lastUpdateTimeNanos )
         / ( double ) MathConstants.NANOSECONDS_PER_SECOND;

       lastUpdateTimeNanos = updateTimeNanos;

       double  x = sprite.getX ( );

       double  y = sprite.getY ( );

       double  heading  = sprite.getHeading  ( );

       double  velocity = sprite.getVelocity ( );

       double  delta_x = Math.cos ( heading ) * velocity * timeDelta;

       double  delta_y = Math.sin ( heading ) * velocity * timeDelta;

       x += delta_x;

       y += delta_y;

       int  minX = bounds.x;

       int  minY = bounds.y;

       sprite.getCollisionBounds ( collisionBounds );

       int  maxX = bounds.x + bounds.width  - collisionBounds.width;

       int  maxY = bounds.y + bounds.height - collisionBounds.height;

       boolean  headingAlreadyAdjusted = false;

       if ( x < minX )
       {
         x = minX;

         if ( delta_x < 0.0 )
         {
           if ( heading > Math.PI )
           {
             heading = 3.0 * Math.PI - heading;
           }
           else
           {
             heading = Math.PI - heading;
           }

           headingAlreadyAdjusted = true;
         }
       }
       else if ( x > maxX )
       {
         x = maxX;

         if ( delta_x > 0.0 )
         {
           if ( heading < Math.PI )
           {
             heading = Math.PI - heading;
           }
           else
           {
             heading = 3.0 * Math.PI - heading;
           }

           headingAlreadyAdjusted = true;
         }
       }

       if ( y < minY )
       {
         y = minY;

         if ( delta_y < 0.0 )
         {
           if ( !headingAlreadyAdjusted )
           {
             heading = 2.0 * Math.PI - heading;
           }
         }
       }
       else if ( y > maxY )
       {
         y = maxY;

         if ( delta_y > 0.0 )
         {
           if ( !headingAlreadyAdjusted )
           {
             heading = 2.0 * Math.PI - heading;
           }
         }
       }

       sprite.getPaintBounds ( oldPaintBounds );

       sprite.setX ( x );

       sprite.setY ( y );

       sprite.setHeading ( heading );

       sprite.getPaintBounds ( newPaintBounds );

       oldPaintBounds.add ( newPaintBounds );

       component.repaint ( oldPaintBounds );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
