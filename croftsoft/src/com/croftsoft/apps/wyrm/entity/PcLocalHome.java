     package com.croftsoft.apps.wyrm.entity;

     import java.rmi.*;
     import javax.ejb.*;

     /*********************************************************************
     * Local home interface for player character EJB.
     *
     * @version
     *   2002-10-01
     * @since
     *   2002-09-30
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public interface  PcLocalHome
       extends EJBLocalHome
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public PcLocal  create ( )
       throws CreateException, EJBException;

     public PcLocal  findByPrimaryKey ( Long  id )
       throws FinderException, EJBException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
