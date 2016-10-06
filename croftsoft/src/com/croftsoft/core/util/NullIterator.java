     package com.croftsoft.core.util;

     import java.util.*;

     /*********************************************************************
     * A singleton null object Iterator implementation.
     *
     * @version
     *   2003-05-12
     * @since
     *   2003-05-10
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  NullIterator
       implements Iterator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final NullIterator  INSTANCE = new NullIterator ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  hasNext ( ) { return false; }

     public Object  next ( ) { throw new NoSuchElementException ( ); }

     public void  remove ( ) {
       throw new UnsupportedOperationException ( ); }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  NullIterator ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }