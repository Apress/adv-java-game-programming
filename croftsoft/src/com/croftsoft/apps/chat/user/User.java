     package com.croftsoft.apps.chat.user;

     import com.croftsoft.core.util.queue.Queue;

     import com.croftsoft.core.animation.model.ModelId;

     /*********************************************************************
     * User data.
     *
     * @version
     *   2003-06-18
     * @since
     *   2003-06-05
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  User
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public long     getEventIndex      ( );

     public long     getLastRequestTime ( );

     public Queue    getMessageQueue    ( );

     public ModelId  getModelId         ( );

     public String   getPassword        ( );

     public Queue    getRequestQueue    ( );

     public UserId   getUserId          ( );

     public String   getUsername        ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public long  nextEventIndex ( );

     public void  setModelId  ( ModelId  modelId  );

     public void  setPassword ( String   password );

     public void  updateLastRequestTime ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
