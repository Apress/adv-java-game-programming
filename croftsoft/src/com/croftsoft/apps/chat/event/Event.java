     package com.croftsoft.apps.chat.event;

     import java.io.Serializable;

     /*********************************************************************
     * An interface for events from the Server.
     *
     * @version
     *   2003-06-18
     * @since
     *   2003-06-17
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  Event
       extends Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public long  getEventIndex ( );

     public void  setEventIndex ( long  eventIndex );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
