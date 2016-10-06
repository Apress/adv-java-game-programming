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

     public class  NameMiniNode
        extends AbstractMiniNode implements ElementMiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected String  name;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  NameMiniNode ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       this.name = name;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getName ( ) { return name; }

     //////////////////////////////////////////////////////////////////////
     // MiniNode interface methods
     //////////////////////////////////////////////////////////////////////

     public void  generate ( MiniNodeCodeVisitor  miniNodeCodeVisitor )
     //////////////////////////////////////////////////////////////////////
     {
       miniNodeCodeVisitor.generateName ( this );
     }

     public void  checkSemantics ( Stack  parentMiniNodeStack )
       throws SemanticErrorException
     //////////////////////////////////////////////////////////////////////
     {
       int  size = parentMiniNodeStack.size ( );

       boolean  wasDeclared = false;

       for ( int  i = size - 1; i >= 0; i-- )
       {
         MiniNode  miniNode = ( MiniNode ) parentMiniNodeStack.get ( i );

         if ( miniNode instanceof BlockMiniNode )
         {
           DeclarationSequenceMiniNode  declarationSequenceMiniNode
             = ( ( BlockMiniNode ) miniNode
             ).getDeclarationSequenceMiniNode ( );

           if ( ( declarationSequenceMiniNode != null )
             && declarationSequenceMiniNode.declares ( this ) )
           {
             wasDeclared = true;
             break;
           }
         }
         else if ( miniNode instanceof ProcedureDeclarationMiniNode )
         {
           ParameterSequenceMiniNode  parameterSequenceMiniNode
             = ( ( ProcedureDeclarationMiniNode ) miniNode
             ).getParameterSequenceMiniNode ( );

           if ( ( parameterSequenceMiniNode != null )
             && parameterSequenceMiniNode.declares ( this ) )
           {
             wasDeclared = true;
             break;
           }
         }
       }

       if ( !wasDeclared )
       {
         throw new SemanticErrorException ( "undeclared name" );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  equals ( Object  other )
     //////////////////////////////////////////////////////////////////////
     {
       if ( other == null ) return false;

       if ( !getClass ( ).equals ( other.getClass ( ) ) ) return false;

       return name.equals ( ( ( NameMiniNode ) other ).name );
     }

     public int  hashCode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return name.hashCode ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
