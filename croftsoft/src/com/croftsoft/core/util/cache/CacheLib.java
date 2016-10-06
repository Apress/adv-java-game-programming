     package com.croftsoft.core.util.cache;

     import java.io.*;

     import com.croftsoft.core.util.id.Id;
     import com.croftsoft.core.util.id.IntId;

     /*********************************************************************
     * Static methods to support Cache implementations.
     *
     * @see
     *   Cache
     *
     * @version
     *   1999-04-20
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  CacheLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private  CacheLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     // Test methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Tests Cache implementations.
     *
     * <PRE>
     *
     * if ( args.length < 1 )
     * {
     *   System.out.println ( test ( ) );
     * }
     * else
     * {
     *   System.out.println ( test ( args ) );
     * }
     *
     * </PRE>
     *
     * @param  args
     *   Names of Cache classes.
     *********************************************************************/
/*
     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       if ( args.length < 1 )
       {
         System.out.println ( test ( ) );
       }
       else
       {
         System.out.println ( test ( args ) );
       }
     }
*/

     /*********************************************************************
     * Tests the Cache implementations provided in this package.
     *********************************************************************/
/*
     public static boolean  test ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         return test ( new SoftCache ( ) )
           &&   test ( new WeakCache ( ) );
       }
       catch ( Throwable  t )
       {
         t.printStackTrace ( );
         return false;
       }
     }
*/

     /*********************************************************************
     * Tests Cache implementations.
     *
     * @param  cacheClassNames
     *   Names of Cache classes.
     *********************************************************************/
/*
     public static boolean  test ( String [ ]  cacheClassNames )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         boolean  result = true;

         for ( int  i = 0; i < cacheClassNames.length; i++ )
         {
           Class  c = Class.forName ( cacheClassNames [ i ] );
           Cache  cache = ( Cache ) c.newInstance ( );
           result = test ( cache ) && result;
           if ( !result ) break;
         }

         return result;
       }
       catch ( Throwable  t )
       {
         t.printStackTrace ( );
         return false;
       }
     }
*/

     /*********************************************************************
     * Tests a Cache implementation.
     *
     * @param  cache
     *   An instance of a Cache to be tested.
     *********************************************************************/
/*
     public static boolean  test ( Cache  cache )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         Runtime  runtime = Runtime.getRuntime ( );

         Id  id0 = null;

         for ( int  index = 0; index < 1000; index++ )
         {
           // System.gc ( );

           if ( index % 10 == 0 )
           {
             System.out.println (
               index + "-:  "
               + runtime.freeMemory ( )
               + " / "
               + runtime.totalMemory ( ) );
           }

           String  s = Integer.toString ( index );
           Id  id = new IntegerId ( index );
           id = ( Id ) storeString ( cache, s, id ).clone ( );

           if ( index == 0 )
           {
             id0 = id;
           }

           if ( index % 10 == 0 )
           {
             System.out.println (
               index + "+:  "
               + runtime.freeMemory ( )
               + " / "
               + runtime.totalMemory ( )
               + " "
               + retrieveString ( cache, id0 ) );
           }

           if ( !s.equals ( retrieveString ( cache, id ) ) )
           {
             return false;
           }
         }

         return true;
       }
       catch ( Throwable  t )
       {
         t.printStackTrace ( );
         return false;
       }
     }
*/

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static Id  storeString ( Cache  cache, String  s )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return cache.store ( toInputStream ( s ) );
     }

     public static String  retrieveString ( Cache  cache, Id  id )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return toString ( cache.retrieve ( id ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static byte [ ]  toByteArray ( InputStream  in )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       ByteArrayOutputStream  out = null;

       try
       {
         out = new ByteArrayOutputStream ( );

         int  i;
         while ( ( i = in.read ( ) ) > -1 ) out.write ( i );

         return out.toByteArray ( );
       }
       finally
       {
         try { in.close  ( ); } catch ( Exception  ex ) { }
         try { out.close ( ); } catch ( Exception  ex ) { }
       }
     }

     public static InputStream  toInputStream ( String  s )
     //////////////////////////////////////////////////////////////////////
     {
       return toInputStream ( s.getBytes ( ) );
     }

     public static InputStream  toInputStream ( byte [ ]  byteArray )
     //////////////////////////////////////////////////////////////////////
     {
       return new ByteArrayInputStream ( byteArray );
     }

     public static String  toString ( InputStream  in ) throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       if ( in == null ) return null;

       ByteArrayOutputStream  out = new ByteArrayOutputStream ( );
       int  i;
       while ( ( i = in.read ( ) ) > -1 ) out.write ( i );

       return new String ( out.toByteArray ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
