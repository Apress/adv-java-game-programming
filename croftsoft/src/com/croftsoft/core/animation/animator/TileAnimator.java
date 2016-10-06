     package com.croftsoft.core.animation.animator;

     import java.awt.*;
     import javax.swing.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.animation.painter.TilePainter;

     /*********************************************************************
     * Paints a sliding tile pattern.
     *
     * @version
     *   2003-07-05
     * @since
     *   2002-02-20
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  TileAnimator
       implements ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final TilePainter  tilePainter;

     private final int          deltaX;

     private final int          deltaY;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *********************************************************************/
     public  TileAnimator (
       TilePainter  tilePainter,
       int          deltaX,
       int          deltaY )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.tilePainter = tilePainter );

       this.deltaX = deltaX;

       this.deltaY = deltaY;

       if ( ( deltaX == 0 )
         && ( deltaY == 0 ) )
       {
         throw new IllegalArgumentException ( "deltaX and deltaY both 0" );
       }
     }

     /*********************************************************************
     * Convenience constructor.
     *********************************************************************/
     public  TileAnimator (
       int    offsetX,
       int    offsetY,
       Icon   icon,
       Shape  tileShape,
       int    deltaX,
       int    deltaY )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         new TilePainter ( offsetX, offsetY, icon, tileShape ),
         deltaX,
         deltaY );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       int  offsetX = tilePainter.getOffsetX ( );

       int  offsetY = tilePainter.getOffsetY ( );

       int  x = offsetX - deltaX;

       int  y = offsetY - deltaY;

       int  tileWidth  = tilePainter.getTileWidth ( );

       int  tileHeight = tilePainter.getTileHeight ( );

       int  tilesWide  = tilePainter.getTileColumns ( );

       int  tilesHigh  = tilePainter.getTileRows    ( );

       if ( x < 0 )
       {
         x += ( tileWidth * tilesWide );
       }
       else if ( x >= tileWidth * tilesWide )
       {
         x -= ( tileWidth * tilesWide );
       }

       if ( y < 0 )
       {
         y += ( tilesHigh * tileHeight );
       }
       else if ( y >= tilesHigh * tileHeight )
       {
         y -= ( tilesHigh * tileHeight );
       }

       tilePainter.setOffsetX ( x );

       tilePainter.setOffsetY ( y );

       component.repaint ( );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       tilePainter.paint ( component, graphics );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
