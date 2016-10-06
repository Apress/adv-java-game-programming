     package com.croftsoft.core.util.pubsub;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.ex.ExceptionHandler;
     import com.croftsoft.core.util.ArrayLib;

     /*********************************************************************
     * A synchronous Publisher implementation backed by a Subscriber array.
     *
     * <p>
     * In this simple Publisher implementation, message propagation is
     * synchronous; all of the methods are synchronized and the publish()
     * method does not return until all Subscribers have been contacted
     * one-at-a-time and in array order.
     * </p>
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     *
     * @see
     *   Publisher
     * @see
     *   Subscriber
     *
     * @version
     *   2002-01-29
     * @since
     *   2001-04-04
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ArrayPublisher
       implements Publisher
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final ExceptionHandler   exceptionHandler;

     private Subscriber [ ]  subscribers;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * @param  exceptionHandler
     *
     *   Handles any Exceptions thrown by Subscriber.receive().
     *   If null, exception.printStackTrace() will be used by default.
     *********************************************************************/
     public  ArrayPublisher (
       ExceptionHandler  exceptionHandler,
       Subscriber [ ]    subscribers )
     //////////////////////////////////////////////////////////////////////
     {
       this.exceptionHandler = exceptionHandler;

       NullArgumentException.check ( this.subscribers = subscribers );
     }

     /*********************************************************************
     * this ( null, new Subscriber [ ] { } );
     *********************************************************************/
     public  ArrayPublisher ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null, new Subscriber [ ] { } );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * @return
     *   False if the Subscriber was already in the array.
     *********************************************************************/
     public synchronized boolean  addSubscriber ( Subscriber  subscriber )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( subscriber );

       for ( int  i = subscribers.length - 1; i > -1; i-- )
       {
         if ( subscribers [ i ] == subscriber )
         {
           return false;
         }
       }

       subscribers
         = ( Subscriber [ ] ) ArrayLib.append ( subscribers, subscriber );

       return true;
     }

     /*********************************************************************
     * @return
     *   False if the Subscriber was not in the array to be removed.
     *********************************************************************/
     public synchronized boolean  removeSubscriber (
       Subscriber  subscriber )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( subscriber );

       int  index = -1;

       for ( int  i = subscribers.length - 1; i > -1; i-- )
       {
         if ( subscribers [ i ] == subscriber )
         {
           index = i;

           break;
         }
       }

       if ( index < 0 )
       {
         return false;
       }

       subscribers
         = ( Subscriber [ ] ) ArrayLib.remove ( subscribers, index );

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  publish ( Object  message )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < subscribers.length; i++ )
       {
         try
         {
           subscribers [ i ].receive ( message );
         }
         catch ( Exception  ex )
         {
           if ( exceptionHandler != null )
           {
             exceptionHandler.handleException ( ex, subscribers [ i ] );
           }
           else
           {
             ex.printStackTrace ( );
           }
         }
       }
     }

     public synchronized void  receive ( Object  message )
     //////////////////////////////////////////////////////////////////////
     {
       publish ( message );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }