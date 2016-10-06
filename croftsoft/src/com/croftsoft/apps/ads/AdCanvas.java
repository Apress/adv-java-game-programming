     package com.croftsoft.apps.ads;

     import java.applet.Applet;
     import java.awt.*;
     import java.io.*;
     import java.net.*;
     import java.util.*;

     /*********************************************************************
     * Rotating ad banner canvas.
     *
     * Allows rotating ad banners to be integrated into both Java applets
     * and applications.
     *
     * @version
     *   1999-03-14
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public final class  AdCanvas extends Canvas implements Runnable {
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /**
     *
     * Modify to redirect control of what ads are displayed.
     *
     * Example:  "/mydata/ads.data"
     *
     * The link is relative to the CodeBase although this may be modified
     * as well.
     *
     */
     private static final String    ADS_DATA
       = "http://www.orbs.com/advertising/data/Ads.data";

     /**
     *
     * How long each image is displayed, in milliseconds.
     *
     */
     private static final long      AIR_TIME = 15000;

     private static       Random    random = new Random ( );
     private static       boolean   data_loaded = false;
     private static       Vector    imageURL_StringVector = new Vector ( );
     private static       Vector    site_URL_StringVector = new Vector ( );
     private              Graphics  g;
     private              Dimension size;
     private              Image     offscreenImage;
     private              Graphics  offscreenGraphics;
     private              Thread    thread;
     private              int       index;
     private              Applet    applet;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AdCanvas ( Applet  applet ) {
     //////////////////////////////////////////////////////////////////////
       this.applet = applet;
     }

     public void  start ( ) {
     //////////////////////////////////////////////////////////////////////
       g = getGraphics ( );
       size = size ( );
       offscreenImage = createImage ( size.width, size.height );
       offscreenGraphics = offscreenImage.getGraphics ( );
       if ( data_load ( applet ) ) ( thread = new Thread ( this ) ).start ( );
     }

     private synchronized static boolean  data_load ( Applet  applet ) {
     //////////////////////////////////////////////////////////////////////
       if ( data_loaded ) return true;
       try {
         URL  dataURL = new URL ( applet.getCodeBase ( ), ADS_DATA );
         InputStream  conn = dataURL.openStream ( );
         DataInputStream  data = new DataInputStream ( conn );
         while ( true ) {
           String  line = data.readLine ( );
           if ( line == null ) break;
           imageURL_StringVector.addElement ( line );
           site_URL_StringVector.addElement ( data.readLine ( ) );
           if ( data.readLine ( ) == null ) break;
         }
       }
       catch ( Exception  e ) {
         e.printStackTrace ( );
         applet.showStatus ( e.getMessage ( ) );
         return false;
       }
       data_loaded = true;
       return true;
     }

     public void  run ( ) {
     //////////////////////////////////////////////////////////////////////
       int  count = imageURL_StringVector.size ( );
       while ( true ) {
         int [ ]  random_order = random_order_list ( count );
         for ( int  i = 0; i < count; i++ ) {
           index = random_order [ i ];
           try {
             Image  image = applet.getImage ( new URL (
               ( String ) imageURL_StringVector.elementAt ( index ) ) );
             if ( prepareImage ( image, size.width, size.height, this ) ) {
               offscreenGraphics.drawImage (
                 image, 0, 0, size.width, size.height, this );
               paint ( g );
             }
           } catch ( Exception  e ) {
             e.printStackTrace ( );
             applet.showStatus ( e.getMessage ( ) );
           }
           try { thread.sleep ( AIR_TIME ); }
           catch ( InterruptedException  e ) { }
         }
       }
     }

     public boolean  imageUpdate (
       Image  image, int  flags, int  x, int  y, int  w, int  h ) {
     //////////////////////////////////////////////////////////////////////
       if ( flags == 32 ) {
         offscreenGraphics.drawImage (
           image, 0, 0, size.width, size.height, this );
         paint ( g );
       }
       return super.imageUpdate ( image, flags, x, y, w, h );
     }

     public boolean  mouseMove ( Event evt, int x, int y ) {
     //////////////////////////////////////////////////////////////////////
       if ( site_URL_StringVector == null ) return true;
       if ( site_URL_StringVector.size ( ) <= index ) return true;
       applet.showStatus (
         ( String ) site_URL_StringVector.elementAt ( index ) );
       return true;
     }

     public boolean  mouseUp ( Event  evt, int  x, int  y ) {
     //////////////////////////////////////////////////////////////////////
       if ( site_URL_StringVector == null ) return true;
       if ( site_URL_StringVector.size ( ) <= index ) return true;
       try {
         applet.getAppletContext ( ).showDocument ( new URL (
           ( String ) site_URL_StringVector.elementAt ( index ) ) );
       } catch ( MalformedURLException  e ) {
         e.printStackTrace ( );
         applet.showStatus ( e.getMessage ( ) );
       }
       return true;
     }

     public void  paint ( Graphics  graphics ) {
     //////////////////////////////////////////////////////////////////////
       if ( offscreenImage == null ) return;
       graphics.drawImage ( offscreenImage, 0, 0, this );
     }

     public void  stop ( ) {
     //////////////////////////////////////////////////////////////////////
       if ( thread != null ) {
         thread.stop ( );
         thread = null;
       }
     }

     public void  update ( Graphics  graphics ) {
     //////////////////////////////////////////////////////////////////////
       paint ( graphics );
     }

     public void  repaint ( ) {
     //////////////////////////////////////////////////////////////////////
       paint ( g );
     }

     public static int [ ]  random_order_list ( int  count ) {
     //////////////////////////////////////////////////////////////////////
       Vector  vector = new Vector ( count );
       for ( int  i = 0; i < count; i++ ) {
         vector.addElement ( new Integer ( i ) );
       }
       int [ ]  random_order = new int [ count ];
       for ( int  i = 0; i < count; i++ ) {
         Integer  lucky = ( Integer ) vector.elementAt (
           ( int ) roll ( 1, count - i, -1 ) );
         vector.removeElement ( lucky );
         random_order [ i ] = lucky.intValue ( );
       }
       return random_order;
     }

     public static long roll (
       long multiplier,
       long base,
       long offset ) {
     //////////////////////////////////////////////////////////////////////
       long temp = 0;
     //////////////////////////////////////////////////////////////////////
       for ( long index_roll = 0; index_roll < multiplier; index_roll++ ) {
         temp += 1 + Math.round (
           ( double ) ( base - 1 ) * random.nextDouble ( ) );
       }
       return temp + offset;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
