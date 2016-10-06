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

     public class  DefiniteLoopStatementMiniNode extends AbstractMiniNode
       implements StatementMiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected ExpressionMiniNode         expressionMiniNode;
     protected StatementSequenceMiniNode  statementSequenceMiniNode;

     //////////////////////////////////////////////////////////////////////
     // Constructor methods
     //////////////////////////////////////////////////////////////////////

     public  DefiniteLoopStatementMiniNode (
       ExpressionMiniNode         expressionMiniNode,
       StatementSequenceMiniNode  statementSequenceMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this.expressionMiniNode        = expressionMiniNode;
       this.statementSequenceMiniNode = statementSequenceMiniNode;
     }

     //////////////////////////////////////////////////////////////////////
     // Access methods
     //////////////////////////////////////////////////////////////////////

     public ExpressionMiniNode  getExpressionMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return expressionMiniNode;
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
       miniNodeCodeVisitor.generateDefiniteLoopStatement ( this );
     }
     
     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       parentMiniNodeStack.push ( this );

       expressionMiniNode.checkSemantics ( parentMiniNodeStack );
       statementSequenceMiniNode.checkSemantics ( parentMiniNodeStack );

       parentMiniNodeStack.pop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
