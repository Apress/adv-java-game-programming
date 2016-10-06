     package com.croftsoft.core.util;

     import java.util.*;

     /*********************************************************************
     *
     * A List that, like a Set, contains no duplicate Elements.
     * 
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1998-11-23
     *********************************************************************/

     public class  SetList implements Set, List
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected List  list;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SetList ( List  list )
     //////////////////////////////////////////////////////////////////////
     {
       this.list = list;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Returns false if the List already contains the object.
     *********************************************************************/
     public synchronized boolean add ( Object o )
     //////////////////////////////////////////////////////////////////////
     {
       if ( list.contains ( o ) ) return false;
       else return list.add ( o );
     }
       
     /*********************************************************************
     * Skips objects in the Collection that already exist in the List.
     * Returns true if any of the objects were added.
     *********************************************************************/
     public synchronized boolean addAll ( Collection  c )
     //////////////////////////////////////////////////////////////////////
     {
       boolean  result = false;
       Iterator  iterator = c.iterator ( );
       while ( iterator.hasNext ( ) )
       {
         if ( this.add ( iterator.next ( ) ) ) result = true;
       }
       return result;
     }

     /*********************************************************************
     * Skips object in the Collection that already exist in the List.
     * Returns true if any of the objects were added.
     *********************************************************************/
     public synchronized boolean addAll ( int  index, Collection  c )
     //////////////////////////////////////////////////////////////////////
     {
       boolean result = false;
       int  i = 0;
       Iterator  iterator = c.iterator ( );
       while ( iterator.hasNext ( ) )
       {
         Object  o = iterator.next ( );
         if ( !list.contains ( o ) )
         {
           list.add ( index + i, o );
           i++;
           result = true;
         }
       }
       return result;
     }

     /*********************************************************************
     * @throws IllegalArgumentException
     *   If a duplicate object already exists in the List.
     *********************************************************************/
     public synchronized Object  set ( int index, Object element )
     //////////////////////////////////////////////////////////////////////
     {
       if ( list.contains ( element ) )
       {
         throw new IllegalArgumentException ( "duplicate" );
       }
       else return list.set ( index, element );
     }

     /*********************************************************************
     * @throws IllegalArgumentException
     *   If a duplicate object already exists in the List.
     *********************************************************************/
     public synchronized void  add ( int  index, Object  element )
     //////////////////////////////////////////////////////////////////////
     {
       if ( list.contains ( element ) )
       {
         throw new IllegalArgumentException ( "duplicate" );
       }
       else list.add ( index, element );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int size() { return list.size ( ); }

     public boolean isEmpty() { return list.isEmpty ( ); }

     public boolean contains(Object o) { return list.contains ( o ); }

     public Iterator iterator() { return list.iterator ( ); }

     public Object[] toArray() { return list.toArray ( ); }

     public Object[] toArray(Object a[]) { return list.toArray ( a ); }

     public boolean remove(Object o) { return list.remove ( o ); }

     public boolean containsAll(Collection c)
       { return list.containsAll ( c ); }

     public boolean removeAll(Collection c)
       { return list.removeAll ( c ); }

     public boolean retainAll(Collection c)
       { return list.retainAll ( c ); }

     public void clear ( ) { list.clear ( ); }

     public boolean equals(Object o) { return list.equals ( o ); }

     public int hashCode ( ) { return list.hashCode ( ); }

     public Object get(int index) { return list.get ( index ); }

     public Object remove ( int index ) { return list.remove ( index ); }

     public int indexOf(Object o) { return list.indexOf ( o ); }

     public int lastIndexOf(Object o) { return list.lastIndexOf ( o ); }

     public ListIterator listIterator() { return list.listIterator ( ); }

     public ListIterator listIterator(int index)
       { return list.listIterator ( index ); }

     public List subList(int fromIndex, int toIndex)
       { return list.subList ( fromIndex, toIndex ); }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
