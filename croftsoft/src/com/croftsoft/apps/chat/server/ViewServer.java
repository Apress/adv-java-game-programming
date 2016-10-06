     package com.croftsoft.apps.chat.server;

     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.apps.chat.model.seri.SeriChatWorld;
     import com.croftsoft.apps.chat.request.ViewRequest;
     import com.croftsoft.apps.chat.request.Request;
     import com.croftsoft.apps.chat.response.ViewResponse;
     import com.croftsoft.apps.chat.user.User;

     /*********************************************************************
     * Returns a view of the game.
     *
     * @version
     *   2003-06-18
     * @since
     *   2003-06-11
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ViewServer
       extends AbstractServer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final SeriChatWorld  seriChatWorld;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ViewServer ( SeriChatWorld  seriChatWorld )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.seriChatWorld = seriChatWorld );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Object  serve (
       User     user,
       Request  request )
     //////////////////////////////////////////////////////////////////////
     {
       long  eventIndex = user.nextEventIndex ( );

       return new ViewResponse (
         seriChatWorld,
         eventIndex );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
