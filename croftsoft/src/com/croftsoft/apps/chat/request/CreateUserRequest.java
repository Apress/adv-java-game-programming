     package com.croftsoft.apps.chat.request;

     import com.croftsoft.core.security.Authentication;

     /*********************************************************************
     * A request to create a User.
     *
     * @version
     *   2003-06-20
     * @since
     *   2003-06-10
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  CreateUserRequest
       extends CoalesceableRequest
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  CreateUserRequest ( Authentication  authentication )
     //////////////////////////////////////////////////////////////////////
     {
       super ( authentication );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
