     package com.croftsoft.core.util.cache;

     import java.io.*;
     import java.net.*;

     /*********************************************************************
     * A ContentAccessor that accesses the content via a URL.
     *
     * @version
     *   1999-04-24
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  URLContentAccessor
       implements ContentAccessor, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 1L;

     protected String  urlName;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  URLContentAccessor ( String  urlName )
       throws MalformedURLException
     //////////////////////////////////////////////////////////////////////
     {
       this.urlName = urlName;


       // Test to see if valid URL.
       new URL ( urlName );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getURLName ( ) { return urlName; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public InputStream  getInputStream ( ) throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       URL  url = null;

       try
       {
         url = new URL ( urlName );
       }
       catch ( MalformedURLException  ex )
       {
         return null;
       }

       URLConnection  urlConnection = url.openConnection ( );

       if ( urlConnection instanceof HttpURLConnection )
       {
         HttpURLConnection  httpURLConnection
           = ( HttpURLConnection ) urlConnection;

         if ( httpURLConnection.getResponseCode ( )
           != HttpURLConnection.HTTP_OK )
         {
           return null;
         }
       }

       return urlConnection.getInputStream ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
