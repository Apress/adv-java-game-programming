     package com.croftsoft.core.security;

     import java.io.*;
     import java.security.*;

     /*********************************************************************
     * Static method library for creating message digests of byte streams.
     *
     * @version
     *   2003-03-27
     * @since
     *   1998-10-04
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  DigestLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static byte [ ]  digest (
       InputStream  inputStream,
       String       algorithm )
       throws IOException, NoSuchAlgorithmException
     //////////////////////////////////////////////////////////////////////
     {
       BufferedInputStream  bufferedInputStream
         = new BufferedInputStream ( inputStream );

       MessageDigest  messageDigest
         = MessageDigest.getInstance ( algorithm );

       int  i;

       while ( ( i = bufferedInputStream.read ( ) ) > -1 )
       {
         messageDigest.update ( ( byte ) i );
       }

       bufferedInputStream.close ( );

       return messageDigest.digest ( );
     }

     public static byte [ ]  digest (
       File    file,
       String  algorithm )
       throws IOException, NoSuchAlgorithmException
     //////////////////////////////////////////////////////////////////////
     {
       return digest ( new FileInputStream ( file ), algorithm );
     }

     public static byte [ ]  digest ( File  file )
       throws IOException, NoSuchAlgorithmException
     //////////////////////////////////////////////////////////////////////
     {
       return digest ( file, "SHA-1" );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  DigestLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
