     package com.croftsoft.core.util.queue;

     import java.io.Serializable;
     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * An implementation of Queue that relies upon a List backbone.
     *
     * @version
     *   2003-06-06
     * @since
     *   1999-02-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ListQueue
       implements Queue, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final List  list;

     private final int   maxSize;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ListQueue (
       List  list,
       int   maxSize )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.list = list );

       this.maxSize = maxSize;
     }

     public  ListQueue ( List  list )
     //////////////////////////////////////////////////////////////////////
     {
       this ( list, Integer.MAX_VALUE );
     }

     public  ListQueue ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new LinkedList ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  append ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( o );

       synchronized ( list )
       {
         if ( list.size ( ) < maxSize )
         {
           list.add ( o );

           list.notifyAll ( );

           return true;
         }
       }

       return false;
     }

     public Object  poll ( )
     //////////////////////////////////////////////////////////////////////
     {
       synchronized ( list )
       {
         if ( list.size ( ) > 0 )
         {
           return list.remove ( 0 );
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
           synchronized ( list )
           {
             list.wait ( );
           }
         }
         else
         {
           long  nowTime = System.currentTimeMillis ( );

           if ( stopTime > nowTime )
           {
             synchronized ( list )
             {
               list.wait ( stopTime - nowTime );
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
       throws IndexOutOfBoundsException
     //////////////////////////////////////////////////////////////////////
     {
       synchronized ( list )
       {
         int  index = list.indexOf ( o );

         if ( index < 0 )
         {
           if ( append ( o ) )
           {
             return null;
           }

           throw new IndexOutOfBoundsException ( );
         }
         else
         {
           return list.set ( index, o );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
