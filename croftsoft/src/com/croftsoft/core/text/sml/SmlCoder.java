     package com.croftsoft.core.text.sml;

     import com.croftsoft.core.lang.StringLib;

     /*********************************************************************
     * Static methods for encoding and decoding SML.
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     *
     * @version
     *   2001-08-09
     * @since
     *   2001-05-14
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SmlCoder
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( encode ( "<>" ) );

       System.out.println ( decode ( "&lt;&gt;" ) );
     }
          
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Encodes a String for SML transport.
     *
     * <p>
     * Replaces angle brackets.
     * </p>
     *********************************************************************/
     public static String  encode ( String  s )
     //////////////////////////////////////////////////////////////////////
     {
       if ( s == null )
       {
         return null;
       }

       s = StringLib.replace ( s, "<", "&lt;" );

       s = StringLib.replace ( s, ">", "&gt;" );
       
       return s;
     }

     /*********************************************************************
     * Decodes an SML String back into a regular String.
     *
     * <p>
     * Substitutes angle brackets.
     * </p>
     *********************************************************************/
     public static String  decode ( String  s )
     //////////////////////////////////////////////////////////////////////
     {
       if ( s == null )
       {
         return null;
       }

       s = StringLib.replace ( s, "&lt;", "<" );

       s = StringLib.replace ( s, "&gt;", ">" );
       
       return s;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  SmlCoder ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
