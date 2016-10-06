     package com.croftsoft.core.net;

     /*********************************************************************
     *
     * Java 1.1 substitute for URLDecoder and URLEncoder.
     *
     * <P>
     *
     * The classes java.net.URLDecoder and java.net.URLEncoder were not
     * available until Java 1.2.  This class provides a substitute for
     * those programs which must use the Java 1.1 API.
     *
     * <P>
     *
     * @see
     *   java.net.URLDecoder
     * @see
     *   java.net.URLEncoder
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   2000-04-21
     *********************************************************************/

     public final class  URLCoder
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private  URLCoder ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Displays the encoded value of the first argument.
     *********************************************************************/
     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( encode ( args [ 0 ] ) );
     }

     /*********************************************************************
     * Performs the equivalent of java.net.URLDecoder.decode().
     *********************************************************************/
     public static String  decode ( String  s )
     //////////////////////////////////////////////////////////////////////
     {
       if ( s == null )
       {
         throw new IllegalArgumentException ( "null" );
       }

       StringBuffer  stringBuffer = new StringBuffer ( );

       int  length = s.length ( );

       for ( int  i = 0; i < length; i++ )
       {
         char  c = s.charAt ( i );

         if ( ( ( c >= 'a' ) && ( c <= 'z' ) )
           || ( ( c >= 'A' ) && ( c <= 'Z' ) )
           || ( ( c >= '0' ) && ( c <= '9' ) )
           || ( c == '.'                     )
           || ( c == '-'                     )
           || ( c == '*'                     )
           || ( c == '_'                     ) )
         {
           stringBuffer.append ( c );
         }
         else if ( c == '+' )
         {
           stringBuffer.append ( ' ' );
         }
         else if ( c == '%' )
         {
           stringBuffer.append ( ( char )
             ( 16 * Character.digit ( s.charAt ( ++i ), 16 )
                  + Character.digit ( s.charAt ( ++i ), 16 ) ) );
         }
         else
         {
           stringBuffer.append ( c );
         }
       }

       return stringBuffer.toString ( );
     }

     /*********************************************************************
     * Performs the equivalent of java.net.URLEncoder.encode().
     *********************************************************************/
     public static String  encode ( String  s )
     //////////////////////////////////////////////////////////////////////
     {
       if ( s == null )
       {
         throw new IllegalArgumentException ( "null" );
       }

       StringBuffer  stringBuffer = new StringBuffer ( );

       int  length = s.length ( );

       for ( int  i = 0; i < length; i++ )
       {
         char  c = s.charAt ( i );

         if ( ( ( c >= 'a' ) && ( c <= 'z' ) )
           || ( ( c >= 'A' ) && ( c <= 'Z' ) )
           || ( ( c >= '0' ) && ( c <= '9' ) )
           || ( c == '.'                     )
           || ( c == '-'                     )
           || ( c == '*'                     )
           || ( c == '_'                     ) )
         {
           stringBuffer.append ( c );
         }
         else if ( c == ' ' )
         {
           stringBuffer.append ( '+' );
         }
         else
         {
           stringBuffer.append ( "%" + Integer.toHexString (
             ( ( int ) c ) & 0xFF ).toUpperCase ( ) );
         }
       }

       return stringBuffer.toString ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
