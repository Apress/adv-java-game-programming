     package com.croftsoft.apps.compiler.mini.parse;

     import java.io.IOException;

     /*********************************************************************
     * Token scanner interface for use with CUP.
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-04-24
     *********************************************************************/

     public interface  CUPTokenScanner
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public java_cup.runtime.Symbol  nextToken ( ) throws IOException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
