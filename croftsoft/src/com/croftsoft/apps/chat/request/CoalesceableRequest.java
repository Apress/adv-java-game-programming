     package com.croftsoft.apps.chat.request;

     import com.croftsoft.core.security.Authentication;

     /*********************************************************************
     * A request that can be coalesced.
     *
     * @version
     *   2003-06-20
     * @since
     *   2003-06-20
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public abstract class  CoalesceableRequest
       extends AbstractRequest
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public  CoalesceableRequest ( Authentication  authentication )
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

       return this.getClass ( ) == other.getClass ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
