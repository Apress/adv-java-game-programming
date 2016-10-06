     package com.croftsoft.apps.chat.model.seri;

     import java.util.*;

     import com.croftsoft.core.animation.model.Model;
     import com.croftsoft.core.animation.model.ModelId;
     import com.croftsoft.core.animation.model.seri.SeriModelId;
     import com.croftsoft.core.animation.model.seri.SeriWorld;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.role.Consumer;
     import com.croftsoft.core.role.Server;

     import com.croftsoft.apps.chat.event.CreateModelEvent;
     import com.croftsoft.apps.chat.event.RemoveModelEvent;
     import com.croftsoft.apps.chat.model.ChatModel;
     import com.croftsoft.apps.chat.model.ChatWorld;

     /*********************************************************************
     * A Serializable ChatWorld implementation.
     *
     * @version
     *   2003-06-17
     * @since
     *   2003-06-05
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SeriChatWorld
       extends SeriWorld
       implements ChatWorld
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private transient Consumer  eventConsumer;

     private transient Random    random;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SeriChatWorld ( Consumer  eventConsumer )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.eventConsumer = eventConsumer );

       random = new Random ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  addChatModel ( ChatModel  chatModel )
     //////////////////////////////////////////////////////////////////////
     {
       modelArrayKeeper.insert ( chatModel );

       eventConsumer.consume ( new CreateModelEvent ( chatModel ) );
     }

     public ModelId  createModel (
       String  avatarType,
       double  x,
       double  y )
     //////////////////////////////////////////////////////////////////////
     {
       ModelId  modelId = new SeriModelId ( random.nextLong ( ) );

       addChatModel ( new SeriChatModel (
         modelId, eventConsumer, this, avatarType, x, y ) );

       return modelId;
     }

     public ChatModel  getChatModel ( ModelId  modelId )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( modelId );

       Model [ ]  models = getModels ( );

       for ( int  i = 0; i < models.length; i++ )
       {
         Model  model = models [ i ];

         if ( model.getModelId ( ).equals ( modelId ) )
         {
           return ( ChatModel ) model;
         }
       }

       return null;
     }

     public  boolean  removeModel ( ModelId  modelId )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( modelId );

       ChatModel  chatModel = getChatModel ( modelId );

       if ( chatModel == null )
       {
         return false;
       }

       remove ( chatModel );

       eventConsumer.consume ( new RemoveModelEvent ( modelId ) );

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
