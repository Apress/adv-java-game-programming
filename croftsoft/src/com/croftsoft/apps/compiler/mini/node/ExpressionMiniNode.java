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

     public class  ExpressionMiniNode extends AbstractMiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected ExpressionMiniNode  expressionMiniNode;
     protected OperatorMiniNode    operatorMiniNode;
     protected TermMiniNode        termMiniNode;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ExpressionMiniNode (
       ExpressionMiniNode  expressionMiniNode,
       OperatorMiniNode    operatorMiniNode,
       TermMiniNode        termMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this.expressionMiniNode = expressionMiniNode;
       this.operatorMiniNode   = operatorMiniNode;
       this.termMiniNode       = termMiniNode;
     }

     public  ExpressionMiniNode ( TermMiniNode  termMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null, null, termMiniNode );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public ExpressionMiniNode  getExpressionMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return expressionMiniNode;
     }

     public OperatorMiniNode  getOperatorMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return operatorMiniNode;
     }

     public TermMiniNode  getTermMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return termMiniNode;
     }

     //////////////////////////////////////////////////////////////////////
     // MiniNode interface methods
     //////////////////////////////////////////////////////////////////////

     public void  generate ( MiniNodeCodeVisitor  miniNodeCodeVisitor )
     //////////////////////////////////////////////////////////////////////
     {
       miniNodeCodeVisitor.generateExpression ( this );
     }
     
     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       parentMiniNodeStack.push ( this );

       if ( expressionMiniNode != null )
       {
         expressionMiniNode.checkSemantics ( parentMiniNodeStack );
       }

       if ( operatorMiniNode != null )
       {
         operatorMiniNode.checkSemantics ( parentMiniNodeStack );
       }

       termMiniNode.checkSemantics ( parentMiniNodeStack );

       parentMiniNodeStack.pop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
