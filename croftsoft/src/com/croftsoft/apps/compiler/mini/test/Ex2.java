     public class  Ex2
     //////////////////////////////////////////////////////////////////////
     // Start of Ex2
     //////////////////////////////////////////////////////////////////////
     {

     int  x, y, i;

     public void  procedure ( )
     //////////////////////////////////////////////////////////////////////
     {
       y = 0;

       i = 1;

       try
       {
         x = Integer.parseInt ( new java.io.BufferedReader (
           new java.io.InputStreamReader ( System.in ) ).readLine ( ) );
       }
       catch ( java.io.IOException  ex )
       {
         throw new RuntimeException ( "input error" );
       }

       if ( x > 1 )
       {
         int  count_0 = x;
         for ( int  index_1 = 0; index_1 < count_0; index_1++ )
         {
           y = y + i;

           i = i + 1;
         }
       }

       System.out.println ( y );
     }

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       new Ex2 ( ).procedure ( );
     }

     //////////////////////////////////////////////////////////////////////
     // End of Ex2
     //////////////////////////////////////////////////////////////////////
     }
