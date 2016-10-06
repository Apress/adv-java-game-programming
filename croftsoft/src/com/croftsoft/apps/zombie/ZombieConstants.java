     package com.croftsoft.apps.zombie;

     import java.awt.Dimension;

     /*********************************************************************
     * An interface of constants for Zombie Hunter.
     *
     * @version
     *   2003-07-11
     * @since
     *   2002-02-17
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  ZombieConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     //////////////////////////////////////////////////////////////////////
     // Applet constants
     //////////////////////////////////////////////////////////////////////

     public static final String  VERSION
       = "2003-07-11";

     public static final String  TITLE
       = "CroftSoft Zombie Hunter";

     public static final String  INFO
       = "\n" + TITLE + "\n"
       + "Copyright 2003 CroftSoft Inc\n"
       + "http://www.croftsoft.com/\n"
       + "Version " + VERSION + "\n"
       + "Programming...:  David Wallace Croft\n"
       + "Graphics......:  Steven Morris Croft\n";

     //////////////////////////////////////////////////////////////////////
     // Frame constants
     //////////////////////////////////////////////////////////////////////

     public static final String  FRAME_TITLE
       = TITLE;

     public static final String  FRAME_ICON_FILENAME
       = "/images/david.png";
       
     public static final Dimension  FRAME_SIZE
       = null;

     public static final String  SHUTDOWN_CONFIRMATION_PROMPT
       = "Kill " + TITLE + "?";

// do something about not drawing background color if null
// in SpriteCanvas

// show demo of animated GIF and document

     //////////////////////////////////////////////////////////////////////
     // Director constants
     //////////////////////////////////////////////////////////////////////

     public static final double  FRAME_RATE = 60.0;

     public static final String  BACKGROUND_IMAGE_FILENAME
       = "images/zombie/background.gif";

     public static final String [ ]  ZOMBIE_IMAGE_FILENAMES = {
       "images/zombie/zombie1.gif",
       "images/zombie/zombie2.gif" };

     public static final int  ZOMBIE_COUNT = 3;

     public static final String  SHOTGUN_AUDIO_FILENAME
       = "audio/zombie/shotgun.wav";
       
     public static final String  HIT_AUDIO_FILENAME
       = "audio/zombie/hit.wav";
       
     public static final String  BARK_AUDIO_FILENAME
       = "audio/zombie/bark.wav";
       
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
