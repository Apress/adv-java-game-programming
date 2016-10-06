     package com.croftsoft.core.util.state;

     /*********************************************************************
     *
     * Static methods to support concrete implementations of the State
     * interface.
     *
     * <P>
     *
     * Reference:<BR>
     * Mark Roulo,
     * <A HREF="http://www.javaworld.com/javaworld/jw-01-1999/jw-01-object.html">
     * "How to avoid traps and correctly override methods
     * from java.lang.Object"</A>, January 1999, JavaWorld.
     *
     * <P>
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-02-06
     *********************************************************************/

     public class  StateLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private  StateLib ( ) { }

     /*********************************************************************
     * Returns true if the classes and State keys are equal.
     *
     * State implementation usage:
     * <PRE>
     * public boolean  equals ( Object  other )
     * {
     *   return StateLib.equals ( this, other );
     * }
     * </PRE>
     *********************************************************************/
     public static boolean  equals ( State  state, Object  other )
     //////////////////////////////////////////////////////////////////////
     {
       if ( state == null )
       {
         throw new IllegalArgumentException ( "null state" );
       }

       if ( ( other == null )
         || !other.getClass ( ).equals ( state.getClass ( ) ) )
       {
         return false;
       }

       return state.getKey ( ).equals ( ( ( State ) other ).getKey ( ) );
     }

     /*********************************************************************
     * Returns the hash code of the State key.
     *
     * <PRE>
     *   return state.getKey ( ).hashCode ( );
     * </PRE>
     *
     * State implementation usage:
     * <PRE>
     * public int  hashCode ( )
     * {
     *   return StateLib.hashCode ( this );
     * }
     * </PRE>
     *********************************************************************/
     public static int  hashCode ( State  state )
     //////////////////////////////////////////////////////////////////////
     {
       return state.getKey ( ).hashCode ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
