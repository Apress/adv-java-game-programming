     package com.croftsoft.core.role.actor;

     import java.rmi.*;

     /*********************************************************************
     * A semantic interface for bootstrapping the exchange of semantic
     * interfaces between remote objects.
     *
     * <P>
     *
     * The semantic definition for this interface is available at:<BR>
     * <A HREF="http://www.alumni.caltech.edu/~croft/research/agent/role/">
     * http://www.alumni.caltech.edu/~croft/research/agent/role/</A>
     *
     * <P>
     *
     * This differs from the com.orbs.pub.agent.role.Actor semantic
     * interface in the following ways:
     *
     * <OL>
     * <LI> the interface extend java.rmi.Remote
     * <LI> the method throws java.rmi.RemoteException
     * </OL>
     *
     * <P>
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1998-04-26
     *********************************************************************/

     public interface  ActorRemote extends Remote
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public abstract String [ ]  getRolesRemote ( ) throws RemoteException;
     
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
