     package com.croftsoft.apps.chat.user.seri;

     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.security.Authentication;

     import com.croftsoft.apps.chat.user.User;
     import com.croftsoft.apps.chat.user.UserId;
     import com.croftsoft.apps.chat.user.UserStore;

     /*********************************************************************
     * Serializable UserStore implementation.
     *
     * @version
     *   2003-06-11
     * @since
     *   2003-06-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SeriUserStore
       implements Serializable, UserStore
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final Map     userIdToUserMap;

     private final Map     usernameToUserIdMap;

     private final Random  random;

     //

     private UserId [ ]  userIds;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SeriUserStore ( )
     //////////////////////////////////////////////////////////////////////
     {
       userIdToUserMap     = new HashMap ( );

       usernameToUserIdMap = new HashMap ( );

       random              = new Random ( );

       userIds             = new UserId [ 0 ];
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public User  getUser ( Authentication  authentication )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( authentication );

       UserId  userId = getUserId ( authentication.getUsername ( ) );

       if ( userId == null )
       {
         return null;
       }

       User  user = getUser ( userId );

       if ( user == null )
       {
         return null;
       }

       if ( !user.getPassword ( ).equals (
         authentication.getPassword ( ) ) )
       {
         return null;
       }

       return user;
     }

     public User  getUser ( UserId  userId )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( userId );

       return ( User ) userIdToUserMap.get ( userId );
     }

     public UserId  getUserId ( String  username )
     //////////////////////////////////////////////////////////////////////
     {
       return ( UserId ) usernameToUserIdMap.get ( username );
     }

     public UserId [ ]  getUserIds ( )
     //////////////////////////////////////////////////////////////////////
     {
       return userIds;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized UserId  createUser (
       Authentication  authentication )
     //////////////////////////////////////////////////////////////////////
     {
       String  username = authentication.getUsername ( );

       String  password = authentication.getPassword ( );

       NullArgumentException.check ( username );

       if ( usernameToUserIdMap.containsKey ( username ) )
       {
         throw new IllegalArgumentException ( );
       }

       NullArgumentException.check ( password );

       UserId  userId = new SeriUserId ( random.nextLong ( ) );

       User  user = new SeriUser ( userId, username, password );

       userIdToUserMap.put ( userId, user );

       usernameToUserIdMap.put ( username, userId );

       userIds = ( UserId [ ] )
         userIdToUserMap.keySet ( ).toArray ( new UserId [ 0 ] );

       return userId;
     }

     public synchronized boolean  removeUser ( UserId  userId )
     //////////////////////////////////////////////////////////////////////
     {
       User  user = getUser ( userId );

       if ( user == null )
       {
         return false;
       }

       usernameToUserIdMap.remove ( user.getUsername ( ) );

       userIdToUserMap.remove ( userId );

       userIds = ( UserId [ ] )
         userIdToUserMap.keySet ( ).toArray ( new UserId [ 0 ] );

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
