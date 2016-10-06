     package com.croftsoft.core.util.log;

     /*********************************************************************
     * An interface for logging.
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   2001-02-27
     *********************************************************************/

     public interface  Log
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  record ( String  message );

     public void  record ( Throwable  throwable );

     public void  record ( String  message, Throwable  throwable );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
