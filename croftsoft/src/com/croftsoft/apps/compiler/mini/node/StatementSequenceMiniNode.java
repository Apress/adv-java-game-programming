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
     *   1999-04-22
     *********************************************************************/

     public class  StatementSequenceMiniNode extends AbstractMiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected List  statementMiniNodeList;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  StatementSequenceMiniNode (
       StatementSequenceMiniNode  statementSequenceMiniNode,
       StatementMiniNode          statementMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       if ( statementSequenceMiniNode != null )
       {
         statementMiniNodeList
           = statementSequenceMiniNode.statementMiniNodeList;
       }
       else
       {
         statementMiniNodeList = new LinkedList ( );
       }

       statementMiniNodeList.add ( statementMiniNode );
     }

     public  StatementSequenceMiniNode (
       StatementMiniNode  statementMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null, statementMiniNode );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public List  getStatementMiniNodeList ( )
     //////////////////////////////////////////////////////////////////////
     {
       return statementMiniNodeList;
     }

     //////////////////////////////////////////////////////////////////////
     // MiniNode interface methods
     //////////////////////////////////////////////////////////////////////

     public void  generate ( MiniNodeCodeVisitor  miniNodeCodeVisitor )
     //////////////////////////////////////////////////////////////////////
     {
       miniNodeCodeVisitor.generateStatementSequence ( this );
     }
     
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       parentMiniNodeStack.push ( this );

       Iterator  i = statementMiniNodeList.iterator ( );

       while ( i.hasNext ( ) )
       {
         ( ( StatementMiniNode ) i.next ( ) ).checkSemantics (
           parentMiniNodeStack );
       }

       parentMiniNodeStack.pop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
