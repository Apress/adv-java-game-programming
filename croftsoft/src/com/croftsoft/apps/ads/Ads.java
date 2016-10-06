     package com.croftsoft.apps.ads;

     import java.applet.Applet;
     import java.awt.*;
     import java.io.*;
     import java.net.*;
     import java.util.*;

     /*********************************************************************
     * Rotating ad banner applet.
     *
     * @version
     *   2001-07-18
     * @since
     *   1997
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Ads extends Applet implements Runnable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  APPLET_TITLE
       = "Ads \u00A9 2001 David Wallace Croft";

     private static final String  APPLET_INFO
       = "\n" + APPLET_TITLE + "\n"
       + "croft@alumni.caltech.edu\n"
       + "http://www.alumni.caltech.edu/~croft/\n"
       + "Version 2001-07-18\n";

     private static final String  PARAM_DATA_FILENAME = "dataFilename";

     /**
     * Modify to redirect control of what ads are displayed.
     *
     * The link is relative to the CodeBase although this may be modified
     * as well.
     */
     private static final String  DEFAULT_DATA_FILENAME = "ads.dat";

     /**
     * How long each image is displayed, in milliseconds.
     */
     private static final long    AIR_TIME = 15000;

     //

     private static Random  random = new Random ( );

     //

     private String     dataFilename;

     private Vector     imageURL_StringVector;

     private Vector     site_URL_StringVector;

     private Hashtable  imageUrlStringToImageHashtable;

     private Graphics   g;

     private Dimension  size;

     private Image      offscreenImage;

     private Graphics   offscreenGraphics;

     private boolean    shouldRun;

     private Thread     thread;

     private int        index;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getAppletInfo ( )
     //////////////////////////////////////////////////////////////////////
     {
       return APPLET_INFO;
     }    

     public synchronized void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       setCursor ( Cursor.getPredefinedCursor ( Cursor.HAND_CURSOR ) );

       imageURL_StringVector = new Vector ( );

       site_URL_StringVector = new Vector ( );

       imageUrlStringToImageHashtable = new Hashtable ( );

       dataFilename = getParameter ( PARAM_DATA_FILENAME );

       if ( dataFilename == null )
       {
         dataFilename = DEFAULT_DATA_FILENAME;
       }

       loadData ( );
     }

     public synchronized void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( g != null )
       {
         g.dispose ( );
       }

       if ( offscreenGraphics != null )
       {
         offscreenGraphics.dispose ( );
       }
     }

     public synchronized void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       g = getGraphics ( );

       size = size ( );

       offscreenImage = createImage ( size.width, size.height );

       offscreenGraphics = offscreenImage.getGraphics ( );

       shouldRun = true;

       ( thread = new Thread ( this ) ).start ( );
     }

     public synchronized void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       shouldRun = false;

       if ( thread != null )
       {
         thread.interrupt ( );
       }
     }

     public void  run ( )
     //////////////////////////////////////////////////////////////////////
     {
       int  count = imageURL_StringVector.size ( );

       while ( shouldRun )
       {
         int [ ]  random_order = random_order_list ( count );

         for ( int  i = 0; shouldRun && i < count; i++ )
         {
           index = random_order [ i ];

           try
           {
             String  imageUrlString
               = ( String ) imageURL_StringVector.elementAt ( index );

             Image  image = ( Image )
               imageUrlStringToImageHashtable.get ( imageUrlString );

             if ( image == null )
             {
               image = getImage (
                 new URL ( getCodeBase ( ), imageUrlString ) );

               imageUrlStringToImageHashtable.put ( imageUrlString, image );
             }

             if ( prepareImage ( image, size.width, size.height, this ) )
             {
               offscreenGraphics.drawImage (
                 image, 0, 0, size.width, size.height, this );

               paint ( g );
             }
           }
           catch ( Exception  e )
           {
             e.printStackTrace ( );

             showStatus ( e.getMessage ( ) );
           }

           try
           {
             thread.sleep ( AIR_TIME );
           }
           catch ( InterruptedException  e ) { }
         }
       }
     }

     public boolean  imageUpdate (
       Image  image, int  flags, int  x, int  y, int  w, int  h )
     //////////////////////////////////////////////////////////////////////
     {
       if ( flags == 32 )
       {
         offscreenGraphics.drawImage (
           image, 0, 0, size.width, size.height, this );

         paint ( g );
       }

       return super.imageUpdate ( image, flags, x, y, w, h );
     }

     public boolean  mouseMove ( Event evt, int x, int y )
     //////////////////////////////////////////////////////////////////////
     {
       if ( site_URL_StringVector == null ) return true;

       if ( site_URL_StringVector.size ( ) <= index ) return true;

       showStatus ( ( String ) site_URL_StringVector.elementAt ( index ) );

       return true;
     }

     public boolean  mouseUp ( Event  evt, int  x, int  y )
     //////////////////////////////////////////////////////////////////////
     {
       if ( site_URL_StringVector == null ) return true;

       if ( site_URL_StringVector.size ( ) <= index ) return true;

       try
       {
         getAppletContext ( ).showDocument ( new URL (
           ( String ) site_URL_StringVector.elementAt ( index ) ) );

       }
       catch ( MalformedURLException  e )
       {
         e.printStackTrace ( );

         showStatus ( e.getMessage ( ) );
       }

       return true;
     }

     public void  paint ( Graphics  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       if ( offscreenImage == null ) return;

       graphics.drawImage ( offscreenImage, 0, 0, this );
     }

     public void  update ( Graphics  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       paint ( graphics );
     }

     public void  repaint ( )
     //////////////////////////////////////////////////////////////////////
     {
       paint ( g );
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private synchronized boolean  loadData ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         URL  dataURL = new URL ( getCodeBase ( ), dataFilename );

         InputStream  conn = dataURL.openStream ( );

         DataInputStream  data = new DataInputStream ( conn );

         while ( true )
         {
           String  line = data.readLine ( );

           if ( line == null ) break;

           imageURL_StringVector.addElement ( line );

           site_URL_StringVector.addElement ( data.readLine ( ) );

           if ( data.readLine ( ) == null ) break;
         }
       }
       catch ( Exception  e )
       {
         e.printStackTrace ( );

         showStatus ( e.getMessage ( ) );

         return false;
       }

       return true;
     }

     private static int [ ]  random_order_list ( int  count ) {
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

     private static long roll (
       long multiplier,
       long base,
       long offset )
     //////////////////////////////////////////////////////////////////////
     {
       long temp = 0;

       for ( long index_roll = 0; index_roll < multiplier; index_roll++ )
       {
         temp += 1 + Math.round (
           ( double ) ( base - 1 ) * random.nextDouble ( ) );
       }

       return temp + offset;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
