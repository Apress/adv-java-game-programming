     package com.croftsoft.apps.retirement;

     import java.io.*;
     import java.text.NumberFormat;

     import javax.servlet.*;
     import javax.servlet.http.*;

     import com.croftsoft.core.io.FileLib;
     import com.croftsoft.core.math.FinanceLib;
     import com.croftsoft.core.text.ParseLib;

     /*********************************************************************
     * Retirement calculator servlet.
     *
     * @version
     *   2001-06-09
     * @since
     *   1999-08-15
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  RetirementServlet extends HttpServlet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     //////////////////////////////////////////////////////////////////////
     // Static variables
     //////////////////////////////////////////////////////////////////////

     private static final String  TITLE   = "Retirement Calculator";

     private static final String  VERSION = "2001-06-09";

     private static final String  DEFAULT_HEADER
       = "<HTML>\n<HEAD>\n<TITLE>" + TITLE + "</TITLE>\n</HEAD>\n"
       + "<BODY>\n<CENTER>\n<H1>" + TITLE + "</H1>\n"
       + "<p><a target=\"_blank\" href=\"http://croftsoft.com/people/david/\">"
       + "David Wallace Croft</a></p>\n"
       + "<p>Version " + VERSION + "</p>\n";

     private static final String  DEFAULT_FOOTER
       = "</CENTER>\n</BODY>\n</HTML>";

     private static final String  DEFAULT_HEADER_ALIAS_PATH
       = "/projects/retirement/header.html";

     private static final String  DEFAULT_FOOTER_ALIAS_PATH
       = "/projects/retirement/footer.html";

     private static final String [ ]  PARAM_TEXT = {
       "Desired annual retirement income (present value, after taxes)",
       "Years until retirement (usually at 59&#189; years of age)",
       "Annual investment growth rate before retirement (tax-deferred)",
       "Annual interest earned on retirement savings during retirement",
       "Tax rate during retirement on savings interest",
       "Estimated annual inflation" };

     private static final String [ ]  PARAM_NAMES = {
       "rIncome",
       "iYears",
       "iInterest",
       "rInterest",
       "rTaxRate",
       "rInflation" };

     private static final double [ ]  PARAM_DEFAULT_VALUES = {
       30000.0,
       40.0,
       10.0,
       6.0,
       15.0,
       2.0 };

     private static final boolean [ ]  PARAM_IS_PERCENTAGE = {
       false,
       false,
       true,
       true,
       true,
       true };

     private static final NumberFormat  currencyNumberFormat
       = NumberFormat.getCurrencyInstance ( );

     //////////////////////////////////////////////////////////////////////
     // Instance variables
     //////////////////////////////////////////////////////////////////////

     private String  htmlHeader;

     private String  htmlFooter;

     //////////////////////////////////////////////////////////////////////
     // Public static methods
     //////////////////////////////////////////////////////////////////////

     public static final double  calculateRequiredAnnualInvestment (
       double  desiredSavingsInterestIncome,
       double  yearsOfSaving,
       double  investmentInterestRate,
       double  savingsInterestRate,
       double  taxRate,
       double  inflationRate )
     //////////////////////////////////////////////////////////////////////
     {
       double  savings
         = desiredSavingsInterestIncome * ( 1.0 + inflationRate )
         / ( savingsInterestRate * ( 1.0 - taxRate ) - inflationRate );

       double  annualSavings;

       if ( yearsOfSaving == 0.0 )
       {
         annualSavings = savings;
       }
       else
       {
         double  futureValueSavings
           = savings * Math.pow ( 1.0 + inflationRate, yearsOfSaving );

         annualSavings = FinanceLib.annualSavingsNeeded (
           futureValueSavings, investmentInterestRate, yearsOfSaving );
       }

       return annualSavings;
     }

     //////////////////////////////////////////////////////////////////////
     // HttpServlet methods
     //////////////////////////////////////////////////////////////////////

     public String  getServletInfo ( ) { return TITLE; }

     public void  init ( ServletConfig  servletConfig )
       throws ServletException
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         super.init ( servletConfig );

         ServletContext  servletContext = getServletContext ( );

         String  headerRealPath
           = servletContext.getRealPath ( DEFAULT_HEADER_ALIAS_PATH );

         String  footerRealPath
           = servletContext.getRealPath ( DEFAULT_FOOTER_ALIAS_PATH );

         try
         {
           htmlHeader = FileLib.loadTextFile ( headerRealPath );
         }
         catch ( Exception  ex )
         {
           htmlHeader = "<! Unable to load \"" + headerRealPath + "\">\n"
             + "<! " + ex + " >\n"
             + DEFAULT_HEADER;
         }

         try
         {
           htmlFooter = FileLib.loadTextFile ( footerRealPath );
         }
         catch ( Exception  ex )
         {
           htmlFooter = "<! Unable to load \"" + footerRealPath + "\">\n"
             + "<! " + ex + " >\n"
             + DEFAULT_FOOTER;
         }
       }
       catch ( Exception  ex )
       {
         log ( "init() Exception", ex );
       }
     }

     public void  doGet (
       HttpServletRequest   req,
       HttpServletResponse  res )
       throws ServletException, IOException
     //////////////////////////////////////////////////////////////////////
     {
       doPost ( req, res );
     }

     public void  doPost (
       HttpServletRequest   req,
       HttpServletResponse  res )
       throws ServletException, IOException
     //////////////////////////////////////////////////////////////////////
     {
       res.setContentType ( "text/html" );

       PrintStream  out = new PrintStream ( res.getOutputStream ( ) );

       try
       {
         out.println ( htmlHeader );

         double [ ]  paramDoubles = new double [ PARAM_NAMES.length ];

         for ( int  i = 0; i < paramDoubles.length; i++ )
         {
           String [ ]  paramValues
             = req.getParameterValues ( PARAM_NAMES [ i ] );

           String  paramValue = null;

           if ( ( paramValues != null ) && ( paramValues.length > 0 ) )
           {
             paramValue = paramValues [ 0 ];
           }

           paramDoubles [ i ] = ParseLib.parseDouble (
             paramValue, PARAM_DEFAULT_VALUES [ i ] );
         }

// change method to POST?

         out.println ( "<FORM ACTION=\""
           + req.getRequestURI ( ) + "\" METHOD=GET>\n" );

         out.println ( "<TABLE>" );

         for ( int  i = 0; i < PARAM_TEXT.length; i++ )
         {
           out.println ( "<TR>" );

           out.println ( "  <TD>" + PARAM_TEXT [ i ] + "</TD>" );

           out.println ( "  <td><input type=\"text\" name=\""
             + PARAM_NAMES [ i ]
             + "\" SIZE=10 VALUE=\""
             + paramDoubles [ i ]
             + "\">" 
             + ( PARAM_IS_PERCENTAGE [ i ] ? "%" : "" ) + "</TD>" );

           out.println ( "</TR>" );
         }

         out.println ( "</TABLE>" );

         double [ ]  normalizedValues = new double [ paramDoubles.length ];

         for ( int  i = 0; i < paramDoubles.length; i++ )
         {
           normalizedValues [ i ] = PARAM_IS_PERCENTAGE [ i ]
             ? paramDoubles [ i ] / 100 : paramDoubles [ i ];
         }

         out.println ( "<P>" );
  
         out.println ( "<INPUT TYPE=\"SUBMIT\" VALUE=\"Recalculate\">" );

         out.println ( "</FORM>\n" );

         out.println ( "<P>" );

         double  annualSavings = calculateRequiredAnnualInvestment (
           normalizedValues [ 0 ],
           normalizedValues [ 1 ],
           normalizedValues [ 2 ],
           normalizedValues [ 3 ],
           normalizedValues [ 4 ],
           normalizedValues [ 5 ] );

         if ( annualSavings < 0 )
         {
           out.println ( "<font color=\"red\">" );

           out.println ( "The interest rate on retirement savings"
             + " must exceed the annual inflation rate." );

           out.println ( "</font>" );
         }
         else
         {
           out.println ( "<font color=\"green\">" );

           out.println ( "You would need to invest "
             + currencyNumberFormat.format ( annualSavings ) + " each year." );

           out.println ( "</font>" );

           showTable ( out,
             annualSavings,
             normalizedValues [ 0 ],
             normalizedValues [ 1 ],
             normalizedValues [ 2 ],
             normalizedValues [ 3 ],
             normalizedValues [ 4 ],
             normalizedValues [ 5 ] );
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         out.println ( "A processing error occurred.  Please try again." );
       }
       catch ( Throwable  t )
       {
         t.printStackTrace ( );
       }
       finally
       {
         try
         {
           out.println ( htmlFooter );

           out.flush ( );

           out.close ( );
         }
         catch ( Exception  ex ) { }
       }
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       super.destroy ( );
     }

     //////////////////////////////////////////////////////////////////////
     // Private methods
     //////////////////////////////////////////////////////////////////////

     private static void  showTable (
       PrintStream  out,
       double       annualSavings,
       double       desiredIncome,
       double       investmentYears,
       double       investmentInterestRate,
       double       retirementInterestRate,
       double       taxRate,
       double       inflationRate )
     //////////////////////////////////////////////////////////////////////
     {
       int  yearsWorking = ( int ) investmentYears;

       if ( ( yearsWorking < 0 ) || ( yearsWorking > 100 ) ) return;

       out.println ( "<TABLE BORDER CELLPADDING=10>" );

       out.println ( "  <THEAD> Investment Years </THEAD>" );
       out.println ( "  <TH> Year                  </TH>" );
       out.println ( "  <TH> Investment Earnings   </TH>" );
       out.println ( "  <TH> Additional Investment </TH>" );
       out.println ( "  <TH> Total Investment      </TH>" );
       out.println ( "  <TH> Present Value         </TH>" );

       double  totalSavings = 0.0;

       for ( int  i = 1; i < yearsWorking + 1; i++ )
       {
         out.println ( "  <TR ALIGN=RIGHT>" );

         out.println ( "    <TD> " + i + "</TD>" );

         double  interest = investmentInterestRate * totalSavings;

         out.println ( "    <TD> "
           + currencyNumberFormat.format ( interest ) + "</TD>" );

         out.println ( "    <TD> "
           + currencyNumberFormat.format ( annualSavings ) + "</TD>" );

         totalSavings = FinanceLib.futureValueAnnuity (
           annualSavings, investmentInterestRate, ( double ) i );

         out.println ( "    <TD> "
           + currencyNumberFormat.format ( totalSavings ) + "</TD>" );

         double  presentValue = FinanceLib.presentValue (
           totalSavings, inflationRate, ( double ) i );

         out.println ( "    <TD> "
           + currencyNumberFormat.format ( presentValue ) + "</TD>" );

         out.println ( "  </TR>" );
       }

       out.println ( "</TABLE>" );

       out.println ( "<P>" );

       out.println ( "<TABLE BORDER CELLPADDING=10>" );

       out.println ( "  <THEAD> First Ten Retirement Years </THEAD>" );
       out.println ( "  <TH> Year                  </TH>" );
       out.println ( "  <TH> Interest Earned       </TH>" );
       out.println ( "  <TH> Tax on Interest       </TH>" );
       out.println ( "  <TH> Living Income         </TH>" );
       out.println ( "  <TH> Remaining Savings     </TH>" );
       out.println ( "  <TH> Present Value         </TH>" );

       if ( yearsWorking == 0 )
       {
         totalSavings = annualSavings;
       }

       for ( int  i = yearsWorking + 1; i < yearsWorking + 11; i++ )
       {
         out.println ( "  <TR ALIGN=RIGHT>" );

         out.println ( "    <TD> " + i + "</TD>" );

         double  interest = retirementInterestRate * totalSavings;

         out.println ( "    <TD> "
           + currencyNumberFormat.format ( interest ) + "</TD>" );

         double  taxes = -taxRate * interest;

         out.println ( "    <TD> "
           + currencyNumberFormat.format ( taxes    ) + "</TD>" );

         double  livingExpenses = -FinanceLib.futureValue (
           desiredIncome, inflationRate, ( double ) i );

         out.println ( "    <TD> "
           + currencyNumberFormat.format ( livingExpenses ) + "</TD>" );

         totalSavings += interest + taxes + livingExpenses;

         out.println ( "    <TD> "
           + currencyNumberFormat.format ( totalSavings ) + "</TD>" );

         double  presentValue = FinanceLib.presentValue (
           totalSavings, inflationRate, ( double ) i );

         out.println ( "    <TD> "
           + currencyNumberFormat.format ( presentValue ) + "</TD>" );

         out.println ( "  </TR>" );
       }

       out.println ( "</TABLE>" );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
