     package com.croftsoft.apps.road;
     
     import java.applet.*;
     import java.awt.*;
     import java.awt.event.*;
     import java.awt.geom.*;
     import java.io.*;
     import java.net.URL;
     import java.util.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.animation.AnimatedApplet;
     import com.croftsoft.core.animation.AnimationInit;
     import com.croftsoft.core.animation.animator.TileAnimator;
     import com.croftsoft.core.animation.clock.HiResClock;
     import com.croftsoft.core.animation.clock.Timekeeper;
     import com.croftsoft.core.animation.controller.FrameRateController;
     import com.croftsoft.core.animation.icon.ColorTileIcon;
     import com.croftsoft.core.animation.painter.ColorPainter;
     import com.croftsoft.core.animation.painter.IconPainter;
     import com.croftsoft.core.animation.painter.TilePainter;
     import com.croftsoft.core.animation.sprite.IconSprite;
     import com.croftsoft.core.awt.image.ImageLib;
     import com.croftsoft.core.gui.event.UserInputAdapter;
//   import com.croftsoft.core.media.sound.AudioClipCache;

     /*********************************************************************
     * CroftSoft Roadrunner main class.
     *
     * @version
     *   2003-09-10
     * @since
     *   2003-07-02
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Main
       extends AnimatedApplet
       implements Constants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Rectangle   componentBounds;

     private final Rectangle   tileArea;

     private final Rectangle   leftBorderArea;

     private final Rectangle   rightBorderArea;

     //

     private TilePainter   tilePainter;

     private TileAnimator  tileAnimator;

     private boolean       componentResized;

     private Timekeeper    timekeeper;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       launch ( new Main ( ) );
     }

     private static AnimationInit  createAnimationInit ( )
     //////////////////////////////////////////////////////////////////////
     {
       AnimationInit  animationInit = new AnimationInit ( );

       animationInit.setAppletInfo ( APPLET_INFO );

       animationInit.setBackgroundColor ( BACKGROUND_COLOR );

       animationInit.setCursor ( CURSOR );

       animationInit.setFont ( FONT );

       animationInit.setForegroundColor ( FOREGROUND_COLOR );

       animationInit.setFrameIconFilename ( FRAME_ICON_FILENAME );

       animationInit.setFrameRate ( FRAME_RATE );

       animationInit.setFrameSize ( FRAME_SIZE );

       animationInit.setFrameTitle ( FRAME_TITLE );

       animationInit.setShutdownConfirmationPrompt (
         SHUTDOWN_CONFIRMATION_PROMPT );

       return animationInit;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Main ( )
     //////////////////////////////////////////////////////////////////////
     {
       super ( createAnimationInit ( ) );

       componentBounds = new Rectangle ( );

       tileArea        = new Rectangle ( );

       leftBorderArea  = new Rectangle ( );

       rightBorderArea = new Rectangle ( );

       tileArea.width = TILE_AREA_WIDTH;

       componentResized = true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       super.init ( );

       // create left and right border painters

       addComponentPainter (
         new ColorPainter ( BACKGROUND_COLOR, leftBorderArea ) );

       addComponentPainter (
         new ColorPainter ( BACKGROUND_COLOR, rightBorderArea ) );

       // initialize background tiles

       Icon [ ]  tileIcons = new Icon [ TILE_IMAGE_FILENAMES.length ];

       for ( int  i = 0; i < tileIcons.length; i++ )
       {
         try
         {
           tileIcons [ i ] = new ImageIcon (
             ImageLib.loadAutomaticImage (
               MEDIA_DIR + TILE_IMAGE_FILENAMES [ i ]
                 + IMAGE_FILENAME_EXTENSION,
               Transparency.OPAQUE,
               animatedComponent,
               getClass ( ).getClassLoader ( ),
               TILE_DIMENSION ) ); // dimension
         }
         catch ( Exception  ex )
         {
           tileIcons [ i ]
             = new ColorTileIcon ( Color.RED, TILE_DIMENSION );

           ex.printStackTrace ( );
         }
       }

       // initialize tileMap

       byte [ ] [ ]  tileMap = new byte [ ] [ ] { {
         TILE_TYPE_WALL,
         TILE_TYPE_GRASS,
         TILE_TYPE_SHOULDER,
         TILE_TYPE_ROAD,
         TILE_TYPE_DIVIDER,
         TILE_TYPE_ROAD,
         TILE_TYPE_DIVIDER,
         TILE_TYPE_ROAD,
         TILE_TYPE_DIVIDER,
         TILE_TYPE_ROAD,
         TILE_TYPE_DIVIDER,
         TILE_TYPE_ROAD,
         TILE_TYPE_SHOULDER,
         TILE_TYPE_GRASS,
         TILE_TYPE_WALL } };

       // initialize TileAnimator

       tilePainter = new TilePainter (
         0, // tileOffsetX
         0, // tileOffsetY
         tileIcons,
         tileMap,
         TILE_DIMENSION,
         tileArea ); // tileShape

       tileAnimator = new TileAnimator ( tilePainter, 0, ROAD_RATE );

       addComponentAnimator ( tileAnimator );

       // initialize avatar

       Icon  runnerIcon = null;

       try
       {
         runnerIcon = new ImageIcon (
           ImageLib.loadAutomaticImage (
             MEDIA_DIR + RUNNER_IMAGE_FILENAME + IMAGE_FILENAME_EXTENSION,
             Transparency.BITMASK, // transparent background
             animatedComponent,
             getClass ( ).getClassLoader ( ),
             TILE_DIMENSION ) );
       }
       catch ( Exception  ex )
       {
         runnerIcon = new ColorTileIcon ( Color.RED, TILE_DIMENSION );

         ex.printStackTrace ( );
       }

       timekeeper
         = new Timekeeper ( new HiResClock ( ), 1.0 );

       addComponentAnimator (
         new RunnerSprite ( runnerIcon, timekeeper, animatedComponent ) );

       // initialize enemies

       Icon  enemyIcon = null;

       try
       {
         enemyIcon = new ImageIcon (
           ImageLib.loadAutomaticImage (
             MEDIA_DIR + ENEMY_IMAGE_FILENAME + IMAGE_FILENAME_EXTENSION,
             Transparency.BITMASK, // transparent background
             animatedComponent,
             getClass ( ).getClassLoader ( ),
             TILE_DIMENSION ) );
       }
       catch ( Exception  ex )
       {
         enemyIcon = new ColorTileIcon ( Color.RED, TILE_DIMENSION );

         ex.printStackTrace ( );
       }

       addComponentAnimator (
         new EnemySprite ( enemyIcon, timekeeper ) );

       // The frame rate display is toggled when the 'f' key is pressed.

       addComponentAnimator (
         new FrameRateController ( animatedComponent )
           .getFrameRateAnimator ( ) );
       //

       animatedComponent.addComponentListener (
         new ComponentAdapter ( )
         {
           public void  componentResized ( ComponentEvent  componentEvent )
           {
             componentResized = true;
           }
         } );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       if ( componentResized )
       {
         resetBounds ( );

         component.repaint ( );

         componentResized = false;
       }

       timekeeper.update ( );

       super.update ( component );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private void  resetBounds ( )
     //////////////////////////////////////////////////////////////////////
     {
       animatedComponent.getBounds ( componentBounds );

       tileArea.x = ( componentBounds.width - TILE_AREA_WIDTH ) / 2;

       tilePainter.setOffsetX ( tileArea.x );

       tileArea.height = componentBounds.height;

       leftBorderArea.width = tileArea.x > 0 ? tileArea.x : 0;

       leftBorderArea.height = componentBounds.height;

       rightBorderArea.x = tileArea.x + TILE_AREA_WIDTH;

       int  rightBorderWidth = componentBounds.width - rightBorderArea.x;

       rightBorderArea.width = rightBorderWidth > 0 ? rightBorderWidth : 0;

       rightBorderArea.height = componentBounds.height;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }