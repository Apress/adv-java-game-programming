     package com.croftsoft.apps.wyrm.entity;

     import java.rmi.*;
     import javax.ejb.*;

     /*********************************************************************
     * Local interface to User EJB.
     *
     * @version
     *   2002-10-31
     * @since
     *   2002-09-30
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public interface  UserLocal
       extends EJBLocalObject 
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public Long  getId ( )
       throws EJBException;

     public String  getUsername   ( )
       throws EJBException;

     public String  getPassword   ( )
       throws EJBException;

     public String  getFirstName  ( )
       throws EJBException;

     public String  getMiddleName ( )
       throws EJBException;

     public String  getLastName   ( )
       throws EJBException;

     public double  getCredits    ( )
       throws EJBException;

     public PcLocal  getPcLocal   ( )
       throws EJBException;

     //

     public void  setPassword   ( String   password   )
       throws EJBException;

     public void  setFirstName  ( String   firstName  )
       throws EJBException;

     public void  setMiddleName ( String   middleName )
       throws EJBException;

     public void  setLastName   ( String   lastName   )
       throws EJBException;

     public void  setCredits    ( double   credits    )
       throws EJBException;

     public void  setPcLocal    ( PcLocal  pcLocal    )
       throws EJBException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
