     package com.croftsoft.apps.zombie;

     import java.applet.*;
     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import java.net.*;
     import java.util.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.animation.*;
     import com.croftsoft.core.animation.animator.*;
     import com.croftsoft.core.animation.clock.*;
     import com.croftsoft.core.animation.component.*;
     import com.croftsoft.core.animation.icon.*;
     import com.croftsoft.core.animation.painter.*;
     import com.croftsoft.core.animation.sprite.*;
     import com.croftsoft.core.animation.updater.*;
     import com.croftsoft.core.awt.image.ImageLib;
     import com.croftsoft.core.lang.ClassLib;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.*;

     /*********************************************************************
     * Animates the zombies.
     *
     * @version
     *   2003-07-17
     * @since
     *   2002-02-17
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ZombieReanimator
       implements Clock, ComponentAnimator, ZombieConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final AnimatedComponent  animatedComponent;

     private final Random             random;

     private final Rectangle          componentBounds;

     private final Map                iconMap;

     private final AudioClip          shotgunAudioClip;

     private final AudioClip          hitAudioClip;

     private final AudioClip          barkAudioClip;

     private final Clock              clock;

     private final ArrayComponentUpdater  arrayComponentUpdater;

     private final ArrayComponentPainter  arrayComponentPainter;

     //

     private boolean            initialized;

     private ZombieSprite [ ]   zombies;

     private boolean            resetZombies;

     private long               updateTimeNanos;

//   private ResourceImageIcon  backgroundResourceImageIcon;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ZombieReanimator ( )
     //////////////////////////////////////////////////////////////////////
     {
       iconMap = new HashMap ( );

       random  = new Random  ( );

       componentBounds = new Rectangle ( );

       //

       URL  audioURL = getClass ( ).getClassLoader ( ).getResource (
         SHOTGUN_AUDIO_FILENAME );

       shotgunAudioClip = Applet.newAudioClip ( audioURL );

       audioURL = getClass ( ).getClassLoader ( ).getResource (
         HIT_AUDIO_FILENAME );

       hitAudioClip = Applet.newAudioClip ( audioURL );

       audioURL = getClass ( ).getClassLoader ( ).getResource (
         BARK_AUDIO_FILENAME );

       barkAudioClip = Applet.newAudioClip ( audioURL );

       //

       animatedComponent
         = new AnimatedComponent ( this, FRAME_RATE );

       animatedComponent.repaint ( );

       animatedComponent.addComponentListener (
         new ComponentAdapter ( )
         {
           public void  componentResized ( ComponentEvent  e )
           {
             handleAnimatedComponentResize ( );
           }
         } );

       animatedComponent.setCursor (
         new Cursor ( Cursor.CROSSHAIR_CURSOR ) );

       animatedComponent.addMouseListener (
         new MouseAdapter ( )
         {
           public void  mousePressed ( MouseEvent  mouseEvent )
           {
             fire ( mouseEvent.getX ( ), mouseEvent.getY ( ) );
           }
         } );

       clock = new HiResClock ( );

       arrayComponentUpdater = new ArrayComponentUpdater ( );

       arrayComponentPainter = new ArrayComponentPainter ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public AnimatedComponent  getAnimatedComponent ( )
     //////////////////////////////////////////////////////////////////////
     {
       return animatedComponent;
     }

     public Random  getRandom ( )
     //////////////////////////////////////////////////////////////////////
     {
       return random;
     }

     public int  getComponentWidth  ( )
     //////////////////////////////////////////////////////////////////////
     {
       return componentBounds.width;
     }

     public int  getComponentHeight ( )
     //////////////////////////////////////////////////////////////////////
     {
       return componentBounds.height;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public long  currentTimeNanos ( )
     //////////////////////////////////////////////////////////////////////
     {
       return updateTimeNanos;
     }

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !initialized )
       {
         return;
       }

       updateTimeNanos = clock.currentTimeNanos ( );

       if ( resetZombies )
       {
         resetZombies ( );
       }

       arrayComponentUpdater.update ( component );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !initialized )
       {
         initialize ( );

         return;
       }

       arrayComponentPainter.paint ( component, graphics );
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private void  initialize ( )
     //////////////////////////////////////////////////////////////////////
     {
/*
       backgroundResourceImageIcon = new ResourceImageIcon (
         BACKGROUND_IMAGE_FILENAME,
         getClass ( ).getClassLoader ( ),
         new Dimension ( componentBounds.getSize ( ) ),
         animatedComponent );

       arrayComponentPainter.add (
         new IconPainter ( 0, 0, backgroundResourceImageIcon ) );
*/

       try
       {
         Image  backgroundImage = loadAutomaticImage (
           BACKGROUND_IMAGE_FILENAME, Transparency.OPAQUE );

         arrayComponentPainter.add (
           new IconPainter (
             0, 0, new StretchImageIcon ( backgroundImage ) ) );
       }
       catch ( IOException  ex )
       {
         ex.printStackTrace ( );
       }

       Icon [ ]  zombieIcons
         = new Icon [ ZOMBIE_IMAGE_FILENAMES.length ];

       for ( int  i = 0; i < ZOMBIE_IMAGE_FILENAMES.length; i++ )
       {
         zombieIcons [ i ]
           = validateIcon ( ZOMBIE_IMAGE_FILENAMES [ i ] );
       }

       Clock  clock = new HiResClock ( );

       zombies = new ZombieSprite [ ZOMBIE_COUNT ];

       for ( int  i = 0; i < ZOMBIE_COUNT; i++ )
       {
         ZombieSprite  zombie
           = new ZombieSprite ( zombieIcons [ 0 ], this );

         zombie.setComponentUpdater (
           new ArrayComponentUpdater ( new ComponentUpdater [ ] {
           new IconSequenceUpdater (
             zombie, zombieIcons, 1000000000L, this ),
           new BounceUpdater ( zombie, componentBounds, this ) } ) );

         zombies [ i ] = zombie;

         arrayComponentPainter.add ( zombie );

         arrayComponentUpdater.add ( zombie );
       }

       resetZombies ( );

/*
       FrameRateUpdater  frameRateUpdater
         = new FrameRateUpdater ( true );

       arrayComponentUpdater.add ( frameRateUpdater );
*/

       initialized = true;         
     }

// shift this reusable code to another class (ImageIconCache?)
     private Icon  validateIcon ( String  imageFilename )
     //////////////////////////////////////////////////////////////////////
     {
       Icon  icon = ( Icon ) iconMap.get ( imageFilename );

       if ( icon == null )
       {
         Image  image = null;

         try
         {
           image
             = loadAutomaticImage ( imageFilename, Transparency.BITMASK );
         }
         catch ( IOException  ex )
         {
           ex.printStackTrace ( );
         }

         if ( image == null )
         {
           return null;
         }

         icon = new ImageIcon ( image );

         iconMap.put ( imageFilename, icon );
       }

       return icon;
     }

     private void  handleAnimatedComponentResize ( )
     //////////////////////////////////////////////////////////////////////
     {
       animatedComponent.getBounds ( componentBounds );

/*
       ResourceImageIcon  backgroundResourceImageIcon
         = this.backgroundResourceImageIcon;

       if ( backgroundResourceImageIcon != null )
       {
         backgroundResourceImageIcon.setSize (
           componentBounds.getSize ( ) );
       }
*/
     }

     private void  fire ( int  x, int  y )
     //////////////////////////////////////////////////////////////////////
     {
       shotgunAudioClip.play ( );

       boolean  allZombiesDestroyed = true;

       for ( int  i = ZOMBIE_COUNT - 1; i >= 0; i-- )
       {
         ZombieSprite  zombie = zombies [ i ];

         if ( !zombie.isDestroyed ( ) )
         {
           allZombiesDestroyed = false;

           if ( zombie.getCollisionShape ( ).contains ( x, y ) )
           {
             hitAudioClip.play ( );

             zombie.setHit ( );

             break;
           }
         }
       }

       resetZombies = allZombiesDestroyed;
     }

     private Image  loadAutomaticImage (
       String  imageFilename,
       int     transparency )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return ImageLib.loadAutomaticImage (
         imageFilename,
         transparency,
         animatedComponent,
         getClass ( ).getClassLoader ( ),
         null );
     }

     private void  resetZombies ( )
     //////////////////////////////////////////////////////////////////////
     {
       resetZombies = false;

       barkAudioClip.play ( );

       for ( int  i = 0; i < ZOMBIE_COUNT; i++ )
       {
         zombies [ i ].reset ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
