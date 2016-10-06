     package com.croftsoft.core.animation.icon;

     import java.awt.*;
     import javax.swing.Icon;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * An Icon that draws a colored square.
     *
     * @version
     *   2003-03-17
     * @since
     *   2003-03-17
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ColorTileIcon
       implements Icon
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Color      color;

     private final Dimension  size;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ColorTileIcon (
       Color      color,
       Dimension  size )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.color = color );

       this.size = size;
     }

     public  ColorTileIcon ( Color  color )
     //////////////////////////////////////////////////////////////////////
     {
       this ( color, ( Dimension ) null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int  getIconWidth  ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( size == null )
       {
         return Integer.MAX_VALUE;
       }
       else
       {
         return size.width;
       }
     }

     public int  getIconHeight ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( size == null )
       {
         return Integer.MAX_VALUE;
       }
       else
       {
         return size.height;
       }
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
       graphics.setColor ( color );

       if ( size == null )
       {
         graphics.fillRect ( 0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE );
       }
       else
       {
         graphics.fillRect ( x, y, size.width, size.height );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }