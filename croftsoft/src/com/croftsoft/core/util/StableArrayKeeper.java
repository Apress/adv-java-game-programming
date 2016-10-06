     package com.croftsoft.core.util;

     import java.io.Serializable;
     import java.lang.reflect.Array;
     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Testable;

     /*********************************************************************
     * Optimized for reading from a stable array.
     *
     * @version
     *   2003-04-14
     * @since
     *   2003-04-11
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  StableArrayKeeper
       implements ArrayKeeper, Serializable, Testable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private Class       baseClass;

     private Object [ ]  array;

     //

     private transient Map  classToArrayMap;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( test ( args ) );
     }

     public static boolean  test ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         ArrayKeeper  arrayKeeper = new StableArrayKeeper ( );

         arrayKeeper.insert ( "c" );

         arrayKeeper.insert ( "b" );

         arrayKeeper.insert ( "a" );

         ArrayLib.println ( arrayKeeper.getArray ( ) );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         return false;
       }

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  StableArrayKeeper ( Object [ ]  array )
     //////////////////////////////////////////////////////////////////////
     {
       setArray ( array );
     }

     public  StableArrayKeeper ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new Object [ 0 ] );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public Object [ ]  getArray ( )
     //////////////////////////////////////////////////////////////////////
     {
       return array;
     }

     public Object [ ]  getArray ( Class  c )
     //////////////////////////////////////////////////////////////////////
     {
       if ( baseClass.equals ( c ) )
       {
         return array;
       }

       NullArgumentException.check ( c );

       if ( classToArrayMap == null )
       {
         classToArrayMap = new HashMap ( );
       }

       Object [ ]  cArray = ( Object [ ] ) classToArrayMap.get ( c );

       if ( cArray != null )
       {
         return cArray;
       }

       cArray = ( Object [ ] ) Array.newInstance ( c, 0 );

       for ( int  i = 0; i < array.length; i++ )
       {
         if ( c.isInstance ( array [ i ] ) )
         {
           cArray = ( Object [ ] ) ArrayLib.append ( cArray, array [ i ] );
         }
       }

       classToArrayMap.put ( c, cArray );

       return cArray;
     }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  append ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       setArray ( ( Object [ ] ) ArrayLib.append ( array, o ) );
     }

     public void  insert ( Comparable  comparable )
     //////////////////////////////////////////////////////////////////////
     {
       append ( comparable );

       Arrays.sort ( array );
     }

     public void  remove ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       setArray ( ( Object [ ] ) ArrayLib.remove ( array, o ) );
     }

     public void  setArray ( Object [ ]  array )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.array = array );

       baseClass = array.getClass ( );

       classToArrayMap = null;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }