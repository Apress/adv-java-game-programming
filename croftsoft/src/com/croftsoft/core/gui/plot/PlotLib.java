     package com.croftsoft.core.gui.plot;

     import java.awt.*;
     import java.lang.Math;

     /*********************************************************************
     * Plots points on an XY chart.
     *
     * @version
     *   2002-02-28
     * @since
     *   1998-12-27
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  PlotLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  line (
       double     m,
       double     b,
       Rectangle  r,
       Graphics   g,
       double     x0,
       double     x1,
       double     y0,
       double     y1 )
     //////////////////////////////////////////////////////////////////////
     {
       double  x_scale = ( double ) r.width  / ( double ) ( x1 - x0 );
       double  y_scale = ( double ) r.height / ( double ) ( y1 - y0 );

       Rectangle  r_old = g.getClipBounds ( );
       g.clipRect ( r.x, r.y, r.width, r.height );
       g.drawLine ( r.x,
         r.y + r.height - ( int ) ( ( m * x0 + b - y0 ) * y_scale ),
         r.x + r.width ,
         r.y + r.height - ( int ) ( ( m * x1 + b - y0 ) * y_scale ) );
       g.clipRect ( r_old.x, r_old.y, r_old.width, r_old.height );
     }

     public static Point  graphics_to_plot_transform (
       Point      gPoint,
       Rectangle  r,
       Graphics   g,
       double     axis_x0,
       double     axis_x1,
       double     axis_y0,
       double     axis_y1 )
     //////////////////////////////////////////////////////////////////////
     {
       double  x_scale
         = ( double ) r.width  / ( double ) ( axis_x1 - axis_x0 );
       double  y_scale
         = ( double ) r.height / ( double ) ( axis_y1 - axis_y0 );
       return new Point ( 
         ( int ) ( axis_x0 + ( gPoint.x - r.x            ) / x_scale ),
         ( int ) ( axis_y0 - ( gPoint.y - r.y - r.height ) / y_scale ) );
     }

     public static Point  plot_to_graphics_transform (
       Point      plot_Point,
       Rectangle  r,
       Graphics   g,
       double     axis_x0,
       double     axis_x1,
       double     axis_y0,
       double     axis_y1 )
     //////////////////////////////////////////////////////////////////////
     {
       double  x_scale
         = ( double ) r.width  / ( double ) ( axis_x1 - axis_x0 );
       double  y_scale
         = ( double ) r.height / ( double ) ( axis_y1 - axis_y0 );
       return new Point (
         r.x +            ( int ) ( ( plot_Point.x - axis_x0 ) * x_scale ),
         r.y + r.height - ( int ) ( ( plot_Point.y - axis_y0 ) * y_scale ) );
     }

     public static void  xy (
       Color      c,
       double     x,
       double     y,
       Rectangle  r,
       Graphics   g,
       double     x0,
       double     x1,
       double     y0,
       double     y1,
       int        oval_size )
     //////////////////////////////////////////////////////////////////////
     {
       double  x_scale = ( double ) r.width  / ( double ) ( x1 - x0 );

       double  y_scale = ( double ) r.height / ( double ) ( y1 - y0 );

       Color  c_old = g.getColor ( );

       g.setColor ( c );

       g.fillOval ( r.x + ( int ) ( ( x - x0 ) * x_scale - oval_size / 2 ),
         r.y + r.height - ( int ) ( ( y - y0 ) * y_scale + oval_size / 2 ),
         oval_size, oval_size );

       g.setColor ( c_old );
     }

     public static void  xy (
       Color      c,
       double     x,
       double     y,
       Rectangle  r,
       Graphics   g,
       double     x0,
       double     x1,
       double     y0,
       double     y1,
       int        ovalSizeMin,
       boolean    scaleOvalSize )
     //////////////////////////////////////////////////////////////////////
     {
       if ( scaleOvalSize )
       {
         double  x_scale = ( double ) r.width  / ( double ) ( x1 - x0 );

         double  y_scale = ( double ) r.height / ( double ) ( y1 - y0 );

         ovalSizeMin = ( int )
           Math.max ( Math.min ( x_scale, y_scale ), ovalSizeMin );
       }

       xy ( c, x, y, r, g, x0, x1, y0, y1, ovalSizeMin );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  PlotLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
