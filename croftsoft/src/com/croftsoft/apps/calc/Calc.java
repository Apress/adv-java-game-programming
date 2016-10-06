     package com.croftsoft.apps.calc;

     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.math.FinanceLib;
     
     /*********************************************************************
     * Command line financial calculator.
     *
     * <p />
     *
     * @version
     *   2001-10-10
     * @since
     *   2001-10-10
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Calc
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( "Calc (c) 2001 CroftSoft Inc" );

       System.out.println ( "http://croftsoft.com/" );

       System.out.println ( "" );

       System.out.println ( "1 = Net Present Value" );

       System.out.println ( "2 = Internal Rate of Return" );

       System.out.println ( "" );

       String  line = prompt ( "Function [Quit]:  " );

       if ( "1".equals ( line ) )
       {
         netPresentValue ( );
       }
       else if ( "2".equals ( line ) )
       {
         internalRateOfReturn ( );
       }
     }

     private static void  netPresentValue ( )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( "Net Present Value (NPV)" );

       double  discountRate
         = promptDouble ( "DiscountRate (%)", 10.0 ) / 100.0;

       List  cashFlowList = new ArrayList ( );

       int  time = 0;

       double  value;

       double  defaultValue = 0.0;

       while ( true )
       {
         String  line = prompt (
           "Cash Flow at Time " + time
           + " (Control-Z or Control-D to end) [" 
           + defaultValue + "]:  " );


         if ( line == null )
         {
           break;
         }

         if ( "".equals ( line ) )
         {
           value = defaultValue;
         }
         else
         {
           value = Double.parseDouble ( line );

           defaultValue = value;
         }

         cashFlowList.add ( new Double ( value ) );

         time++;
       }

       double [ ]  cashFlows = new double [ cashFlowList.size ( ) ];

       for ( int  i = 0; i < cashFlows.length; i++ )
       {
         cashFlows [ i ] = ( ( Double ) cashFlowList.get ( i ) ).doubleValue ( );
       }
       
       System.out.println (
         FinanceLib.netPresentValue ( discountRate, cashFlows ) );
     }

     private static void  internalRateOfReturn ( )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( "Internal Rate of Return (IRR)" );

       double  irrGuess
         = promptDouble ( "Estimated IRR (%)", 10.0 ) / 100.0;

       List  cashFlowList = new ArrayList ( );

       int  time = 0;

       double  value;

       double  defaultValue = 0.0;

       while ( true )
       {
         String  line = prompt (
           "Cash Flow at Time " + time
           + " (Control-Z or Control-D to end) [" 
           + defaultValue + "]:  " );


         if ( line == null )
         {
           break;
         }

         if ( "".equals ( line ) )
         {
           value = defaultValue;
         }
         else
         {
           value = Double.parseDouble ( line );

           defaultValue = value;
         }

         cashFlowList.add ( new Double ( value ) );

         time++;
       }

       double [ ]  cashFlows = new double [ cashFlowList.size ( ) ];

       for ( int  i = 0; i < cashFlows.length; i++ )
       {
         cashFlows [ i ]
           = ( ( Double ) cashFlowList.get ( i ) ).doubleValue ( );
       }
       
       System.out.println ( ( 100.0 *
         FinanceLib.internalRateOfReturn ( irrGuess, cashFlows ) ) + "%" );
     }

     private static String  prompt ( String  promptString )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       System.out.print ( promptString );

       return new BufferedReader (
           new InputStreamReader ( System.in ) ).readLine ( );
     }

     private static double  promptDouble (
       String  promptString,
       double  defaultValue )
       throws IOException, NumberFormatException
     //////////////////////////////////////////////////////////////////////
     {
       System.out.print ( promptString + " [" + defaultValue + "]:  " );

       String  line = new BufferedReader (
         new InputStreamReader ( System.in ) ).readLine ( );

       if ( "".equals ( line ) )
       {
         return defaultValue;
       }

       return Double.parseDouble ( line );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }