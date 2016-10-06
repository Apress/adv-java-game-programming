     package com.croftsoft.apps.tab2xml;

     import java.io.*;
     import java.util.*;

     /*********************************************************************
     * Converts a tab-delimited file to XML.
     *
     * @version
     *   2002-11-13
     * @since
     *   2002-11-13
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public final class  Tab2Xml
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       FileReader  fileReader = new FileReader ( args [ 0 ] );

       BufferedReader  bufferedReader = new BufferedReader ( fileReader );

       List  tokenList = new ArrayList ( );

       String  line = bufferedReader.readLine ( );

       StringTokenizer  stringTokenizer
         = new StringTokenizer ( line, "\t" );

       while ( stringTokenizer.hasMoreTokens ( ) )
       {
         tokenList.add ( stringTokenizer.nextToken ( ).toLowerCase ( ) );
       }

       String [ ]  columnHeaders
         = ( String [ ] ) tokenList.toArray ( new String [ 0 ] );

       int  columnCount = columnHeaders.length;

       PrintWriter  printWriter = new PrintWriter (
         new BufferedWriter ( new FileWriter ( args [ 1 ] ) ) );

       printWriter.println ( "<data>" );

       while ( ( line = bufferedReader.readLine ( ) ) != null )
       {
         int  columnIndex = 0;

         String [ ]  values = new String [ columnCount ];

         stringTokenizer = new StringTokenizer ( line, "\t", true );

         while ( stringTokenizer.hasMoreTokens ( ) )
         {
           String  token = stringTokenizer.nextToken ( );

           if ( "\t".equals ( token ) )
           {
             ++columnIndex;
           }
           else
           {
             values [ columnIndex ] = token;
           }
         }

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

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
