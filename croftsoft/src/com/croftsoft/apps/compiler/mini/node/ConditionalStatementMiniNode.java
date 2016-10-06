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

     public class  ConditionalStatementMiniNode extends AbstractMiniNode
       implements StatementMiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected ComparisonMiniNode         comparisonMiniNode;
     protected StatementSequenceMiniNode  thenStatementSequenceMiniNode;
     protected StatementSequenceMiniNode  elseStatementSequenceMiniNode;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ConditionalStatementMiniNode (
       ComparisonMiniNode         comparisonMiniNode,
       StatementSequenceMiniNode  thenStatementSequenceMiniNode,
       StatementSequenceMiniNode  elseStatementSequenceMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this.comparisonMiniNode            = comparisonMiniNode;
       this.thenStatementSequenceMiniNode = thenStatementSequenceMiniNode;
       this.elseStatementSequenceMiniNode = elseStatementSequenceMiniNode;
     }

     public  ConditionalStatementMiniNode (
       ComparisonMiniNode         comparisonMiniNode,
       StatementSequenceMiniNode  thenStatementSequenceMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this ( comparisonMiniNode, thenStatementSequenceMiniNode, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public ComparisonMiniNode  getComparisonMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return comparisonMiniNode;
     }

     public StatementSequenceMiniNode  getThenStatementSequenceMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return thenStatementSequenceMiniNode;
     }

     public StatementSequenceMiniNode  getElseStatementSequenceMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return elseStatementSequenceMiniNode;
     }

     //////////////////////////////////////////////////////////////////////
     // MiniNode interface methods
     //////////////////////////////////////////////////////////////////////

     public void  generate ( MiniNodeCodeVisitor  miniNodeCodeVisitor )
     //////////////////////////////////////////////////////////////////////
     {
       miniNodeCodeVisitor.generateConditionalStatement ( this );
     }
     
     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       parentMiniNodeStack.push ( this );

       comparisonMiniNode.checkSemantics ( parentMiniNodeStack );

       if ( thenStatementSequenceMiniNode != null )
       {
         thenStatementSequenceMiniNode.checkSemantics (
           parentMiniNodeStack );
       }

       if ( elseStatementSequenceMiniNode != null )
       {
         elseStatementSequenceMiniNode.checkSemantics (
           parentMiniNodeStack );
       }

       parentMiniNodeStack.pop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
