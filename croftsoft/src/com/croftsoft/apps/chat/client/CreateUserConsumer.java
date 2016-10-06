     package com.croftsoft.apps.chat.client;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.security.Authentication;
     import com.croftsoft.core.util.queue.Queue;

     import com.croftsoft.apps.chat.request.CreateModelRequest;
     import com.croftsoft.apps.chat.request.ViewRequest;
     import com.croftsoft.apps.chat.response.CreateUserResponse;

     /*********************************************************************
     * Processes a CreateUserResponse.
     *
     * @version
     *   2003-06-20
     * @since
     *   2003-06-11
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  CreateUserConsumer
       extends AbstractConsumer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Queue               requestQueue;

     private final CreateModelRequest  createModelRequest;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  CreateUserConsumer (
       Queue           requestQueue,
       Authentication  authentication,
       String          avatarType,
       double          x,
       double          y )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.requestQueue = requestQueue );

       NullArgumentException.check ( authentication );

       createModelRequest = new CreateModelRequest (
         authentication,
         avatarType,
         x,
         y );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  consume ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       CreateUserResponse  createUserResponse = ( CreateUserResponse ) o;

       if ( createUserResponse.isDenied ( ) )
       {
         throw new RuntimeException ( "CreateUserRequest denied" );
       }
       else
       {
         requestQueue.replace ( createModelRequest );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
