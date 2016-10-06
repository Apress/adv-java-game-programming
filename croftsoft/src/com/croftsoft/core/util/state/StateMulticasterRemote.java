     package com.croftsoft.core.util.state;

     import java.rmi.*;

     /*********************************************************************
     *
     * An interface for remote objects that mulicast State updates.
     * 
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1998-12-06
     *********************************************************************/

     public interface  StateMulticasterRemote extends Remote
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  update ( State  state )
       throws RemoteException;

     public boolean  addStateListener (
       StateListener  stateListener )
       throws RemoteException;

     public boolean  removeStateListener (
       StateListener  stateListener )
       throws RemoteException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
