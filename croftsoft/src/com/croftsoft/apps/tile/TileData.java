     package com.croftsoft.apps.tile;

     import java.awt.image.BufferedImage;
     import java.io.*;
     import java.net.URL;
     import java.util.Random;

     import com.croftsoft.core.awt.image.ImageLib;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.util.ArrayLib;
     
     /*********************************************************************
     * 2D tile data.
     *
     * @version
     *   2003-08-11
     * @since
     *   2003-03-08
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  TileData
       implements Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 1L;

     //

     private final int  [ ]      palette;

     private final byte [ ] [ ]  tileMap;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  check (
       int  [ ]      palette,
       byte [ ] [ ]  tileMap )
       throws IllegalArgumentException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( palette );

       NullArgumentException.check ( tileMap );

       if ( palette.length < 1 )
       {
         throw new IllegalArgumentException ( "palette length < 1" );
       }

       if ( palette.length > 256 )
       {
         throw new IllegalArgumentException ( "palentte length > 256" );
       }

       int  rows = tileMap.length;

       if ( rows < 1 )
       {
         throw new IllegalArgumentException ( "rows < 1" );
       }

       int  columns = tileMap [ 0 ].length;

       if ( columns < 1 )
       {
         throw new IllegalArgumentException ( "columns < 1" );
       }

       for ( int  row = 1; row < rows; row++ )
       {
         if ( tileMap [ row ].length != columns )
         {
           throw new IllegalArgumentException ( "tileMap not square" );
         }
       }
     }

     public static int [ ]  generateRandomPalette (
       Random   random,
       int      paletteLength,
       boolean  permitTransparent )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( random );

       if ( paletteLength < 0 )
       {
         throw new IllegalArgumentException ( "paletteLength < 0" );
       }

       if ( paletteLength > 256 )
       {
         throw new IllegalArgumentException ( "paletteLength > 256" );
       }

       int [ ]  palette = new int [ paletteLength ];

       if ( permitTransparent )
       {
         for ( int  i = 0; i < palette.length; i++ )
         {
           palette [ i ] = random.nextInt ( );
         }
       }
       else
       {
         for ( int  i = 0; i < palette.length; i++ )
         {
           palette [ i ] = 0xFF000000 | random.nextInt ( );
         }
       }

       return palette;
     }

     public static int [ ]  generateRandomPalette (
       Random   random,
       int      paletteLength )
     //////////////////////////////////////////////////////////////////////
     {
       return generateRandomPalette ( random, paletteLength, false );
     }

     public static byte [ ] [ ]  generateRandomTileMap (
       Random   random,
       int [ ]  palette,
       int      rows,
       int      columns,
       int      smoothingLoops )
     //////////////////////////////////////////////////////////////////////
     {
       byte [ ] [ ]  tileMap = new byte [ rows ] [ columns ];

       for ( int  row = 0; row < rows; row++ )
       {
         for ( int  column = 0; column < columns; column++ )
         {
           int  paletteIndex = random.nextInt ( palette.length );

           tileMap [ row ] [ column ] = ( byte ) paletteIndex;
         }
       }

       for ( int  i = 0; i < smoothingLoops; i++ )
       {
         for ( int  row = 0; row < rows; row++ )
         {
           for ( int  column = 0; column < columns; column++ )
           {
             int  left  = column > 0 ? column - 1 : columns - 1;

             int  right = column < columns - 1 ? column + 1 : 0;

             int  up    = row > 0 ? row - 1 : rows - 1;

             int  down  = row < rows - 1 ? row + 1 : 0;

             byte [ ]  neighbors = {
               tileMap [ row  ] [ column ],   // center
               tileMap [ row  ] [ left   ],   // west
               tileMap [ row  ] [ right  ],   // east
               tileMap [ up   ] [ column ],   // north
               tileMap [ up   ] [ left   ],   // north west
               tileMap [ up   ] [ right  ],   // north east
               tileMap [ down ] [ column ],   // south
               tileMap [ down ] [ left   ],   // south west
               tileMap [ down ] [ right  ] }; // south east

             tileMap [ row ] [ column ]
               = neighbors [ random.nextInt ( neighbors.length ) ];
           }
         }
       }

       return tileMap;
     }

     public static void  remapToPalette (
       int  [ ]      palette,
       byte [ ] [ ]  tileMap,
       int           defaultPaletteIndex )
     //////////////////////////////////////////////////////////////////////
     {
       check ( palette, tileMap );

       for ( int  row = 0; row < tileMap.length; row++ )
       {
         for ( int  column = 0; column < tileMap [ 0 ].length; column++ )
         {
           int  paletteIndex = 0xFF & tileMap [ row ] [ column ];

           if ( ( paletteIndex < 0               )
             || ( paletteIndex >= palette.length ) )
           {
             tileMap [ row ] [ column ] = ( byte ) defaultPaletteIndex;
           }
         }
       }
     }
       
     public static TileData  loadTileDataFromImage (
       String       filename,
       ClassLoader  classLoader )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       BufferedImage  bufferedImage
         = ImageLib.loadBufferedImage ( filename, classLoader );

       int  rows    = bufferedImage.getHeight ( );

       int  columns = bufferedImage.getWidth  ( );

       int [ ]  palette = new int [ 0 ];

       byte [ ] [ ]  tileMap = new byte [ rows ] [ columns ];

       for ( int  row = 0; row < rows; row++ )
       {
         for ( int  column = 0; column < columns; column++ )
         {
           int  rgb = bufferedImage.getRGB ( column, row );

           boolean  inPalette = false;

           for ( int  i = 0; i < palette.length; i++ )
           {
             if ( palette [ i ] == rgb )
             {
               tileMap [ row ] [ column ] = ( byte ) i;

               inPalette = true;

               break;
             }
           }

           if ( !inPalette )
           {
             tileMap [ row ] [ column ] = ( byte ) palette.length;

             palette = ( int [ ] ) ArrayLib.append ( palette, rgb );
           }         
         }
       }

       return new TileData ( palette, tileMap );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  TileData (
       int  [ ]      palette,
       byte [ ] [ ]  tileMap )
     //////////////////////////////////////////////////////////////////////
     {
       check ( palette, tileMap );

       this.palette = palette;

       this.tileMap = tileMap;
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public int  [ ]      getPalette ( ) { return palette; }

     public byte [ ] [ ]  getTileMap ( ) { return tileMap; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
