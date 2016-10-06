     package com.croftsoft.core.awt.image;

     import java.awt.*;
     import java.awt.image.BufferedImage;
     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.lang.ClassLib;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Caches loaded images by file name.
     *
     * @version
     *   2003-07-24
     * @since
     *   2002-02-05
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ImageCache
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Map          imageMap;

     private final int          transparency;

     private final Component    component;

     private final ClassLoader  classLoader;

     private final String       mediaDir;

     //////////////////////////////////////////////////////////////////////
     // Constructor methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *
     * @see
     *   java.util.WeakHashMap
     *
     * @param  imageMap
     *
     *   A Map of images keyed by filenames.  You may wish to consider
     *   using a WeakHashMap instead of the default HashMap created by
     *   the convenience constructor.
     *********************************************************************/
     public  ImageCache (
       int          transparency,
       Component    component,
       ClassLoader  classLoader,
       String       mediaDir,
       Map          imageMap )
     //////////////////////////////////////////////////////////////////////
     {
       this.transparency = transparency;

       this.component    = component;

       this.classLoader  = classLoader;

       this.mediaDir     = mediaDir;

       NullArgumentException.check ( this.imageMap = imageMap );
     }

     public  ImageCache (
       int          transparency,
       Component    component,
       ClassLoader  classLoader,
       String       mediaDir )
     //////////////////////////////////////////////////////////////////////
     {
       this ( transparency, component, classLoader, mediaDir,
         new HashMap ( ) );
     }

     /*********************************************************************
     * Convenience constructor.
     *********************************************************************/
     public  ImageCache ( Map  imageMap )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         0,
         ( Component   ) null,
         ( ClassLoader ) null,
         ( String      ) null,
         imageMap );
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * this(new HashMap());
     *********************************************************************/
     public  ImageCache ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new HashMap ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  clear ( )
     //////////////////////////////////////////////////////////////////////
     {
       imageMap.clear ( );
     }

     public boolean  containsKey ( String  imageFilename )
     //////////////////////////////////////////////////////////////////////
     {
       return imageMap.containsKey ( imageFilename );
     }

     public BufferedImage  get ( String  imageFilename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       BufferedImage  image
         = ( BufferedImage ) imageMap.get ( imageFilename );

       if ( ( image       == null )
         && ( component   != null )
         && ( classLoader != null ) )
       {
         image = ImageLib.loadAutomaticImage (
           mediaDir == null ? imageFilename : mediaDir + imageFilename,
           transparency,
           component,
           classLoader,
           ( Dimension ) null );

         imageMap.put ( imageFilename, image );
       }

       return image;
     }

     public Image  remove ( String  imageFilename )
     //////////////////////////////////////////////////////////////////////
     {
       return ( Image ) imageMap.remove ( imageFilename );
     }

     /*********************************************************************
     * Validates the image.
     *
     * <p>
     * Checks to see if the image is already loaded.  If not, loads the
     * file as a class resource byte stream and converts to an image.
     * If component is not null, creates a MediaTracker to wait until the
     * image is completely loaded before returning.
     * </p>
     *
     * @see
     *   java.awt.MediaTracker
     *
     * @see
     *   com.croftsoft.core.lang.ClassLib#getResourceAsImage
     *
     * @param  imageFilename
     *
     *    The image filename and cache key.  Filenames are relative to
     *    the root of the classpath or JAR file.
     *    Example:  if the classpath is "J:\lib", this method would load
     *    "/images/image.png" from "J:\lib\images\image.png".
     *
     * @param  component
     *
     *   If null, a MediaTracker will not be used to ensure load completion
     *   before returning.
     *
     * @param  maxWaitTimeMs
     *
     *   The maximum time in milliseconds to wait for an image to finish
     *   loading before returning.  Passed as the argument to
     *   MediaTracker.waitForAll().
     *********************************************************************/
     public Image  validate (
       String     imageFilename,
       Component  component,
       long       maxWaitTimeMs )
       throws IOException, InterruptedException
     //////////////////////////////////////////////////////////////////////
     {
       Image  image = ( Image ) imageMap.get ( imageFilename );

       if ( image == null )
       {
         image = ClassLib.getResourceAsImage (
           ImageCache.class, imageFilename );

         imageMap.put ( imageFilename, image );

         if ( component != null )
         {
           MediaTracker  mediaTracker = new MediaTracker ( component );

           mediaTracker.addImage ( image, 0 );

           mediaTracker.waitForAll ( maxWaitTimeMs );
         }
       }

       return image;
     }

     /*********************************************************************
     * Validates the image without waiting to ensure load completion.
     *
     * Calls <code>validate(imageFilename,null,0)</code>.
     *********************************************************************/
     public Image  validate ( String  imageFilename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       Image  image = null;

       try
       {
         image = validate ( imageFilename, null, 0 );
       }
       catch ( InterruptedException  ex )
       {
         // This code should never happen.

         ex.printStackTrace ( );
       }

       return image;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }