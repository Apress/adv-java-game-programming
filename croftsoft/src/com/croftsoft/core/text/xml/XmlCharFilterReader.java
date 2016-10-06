     package com.croftsoft.core.text.xml;

     import java.io.*;

     /*********************************************************************
     * Filters out characters not permitted in XML.
     *
     * @see
     *   <a target="_blank" href="http://www.w3.org/TR/REC-xml#charsets">
     *   Extensible Markup Language (XML) 1.0 (Second Edition),
     *   2.2 Characters</a>
     *
     * @version
     *   2002-09-24
     * @since
     *   2002-09-19
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public final class  XmlCharFilterReader
       extends FilterReader
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Main constructor.
     *********************************************************************/
     public  XmlCharFilterReader ( Reader  in )
     //////////////////////////////////////////////////////////////////////
     {
       super ( in );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int  read ( )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       int  ch;

       while ( ( ch = super.read ( ) ) > -1 )
       {
         if ( ( ch == 0x9 )
           || ( ch == 0xA )
           || ( ch == 0xD )
           || ( ( ch >= 0x00020 ) && ( ch <= 0x00D7FF ) )
           || ( ( ch >= 0x0E000 ) && ( ch <= 0x00FFFD ) )
           || ( ( ch >= 0x10000 ) && ( ch <= 0x10FFFF ) ) )
         {
           return ch;
         }
       }

       return -1;
     }

     public int  read (
       char [ ]  cbuf,
       int       off,
       int       len )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       int  count = 0;

       for ( int  i = 0; i < len; i++ )
       {
         int  ch = read ( );

         if ( ch < 0 )
         {
           return count > 0 ? count : -1;
         }

         cbuf [ off + count ] = ( char ) ch;

         count++;
       }

       return len;
     }

     public int  read ( char [ ]  cbuf )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return read ( cbuf, 0, cbuf.length );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }