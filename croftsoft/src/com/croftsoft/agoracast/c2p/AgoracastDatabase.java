     package com.croftsoft.agoracast.c2p;

//   import com.croftsoft.core.util.event.EventObserver;

     /*********************************************************************
     *
     * <p />
     *
     * @version
     *   2001-11-20
     * @since
     *   2001-08-01
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  AgoracastDatabase
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public boolean  add ( AgoracastData  agoracastData );

     public AgoracastData  getAgoracastData ( String  articleId );

     public AgoracastData [ ]  getAgoracastDatas ( );

     public AgoracastData [ ]  
       getAgoracastDatasForCategory ( String  category );

//   public boolean  addEventListener ( EventListener  eventListener );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }