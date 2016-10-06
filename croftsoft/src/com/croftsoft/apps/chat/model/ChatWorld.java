     package com.croftsoft.apps.chat.model;

     import com.croftsoft.core.animation.model.ModelId;
     import com.croftsoft.core.animation.model.World;

     /*********************************************************************
     * Provides methods for manipulating the Models in the game.
     *
     * @version
     *   2003-06-17
     * @since
     *   2003-06-05
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  ChatWorld
       extends World, ChatWorldAccessor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  addChatModel ( ChatModel  chatModel );

     public ModelId  createModel (
       String  avatarType,
       double  x,
       double  y );

     public ChatModel  getChatModel ( ModelId  modelId );

     public  boolean  removeModel ( ModelId  modelId );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
