     package com.croftsoft.core.animation.icon;

     import java.awt.*;
     import java.awt.image.*;
     import java.net.URL;
     import javax.swing.*;

     import com.croftsoft.core.awt.image.NullVolatileImage;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * When contents lost, reloads the VolatileImage from the ClassLoader.
     *
     * <p>
     * This class only works with opaque (non-transparent) images.
     * </p>
     *
     * <p>
     * Example code:
     * </p>
     * <pre>
     * Icon  heroIcon = new ResourceImageIcon (
     *   HERO_IMAGE_FILENAME,
     *   getClass ( ).getClassLoader ( ),
     *   new Dimension (
     *     ( int ) scalingRatio * HERO_IMAGE_WIDTH,
     *     ( int ) scalingRatio * HERO_IMAGE_HEIGHT ),
     *   animatedComponent );
     * </pre>
     *
     * @see
     *   VolatileImage
     *
     * @version
     *   2003-02-18
     * @since
     *   2003-02-18
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ResourceImageIcon
       implements Icon
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final String       imageFilename;

     private final ClassLoader  classLoader;

     //

     private VolatileImage  volatileImage;

     private boolean        scaleImage;

     private int            width;

     private int            height;

     private boolean        resized;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *
     * @param  imageFilename
     *
     *   The image path and filename, usually pulled from a JAR.
     *
     * @param  classLoader
     *
     *   The ClassLoader to use to load the image as a resource file.
     *
     * @param  scaleDimension
     *
     *   If not null, used to pre-scale the VolatileImage.
     *
     * @param  component
     *
     *   If not null, prepareVolatileImage() is called during construction.
     *********************************************************************/
     public  ResourceImageIcon (
       String       imageFilename,
       ClassLoader  classLoader,
       Dimension    scaleDimension,
       Component    component )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.imageFilename = imageFilename );

       NullArgumentException.check ( this.classLoader   = classLoader   );

       if ( scaleDimension != null )
       {
         setSize ( scaleDimension );
       }

       if ( component == null )
       {
         volatileImage = NullVolatileImage.INSTANCE;
       }
       else
       {
         prepareVolatileImage ( component );
       }
     }

     public  ResourceImageIcon ( String  imageFilename )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         imageFilename,
         ResourceImageIcon.class.getClassLoader ( ),
         ( Dimension ) null,
         ( Component ) null );       
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int  getIconWidth  ( ) { return width;  }

     public int  getIconHeight ( ) { return height; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * If VolatileImage contents are lost, calls prepareVolatileImage().
     *********************************************************************/
     public void  paintIcon (
       Component  component,
       Graphics   graphics,
       int        x,
       int        y )
     //////////////////////////////////////////////////////////////////////
     {
       if ( resized
         || volatileImage.contentsLost ( ) )
       {
         prepareVolatileImage ( component );
       }

       graphics.drawImage ( volatileImage, x, y, component );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Loads the VolatileImage.
     *
     * <p>
     * Call this method to load the image after construction but before
     * the first call to paint().  This is useful if you cannot perform
     * this operation when the main constructor is called because the
     * Component is not ready at the time and you do not want to wait
     * until the first paint() because the load may stall animation.
     * </p>
     *
     * <p>
     * Example code:
     * </p>
     *
     * <pre>
     * ResourceImageIcon  heroIcon = new ResourceImageIcon (
     *   HERO_IMAGE_FILENAME,
     *   getClass ( ).getClassLoader ( ),
     *   ( Dimension ) null, // no scaling
     *   ( Component ) null );
     *
     * [...wait until Component is ready...]
     *
     * heroIcon.prepareVolatileImage ( animatedComponent );
     * </pre>
     *********************************************************************/
     public void  prepareVolatileImage ( Component  component )
     //////////////////////////////////////////////////////////////////////
     {
       URL  imageURL
         = classLoader.getResource ( imageFilename );

       ImageIcon  imageIcon = new ImageIcon ( imageURL );

       Image  imageIconImage = imageIcon.getImage ( );

       if ( !scaleImage )
       {
         width  = imageIcon.getIconWidth  ( );

         height = imageIcon.getIconHeight ( );
       }

       if ( volatileImage != null )
       {
         volatileImage.flush ( );
       }

       volatileImage = component.createVolatileImage ( width, height );

       Graphics2D  graphics = volatileImage.createGraphics ( );

       if ( scaleImage )
       {
         graphics.drawImage (
           imageIconImage, 0, 0, width, height, null );
       }
       else
       {
         graphics.drawImage ( imageIconImage, 0, 0, null );
       }

       imageIconImage.flush ( );

       graphics.dispose ( );
     }

     public void  setWidth ( int  width )
     //////////////////////////////////////////////////////////////////////
     {
       if ( width < 1 )
       {
         throw new IllegalArgumentException ( "width < 1:  " + width );
       }

       scaleImage = true;

       resized    = true;

       this.width = width;
     }

     public void  setHeight ( int  height )
     //////////////////////////////////////////////////////////////////////
     {
       if ( height < 1 )
       {
         throw new IllegalArgumentException ( "height < 1:  " + height );
       }

       scaleImage = true;

       resized    = true;

       this.height = height;
     }

     public void  setSize ( Dimension  size )
     //////////////////////////////////////////////////////////////////////
     {
       setWidth  ( size.width  );

       setHeight ( size.height );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }