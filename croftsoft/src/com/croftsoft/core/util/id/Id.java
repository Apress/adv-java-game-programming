     package com.croftsoft.core.util.id;

     import java.io.Serializable;

     /*********************************************************************
     * An Id is used for consistent cross-process identification.
     *
     * <p>
     * Id objects are useful for identifying, by equality, distributed
     * and persistent objects (space and time, respectively).
     * </p>
     *
     * <p>
     * An object that implements Id has the following characteristics:
     *
     * <ul>
     * <li> immutable;</li>
     * <li> Serializable and deeply Cloneable; and</li>
     * <li> results from the methods equals() and hashCode() are not based
     *      upon transient data and are consistent across processes.</li>
     * </ul>
     * </p>
     *
     * @version
     *   2003-06-17
     * @since
     *   2000-01-16
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  Id
       extends Cloneable, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public Object   clone    ( );

     public boolean  equals   ( Object  other );

     public int      hashCode ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
