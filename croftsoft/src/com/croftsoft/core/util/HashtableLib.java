     package com.croftsoft.core.util;

     import java.util.*;

     /*********************************************************************
     *
     * Static method library to manipulate Hashtable objects.
     *
     * @version
     *   1998-10-04
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  HashtableLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private  HashtableLib ( ) { }

     /*********************************************************************
     * Creates a Hashtable of just the new and updated elements.
     * <P>
     * Returns a Hashtable containing those elements in the new
     * Hashtable that were not in the old Hashtable plus those elements
     * in new Hashtable that were in the old Hashtable but with different
     * values.
     * <P>
     * Assumes that the Hashtable value objects have overridden
     * their equals() method for comparison.
     *
     * @return
     *   Key and value objects returned are shared from the newHashtable.
     *   Returns null if newHashtable is null.
     *   Returns a shallow clone of newHashtable if oldHashtable is null.
     *********************************************************************/
     public static Hashtable  hashtableDelta (
       Hashtable  oldHashtable, Hashtable  newHashtable )
     //////////////////////////////////////////////////////////////////////
     {
       if ( newHashtable == null ) return null;
       if ( oldHashtable == null )
       {
         return ( Hashtable ) newHashtable.clone ( );
       }

       Hashtable  deltaHashtable = new Hashtable ( );

       Enumeration  e = newHashtable.keys ( );
       while ( e.hasMoreElements ( ) )
       {
         Object  key = e.nextElement ( );
         Object  value = oldHashtable.get ( key );
         if ( ( value == null )
           || !value.equals ( newHashtable.get ( key ) ) )
         {
           deltaHashtable.put ( key, newHashtable.get ( key ) );
         }
       }

       return deltaHashtable;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
