     package com.croftsoft.apps.chat.request;

     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.core.security.Authentication;

     /*********************************************************************
     * A request to get a view of the world.
     *
     * @version
     *   2003-06-20
     * @since
     *   2003-06-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ViewRequest
       extends CoalesceableRequest
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ViewRequest ( Authentication  authentication )
     //////////////////////////////////////////////////////////////////////
     {
       super ( authentication );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  equals ( Object  other )
     //////////////////////////////////////////////////////////////////////
     {
       if ( other == null )
       {
         return false;
       }

       if ( other.getClass ( ) != ViewRequest.class )
       {
         return false;
       }

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
