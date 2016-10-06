     package com.croftsoft.core.util.pubsub;

     /*********************************************************************
     * An interface for publish-and-subscribe publishers.
     *
     * <p>
     * Used to pass messages and events between loosely coupled objects.
     * </p>
     *
     * <p>
     * This interface extends interface Subscriber so that it can be used
     * as a one-to-many relay; received messages are simply published.
     * </p>
     *
     * @see
     *   Subscriber
     *
     * @version
     *   2002-01-29
     * @since
     *   2002-01-29
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  Publisher
       extends Subscriber
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public boolean  addSubscriber ( Subscriber  subscriber );

     public boolean  removeSubscriber ( Subscriber  subscriber );

     public void  publish ( Object  message );

     /*********************************************************************
     * Simply calls method <code>publish(message)</code>.
     *
     * <p>
     * This interface extends interface Subscriber so that it can be used
     * as a one-to-many relay; received messages are published.
     * </p>
     *********************************************************************/
     public void  receive ( Object  message );
     
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
