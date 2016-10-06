     package com.croftsoft.core.text.sml;

     import java.util.*;

     /*********************************************************************
     * Constructs SmlNodes from parsed SML tokens.
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     *
     * @version
     *   2001-07-26
     * @since
     *   2001-05-10
     * @author
     *   <a href="http://www.alumni.caltech.edu/~croft/">David W. Croft</a>
     *********************************************************************/

     public final class  SmlNodeParseHandler
       implements SmlParseHandler
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private boolean  allowMixedChildren;

     private SmlNode  smlNode;

     private Vector   stackVector;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SmlNodeParseHandler ( boolean  allowMixedChildren )
     //////////////////////////////////////////////////////////////////////
     {
       this.allowMixedChildren = allowMixedChildren;

       stackVector = new Vector ( );
     }

     public  SmlNodeParseHandler ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( false );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public SmlNode  getSmlNode ( ) { return smlNode; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  handleCData ( String  cData )
     //////////////////////////////////////////////////////////////////////
     {
// System.out.println ( "handleCData(" + cData + ")" );

       if ( smlNode != null )
       {
         if ( allowMixedChildren
           || !smlNode.hasChild ( ) )
         {
           smlNode.add ( cData );
         }
       }
     }

     public void  handleElementOpen ( String  elementName )
     //////////////////////////////////////////////////////////////////////
     {
// System.out.println ( "handleElementOpen(" + elementName + ")" );

       SmlNode  smlNode = new SmlNode ( elementName );

       if ( this.smlNode != null )
       {
         if ( allowMixedChildren )
         {
           this.smlNode.add ( smlNode );
         }
         else
         {
           Object  firstChild = this.smlNode.getChild ( 0 );

           if ( firstChild instanceof String )
           {
             this.smlNode.getChildren ( ) [ 0 ] = smlNode;
           }
           else
           {
             this.smlNode.add ( smlNode );
           }
         }

         stackVector.addElement ( this.smlNode ); // stack push
       }
  
       this.smlNode = smlNode;
     }

     public void  handleElementClose ( String  elementName )
     //////////////////////////////////////////////////////////////////////
     {
// System.out.println ( "handleElementClose(" + elementName + ")" );

       int  index = stackVector.size ( ) - 1;

       if ( index > -1 )
       {
         this.smlNode = ( SmlNode ) stackVector.elementAt ( index );

         stackVector.removeElementAt ( index ); // stack pop
       }
     }

     public void  handleParseError ( )
     //////////////////////////////////////////////////////////////////////
     {
// System.out.println ( "handleParseError()" );

       throw new RuntimeException ( "bad SML stream" );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
