     package com.croftsoft.apps.wyrm.server;

     import javax.ejb.*;

     /*********************************************************************
     * Local interface to the Wyrm server EJB.
     *
     * @version
     *   2002-10-31
     * @since
     *   2002-10-31
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public interface  WyrmServerLocal
       extends EJBLocalObject
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public Object  serve ( Object  request )
       throws EJBException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }