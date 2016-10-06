     package com.croftsoft.core.util.jlex;

     import java.io.IOException;

     /*********************************************************************
     * Interface for token scanners.
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

     public interface  TokenScanner
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Fetches the next Token from the input stream.
     *
     * @return
     *   The next Token or, if the end of the file is reached, null.
     *********************************************************************/
     public Token  nextToken ( ) throws IOException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
