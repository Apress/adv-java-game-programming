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

     public class  TermMiniNode extends AbstractMiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected TermMiniNode      termMiniNode;
     protected OperatorMiniNode  operatorMiniNode;
     protected ElementMiniNode   elementMiniNode;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  TermMiniNode (
       TermMiniNode      termMiniNode,
       OperatorMiniNode  operatorMiniNode,
       ElementMiniNode   elementMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this.termMiniNode     = termMiniNode;
       this.operatorMiniNode = operatorMiniNode;
       this.elementMiniNode  = elementMiniNode;
     }

     public  TermMiniNode ( ElementMiniNode  elementMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null, null, elementMiniNode );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public TermMiniNode  getTermMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return termMiniNode;
     }

     public OperatorMiniNode  getOperatorMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return operatorMiniNode;
     }

     public ElementMiniNode  getElementMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return elementMiniNode;
     }

     //////////////////////////////////////////////////////////////////////
     // MiniNode interface methods
     //////////////////////////////////////////////////////////////////////

     public void  generate ( MiniNodeCodeVisitor  miniNodeCodeVisitor )
     //////////////////////////////////////////////////////////////////////
     {
       miniNodeCodeVisitor.generateTerm ( this );
     }
     
     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       parentMiniNodeStack.push ( this );

       if ( termMiniNode != null )
       {
         termMiniNode.checkSemantics ( parentMiniNodeStack );
       }

       if ( operatorMiniNode != null )
       {
         operatorMiniNode.checkSemantics ( parentMiniNodeStack );
       }

       elementMiniNode.checkSemantics ( parentMiniNodeStack );

       parentMiniNodeStack.pop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
