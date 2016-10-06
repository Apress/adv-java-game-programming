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
     *   2001-11-09
     * @since
     *   2001-08-08
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastField
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  SML_NODE_NAME = "field";

     public static final int  TYPE_NUMBER = 0;

     public static final int  TYPE_STRING = 1;

     //

     private final String      name;

     private final String      value;

     private final int         type;

     private final boolean     isReverse;

     private final String [ ]  choices;

     private final String      semantic;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static AgoracastField  fromSmlNode ( SmlNode  smlNode )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !SML_NODE_NAME.equals ( smlNode.getName ( ) ) )
       {
         throw new IllegalArgumentException ( smlNode.getName ( ) );
       }

       String   name       = smlNode.getString  ( "name"      );

       String   value      = smlNode.getString  ( "value"     );

       int      type       = smlNode.getInt     ( "type", TYPE_STRING );

       boolean  isReverse  = smlNode.getBoolean ( "isReverse", false );

       String [ ]  choices = smlNode.getStrings ( "choice" );

       String   semantic   = smlNode.getString  ( "semantic"  );

       return new AgoracastField (
         name, value, type, isReverse, choices, semantic );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastField (
       String      name,
       String      value,
       int         type,
       boolean     isReverse,
       String [ ]  choices,
       String      semantic )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.name = name );

       this.value     = value;

       this.type      = type;

       this.isReverse = isReverse;

       this.choices   = choices;

       this.semantic  = semantic;

       if ( type < 0 || type > 1 )
       {
         throw new IllegalArgumentException ( "unknown type:  " + type );
       }
     }
      
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String      getName     ( ) { return name;      }

     public String      getValue    ( ) { return value;     }

     public int         getType     ( ) { return type;      }

     public boolean     isReverse   ( ) { return isReverse; }

     public String [ ]  getChoices  ( ) { return choices;   }

     public String      getSemantic ( ) { return semantic;  }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int  compare (
       String  value1,
       String  value2 )
     //////////////////////////////////////////////////////////////////////
     {
       if ( ( value1 == null )
         && ( value2 == null ) )
       {
         return 0;
       }

       if ( value1 == null )
       {
         return -1;
       }

       if ( value2 == null )
       {
         return 1;
       }

       if ( type == TYPE_STRING )
       {
         return value1.compareTo ( value2 );
       }

       Double  d1 = null;

       try
       {
         String  s1 = ParseLib.stripNonNumbers ( value1 );

         d1 = new Double ( s1 );
       }
       catch ( NumberFormatException  ex )
       {
         return -1;
       }

       Double  d2 = null;

       try
       {
         String  s2 = ParseLib.stripNonNumbers ( value2 );

         d2 = new Double ( s2 );
       }
       catch ( NumberFormatException  ex )
       {
         return 1;
       }

       return d1.compareTo ( d2 );
     }

     public synchronized SmlNode  toSmlNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       SmlNode  rootSmlNode = new SmlNode ( SML_NODE_NAME );

       rootSmlNode.add ( new SmlNode ( "name" , name  ) );

       rootSmlNode.add ( new SmlNode ( "value", value ) );

       rootSmlNode.add (
         new SmlNode ( "type", Integer.toString ( type ) ) );

       rootSmlNode.add (
         new SmlNode ( "isReverse", isReverse ? "1" : "0" ) );

       if ( choices != null )
       {
         for ( int  i = 0; i < choices.length; i++ )
         {
           rootSmlNode.add ( new SmlNode ( "choice", choices [ i ] ) );
         }
       }

       rootSmlNode.add ( new SmlNode ( "semantic", semantic ) );

       return rootSmlNode;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }