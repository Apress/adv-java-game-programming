     package com.croftsoft.apps.chat.view;

     import com.croftsoft.core.animation.model.Model;
     import com.croftsoft.core.animation.model.ModelId;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.geom.PointXY;
     import com.croftsoft.core.role.Consumer;

     import com.croftsoft.apps.chat.event.CreateModelEvent;
     import com.croftsoft.apps.chat.event.MoveEvent;
     import com.croftsoft.apps.chat.event.NullEvent;
     import com.croftsoft.apps.chat.event.RemoveModelEvent;
     import com.croftsoft.apps.chat.model.ChatGame;
     import com.croftsoft.apps.chat.model.ChatModel;
     import com.croftsoft.apps.chat.model.ChatWorld;
     import com.croftsoft.apps.chat.response.ViewResponse;

     /*********************************************************************
     * ChatGame synchronizer.
     *
     * @version
     *   2003-06-18
     * @since
     *   2003-06-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ChatSynchronizer
       implements Consumer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Consumer   eventConsumer;

     private final ChatWorld  chatWorld;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ChatSynchronizer (
       ChatWorld  chatWorld,
       Consumer   eventConsumer )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.chatWorld     = chatWorld     );


       NullArgumentException.check ( this.eventConsumer = eventConsumer );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  consume ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       if ( o instanceof ViewResponse )
       {
         ChatWorld  newChatWorld
           = ( ( ViewResponse ) o ).getSeriChatWorld ( );

         chatWorld.clear ( );

         Model [ ]  models = newChatWorld.getModels ( );

         for ( int  i = 0; i < models.length; i++ )
         {
           ChatModel  chatModel = ( ChatModel ) models [ i ];

           chatModel.setEventConsumer ( eventConsumer );

           chatWorld.addChatModel ( chatModel );
         }

         return;
       }

       if ( o instanceof MoveEvent )
       {
         MoveEvent  moveEvent = ( MoveEvent ) o;

         ChatModel  chatModel
           = chatWorld.getChatModel ( moveEvent.getModelId ( ) );

         if ( chatModel == null )
         {
           return;
         }

         PointXY  origin = moveEvent.getOrigin ( );

         if ( origin != null )
         {
           chatModel.setCenter ( origin.getX ( ), origin.getY ( ) );
         }

         chatModel.setDestination ( moveEvent.getDestination ( ) );

         return;
       }

       if ( o instanceof CreateModelEvent )
       {
         ChatModel  chatModel
           = ( ( CreateModelEvent ) o ).getChatModel ( );

         chatModel.setEventConsumer ( eventConsumer );

         chatWorld.addChatModel ( chatModel );         

         return;
       }

       if ( o instanceof RemoveModelEvent )
       {
         ModelId  modelId = ( ( RemoveModelEvent ) o ).getModelId ( );

         ChatModel  chatModel = chatWorld.getChatModel ( modelId );

         chatModel.setActive ( false );

         return;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
