     package com.croftsoft.agoracast.c2p;

     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.io.SerializableLib;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.text.sml.*;
//   import com.croftsoft.core.util.event.EventBroadcaster;
//   import com.croftsoft.core.util.event.Event;
//   import com.croftsoft.core.util.event.EventListener;
     import com.croftsoft.core.util.SoftHashMap;

     /*********************************************************************
     *
     * <p />
     *
     * @version
     *   2001-08-09
     * @since
     *   2001-08-06
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastMemory
       implements AgoracastDatabase
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  SML_NODE_NAME = "database";

//   private final EventBroadcaster  eventBroadcaster;

     //

     private boolean  isDirty;

     private Map      idToDataMap;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static AgoracastMemory  load ( String  filename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return fromSmlNode ( SmlNodeLib.load ( filename, false ) );
     }

     public static AgoracastMemory  fromSmlNode ( SmlNode  smlNode )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !SML_NODE_NAME.equals ( smlNode.getName ( ) ) )
       {
         throw new IllegalArgumentException ( smlNode.getName ( ) );
       }

       Map  idToDataMap = new SoftHashMap ( );

       SmlNode [ ]  dataNodes
         = smlNode.getChildNodes ( AgoracastData.SML_NODE_NAME );

       if ( dataNodes != null )
       {
         for ( int  i = 0; i < dataNodes.length; i++ )
         {
           AgoracastData  agoracastData
             = AgoracastData.fromSmlNode ( dataNodes [ i ] );

           idToDataMap.put (
             agoracastData.getArticleId ( ), agoracastData );
         }
       }

       return new AgoracastMemory ( idToDataMap );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastMemory ( Map  idToDataMap )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.idToDataMap = idToDataMap );

//     eventBroadcaster = new EventBroadcaster ( );
     }

     public  AgoracastMemory ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new SoftHashMap ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

/*
     public boolean  addEventListener ( EventListener  eventListener )
     //////////////////////////////////////////////////////////////////////
     {
       return eventBroadcaster.addEventListener ( eventListener );
     }
*/

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized boolean  add ( AgoracastData  agoracastData )
     //////////////////////////////////////////////////////////////////////
     {
       String  articleId = agoracastData.getArticleId ( );

       idToDataMap.put ( articleId, agoracastData );

       isDirty = true;

//     eventBroadcaster.broadcast (
//       new AgoracastEvent ( AgoracastEvent.TYPE_ADD, articleId ) );

       return true;
     }

     public AgoracastData  getAgoracastData ( String  articleId )
     //////////////////////////////////////////////////////////////////////
     {
       return ( AgoracastData ) idToDataMap.get ( articleId );
     }

     public synchronized AgoracastData [ ]  getAgoracastDatas ( )
     //////////////////////////////////////////////////////////////////////
     {
       return ( AgoracastData [ ] )
         idToDataMap.values ( ).toArray ( new AgoracastData [ ] { } );
     }

     public synchronized AgoracastData [ ]  
       getAgoracastDatasForCategory ( String  category )
     //////////////////////////////////////////////////////////////////////
     {
       Iterator  iterator = idToDataMap.values ( ).iterator ( );

       List  agoracastDataList = new ArrayList ( );

       while ( iterator.hasNext ( ) )
       {
         AgoracastData  agoracastData
           = ( AgoracastData ) iterator.next ( );

         if ( category.equals ( agoracastData.getValue (
           AgoracastConstants.FIELD_NAME_CATEGORY ) ) )
         {
           agoracastDataList.add ( agoracastData );
         }
       }

       return ( AgoracastData [ ] )
         agoracastDataList.toArray ( new AgoracastData [ ] { } );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized boolean  saveIfDirty ( String  filename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       if ( isDirty )
       {
         SmlNodeLib.save ( filename, toSmlNode ( ) );

         isDirty = false;

         return true;
       }

       return false;
     }

     public synchronized SmlNode  toSmlNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       SmlNode  rootSmlNode = new SmlNode ( SML_NODE_NAME );

       if ( idToDataMap != null )
       {
         Iterator  iterator = idToDataMap.values ( ).iterator ( );

         while ( iterator.hasNext ( ) )
         {
           AgoracastData  agoracastData
             = ( AgoracastData ) iterator.next ( );

           rootSmlNode.add ( agoracastData.toSmlNode ( ) );
         }
       }

       return rootSmlNode;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }