     package com.croftsoft.core.util.state;

     /*********************************************************************
     * The state of an object may be communicated by a State object.
     * Each State object has a unique key, usually the object or its unique
     * identifier whose state or a portion of its state is reflected by
     * this State object.
     *
     * <P>
     *
     * State objects are considered equal if their classes and keys are
     * equal.  This makes a State object useful in Set collections where
     * the state or the latest subset of the state of the key object should
     * should only be contained once.  One application is the queued
     * transmission of object state information wherein only the latest
     * state data should be retained.
     *
     * @see
     *   StateLib
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-02-06
     *********************************************************************/

     public interface State
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Returns the State key, usually the object or its unique identifier
     * whose state or a portion of its state is reflected by this State
     * object.
     *********************************************************************/
     public Object  getKey ( );

     /*********************************************************************
     * Returns true if the classes and State keys are equal.
     *********************************************************************/
     public boolean  equals ( Object  other );

     /*********************************************************************
     * Returns the hash code of the State key.
     *********************************************************************/
     public int  hashCode ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
