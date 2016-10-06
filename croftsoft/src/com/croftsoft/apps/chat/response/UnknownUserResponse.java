     package com.croftsoft.apps.chat.response;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Indicates that the request was denied because of an unknown User.
     *
     * @version
     *   2003-06-10
     * @since
     *   2003-06-10
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  UnknownUserResponse
       extends AbstractResponse
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final Object  request;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  UnknownUserResponse ( Object  request )
     //////////////////////////////////////////////////////////////////////
     {
       super ( true );

       NullArgumentException.check ( this.request = request );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Object  getRequest ( ) { return request; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
