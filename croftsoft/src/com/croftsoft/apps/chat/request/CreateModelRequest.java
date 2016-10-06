     package com.croftsoft.apps.chat.request;

     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.core.security.Authentication;

     /*********************************************************************
     * A request to create an avatar.
     *
     * @version
     *   2003-06-20
     * @since
     *   2003-06-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  CreateModelRequest
       extends CoalesceableRequest
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final String  avatarType;

     private final double  x;

     private final double  y;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  CreateModelRequest (
       Authentication  authentication,
       String          avatarType,
       double          x,
       double          y )
     //////////////////////////////////////////////////////////////////////
     {
       super ( authentication );

       NullArgumentException.check ( this.avatarType = avatarType );

       this.x = x;

       this.y = y;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getAvatarType ( ) { return avatarType; }

     public double  getX          ( ) { return x; }

     public double  getY          ( ) { return y; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
