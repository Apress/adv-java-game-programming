     package com.croftsoft.apps.chat.response;

     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.apps.chat.user.UserId;

     /*********************************************************************
     * Response to a CreateUserRequest.
     *
     * @version
     *   2003-06-07
     * @since
     *   2003-06-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  CreateUserResponse
       extends AbstractResponse
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final UserId   userId;

     private final boolean  usernameBad;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  CreateUserResponse (
       boolean  denied,
       UserId   userId,
       boolean  usernameBad )
     //////////////////////////////////////////////////////////////////////
     {
       super ( denied );

       this.userId      = userId;

       this.usernameBad = usernameBad;
     }

     public  CreateUserResponse ( UserId  userId )
     //////////////////////////////////////////////////////////////////////
     {
       this ( false, userId, false );

       NullArgumentException.check ( userId );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public UserId   getUserId     ( ) { return userId;      }

     public boolean  isUsernameBad ( ) { return usernameBad; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
