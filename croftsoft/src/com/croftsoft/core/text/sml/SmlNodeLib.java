     package com.croftsoft.core.text.sml;

     import java.io.*;
     import java.util.StringTokenizer;
     import java.util.Vector;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.StringLib;

     /*********************************************************************
     * A library of static methods for manipulating SmlNode objects.
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     *
     * @version
     *   2001-05-14
     * @since
     *   2001-05-10
     * @author
     *   <a href="http://www.alumni.caltech.edu/~croft/">David W. Croft</a>
     *********************************************************************/

     public final class  SmlNodeLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( parse ( "<abc/>" ) );
       System.out.println ( parse ( "<abc></abc>" ) );
       System.out.println ( parse ( "<abc>def</abc>" ) );
       System.out.println ( parse ( "<abc><def/></abc>" ) );
       System.out.println ( parse ( "<abc><def></def></abc>" ) );
       System.out.println ( parse ( "<abc><def>ghi</def></abc>" ) );
     }
          
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static boolean  isCDataCharacter ( char  c )
     //////////////////////////////////////////////////////////////////////
     {
// check to see what XML really defines

       return ( c != '<' )
         &&   ( c != '>' );
     }

     public static boolean  isElementNameCharacter ( char  c )
     //////////////////////////////////////////////////////////////////////
     {
// check to see what XML really defines

       return
         !isWhiteSpaceCharacter ( c )
         && ( c != '<' )
         && ( c != '>' )
         && ( c != '/' );
     }

     public static boolean  isWhiteSpaceCharacter ( char  c )
     //////////////////////////////////////////////////////////////////////
     {
// check to see what XML really defines

       return Character.isWhitespace ( c );
     }

     public static SmlNode  load (
       String   filename,
       boolean  allowMixedChildren )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       SmlNodeParseHandler  smlNodeParseHandler
         = new SmlNodeParseHandler ( allowMixedChildren );

       BufferedInputStream  in = null;

       try
       {
         in = new BufferedInputStream (
           new FileInputStream ( filename ) );

         SmlNodeLib.parse ( in, smlNodeParseHandler );
       }
       finally
       {
         if ( in != null )
         {
           in.close ( );
         }
       }

       return smlNodeParseHandler.getSmlNode ( );
     }

     public static SmlNode  parse ( String  smlString )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( smlString );

       StringTokenizer  stringTokenizer
         = new StringTokenizer ( smlString, "<>", true );

       Vector  stackVector = new Vector ( );

       SmlNode  currentSmlNode = null;

       while ( stringTokenizer.hasMoreTokens ( ) )
       {
         String  token = stringTokenizer.nextToken ( );
         
         if ( token.equals ( "<" ) )
         {
           token = stringTokenizer.nextToken ( );
           
           if ( token.startsWith ( "/" ) )
           {
             if ( !currentSmlNode.hasChild ( ) )
             {
               currentSmlNode.add ( "" );
             }
             
             if ( !stackVector.isEmpty ( ) )
             {
               // stack pop

               int  index = stackVector.size ( ) - 1;

               currentSmlNode = ( SmlNode ) stackVector.elementAt ( index );

               stackVector.removeElementAt ( index );
             }
             else
             {
               return currentSmlNode;
             }
           }
           else
           {
             String  name = token;
             
             boolean  isParent = true;
             
             if ( token.endsWith ( "/" ) )
             {
               name = token.substring ( 0, token.length ( ) - 1 );
               
               isParent = false;
             }

             SmlNode  childSmlNode = new SmlNode ( name );
             
             if ( currentSmlNode == null )
             {
               if ( !isParent )
               {
                 return childSmlNode;
               }

               currentSmlNode = childSmlNode;
             }
             else
             {
               currentSmlNode.add ( childSmlNode );
               
               if ( isParent )
               {
                 stackVector.addElement ( currentSmlNode );
                 
                 currentSmlNode = childSmlNode;
               }
             }
           }               

           stringTokenizer.nextToken ( ); // >
         }
         else
         {
           try
           {
             if ( currentSmlNode != null )
             {
               currentSmlNode.add ( SmlCoder.decode ( token ) );
             }
           }
           catch ( NullPointerException  ex )
           {
             throw new IllegalArgumentException (
               "Missing or mismatched angle brackets:  \""
               + smlString + "\"" );
           }
         } 
       }

       throw new IllegalArgumentException (
         "Missing or mismatched angle brackets:  \""
         + smlString + "\"" );
     }

     /*********************************************************************
     * Parses an SML stream.
     *
     * <p>
     * State Transitions:
     * <pre>
     * c == character data (cdata) character (excludes angle brackets)
     * w == white space character
     * e == element (tag) name character
     *
     * (+)     == saves character to buffer
     * (cdata) == calls handleCData
     * (open)  == calls handleElementOpen
     * (close) == calls handleElementClose
     *
     * 0 | c --> 0 (+), < --> 1 (cdata)
     * 1 | w --> 2, e --> 3 (+), / --> 6
     * 2 | w --> 2, e --> 3 (+)
     * 3 | e --> 3 (+), w --> 4, / --> 5, > --> 0 (open)
     * 4 | w --> 4, / --> 5, > --> 0 (open)
     * 5 | > --> 0 (open, close)
     * 6 | w --> 6, e --> 7 (+)
     * 7 | e --> 7 (+), w --> 8, > --> 0 (close)
     * 8 | w --> 8, > --> 0 (close)
     *
     *  0 | reading cdata, seeking <
     *  1 | just after <; seeking element (tag) name, white space, or /
     *  2 | inside white space before tag name; seeking tag name
     *  3 | reading opening tag name; seeking white space, /, or >
     *  4 | inside white space after opening tag name; seeking / or >
     *  5 | found / after tag name, element has no children; seeking >
     *  6 | found / after <, closing tag; skipping white, seeking tag name
     *  7 | reading closing tag name; seeking white space or >
     *  8 | inside white space after closing tag name; seeking >
     * </pre>
     * </p>
     *********************************************************************/
     public static void  parse (
       InputStream      inputStream,
       SmlParseHandler  smlParseHandler )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( inputStream );

       NullArgumentException.check ( smlParseHandler );

       StringBuffer  stringBuffer = new StringBuffer ( );

       int  state = 0;

       int  i;

       while ( ( i = inputStream.read ( ) ) > -1 )
       {
         char  c = ( char ) i;

         switch ( state )
         {
           case 0:

             // c --> 0 (+), < --> 1 (cdata)
             // reading cdata, seeking <

             if ( isCDataCharacter ( c ) )
             {
               stringBuffer.append ( c );
             }
             else if ( c == '<' )
             {
               state = 1;

               smlParseHandler.handleCData ( stringBuffer.toString ( ) );

               stringBuffer = new StringBuffer ( );
             }
             else
             {
               smlParseHandler.handleParseError ( );
             }
 
             break;

           case 1:
 
             // w --> 2, e --> 3 (+), / --> 6
             // just after <; seeking tag name, white space, or /

             if ( isWhiteSpaceCharacter ( c ) )
             {
               state = 2;
             }
             else if ( isElementNameCharacter ( c ) )
             {
               state = 3;

               stringBuffer.append ( c );
             }
             else if ( c == '/' )
             {
               state = 6;
             }
             else
             {
               smlParseHandler.handleParseError ( );
             }
 
             break;

           case 2:

             // w --> 2, e --> 3 (+)
             // inside white space before tag name; seeking tag name

             if ( isWhiteSpaceCharacter ( c ) )
             {
             }
             else if ( isElementNameCharacter ( c ) )
             {
               state = 3;

               stringBuffer.append ( c );
             }
             else
             {
               smlParseHandler.handleParseError ( );
             }
 
             break;

           case 3:

             // e --> 3 (+), w --> 4, / --> 5, > --> 0 (open)
             // reading opening tag name; seeking white space, /, or >

             if ( isElementNameCharacter ( c ) )
             {
               stringBuffer.append ( c );
             }
             else if ( isWhiteSpaceCharacter ( c ) )
             {
               state = 4;
             }
             else if ( c == '/' )
             {
               state = 5;
             }
             else if ( c == '>' )
             {
               state = 0;

               smlParseHandler.handleElementOpen ( stringBuffer.toString ( ) );

               stringBuffer = new StringBuffer ( );
             }
             else
             {
               smlParseHandler.handleParseError ( );
             }
 
             break;

           case 4:

             // w --> 4, / --> 5, > --> 0 (open)
             // inside white space after opening tag name; seeking / or >

             if ( isWhiteSpaceCharacter ( c ) )
             {
             }
             else if ( c == '/' )
             {
               state = 5;
             }
             else if ( c == '>' )
             {
               state = 0;

               smlParseHandler.handleElementOpen ( stringBuffer.toString ( ) );

               stringBuffer = new StringBuffer ( );
             }
             else
             {
               smlParseHandler.handleParseError ( );
             }
 
             break;

           case 5:

             // > --> 0 (open, close)
             // found / after tag name, element has no children; seeking >

             if ( c == '>' )
             {
               state = 0;

               String  elementName = stringBuffer.toString ( );

               smlParseHandler.handleElementOpen ( elementName );

               smlParseHandler.handleElementClose ( elementName );

               stringBuffer = new StringBuffer ( );
             }
             else
             {
               smlParseHandler.handleParseError ( );
             }
 
             break;

           case 6:

             // w --> 6, e --> 7 (+)
             // found / after <, closing tag; skipping white, seeking tag name

             if ( isWhiteSpaceCharacter ( c ) )
             {
             }
             else if ( isElementNameCharacter ( c ) )
             {
               state = 7;

               stringBuffer.append ( c );
             }
             else
             {
               smlParseHandler.handleParseError ( );
             }
 
             break;

           case 7:

             // e --> 7 (+), w --> 8, > --> 0 (close)
             // reading closing tag name; seeking white space or >

             if ( isElementNameCharacter ( c ) )
             {
               stringBuffer.append ( c );
             }
             else if ( isWhiteSpaceCharacter ( c ) )
             {
               state = 8;
             }
             else if ( c == '>' )
             {
               state = 0;

               smlParseHandler.handleElementClose ( stringBuffer.toString ( ) );

               stringBuffer = new StringBuffer ( );
             }
             else
             {
               smlParseHandler.handleParseError ( );
             }
 
             break;

           case 8:

             // w --> 8, > --> 0 (close)
             // inside white space after closing tag name; seeking >

             if ( isWhiteSpaceCharacter ( c ) )
             {
             }
             else if ( c == '>' )
             {
               state = 0;

               smlParseHandler.handleElementClose ( stringBuffer.toString ( ) );

               stringBuffer = new StringBuffer ( );
             }
             else
             {
               smlParseHandler.handleParseError ( );
             }
 
             break;

           default:

             throw new RuntimeException ( ); 
         }
       }
     }

     public static void  save (
       String   filename,
       SmlNode  smlNode )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       PrintWriter  printWriter = null;

       try
       {
         printWriter = new PrintWriter (
           new BufferedWriter ( new FileWriter ( filename ) ) );

         printWriter.println ( smlNode.toString ( 0, 2 ) );
       }
       finally
       {
         if ( printWriter != null )
         {
           printWriter.close ( );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  SmlNodeLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
