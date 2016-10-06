     package com.croftsoft.apps.chat.request;

     import java.io.Serializable;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.security.Authentication;

     /*********************************************************************
     * An abstract user request to the Server.
     *
     * @version
     *   2003-06-20
     * @since
     *   2003-06-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public abstract class  AbstractRequest
       implements Request
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     protected final Authentication  authentication;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AbstractRequest ( Authentication  authentication )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.authentication = authentication );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Authentication  getAuthentication ( )
     //////////////////////////////////////////////////////////////////////
     {
       return authentication;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
