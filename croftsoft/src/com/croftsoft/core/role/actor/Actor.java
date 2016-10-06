     package com.croftsoft.core.role.actor;

     /*********************************************************************
     * A semantic interface for bootstrapping the exchange of semantic
     * interfaces between objects.
     *
     * <P>
     *
     * The semantic definition for this interface is available at:<BR>
     * <A HREF="http://www.alumni.caltech.edu/~croft/research/agent/role/">
     * http://www.alumni.caltech.edu/~croft/research/agent/role/</A>
     *
     * <P>
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1998-04-26
     *********************************************************************/

     public interface  Actor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Returns an array of String representing the names of semantic
     * interfaces, or "roles", supported by this object.
     *
     * <P>
     *
     * The array may represent just a limited subset from all of the
     * interfaces that are actually implemented by this object.  Which
     * interfaces are returned are chosen as deemed appropriate by this
     * object for the current context and may change with each call to
     * this method.
     *
     * <P>
     *
     * The ordering of the roles in the array may be determined dynamically
     * by the object and should be assumed to be listed in the order of 
     * preference, from the viewpoint of this object, for polymorphic
     * casting of this object by another.  The ordering may change with
     * each call to this method.
     *
     * <P>
     *
     * May return null.
     *********************************************************************/
     public abstract String [ ]  getRoles ( );
     
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
