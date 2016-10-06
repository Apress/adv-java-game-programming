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

     public class  IntegerDeclarationMiniNode extends AbstractMiniNode
       implements DeclarationMiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected NameSequenceMiniNode  nameSequenceMiniNode;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  IntegerDeclarationMiniNode (
       NameSequenceMiniNode  nameSequenceMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       if ( nameSequenceMiniNode == null )
       {
         throw new IllegalArgumentException (
           "null nameSequenceMiniNode" );
       }

       this.nameSequenceMiniNode = nameSequenceMiniNode;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public NameSequenceMiniNode  getNameSequenceMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return nameSequenceMiniNode;
     }

     //////////////////////////////////////////////////////////////////////
     // MiniNode interface methods
     //////////////////////////////////////////////////////////////////////

     public void  generate ( MiniNodeCodeVisitor  miniNodeCodeVisitor )
     //////////////////////////////////////////////////////////////////////
     {
       miniNodeCodeVisitor.generateIntegerDeclaration ( this );
     }
     
     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       parentMiniNodeStack.push ( this );

       nameSequenceMiniNode.checkSemantics ( parentMiniNodeStack );

       parentMiniNodeStack.pop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
