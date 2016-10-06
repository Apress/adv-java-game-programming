     package com.croftsoft.apps.rename;

     import java.io.*;
     import java.util.*;

     /*********************************************************************
     * Renames files in a directory.
     *
     * <P>
     *
     * @version
     *   2000-05-09
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  Rename implements FilenameFilter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final String  oldNameTemplate;

     private final String  pre;

     private final String  post;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args ) throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       rename ( new File ( "." ), args [ 0 ], args [ 1 ] );
     }

     public static void  rename (
       File    directory,
       String  oldNameTemplate,
       String  newNameTemplate )
     //////////////////////////////////////////////////////////////////////
     {
       if ( directory == null )
       {
         throw new IllegalArgumentException ( "null directory" );
       }

       FilenameFilter  filenameFilter = new Rename ( oldNameTemplate );

       File [ ]  files = directory.listFiles ( filenameFilter );

       for ( int  i = 0; i < files.length; i++ )
       {
         String  oldName = files [ i ].getName ( );

         File  newFile = new File ( directory,
           toNewName ( oldName, oldNameTemplate, newNameTemplate ) );

         if ( !files [ i ].renameTo ( newFile ) )
         {
           throw new RuntimeException ( "Failure renaming \""
             + files [ i ] + "\" to \"" + newFile + "\"" );
         }
       }      
     }

     public static String  toNewName (
       String  oldName,
       String  oldNameTemplate,
       String  newNameTemplate )
     //////////////////////////////////////////////////////////////////////
     {
       String [ ]  oldParts = parseParts ( oldNameTemplate );

       String  value = oldName.substring ( oldParts [ 0 ].length ( ),
         oldName.length ( ) - oldParts [ 1 ].length ( ) );

       String [ ]  newParts = parseParts ( newNameTemplate );

       return newParts [ 0 ] + value + newParts [ 1 ];
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Rename (
       String  oldNameTemplate )
     //////////////////////////////////////////////////////////////////////
     {
       this.oldNameTemplate = oldNameTemplate;

       String [ ]  parts = parseParts ( oldNameTemplate );

       pre  = parts [ 0 ];

       post = parts [ 1 ];
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  accept (
       File    directory,
       String  filename )
     //////////////////////////////////////////////////////////////////////
     {
       return filename.startsWith ( pre  )
         &&   filename.endsWith   ( post );
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private static String [ ]  parseParts ( String  filenameTemplate )
     //////////////////////////////////////////////////////////////////////
     {
       int  index = filenameTemplate.indexOf ( '*' );

       if ( index < 0 )
       {
         throw new IllegalArgumentException (
           "No wildcard character (*) in \"" + filenameTemplate + "\"" );
       }

       String  pre = filenameTemplate.substring ( 0, index );

       String  post = filenameTemplate.substring ( index + 1 );

       return new String [ ] { pre, post };
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
