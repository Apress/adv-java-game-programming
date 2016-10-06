     package com.croftsoft.core.awt.image;

     import java.awt.Graphics;
     import java.awt.Graphics2D;
     import java.awt.GraphicsConfiguration;
     import java.awt.ImageCapabilities;
     import java.awt.image.ImageObserver;
     import java.awt.image.ImageProducer;
     import java.awt.image.BufferedImage;
     import java.awt.image.VolatileImage;

     /*********************************************************************
     * A Null Object implementation of VolatileImage.
     *
     * <p>
     * Use wherever a placeholder VolatileImage or Image is needed.
     * </p>
     *
     * @version
     *   2002-12-18
     * @since
     *   2002-12-18
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  NullVolatileImage
       extends VolatileImage
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final NullVolatileImage  INSTANCE
       = new NullVolatileImage ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  contentsLost ( )
     //////////////////////////////////////////////////////////////////////
     {
       return true;
     }

     public Graphics2D  createGraphics ( )
     //////////////////////////////////////////////////////////////////////
     {
       return null;
     }

     public void  flush ( )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public ImageCapabilities  getCapabilities ( )
     //////////////////////////////////////////////////////////////////////
     {
       return null;
     }

     public Graphics  getGraphics ( )
     //////////////////////////////////////////////////////////////////////
     {
       return null;
     }

     public int  getHeight ( )
     //////////////////////////////////////////////////////////////////////
     {
       return 0;
     }

     public int  getHeight ( ImageObserver  imageObserver )
     //////////////////////////////////////////////////////////////////////
     {
       return 0;
     }

     public Object  getProperty (
       String         name,
       ImageObserver  imageObserver )
     //////////////////////////////////////////////////////////////////////
     {
       return null;
     }

     public BufferedImage  getSnapshot ( )
     //////////////////////////////////////////////////////////////////////
     {
       return null;
     }

     public ImageProducer  getSource ( )
     //////////////////////////////////////////////////////////////////////
     {
       return null;
     }

     public int  getWidth ( )
     //////////////////////////////////////////////////////////////////////
     {
       return 0;
     }

     public int  getWidth ( ImageObserver  imageObserver )
     //////////////////////////////////////////////////////////////////////
     {
       return 0;
     }

     public int  validate ( GraphicsConfiguration  gc )
     //////////////////////////////////////////////////////////////////////
     {
       return VolatileImage.IMAGE_INCOMPATIBLE;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  NullVolatileImage ( ) { };

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
