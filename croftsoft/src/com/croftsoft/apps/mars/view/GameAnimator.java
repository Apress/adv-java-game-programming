     package com.croftsoft.apps.mars.view;

     import java.awt.*;
     import java.io.*;
     import java.util.*;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.animation.painter.ColorPainter;
     import com.croftsoft.core.awt.image.ImageCache;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.media.sound.AudioClipCache;

     import com.croftsoft.apps.mars.model.GameAccessor;
     import com.croftsoft.apps.mars.model.TankAccessor;
     import com.croftsoft.apps.mars.model.WorldAccessor;

     /*********************************************************************
     * ComponentAnimator that maps models to views.
     *
     * @version
     *   2003-07-17
     * @since
     *   2003-04-01
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  GameAnimator
       implements ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final GameAccessor    gameAccessor;

     private final AudioClipCache  audioClipCache;

     private final ColorPainter    backgroundColorPainter;

     private final ImageCache      imageCache;

     //

     private int               oldLevel;

     private PathAnimator      pathAnimator;

     private TankAmmoAnimator  tankAmmoAnimator;

     private WorldAnimator     worldAnimator;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  GameAnimator (
       GameAccessor  gameAccessor,
       JComponent    component,
       ClassLoader   classLoader,
       String        mediaDir,
       Color         backgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.gameAccessor = gameAccessor );

       NullArgumentException.check ( component );

       audioClipCache = new AudioClipCache ( classLoader, mediaDir );

       imageCache = new ImageCache (
         Transparency.BITMASK,
         component,
         classLoader,
         mediaDir );

       backgroundColorPainter = new ColorPainter ( backgroundColor );

       updateAnimators ( component );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public AudioClipCache  getAudioClipCache ( )
     //////////////////////////////////////////////////////////////////////
     {
       return audioClipCache;
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       int  level = gameAccessor.getLevel ( );

       if ( level != oldLevel )
       {
         oldLevel = level;

         updateAnimators ( component );

         component.repaint ( );
       }

       worldAnimator.update ( component );

       if ( pathAnimator != null )
       {
         pathAnimator.update ( component );
       }

       if ( tankAmmoAnimator != null )
       {
         tankAmmoAnimator.update ( component );
       }
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       backgroundColorPainter.paint ( component, graphics );

       worldAnimator         .paint ( component, graphics );

       if ( pathAnimator != null )
       {
         pathAnimator        .paint ( component, graphics );
       }

       if ( tankAmmoAnimator != null )
       {
         tankAmmoAnimator    .paint ( component, graphics );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  togglePathAnimator ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( pathAnimator != null )
       {
         pathAnimator.toggle ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private void  updateAnimators ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       // Caches not cleared since images and audio same for each level.

       // imageCache.clear ( );

       // audioClipCache.clear ( );

       WorldAccessor  worldAccessor = gameAccessor.getWorldAccessor ( );

       worldAnimator = new WorldAnimator (
         worldAccessor, audioClipCache, imageCache );

       TankAccessor  playerTankAccessor
         = gameAccessor.getPlayerTankAccessor ( );

       if ( playerTankAccessor != null )
       {
         pathAnimator = new PathAnimator (
           gameAccessor, playerTankAccessor.getRadius ( ) );

         try
         {
           tankAmmoAnimator = new TankAmmoAnimator (
             playerTankAccessor, imageCache, component );
         }
         catch ( IOException  ex )
         {
           ex.printStackTrace ( );
         }
       }
       else
       {
         pathAnimator     = null;

         tankAmmoAnimator = null;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }