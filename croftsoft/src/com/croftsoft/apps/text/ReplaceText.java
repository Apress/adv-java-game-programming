     package com.croftsoft.apps.text;

     import java.io.*;

     import com.croftsoft.core.io.FileLib;

     /*********************************************************************
     * Replaces text in a file.
     *
     * <P>
     *
     * Command line:<BR>
     * java com.croftsoft.apps.text.ReplaceText fileName oldString newString
     *
     * <P>
     *
     * @version
     *   1999-08-15
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  ReplaceText
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
       throws FileNotFoundException, IOException
     //////////////////////////////////////////////////////////////////////
     {
       if ( args.length != 3 )
       {
         System.out.println ( "java com.orbs.app.util.ReplaceText "
           + "fileName oldString newString" );
         return;
       }
       replaceText ( args [ 0 ], args [ 1 ], args [ 2 ] );
     }

     /*********************************************************************
     * Calls FileLib.replaceStrings(file, oldString, newString).
     *********************************************************************/
     public static void  replaceText (
       String  fileName, String  oldString, String  newString )
       throws FileNotFoundException, IOException
     //////////////////////////////////////////////////////////////////////
     {
       File  file = new File ( fileName );
       System.out.print ( file.getCanonicalPath ( ) + " " );
       System.out.println (
         FileLib.replaceStrings ( file, oldString, newString )
         ? "changed" : "unchanged" );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
