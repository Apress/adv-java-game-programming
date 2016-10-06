     package com.croftsoft.apps.sprite;

     import java.awt.Dimension;

     /*********************************************************************
     * An interface of constants.
     *
     * @version
     *   2003-09-29
     * @since
     *   2001-02-28
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  SpriteConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  TITLE = "CroftSoft Sprite";

     public static final String  VERSION = "2003-09-29";

     //////////////////////////////////////////////////////////////////////
     // Applet constants
     //////////////////////////////////////////////////////////////////////

     public static final String  INFO
       = "\n" + TITLE + "\n"
       + "Copyright 2002 CroftSoft Inc\n"
       + "http://www.croftsoft.com/\n"
       + "Version " + VERSION + "\n";

     //////////////////////////////////////////////////////////////////////
     // Director constants
     //////////////////////////////////////////////////////////////////////

     /** frames per second */
     public static final double  DEFAULT_FRAME_RATE = 999.9;

     /** pixels per second */
     public static final double  DEFAULT_SPRITE_VELOCITY = 120.0;

     public static final String  BACKGROUND_IMAGE_FILENAME
       = "images/sprite/tile/clear_brick_32x32.png";

     public static final String  NORMAL_IMAGE_FILENAME
       = "images/sprite/david.png";
       
     public static final String  IMPACT_IMAGE_FILENAME
       = "images/sprite/david2.png";
       
     public static final String  LOOK_LEFT_IMAGE_FILENAME
       = "images/sprite/david_left.png";

     public static final String  LOOK_RIGHT_IMAGE_FILENAME
       = "images/sprite/david_right.png";

     public static final String  CLOUD_IMAGE_FILENAME
       = "images/sprite/tile/cloud_256x256.png";

     public static final String  MOUSE_IMAGE_FILENAME
       = "images/sprite/david.png";

     public static final String  MOUSE_PRESSED_IMAGE_FILENAME
       = "images/sprite/david2.png";

     public static final String  CURSOR_IMAGE_FILENAME
       = "/" + NORMAL_IMAGE_FILENAME;

     //////////////////////////////////////////////////////////////////////
     // Frame constants
     //////////////////////////////////////////////////////////////////////

     public static final String  FRAME_TITLE
       = TITLE;

     public static final String  FRAME_ICON_FILENAME
       = "/" + NORMAL_IMAGE_FILENAME;
       
     public static final Dimension  FRAME_SIZE
       = new Dimension ( 240, 360 );

     public static final String  SHUTDOWN_CONFIRMATION_PROMPT
       = "Close " + TITLE + "?";

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
