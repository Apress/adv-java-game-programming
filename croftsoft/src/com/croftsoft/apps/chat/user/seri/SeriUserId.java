     package com.croftsoft.apps.chat.user.seri;

     import com.croftsoft.core.util.id.LongId;

     import com.croftsoft.apps.chat.user.UserId;

     /*********************************************************************
     * A Serializable UserId.
     *
     * @version
     *   2003-06-07
     * @since
     *   2003-06-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SeriUserId
       extends LongId
       implements UserId
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SeriUserId ( long  l )
     //////////////////////////////////////////////////////////////////////
     {
       super ( l );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
