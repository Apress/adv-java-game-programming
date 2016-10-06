     package com.croftsoft.apps.chat.response;

     import java.io.Serializable;

     /*********************************************************************
     * Interface for a Response from the Server.
     *
     * @version
     *   2003-06-17
     * @since
     *   2003-06-17
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  Response
       extends Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public boolean  isDenied ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
