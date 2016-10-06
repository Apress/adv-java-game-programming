     package com.croftsoft.apps.chat.server;

     import com.croftsoft.apps.chat.request.Request;
     import com.croftsoft.apps.chat.user.User;

     /*********************************************************************
     * Processes a User request.
     *
     * @version
     *   2003-06-10
     * @since
     *   2003-06-10
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  RequestServer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public Object  serve (
       User     user,
       Request  request );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
