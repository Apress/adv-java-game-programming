     package com.croftsoft.apps.chat.server;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.role.Consumer;

     import com.croftsoft.apps.chat.event.TalkEvent;
     import com.croftsoft.apps.chat.request.TalkRequest;
     import com.croftsoft.apps.chat.request.Request;
     import com.croftsoft.apps.chat.user.User;

     /*********************************************************************
     * Serves a TalkRequest.
     *
     * @version
     *   2003-06-11
     * @since
     *   2003-06-11
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  TalkServer
       extends AbstractServer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Consumer  eventConsumer;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  TalkServer ( Consumer  eventConsumer )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.eventConsumer = eventConsumer );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Object  serve (
       User     user,
       Request  request )
     //////////////////////////////////////////////////////////////////////
     {
       TalkRequest  talkRequest = ( TalkRequest ) request;

       String  text = talkRequest.getText ( );

       eventConsumer.consume ( new TalkEvent ( text ) );

       return null;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
