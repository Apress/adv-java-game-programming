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

     public class  ComparisonMiniNode extends AbstractMiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected ExpressionMiniNode  leftExpressionMiniNode;
     protected RelationMiniNode    relationMiniNode;
     protected ExpressionMiniNode  rightExpressionMiniNode;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ComparisonMiniNode (
       ExpressionMiniNode  leftExpressionMiniNode,
       RelationMiniNode    relationMiniNode,
       ExpressionMiniNode  rightExpressionMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this.leftExpressionMiniNode  = leftExpressionMiniNode;
       this.relationMiniNode        = relationMiniNode;
       this.rightExpressionMiniNode = rightExpressionMiniNode;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public ExpressionMiniNode  getLeftExpressionMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return leftExpressionMiniNode;
     }

     public RelationMiniNode  getRelationMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return relationMiniNode;
     }

     public ExpressionMiniNode  getRightExpressionMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return rightExpressionMiniNode;
     }

     //////////////////////////////////////////////////////////////////////
     // MiniNode interface methods
     //////////////////////////////////////////////////////////////////////

     public void  generate ( MiniNodeCodeVisitor  miniNodeCodeVisitor )
     //////////////////////////////////////////////////////////////////////
     {
       miniNodeCodeVisitor.generateComparison ( this );
     }
     
     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       parentMiniNodeStack.push ( this );

       leftExpressionMiniNode.checkSemantics ( parentMiniNodeStack );
       relationMiniNode.checkSemantics ( parentMiniNodeStack );
       rightExpressionMiniNode.checkSemantics ( parentMiniNodeStack );

       parentMiniNodeStack.pop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
