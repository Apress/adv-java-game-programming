     package com.croftsoft.apps.chat.request;

     import com.croftsoft.core.security.Authentication;

     /*********************************************************************
     * A request to destroy an avatar.
     *
     * @version
     *   2003-06-20
     * @since
     *   2003-06-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  KillRequest
       extends CoalesceableRequest
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  KillRequest ( Authentication  authentication )
     //////////////////////////////////////////////////////////////////////
     {
       super ( authentication );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
