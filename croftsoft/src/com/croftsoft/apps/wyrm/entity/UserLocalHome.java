     package com.croftsoft.apps.wyrm.entity;

     import java.rmi.*;
     import java.util.*;

     import javax.ejb.*;

     /*********************************************************************
     * Local home interface for User EJB.
     *
     * @version
     *   2002-10-03
     * @since
     *   2002-09-30
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public interface  UserLocalHome
       extends EJBLocalHome
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public UserLocal  create ( String  username )
       throws CreateException, EJBException;

     public UserLocal  findByPrimaryKey ( Long  id )
       throws FinderException, EJBException;

     public UserLocal  findByUsername ( String  username )
       throws FinderException, EJBException;

     public Collection  findAll ( )
       throws FinderException, EJBException;

     public String [ ]  getUsernames ( )
       throws FinderException, EJBException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
