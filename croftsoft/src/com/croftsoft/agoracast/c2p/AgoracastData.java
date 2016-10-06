     package com.croftsoft.agoracast.c2p;

     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Pair;
     import com.croftsoft.core.lang.StringLib;
     import com.croftsoft.core.text.sml.*;

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

     public final class  AgoracastData
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  SML_NODE_NAME = "data";

     //

     private final String    newsgroup;

     private final String    articleId;

     private final Map       map;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static AgoracastData  fromSmlNode ( SmlNode  smlNode )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !SML_NODE_NAME.equals ( smlNode.getName ( ) ) )
       {
         throw new IllegalArgumentException ( smlNode.getName ( ) );
       }

       String  newsgroup = smlNode.getString  ( "newsgroup" );

       String  articleId = smlNode.getString ( "articleId" );

       Pair [ ]  pairs = null;

       SmlNode [ ]  fieldNodes = smlNode.getChildNodes ( "field" );

       if ( fieldNodes != null )
       {
         List  pairList = new ArrayList ( );

         for ( int  i = 0; i < fieldNodes.length; i++ )
         {
           SmlNode  fieldNode = fieldNodes [ i ];

           String  name
             = StringLib.trimToNull ( fieldNode.getString ( "name" ) );

           if ( name != null )
           {
             String  value = StringLib.trimToNull (
               fieldNode.getString ( "value" ) );

             pairList.add ( new Pair ( name, value ) );
           }
         }

         pairs = ( Pair [ ] ) pairList.toArray ( new Pair [ ] { } );
       }

       return new AgoracastData ( newsgroup, articleId, pairs );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastData (
       String    newsgroup,
       String    articleId,
       Pair [ ]  pairs )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.newsgroup = newsgroup );

       NullArgumentException.check ( this.articleId = articleId );

       NullArgumentException.check ( pairs );

       if ( pairs.length < 1 )
       {
         throw new IllegalArgumentException ( "pairs.length < 1" );
       }

       map = new HashMap ( );

       for ( int  i = 0; i < pairs.length; i++ )
       {
         Pair  pair = pairs [ i ];

         map.put ( pair.name, pair.value );
       }
     }
      
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getNewsgroup ( ) { return newsgroup; }

     public String  getArticleId ( ) { return articleId; }

     public String  getValue ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       return ( String ) map.get ( name );
     }

     public synchronized SmlNode  toSmlNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       SmlNode  rootSmlNode = new SmlNode ( SML_NODE_NAME );

       rootSmlNode.add ( new SmlNode ( "newsgroup", newsgroup ) );

       rootSmlNode.add ( new SmlNode ( "articleId", articleId ) );

       if ( map != null )
       {
         Iterator  iterator = map.keySet ( ).iterator ( );

         while ( iterator.hasNext ( ) )
         {
           SmlNode  fieldSmlNode = new SmlNode ( "field" );

           rootSmlNode.add ( fieldSmlNode );

           String  name = ( String ) iterator.next ( );
 
           fieldSmlNode.add ( new SmlNode ( "name", name ) );

           String  value = ( String ) map.get ( name );

           if ( value != null )
           {
             fieldSmlNode.add ( new SmlNode ( "value", value ) );
           }
         }
       }

       return rootSmlNode;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }