     package com.croftsoft.apps.road;

     import java.awt.*;

     /*********************************************************************
     * CroftSoft Roadrunner constants.
     *
     * @version
     *   2003-09-10
     * @since
     *   2003-08-05
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  Constants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  VERSION
       = "2003-09-10";

     public static final String  TITLE
       = "CroftSoft Roadrunner";

     public static final String  APPLET_INFO
       = "\n" + TITLE + "\n"
       + "Copyright 2003 CroftSoft Inc\n"
       + "http://www.croftsoft.com/\n"
       + "Version " + VERSION + "\n"
       + "Licensed under the Academic Free License version 1.2\n";

     //////////////////////////////////////////////////////////////////////
     // frame constants
     //////////////////////////////////////////////////////////////////////

     public static final String  FRAME_TITLE
       = TITLE;

     public static final String  FRAME_ICON_FILENAME
       = "/images/croftsoft.png";
       
     public static final Dimension  FRAME_SIZE
       = new Dimension ( 600, 400 );

     public static final String  SHUTDOWN_CONFIRMATION_PROMPT
       = "Close " + TITLE + "?";

     //////////////////////////////////////////////////////////////////////
     // animation constants
     //////////////////////////////////////////////////////////////////////

     /** frames per second */
     public static final Double  FRAME_RATE = new Double ( 85.0 );

     public static final Color   BACKGROUND_COLOR
       = new Color ( 0, 100, 0 );

     public static final Color   FOREGROUND_COLOR
       = Color.BLACK;

     public static final Font    FONT
       = new Font ( "Arioso", Font.BOLD, 10 );

     public static final Cursor  CURSOR
       = new Cursor ( Cursor.CROSSHAIR_CURSOR );

     public static final String  MEDIA_DIR = "media/road/";

     //////////////////////////////////////////////////////////////////////
     // tile constants
     //////////////////////////////////////////////////////////////////////

     public static final byte  TILE_TYPE_DIVIDER  = 0;

     public static final byte  TILE_TYPE_GRASS    = 1;

     public static final byte  TILE_TYPE_ROAD     = 2;

     public static final byte  TILE_TYPE_SHOULDER = 3;

     public static final byte  TILE_TYPE_WALL     = 4;

     public static final int   TILE_TYPES         = 5;

     //

     public static final String [ ]  TILE_IMAGE_FILENAMES
       = new String [ ] {
       "divider",
       "grass",
       "road",
       "shoulder",
       "wall" };

     public static final String  ENEMY_IMAGE_FILENAME  = "enemy";

     public static final String  RUNNER_IMAGE_FILENAME = "runner";

     public static final String  IMAGE_FILENAME_EXTENSION = ".png";

     //

     public static final int  TILE_SIZE = 40;

     public static final Dimension  TILE_DIMENSION
       = new Dimension ( TILE_SIZE, TILE_SIZE );

     public static final int  TILE_AREA_WIDTH = 15 * TILE_SIZE;

     /** pixels per frame */
     public static final int  ROAD_RATE = -2;

     public static final double  TIME_DELTA_MAX = 0.1;

     /** pixels per second */
     public static final double  RUNNER_VELOCITY = 2 * TILE_SIZE;

     /** pixels per second */
     public static final double  ENEMY_VELOCITY  = RUNNER_VELOCITY;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }