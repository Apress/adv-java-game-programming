     package com.croftsoft.core.animation.painter;

     import java.awt.*;
     import java.awt.geom.Rectangle2D;
     import javax.swing.*;

     import com.croftsoft.core.animation.ComponentPainter;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Tiles the Icon across the Component.
     *
     * <p>
     * Supports a palette of up to 256 different icons.
     * </p>
     *
     * @version
     *   2003-07-11
     * @since
     *   2002-02-14
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  TilePainter
       implements ComponentPainter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Shape         tileShape;

     private final int           tileWidth;

     private final int           tileHeight;

     private final Icon [ ]      tileIcons;

     private final byte [ ] [ ]  tileMap;

     private final Rectangle     clipBounds;

     private final Rectangle     originalClipBounds;

     //

     private int  offsetX;

     private int  offsetY;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *
     * @param  offsetX
     *
     *   Shifts the tile pattern horizontally.
     *   When there is only one tile icon that is being repeated,
     *   reasonable values are between 0 and the icon width less 1.
     *   Example:  values of 0 to 39 for an icon width of 40.
     *
     * @param  offsetY
     *
     *   Shifts the tile pattern vertically.
     *   When there is only one tile icon that is being repeated,
     *   reasonable values are between 0 and the icon height less 1.
     *   Example:  values of 0 to 39 for an icon height of 40.
     *
     * @param  tileShape
     *
     *   The area of the Component where the tiles will be painted.
     *   If the tileShape is null, component.getBounds() will be used.
     *********************************************************************/
     public  TilePainter ( 
       int            offsetX,
       int            offsetY,
       Icon  [ ]      tileIcons,
       byte  [ ] [ ]  tileMap,
       Dimension      tileSize,
       Shape          tileShape )
     //////////////////////////////////////////////////////////////////////
     {
       this.offsetX = offsetX;

       this.offsetY = offsetY;

       NullArgumentException.check ( this.tileIcons = tileIcons );

       NullArgumentException.check ( this.tileMap  = tileMap  );

       if ( tileIcons.length < 1 )
       {
         throw new IllegalArgumentException ( "tileIcons.length < 1" );
       }

       if ( tileIcons.length > 256 )
       {
         throw new IllegalArgumentException ( "tileIcons.length > 256" );
       }

       for ( int  i = 0; i < tileIcons.length; i++ )
       {
         if ( tileIcons [ i ] == null )
         {
           throw new IllegalArgumentException (
             "tileIcons[" + i + "] == null" );
         }
       }

       if ( tileMap.length < 1 )
       {
         throw new IllegalArgumentException ( "tileMap.length < 1" );
       }

       int  tilesWide = tileMap [ 0 ].length;

       if ( tilesWide < 1 )
       {
         throw new IllegalArgumentException ( "tileMap[0].length < 1" );
       }

       for ( int  row = 0; row < tileMap.length; row++ )
       {
         if ( tileMap [ row ].length != tilesWide )
         {
           throw new IllegalArgumentException (
             "tileMap[" + row + "].length != tileMap[0].length" );
         }

         for ( int  column = 0; column < tileMap [ row ].length; column++ )
         {
           int  paletteIndex = 0xFF & tileMap [ row ] [ column ];

           if ( paletteIndex >= tileIcons.length )
           {
             throw new IllegalArgumentException (
               "tileMap[" + row + "][" + column + "] >= tileIcons.length" );
           }
         }
       }

       if ( tileSize == null )
       {
         tileWidth  = tileIcons [ 0 ].getIconWidth  ( );

         tileHeight = tileIcons [ 0 ].getIconHeight ( );
       }
       else
       {
         tileWidth  = tileSize.width;

         tileHeight = tileSize.height;
       }

       if ( ( tileWidth  < 1 )
         || ( tileHeight < 1 ) )
       {
         throw new IllegalArgumentException (
           "tileWidth < 1 or tileHeight < 1" );
       }

       this.tileShape = tileShape;

       clipBounds = new Rectangle ( );

       originalClipBounds = new Rectangle ( );
     }

     /*********************************************************************
     * Convenience constructor.
     *********************************************************************/
     public  TilePainter (
       int    offsetX,
       int    offsetY,
       Icon   icon,
       Shape  tileShape )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         offsetX,
         offsetY,
         new Icon [ ] { icon },
         new byte [ ] [ ] { { 0 } },
         ( Dimension ) null,
         tileShape );
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * <p><code>this ( 0, 0, icon, null );</code></p>
     *********************************************************************/
     public  TilePainter ( Icon  icon )
     //////////////////////////////////////////////////////////////////////
     {
       this ( 0, 0, icon, null );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public int  getOffsetX     ( ) { return offsetX;               }

     public int  getOffsetY     ( ) { return offsetY;               }

     public int  getTileWidth   ( ) { return tileWidth;             }

     public int  getTileHeight  ( ) { return tileHeight;            }

     public int  getTileRows    ( ) { return tileMap.length;        }

     public int  getTileColumns ( ) { return tileMap [ 0 ].length;  }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setOffsetX ( int  offsetX ) { this.offsetX = offsetX; }

     public void  setOffsetY ( int  offsetY ) { this.offsetY = offsetY; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int  getTileRow ( Point  mousePoint )
     //////////////////////////////////////////////////////////////////////
     {
       int  row = floorDivision ( mousePoint.y, offsetY, tileHeight );

       row = row % getTileRows ( );

       if ( row < 0 )
       {
         row += getTileRows ( );
       }

       return row;
     }

     public int  getTileColumn ( Point  mousePoint )
     //////////////////////////////////////////////////////////////////////
     {
       int  column = floorDivision ( mousePoint.x, offsetX, tileWidth );

       column = column % getTileColumns ( );

       if ( column < 0 )
       {
         column += getTileColumns ( );
       }

       return column;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       graphics.getClipBounds ( clipBounds );

       if ( tileShape != null )
       {
         if ( !tileShape.intersects ( clipBounds ) )
         {
           return;
         }

         graphics.setClip ( tileShape );

         originalClipBounds.setBounds ( clipBounds );

         Rectangle2D.intersect (
           originalClipBounds, tileShape.getBounds2D ( ), clipBounds );
       }

       int  minX = clipBounds.x;

       int  maxX = clipBounds.x + clipBounds.width  - 1;

       int  minY = clipBounds.y;

       int  maxY = clipBounds.y + clipBounds.height - 1;

       int  minColumn = floorDivision ( minX, offsetX, tileWidth  );

       int  maxColumn = floorDivision ( maxX, offsetX, tileWidth  );

       int  minRow    = floorDivision ( minY, offsetY, tileHeight );

       int  maxRow    = floorDivision ( maxY, offsetY, tileHeight );

       int  rows    = getTileRows    ( );

       int  columns = getTileColumns ( );

       for ( int  row = minRow; row <= maxRow; row++ )
       {
         int  r = row % rows;

         if ( r < 0 )
         {
           r += rows;
         }

         byte [ ]  rowTileData = tileMap [ r ];

         for ( int  column = minColumn; column <= maxColumn; column++ )
         {
           int  c = column % columns;

           if ( c < 0 )
           {
             c += columns;
           }

           tileIcons [ 0xFF & rowTileData [ c ] ].paintIcon (
             component,
             graphics,
             column * tileWidth  + offsetX,
             row    * tileHeight + offsetY);
         }
       }

       if ( tileShape != null )
       {
         graphics.setClip ( originalClipBounds );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private static int  floorDivision (
       int  point,
       int  zero,
       int  length )
     //////////////////////////////////////////////////////////////////////
     {
       if ( point >= zero )
       {
         return ( point - zero ) / length;
       }

       return ( point - zero + 1 ) / length - 1;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
