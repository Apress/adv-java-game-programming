     package com.croftsoft.apps.chat.server;

     import com.croftsoft.core.animation.model.ModelId;
     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.apps.chat.model.ChatModel;
     import com.croftsoft.apps.chat.model.ChatWorld;
     import com.croftsoft.apps.chat.request.CreateModelRequest;
     import com.croftsoft.apps.chat.request.Request;
     import com.croftsoft.apps.chat.response.CreateModelResponse;
     import com.croftsoft.apps.chat.user.User;

     /*********************************************************************
     * Makes an avatar.
     *
     * @version
     *   2003-06-17
     * @since
     *   2003-06-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  CreateModelServer
       extends AbstractServer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final ChatWorld  chatWorld;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  CreateModelServer ( ChatWorld  chatWorld )
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
       CreateModelRequest  createModelRequest
         = ( CreateModelRequest ) request;

       double  x = createModelRequest.getX ( );

       double  y = createModelRequest.getY ( );

       ModelId  modelId = user.getModelId ( );

       if ( modelId != null )
       {
         ChatModel  chatModel = chatWorld.getChatModel ( modelId );

         if ( chatModel != null )
         {
           x = chatModel.getCenterX ( );

           y = chatModel.getCenterY ( );
         }

         chatWorld.removeModel ( modelId );
       }

       modelId = chatWorld.createModel (
         createModelRequest.getAvatarType ( ), x, y );

       user.setModelId ( modelId );

       return new CreateModelResponse ( modelId );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
