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

     public class  ParameterSequenceMiniNode extends AbstractMiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected NameSequenceMiniNode  nameSequenceMiniNode;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * @throws  SemanticErrorException
     *   If there are duplicate parameter names.
     *********************************************************************/
     public  ParameterSequenceMiniNode (
       NameSequenceMiniNode  nameSequenceMiniNode )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       this.nameSequenceMiniNode = nameSequenceMiniNode;

       List  nameMiniNodeList
         = nameSequenceMiniNode.getNameMiniNodeList ( );

       boolean  hasDuplicates = false;

       Set  nameSet = new HashSet ( );

       Iterator  i = nameMiniNodeList.iterator ( );
       while ( i.hasNext ( ) )
       {
         NameMiniNode  nameMiniNode = ( NameMiniNode ) i.next ( );

         String  name = nameMiniNode.getName ( );

         if ( nameSet.contains ( name ) )
         {
           hasDuplicates = true;
           break;
         }
         else
         {
           nameSet.add ( name );
         }
       }

       if ( hasDuplicates )
       {
         throw new SemanticErrorException (
           "duplicated parameter names" );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // Access methods
     //////////////////////////////////////////////////////////////////////

     public NameSequenceMiniNode  getNameSequenceMiniNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return nameSequenceMiniNode;
     }

     public boolean  declares ( NameMiniNode  nameMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       return nameSequenceMiniNode.contains ( nameMiniNode );
     }

     public int  size ( )
     //////////////////////////////////////////////////////////////////////
     {
       return nameSequenceMiniNode.size ( );
     }

     //////////////////////////////////////////////////////////////////////
     // MiniNode interface methods
     //////////////////////////////////////////////////////////////////////

     public void  generate ( MiniNodeCodeVisitor  miniNodeCodeVisitor )
     //////////////////////////////////////////////////////////////////////
     {
       miniNodeCodeVisitor.generateParameterSequence ( this );
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
