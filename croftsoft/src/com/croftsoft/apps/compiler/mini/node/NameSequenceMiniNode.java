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

     public class  NameSequenceMiniNode extends AbstractMiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected List  nameMiniNodeList;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  NameSequenceMiniNode (
       NameSequenceMiniNode  nameSequenceMiniNode,
       NameMiniNode          nameMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       if ( nameSequenceMiniNode != null )
       {
         nameMiniNodeList = nameSequenceMiniNode.nameMiniNodeList;
       }
       else
       {
         nameMiniNodeList = new LinkedList ( );
       }

       nameMiniNodeList.add ( nameMiniNode );
     }

     public  NameSequenceMiniNode ( NameMiniNode  nameMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null, nameMiniNode );
     }

     //////////////////////////////////////////////////////////////////////
     // Access methods
     //////////////////////////////////////////////////////////////////////

     public List  getNameMiniNodeList ( ) { return nameMiniNodeList; }

     public boolean  contains ( NameMiniNode  nameMiniNode )
     //////////////////////////////////////////////////////////////////////
     {
       return nameMiniNodeList.contains ( nameMiniNode );
     }

     public int  size ( )
     //////////////////////////////////////////////////////////////////////
     {
       return nameMiniNodeList.size ( );
     }

     //////////////////////////////////////////////////////////////////////
     // MiniNode interface methods
     //////////////////////////////////////////////////////////////////////

     public void  generate ( MiniNodeCodeVisitor  miniNodeCodeVisitor )
     //////////////////////////////////////////////////////////////////////
     {
       miniNodeCodeVisitor.generateNameSequence ( this );
     }

     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       parentMiniNodeStack.push ( this );

       MiniNodeLib.checkSemantics (
         nameMiniNodeList, parentMiniNodeStack );

       parentMiniNodeStack.pop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
