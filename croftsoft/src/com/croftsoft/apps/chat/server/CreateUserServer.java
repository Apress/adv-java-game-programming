     package com.croftsoft.apps.chat.server;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.security.Authentication;

     import com.croftsoft.apps.chat.request.CreateUserRequest;
     import com.croftsoft.apps.chat.response.CreateUserResponse;
     import com.croftsoft.apps.chat.user.User;
     import com.croftsoft.apps.chat.user.UserId;
     import com.croftsoft.apps.chat.user.UserStore;

     /*********************************************************************
     * Creates a User.
     *
     * @version
     *   2003-06-11
     * @since
     *   2003-06-11
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  CreateUserServer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final UserStore  userStore;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  CreateUserServer ( UserStore  userStore )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.userStore = userStore );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Object  serve ( CreateUserRequest  createUserRequest )
     //////////////////////////////////////////////////////////////////////
     {
       Authentication  authentication
         = createUserRequest.getAuthentication ( );

       try
       {
         UserId  userId = userStore.createUser ( authentication );

         return new CreateUserResponse ( userId );
       }
       catch ( IllegalArgumentException  ex )
       {
         return new CreateUserResponse ( true, null, true );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
