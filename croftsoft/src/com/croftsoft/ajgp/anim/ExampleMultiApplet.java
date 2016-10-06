     package com.croftsoft.ajgp.anim;

     import java.awt.Dimension;
     import java.net.URL;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.gui.multi.MultiApplet;
     import com.croftsoft.core.lang.Pair;

     /*********************************************************************
     * An example MultiApplet implementation.
     *
     * @version
     *   2003-09-15
     * @since
     *   2002-09-15
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ExampleMultiApplet
       extends MultiApplet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  VERSION = "2003-09-15";

     private static final String  TITLE = "Example MultiApplet";

     private static final String  APPLET_INFO
       = "\n" + TITLE
       + "\n" + CroftSoftConstants.COPYRIGHT
       + "\n" + CroftSoftConstants.DEFAULT_LICENSE
       + "\n" + CroftSoftConstants.HOME_PAGE
       + "\n" + "Version " + VERSION
       + "\n";

     private static final Pair [ ]  APPLET_PAIRS = {
       new Pair (
         "Example",
         "com.croftsoft.ajgp.anim.AnimationExample" ) };

     //////////////////////////////////////////////////////////////////////
     // News panel constants
     //////////////////////////////////////////////////////////////////////

     private static final String  NEWS_NAME
       = MultiApplet.DEFAULT_NEWS_NAME;

     private static final String  NEWS_HTML
       = "<html><body><pre>" + APPLET_INFO + "</pre></body></html>";

     private static final String  NEWS_PAGE
       = "http://www.croftsoft.com/library/books/ajgp/";

     //////////////////////////////////////////////////////////////////////
     // Frame constants
     //////////////////////////////////////////////////////////////////////

     private static final String  FRAME_TITLE = TITLE;

     private static final String  FRAME_ICON_FILENAME
       = "media/frame_icon.png";

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
         ExampleMultiApplet.class.getClassLoader ( ),
         FRAME_SIZE,
         SHUTDOWN_CONFIRMATION_PROMPT );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ExampleMultiApplet ( )
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