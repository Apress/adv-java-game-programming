     package com.croftsoft.core.util.jlex;

     /*********************************************************************
     * Token data class for use with JLex.
     *
     * <B>Reference:</B>
     *
     * <P>
     *
     * "JLex: A Lexical Analyzer Generator for Java"<BR>
     * <A HREF="http://www.cs.princeton.edu/~appel/modern/java/JLex/">
     * http://www.cs.princeton.edu/~appel/modern/java/JLex/</A>
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-02-10
     *********************************************************************/

     public class  Token
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /** The token type identifier number. */
     public int     id        = -1;

     public String  text      = null;

     /** Defaults to -1. */
     public int     line      = -1;

     /** Defaults to -1. */
     public int     charBegin = -1;

     /** Defaults to -1. */
     public int     charEnd   = -1;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Token (
       int     id,
       String  text,
       int     line,
       int     charBegin,
       int     charEnd )
     //////////////////////////////////////////////////////////////////////
     {
       this.id        = id;
       this.text      = text;
       this.line      = line;
       this.charBegin = charBegin;
       this.charEnd   = charEnd;
     }

     public  Token (
       int     id,
       String  text )
     //////////////////////////////////////////////////////////////////////
     {
       this.id   = id;
       this.text = text;
     }

     public  Token ( int  id )
     //////////////////////////////////////////////////////////////////////
     {
       this.id = id;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  toString ( )
     //////////////////////////////////////////////////////////////////////
     {
       return
           "id        = " + id        + "\n"
         + "text      = " + text      + "\n"
         + "line      = " + line      + "\n"
         + "charBegin = " + charBegin + "\n"
         + "charEnd   = " + charEnd;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
