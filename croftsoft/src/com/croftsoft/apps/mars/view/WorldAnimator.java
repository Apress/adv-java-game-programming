     package com.croftsoft.apps.mars.view;

     import java.awt.*;
     import java.util.*;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.animation.painter.ColorPainter;
     import com.croftsoft.core.awt.image.ImageCache;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.media.sound.AudioClipCache;

     import com.croftsoft.apps.mars.model.AmmoDumpAccessor;
     import com.croftsoft.apps.mars.model.BulletAccessor;
     import com.croftsoft.apps.mars.model.ModelAccessor;
     import com.croftsoft.apps.mars.model.ObstacleAccessor;
     import com.croftsoft.apps.mars.model.TankAccessor;
     import com.croftsoft.apps.mars.model.WorldAccessor;

     /*********************************************************************
     * ComponentAnimator that maps Models to views.
     *
     * @version
     *   2003-04-17
     * @since
     *   2003-04-14
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  WorldAnimator
       implements ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final Color  COLOR_BULLET   = Color.MAGENTA;

     private static final Color  COLOR_OBSTACLE = Color.BLACK;

     //

     private final AudioClipCache  audioClipCache;

     private final ImageCache      imageCache;

     private final Map             componentAnimatorMap;

     private final WorldAccessor   worldAccessor;

     //

     private ModelAccessor [ ]  modelAccessors;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  WorldAnimator (
       WorldAccessor   worldAccessor,
       AudioClipCache  audioClipCache,
       ImageCache      imageCache )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.worldAccessor  = worldAccessor );

       NullArgumentException.check ( this.audioClipCache = audioClipCache);

       NullArgumentException.check ( this.imageCache     = imageCache    );

       componentAnimatorMap = new HashMap ( );

       modelAccessors = new ModelAccessor [ 0 ];
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       modelAccessors = worldAccessor.getModelAccessors ( modelAccessors );       

       for ( int  i = 0; i < modelAccessors.length; i++ )
       {
         ModelAccessor  modelAccessor = modelAccessors [ i ];

         if ( modelAccessor == null )
         {
           break;
         }

         ComponentAnimator  componentAnimator
           = getComponentAnimator ( modelAccessor );

         componentAnimator.update ( component );
       }
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < modelAccessors.length; i++ )
       {
         ModelAccessor  modelAccessor = modelAccessors [ i ];

         if ( modelAccessor == null )
         {
           break;
         }

         ComponentAnimator  componentAnimator
           = getComponentAnimator ( modelAccessors [ i ] );

         componentAnimator.paint ( component, graphics );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private ComponentAnimator  getComponentAnimator (
       ModelAccessor  modelAccessor )
     //////////////////////////////////////////////////////////////////////
     {
       ComponentAnimator  componentAnimator = ( ComponentAnimator )
         componentAnimatorMap.get ( modelAccessor );

       if ( componentAnimator == null )
       {
         if ( modelAccessor instanceof AmmoDumpAccessor )
         {
           componentAnimator = new AmmoDumpAnimator (
             ( AmmoDumpAccessor ) modelAccessor, audioClipCache );
         }
         else if ( modelAccessor instanceof BulletAccessor )
         {
           componentAnimator
             = new ModelAnimator ( modelAccessor, COLOR_BULLET );
         }
         else if ( modelAccessor instanceof ObstacleAccessor )
         {
           componentAnimator
             = new ModelAnimator ( modelAccessor, COLOR_OBSTACLE );
         }
         else if ( modelAccessor instanceof TankAccessor )
         {
           componentAnimator = new TankAnimator (
             ( TankAccessor ) modelAccessor, imageCache, audioClipCache );
         }
         else
         {
           componentAnimator = new ModelAnimator ( modelAccessor );
         }

         componentAnimatorMap.put ( modelAccessor, componentAnimator );
       }

       return componentAnimator;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
