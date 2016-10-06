     package com.croftsoft.core.text;

     import java.text.SimpleDateFormat;
     import java.util.Date;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Static library methods for formatting dates.
     *
     * @version
     *   2001-08-01
     * @since
     *   2001-06-28
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  DateFormatLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static SimpleDateFormat  isoDateFormat
       = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( toIsoDateFormat ( new Date ( ) ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Returns a String in ISO date format (yyyy-MM-dd hh:mm:ss).
     *********************************************************************/
     public static String  toIsoDateFormat ( Date  date )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( date );

       return isoDateFormat.format ( date );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  DateFormatLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }