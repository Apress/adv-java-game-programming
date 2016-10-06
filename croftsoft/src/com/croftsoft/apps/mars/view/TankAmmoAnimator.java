     package com.croftsoft.apps.mars.view;

     import java.awt.*;
     import java.io.*;
     import javax.swing.*;

     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.animation.animator.IconRowAnimator;
     import com.croftsoft.core.awt.image.ImageCache;
     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.apps.mars.model.TankAccessor;

     /*********************************************************************
     * Display the amount of ammo left in the tank.
     *
     * @version
     *   2003-07-17
     * @since
     *   2003-03-28
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  TankAmmoAnimator
       implements ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  BULLET_IMAGE_FILENAME = "bullet.png";

     //

     private final IconRowAnimator  iconRowAnimator;

     //

     private TankAccessor  tankAccessor;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *********************************************************************/
     public  TankAmmoAnimator (
       TankAccessor  tankAccessor,
       ImageCache    imageCache,
       JComponent    component )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       setTankAccessor ( tankAccessor );

       iconRowAnimator = new IconRowAnimator (
         new ImageIcon ( imageCache.get ( BULLET_IMAGE_FILENAME ) ),
         tankAccessor.getAmmo ( ),
         component );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setTankAccessor ( TankAccessor  tankAccessor )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.tankAccessor = tankAccessor );
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       iconRowAnimator.setCount ( tankAccessor.getAmmo ( ) );

       iconRowAnimator.update ( component );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       iconRowAnimator.paint ( component, graphics );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }