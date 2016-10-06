     package com.croftsoft.agoracast.c2p;

     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Sorts AgoracastData based upon comparison of a field value.
     *
     * <p />
     *
     * @version
     *   2001-08-12
     * @since
     *   2001-08-09
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastComparator
       implements Comparator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final AgoracastModel  agoracastModel;

     //

     private String          sortField;

     private boolean         reverse;

     private AgoracastField  agoracastField;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *********************************************************************/
     public  AgoracastComparator (
       AgoracastModel  agoracastModel,
       String          sortField,
       boolean         reverse )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.agoracastModel = agoracastModel );

       setSortField ( sortField );

       this.reverse = reverse;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * The field name on which the AgoracastData are to be sorted.
     *********************************************************************/
     public void  setSortField ( String  sortField )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.sortField = sortField );

       agoracastField = agoracastModel.getAgoracastField ( sortField );
     }

     /*********************************************************************
     * Reverses the sort order.
     *********************************************************************/
     public void  setReverse ( boolean  reverse )
     //////////////////////////////////////////////////////////////////////
     {
       this.reverse = reverse;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Compares two AgoracastData objects based upon field values.
     *********************************************************************/
     public int  compare ( Object  o1, Object  o2 )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( o1 );

       NullArgumentException.check ( o2 );

       AgoracastData  a1 = ( AgoracastData ) o1;

       AgoracastData  a2 = ( AgoracastData ) o2;

       Object  e1 = a1.getValue ( sortField );

       Object  e2 = a2.getValue ( sortField );

       if ( agoracastField != null )
       {
         return agoracastField.compare ( ( String ) e1, ( String ) e2 )
           * ( reverse ? -1 : 1 );
       }

       if ( ( e1 == null )
         && ( e2 == null ) )
       {
         return 0;
       }

       if ( e1 == null )
       {
         return reverse ? 1 : -1;
       }

       if ( e2 == null )
       {
         return reverse ? -1 : 1;
       }

       Comparable  c1 = ( Comparable ) e1;

       Comparable  c2 = ( Comparable ) e2;

       return c1.compareTo ( c2 ) * ( reverse ? -1 : 1 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }