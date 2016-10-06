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

     public class  ProcedureCallStatementMiniNode extends AbstractMiniNode
       implements StatementMiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected NameMiniNode                nameMiniNode;
     protected ExpressionSequenceMiniNode  expressionSequenceMiniNode;

     protected Boolean                     callsNested;

     //////////////////////////////////////////////////////////////////////
     // Constructor methods
     //////////////////////////////////////////////////////////////////////

     public  ProcedureCallStatementMiniNode (
       NameMiniNode                nameMiniNode,
       ExpressionSequenceMiniNode  expressionSequenceMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this.nameMiniNode               = nameMiniNode;
       this.expressionSequenceMiniNode = expressionSequenceMiniNode;
     }

     public  ProcedureCallStatementMiniNode ( NameMiniNode  nameMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this ( nameMiniNode, null );
     }

     //////////////////////////////////////////////////////////////////////
     // Access methods
     //////////////////////////////////////////////////////////////////////

     public NameMiniNode  getNameMiniNode ( ) { return nameMiniNode; }

     public ExpressionSequenceMiniNode  getExpressionSequenceMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return expressionSequenceMiniNode;
     }

     public Boolean  callsNested ( ) { return callsNested; }

     //////////////////////////////////////////////////////////////////////
     // MiniNode interface methods
     //////////////////////////////////////////////////////////////////////

     public void  generate ( MiniNodeCodeVisitor  miniNodeCodeVisitor )
     //////////////////////////////////////////////////////////////////////
     {
       miniNodeCodeVisitor.generateProcedureCallStatement ( this );
     }
     
     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       parentMiniNodeStack.push ( this );

       if ( expressionSequenceMiniNode != null )
       {
         expressionSequenceMiniNode.checkSemantics ( parentMiniNodeStack );
       }

       int  size = parentMiniNodeStack.size ( );

       boolean  wasDeclared = false;

       outerLoop:
       for ( int  i = size - 2; i >= 0; i-- )
       {
         MiniNode  miniNode = ( MiniNode ) parentMiniNodeStack.get ( i );

         if ( !( miniNode instanceof BlockMiniNode ) ) continue;

         DeclarationSequenceMiniNode  declarationSequenceMiniNode
           = ( ( BlockMiniNode ) miniNode
           ).getDeclarationSequenceMiniNode ( );

         if ( declarationSequenceMiniNode == null ) continue;

         List  declarationMiniNodeList
           = declarationSequenceMiniNode.getDeclarationMiniNodeList ( );

         Iterator  j = declarationMiniNodeList.iterator ( );

         while ( j.hasNext ( ) )
         {
           DeclarationMiniNode  declarationMiniNode
             = ( DeclarationMiniNode ) j.next ( );

           if ( !( declarationMiniNode instanceof
             ProcedureDeclarationMiniNode ) ) continue;

           ProcedureDeclarationMiniNode  procedureDeclarationMiniNode
             = ( ProcedureDeclarationMiniNode ) declarationMiniNode;

           ParameterSequenceMiniNode  parameterSequenceMiniNode
             = procedureDeclarationMiniNode.getParameterSequenceMiniNode (
             );

           if ( nameMiniNode.equals (
             procedureDeclarationMiniNode.getNameMiniNode ( ) )
             && ( expressionSequenceMiniNode.size ( )
                == parameterSequenceMiniNode.size ( ) ) )
           {
             callsNested = procedureDeclarationMiniNode.containsNested ( );
             wasDeclared = true;
             break outerLoop;
           }
         }
       }

       if ( !wasDeclared )
       {
         throw new SemanticErrorException ( "procedure undeclared" );
       }

       parentMiniNodeStack.pop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
