     package com.croftsoft.apps.zombie;

     import java.awt.Component;
     import java.awt.Graphics;
     import java.awt.Graphics2D;
     import java.awt.Rectangle;
     import java.util.Random;
     import javax.swing.Icon;
     import javax.swing.JComponent;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.animation.ComponentUpdater;
     import com.croftsoft.core.animation.sprite.IconSprite;
     import com.croftsoft.core.animation.updater.NullComponentUpdater;

     /*********************************************************************
     * An IconSprite subclass representing a zombie.
     *
     * @version
     *   2003-07-11
     * @since
     *   2002-03-15
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  ZombieSprite
       extends IconSprite
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final ZombieReanimator  zombieReanimator;

     private final Rectangle         repaintBounds;

     //

     private boolean  hit;

     private boolean  destroyed;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////


     public  ZombieSprite (
       double            x,
       double            y,
       double            z,
       double            heading,
       double            velocity,
       Icon              icon,
       ZombieReanimator  zombieReanimator )
     //////////////////////////////////////////////////////////////////////
     {
       super ( x, y, z, heading, velocity,
         NullComponentUpdater.INSTANCE, icon );

       NullArgumentException.check (
         this.zombieReanimator = zombieReanimator );

       repaintBounds = new Rectangle ( );
     }

     public  ZombieSprite (
       Icon              icon,
       ZombieReanimator  zombieReanimator )
     //////////////////////////////////////////////////////////////////////
     {
       this ( 0.0, 0.0, 0.0, 0.0, 0.0, icon, zombieReanimator );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public boolean  isDestroyed ( ) { return destroyed; }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setHit ( )
     //////////////////////////////////////////////////////////////////////
     {
       hit = true;
     }

     public void  reset ( )
     //////////////////////////////////////////////////////////////////////
     {
       Random  random = zombieReanimator.getRandom ( );

       setX ( random.nextInt (
         zombieReanimator.getComponentWidth  ( ) + 1 ) );

       setY ( random.nextInt (
         zombieReanimator.getComponentHeight ( ) + 1 ) );

       setHeading ( random.nextDouble ( ) * 2.0 * Math.PI );

       setVelocity ( 50.0 + 250.0 * random.nextDouble ( ) );

       hit       = false;

       destroyed = false;
     }

/*
     //////////////////////////////////////////////////////////////////////
     // interface Sprite methods
     //////////////////////////////////////////////////////////////////////

     public boolean  contains (
       double  x,
       double  y )
     //////////////////////////////////////////////////////////////////////
     {
       if ( destroyed )
       {
         return false;
       }

       return super.contains ( x, y );
     }
*/
         
     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       if ( hit )
       {
         getPaintBounds ( repaintBounds );

         setX ( Integer.MIN_VALUE );

         component.repaint ( repaintBounds );

         destroyed = true;
       }
       else if ( !destroyed )
       {
         super.update ( component );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
