     package com.croftsoft.core.lang.ex;

     /*********************************************************************
     *
     * A concrete ExceptionListener that simply calls printStackTrace().
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1998-12-05
     *********************************************************************/

     public class  PrintExceptionListener implements ExceptionListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  threwException ( Object  o, Exception  ex )
     //////////////////////////////////////////////////////////////////////
     {
       ex.printStackTrace ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
