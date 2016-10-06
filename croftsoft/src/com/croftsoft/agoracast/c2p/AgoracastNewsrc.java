     package com.croftsoft.agoracast.c2p;

     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.io.SerializableLib;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.StringLib;
     import com.croftsoft.core.net.news.UsenetMessage;
     import com.croftsoft.core.text.sml.*;

     /*********************************************************************
     *
     * <p />
     *
     * @version
     *   2001-09-12
     * @since
     *   2001-07-31
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastNewsrc
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Map  serverToGroupToLastMap;

     //

     private transient boolean  isDirty;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static AgoracastNewsrc  load ( String  filename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return fromSmlNode ( SmlNodeLib.load ( filename, false ) );
     }

     public static AgoracastNewsrc  fromSmlNode ( SmlNode  smlNode )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !"agoracast-newsrc".equals ( smlNode.getName ( ) ) )
       {
         throw new IllegalArgumentException ( "agoracast-newsrc" );
       }

       Map  serverToGroupToLastMap = new HashMap ( );

       SmlNode  serversSmlNode = smlNode.getChildNode ( "servers" );

       if ( serversSmlNode != null )
       {
         SmlNode [ ]  serverNodes
           = serversSmlNode.getChildNodes ( "server" );

         for ( int  i = 0; i < serverNodes.length; i++ )
         {
           SmlNode  serverNode = serverNodes [ i ];

           String  serverName = StringLib.trimToNull (
             serverNode.getString ( "name" ) );

           if ( serverName == null )
           {
             continue;
           }

           SmlNode  groupsSmlNode = serverNode.getChildNode ( "groups" );

           if ( groupsSmlNode == null )
           {
             continue;
           }

           SmlNode [ ]  groupNodes
             = groupsSmlNode.getChildNodes ( "group" );

           for ( int  j = 0; j < groupNodes.length; j++ )
           {
             SmlNode  groupNode = groupNodes [ j ];

             String  groupName = StringLib.trimToNull (
               groupNode.getString ( "name" ) );

             if ( groupName == null )
             {
               continue;
             }

             long  last = groupNode.getLong ( "last", -1 );

             if ( last > -1 )
             {
               Map  groupToLastMap
                 = ( Map ) serverToGroupToLastMap.get ( serverName );

               if ( groupToLastMap == null )
               {
                 groupToLastMap = new HashMap ( );

                 serverToGroupToLastMap.put (
                   serverName, groupToLastMap );
               }

               groupToLastMap.put ( groupName, new Long ( last ) );
             }               
           }                          
         }
       }

       return new AgoracastNewsrc ( serverToGroupToLastMap );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastNewsrc (
       Map  serverToGroupToLastMap )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.serverToGroupToLastMap = serverToGroupToLastMap );
     }

     public  AgoracastNewsrc ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new HashMap ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized long  getLastArticleNumber (
       String  server,
       String  newsgroupName )
     //////////////////////////////////////////////////////////////////////
     {
       Map  nameToLastMap
         = ( Map ) serverToGroupToLastMap.get ( server );

       if ( nameToLastMap == null )
       {
         return -1;
       }

       Long  last = ( Long ) nameToLastMap.get ( newsgroupName );

       if ( last == null )
       {
         return -1;
       }

       return last.longValue ( );
     }

     public synchronized void  setLastArticleNumber (
       String  server,
       String  group,
       long    lastArticleNumber )
     //////////////////////////////////////////////////////////////////////
     {
       Long  last = new Long ( lastArticleNumber );

       Map  nameToLastMap = ( Map ) serverToGroupToLastMap.get ( server );

       if ( nameToLastMap == null )
       {
         nameToLastMap = new HashMap ( );

         serverToGroupToLastMap.put ( server, nameToLastMap );
       }

       nameToLastMap.put ( group, last );

       isDirty = true;
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
       SmlNode  rootSmlNode = new SmlNode ( "agoracast-newsrc" );

       SmlNode  serversSmlNode = new SmlNode ( "servers" );

       rootSmlNode.add ( serversSmlNode );

       Iterator  iterator = serverToGroupToLastMap.keySet ( ).iterator ( );

       while ( iterator.hasNext ( ) )
       {
         SmlNode  serverSmlNode = new SmlNode ( "server" );

         serversSmlNode.add ( serverSmlNode );

         String  serverName = ( String ) iterator.next ( );

         serverSmlNode.add ( new SmlNode ( "name", serverName ) );

         Map  groupToLastMap
           = ( Map ) serverToGroupToLastMap.get ( serverName );

         if ( groupToLastMap == null )
         {
           continue;
         }

         SmlNode  groupsSmlNode = new SmlNode ( "groups" );

         serverSmlNode.add ( groupsSmlNode );

         Iterator  groupIterator = groupToLastMap.keySet ( ).iterator ( );

         while ( groupIterator.hasNext ( ) )
         {
           SmlNode  groupSmlNode = new SmlNode ( "group" );

           groupsSmlNode.add ( groupSmlNode );

           String  groupName = ( String ) groupIterator.next ( );

           groupSmlNode.add ( new SmlNode ( "name", groupName ) );

           Long  last = ( Long ) groupToLastMap.get ( groupName );

           if ( last == null )
           {
             continue;
           }

           groupSmlNode.add ( new SmlNode ( "last", last.toString ( ) ) );
         }         
       }

       return rootSmlNode;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }