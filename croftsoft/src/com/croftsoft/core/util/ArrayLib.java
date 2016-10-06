     package com.croftsoft.core.util;

     import java.lang.reflect.Array;
     import java.util.Enumeration;
     import java.util.Hashtable;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.ObjectLib;

     /*********************************************************************
     * Array manipulation for Java 1.1+.
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     *
     * @see
     *   ArrayLib2
     *
     * @version
     *   2003-04-07
     * @since
     *   2001-04-06
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ArrayLib
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
       try
       {
         insert ( new int [ ] { }, 0, 0 );

         String [ ]  stringArray
           = ( String [ ] ) append ( new String [ ] { }, "" );

         stringArray
           = ( String [ ] ) insert ( new String [ ] { }, "", 0 );

         stringArray
           = ( String [ ] ) remove ( new String [ ] { "" }, 0 );

         return true;
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         return false;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Appends an Object to an Object array.
     *
     * <p>
     * Example:
     * <code>
     * <pre>
     * String [ ]  stringArray
     *   = ( String [ ] ) ArrayLib.append ( new String [ ] { }, "" );
     * </pre>
     * </code>
     * </p>
     *
     * @throws NullArgumentException
     *
     *   If either argument is null.
     *
     * @return
     *
     *   Returns a new array with the same component type as the old array.
     *********************************************************************/
     public static Object [ ]  append ( Object [ ]  oldArray, Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( oldArray );

       NullArgumentException.check ( o );

       Object [ ]  newArray = ( Object [ ] ) Array.newInstance (
         oldArray.getClass ( ).getComponentType ( ), oldArray.length + 1 );

       System.arraycopy ( oldArray, 0, newArray, 0, oldArray.length );

       newArray [ oldArray.length ] = o;

       return newArray;
     }

     /*********************************************************************
     * Appends an integer to an integer array.
     *
     * @param  intArray
     *
     *   May be null.
     *********************************************************************/
     public static int [ ]  append ( int [ ]  intArray, int  i )
     //////////////////////////////////////////////////////////////////////
     {
       if ( intArray == null )
       {
         return new int [ ] { i };
       }

       int  intArrayLength = intArray.length;

       int [ ]  newIntArray = new int [ intArrayLength + 1 ];

       System.arraycopy ( intArray, 0, newIntArray, 0, intArrayLength );

       newIntArray [ intArrayLength ] = i;

       return newIntArray;
     }

     /*********************************************************************
     * Determines if an array contains an equivalent object.
     *
     * @param  objectArray
     *
     *   May not be null.
     *
     * @param  o
     *
     *   If null, function will return true if an array element is null.
     *********************************************************************/
     public static boolean  contains (
       Object [ ]  objectArray,
       Object      o )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( objectArray );

       for ( int  i = 0; i < objectArray.length; i++ )
       {
         if ( ObjectLib.equivalent ( objectArray [ i ], o ) )
         {
           return true;
         }
       }

       return false;
     }

     /*********************************************************************
     * Compares two object arrays for equivalency.
     *
     * <p>
     * A Java 1.1 version of the Java 1.2 method java.util.Arrays.equals().
     * </p>
     *********************************************************************/
     public static boolean  equals (
       Object [ ]  objectArray1,
       Object [ ]  objectArray2 )
     //////////////////////////////////////////////////////////////////////
     {
       if ( objectArray1 == null )
       {
         return objectArray2 == null;
       }
       else if ( objectArray2 == null )
       {
         return false;
       }

       if ( objectArray1.length != objectArray2.length )
       {
         return false;
       }

       for ( int  i = 0; i < objectArray1.length; i++ )
       {
         Object  element1 = objectArray1 [ i ];

         Object  element2 = objectArray2 [ i ];

         if ( element1 == null )
         {
           if ( element2 != null )
           {
             return false;
           }
         }
         else if ( !element1.equals ( element2 ) )
         {
           return false;         
         }
       }

       return true;
     }

     /*********************************************************************
     * Inserts an integer into an integer array at the index position.
     *********************************************************************/
     public static int [ ]  insert (
       int [ ]  intArray,
       int      i,
       int      index )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( intArray );

       if ( ( index < 0               )
         || ( index > intArray.length ) )
       {
         throw new IllegalArgumentException (
           "index out of range:  " + index );
       }

       int  intArrayLength = intArray.length;

       int [ ]  newIntArray = new int [ intArrayLength + 1 ];

       System.arraycopy ( intArray, 0, newIntArray, 0, index );

       newIntArray [ index ] = i;

       System.arraycopy (
         intArray, index, newIntArray, index + 1, intArrayLength - index );

       return newIntArray;
     }

     /*********************************************************************
     * Inserts an Object into an Object array at the index position.
     *
     * <p>
     * Example:
     * <code>
     * <pre>
     * String [ ]  stringArray
     *   = ( String [ ] ) ArrayLib.insert ( new String [ ] { }, "", 0 );
     * </pre>
     * </code>
     * </p>
     *
     * @throws NullArgumentException
     *
     *   If objectArray or o is null.
     *
     * @throws IndexOutOfBoundsException
     *
     *   If index < 0 or index > objectArray.length.
     *
     * @return
     *
     *   Returns a new array with the same component type as the old array.
     *********************************************************************/
     public static Object [ ]  insert (
       Object [ ]  objectArray,
       Object      o,
       int         index )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( objectArray );

       NullArgumentException.check ( o );

       if ( ( index < 0                  )
         || ( index > objectArray.length ) )
       {
         throw new IndexOutOfBoundsException (
           "index out of range:  " + index );
       }

       Object [ ]  newObjectArray = ( Object [ ] ) Array.newInstance (
         objectArray.getClass ( ).getComponentType ( ),
         objectArray.length + 1 );


       System.arraycopy ( objectArray, 0, newObjectArray, 0, index );

       newObjectArray [ index ] = o;

       System.arraycopy ( objectArray, index, newObjectArray, index + 1,
         objectArray.length - index );

       return newObjectArray;
     }

     /*********************************************************************
     * Prepends an Object to an Object array.
     *
     * <p>
     * Example:
     * <code>
     * <pre>
     * String [ ]  stringArray
     *   = ( String [ ] ) ArrayLib.prepend ( new String [ ] { }, "" );
     * </pre>
     * </code>
     * </p>
     *
     * @throws NullArgumentException
     *
     *   If either argument is null.
     *
     * @return
     *
     *   Returns a new array with the same component type as the old array.
     *********************************************************************/
     public static Object [ ]  prepend ( Object [ ]  oldArray, Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( oldArray );

       NullArgumentException.check ( o );

       Object [ ]  newArray = ( Object [ ] ) Array.newInstance (
         oldArray.getClass ( ).getComponentType ( ), oldArray.length + 1 );

       System.arraycopy ( oldArray, 0, newArray, 1, oldArray.length );

       newArray [ 0 ] = o;

       return newArray;
     }

     /*********************************************************************
     * Prints each array element to the standard output.
     *********************************************************************/
     public static void  println ( Object [ ]  objectArray )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < objectArray.length; i++ )
       {
         System.out.println ( objectArray [ i ] );
       }
     }

     /*********************************************************************
     * Removes an Object from an Object array.
     *
     * <p>
     * Example:
     * <code>
     * <pre>
     * String [ ]  stringArray
     *   = ( String [ ] ) remove ( new String [ ] { "" }, 0 );
     * </pre>
     * </code>
     * </p>
     *
     * @throws NullArgumentException
     *
     *   If oldArray is null.
     *
     * @throws ArrayIndexOutOfBoundsException
     *
     *   If index < 0 or index >= oldArray.length.
     *
     * @return
     *
     *   Returns a new array with the same component type as the old array.
     *********************************************************************/
     public static Object [ ]  remove ( Object [ ]  oldArray, int  index )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( oldArray );

       if ( ( index < 0 )
         || ( index >= oldArray.length ) )
       {
         throw new ArrayIndexOutOfBoundsException ( index );
       }

       Object [ ]  newArray = ( Object [ ] ) Array.newInstance (
         oldArray.getClass ( ).getComponentType ( ), oldArray.length - 1 );

       System.arraycopy ( oldArray, 0, newArray, 0, index );

       System.arraycopy (
         oldArray, index + 1, newArray, index, newArray.length - index );

       return newArray;
     }

     public static Object [ ]  remove ( Object [ ]  oldArray, Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( oldArray );

       int  index = -1;

       for ( int  i = 0; i < oldArray.length; i++ )
       {
         if ( oldArray [ i ] == o )
         {
           index = i;

           break;
         }
       }

       if ( index > -1 )
       {
         return remove ( oldArray, index );
       }

       return oldArray;
     }

     /*********************************************************************
     * Removes duplicate elements from the array.
     *********************************************************************/
     public static Object [ ]  removeDuplicates ( Object [ ]  array )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( array );

       Hashtable  hashtable = new Hashtable ( );

       for ( int  i = 0; i < array.length; i++ )
       {
         hashtable.put ( array [ i ], array [ i ] );
       }

       Object [ ]  newArray = ( Object [ ] ) Array.newInstance (
         array.getClass ( ).getComponentType ( ), hashtable.size ( ) );

       int  index = 0;

       Enumeration  enumeration = hashtable.elements ( );

       while ( enumeration.hasMoreElements ( ) )
       {
         newArray [ index++ ] = enumeration.nextElement ( );
       }

       return newArray;
     }

     /*********************************************************************
     * Creates a new subarray from a larger array.
     *
     * <p>
     * To avoid unnecessary object creation, this method returns the
     * original array argument if the requested subarray length is the same
     * and the startIndex is 0.  That is to say, if the method arguments
     * are such that the algorithm would have created a shallow clone, the
     * original array is returned instead.
     * </p>
     *
     * @throws NullArgumentException
     *
     *   If objectArray is null.
     *
     * @throws ArrayIndexOutOfBoundsException
     *
     *   If startIndex, length, or startIndex + length are out of range.
     *
     * @return
     *
     *   Returns an array with the same component type as the old array.
     *********************************************************************/
     public static Object [ ]  subArray (
       Object [ ]  objectArray,
       int         startIndex,
       int         length )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( objectArray );

       if ( ( startIndex == 0 )
         && ( length == objectArray.length ) )
       {
         return objectArray;
       }

       Object [ ]  newArray = ( Object [ ] ) Array.newInstance (
         objectArray.getClass ( ).getComponentType ( ), length );

       System.arraycopy ( objectArray, startIndex, newArray, 0, length );

       return newArray;
     }

     /*********************************************************************
     * Creates a new subarray from a larger array.
     *
     * <p>
     * <code>
     * <pre>
     * return subArray (
     *   objectArray, startIndex, objectArray.length - startIndex );
     * </pre>
     * </code>
     * </p>
     *********************************************************************/
     public static Object [ ]  subArray (
       Object [ ]  objectArray,
       int         startIndex )
     //////////////////////////////////////////////////////////////////////
     {
       return subArray (
         objectArray, startIndex, objectArray.length - startIndex );
     }

     /*********************************************************************
     * Returns the union of the arrays, discarding duplicates.
     *********************************************************************/
     public static Object [ ]  union (
       Object [ ]  array1,
       Object [ ]  array2 )
     //////////////////////////////////////////////////////////////////////
     {
       if ( array1 == null )
       {
         if ( array2 == null )
         {
           return null;
         }
         else
         {
           return removeDuplicates ( array2 );
         }
       }
       else if ( array2 == null )
       {
         return removeDuplicates ( array1 );
       }

       Class  componentType1 = array1.getClass ( ).getComponentType ( );

       Class  componentType2 = array2.getClass ( ).getComponentType ( );

       if ( componentType1 != componentType2 )
       {
         throw new IllegalArgumentException (
           "arrays of different component types" );
       }

       Hashtable  hashtable = new Hashtable ( );

       for ( int  i = 0; i < array1.length; i++ )
       {
         hashtable.put ( array1 [ i ], array1 [ i ] );
       }

       for ( int  i = 0; i < array2.length; i++ )
       {
         hashtable.put ( array2 [ i ], array2 [ i ] );
       }

       Object [ ]  array = ( Object [ ] )
         Array.newInstance ( componentType1, hashtable.size ( ) );

       int  index = 0;

       Enumeration  enumeration = hashtable.elements ( );

       while ( enumeration.hasMoreElements ( ) )
       {
         array [ index++ ] = enumeration.nextElement ( );
       }

       return array;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  ArrayLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }