     package com.croftsoft.core.util;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Keeps an array.
     *
     * @version
     *   2003-04-11
     * @since
     *   2003-04-11
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  ArrayKeeper
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public Object [ ]  getArray ( );

     public Object [ ]  getArray ( Class c );

     //

     public void  append ( Object  o );

     public void  insert ( Comparable  comparable );

     public void  remove ( Object  o );

     public void  setArray ( Object [ ]  array );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }