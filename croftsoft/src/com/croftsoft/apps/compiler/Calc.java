     package com.croftsoft.apps.compiler;

     import java.io.*;

     import java_cup.runtime.Symbol;

     /*********************************************************************
     * A calculator capable of parsing input.
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
     *   1999-03-16
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  Calc
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  INFO
       = "=============================================\n"
       + "Calc (C) Copyright 1999 David Wallace Croft\n"
       + "\n"
       + "Version 1999-03-16\n"
       + "Source code available at http://www.orbs.com/\n"
       + "\n"
       + "Enter your calculations at the command line.\n"
       + "Example:  a := 2; b := 3; write ( a + b );\n"
       + "=============================================\n"
       + "\n";

     private static final boolean  DO_DEBUG_PARSE = false;

     private static final String [ ]  TEST_DATA = {

         "a := 2;"                        
       + "write ( a );"                   // 2
       + "write ( b );"                   // null
       + "b := 3;"
       + "write ( b );"                   // 3
       + "c := a + b;"                    
       + "write ( c );",                  // 5

         "a := 2;"
       + "b := 3;"
       + "write ( a + b );"               // 5
       + "write ( a - b );"               // -1
       + "write ( a * b );"               // 6
       + "write ( a / b );"               // 0
       + "write ( a % b );"               // 2
       + "write ( a ^ b );"               // 8
       + "write ( a * b ^ a );",          // 18, not 36

         "bill_123 := 3;"
       + "write ( bill_123 );",           // 3

         "write ( a := 1 + 2 );"          // 3
       + "write ( b := a + 3 );"          // 6
       + "write ( c := 1 + ( b := 2 ) );" // 3
       + "write ( b );"                   // 2
       + "write ( c );",                  // 3

         "write ( a );"                   // null
       + "b := 1;"
       + "write ( b );"                   // 1
       + "b := a;"
       + "write ( b );",                  // null

         "a := 2;"
       + "b := 3;"
       + "write ( -1 );"                  // -1
       + "write ( 1 - -1 );"              // 2
       + "write ( -a );"                  // -2
       + "write ( -a + -b );"             // -5
       + "write ( - ( a - -b ) + 1 );",   // -4

       // The following test is only partially successful due to error
       // recovery with parentheses not being handled properly, I suspect.

         "hello;"                         // syntax error
       + "write ( 1 );"                   // 1
       + "write ( 1 + ( 2 + );"           // syntax error
       + "write ( 2 );"                   // 2
       + "write ( 1 + ( 2 + 1 );",        // syntax error

         "a := 2;"
       + "write ( a + b );"               // null operand, null operand
       + "b := 3;"
       + "write ( a + b );"               // 5

       };

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( INFO );

       if ( args.length < 1 )
       {
         parse ( System.in );
       }
       else
       {
         test ( );
       }
     }

     public static boolean  test ( )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < TEST_DATA.length; i++ )
       {
         System.out.println ( "--- Test " + i + " --- " );
         parse ( new StringReader ( TEST_DATA [ i ] ) );
         System.out.println ( "" );
       }

       return true;
     }

     public static void  parse ( InputStream  inputStream )
     //////////////////////////////////////////////////////////////////////
     {
       parse ( new CalcScanner ( inputStream ) );
     }

     public static void  parse ( Reader  reader )
     //////////////////////////////////////////////////////////////////////
     {
       parse ( new CalcScanner ( reader ) );
     }

     public static void  parse ( CalcScanner  calcScanner )
     //////////////////////////////////////////////////////////////////////
     {
       CalcParser  parser = new CalcParser ( calcScanner );

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

       // System.out.println ( parseTree );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
