     package com.croftsoft.apps.collection;

     import java.awt.Dimension;
     import java.net.URL;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.gui.multi.MultiApplet;
     import com.croftsoft.core.lang.Pair;

     /*********************************************************************
     * A collection of CroftSoft programs.
     *
     * @version
     *   2003-10-06
     * @since
     *   2002-02-25
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  CroftSoftCollection
       extends MultiApplet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  VERSION = "2003-10-06";

     private static final String  TITLE = "CroftSoft Collection";

     private static final String  APPLET_INFO
       = "\n" + TITLE
       + "\n" + "Version " + VERSION
       + "\n" + CroftSoftConstants.COPYRIGHT
       + "\n" + CroftSoftConstants.DEFAULT_LICENSE
       + "\n" + CroftSoftConstants.HOME_PAGE
       + "\n";

     private static final Pair [ ]  APPLET_PAIRS = {
       new Pair (
         "BackpropXOR",
         "com.croftsoft.apps.backpropxor.BackpropXor" ),
       new Pair (
         "Basics",
         "com.croftsoft.ajgp.basics.BasicsExample" ),
       new Pair (
         "Clock",
         "com.croftsoft.apps.clock.DigitalClock" ),
       new Pair (
         "Color",
         "com.croftsoft.apps.color.ColorTest" ),
       new Pair (
         "Dice",
         "com.croftsoft.apps.dice.Dice" ),
       new Pair (
         "Dodger",
         "com.croftsoft.apps.dodger.Dodger" ),
       new Pair (
         "Evolve",
         "com.croftsoft.apps.evolve.Evolve" ),
       new Pair (
         "Fraction",
         "com.croftsoft.apps.fraction.Fraction" ),
       new Pair (
         "Insight",
         "com.croftsoft.apps.insight.Insight" ),
       new Pair (
         "Mars",
         "com.croftsoft.apps.mars.Main" ),
       new Pair (
         "MIDI",
         "com.croftsoft.apps.midi.Main" ),
       new Pair (
         "Rainbow",
         "com.croftsoft.apps.rainbow.Rainbow" ),
       new Pair (
         "Roadrunner",
         "com.croftsoft.apps.road.Main" ),
       new Pair (
         "Scribble",
         "com.croftsoft.apps.scribble.Scribble" ),
       new Pair (
         "Shooter",
         "com.croftsoft.apps.shooter.Shooter" ),
       new Pair (
         "Sprite",
         "com.croftsoft.apps.sprite.SpriteDemo" ),
       new Pair (
         "Tile",
         "com.croftsoft.apps.tile.Tile" ),
       new Pair (
         "Zombie",
         "com.croftsoft.apps.zombie.Zombie" ) };

     //////////////////////////////////////////////////////////////////////
     // News panel constants
     //////////////////////////////////////////////////////////////////////

     private static final String  NEWS_NAME
       = MultiApplet.DEFAULT_NEWS_NAME;

     private static final String  NEWS_HTML
       = "<html><body><pre>" + APPLET_INFO + "</pre></body></html>";

     private static final String  NEWS_PAGE
       = "http://www.croftsoft.com/portfolio/collection/news/";

     //////////////////////////////////////////////////////////////////////
     // Frame constants
     //////////////////////////////////////////////////////////////////////

     private static final String  FRAME_TITLE = TITLE;

     private static final String  FRAME_ICON_FILENAME
       = CroftSoftConstants.FRAME_ICON_FILENAME;

     private static final Dimension  FRAME_SIZE = null;

     private static final String  SHUTDOWN_CONFIRMATION_PROMPT
       = "Close " + TITLE + "?";

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( APPLET_INFO );

       MultiApplet.launch (
         APPLET_INFO,
         APPLET_PAIRS,
         NEWS_NAME,
         NEWS_HTML,
         NEWS_PAGE,
         FRAME_TITLE,
         FRAME_ICON_FILENAME,
         CroftSoftCollection.class.getClassLoader ( ),
         FRAME_SIZE,
         SHUTDOWN_CONFIRMATION_PROMPT );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  CroftSoftCollection ( )
     //////////////////////////////////////////////////////////////////////
     {
       super ( 
         APPLET_INFO,
         APPLET_PAIRS,
         NEWS_NAME,
         NEWS_HTML,
         NEWS_PAGE );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }