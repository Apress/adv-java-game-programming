     package com.croftsoft.apps.chat.event;

     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.apps.chat.model.ChatModel;

     /*********************************************************************
     * A model was created in the World.
     *
     * @version
     *   2003-06-07
     * @since
     *   2003-06-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  CreateModelEvent
       extends AbstractEvent
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final ChatModel  chatModel;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  CreateModelEvent ( ChatModel  chatModel )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.chatModel = chatModel );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public ChatModel  getChatModel ( ) { return chatModel; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
