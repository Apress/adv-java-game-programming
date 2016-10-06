     package com.croftsoft.core.security.manager;

     import java.io.FileDescriptor;
     import java.net.InetAddress;

     /*********************************************************************
     * A generic <I>SecurityManager</I> implementation to host untrusted
     * code loaded over a network.  "Untrusted" code is defined as any
     * class that was loaded using a <I>ClassLoader</I> instead of being
     * read in directly from the local <I>classpath</I>.
     *
     * <P>
     *
     * Implement by including the following as the very first line of the
     * <I>main ( )</I> method of your application:
     *
     * <PRE>
     * System.setSecurityManager ( new HostSecurityManager ( ) );
     * </PRE>
     *
     * <P>
     *
     * Each of the 29 "check" methods of the standard Java 1.1.5 superclass
     * <I>SecurityManager</I> are overridden to just call the
     * <A HREF="#reject_untrusted">reject_untrusted ( )</A> method.
     * This method simply throws a <I>SecurityException</I> if the
     * superclass method <I>inClassLoader ( )</I> returns true.
     *
     * <P>
     *
     * To my knowledge, the only four possible "hostile" actions remaining
     * that untrusted code could still perform on the host when using
     * this implementation of <I>SecurityManager</I> are
     *
     * <UL>
     * <LI> attempting to read from the standard input <I>System.in</I>,
     * <LI> writing to the console outputs <I>err</I> and <I>out</I>,
     * <LI> consuming excessive processor time in its single thread, and
     * <LI> consuming memory until an <I>OutOfMemoryError</I> occurs.
     * </UL>
     *
     * <P>
     *
     * Preventing untrusted code from reading from and writing to the
     * standard console could be accomplished by replacing the default
     * IO streams with customized classes that would throw a
     * <I>SecurityException</I>.
     *
     * See <I>System.setErr ( err )</I>, <I>System.setIn ( in )</I>, and
     * <I>System.setOut ( out )</I>.
     *
     * <P>
     *
     * If the standard console IO stream blocking were implemented,
     * untrusted code would have no method of communication except by
     * calling the methods of other objects within the virtual machine.
     * Further communication (sockets, etc.) to the outside could then be
     * optionally permitted by trusted classes.  See
     * <I>SecurityManager.inCheck</I>,
     * <I>SecurityManager.getInCheck ( )</I>, and
     * <I>SecurityManager.getSecurityContext ( )</I>.
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1999-02-13
     *********************************************************************/

     public class  UntrustedSecurityManager extends SecurityManager
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Called by all of the "check" methods to foil untrusted code.
     * <PRE>
     * if ( inClassLoader ( ) )
     *   throw new SecurityException ( "untrusted" );
     * </PRE>
     *********************************************************************/
     protected void  reject_untrusted ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( inClassLoader ( ) )
         throw new SecurityException ( "untrusted" );
     }

     //////////////////////////////////////////////////////////////////////
     // Check methods
     //////////////////////////////////////////////////////////////////////

     public void  checkAccept ( String  host, int  port )
       { reject_untrusted ( ); }

     public void  checkAccess ( Thread  t )
       { reject_untrusted ( ); }

     public void  checkAccess ( ThreadGroup  g )
       { reject_untrusted ( ); }

     public void  checkAwtEventQueueAccess ( )
       { reject_untrusted ( ); }

     public void  checkConnect ( String  host, int  port )
       { reject_untrusted ( ); }

     public void  checkConnect ( String  host, int  port, Object  context )
       { reject_untrusted ( ); }

     public void  checkCreateClassLoader ( )
       { reject_untrusted ( ); }

     public void  checkDelete ( String  file )
       { reject_untrusted ( ); }

     public void  checkExec ( String  cmd )
       { reject_untrusted ( ); }

     public void  checkExit ( int  status )
       { reject_untrusted ( ); }

     public void  checkLink ( String  libname )
       { reject_untrusted ( ); }

     public void  checkListen ( int  port )
       { reject_untrusted ( ); }

     public void  checkMemberAccess ( Class  clazz, int  which )
       { reject_untrusted ( ); }

     public void  checkMulticast ( InetAddress  maddr )
       { reject_untrusted ( ); }

     public void  checkMulticast ( InetAddress  maddr, byte  ttl )
       { reject_untrusted ( ); }

     public void  checkPackageAccess ( String  pkg )
       { reject_untrusted ( ); }

     public void  checkPackageDefinition ( String  pkg )
       { reject_untrusted ( ); }

     public void  checkPrintJobAccess ( )
       { reject_untrusted ( ); }

     public void  checkPropertiesAccess ( )
       { reject_untrusted ( ); }

     public void  checkPropertyAccess ( String  key )
       { reject_untrusted ( ); }

     public void  checkRead ( FileDescriptor  fd )
       { reject_untrusted ( ); }

     public void  checkRead ( String  file )
       { reject_untrusted ( ); }

     public void  checkRead ( String  file, Object  context )
       { reject_untrusted ( ); }

     public void  checkSecurityAccess ( String  action )
       { reject_untrusted ( ); }

     public void  checkSetFactory ( )
       { reject_untrusted ( ); }

     public void  checkSystemClipboardAccess ( )
       { reject_untrusted ( ); }

     public boolean  checkTopLevelWindow ( Object  window )
       { reject_untrusted ( ); return true; }

     public void  checkWrite ( FileDescriptor  fd )
       { reject_untrusted ( ); }

     public void  checkWrite ( String  file )
       { reject_untrusted ( ); }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
