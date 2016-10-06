     package com.croftsoft.core.awt;

     import java.awt.*;

     /*********************************************************************
     * <P>
     * Supplementary static methods for the java.awt.Graphics class.
     * <P>
     * @version
     *   1997-04-16
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public final class  GraphicsLib {
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Coughs a hairball if the targetPoint and the originPoint are equal.
     *********************************************************************/
     public static Point [ ]  rasterize (
       Point  originPoint,
       Point  targetPoint ) {
     //////////////////////////////////////////////////////////////////////
       int  delta_x = targetPoint.x - originPoint.x;
       int  delta_y = targetPoint.y - originPoint.y;
//     if ( ( delta_x == 0 ) && ( delta_y == 0 ) ) return null;
       Point [ ]  array;
       int  step = -1;
       int  y = originPoint.y;
       int  num_y = delta_y > 0 ? delta_y : -delta_y;
       if ( delta_x == 0 ) {
         array = new Point [ num_y ];
         if ( targetPoint.y > originPoint.y ) step = 1;
         for ( int  i = 0; i < array.length; i++ ) {
           y += step;
           array [ i ] = new Point ( originPoint.x, y );
         }
         return array;
       }
       int  x = originPoint.x;
       int  num_x = delta_x > 0 ? delta_x : -delta_x;
       if ( delta_y == 0 ) {
         array = new Point [ num_x ];
         if ( targetPoint.x > originPoint.x ) step = 1;
         for ( int  i = 0; i < array.length; i++ ) {
           x += step;
           array [ i ] = new Point ( x, originPoint.y );
         }
         return array;
       }
       double  m = ( ( double ) delta_y ) / ( ( double ) delta_x );
       double  b = y - m * x;
       if ( num_y > num_x ) {
         array = new Point [ num_y ];
         if ( targetPoint.y > originPoint.y ) step = 1;
         for ( int  i = 0; i < array.length; i++ ) {
           y += step;
           double  x_double = ( y - b ) / m;
           if ( x_double > x + 0.5 ) x++;
           else if ( x_double < x - 0.5 ) x--;
           array [ i ] = new Point ( x, y );
         }
       } else {
         array = new Point [ num_x ];
         if ( targetPoint.x > originPoint.x ) step = 1;
         for ( int  i = 0; i < array.length; i++ ) {
           x += step;
           double  y_double = m * x + b;
           if ( y_double > y + 0.5 ) y++;
           else if ( y_double < y - 0.5 ) y--;
           array [ i ] = new Point ( x, y );
         }
       }
       return array;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
