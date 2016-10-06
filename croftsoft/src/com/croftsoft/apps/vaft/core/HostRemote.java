     package com.croftsoft.apps.vaft.core;

     import java.rmi.*;

     import com.croftsoft.core.role.actor.Actor;

     /*********************************************************************
     * <P>
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1998-04-26
     *********************************************************************/

     public interface  HostRemote extends Remote
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  host ( Actor  actor ) throws RemoteException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
