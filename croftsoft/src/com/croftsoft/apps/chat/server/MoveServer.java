     package com.croftsoft.apps.chat.server;

     import com.croftsoft.core.animation.model.ModelId;
     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.apps.chat.model.ChatModel;
     import com.croftsoft.apps.chat.model.ChatWorld;
     import com.croftsoft.apps.chat.request.MoveRequest;
     import com.croftsoft.apps.chat.request.Request;
     import com.croftsoft.apps.chat.response.MoveResponse;
     import com.croftsoft.apps.chat.user.User;

     /*********************************************************************
     * Moves an avatar.
     *
     * @version
     *   2003-06-10
     * @since
     *   2003-06-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  MoveServer
       extends AbstractServer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final ChatWorld  chatWorld;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  MoveServer ( ChatWorld  chatWorld )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.chatWorld = chatWorld );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Object  serve (
       User     user,
       Request  request )
     //////////////////////////////////////////////////////////////////////
     {
       MoveRequest  moveRequest = ( MoveRequest ) request;

       ModelId  modelId = user.getModelId ( );

       if ( modelId == null )
       {
         return new MoveResponse ( true, true, false );
       }

       ChatModel  chatModel = chatWorld.getChatModel ( modelId );

       if ( chatModel == null )
       {
         return new MoveResponse ( true, false, true );
       }

       chatModel.setDestination ( moveRequest.getDestination ( ) );

       return null;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
