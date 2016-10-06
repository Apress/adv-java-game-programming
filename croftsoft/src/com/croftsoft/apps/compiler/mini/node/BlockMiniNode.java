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

     public class  BlockMiniNode extends AbstractMiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected DeclarationSequenceMiniNode  declarationSequenceMiniNode;
     protected StatementSequenceMiniNode    statementSequenceMiniNode;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  BlockMiniNode (
       DeclarationSequenceMiniNode  declarationSequenceMiniNode,
       StatementSequenceMiniNode    statementSequenceMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this.declarationSequenceMiniNode = declarationSequenceMiniNode;
       this.statementSequenceMiniNode   = statementSequenceMiniNode;
     }

     public  BlockMiniNode (
       StatementSequenceMiniNode  statementSequenceMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null, statementSequenceMiniNode );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public DeclarationSequenceMiniNode  getDeclarationSequenceMiniNode ( )
       { return declarationSequenceMiniNode; }

     public StatementSequenceMiniNode  getStatementSequenceMiniNode ( )
       { return statementSequenceMiniNode; }

     //////////////////////////////////////////////////////////////////////
     // MiniNode interface methods
     //////////////////////////////////////////////////////////////////////

     public void  generate ( MiniNodeCodeVisitor  miniNodeCodeVisitor )
     //////////////////////////////////////////////////////////////////////
     {
       miniNodeCodeVisitor.generateBlock ( this );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       parentMiniNodeStack.push ( this );

       if ( declarationSequenceMiniNode != null )
       {
         declarationSequenceMiniNode.checkSemantics (
           parentMiniNodeStack );
       }

       if ( statementSequenceMiniNode != null )
       {
         statementSequenceMiniNode.checkSemantics ( parentMiniNodeStack );
       }

       parentMiniNodeStack.pop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
