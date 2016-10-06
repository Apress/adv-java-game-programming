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
     *   1999-04-26
     *********************************************************************/

     public class  ExpressionSequenceMiniNode extends AbstractMiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected List  expressionMiniNodeList;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ExpressionSequenceMiniNode (
       ExpressionSequenceMiniNode  expressionSequenceMiniNode,
       ExpressionMiniNode          expressionMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       if ( expressionSequenceMiniNode != null )
       {
         expressionMiniNodeList
           = expressionSequenceMiniNode.expressionMiniNodeList;
       }
       else
       {
         expressionMiniNodeList = new LinkedList ( );
       }

       expressionMiniNodeList.add ( expressionMiniNode );
     }

     public  ExpressionSequenceMiniNode (
       ExpressionMiniNode  expressionMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null, expressionMiniNode );
     }

     //////////////////////////////////////////////////////////////////////
     // Access methods
     //////////////////////////////////////////////////////////////////////

     public List  getExpressionMiniNodeList ( )
     //////////////////////////////////////////////////////////////////////
     {
       return expressionMiniNodeList;
     }

     public int  size ( )
     //////////////////////////////////////////////////////////////////////
     {
       return expressionMiniNodeList.size ( );
     }

     //////////////////////////////////////////////////////////////////////
     // MiniNode interface methods
     //////////////////////////////////////////////////////////////////////

     public void  generate ( MiniNodeCodeVisitor  miniNodeCodeVisitor )
     //////////////////////////////////////////////////////////////////////
     {
       miniNodeCodeVisitor.generateExpressionSequence ( this );
     }
     
     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       parentMiniNodeStack.push ( this );

       MiniNodeLib.checkSemantics (
         expressionMiniNodeList, parentMiniNodeStack );

       parentMiniNodeStack.pop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
