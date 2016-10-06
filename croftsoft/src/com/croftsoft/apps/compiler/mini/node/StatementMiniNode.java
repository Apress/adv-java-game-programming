     package com.croftsoft.apps.compiler.mini.node;

     import java.util.*;

     /*********************************************************************
     * Abstract parse tree node for the Mini programming language.
     *
     * @see
     *   MiniNode
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-04-25
     *********************************************************************/

     public interface  StatementMiniNode extends MiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
