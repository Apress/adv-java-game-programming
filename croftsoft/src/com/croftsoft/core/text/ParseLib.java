     package com.croftsoft.core.text;

     /*********************************************************************
     * A collection of static methods to parse primitive types.
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     * 
     * @version
     *   2002-09-26
     * @since
     *   1999-08-15
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public final class  ParseLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( parseDouble ( null    , 1968.0 ) );
       System.out.println ( parseDouble ( "1968.0", 1974.0 ) );
       System.out.println ( parseDouble ( "abcdef", 1968.0 ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Parses a boolean value from a String.
     *
     * <p>
     * All leading and trailing whitespace is trimmed and all characters
     * are converted to lower case before the comparison.
     * </p>
     *
     * @param  backup
     *
     *   Default value to be returned is s is null or cannot be parsed.
     *
     * @return
     *
     *   Returns false if s is "0", "f", "false", "n", "no" , or "off".
     *   Returns true  if s is "1", "t", "true" , "y", "yes", or "on" .
     *   Otherwise returns backup value.
     *********************************************************************/
     public static boolean  parseBoolean (
       String   s,
       boolean  backup )
     //////////////////////////////////////////////////////////////////////
     {
       if ( s == null )
       {
         return backup;
       }

       s = s.trim ( ).toLowerCase ( );

       if ( s.equals ( "0"     )
         || s.equals ( "f"     )
         || s.equals ( "false" )
         || s.equals ( "n"     )
         || s.equals ( "no"    )
         || s.equals ( "off"   ) )
       {
         return false;
       }
         
       if ( s.equals ( "1"    )
         || s.equals ( "t"    )
         || s.equals ( "true" )
         || s.equals ( "y"    )
         || s.equals ( "yes"  )
         || s.equals ( "on"   ) )
       {
         return true;
       }

       return backup;
     }

     /*********************************************************************
     * Parses a double out of a String.
     *
     * @param  backup
     *   Default value returned if unable to parse the String.
     *********************************************************************/
     public static double  parseDouble ( String  s, double  backup )
     //////////////////////////////////////////////////////////////////////
     {
       if ( s == null )
       {
         return backup;
       }

       try
       {
         return Double.valueOf ( s ).doubleValue ( );
       }
       catch ( NumberFormatException  ex )
       {
         return backup;
       }
     }

     /*********************************************************************
     * Returns backup if s is null or a NumberFormatException occurs.
     *********************************************************************/
     public static int  parseInt (
       String  s,
       int     backup )
     //////////////////////////////////////////////////////////////////////
     {
       if ( s == null )
       {
         return backup;
       }

       try
       {
         return Integer.parseInt ( s );
       }
       catch ( NumberFormatException  e )
       {
         return backup;
       }
     }

     /*********************************************************************
     * Returns backup if s is null or a NumberFormatException occurs.
     *********************************************************************/
     public static long  parseLong (
       String  s,
       long    backup )
     //////////////////////////////////////////////////////////////////////
     {
       if ( s == null )
       {
         return backup;
       }

       try
       {
         return Long.parseLong ( s );
       }
       catch ( NumberFormatException  e )
       {
         return backup;
       }
     }

     /*********************************************************************
     * Strips non-number characters out of the String.
     *
     * <p>
     * Example:  "$1,999.99 or best offer" ==> "1999.99"
     * </p>
     *********************************************************************/
     public static String  stripNonNumbers ( String  s )
     //////////////////////////////////////////////////////////////////////
     {
       if ( s == null )
       {
         return null;
       }

       StringBuffer  stringBuffer = new StringBuffer ( );

       int  sLength = s.length ( );

       for ( int  i = 0; i < sLength; i++ )
       {
         char  c = s.charAt ( i );

         if ( c == '.'
           || Character.isDigit ( c ) )
         {
           stringBuffer.append ( c );
         }
       }

       String  stripped = stringBuffer.toString ( );

       if ( stripped.length ( ) < 1 )
       {
         return null;
       }

       return stripped;       
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  ParseLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
