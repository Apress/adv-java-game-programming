     package com.croftsoft.apps.sprite;

     import java.awt.*;
     import java.awt.image.*;
     import java.awt.event.*;
     import java.io.*;
     import java.net.*;
     import java.util.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.animation.*;
     import com.croftsoft.core.animation.animator.*;
     import com.croftsoft.core.animation.clock.*;
     import com.croftsoft.core.animation.collector.*;
     import com.croftsoft.core.animation.component.*;
     import com.croftsoft.core.animation.factory.DefaultAnimationFactory;
     import com.croftsoft.core.animation.icon.*;
     import com.croftsoft.core.animation.painter.*;
     import com.croftsoft.core.animation.sprite.*;
     import com.croftsoft.core.animation.updater.*;
     import com.croftsoft.core.awt.image.ImageLib;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.util.loop.*;

// Direct control of imageCache when sprite removed for last time?

// MemoryCacheInputStream
// Java 1.4 upgrade:  Component.createVolatileImage(int,int)

// garbage collection settings?

// transparent background Color for fun

     /*********************************************************************
     * Directs the AnimatedComponent.
     *
     * @version
     *   2003-11-06
     * @since
     *   2002-02-15
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SpriteAnimator
       implements Clock, ComponentAnimator, SpriteConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final AnimatedComponent  animatedComponent;

     private final Map                iconMap;

// get rid of Random
     private final Random             random;

     private final Rectangle          bounds;

     private final Rectangle          spriteBounds;

     //

     private Clock  clock;

     private long   updateTimeNanos;

     // Animators

     private TileAnimator       brickTileAnimator;

     private Sprite [ ]         headSprites;

     private Sprite [ ]         rateSprites;

     private Sprite [ ]         sprites;

     private TileAnimator       cloudTileAnimator;

     // Updaters

     private FogNightUpdater    fogNightUpdater;

     private ComponentUpdater   updateRateSampler;

     // Painters

     private ColorPainter       brickColorPainter;

     private CursorAnimator     cursorAnimator;

     private ColorPainter       fogNightColorPainter;

     private ComponentPainter   paintRateSampler;

     //

     private boolean  initialized;

     private boolean  paintBackground;

     private boolean  paintBricks;

     private boolean  paintSprites;

     private boolean  paintClouds;

     private boolean  paintFogNight;

     private boolean  onlySpriteUpdates;

     private boolean  onlySpriteRegions;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SpriteAnimator ( BufferStrategy  bufferStrategy )
     //////////////////////////////////////////////////////////////////////
     {
//javax.swing.RepaintManager.setCurrentManager ( new SpriteRepaintManager ( ) );

       iconMap      = new HashMap   ( );

       random       = new Random    ( );

       bounds       = new Rectangle ( );

       spriteBounds = new Rectangle ( );

       if ( bufferStrategy == null )
       {
         animatedComponent = new AnimatedComponent (
           this,
           new CoalescingRepaintCollector ( ),
           new WindowedLoopGovernor ( DEFAULT_FRAME_RATE ) );
       }
       else
       {
         animatedComponent = new BufferStrategyAnimatedComponent (
           this,
           new CoalescingRepaintCollector ( ),
           new WindowedLoopGovernor ( DEFAULT_FRAME_RATE ),
           bufferStrategy );
       }

       animatedComponent.setBackground ( Color.MAGENTA );

       animatedComponent.setForeground ( Color.GREEN );

       animatedComponent.setFont (
         new Font ( "Times New Roman", Font.BOLD, 12 ) );

       paintBackground = true;

       paintBricks     = true;

       paintSprites    = true;

       paintClouds     = true;

       paintFogNight   = false;

       clock = new HiResClock ( );
     }

     public void  initialize ( )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       IconSprite  sprite1
         = new IconSprite ( validateIcon ( NORMAL_IMAGE_FILENAME ) );

       sprite1.setComponentUpdater (
         new ArrayComponentUpdater (
           new ComponentUpdater [ ] {
             new BounceUpdater ( sprite1, bounds, this ),
             new ImpactUpdater (
               sprite1,
               validateIcon ( NORMAL_IMAGE_FILENAME ),
               validateIcon ( IMPACT_IMAGE_FILENAME ),
               250000000,
               this ) } ) );

       sprite1.setHeading ( random.nextDouble ( ) * 2.0 * Math.PI );

       IconSprite  sprite2
         = new IconSprite ( validateIcon ( LOOK_LEFT_IMAGE_FILENAME ) );

       sprite2.setY ( 10.0 );

       sprite2.setComponentUpdater (
         new ArrayComponentUpdater (
           new ComponentUpdater [ ] {
             new BounceUpdater ( sprite2, bounds, this ),
             new LeftRightUpdater (
               sprite2,
               validateIcon ( LOOK_LEFT_IMAGE_FILENAME  ),
               validateIcon ( LOOK_RIGHT_IMAGE_FILENAME ) ) } ) );

       TextSprite  updateRateTextSprite = new TextSprite ( "*" );

       TextSprite  paintRateTextSprite  = new TextSprite ( "*" );

       updateRateTextSprite.setColor ( Color.GREEN );

       paintRateTextSprite .setColor ( Color.BLUE  );

       updateRateTextSprite.setComponentUpdater (
         new TextWrapUpdater ( updateRateTextSprite, -1,  1, bounds ) );

       paintRateTextSprite .setComponentUpdater (
         new TextWrapUpdater ( paintRateTextSprite , -1, -1, bounds ) );

       updateRateSampler = new FrameRateSampler (
         updateRateTextSprite, 3000, "Updates per second:  " );

       paintRateSampler  = new FrameRateSampler (
         paintRateTextSprite , 3000, "Paints per second:  " );

       headSprites = new Sprite [ ] {
         sprite1,
         sprite2 };

       rateSprites = new Sprite [ ] {
         paintRateTextSprite,
         updateRateTextSprite };

       sprites = new Sprite [ ] {
         sprite1,
         sprite2,
         paintRateTextSprite,
         updateRateTextSprite };

       setSpriteVelocity ( DEFAULT_SPRITE_VELOCITY );

       // We need a background color since the background image contains
       // some transparent pixels.

       brickColorPainter = new ColorPainter ( Color.RED, bounds );

       brickTileAnimator = new TileAnimator (
         0, 0, validateIcon ( BACKGROUND_IMAGE_FILENAME ), null,  1, 1 );

       cloudTileAnimator = new TileAnimator (
         0, 0, validateIcon ( CLOUD_IMAGE_FILENAME      ), null, -1, 1 );

       fogNightColorPainter = new ColorPainter ( Color.BLACK, bounds );

       fogNightUpdater = new FogNightUpdater (
         fogNightColorPainter, brickColorPainter, 60, this );

// show a sprite with more than one updater (multimage animation)

// also show alpha transparency baloons

// show orientation and mirroring

// show animated GIFs and other animation

       cursorAnimator = new CursorAnimator (
         validateIcon ( MOUSE_IMAGE_FILENAME ),
         validateIcon ( MOUSE_PRESSED_IMAGE_FILENAME ),
         ( Point ) null, // hotSpot
         animatedComponent );

       initialized = true;
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public AnimatedComponent  getAnimatedComponent ( )
     //////////////////////////////////////////////////////////////////////
     {
       return animatedComponent;
     }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setSpriteVelocity ( double  spriteVelocity )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < sprites.length; i++ )
       {
         sprites [ i ].setVelocity ( spriteVelocity );
       }
     }

     public void  setPaintBackground ( boolean  paintBackground )
     //////////////////////////////////////////////////////////////////////
     {
       this.paintBackground = paintBackground;
     }

     public void  setPaintBricks ( boolean  paintBricks )
     //////////////////////////////////////////////////////////////////////
     {
       this.paintBricks = paintBricks;
     }

     public void  setPaintSprites ( boolean  paintSprites )
     //////////////////////////////////////////////////////////////////////
     {
       this.paintSprites = paintSprites;
     }

     public void  setPaintClouds ( boolean  paintClouds )
     //////////////////////////////////////////////////////////////////////
     {
       this.paintClouds = paintClouds;
     }

     public void  setPaintFogNight ( boolean  paintFogNight )
     //////////////////////////////////////////////////////////////////////
     {
       this.paintFogNight = paintFogNight;
     }

     public void  setOnlySpriteUpdates ( boolean  onlySpriteUpdates )
     //////////////////////////////////////////////////////////////////////
     {
       this.onlySpriteUpdates = onlySpriteUpdates;
     }

     public void  setOnlySpriteRegions ( boolean  onlySpriteRegions )
     //////////////////////////////////////////////////////////////////////
     {
       this.onlySpriteRegions = onlySpriteRegions;
     }

     public void  setUseSystemClock ( boolean  useSystemClock )
     //////////////////////////////////////////////////////////////////////
     {
       if ( useSystemClock )
       {
         clock = SystemClock.INSTANCE;
       }
       else
       {
         clock = new HiResClock ( );
       }
     }

     public void  setLoopGovernor (
       boolean  useFixedDelay,
       double   frequency )
     //////////////////////////////////////////////////////////////////////
     {
       if ( useFixedDelay )
       {
         animatedComponent.setLoopGovernor (
           new FixedDelayLoopGovernor ( frequency ) );
       }
       else
       {
         animatedComponent.setLoopGovernor (
           new WindowedLoopGovernor ( frequency ) );
       }
     }

     public void  setUseSwingRepaintCollector (
       boolean  useSwingRepaintCollector )
     //////////////////////////////////////////////////////////////////////
     {
       if ( useSwingRepaintCollector )
       {
         animatedComponent.setRepaintCollector (
           new SwingRepaintCollector ( animatedComponent ) );
       }
       else
       {
         animatedComponent.setRepaintCollector (
           DefaultAnimationFactory.INSTANCE.createRepaintCollector ( ) );
       }
     }

     public void  setUseDoubleBuffering ( boolean  useDoubleBuffering )
     //////////////////////////////////////////////////////////////////////
     {
       RepaintManager.currentManager ( animatedComponent )
         .setDoubleBufferingEnabled ( useDoubleBuffering );
     }

     //////////////////////////////////////////////////////////////////////
     // interface Clock method
     //////////////////////////////////////////////////////////////////////

     public long  currentTimeNanos ( )
     //////////////////////////////////////////////////////////////////////
     {
       return updateTimeNanos;
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !initialized )
       {
         return;
       }

       updateTimeNanos = clock.currentTimeNanos ( );

       // Set the bounds here to prevent them from changing during the
       // update.

       component.getBounds ( bounds );

       if ( paintSprites )
       {
         for ( int  i = 0; i < headSprites.length; i++ )
         {
           headSprites [ i ].update ( component );
         }
       }

       if ( !onlySpriteUpdates )
       {
         RepaintCollector  oldRepaintCollector
           = animatedComponent.setRepaintCollector (
           NullRepaintCollector.INSTANCE );

         if ( paintBricks )
         {
           brickTileAnimator.update ( component );
         }

         if ( paintClouds )
         {
           cloudTileAnimator.update ( component );
         }

         if ( paintFogNight )
         {
           fogNightUpdater  .update ( component );
         }

         animatedComponent.setRepaintCollector ( oldRepaintCollector );
       }

       cursorAnimator.update ( component );

       for ( int  i = 0; i < rateSprites.length; i++ )
       {
         rateSprites [ i ].update ( component );
       }

       updateRateSampler.update ( component );

       if ( !onlySpriteRegions )
       {
         component.repaint ( );
       }
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !initialized )
       {
         try
         {
           initialize ( );
         }
         catch ( IOException  ex )
         {
           ex.printStackTrace ( );
         }
       }

       if ( paintBackground )
       {
         brickColorPainter.paint ( component, graphics );
       }

       if ( paintBricks )
       {
         brickTileAnimator.paint ( component, graphics );
       }

       if ( paintSprites )
       {
         for ( int  i = 0; i < headSprites.length; i++ )
         {
           headSprites [ i ].paint ( component, graphics );
         }
       }

       cursorAnimator.paint ( component, graphics );

       if ( paintClouds )
       {
         cloudTileAnimator.paint ( component, graphics );
       }

       if ( paintFogNight )
       {
         fogNightColorPainter.paint ( component, graphics );
       }

       paintRateSampler.paint ( component, graphics );

       for ( int  i = 0; i < rateSprites.length; i++ )
       {
         rateSprites [ i ].paint ( component, graphics );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private Icon  validateIcon ( String  imageFilename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       Icon  icon = ( Icon ) iconMap.get ( imageFilename );

       if ( icon == null )
       {
         Image  image
           = loadAutomaticImage ( imageFilename, Transparency.BITMASK );

         if ( image == null )
         {
           return null;
         }

         icon = new ImageIcon ( image );

         iconMap.put ( imageFilename, icon );
       }

       return icon;
     }

     private BufferedImage  loadAutomaticImage (
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

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
