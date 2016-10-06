     package com.croftsoft.core.util;

     import java.lang.ref.*;
     import java.util.*;

     /*********************************************************************
     * A Map implementation that dumps its content when memory runs low.
     *
     * <P>
     *
     * Backed by a WeakHashMap.  Note that an entry will not be garbage
     * collected if its key remains strongly reachable.
     *
     * <P>
     *
     * @see
     *   java.util.WeakHashMap
     * @see
     *   java.lang.ref.SoftReference
     *
     * @version
     *   1999-04-20
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  SoftHashMap extends AbstractMap
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private WeakHashMap     weakHashMap    = new WeakHashMap    ( );
     private ReferenceQueue  referenceQueue = new ReferenceQueue ( );
     private Set             softSet        = new HashSet        ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( test ( ) );
     }

     public static boolean  test ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         SoftHashMap  softHashMap = new SoftHashMap ( );

         softHashMap.put ( "key", "value" );

         if ( !softHashMap.remove ( "key" ).equals ( "value" )
           || ( softHashMap.size ( ) > 0 ) )
         {
           return false;
         }

         Runtime  runtime = Runtime.getRuntime ( );

         for ( int  i = 0; i < 1000000; i++ )
         {
           if ( i % 10000 == 0 )
           {
             System.out.println (
               i + ":  "
               + softHashMap.size ( )
               + " entries, "
               + runtime.freeMemory ( )
               + " / "
               + runtime.totalMemory ( )
               + " memory usage" );
           }

           Integer  value = new Integer ( i );
           softHashMap.put ( value.toString ( ), value );
         }
       }
       catch ( Throwable  t )
       {
         t.printStackTrace ( );

         return false;
       }

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Set  entrySet ( )
     //////////////////////////////////////////////////////////////////////
     {
       clearQueue ( );

       return weakHashMap.entrySet ( );
     }

     public Object  put ( Object  key, Object  value )
     //////////////////////////////////////////////////////////////////////
     {
       clearQueue ( );

       softSet.add ( new SoftReference ( key, referenceQueue ) );

       return weakHashMap.put ( key, value );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  clearQueue ( )
     //////////////////////////////////////////////////////////////////////
     {
       Reference  reference = null;
       while ( ( reference = referenceQueue.poll ( ) ) != null )
       {
         softSet.remove ( reference );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
