     package com.croftsoft.apps.chat.response;

     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.apps.chat.user.UserId;

     /*********************************************************************
     * Response to a GetUserIdRequest.
     *
     * @version
     *   2003-06-07
     * @since
     *   2003-06-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  GetUserIdResponse
       extends AbstractResponse
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final UserId   userId;

     private final boolean  usernameBad;

     private final boolean  passwordBad;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  GetUserIdResponse (
       boolean  denied,
       UserId   userId,
       boolean  usernameBad,
       boolean  passwordBad )
     //////////////////////////////////////////////////////////////////////
     {
       super ( denied );

       this.userId      = userId;

       this.usernameBad = usernameBad;

       this.passwordBad = passwordBad;
     }

     public  GetUserIdResponse ( UserId  userId )
     //////////////////////////////////////////////////////////////////////
     {
       this ( false, userId, false, false );

       NullArgumentException.check ( userId );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public UserId   getUserId     ( ) { return userId;      }

     public boolean  isUsernameBad ( ) { return usernameBad; }

     public boolean  isPasswordBad ( ) { return passwordBad; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
