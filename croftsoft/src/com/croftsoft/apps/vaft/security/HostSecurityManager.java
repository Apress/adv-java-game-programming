     package com.croftsoft.apps.vaft.security;

     import java.io.FileDescriptor;
     import java.net.InetAddress;

     import com.croftsoft.core.security.manager.UntrustedSecurityManager;

     /*********************************************************************
     * A subclass of <I>UntrustedSecurityManager</I> that overrides
     * some restrictions.
     *
     * <P>
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1998-04-26
     *********************************************************************/

     public class  HostSecurityManager extends UntrustedSecurityManager
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String [ ]  PACKAGES_FORBIDDEN
       = { "java.",
           "javax.",
           "sun.",
           "com.sun.",
           "com.orbs.pub.app.vaft."
         };

     //////////////////////////////////////////////////////////////////////
     // Check methods
     //////////////////////////////////////////////////////////////////////

// There may be a problem with classloaders for classes loaded from
// different URLs but using the same class names.

     /*********************************************************************
     * Allows the agent to use classes from the packages "java.lang",
     * "java.io", and the "vaft.*" hierarchy.  All other classes are
     * forbidden.
     *********************************************************************/
     public void  checkPackageAccess ( String  pkg )
     //////////////////////////////////////////////////////////////////////
     {
       if ( pkg.equals ( "java.lang" ) ) return;
       if ( pkg.equals ( "java.io"   ) ) return;
       if ( pkg.startsWith ( "com.orbs.pub.app.agent.vaft." ) ) return;
       super.checkPackageAccess ( pkg );
     }

     /*********************************************************************
     * Prevents any thread, trusted or untrusted, from creating new
     * classes within the following package hierarchies:
     * <UL>
     * <LI> java.*
     * <LI> javax.*
     * <LI> sun.*
     * <LI> com.sun.*
     * <LI> com.orbs.pub.app.vaft.*
     * </UL>
     *********************************************************************/
     public void  checkPackageDefinition ( String  pkg )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < PACKAGES_FORBIDDEN.length; i++ )
       {
         if ( pkg.startsWith ( PACKAGES_FORBIDDEN [ i ] ) )
           throw new SecurityException ( pkg );
       }
       super.checkPackageDefinition ( pkg );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
