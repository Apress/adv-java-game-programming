     package com.croftsoft.core.applet;

     import java.applet.*;
     import java.io.*;
     import java.util.zip.*;

     import com.croftsoft.core.io.SerializableLib;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Static library methods for manipulating Applets.
     *
     * @version
     *   2003-03-14
     * @since
     *   2002-12-22
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AppletLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Loads GZIP compressed data.
     *********************************************************************/
     public static Serializable  loadSerializableUsingAppletPersistence (
       Applet  applet,
       String  key )
       throws ClassNotFoundException, IOException,
         UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( applet, "null applet" );

       NullArgumentException.check ( key, "null key" );

       AppletContext  appletContext = null;

       try
       {
         appletContext = applet.getAppletContext ( );
       }
       catch ( NullPointerException  ex )
       {
       }

       if ( appletContext == null )
       {
         throw new UnsupportedOperationException ( "null AppletContext" );
       }

       InputStream  inputStream = appletContext.getStream ( key );

       if ( inputStream == null )
       {
         return null;
       }

       return SerializableLib.load ( inputStream );
     }

     /*********************************************************************
     * Saves data using GZIP compression.
     *********************************************************************/
     public static void  saveSerializableUsingAppletPersistence (
       Applet        applet,
       String        key,
       Serializable  serializable )
       throws IOException, UnsupportedOperationException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( applet, "null applet" );

       NullArgumentException.check ( key, "null key" );

       NullArgumentException.check ( serializable, "null serializable" );

       AppletContext  appletContext = null;

       try
       {
         appletContext = applet.getAppletContext ( );
       }
       catch ( NullPointerException  ex )
       {
       }

       if ( appletContext == null )
       {
         throw new UnsupportedOperationException ( "null AppletContext" );
       }

       InputStream  inputStream = new ByteArrayInputStream (
         SerializableLib.compress ( serializable ) );

       appletContext.setStream ( key, inputStream );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  AppletLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }