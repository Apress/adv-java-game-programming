     package com.croftsoft.apps.wyrm.entity;

     import java.rmi.*;
     import javax.ejb.*;

     /*********************************************************************
     * Local interface to player character EJB.
     *
     * @version
     *   2002-09-30
     * @since
     *   2002-09-30
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public interface  PcLocal
       extends EJBLocalObject 
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public Long  getId ( )
       throws EJBException;

     public String  getName  ( )
       throws EJBException;

     public String  getState ( )
       throws EJBException;

     public long  getHealth  ( )
       throws EJBException;

     public long  getWealth  ( )
       throws EJBException;

     public long  getLevel   ( )
       throws EJBException;

     public long  getExperience ( )
       throws EJBException;

     //

     public void  setName   ( String  name  )
       throws EJBException;

     public void  setState  ( String  state )
       throws EJBException;

     public void  setHealth ( long  health )
       throws EJBException;

     public void  setWealth ( long  wealth )
       throws EJBException;

     public void  setLevel  ( long  level  )
       throws EJBException;

     public void  setExperience ( long  experience )
       throws EJBException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
