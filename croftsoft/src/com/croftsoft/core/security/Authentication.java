     package com.croftsoft.core.security;

     import java.io.Serializable;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.ObjectLib;

     /*********************************************************************
     * Stores a username/password pair.
     *
     * @version
     *   2003-06-17
     * @since
     *   2001-04-12
     * @author
     *   <a href="http://www.alumni.caltech.edu/~croft/">David W. Croft</a>
     *********************************************************************/

     public final class  Authentication
       implements Cloneable, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final String  username;

     private final String  password;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *
     * @throws NullArgumentException
     *   If username is null.
     *********************************************************************/
     public  Authentication (
       String  username,
       String  password )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.username = username );

       this.password = password;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getUsername ( ) { return username; }

     public String  getPassword ( ) { return password; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Object  clone ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         return super.clone ( );
       }
       catch ( CloneNotSupportedException  ex )
       {
         // This will never happen.

         throw new RuntimeException ( );
       }          
     }

     public boolean  equals ( Object  other )
     //////////////////////////////////////////////////////////////////////
     {
       if ( other == null )
       {
         return false;
       }

       if ( !getClass ( ).equals ( other.getClass ( ) ) )
       {
         return false;
       }

       Authentication  that = ( Authentication ) other;

       return this.username.equals ( that.username )
         && ObjectLib.equivalent ( this.password, that.password );
     }

     public int  hashCode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return username.hashCode ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
