     package com.croftsoft.apps.chat.user;

     import com.croftsoft.core.security.Authentication;

     /*********************************************************************
     * Stores User objects.
     *
     * @version
     *   2003-06-07
     * @since
     *   2003-06-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  UserStore
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public User        getUser    ( Authentication  authentication );

     public User        getUser    ( UserId  userId   );

     public UserId      getUserId  ( String  username );

     public UserId [ ]  getUserIds ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public UserId   createUser ( Authentication  authentication );

     public boolean  removeUser ( UserId  userId );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
