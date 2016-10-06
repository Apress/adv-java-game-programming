     package com.croftsoft.core.util.cache.secure;

     import java.io.*;
     import java.security.*;

     /*********************************************************************
     * A FilterInputStream which will throw a SecurityException if the
     * stream content does not have the digest expected.
     *
     * @see
     *   java.security.DigestInputStream
     * @see
     *   java.security.MessageDigest
     *
     * @version
     *   1999-04-13
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  SecureInputStream extends FilterInputStream
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /** The expected digest. */
     private byte [ ]  digest;

     //////////////////////////////////////////////////////////////////////
     // Constructor method
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * @param  algorithm
     *   The MessageDigest algorithm to use.
     * @param  digest
     *   The expected digest.
     *********************************************************************/
     public  SecureInputStream (
       InputStream  inputStream,
       String       algorithm,
       byte [ ]     digest )
       throws NoSuchAlgorithmException
     //////////////////////////////////////////////////////////////////////
     {
       super ( new DigestInputStream ( inputStream,
         MessageDigest.getInstance ( algorithm ) ) );

       this.digest = digest;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * @throws  SecurityException
     *   If the end of the stream is reached and the digest is not what
     *   is expected.
     *********************************************************************/
     public int  read ( ) throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       int  i = super.read ( );

       if ( i < 0 ) checkDigest ( );

       return i;
     }

     /*********************************************************************
     * @throws  SecurityException
     *   If the end of the stream is reached and the digest is not what
     *   is expected.
     *********************************************************************/
     public int  read ( byte [ ]  byteArray, int  offset, int  length )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       int  i = super.read ( byteArray, offset, length );

       if ( i < length ) checkDigest ( );

       return i;
     }

     private void  checkDigest ( ) throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       if ( !MessageDigest.isEqual ( digest,
           ( ( DigestInputStream ) in ).getMessageDigest ( ).digest ( ) ) )
       {
         throw new IOException ( "bad message digest" );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
