     package com.croftsoft.core.lang.classloader;

     /*********************************************************************
     *
     * Static library for use with custom ClassLoaders.
     *
     * @version
     *   1998-10-04
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  ClassLoaderLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private  ClassLoaderLib ( ) { }

     /*********************************************************************
     * Parses the package name out of a full class name.
     * Returns the empty string ("") if the class is not in a package.
     *********************************************************************/
     public static String  parsePackageName ( String  className )
     //////////////////////////////////////////////////////////////////////
     {
       String  packageName = "";
       int  i = className.lastIndexOf ( '.' );
       if ( i > -1 ) packageName = className.substring ( 0, i );
       return packageName;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
