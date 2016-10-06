     package com.croftsoft.core.util;

     import java.util.Arrays;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Array manipulation for Java 1.2+.
     *
     * <p>
     * Java 1.2 compatible.
     * </p>
     *
     * @see
     *   ArrayLib
     *
     * @version
     *   2001-05-25
     * @since
     *   2001-05-25
     * @author
     *   <a href="http://www.alumni.caltech.edu/~croft/">David W. Croft</a>
     *********************************************************************/

     public final class  ArrayLib2
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
         String [ ]  ARRAY_ABC = new String [ ] { "a", "b", "c" };

         if ( !Arrays.equals ( ARRAY_ABC,
           insertSorted ( new String [ ] { "a", "c" }, "b", false ) ) )
         {
           return false;
         }

         if ( !Arrays.equals ( new String [ ] { "a", "b", "b", "c" },
           insertSorted ( ARRAY_ABC, "b", true ) ) )
         {
           return false;
         }

         if ( !Arrays.equals ( ARRAY_ABC,
           insertSorted ( ARRAY_ABC, "b", false ) ) )
         {
           return false;
         }

         //

         int [ ]  ARRAY_123 = new int [ ] { 1, 2, 3 };

         if ( !Arrays.equals ( ARRAY_123,
           insertSorted ( new int [ ] { 1, 3 }, 2, false ) ) )
         {
           return false;
         }

         if ( !Arrays.equals ( new int [ ] { 1, 2, 2, 3 },
           insertSorted ( ARRAY_123, 2, true ) ) )
         {
           return false;
         }

         if ( !Arrays.equals ( ARRAY_123,
           insertSorted ( ARRAY_123, 2, false ) ) )
         {
           return false;
         }

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
     * Inserts, in sort order, an integer into a presorted integer array.
     *
     * <p>
     * As this function uses Arrays.binarySearch() and ArrayLib.insert(),
     * it should be more efficient than growing a sorted array by
     * appending an element and calling Arrays.sort() each time, especially
     * as the array grows larger.
     * </p>
     *
     * @see
     *   java.util.Arrays#binarySearch(int[],int)
     *
     * @param  sortedIntArray
     *
     *   The array argument must be presorted.  If the array was built up
     *   one element at a time using this method, the array will always be
     *   in a sorted state.
     *
     *   If this parameter is null, a new integer array will be created.
     *
     * @param  comparable
     *
     *   The int value to be inserted.
     *
     * @param  allowDuplicates
     *
     *   If false, the original array argument may be returned unchanged.
     *
     * @return
     *
     *   Returns a new sorted integer array with integer argument inserted.
     *********************************************************************/
     public static int [ ]  insertSorted (
       int [ ]  sortedIntArray,
       int      i,
       boolean  allowDuplicates )
     //////////////////////////////////////////////////////////////////////
     {
       if ( sortedIntArray == null )
       {
         return new int [ ] { i };
       }

       int  index = Arrays.binarySearch ( sortedIntArray, i );

       if ( index >= 0 )
       {
         if ( allowDuplicates )
         {
           return ArrayLib.insert ( sortedIntArray, i, index );
         }
         else
         {
           return sortedIntArray;
         }
       }

       return ArrayLib.insert ( sortedIntArray, i, -index - 1 );
     }

     /*********************************************************************
     * Inserts, in sort order, a Comparable into a presorted array.
     *
     * <p>
     * As this function uses Arrays.binarySearch(), it should be more
     * efficient than growing a sorted array by appending an element and
     * calling Arrays.sort() each time, especially as the array grows
     * larger.
     * </p>
     *
     * @see
     *   java.util.Arrays#binarySearch(java.lang.Object[],java.lang.Object)
     *
     * @param  sortedComparableArray
     *
     *   The array argument must be presorted.  If the array was built up
     *   one element at a time using this method, the array will always be
     *   in a sorted state.
     *
     * @param  comparable
     *
     *   The Comparable object to be inserted.
     *
     * @param  allowDuplicates
     *
     *   If false, the original array argument may be returned unchanged.
     *
     * @throws NullArgumentException
     *
     *   If sortedComparableArray or comparable is null.
     *
     * @return
     *
     *   Returns an array with the same component type as the old array.
     *********************************************************************/
     public static Comparable [ ]  insertSorted (
       Comparable [ ]  sortedComparableArray,
       Comparable      comparable,
       boolean         allowDuplicates )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( sortedComparableArray );

       NullArgumentException.check ( comparable );

       int  index
         = Arrays.binarySearch ( sortedComparableArray, comparable );

       if ( index >= 0 )
       {
         if ( allowDuplicates )
         {
           return ( Comparable [ ] )
             ArrayLib.insert ( sortedComparableArray, comparable, index );
         }
         else
         {
           return sortedComparableArray;
         }
       }

       return ( Comparable [ ] )
         ArrayLib.insert ( sortedComparableArray, comparable, -index - 1 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  ArrayLib2 ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
