     package com.croftsoft.core.animation.icon;

     import java.awt.Component;
     import java.awt.Graphics;
     import java.awt.Graphics2D;
     import java.awt.Image;
     import java.awt.image.VolatileImage;
     import java.net.URL;
     import javax.swing.Icon;
     import javax.swing.ImageIcon;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * An Icon backed by a VolatileImage.
     *
     * @see
     *   VolatileImage
     *
     * @version
     *   2002-03-13
     * @since
     *   2002-03-13
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  VolatileImageIcon
       implements Icon
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Component  component;

     private final Image      image;

     //

     private VolatileImage  volatileImage;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  VolatileImageIcon (
       Component  component,
       Image      image )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.component = component );

       NullArgumentException.check ( this.image     = image     );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int  getIconWidth  ( ) { return image.getWidth  ( null ); }

     public int  getIconHeight ( ) { return image.getHeight ( null ); }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  paintIcon (
       Component  component,
       Graphics   graphics,
       int        x,
       int        y )
     //////////////////////////////////////////////////////////////////////
     {
       if ( ( volatileImage == null )
         || volatileImage.contentsLost ( ) )
       {
         prepareVolatileImage ( );

         if ( volatileImage.contentsLost ( ) )
         {
           graphics.drawImage ( image, x, y, null );
         }
       }

       graphics.drawImage ( volatileImage, x, y, component );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private void  prepareVolatileImage ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( ( volatileImage == null )
         || ( volatileImage.validate (
         component.getGraphicsConfiguration ( ) )
         == VolatileImage.IMAGE_INCOMPATIBLE ) )
       {
         volatileImage = component.createVolatileImage (
           image.getWidth  ( null ),
           image.getHeight ( null ) );
       }

       Graphics2D  graphics = volatileImage.createGraphics ( );

       graphics.drawImage ( image, 0, 0, component );

       graphics.dispose ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }