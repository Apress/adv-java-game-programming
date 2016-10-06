     package com.croftsoft.core.text.sml;

     import java.io.*;
     import java.util.*;
     import java.util.zip.*;
     import javax.xml.parsers.*;
     import org.xml.sax.*;
     import org.xml.sax.helpers.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.role.Consumer;
     import com.croftsoft.core.text.sml.SmlNode;

     /*********************************************************************
     * Used to parse large SML files one data record at a time.
     *
     * <p>
     * This class is similar to SmlNodeLoader except that is uses an XML
     * parser instead of the SML parser.
     * </p>
     *
     * @version
     *   2002-09-24
     * @since
     *   2002-09-18
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public final class  SmlRecordLoader
       extends DefaultHandler
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Consumer  smlRecordConsumer;

     private final Stack     parentNodeStack;

     //

     private int      depth;

     private SmlNode  smlNode;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       parse (
         args [ 0 ],
         new Consumer ( )
         {
           public void  consume ( Object  o )
           {
             System.out.println ( o );

             System.out.println ( "" );
           }
         },
         args [ 0 ].toLowerCase ( ).endsWith ( ".zip"  ),
         args [ 0 ].toLowerCase ( ).endsWith ( ".gz"   ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  parse (
       InputSource  inputSource,
       Consumer     smlRecordConsumer )
       throws IOException, ParserConfigurationException, SAXException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( inputSource );

       NullArgumentException.check ( smlRecordConsumer );

       SAXParserFactory  saxParserFactory
         = SAXParserFactory.newInstance ( );

       SAXParser  saxParser = saxParserFactory.newSAXParser ( );

       saxParser.parse (
         inputSource, new SmlRecordLoader ( smlRecordConsumer ) );
     }

     public static void  parse (
       String    smlDataFilename,
       Consumer  smlRecordConsumer,
       boolean   isZipFile,
       boolean   isGzipFile )
       throws IOException, ParserConfigurationException, SAXException
     //////////////////////////////////////////////////////////////////////
     {
       InputStream  inputStream = null;

       try
       {
         inputStream = new FileInputStream ( smlDataFilename );

         if ( isZipFile )
         {
           ZipInputStream  zipInputStream
             = new ZipInputStream ( inputStream );

           zipInputStream.getNextEntry ( );

           inputStream = zipInputStream;
         }
         else if ( isGzipFile )
         {
           inputStream = new GZIPInputStream ( inputStream );
         }

         InputSource  inputSource = new InputSource (
           new BufferedReader ( new InputStreamReader ( inputStream ) ) );

         parse ( inputSource, smlRecordConsumer );
       }
       finally
       {
         if ( inputStream != null )
         {
           inputStream.close ( );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  SmlRecordLoader ( Consumer  smlRecordConsumer )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.smlRecordConsumer = smlRecordConsumer );

       parentNodeStack = new Stack ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  startElement (
       String      namespaceURI,
       String      localName,
       String      qName,
       Attributes  atts )
     //////////////////////////////////////////////////////////////////////
     {
         depth++;

         if ( depth == 2 )
         {
           smlNode = new SmlNode ( qName );
         }
         else if ( depth > 2 )
         {
           SmlNode  childNode = new SmlNode ( qName );

           Object  firstChild = smlNode.getChild ( 0 );

           if ( firstChild instanceof String )
           {
             smlNode.getChildren ( ) [ 0 ] = childNode;
           }
           else
           {
             smlNode.add ( childNode );
           }

           parentNodeStack.push ( smlNode );

           smlNode = childNode;
         }
     }

     public void  characters (
       char [ ]  ch,
       int       start,
       int       length )
     //////////////////////////////////////////////////////////////////////
     {
         if ( smlNode != null )
         {
           Object  firstChild = smlNode.getChild ( 0 );

           if ( firstChild == null )
           {
             smlNode.add ( new String ( ch, start, length ) );
           }
           else if ( firstChild instanceof String )
           {
             smlNode.getChildren ( ) [ 0 ]
               = firstChild + new String ( ch, start, length );
           }
         }
     }

     public void  endElement (
       String      namespaceURI,
       String      localName,
       String      qName )
     //////////////////////////////////////////////////////////////////////
     {
         depth--;

         if ( depth > 1 )
         {
           smlNode = ( SmlNode ) parentNodeStack.pop ( );
         }
         else if ( depth == 1 )
         {
           smlRecordConsumer.consume ( smlNode );

           smlNode = null;
         }
     }

/*
     public void  error ( SAXParseException  e )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
System.out.println ( "Parse error on node " + smlNode );

       super.error ( e );
     }

     public void  fatalError ( SAXParseException  e )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
System.out.println ( "Fatal parse error on node " + smlNode );

       super.fatalError ( e );
     }
*/

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
