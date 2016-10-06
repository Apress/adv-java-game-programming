     package com.croftsoft.apps.wyrm.server;

     import javax.ejb.*;

     /*********************************************************************
     * Local home interface for the Wyrm server EJB.
     *
     * @version
     *   2002-10-31
     * @since
     *   2002-10-31
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public interface  WyrmServerLocalHome
       extends EJBLocalHome
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public WyrmServerLocal  create ( )
       throws CreateException, EJBException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
