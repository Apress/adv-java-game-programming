     package com.croftsoft.core.text.sml;

     import java.io.*;
     import java.util.zip.ZipInputStream;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.role.Consumer;
     import com.croftsoft.core.text.sml.SmlNode;

     /*********************************************************************
     * Used to parse large SML files one data record at a time.
     *
     * <p>
     * The input is assumed to be a very large Simplified Markup Language
     * (SML) file consisting of a root node containing zero or more child
     * nodes, each child representing an individual data record.  As each
     * direct child of the top-level root element is parsed, it is passed
     * as an SmlNode to a Consumer.
     * </p>
     *
     * <p>
     * This parser is useful when you have a database dump in SML
     * format and you want to read it back in one data record at a time.
     * Since the data file is large, your Consumer implementation will
     * typically commit each record to secondary storage (disk or database)
     * as it is received.  This prevents an out-of-memory condition that
     * might result from loading the entire file into primary storage
     * (memory) as a Document Object Model (DOM), an object graph composed
     * of a root node and multiple child nodes, as it is being parsed.
     * </p>
     *
     * <p>
     * Example:
     * <pre>
     * SmlNodeLoader.load ( smlInputStream,
     *   new Consumer ( )
     *   {
     *     public void  consume ( Object  o )
     *     {
     *       SmlNode  smlNode = ( SmlNode ) o;
     *
     *       User  user = User.fromSmlNode ( smlNode );
     *
     *       userDatabase.add ( user );
     *     }
     *   } );
     * </pre>
     * </p>
     *
     * <p>
     * It is assumed that an SML node will have never have both character
     * data and SML nodes mixed together as immediate children.  Given that
     * assumption, this parser will overwrite a parsed String child with a
     * subsequently parsed SmlNode child.  Additionally, character data will
     * not be recorded as a child once an SmlNode child is already in place.
     * This is useful for preventing unnecessary white space between element
     * tags in the SML file from being stored as character data.
     * </p>
     *
     * @version
     *   2001-05-18
     * @since
     *   2001-05-10
     * @author
     *   <a href="http://www.alumni.caltech.edu/~croft/">David W. Croft</a>
     *********************************************************************/

     public final class  SmlNodeLoader
       implements SmlParseHandler
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private Consumer             smlNodeConsumer;

     private SmlNodeParseHandler  smlNodeParseHandler;

     private int                  depth;

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
         args [ 0 ].toLowerCase ( ).endsWith ( ".zip" ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  parse (
       InputStream  inputStream,
       Consumer     smlNodeConsumer )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       SmlParseHandler  smlParseHandler
         = new SmlNodeLoader ( smlNodeConsumer );

       SmlNodeLib.parse ( inputStream, smlParseHandler );
     }

     public static void  parse (
       String    smlDataFilename,
       Consumer  smlNodeConsumer,
       boolean   isZipFile )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       InputStream  inputStream = null;

       try
       {
         inputStream = new BufferedInputStream (
           new FileInputStream ( smlDataFilename ) );

         if ( isZipFile )
         {
           ZipInputStream  zipInputStream
             = new ZipInputStream ( inputStream );

           zipInputStream.getNextEntry ( );

           inputStream = zipInputStream;
         }

         parse ( inputStream, smlNodeConsumer );
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

     private  SmlNodeLoader ( Consumer  smlNodeConsumer )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.smlNodeConsumer = smlNodeConsumer );

       smlNodeParseHandler = new SmlNodeParseHandler ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  handleCData ( String  cData )
     //////////////////////////////////////////////////////////////////////
     {
       smlNodeParseHandler.handleCData ( cData );
     }

     public void  handleElementOpen ( String  elementName )
     //////////////////////////////////////////////////////////////////////
     {
       ++depth;

       smlNodeParseHandler.handleElementOpen ( elementName );
     }

     public void  handleElementClose ( String  elementName )
     //////////////////////////////////////////////////////////////////////
     {
       --depth;

       if ( depth == 1 )
       {
         smlNodeConsumer.consume ( smlNodeParseHandler.getSmlNode ( ) );

         smlNodeParseHandler.handleElementClose ( elementName );

         smlNodeParseHandler.getSmlNode ( ).removeChildren ( );
       }
       else
       {
         smlNodeParseHandler.handleElementClose ( elementName );
       }
     }

     public void  handleParseError ( )
     //////////////////////////////////////////////////////////////////////
     {
       smlNodeParseHandler.handleParseError ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }