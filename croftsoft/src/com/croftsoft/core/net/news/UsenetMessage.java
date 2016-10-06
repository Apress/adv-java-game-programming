     package com.croftsoft.core.net.news;

     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.lang.Pair;

     /*********************************************************************
     * USENET message (RFC 1036).
     *
     * @see
     *   <a target="blank"
     *     href="http://www.w3.org/Protocols/rfc1036/rfc1036.html">
     *   RFC 1036:  Standard for Interchange of USENET Messages</a>
     *
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</A>
     * @version
     *   2001-08-18
     * @since
     *   2001-07-27
     *********************************************************************/

     public final class  UsenetMessage
       implements Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 1L;

     // required headers

     public static final String  HEADER_FROM         = "From";

     public static final String  HEADER_DATE         = "Date";

     public static final String  HEADER_NEWSGROUPS   = "Newsgroups";

     public static final String  HEADER_SUBJECT      = "Subject";

     public static final String  HEADER_MESSAGE_ID   = "Message-ID";

     public static final String  HEADER_PATH         = "Path";

     // optional headers

     public static final String  HEADER_FOLLOWUP_TO  = "Followup-To";

     public static final String  HEADER_EXPIRES      = "Expires";

     public static final String  HEADER_REPLY_TO     = "Reply-To";

     public static final String  HEADER_SENDER       = "Sender";

     public static final String  HEADER_REFERENCES   = "References";

     public static final String  HEADER_CONTROL      = "Control";

     public static final String  HEADER_DISTRIBUTION = "Distribution";

     public static final String  HEADER_KEYWORDS     = "Keywords";

     public static final String  HEADER_SUMMARY      = "Summary";

     public static final String  HEADER_APPROVED     = "Approved";

     public static final String  HEADER_LINES        = "Lines";

     public static final String  HEADER_XREF         = "Xref";

     public static final String  HEADER_ORGANIZATION = "Organization";

     //

     public static final String [ ]  HEADERS = {
       // required
       HEADER_FROM,
       HEADER_DATE,
       HEADER_NEWSGROUPS,
       HEADER_SUBJECT,
       HEADER_MESSAGE_ID,
       HEADER_PATH,
       // optional
       HEADER_FOLLOWUP_TO,
       HEADER_EXPIRES,
       HEADER_REPLY_TO,
       HEADER_SENDER,
       HEADER_REFERENCES,
       HEADER_CONTROL,
       HEADER_DISTRIBUTION,
       HEADER_KEYWORDS,
       HEADER_SUMMARY,
       HEADER_APPROVED,
       HEADER_LINES,
       HEADER_XREF,
       HEADER_ORGANIZATION };

     //

     private final Map  headerNameToValueMap;

     private String  body;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static UsenetMessage  parse ( BufferedReader  bufferedReader )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       UsenetMessage  usenetMessage = new UsenetMessage ( );

       Pair  lastPair = null;

       String  line;

       while ( true )
       {
         line = bufferedReader.readLine ( );

System.out.println ( line );

         if ( ".".equals ( line )
           || ( line == null )
           || "".equals ( line ) )
         {
           break;
         }

         Pair  headerPair = parseHeaderLine ( line );

         if ( headerPair != null )
         {
           lastPair = headerPair;

           usenetMessage.setHeader ( headerPair.name, headerPair.value );
         }
         else
         {
           if ( lastPair != null
             && ( line.startsWith ( "\t" ) 
               || line.startsWith ( " " ) ) )
           {
             usenetMessage.setHeader ( lastPair.name,
              lastPair.value + line.substring ( 1 ) );
           }
           else
           {
             throw new IllegalArgumentException ( line );
           }
         }
       }

       if ( "".equals ( line ) )
       {
         String  body = parseBody ( bufferedReader );

         usenetMessage.setBody ( body );
       }

       return usenetMessage;
     }

     public static String  parseBody ( BufferedReader  bufferedReader )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       StringBuffer  stringBuffer = new StringBuffer ( );

       while ( true )
       {
         String  line = bufferedReader.readLine ( );

System.out.println ( line );

         if ( ".".equals ( line )
           || line == null )
         {
           break;
         }
// changing the end-of-line here, will mess up message digests

         stringBuffer.append ( NntpLib.decodeLine ( line ) + "\n" );
       }

       return stringBuffer.toString ( );
     }

     public static Pair  parseHeaderLine ( String  headerLine )
     //////////////////////////////////////////////////////////////////////
     {
       int  index = headerLine.indexOf ( ':' );

       if ( index < 1 )
       {
         return null;
       }

       String  name = headerLine.substring ( 0, index ).trim ( );

       if ( "".equals ( name ) )
       {
         return null;
       }

       String  value = headerLine.substring ( index + 1 ).trim ( );

       return new Pair ( name, value );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  UsenetMessage ( )
     //////////////////////////////////////////////////////////////////////
     {
       headerNameToValueMap = new HashMap ( );
     }

     public  UsenetMessage (
       String  from,
       String  newsgroup,
       String  subject,
       String  messageBody )
     //////////////////////////////////////////////////////////////////////
     {
       this ( );

       setHeader ( HEADER_FROM      , from      );

       setHeader ( HEADER_NEWSGROUPS, newsgroup );

       setHeader ( HEADER_SUBJECT   , subject   );

//     setHeader ( HEADER_PATH      , ""        );

       setBody ( messageBody );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getHeader ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       return ( String ) headerNameToValueMap.get ( name );
     }

     public String  getBody ( ) { return body; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setHeader (
       String  name,
       String  value )
     //////////////////////////////////////////////////////////////////////
     {
       headerNameToValueMap.put ( name, value );
     }

     public void  setBody ( String  body )
     //////////////////////////////////////////////////////////////////////
     {
       this.body = body;
     }

     public String  toString ( )
     //////////////////////////////////////////////////////////////////////
     {
       StringBuffer  stringBuffer = new StringBuffer ( );

       // First append the standard headers in normal order.

       Map  temporaryMap = new HashMap ( headerNameToValueMap );

       for ( int  i = 0; i < HEADERS.length; i++ )
       {
         String  name = HEADERS [ i ];

         if ( temporaryMap.containsKey ( name ) )
         {
           String  value = ( String ) temporaryMap.remove ( name );

           stringBuffer.append (
             name + ": " + ( value == null ? "" : value ) + "\r\n" );
         }
       }

       // Then append any non-standard headers.

       Iterator  iterator = temporaryMap.keySet ( ).iterator ( );

       while ( iterator.hasNext ( ) )
       {
         String  name  = ( String ) iterator.next ( );

         String  value = ( String ) temporaryMap.get ( name );

         stringBuffer.append (
           name + ": " + ( value == null ? "" : value ) + "\r\n" );
       }

       stringBuffer.append ( "\r\n" );

       if ( body == null )
       {
         body = "";
       }

       stringBuffer.append ( NntpLib.encode ( body ) );

       return stringBuffer.toString ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }