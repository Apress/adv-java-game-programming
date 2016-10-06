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

     public class  DeclarationSequenceMiniNode extends AbstractMiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected List  declarationMiniNodeList;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * @throws  SemanticErrorException
     *   If there duplicate names are declared.
     *********************************************************************/
     public  DeclarationSequenceMiniNode (
       DeclarationSequenceMiniNode  declarationSequenceMiniNode,
       DeclarationMiniNode          declarationMiniNode )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       if ( declarationSequenceMiniNode != null )
       {
         declarationMiniNodeList
           = declarationSequenceMiniNode.declarationMiniNodeList;
       }
       else
       {
         declarationMiniNodeList = new LinkedList ( );
       }

       declarationMiniNodeList.add ( declarationMiniNode );

       checkDuplicates ( );

     }

     /*********************************************************************
     * @throws  SemanticErrorException
     *   If there duplicate names are declared.
     *********************************************************************/
     public  DeclarationSequenceMiniNode (
       DeclarationMiniNode  declarationMiniNode )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       this ( null, declarationMiniNode );
     }

     /*********************************************************************
     * @throws  SemanticErrorException
     *   If there duplicate names are declared.
     *********************************************************************/
     protected void  checkDuplicates ( ) throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       boolean  hasDuplicates = false;

       Set  nameSet = new HashSet ( );

       Iterator  i = declarationMiniNodeList.iterator ( );
       while ( i.hasNext ( ) )
       {
         NameSequenceMiniNode  nameSequenceMiniNode
           = ( ( DeclarationMiniNode ) i.next ( )
           ).getNameSequenceMiniNode ( );

         Iterator  j
           = nameSequenceMiniNode.getNameMiniNodeList ( ).iterator ( );

         while ( j.hasNext ( ) )
         {
           NameMiniNode  nameMiniNode = ( NameMiniNode ) j.next ( );

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
       }

       if ( hasDuplicates )
       {
         throw new IllegalArgumentException (
           "duplicate names declared" );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // Access methods
     //////////////////////////////////////////////////////////////////////

     public List  getDeclarationMiniNodeList ( )
     //////////////////////////////////////////////////////////////////////
     {
       return declarationMiniNodeList;
     }

     public boolean  declares ( NameMiniNode  nameMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       Iterator  i = declarationMiniNodeList.iterator ( );

       while ( i.hasNext ( ) )
       {
         DeclarationMiniNode  declarationMiniNode
           = ( DeclarationMiniNode ) i.next ( );

         Iterator  j = declarationMiniNode.getNameSequenceMiniNode (
           ).getNameMiniNodeList ( ).iterator ( );

         while ( j.hasNext ( ) )
         {
           if ( nameMiniNode.equals ( j.next ( ) ) )
           {
             return true;
           }
         }
       }

       return false;
     }

     //////////////////////////////////////////////////////////////////////
     // MiniNode interface methods
     //////////////////////////////////////////////////////////////////////

     public void  generate ( MiniNodeCodeVisitor  miniNodeCodeVisitor )
     //////////////////////////////////////////////////////////////////////
     {
       miniNodeCodeVisitor.generateDeclarationSequence ( this );
     }

     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       parentMiniNodeStack.push ( this );

       MiniNodeLib.checkSemantics (
         declarationMiniNodeList, parentMiniNodeStack );

       parentMiniNodeStack.pop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
