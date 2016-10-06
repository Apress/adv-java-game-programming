     package com.croftsoft.apps.src;

     import java.io.DataInputStream;
     import java.io.File;
     import java.io.FileInputStream;
     import java.io.FileOutputStream;
     import java.io.PrintStream;
     import java.util.StringTokenizer;

     /*********************************************************************
     * <P>
     * This java application searches through all of the *.java files
     * in the current directory and counts the number of lines.
     * Ignores blank lines.
     * <P>
     * @version
     *   1997-01-03
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     *********************************************************************/

     public class  LineCount {
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args ) {
     //////////////////////////////////////////////////////////////////////
//       if ( args.length != 2 ) {
//         System.out.println ( "java LineCount" );
//         return;
//       }
       String  path = ".";
       File  dir = new File ( path );
       String [ ]  file_list = dir.list ( );
       long  lineCount = 0;
       for ( int  i = 0; i < file_list.length; i++ ) {
         File  file = new File ( dir, file_list [ i ] );
         if ( !file.isFile ( ) ) continue;
         StringTokenizer  tokens
           = new StringTokenizer ( file_list [ i ], "." );
         if ( tokens.countTokens ( ) != 2 ) continue;
         String  filename = tokens.nextToken ( );
         String  extension = tokens.nextToken ( );
         if ( !extension.equals ( "java" ) ) continue;
         System.out.println ( file_list [ i ] );
         FileInputStream  fileInputStream;
         DataInputStream  dataInputStream;
         String  line;
         try {
           fileInputStream = new FileInputStream ( file );
           dataInputStream = new DataInputStream ( fileInputStream );
           while ( ( line = dataInputStream.readLine ( ) ) != null ) {
             if ( line.trim ( ).length ( ) > 0 ) {
               ++lineCount;
             }
           }
         } catch ( Exception  e ) { continue; }
System.err.println ( lineCount );
/*
         tokens = new StringTokenizer ( line );
         if ( tokens.countTokens ( ) != 2 ) continue;
         String  tokenStr = tokens.nextToken ( );
         if ( !tokenStr.equals ( "package" ) ) continue;
         int  index = line.lastIndexOf ( args [ 0 ] );
         System.out.println ( "" + index );
         if ( index < 0 ) continue;
         String new_line = line.substring ( 0, index )
           + args [ 1 ] + line.substring ( index + args [ 0 ].length ( ) );
         FileOutputStream  fileOutputStream;
         PrintStream  printStream;
         try {
           fileOutputStream
             = new FileOutputStream ( file_list [ i ] + ".new" );
           printStream = new PrintStream ( fileOutputStream );
         } catch ( Exception e ) { continue; }
         line = new_line;
         try {
           do {
             printStream.println ( line );
             line = dataInputStream.readLine ( );
           } while ( line != null );
         }
         catch ( Exception e ) { }
         printStream.close ( );
*/
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
