     package com.croftsoft.core.util.log;

     import javax.servlet.Servlet;
     import javax.servlet.ServletContext;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Delegates logging to a ServletContext.
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   2001-02-27
     *********************************************************************/

     public final class  ServletContextLog implements Log
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final ServletContext  servletContext;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ServletContextLog ( ServletContext  servletContext )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.servletContext = servletContext );
     }

     public  ServletContextLog ( Servlet  servlet )
     //////////////////////////////////////////////////////////////////////
     {
       this ( servlet.getServletConfig ( ).getServletContext ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  record ( String  message )
     //////////////////////////////////////////////////////////////////////
     {
       servletContext.log ( message );
     }

     public void  record ( Throwable  throwable )
     //////////////////////////////////////////////////////////////////////
     {
       servletContext.log ( throwable.getMessage ( ), throwable );
     }


     public void  record ( String  message, Throwable  throwable )
     //////////////////////////////////////////////////////////////////////
     {
       servletContext.log ( message, throwable );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
