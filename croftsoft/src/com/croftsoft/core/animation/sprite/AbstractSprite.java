     package com.croftsoft.core.animation.sprite;

     import java.awt.Graphics2D;
     import java.awt.Rectangle;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.ComponentPainter;
     import com.croftsoft.core.animation.ComponentUpdater;
     import com.croftsoft.core.animation.Sprite;
     import com.croftsoft.core.animation.painter.NullComponentPainter;
     import com.croftsoft.core.animation.updater.NullComponentUpdater;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * An abstract Sprite implementation.
     *
     * @version
     *   2003-07-12
     * @since
     *   2002-03-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public abstract class  AbstractSprite
       implements Sprite
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected double            x;

     protected double            y;

     protected double            z;

     protected double            heading;

     protected double            velocity;

     protected ComponentUpdater  componentUpdater;

     protected ComponentPainter  componentPainter;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  AbstractSprite (
       double            x,
       double            y,
       double            z,
       double            heading,
       double            velocity,
       ComponentUpdater  componentUpdater,
       ComponentPainter  componentPainter )
     //////////////////////////////////////////////////////////////////////
     {
       this.x = x;

       this.y = y;

       this.z = z;

       this.heading  = heading;

       this.velocity = velocity;

       setComponentUpdater ( componentUpdater );

       setComponentPainter ( componentPainter );
     }

     public  AbstractSprite ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( 0.0, 0.0, 0.0, 0.0, 0.0, null, null );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public double  getX        ( ) { return x;        }

     public double  getY        ( ) { return y;        }

     public double  getZ        ( ) { return z;        }

     public double  getHeading  ( ) { return heading;  }

     public double  getVelocity ( ) { return velocity; }

     public void  getCollisionBounds ( Rectangle  collisionBounds )
     //////////////////////////////////////////////////////////////////////
     {
       collisionBounds.setBounds ( getCollisionShape ( ).getBounds ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setX ( double  x ) { this.x = x; }

     public void  setY ( double  y ) { this.y = y; }

     public void  setZ ( double  z ) { this.z = z; }

     public void  setHeading ( double  heading )
     //////////////////////////////////////////////////////////////////////
     {
       this.heading = heading;
     }

     public void  setVelocity ( double  velocity )
     //////////////////////////////////////////////////////////////////////
     {
       this.velocity = velocity;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setComponentUpdater (
       ComponentUpdater  componentUpdater )
     //////////////////////////////////////////////////////////////////////
     {
       if ( componentUpdater == null )
       {
         componentUpdater = NullComponentUpdater.INSTANCE;
       }

       this.componentUpdater = componentUpdater;
     }

     public void  setComponentPainter (
       ComponentPainter  componentPainter )
     //////////////////////////////////////////////////////////////////////
     {
       if ( componentPainter == null )
       {
         componentPainter = NullComponentPainter.INSTANCE;
       }

       this.componentPainter = componentPainter;
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       componentUpdater.update ( component );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       componentPainter.paint ( component, graphics );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
