     package com.croftsoft.core.util.pubsub;

     /*********************************************************************
     * An interface for publish-and-subscribe message recipients.
     *
     * @see
     *   Publisher
     *
     * @version
     *   2002-01-29
     * @since
     *   2002-01-29
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  Subscriber
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  receive ( Object  message );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
