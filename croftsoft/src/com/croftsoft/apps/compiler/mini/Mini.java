     package com.croftsoft.apps.compiler.mini;

     import java.io.*;

     import java_cup.runtime.Symbol;

     import com.croftsoft.apps.compiler.mini.code.*;
     import com.croftsoft.apps.compiler.mini.node.*;
     import com.croftsoft.apps.compiler.mini.parse.*;

     /*********************************************************************
     * Mini programming language translator.
     *
     * <P>
     *
     * Built using the
     *
     * CUP Parser Generator for Java
     * (<A HREF="http://www.cs.princeton.edu/~appel/modern/java/CUP/">
     * http://www.cs.princeton.edu/~appel/modern/java/CUP/</A>)
     *
     * and
     *
     * JLex: A Lexical Analyzer Generator for Java
     * (<A HREF="http://www.cs.princeton.edu/~appel/modern/java/JLex/">
     * http://www.cs.princeton.edu/~appel/modern/java/JLex/</A>).
     *
     * @version
     *   1999-04-26
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  Mini
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final boolean  DO_DEBUG_PARSE = false;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       Reader  reader = new InputStreamReader ( System.in );
       PrintStream  printStream = System.out;
       String  outputFilename = "MiniProgram";

       if ( args.length > 0 )
       {
         reader = new FileReader ( args [ 0 ] );
       }

       if ( args.length > 1 )
       {
         printStream
           = new PrintStream ( new FileOutputStream ( args [ 1 ] ) );
       }

       if ( args.length > 0 )
       {
         String  filename = args.length > 1 ? args [ 1 ] : args [ 0 ];

         int  index = filename.lastIndexOf ( '.' );

         if ( index < 0 )
         {
           outputFilename = filename;
         }
         else
         {
           outputFilename = filename.substring ( 0, index );
         }
       }

       translate ( reader, printStream, outputFilename );
     }

     public static void  translate (
       Reader  reader, PrintStream  printStream, String  outputFilename )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       ProgramMiniNode  programMiniNode = parse ( reader );

       MiniNodeCodeVisitor  miniNodeCodeVisitor
         = new JavaSourceMiniNodeCodeVisitor (
         printStream, outputFilename );

       programMiniNode.generate ( miniNodeCodeVisitor );
     }

     public static ProgramMiniNode  parse ( InputStream  inputStream )
     //////////////////////////////////////////////////////////////////////
     {
       return parse ( new MiniScanner ( inputStream ) );
     }

     public static ProgramMiniNode  parse ( Reader  reader )
     //////////////////////////////////////////////////////////////////////
     {
       return parse ( new MiniScanner ( reader ) );
     }

     public static ProgramMiniNode  parse ( MiniScanner  miniScanner )
     //////////////////////////////////////////////////////////////////////
     {
       MiniParser  parser = new MiniParser ( miniScanner );

       Symbol  parseTree = null;

       try
       {
         if ( DO_DEBUG_PARSE )
         {
           parseTree = parser.debug_parse ( );
         }
         else
         {
           parseTree = parser.parse ( );
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }

       return ( ProgramMiniNode ) parseTree.value;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
