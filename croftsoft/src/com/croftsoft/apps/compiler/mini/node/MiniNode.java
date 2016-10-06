     package com.croftsoft.apps.compiler.mini.node;

     import java.util.*;

     /*********************************************************************
     * Interface for parse tree objects for the Mini programming language.
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
     *   1999-04-25
     *********************************************************************/

     public interface  MiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Uses the Visitor design pattern to generate code.
     *
     * <PRE>
     * miniNodeCodeVisitor.generate<MiniNode-type> ( this );
     * </PRE>
     *********************************************************************/
     public void  generate ( MiniNodeCodeVisitor  miniNodeCodeVisitor );

     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
