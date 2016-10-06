     package com.croftsoft.apps.chat.request;

     import java.io.Serializable;

     import com.croftsoft.core.security.Authentication;

     /*********************************************************************
     * An interface for user requests to the Server.
     *
     * @version
     *   2003-06-10
     * @since
     *   2003-06-10
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  Request
       extends Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public Authentication  getAuthentication ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
