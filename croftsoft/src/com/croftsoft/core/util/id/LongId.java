     package com.croftsoft.core.util.id;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * An Id implementation that uses a Long object as its value.
     *
     * @version
     *   2003-06-17
     * @since
     *   2000-04-20
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  LongId
       implements Id
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final Long  l;

     //////////////////////////////////////////////////////////////////////
     // Constructor methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *
     * @throws NullArgumentException
     *
     *   If argument is null.
     *********************************************************************/
     public  LongId ( Long  l )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.l = l );
     }

     public  LongId ( long  l )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new Long ( l ) );
     }

     public  LongId ( String  s )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new Long ( s ) );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor method
     //////////////////////////////////////////////////////////////////////

     public Long  getL ( ) { return l; }

     //////////////////////////////////////////////////////////////////////
     // Id interface methods
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
       if ( ( other == null )
         || !getClass ( ).equals ( other.getClass ( ) ) )

       {
         return false;
       }

       return l.equals ( ( ( LongId ) other ).l );
     }

     public int  hashCode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return l.hashCode ( );
     }

     public String  toString ( )
     //////////////////////////////////////////////////////////////////////
     {
       return l.toString ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
