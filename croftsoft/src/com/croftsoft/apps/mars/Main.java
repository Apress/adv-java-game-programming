     package com.croftsoft.apps.mars;
     
     import java.applet.*;
     import java.awt.*;
     import java.awt.event.*;
     import java.awt.geom.*;
     import java.io.*;
     import java.net.URL;
     import java.util.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.animation.*;
     import com.croftsoft.core.animation.animator.*;
     import com.croftsoft.core.animation.clock.Timekeeper;
     import com.croftsoft.core.io.SerializableLib;
     import com.croftsoft.core.media.sound.AudioClipCache;

     import com.croftsoft.apps.mars.controller.FrameRateController;
     import com.croftsoft.apps.mars.controller.GameAnimatorController;
     import com.croftsoft.apps.mars.controller.SoundController;
     import com.croftsoft.apps.mars.controller.TankController;
     import com.croftsoft.apps.mars.controller.TimeController;
     import com.croftsoft.apps.mars.model.Game;
     import com.croftsoft.apps.mars.model.seri.SeriGame;
     import com.croftsoft.apps.mars.view.GameAnimator;

     /*********************************************************************
     * Animated tank combat game.
     *
     * @version
     *   2003-04-30
     * @since
     *   2003-01-23
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Main
       extends AnimatedApplet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  VERSION
       = "2003-04-30";

     private static final String  TITLE
       = "CroftSoft Mars";

     private static final String  APPLET_INFO
       = "\n" + TITLE + "\n"
       + "Copyright 2003 CroftSoft Inc\n"
       + "http://www.croftsoft.com/\n"
       + "Version " + VERSION + "\n"
       + "Licensed under the Academic Free License version 1.2\n";

     //////////////////////////////////////////////////////////////////////
     // frame constants
     //////////////////////////////////////////////////////////////////////

     private static final String  FRAME_TITLE
       = TITLE;

     private static final String  FRAME_ICON_FILENAME
       = "/images/croftsoft.png";
       
     private static final Dimension  FRAME_SIZE
       = null;

     private static final String  SHUTDOWN_CONFIRMATION_PROMPT
       = "Close " + TITLE + "?";

     //////////////////////////////////////////////////////////////////////
     // animation constants
     //////////////////////////////////////////////////////////////////////

     /** frames per second */
     private static final double  FRAME_RATE = 85.0;

     private static final Color   BACKGROUND_COLOR
       = new Color ( 255, 152, 109 );

     private static final Color   FOREGROUND_COLOR
       = Color.BLACK;

     private static final Font    FONT
       = new Font ( "Arioso", Font.BOLD, 10 );

     private static final Cursor  CURSOR
       = new Cursor ( Cursor.CROSSHAIR_CURSOR );

     private static final String  MEDIA_DIR = "media/mars/";

     private static final double  TIME_FACTOR_DELTA = 0.1;

     //////////////////////////////////////////////////////////////////////
     // persistence constants
     //////////////////////////////////////////////////////////////////////

     private static final String  DATA_DIR
       = ".croftsoft" + File.separator + "mars" + File.separator;

     private static final String  LATEST_FILENAME = DATA_DIR + "mars1.dat";

     private static final String  BACKUP_FILENAME = DATA_DIR + "mars2.dat";

     private static final String  FILE_CONTENTS_SPEC = "mars";

     private static final String  PERSISTENCE_KEY = FILE_CONTENTS_SPEC;

     private static final String  RESOURCE_PATH_FILENAME = null;

     //////////////////////////////////////////////////////////////////////
     // instance variables
     //////////////////////////////////////////////////////////////////////

     private UserData  userData;

     private Game      game;

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
     }

     //////////////////////////////////////////////////////////////////////
     // interface Lifecycle methods
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       super.init ( );

       // persistent data

       try
       {
         userData = ( UserData ) SerializableLib.load (
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

       if ( userData == null )
       {
         userData = new UserData ( );
       }

       // model

       game = new SeriGame ( );

       // view

       GameAnimator  gameAnimator = new GameAnimator (
         game,
         animatedComponent,
         getClass ( ).getClassLoader ( ),
         MEDIA_DIR,
         BACKGROUND_COLOR );

       addComponentAnimator ( gameAnimator );

       AudioClipCache  audioClipCache
         = gameAnimator.getAudioClipCache ( );

       audioClipCache.setMuted ( userData.isMuted ( ) );

       FrameRateAnimator  frameRateAnimator
         = new FrameRateAnimator ( animatedComponent );

       frameRateAnimator.toggle ( );

       addComponentAnimator ( frameRateAnimator );

       // controllers

       new FrameRateController (
         frameRateAnimator,
         animatedComponent );

       new GameAnimatorController (
         gameAnimator,
         animatedComponent );

       new TimeController (
         game.getTimekeeper ( ),
         game.getTimeFactorDefault ( ),
         TIME_FACTOR_DELTA,
         animatedComponent );

       new SoundController (
         audioClipCache,
         userData,
         animatedComponent );

       new TankController (
         game.getPlayerTank ( ).getTankOperator ( ),
         animatedComponent );
     }

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       game.update ( );

       super.update ( component );
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         SerializableLib.save (
           userData,
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

       super.destroy ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }