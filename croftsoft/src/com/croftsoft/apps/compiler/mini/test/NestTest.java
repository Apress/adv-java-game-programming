     public class  NestTest
     //////////////////////////////////////////////////////////////////////
     // Start of NestTest
     //////////////////////////////////////////////////////////////////////
     {

     int  divideresult;
     
     public void  divide ( int  y, int  z )
     //////////////////////////////////////////////////////////////////////
     {
       divideresult = y / z;
     }

     int  sum2result;
     
     public void  sum2 ( int  y, int  z )
     //////////////////////////////////////////////////////////////////////
     {
       sum2result = y + z;
     }

     int  average4result;
     
     public class  average4
     //////////////////////////////////////////////////////////////////////
     // Start of inner class average4
     //////////////////////////////////////////////////////////////////////
     {

     int  sum4result;
     
     public class  sum4
     //////////////////////////////////////////////////////////////////////
     // Start of inner class sum4
     //////////////////////////////////////////////////////////////////////
     {

     int  sum3result;
     
     public void  sum3 ( int  x, int  y, int  z )
     //////////////////////////////////////////////////////////////////////
     {
       sum2 ( y, z );

       sum2 ( x, sum2result );

       sum3result = sum2result;
     }


     public void  procedure ( int  w, int  x, int  y, int  z )
     //////////////////////////////////////////////////////////////////////
     {
       sum3 ( x, y, z );

       sum2 ( w, sum3result );

       sum4result = sum2result;
     }

     //////////////////////////////////////////////////////////////////////
     // End of inner class sum4
     //////////////////////////////////////////////////////////////////////
     }


     public void  procedure ( int  w, int  x, int  y, int  z )
     //////////////////////////////////////////////////////////////////////
     {
       new sum4 ( ).procedure ( w, x, y, z );

       divide ( sum4result, 4 );

       average4result = divideresult;
     }

     //////////////////////////////////////////////////////////////////////
     // End of inner class average4
     //////////////////////////////////////////////////////////////////////
     }

     int  w, x, y, z;

     public void  procedure ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         w = Integer.parseInt ( new java.io.BufferedReader (
           new java.io.InputStreamReader ( System.in ) ).readLine ( ) );
         x = Integer.parseInt ( new java.io.BufferedReader (
           new java.io.InputStreamReader ( System.in ) ).readLine ( ) );
         y = Integer.parseInt ( new java.io.BufferedReader (
           new java.io.InputStreamReader ( System.in ) ).readLine ( ) );
         z = Integer.parseInt ( new java.io.BufferedReader (
           new java.io.InputStreamReader ( System.in ) ).readLine ( ) );
       }
       catch ( java.io.IOException  ex )
       {
         throw new RuntimeException ( "input error" );
       }

       new average4 ( ).procedure ( w, x, y, z );

       System.out.println ( average4result );
     }

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       new NestTest ( ).procedure ( );
     }

     //////////////////////////////////////////////////////////////////////
     // End of NestTest
     //////////////////////////////////////////////////////////////////////
     }
