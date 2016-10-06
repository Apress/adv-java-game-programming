     package com.croftsoft.apps.tab2xml;

     import java.io.*;
     import java.util.*;

     /*********************************************************************
     * Converts a comma separated value (CSV) file to XML.
     *
     * @version
     *   2003-05-12
     * @since
     *   2003-05-12
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Csv2Xml
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String [ ]  EMPTY_STRING_ARRAY
       = new String [ 0 ];

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       convert ( args [ 0 ], args [ 1 ] );
     }

     public static void  convert (
       String  inputCsvFilename,
       String  outputXmlFilename )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       FileReader  fileReader = new FileReader ( inputCsvFilename );

       BufferedReader  bufferedReader = new BufferedReader ( fileReader );

       String  line = bufferedReader.readLine ( );

       String [ ]  columnHeaders = parseLine ( line );

       int  columnCount = columnHeaders.length;

       PrintWriter  printWriter = new PrintWriter (
         new BufferedWriter ( new FileWriter ( outputXmlFilename ) ) );

       printWriter.println ( "<data>" );

       while ( ( line = bufferedReader.readLine ( ) ) != null )
       {
         int  columnIndex = 0;

         String [ ]  values = parseLine ( line );

         printWriter.println ( "  <record>" );

         for ( int  i = 0; i < columnCount; i++ )
         {
           if ( values [ i ] != null )
           {
             printWriter.println (
               "    <" + columnHeaders [ i ] + ">"
               + values [ i ]
               + "</" + columnHeaders [ i ] + ">" );
           }
         }

         printWriter.println ( "  </record>" );
       }

       printWriter.println ( "</data>" );

       printWriter.flush ( );

       printWriter.close ( );
     }

     public static String [ ]  parseLine ( String  line )
     //////////////////////////////////////////////////////////////////////
     {
       List  stringList = new ArrayList ( );

       StringTokenizer  stringTokenizer
         = new StringTokenizer ( line, ",\"", true );

       boolean  inQuotes = false;

       boolean  stringStarted = false;

       StringBuffer  stringBuffer = new StringBuffer ( );

       while ( stringTokenizer.hasMoreTokens ( ) )
       {
         String  token = stringTokenizer.nextToken ( );

         if ( token.equals ( "\"" ) )
         {
           if ( inQuotes )
           {
             inQuotes = false;

             stringList.add ( stringBuffer.toString ( ) );

             stringBuffer = new StringBuffer ( );

             stringStarted = false;
           }
           else
           {
             inQuotes = true;

             stringStarted = true;

             stringBuffer = new StringBuffer ( );
           }
         }
         else if ( token.equals ( "," ) )
         {
           if ( inQuotes )
           {
             stringBuffer.append ( "," );
           }
           else
           {
             if ( stringStarted )
             {
               stringList.add ( stringBuffer.toString ( ) );

               stringBuffer = new StringBuffer ( );
             }

             stringStarted = true;
           }
         }
         else
         {
           if ( !stringStarted )
           {
             stringStarted = true;

             stringBuffer = new StringBuffer ( );
           }

           stringBuffer.append ( token );
         }
       }

       if ( stringStarted )
       {
         stringList.add ( stringBuffer.toString ( ) );
       }

       return ( String [ ] ) stringList.toArray ( EMPTY_STRING_ARRAY );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private Csv2Xml ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
