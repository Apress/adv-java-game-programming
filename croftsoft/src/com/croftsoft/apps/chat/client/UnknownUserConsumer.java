     package com.croftsoft.apps.chat.client;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.security.Authentication;
     import com.croftsoft.core.util.queue.Queue;

     import com.croftsoft.apps.chat.request.CreateUserRequest;

     /*********************************************************************
     * Processes an UnknownUserResponse.
     *
     * @version
     *   2003-06-10
     * @since
     *   2003-06-10
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  UnknownUserConsumer
       extends AbstractConsumer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Queue              requestQueue;

     private final CreateUserRequest  createUserRequest;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  UnknownUserConsumer (
       Queue           requestQueue,
       Authentication  authentication )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.requestQueue = requestQueue, "null requestQueue" );

       NullArgumentException.check (
         authentication, "null authentication" );

       createUserRequest = new CreateUserRequest ( authentication );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  consume ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
System.out.println ( o );

//       requestQueue.append ( createUserRequest );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
