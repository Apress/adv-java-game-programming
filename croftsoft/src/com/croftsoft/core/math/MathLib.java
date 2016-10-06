     package com.croftsoft.core.math;

     import java.awt.geom.Point2D;
     import java.util.*;

     /*********************************************************************
     * A collection of static methods to supplement java.lang.Math.
     *
     * @version
     *   2002-11-03
     * @since
     *   1998-12-27
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public final class  MathLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( test ( args ) );
     }

     public static boolean  test ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       return ( greatestCommonFactor ( 6, 15 ) == 3 )
         && ( log (   1.0, 10.0 ) ==  0.0 )
         && ( log (  10.0, 10.0 ) ==  1.0 )
         && ( log ( 100.0, 10.0 ) ==  2.0 )
         && ( log (   0.5,  2.0 ) == -1.0 )
         && ( log (   1.0,  2.0 ) ==  0.0 )
         && ( log (   2.0,  2.0 ) ==  1.0 )
         && ( log (   4.0,  2.0 ) ==  2.0 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static List  factor ( int  n )
     //////////////////////////////////////////////////////////////////////
     {
       if ( n < 0 )
       {
         throw new IllegalArgumentException ( "n < 0" );
       }

       List  primeList = new ArrayList ( );

       if ( n == 0 )
       {
         primeList.add ( new Integer ( 0 ) );

         return primeList;
       }

       if ( n == 1 )
       {
         primeList.add ( new Integer ( 1 ) );

         return primeList;
       }

       for ( int  i = 2; i <= n; i++ )
       {
         if ( n % i == 0 )
         {
           primeList.add ( new Integer ( i ) );

           n = n / i;

           i = 1;
         }
       }

       return primeList;
     }

     public static int  greatestCommonFactor (
       int  n0,
       int  n1 )
     //////////////////////////////////////////////////////////////////////
     {
       int  gcf = 1;

       List  primeList0 = factor ( n0 );

       List  primeList1 = factor ( n1 );

       Integer [ ]  primeArray0
         = ( Integer [ ] ) primeList0.toArray ( new Integer [ 0 ] );

       for ( int  i = 0; i < primeArray0.length; i++ )
       {
         Integer  j = primeArray0 [ i ];

         if ( primeList1.contains ( j ) )
         {
           gcf = gcf * j.intValue ( );

           primeList1.remove ( j );
         }
       }

       return gcf;
     }

     /*********************************************************************
     * Calculates the logarithm in the given base.
     *
     * <code><pre>return Math.log ( a ) / Math.log ( base );</pre></code>
     *********************************************************************/
     public static double  log (
       double  a,
       double  base )
     //////////////////////////////////////////////////////////////////////
     {
       return Math.log ( a ) / Math.log ( base );
     }

     /*********************************************************************
     * Also known as the "logistic function".
     *********************************************************************/
     public static double  sigmoid ( double  a )
     //////////////////////////////////////////////////////////////////////
     {
       return 1.0 / ( 1.0 + Math.exp ( -a ) );
     }

     /*********************************************************************
     * The derivative with respect to the argument.
     *********************************************************************/
     public static double  sigmoidDerivative ( double  a )
     //////////////////////////////////////////////////////////////////////
     {
       double  y = sigmoid ( a );
       return y * ( 1.0 - y );
     }

     /*********************************************************************
     * Returns +1 if positive, -1 if negative, otherwise 0.
     *********************************************************************/
     public static byte  signum ( long  l )
     //////////////////////////////////////////////////////////////////////
     {
       return ( byte ) ( l > 0 ? 1 : ( l < 0 ? -1 : 0 ) );
     }

     /*********************************************************************
     * hyperbolic tangent = 2 * sigmoid ( 2 * a ) - 1
     *********************************************************************/
     public static double  tanh ( double  a )
     //////////////////////////////////////////////////////////////////////
     {
       return 2.0 * sigmoid ( 2.0 * a ) - 1.0;
     }

     /*********************************************************************
     * Converts from polar to rectangular coordinates.
     *********************************************************************/
     public static void  toRectangular (
       double   radius,
       double   angle,
       Point2D  point2D )
     //////////////////////////////////////////////////////////////////////
     {
       point2D.setLocation (
         radius * Math.cos ( angle ),
         radius * Math.sin ( angle ) );
     }

     /*********************************************************************
     * Converts from polar to rectangular coordinates.
     *********************************************************************/
     public static Point2D  toRectangular (
       double  radius,
       double  angle )
     //////////////////////////////////////////////////////////////////////
     {
       Point2D  point2D = new Point2D.Double ( );

       toRectangular ( radius, angle, point2D );

       return point2D;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  MathLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
