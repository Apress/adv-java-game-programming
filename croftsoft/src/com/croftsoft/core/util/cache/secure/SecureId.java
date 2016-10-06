     package com.croftsoft.core.util.cache.secure;

     import java.security.MessageDigest;

     import com.croftsoft.core.util.id.Id;

     /*********************************************************************
     * A concrete Id implementation that uses a secure digest to verify
     * identity.
     *
     * <P>
     *
     * @see
     *   com.croftsoft.core.j1d1.util.id.Id;
     * @see
     *   java.security.MessageDigest
     *
     * @version
     *   2000-08-19
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public final class  SecureId implements Id
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final String    algorithm;

     private final byte [ ]  digest;

     //////////////////////////////////////////////////////////////////////
     // Constructor methods
     //////////////////////////////////////////////////////////////////////

     public  SecureId (
       String    algorithm,
       byte [ ]  digest )
     //////////////////////////////////////////////////////////////////////
     {
       if ( algorithm == null )
       {
         throw new IllegalArgumentException ( "null algorithm" );
       }

       if ( digest == null )
       {
         throw new IllegalArgumentException ( "null digest" );
       }

       this.algorithm = algorithm;

       this.digest    = digest;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String    getAlgorithm ( ) { return algorithm; }

     public byte [ ]  getDigest    ( ) { return digest;    }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  equals ( Object  other )
     //////////////////////////////////////////////////////////////////////
     {
       if ( other == null ) return false;

       if ( !getClass ( ).equals ( other.getClass ( ) ) ) return false;

       SecureId  that = ( SecureId ) other;

       return MessageDigest.isEqual ( this.digest, that.digest )
         && this.algorithm.equals ( that.algorithm );
     }

     public int  hashCode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return new String ( digest ).hashCode ( );
     }

     public Object  clone ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         return super.clone ( );
       }
       catch ( CloneNotSupportedException  ex )
       {
         // this will never happen

         throw new RuntimeException ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
