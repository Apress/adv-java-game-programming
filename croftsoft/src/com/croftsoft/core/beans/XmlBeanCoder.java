     package com.croftsoft.core.beans;
     
     import java.beans.*;
     import java.io.*;

     import com.croftsoft.core.io.Encoder;
     import com.croftsoft.core.io.Parser;

     /*********************************************************************
     * Encodes and decodes objects using XMLEncoder and XMLDecoder.
     *
     * @see
     *   java.beans.XMLEncoder
     * @see
     *   java.beans.XMLDecoder
     *
     * @version
     *   2003-06-12
     * @since
     *   2003-04-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  XmlBeanCoder
       implements Encoder, Parser
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final XmlBeanCoder  INSTANCE = new XmlBeanCoder ( );

     //////////////////////////////////////////////////////////////////////
     // static methods
     //////////////////////////////////////////////////////////////////////

     public static Object  decodeFromXml ( byte [ ]  bytes )
     //////////////////////////////////////////////////////////////////////
     {
       return decodeFromXml ( new ByteArrayInputStream ( bytes ) );
     }

     public static Object  decodeFromXml ( InputStream  inputStream )
     //////////////////////////////////////////////////////////////////////
     {
       XMLDecoder  xmlDecoder = new XMLDecoder ( inputStream );

       try
       {
         return xmlDecoder.readObject ( );
       }
       finally
       {
         xmlDecoder.close ( );
       }
     }

     public static void  encodeAsXml (
       Object        o,
       OutputStream  outputStream )
     //////////////////////////////////////////////////////////////////////
     {
       XMLEncoder  xmlEncoder = new XMLEncoder ( outputStream );

       try
       {
         xmlEncoder.writeObject ( o );
       }
       finally
       {
         xmlEncoder.close ( );
       }
     }

     public static byte [ ]  encodeAsXml ( Object o )
     //////////////////////////////////////////////////////////////////////
     {
       ByteArrayOutputStream  byteArrayOutputStream
         = new ByteArrayOutputStream ( );

       encodeAsXml ( o, byteArrayOutputStream );

       return byteArrayOutputStream.toByteArray ( );
     }

     public static Object  loadFromXmlFile ( String  filename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return decodeFromXml (
         new BufferedInputStream ( new FileInputStream ( filename ) ) );
     }

     public static void  saveToXmlFile (
       Object  object,
       String  filename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       encodeAsXml (
         object,
         new BufferedOutputStream ( new FileOutputStream ( filename ) ) );
     }

     //////////////////////////////////////////////////////////////////////
     // interface Encoder and Parser methods
     //////////////////////////////////////////////////////////////////////

     public byte [ ]  encode ( Object  object )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return encodeAsXml ( object );
     }

     public Object  parse (
       InputStream  inputStream,
       int          contentLength )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return decodeFromXml ( inputStream );
     }

     //////////////////////////////////////////////////////////////////////
     // private constructor method
     //////////////////////////////////////////////////////////////////////

     private  XmlBeanCoder ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
