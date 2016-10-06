     package com.croftsoft.core.text.xml;

//     import java.io.*;
//     import java.util.zip.ZipInputStream;
//     import javax.xml.parsers.*;
     import org.xml.sax.*;
     import org.xml.sax.helpers.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.role.Consumer;

     /*********************************************************************
     * Filters DefaultHandler operations.
     *
     * <p>
     * Override the FilterDefaultHandler methods to filter calls to the
     * DefaultHandler delegate.
     * The initial DefaultHandler delegate can be replaced which allows you
     * to swap the implementation as necessary.
     * </p>
     *
     * @version
     *   2002-09-18
     * @since
     *   2002-09-18
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public class  FilterDefaultHandler
       extends DefaultHandler
       implements EntityResolver, DTDHandler, ContentHandler, ErrorHandler
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private DefaultHandler  defaultHandler;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  FilterDefaultHandler ( DefaultHandler  defaultHandler )
     //////////////////////////////////////////////////////////////////////
     {
       setDefaultHandler ( defaultHandler );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public DefaultHandler  getDefaultHandler ( )
     //////////////////////////////////////////////////////////////////////
     {
       return defaultHandler;
     }

     public void  setDefaultHandler ( DefaultHandler  defaultHandler )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.defaultHandler = defaultHandler );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  characters (
       char [ ]  ch,
       int       start,
       int       length )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
       defaultHandler.characters ( ch, start, length );
     }

     public void  endDocument ( )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
       defaultHandler.endDocument ( );
     }

     public void  endElement (
       String      namespaceURI,
       String      localName,
       String      qName )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
       defaultHandler.endElement ( namespaceURI, localName, qName );
     }

     public void  endPrefixMapping ( String  prefix )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
       defaultHandler.endPrefixMapping ( prefix );
     }

     public void  error ( SAXParseException  e )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
       defaultHandler.error ( e );
     }

     public void  fatalError ( SAXParseException  e )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
       defaultHandler.fatalError ( e );
     }

     public void  ignorableWhitespace (
       char [ ]  ch,
       int       start,
       int       length )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
       defaultHandler.ignorableWhitespace ( ch, start, length );
     }

     public void  notationDecl (
       String  name,
       String  publicId,
       String  systemId )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
       defaultHandler.notationDecl ( name, publicId, systemId );
     }

     public void  processingInstruction (
       String  target,
       String  data )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
       defaultHandler.processingInstruction ( target, data );
     }

     public InputSource  resolveEntity (
       String  publicId,
       String  systemId )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
       return defaultHandler.resolveEntity ( publicId, systemId );
     }

     public void  setDocumentLocator ( Locator  locator )
     //////////////////////////////////////////////////////////////////////
     {
       defaultHandler.setDocumentLocator ( locator );
     }

     public void  skippedEntity ( String  name )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
       defaultHandler.skippedEntity ( name );
     }

     public void  startDocument ( )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
       defaultHandler.startDocument ( );
     }

     public void  startElement (
       String      namespaceURI,
       String      localName,
       String      qName,
       Attributes  atts )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
       defaultHandler.startElement (
         namespaceURI, localName, qName, atts );
     }

     public void  startPrefixMapping (
       String  prefix,
       String  uri )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
       defaultHandler.startPrefixMapping ( prefix, uri );
     }

     public void  unparsedEntityDecl (
       String  name,
       String  publicId,
       String  systemId,
       String  notationName )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
       defaultHandler.unparsedEntityDecl (
         name, publicId, systemId, notationName );
     }

     public void  warning ( SAXParseException  e )
       throws SAXException
     //////////////////////////////////////////////////////////////////////
     {
       defaultHandler.warning ( e );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }