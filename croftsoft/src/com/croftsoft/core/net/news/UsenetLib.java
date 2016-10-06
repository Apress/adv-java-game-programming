     package com.croftsoft.core.net.news;

     import java.net.*;
     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * USENET (RFC 1036) message manipulation.
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

     public final class  UsenetLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  PLAIN_TEXT_ONLY_CHARTER_POSTFIX
       = "Unmarked off-topic materials, binaries, advertising (spam),\r\n"
       + "excessive posting, cancel attacks, virus infected binaries,\r\n"
       + "HTML-encoded postings, and abusive cross postings are\r\n"
       + "prohibited.  All posts (messages) must be in plain text only\r\n"
       + "and be human-readable.";

     /*********************************************************************
     * Creates a control message to create a new newsgroup.
     *
     * @param  email
     *
     *   Your e-mail address.
     *
     * @param  newsgroup
     *
     *   See the FAQ for newsgroup naming conventions.
     *
     * @param  shortDescription
     *
     *   This plus the newsgroup name should be less than 80 characters.
     *
     * @param  charter
     *
     *   May be null.
     *
     * @param  justification
     *
     *   May be null.
     *
     * @param  proponent
     *
     *   May be null.
     *
     * @param  isBooster
     *
     *   True if this is not the first posting of this message.
     * 
     * @throws IllegalArgumentException
     *
     *   If the shortDescription is too long.
     *
     * @see
     *   "alt.config FAQ"
     *
     * @see
     *   "Myths from the new config FAQ"
     *********************************************************************/
     public static UsenetMessage  createNewGroupMessage (
       String   email,
       String   newsgroup,
       String   shortDescription,
       String   charter,
       String   justification,
       String   proponent,
       boolean  isBooster )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( email           );

       NullArgumentException.check ( newsgroup       );

       NullArgumentException.check ( shortDescription );

       UsenetMessage  usenetMessage = new UsenetMessage ( );

       usenetMessage.setHeader (
         UsenetMessage.HEADER_FROM, email );

       usenetMessage.setHeader (
         UsenetMessage.HEADER_NEWSGROUPS, "alt.config,control.newgroup" );

       usenetMessage.setHeader (
         UsenetMessage.HEADER_SUBJECT, "cmsg newgroup " + newsgroup );

       usenetMessage.setHeader (
         UsenetMessage.HEADER_PATH, "" );

       usenetMessage.setHeader (
         UsenetMessage.HEADER_CONTROL, "newgroup " + newsgroup );

       usenetMessage.setHeader (
         UsenetMessage.HEADER_APPROVED, email );

       StringBuffer  stringBuffer = new StringBuffer ( );

       if ( isBooster )
       {
         stringBuffer.append ( "BOOSTER\r\n\r\n" );
       }

       stringBuffer.append ( "For your newsgroups file:\r\n" );

       String  description = newsgroup + '\t' + shortDescription;

       if ( description.length ( ) > 80 )
       {
         throw new IllegalArgumentException (
           "shortDescription too long" );
       }

       stringBuffer.append ( description );

       if ( charter != null )
       {
         stringBuffer.append ( "\r\n\r\nCharter\r\n" );

         stringBuffer.append ( charter );
       }

       if ( justification != null )
       {
         stringBuffer.append ( "\r\n\r\nJustification:\r\n" );

         stringBuffer.append ( justification );
       }

       if ( proponent != null )
       {
         stringBuffer.append ( "\r\n\r\nProponent:\r\n" );

         stringBuffer.append ( proponent );
       }

       usenetMessage.setBody ( stringBuffer.toString ( ) );

       return usenetMessage;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  UsenetLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
