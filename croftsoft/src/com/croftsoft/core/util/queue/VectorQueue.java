     package com.croftsoft.core.util.queue;

     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * An implementation of Queue that relies upon a Vector backbone.
     *
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     * @version
     *   2003-05-22
     * @since
     *   1999-02-07
     *********************************************************************/

     public final class  VectorQueue
       implements Queue
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Vector  vector;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  VectorQueue ( Vector  vector )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.vector = vector );
     }

     public  VectorQueue ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new Vector ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  append ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( o );

       synchronized ( vector )
       {
         vector.addElement ( o );

         vector.notifyAll ( );
       }

       return true;
     }

     public Object  poll ( )
     //////////////////////////////////////////////////////////////////////
     {
       synchronized ( vector )
       {
         if ( vector.size ( ) > 0 )
         {
           Object  o = vector.elementAt ( 0 );

           vector.removeElementAt ( 0 );

           return o;
         }
       }

       return null;
     }

     public Object  pull ( )
       throws InterruptedException
     //////////////////////////////////////////////////////////////////////
     {
       return pull ( 0 );
     }

     public Object  pull ( long  timeout )
       throws InterruptedException
     //////////////////////////////////////////////////////////////////////
     {
       if ( timeout < 0 )
       {
         throw new IllegalArgumentException ( "timeout < 0" );
       }

       long  stopTime = System.currentTimeMillis ( ) + timeout;

       Object  o = null;

       while ( ( o = poll ( ) ) == null )
       {
         if ( timeout == 0 )
         {
           synchronized ( vector )
           {
             vector.wait ( );
           }
         }
         else
         {
           long  now = System.currentTimeMillis ( );

           if ( stopTime > now )
           {
             synchronized ( vector )
             {
               vector.wait ( stopTime - now );
             }
           }
           else
           {
             break;
           }
         }
       }

       return o;
     }

     public Object  replace ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       synchronized ( vector )
       {
         int  index = vector.indexOf ( o );

         if ( index < 0 )
         {
           append ( o );

           return null;
         }
         else
         {
           Object  oldObject = vector.elementAt ( index );

           vector.setElementAt ( o, index );

           return oldObject;
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
