     package com.croftsoft.apps.midi;
     
     import java.applet.*;
     import java.awt.*;
     import java.awt.event.*;
     import java.awt.geom.*;
     import java.io.*;
     import java.net.URL;
     import java.util.*;
     import javax.sound.midi.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.animation.*;
     import com.croftsoft.core.animation.animator.*;
     import com.croftsoft.core.animation.painter.*;
     import com.croftsoft.core.io.SerializableLib;
     import com.croftsoft.core.jnlp.JnlpLib;
     import com.croftsoft.core.lang.lifecycle.*;

     /*********************************************************************
     * MIDI demo.
     *
     * @version
     *   2003-04-12
     * @since
     *   2003-04-12
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Main
       extends AnimatedApplet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  VERSION
       = "2003-04-12";

     private static final String  TITLE
       = "CroftSoft MIDI";

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
     private static final double  FRAME_RATE = 30.0;

     private static final Color   BACKGROUND_COLOR
       = Color.BLACK;

     private static final Color   FOREGROUND_COLOR
       = Color.BLUE;

     private static final Font    FONT
       = new Font ( "Arioso", Font.BOLD, 20 );

     private static final Cursor  CURSOR
       = new Cursor ( Cursor.CROSSHAIR_CURSOR );

     private static final long  DELAY = 2000;

     private static int  DELTA_X = 1;

     private static int  DELTA_Y = 1;

     private static String  SOUNDBANK_URL_NAME
       = "http://java.sun.com/products/java-media/sound/soundbanks.html";

     //////////////////////////////////////////////////////////////////////
     // persistence constants
     //////////////////////////////////////////////////////////////////////


     //////////////////////////////////////////////////////////////////////
     // instance variables
     //////////////////////////////////////////////////////////////////////

     private Sequencer        sequencer;

     private Synthesizer      synthesizer;

     private Instrument  [ ]  instruments;

     private MidiChannel [ ]  midiChannels;

     private int              noteNumber;

     private long             previousTime;

     private boolean          noteOn;

     private TextAnimator     textAnimator;

     private Random           random;

     private boolean          started;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       if ( args.length < 1 )
       {
         launch ( new Main ( ) );

         return;
       }

       // Test of playing a MIDI file using AudioClip

       AudioClip  audioClip = Applet.newAudioClip (
         Main.class.getClassLoader ( ).getResource ( args [ 0 ] ) );

       audioClip.loop ( );
     }

     private static AnimationInit  createAnimationInit ( )
     //////////////////////////////////////////////////////////////////////
     {
       AnimationInit  animationInit = new AnimationInit ( );

       animationInit.setAppletInfo ( APPLET_INFO );

       animationInit.setForegroundColor ( BACKGROUND_COLOR );

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

/*
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
*/
       addComponentPainter ( new ColorPainter ( BACKGROUND_COLOR ) );

       textAnimator = new TextAnimator ( );

       textAnimator.setText ( TITLE );

       textAnimator.setDeltaX ( DELTA_X );

       textAnimator.setDeltaY ( DELTA_Y );

       addComponentAnimator ( textAnimator );

       random = new Random ( );

       try
       {
         sequencer = MidiSystem.getSequencer ( );

         synthesizer = MidiSystem.getSynthesizer ( );

         synthesizer.open ( );

         Soundbank  soundbank = synthesizer.getDefaultSoundbank ( );

         if ( soundbank != null )
         {
           System.out.println ( "Using soundbank instruments..." );

           instruments = soundbank.getInstruments ( );
         }
         else
         {
           System.out.println ( "Using synthesizer instruments..." );

           instruments = synthesizer.getAvailableInstruments ( );
         }

         midiChannels = synthesizer.getChannels ( );

         if ( instruments.length < 1 )
         {
           textAnimator.setText ( SOUNDBANK_URL_NAME );

           URL  soundbankURL = new URL ( SOUNDBANK_URL_NAME );

           try
           {
             getAppletContext ( ).showDocument ( soundbankURL, "_blank" );
           }
           catch ( Exception  ex )
           {
             try
             {
               JnlpLib.showDocument ( soundbankURL );
             }
             catch ( Exception  ex2 )
             {
             }
           }
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         throw new InitializationException ( ex );
       }
     }

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       super.update ( component );

       if ( !started
        || instruments.length < 1 )
       {
         return;
       }

       if ( noteOn )
       {
         long  currentTime = System.currentTimeMillis ( );

         if ( previousTime + DELAY >= currentTime )
         {
           return;
         }

         previousTime = currentTime;

         midiChannels [ 0 ].noteOff ( noteNumber );

         noteOn = false;
       }
       else
       {
         noteNumber = random.nextInt ( 128 );

         int  instrumentIndex = random.nextInt ( instruments.length );

         Instrument  instrument = instruments [ instrumentIndex ];

         textAnimator.setText (
           instrument.getName ( ) + " " + noteNumber );

         synthesizer.loadInstrument ( instrument );

         midiChannels [ 0 ].programChange ( instrumentIndex );

         int  velocity = 64;

         midiChannels [ 0 ].noteOn ( noteNumber, velocity );

         noteOn = true;
       }
     }

     public void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       started = true;

       super.start ( );
     }

     public void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       started = false;

       if ( noteOn )
       {
         midiChannels [ 0 ].noteOff ( noteNumber );

         noteOn = false;
       }

       super.stop ( );
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       synthesizer.close ( );

       sequencer.close ( );

/*
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
*/

       super.destroy ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }