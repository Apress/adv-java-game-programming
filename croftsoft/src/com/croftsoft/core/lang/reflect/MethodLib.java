     package com.croftsoft.core.lang.reflect;

     import java.lang.reflect.*;

     /*********************************************************************
     *
     * Static library to invoke methods using reflection.
     *
     * @version
     *   1998-10-04
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  MethodLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private  MethodLib ( ) { }

     public static void  invokeMain ( Class  c, String [ ]  args )
       throws IllegalAccessException
     //////////////////////////////////////////////////////////////////////
     {
       Method  method;

       try
       {
         method = c.getMethod ( "main",
           new Class [ ] { String [ ].class } );
       }
       catch ( NoSuchMethodException  ex )
       {
         System.err.println ( "No main() method in class \""
           + c.getName ( ) + "\"." );
         return;
       }

       try
       {
         method.invoke ( null, new Object [ ] { args } );
       }
       catch ( InvocationTargetException  ex )
       {
         Throwable  t = ex.getTargetException ( );
         System.err.println (
           "\"main()\" method exited with exception \"" + t + "\"." );
         t.printStackTrace ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
