     package com.croftsoft.core.text.sml;

     import java.util.Vector;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.text.ParseLib;

     /*********************************************************************
     * Simplified Markup Language (SML) node.
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     *
     * @version
     *   2001-09-12
     * @since
     *   2001-03-05
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SmlNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final Object [ ]  ZERO_LENGTH_OBJECT_ARRAY
       = new Object [ ] { };

     private String      name;

     private Object [ ]  children = ZERO_LENGTH_OBJECT_ARRAY;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  SmlNode ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.name = name );
     }

     public  SmlNode (
       String  name,
       String  childString )
     //////////////////////////////////////////////////////////////////////
     {
       this ( name );

       if ( childString != null )
       {
         add ( childString );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public int  childCount ( )
     //////////////////////////////////////////////////////////////////////
     {
       return children.length;
     }

     public String  getName ( )
     //////////////////////////////////////////////////////////////////////
     {
       return name;
     }

     /*********************************************************************
     * @return
     *   Returns zero-length array if childless; never returns null.
     *********************************************************************/
     public Object [ ]  getChildren ( )
     //////////////////////////////////////////////////////////////////////
     {
       return children;
     }

     public Object  getChild ( int  index )
     //////////////////////////////////////////////////////////////////////
     {
       if ( index >= children.length )
       {
         return null;
       }
       
       return children [ index ];
     }

     /*********************************************************************
     * Returns the first SmlNode child with the given name.
     *
     * @return
     *
     *   May return null.
     *********************************************************************/
     public SmlNode  getChildNode ( String  childNodeName )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( childNodeName );

       Object [ ]  children = this.children;

       for ( int  i = 0; i < children.length; i++ )
       {
         Object  child = children [ i ];

         if ( child instanceof SmlNode )
         {
           SmlNode  childSmlNode = ( SmlNode ) child;

           if ( childSmlNode.getName ( ).equals ( childNodeName ) )
           {
             return childSmlNode;
           }
         }
       }

       return null;
     }

     /*********************************************************************
     * Returns all SmlNode children with the given name.
     *
     * @return
     *
     *   May return an empty array.
     *********************************************************************/
     public SmlNode [ ]  getChildNodes ( String  childNodeName )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( childNodeName );

       Object [ ]  children = this.children;

       Vector  childNodeVector = new Vector ( );

       for ( int  i = 0; i < children.length; i++ )
       {
         Object  child = children [ i ];

         if ( child instanceof SmlNode )
         {
           SmlNode  childSmlNode = ( SmlNode ) child;

           if ( childSmlNode.getName ( ).equals ( childNodeName ) )
           {
             childNodeVector.addElement ( childSmlNode );
           }
         }
       }

       SmlNode [ ]  childNodes = new SmlNode [ childNodeVector.size ( ) ];

       childNodeVector.copyInto ( childNodes );

       return childNodes;
     }

     /*********************************************************************
     * return ParseLib.parseBoolean ( getString ( childNodeName ), def );
     *********************************************************************/
     public boolean  getBoolean ( String  childNodeName, boolean  def )
     //////////////////////////////////////////////////////////////////////
     {
       return ParseLib.parseBoolean ( getString ( childNodeName ), def );
     }

     /*********************************************************************
     * return ParseLib.parseInt ( getString ( childNodeName ), def );
     *********************************************************************/
     public int  getInt ( String  childNodeName, int  def )
     //////////////////////////////////////////////////////////////////////
     {
       return ParseLib.parseInt ( getString ( childNodeName ), def );
     }

     /*********************************************************************
     * return ParseLib.parseLong ( getString ( childNodeName ), def );
     *********************************************************************/
     public long  getLong ( String  childNodeName, int  def )
     //////////////////////////////////////////////////////////////////////
     {
       return ParseLib.parseLong ( getString ( childNodeName ), def );
     }

     /*********************************************************************
     * Returns the named child node's first child as cast to a String.
     *
     * @return
     *
     *   May return null.
     *********************************************************************/
     public String  getString ( String  childNodeName )
     //////////////////////////////////////////////////////////////////////
     {
       SmlNode  childNode = getChildNode ( childNodeName );

       if ( childNode == null )
       {
         return null;
       }

       return SmlCoder.decode ( ( String ) childNode.getChild ( 0 ) );
     }

     /*********************************************************************
     * Finds direct children with the given name and returns their values.
     *
     * @return
     *
     *   May return an empty array.
     *********************************************************************/
     public String [ ]  getStrings ( String  childNodeName )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( childNodeName );

       Object [ ]  children = this.children;

       Vector  childStringVector = new Vector ( );

       for ( int  i = 0; i < children.length; i++ )
       {
         Object  child = children [ i ];

         if ( child instanceof SmlNode )
         {
           SmlNode  childSmlNode = ( SmlNode ) child;

           if ( childSmlNode.getName ( ).equals ( childNodeName ) )
           {
             childStringVector.addElement ( SmlCoder.decode (
               ( String ) childSmlNode.getChild ( 0 ) ) );
           }
         }
       }

       String [ ]  strings = new String [ childStringVector.size ( ) ];

       childStringVector.copyInto ( strings );

       return strings;
     }

     public boolean  hasChild ( )
     //////////////////////////////////////////////////////////////////////
     {
       return children.length > 0;
     }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  add ( Object  smlNodeOrString )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( smlNodeOrString );

       synchronized ( this )
       {
         int  childCount = children.length;

         Object [ ]  newChildren = new Object [ childCount + 1 ];

         System.arraycopy ( children, 0, newChildren, 0, childCount );

         newChildren [ childCount ] = smlNodeOrString;

         children = newChildren;
       }
     }

     public void  removeChildren ( )
     //////////////////////////////////////////////////////////////////////
     {
       children = ZERO_LENGTH_OBJECT_ARRAY;
     }

     //////////////////////////////////////////////////////////////////////
     // object methods
     //////////////////////////////////////////////////////////////////////

     public String  toString ( )
     //////////////////////////////////////////////////////////////////////
     {
       return toString ( -1, 0 );
     }

     public String  toString ( int  indent, int  tabSize )
     //////////////////////////////////////////////////////////////////////
     {
       StringBuffer stringBuffer = new StringBuffer ( );

       for ( int  i = 0; i < indent; i++ )
       {
         stringBuffer.append ( ' ' );
       }

       stringBuffer.append ( '<' );

       stringBuffer.append ( name );

       Object [ ]  children = this.children;
         
       if ( children.length < 1 )
       {
         stringBuffer.append ( "/>" );
       }
       else
       {
         stringBuffer.append ( ">" );

         Object  child = null;

         for ( int  i = 0; i < children.length; i++ )
         {
           child = children [ i ];
           
           if ( child instanceof SmlNode )
           {
             if ( indent > -1 )
             {
               stringBuffer.append ( '\n' );
             }

             stringBuffer.append ( ( ( SmlNode ) child )
               .toString ( indent + tabSize, tabSize ) );
           }
           else
           {
             stringBuffer.append (
               SmlCoder.encode ( child.toString ( ) ) );
           }
         }

         if ( child instanceof SmlNode )
         {
           if ( indent > -1 )
           {
             stringBuffer.append ( '\n' );

             for ( int  i = 0; i < indent; i++ )
             {
               stringBuffer.append ( ' ' );
             }
           }
         }

         stringBuffer.append ( "</" );

         stringBuffer.append ( name );
         
         stringBuffer.append ( ">" );
       }

       return stringBuffer.toString ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }