     package com.croftsoft.apps.compiler.mini.node;

     import java.util.*;

     /*********************************************************************
     * Parse tree node for the Mini programming language.
     *
     * @see
     *   MiniNode
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-04-25
     *********************************************************************/

     public class  IndefiniteLoopStatementMiniNode extends AbstractMiniNode
       implements StatementMiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected ComparisonMiniNode         comparisonMiniNode;
     protected StatementSequenceMiniNode  statementSequenceMiniNode;

     //////////////////////////////////////////////////////////////////////
     // Constructor methods
     //////////////////////////////////////////////////////////////////////

     public  IndefiniteLoopStatementMiniNode (
       ComparisonMiniNode         comparisonMiniNode,
       StatementSequenceMiniNode  statementSequenceMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this.comparisonMiniNode        = comparisonMiniNode;
       this.statementSequenceMiniNode = statementSequenceMiniNode;
     }

     //////////////////////////////////////////////////////////////////////
     // Access methods
     //////////////////////////////////////////////////////////////////////

     public ComparisonMiniNode  getComparisonMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return comparisonMiniNode;
     }

     public StatementSequenceMiniNode  getStatementSequenceMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return statementSequenceMiniNode;
     }

     //////////////////////////////////////////////////////////////////////
     // MiniNode interface methods
     //////////////////////////////////////////////////////////////////////

     public void  generate ( MiniNodeCodeVisitor  miniNodeCodeVisitor )
     //////////////////////////////////////////////////////////////////////
     {
       miniNodeCodeVisitor.generateIndefiniteLoopStatement ( this );
     }
     
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       parentMiniNodeStack.push ( this );

       comparisonMiniNode.checkSemantics ( parentMiniNodeStack );
       statementSequenceMiniNode.checkSemantics ( parentMiniNodeStack );

       parentMiniNodeStack.pop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
