     package com.croftsoft.apps.tag3d;

     import java.rmi.RMISecurityManager;

     /*********************************************************************
     * An RMISecurityManager extension that loosens the restrictions where
     * necessary to permit the use of RMI with the Tag3D application.
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-02-07
     *********************************************************************/

     public class  Tag3DSecurityManager extends RMISecurityManager
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  checkAccess ( Thread  t )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  checkAccess ( ThreadGroup  g )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  checkConnect ( String  host, int  port )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  checkConnect ( String  host, int  port, Object  context )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public boolean  checkTopLevelWindow ( Object  window )
     //////////////////////////////////////////////////////////////////////
     {
       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
