     package com.croftsoft.apps.chat.server;

     import com.croftsoft.core.math.MathConstants;
     import com.croftsoft.core.util.queue.Queue;

     import com.croftsoft.apps.chat.event.Event;
     import com.croftsoft.apps.chat.event.NullEvent;
     import com.croftsoft.apps.chat.request.Request;
     import com.croftsoft.apps.chat.request.PullRequest;
     import com.croftsoft.apps.chat.user.User;

     /*********************************************************************
     * Serves a PullRequest.
     *
     * @version
     *   2003-06-18
     * @since
     *   2003-06-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  PullServer
       extends AbstractServer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final long  DEFAULT_QUEUE_PULL_TIMEOUT
       = 30 * MathConstants.MILLISECONDS_PER_SECOND;

     //

     private final long  queuePullTimeout;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  PullServer ( long  queuePullTimeout )
     //////////////////////////////////////////////////////////////////////
     {
       this.queuePullTimeout = queuePullTimeout;
     }

     public  PullServer ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( DEFAULT_QUEUE_PULL_TIMEOUT );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Object  serve (
       User     user,
       Request  request )
     //////////////////////////////////////////////////////////////////////
     {
       Queue  messageQueue = user.getMessageQueue ( );

       Object  message = null;

       try
       {
         message = messageQueue.pull ( queuePullTimeout );
       }
       catch ( InterruptedException  ex )
       {
       }

       if ( message == null )
       {
         message = new NullEvent ( user.getEventIndex ( ) );
       }
       else if ( message instanceof Event )
       {
         ( ( Event ) message ).setEventIndex ( user.nextEventIndex ( ) );         
       }

       return message;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
