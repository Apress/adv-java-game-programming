     package com.croftsoft.agoracast.c2p;

     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.text.ParseLib;
     import com.croftsoft.core.text.sml.SmlNode;

     /*********************************************************************
     *
     * <p />
     *
     * @version
     *   2001-10-29
     * @since
     *   2001-09-21
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastCategory
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  SML_NODE_NAME = "category";

     //

     private final String      name;

     private final String      description;

     private final String [ ]  fieldNames;

     private final String [ ]  columnNames;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static AgoracastCategory  fromSmlNode ( SmlNode  smlNode )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !SML_NODE_NAME.equals ( smlNode.getName ( ) ) )
       {
         throw new IllegalArgumentException ( smlNode.getName ( ) );
       }

       String      name        = smlNode.getString  ( "name"        );

       String      description = smlNode.getString  ( "description" );

       String [ ]  fieldNames  = smlNode.getStrings ( "field"       );

       String [ ]  columnNames = smlNode.getStrings ( "column"      );

       return new AgoracastCategory (
         name, description, fieldNames, columnNames );
     }

     public static String [ ]  getNames (
       AgoracastCategory [ ]  categories )
     //////////////////////////////////////////////////////////////////////
     {
       String [ ]  names = new String [ categories.length ];

       for ( int  i = 0; i < categories.length; i++ )
       {
         names [ i ] = categories [ i ].getName ( );
       }

       return names;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastCategory (
       String      name,
       String      description,
       String [ ]  fieldNames,
       String [ ]  columnNames )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.name = name );

       this.description = description;

       this.fieldNames  = fieldNames;

       this.columnNames = columnNames;
     }
      
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String      getName        ( ) { return name;        }

     public String      getDescription ( ) { return description; }

     public String [ ]  getFieldNames  ( ) { return fieldNames;  }

     public String [ ]  getColumnNames ( ) { return columnNames; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized SmlNode  toSmlNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       SmlNode  rootSmlNode = new SmlNode ( SML_NODE_NAME );

       rootSmlNode.add ( new SmlNode ( "name" , name  ) );

       rootSmlNode.add ( new SmlNode ( "description", description ) );

       if ( fieldNames != null )
       {
         for ( int  i = 0; i < fieldNames.length; i++ )
         {
           rootSmlNode.add ( new SmlNode ( "field", fieldNames [ i ] ) );
         }
       }

       if ( columnNames != null )
       {
         for ( int  i = 0; i < columnNames.length; i++ )
         {
           rootSmlNode.add ( new SmlNode ( "column", columnNames [ i ] ) );
         }
       }

       return rootSmlNode;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }