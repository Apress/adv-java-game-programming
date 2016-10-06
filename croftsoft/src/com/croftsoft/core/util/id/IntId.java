     package com.croftsoft.core.util.id;

     /*********************************************************************
     * An Id implementation that uses an integer as its value.
     *
     * <p>
     * Maintains a static variable "next" which it increments with each
     * new IntId object created using the zero-argument constructor.
     * This ensures uniqueness among IntegerId objects created within the
     * current process.
     * </p>
     *
     * @version
     *   2003-06-07
     * @since
     *   2000-01-16
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  IntId
       implements Id
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private static int  next = 0;

     //

     private int  i;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  IntId ( int  i )
     //////////////////////////////////////////////////////////////////////
     {
       this.i = i;
     }

     /*********************************************************************
     * this ( next++ );
     *********************************************************************/
     public  IntId ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( next ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor method
     //////////////////////////////////////////////////////////////////////

     public int  getI ( ) { return i; }

     //////////////////////////////////////////////////////////////////////
     // Id interface methods
     //////////////////////////////////////////////////////////////////////

     public boolean  equals ( Object  other )
     //////////////////////////////////////////////////////////////////////
     {
       if ( other == null ) return false;

       if ( !getClass ( ).equals ( other.getClass ( ) ) ) return false;

       return ( i == ( ( IntId ) other ).i );
     }

     public int  hashCode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return i;
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
         // This will never happen.

         throw new RuntimeException ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // Other methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * return Integer.toString ( i );
     *********************************************************************/
     public String  toString ( )
     //////////////////////////////////////////////////////////////////////
     {
       return Integer.toString ( i );
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private static synchronized int  next ( ) { return next++; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
