     package com.croftsoft.apps.chat.response;

     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.apps.chat.model.seri.SeriChatWorld;

     /*********************************************************************
     * Response to a ViewRequest.
     *
     * @version
     *   2003-06-18
     * @since
     *   2003-06-11
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ViewResponse
       extends AbstractResponse
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final SeriChatWorld  seriChatWorld;

     private final long           eventIndex;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ViewResponse (
       SeriChatWorld  seriChatWorld,
       long           eventIndex )
     //////////////////////////////////////////////////////////////////////
     {
       super ( false );

       NullArgumentException.check ( this.seriChatWorld = seriChatWorld );

       this.eventIndex = eventIndex;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public SeriChatWorld  getSeriChatWorld ( ) { return seriChatWorld; }

     public long           getEventIndex    ( ) { return eventIndex;    }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
