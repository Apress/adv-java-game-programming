     package com.croftsoft.core.animation.icon;

     import java.awt.*;
     import java.awt.image.BufferedImage;
     import java.io.*;
     import javax.swing.Icon;

     import com.croftsoft.core.awt.image.ImageCache;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * An Icon that uses an ImageCache.
     *
     * @version
     *   2003-03-17
     * @since
     *   2003-03-17
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ImageCacheIcon
       implements Icon
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final ImageCache  imageCache;

     private final String      imageFilename;

     //

     private BufferedImage  bufferedImage;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ImageCacheIcon (
       ImageCache  imageCache,
       String      imageFilename )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.imageCache    = imageCache    );

       NullArgumentException.check ( this.imageFilename = imageFilename );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int  getIconWidth  ( )
     //////////////////////////////////////////////////////////////////////
     {
       return getBufferedImage ( ).getWidth ( );
     }

     public int  getIconHeight ( )
     //////////////////////////////////////////////////////////////////////
     {
       return getBufferedImage ( ).getHeight ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  paintIcon (
       Component  component,
       Graphics   graphics,
       int        x,
       int        y )
     //////////////////////////////////////////////////////////////////////
     {
       graphics.drawImage ( getBufferedImage ( ), x, y, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private BufferedImage  getBufferedImage ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( bufferedImage == null )
       {
         try
         {
           bufferedImage = imageCache.get ( imageFilename );
         }
         catch ( IOException  ex )
         {
           throw new RuntimeException ( ex );
         }
       }

       return bufferedImage;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }