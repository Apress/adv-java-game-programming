     public class  Ex3
     //////////////////////////////////////////////////////////////////////
     // Start of Ex3
     //////////////////////////////////////////////////////////////////////
     {

     int  res, n, i;
     
     public void  fact ( int  n )
     //////////////////////////////////////////////////////////////////////
     {
       int  r, i;

       r = 1;

       i = 1;

       while ( i <= n )
       {
         r = r * i;

         i = i + 1;
       }

       res = r;
     }


     public void  procedure ( )
     //////////////////////////////////////////////////////////////////////
     {
       res = 0;

       i = 3;

       try
       {
         n = Integer.parseInt ( new java.io.BufferedReader (
           new java.io.InputStreamReader ( System.in ) ).readLine ( ) );
       }
       catch ( java.io.IOException  ex )
       {
         throw new RuntimeException ( "input error" );
       }

       fact ( n - 1 );

       System.out.println ( res );

       System.out.println ( i );
     }

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       new Ex3 ( ).procedure ( );
     }

     //////////////////////////////////////////////////////////////////////
     // End of Ex3
     //////////////////////////////////////////////////////////////////////
     }
