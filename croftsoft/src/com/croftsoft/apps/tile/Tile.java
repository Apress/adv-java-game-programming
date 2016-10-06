     package com.croftsoft.apps.tile;
     
     import java.applet.*;
     import java.awt.*;
     import java.awt.image.BufferStrategy;
     import java.awt.event.*;
     import java.awt.geom.*;
     import java.awt.image.BufferedImage;
     import java.io.*;
     import java.net.URL;
     import java.util.*;
     import javax.imageio.ImageIO;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.animation.*;
     import com.croftsoft.core.animation.animator.*;
     import com.croftsoft.core.animation.icon.ColorTileIcon;
     import com.croftsoft.core.animation.sprite.*;
     import com.croftsoft.core.animation.painter.*;
     import com.croftsoft.core.animation.updater.*;
     import com.croftsoft.core.applet.AppletLib;
     import com.croftsoft.core.awt.image.ImageLib;
     import com.croftsoft.core.io.SerializableLib;
     import com.croftsoft.core.jnlp.JnlpLib;
     import com.croftsoft.core.lang.StringLib;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.math.MathConstants;

     /*********************************************************************
     * Plane of modifiable tiles.
     *
     * @version
     *   2003-08-11
     * @since
     *   2003-03-08
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Tile
       extends AnimatedApplet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     //////////////////////////////////////////////////////////////////////
     // Applet constants
     //////////////////////////////////////////////////////////////////////

     private static final String  VERSION
       = "2003-08-11";

     private static final String  TITLE
       = "CroftSoft Tile";

     private static final String  APPLET_INFO
       = "\n" + TITLE + "\n"
       + CroftSoftConstants.COPYRIGHT + "\n"
       + CroftSoftConstants.HOME_PAGE + "\n"
       + "Version " + VERSION + "\n"
       + "Programming.....:  David Wallace Croft\n"
       + "Tile Graphics...:  Shannon Kristine Croft\n";

     //////////////////////////////////////////////////////////////////////
     // Frame constants
     //////////////////////////////////////////////////////////////////////

     private static final String     FRAME_TITLE = TITLE;

     private static final String     FRAME_ICON_FILENAME
       = "/images/croftsoft.png";
       
     private static final Dimension  FRAME_SIZE = null;

     private static final String     SHUTDOWN_CONFIRMATION_PROMPT
       = "Close " + TITLE + "?";

     //////////////////////////////////////////////////////////////////////
     // animation constants
     //////////////////////////////////////////////////////////////////////

     private static final Color   BACKGROUND_COLOR = Color.BLACK;

     private static final Cursor  CURSOR
       = new Cursor ( Cursor.CROSSHAIR_CURSOR );

     //////////////////////////////////////////////////////////////////////
     // persistence constants
     //////////////////////////////////////////////////////////////////////

     private static final String  LATEST_FILENAME
       = ".croftsoft/tile/tile_new.dat";

     private static final String  BACKUP_FILENAME
       = ".croftsoft/tile/tile_old.dat";

     private static final String  FILE_CONTENTS_SPEC
       = "Tile";

     private static final String  PERSISTENCE_KEY
       = FILE_CONTENTS_SPEC;

     private static final String  RESOURCE_PATH_FILENAME
       = "apps/tile/tile.dat";

     //////////////////////////////////////////////////////////////////////
     // graphics constants
     //////////////////////////////////////////////////////////////////////

     private static final long       RANDOM_SEED = 0L;

     private static final String     TILE_MAP_IMAGE_FILENAME
       = "apps/tile/tile_map.png";

     private static final String     TILE_DIR = "apps/tile/tiles/";

     private static final String     TILE_FILENAME_EXTENSION = ".png";

     /** Default width and height of tile data. */
     private static final int        DEFAULT_MAP_SIZE = 100;

     private static final Dimension  DEFAULT_TILE_SIZE
       = new Dimension ( 40, 40 );

     private static final int [ ]    DEFAULT_PALETTE = new int [ ] {
       0xFF000000,   // black
       0xFF0000FF,   // blue
       0xFF00FF00,   // green
       0xFF00FFFF,   // cyan
       0xFFFF0000,   // red
       0xFFFF00FF,   // magenta
       0xFFFFFF00,   // yellow
       0xFFFFFFFF }; // white

     private static final int        DEFAULT_PALETTE_INDEX = 0; // black

     private static final int        SMOOTHING_LOOPS = 1000;

     //////////////////////////////////////////////////////////////////////
     // instance variables
     //////////////////////////////////////////////////////////////////////

     private int [ ]            palette;

     private byte [ ] [ ]       tileMap;

     private EdgeScrollUpdater  edgeScrollUpdater;

     private Rectangle          componentBounds;

     private TilePainter        tilePainter;

     private Ellipse2D.Double   tileEllipse2D;

     private Point              mousePoint;

     private boolean            dataIsDirty;

     private Rectangle          clipBounds;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       launch ( new Tile ( ) );
     }

     public static AnimationInit  createAnimationInit ( )
     //////////////////////////////////////////////////////////////////////
     {
       AnimationInit  animationInit
         = new AnimationInit ( );

       animationInit.setAppletInfo ( APPLET_INFO );

       animationInit.setBackgroundColor ( BACKGROUND_COLOR );

       animationInit.setCursor ( CURSOR );

       animationInit.setFrameIconFilename ( FRAME_ICON_FILENAME );

       animationInit.setFrameSize ( FRAME_SIZE );

       animationInit.setFrameTitle ( FRAME_TITLE );

       animationInit.setShutdownConfirmationPrompt (
         SHUTDOWN_CONFIRMATION_PROMPT );

       return animationInit;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Tile ( )
     //////////////////////////////////////////////////////////////////////
     {
       super ( createAnimationInit ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       super.init ( );

       TileData  tileData = null;

       try
       {
         tileData = ( TileData ) SerializableLib.load (
           LATEST_FILENAME,
           BACKUP_FILENAME,
           FILE_CONTENTS_SPEC,
           ( Applet ) this,
           PERSISTENCE_KEY,
           getClass ( ).getClassLoader ( ),
           RESOURCE_PATH_FILENAME );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }

       if ( tileData == null )
       {
         try
         {
           tileData = TileData.loadTileDataFromImage (
             TILE_MAP_IMAGE_FILENAME,
             getClass ( ).getClassLoader ( ) );
         }
         catch ( IOException  ex )
         {
           ex.printStackTrace ( );
         }
       }

       if ( tileData != null )
       {
         palette = tileData.getPalette ( );

         tileMap = tileData.getTileMap ( );
       }

       try
       {
         TileData.remapToPalette ( palette, tileMap, DEFAULT_PALETTE_INDEX );
       }
       catch ( IllegalArgumentException  ex )
       {
         palette = new int [ ] {
           0xFF0000FF,   // blue water
           0xFF00FF00 }; // green land

         tileMap = TileData.generateRandomTileMap (
           new Random ( RANDOM_SEED ),
           palette,
           DEFAULT_MAP_SIZE, // rows
           DEFAULT_MAP_SIZE, // columns
           SMOOTHING_LOOPS );
       }

       Dimension  tileSize = DEFAULT_TILE_SIZE;

       Icon [ ]  tileIcons = new Icon [ palette.length ];

       for ( int  i = 0; i < palette.length; i++ )
       {
         int  argb = palette [ i ];

         Image  tileImage = null;

         try
         {
           String  iconFilename
             = TILE_DIR
             + StringLib.padLeft ( Integer.toHexString ( argb ), '0', 8 )
             + TILE_FILENAME_EXTENSION;

           tileImage = ImageLib.loadAutomaticImage (
             iconFilename,
             Transparency.OPAQUE,
             animatedComponent,
             getClass ( ).getClassLoader ( ),
             tileSize );
         }
         catch ( IllegalArgumentException  ex )
         {
         }
         catch ( Exception  ex )
         {
           ex.printStackTrace ( );
         }

         if ( tileImage == null )
         {
           tileIcons [ i ]
             = new ColorTileIcon ( new Color ( argb ), tileSize );
         }
         else
         {
           tileIcons [ i ] = new ImageIcon ( tileImage );
         }
       }

       componentBounds = animatedComponent.getBounds ( );

       tileEllipse2D = new Ellipse2D.Double (
         0, 0, componentBounds.width, componentBounds.height );

       tilePainter = new TilePainter (
         0,
         0,
         tileIcons,
         tileMap,
         tileSize,
         tileEllipse2D );

       edgeScrollUpdater = new EdgeScrollUpdater (
         animatedComponent,
         tilePainter.getTileColumns ( ) * tilePainter.getTileWidth  ( ),
         tilePainter.getTileRows    ( ) * tilePainter.getTileHeight ( ),
         ( Dimension ) null, // edgeSize
         10,                 // scrollRate
         true );             // wrapAround

       animatedComponent.addMouseListener (
         new MouseAdapter ( )
         {
           public void  mousePressed ( MouseEvent  mouseEvent )
           {
             mousePoint = mouseEvent.getPoint ( );
           }
         } );

       clipBounds = new Rectangle ( );
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( dataIsDirty )
       {
         try
         {
           SerializableLib.save (
             new TileData ( palette, tileMap ),
             LATEST_FILENAME,
             BACKUP_FILENAME,
             FILE_CONTENTS_SPEC,
             ( Applet ) this,
             PERSISTENCE_KEY );
         }
         catch ( Exception  ex )
         {
           ex.printStackTrace ( );
         }
       }

       super.destroy ( );
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       edgeScrollUpdater.update ( component );

       component.getBounds ( componentBounds );

       tileEllipse2D.x = -edgeScrollUpdater.getTranslateX ( );

       tileEllipse2D.y = -edgeScrollUpdater.getTranslateY ( );

       tileEllipse2D.width  = componentBounds.width;

       tileEllipse2D.height = componentBounds.height;

       if ( mousePoint != null )
       {
         edgeScrollUpdater.translateReverse ( mousePoint );

         if ( tileEllipse2D.contains ( mousePoint ) )
         {
           int  tileRow    = tilePainter.getTileRow    ( mousePoint );

           int  tileColumn = tilePainter.getTileColumn ( mousePoint );

           int  paletteIndex = 0xFF & tileMap [ tileRow ] [ tileColumn ];

           paletteIndex = ( paletteIndex + 1 ) % palette.length;

           tileMap [ tileRow ] [ tileColumn ] = ( byte ) paletteIndex;

           dataIsDirty = true;

           component.repaint ( );
         }

         mousePoint = null;
       }
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       graphics.setColor ( BACKGROUND_COLOR );

       graphics.fillRect ( 0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE );

       edgeScrollUpdater.translate ( graphics );

       tilePainter.paint ( component, graphics );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }