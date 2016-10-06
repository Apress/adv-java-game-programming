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
     *   1999-04-27
     *********************************************************************/

     public class  ProcedureDeclarationMiniNode extends AbstractMiniNode
       implements DeclarationMiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected NameMiniNode               nameMiniNode;
     protected ParameterSequenceMiniNode  parameterSequenceMiniNode;
     protected BlockMiniNode              blockMiniNode;

     protected Boolean                    containsNested;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ProcedureDeclarationMiniNode (
       NameMiniNode               nameMiniNode,
       ParameterSequenceMiniNode  parameterSequenceMiniNode,
       BlockMiniNode              blockMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this.nameMiniNode              = nameMiniNode;
       this.parameterSequenceMiniNode = parameterSequenceMiniNode;
       this.blockMiniNode             = blockMiniNode;
     }

     //////////////////////////////////////////////////////////////////////
     // Access methods
     //////////////////////////////////////////////////////////////////////

     public NameMiniNode  getNameMiniNode ( ) { return nameMiniNode; }

     public ParameterSequenceMiniNode  getParameterSequenceMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return parameterSequenceMiniNode;
     }

     public BlockMiniNode  getBlockMiniNode ( ) { return blockMiniNode; }

     public Boolean  containsNested ( ) { return containsNested; }

     public NameSequenceMiniNode  getNameSequenceMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return new NameSequenceMiniNode ( nameMiniNode );
     }

     //////////////////////////////////////////////////////////////////////
     // MiniNode interface methods
     //////////////////////////////////////////////////////////////////////

     public void  generate ( MiniNodeCodeVisitor  miniNodeCodeVisitor )
     //////////////////////////////////////////////////////////////////////
     {
       miniNodeCodeVisitor.generateProcedureDeclaration ( this );
     }
     
     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       DeclarationSequenceMiniNode  declarationSequenceMiniNode
         = blockMiniNode.getDeclarationSequenceMiniNode ( );

       boolean  containsNested = false;

       if ( declarationSequenceMiniNode != null )
       {
         List  declarationMiniNodeList
           = declarationSequenceMiniNode.getDeclarationMiniNodeList ( );

         Iterator  i = declarationMiniNodeList.iterator ( );

         while ( i.hasNext ( ) )
         {
           if ( i.next ( ) instanceof ProcedureDeclarationMiniNode )
           {
             containsNested = true;
             break;
           }
         }
       }

       this.containsNested = new Boolean ( containsNested );

       parentMiniNodeStack.push ( this );

       if ( blockMiniNode != null )
       {
         blockMiniNode.checkSemantics ( parentMiniNodeStack );
       }

       parentMiniNodeStack.pop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
