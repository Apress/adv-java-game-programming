     package com.croftsoft.agoracast.c2p;

     import java.awt.Color;
     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.gui.IdentifierDialog;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.StringLib;
     import com.croftsoft.core.lang.Pair;
     import com.croftsoft.core.text.sml.*;
     import com.croftsoft.core.util.ArrayLib;

     /*********************************************************************
     *
     * <p />
     *
     * @version
     *   2001-11-09
     * @since
     *   2001-07-25
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastConfig
       implements AgoracastConstants, AgoracastModel
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  SML_NODE_NAME = "agoracast-config";

     //

     private String     email;

     private String     server;

     private String     username;

     private String     newsgroup;

     private SortedMap  nameToFieldMap;

     //

     private String   password;

     private boolean  isDirty;

     //

     private IdentifierDialog   identifierDialog;

     private Color              panelBackgroundColor;

     private Color              textFieldBackgroundColor;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static AgoracastConfig  load ( String  filename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return fromSmlNode ( SmlNodeLib.load ( filename, false ) );
     }

     public static AgoracastConfig  fromSmlNode ( SmlNode  smlNode )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !SML_NODE_NAME.equals ( smlNode.getName ( ) ) )
       {
         throw new IllegalArgumentException ( smlNode.getName ( ) );
       }

       String  email     = smlNode.getString ( "email"    );

       String  server    = smlNode.getString ( "server"   );

       String  username  = smlNode.getString ( "username" );

       String  newsgroup = smlNode.getString ( "newsgroup" );

       List  defaultPairList = new ArrayList ( );

       AgoracastField [ ]  agoracastFields = null;

       SmlNode [ ]  fieldNodes
         = smlNode.getChildNodes ( AgoracastField.SML_NODE_NAME );

       if ( fieldNodes != null
         && fieldNodes.length > 0 )
       {
         List  fieldList = new ArrayList ( fieldNodes.length );
         
         for ( int  i = 0; i < fieldNodes.length; i++ )
         {
           SmlNode  fieldNode = fieldNodes [ i ];

           fieldList.add ( AgoracastField.fromSmlNode ( fieldNodes [ i ] ) );
         }

         agoracastFields = ( AgoracastField [ ] )
           fieldList.toArray ( new AgoracastField [ ] { } );
       }

       return new AgoracastConfig (
         email, server, username, newsgroup, agoracastFields, null, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastConfig (
       String              email,
       String              server,
       String              username,
       String              newsgroup,
       AgoracastField [ ]  agoracastFields,
       Color               panelBackgroundColor,
       Color               textFieldBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       this.email     = email;

       this.server    = server;

       this.username  = username;

       this.newsgroup = newsgroup;

       setAgoracastFields ( agoracastFields );

       this.panelBackgroundColor     = panelBackgroundColor;

       this.textFieldBackgroundColor = textFieldBackgroundColor;

       if ( panelBackgroundColor == null )
       {
         this.panelBackgroundColor
           = AgoracastConstants.DEFAULT_PANEL_BACKGROUND_COLOR;
       }

       if ( textFieldBackgroundColor == null )
       {
         this.textFieldBackgroundColor
           = AgoracastConstants.DEFAULT_TEXTFIELD_BACKGROUND_COLOR;
       }

       isDirty = false;
     }

     public  AgoracastConfig ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null, null, null, null, null, null, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getEmail     ( ) { return email;     }

     public String  getServer    ( ) { return server;    }

     public String  getUsername  ( ) { return username;  }

     public String  getNewsgroup ( ) { return newsgroup; }

     public String  getPassword  ( ) { return password;  }

     public AgoracastField  getAgoracastField ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       return ( AgoracastField ) nameToFieldMap.get ( name );
     }

     public AgoracastField [ ]  getAgoracastFields ( )
     //////////////////////////////////////////////////////////////////////
     {
       return ( AgoracastField [ ] )
         nameToFieldMap.values ( ).toArray ( new AgoracastField [ ] { } );
     }

     public String [ ]  getAgoracastFieldNames ( )
     //////////////////////////////////////////////////////////////////////
     {
       return ( String [ ] )
         nameToFieldMap.keySet ( ).toArray ( new String [ ] { } );
     }

     public String  getDefaultDescription ( )
     //////////////////////////////////////////////////////////////////////
     {
       return null;
     }

     public Color  getPanelBackgroundColor ( )
     //////////////////////////////////////////////////////////////////////
     {
       return panelBackgroundColor;
     }

     public Color  getTextFieldBackgroundColor ( )
     //////////////////////////////////////////////////////////////////////
     {
       return textFieldBackgroundColor;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  add ( AgoracastField  agoracastField )
     //////////////////////////////////////////////////////////////////////
     {
       isDirty = true;

       nameToFieldMap.put ( agoracastField.getName ( ), agoracastField );
     }

     public synchronized void  setAgoracastFields (
       AgoracastField [ ]  agoracastFields )
     //////////////////////////////////////////////////////////////////////
     {
       isDirty = true;

       nameToFieldMap = new TreeMap ( );

       for ( int  i = 0;
         i < AgoracastConstants.DEFAULT_FIELDS.length; i++ )
       {
         AgoracastField  field = AgoracastConstants.DEFAULT_FIELDS [ i ];

         nameToFieldMap.put ( field.getName ( ), field );
       }

       if ( agoracastFields != null )
       {
         for ( int  i = 0; i < agoracastFields.length; i++ )
         {
           AgoracastField  agoracastField = agoracastFields [ i ];

           String  name = agoracastField.getName ( );

           AgoracastField  defaultAgoracastField
             = ( AgoracastField ) nameToFieldMap.get ( name );

           if ( defaultAgoracastField != null )
           {
             String [ ]  choices = ( String [ ] ) ArrayLib.union (
               agoracastField.getChoices ( ),
               defaultAgoracastField.getChoices ( ) );

             agoracastField = new AgoracastField (
               agoracastField.getName     ( ),
               agoracastField.getValue    ( ),
               agoracastField.getType     ( ),
               agoracastField.isReverse   ( ),
               choices,
               agoracastField.getSemantic ( ) );
           }

           nameToFieldMap.put ( name, agoracastField );
         }
       }
     }

     public synchronized void  setEmail ( String  email )
     //////////////////////////////////////////////////////////////////////
     {
       isDirty = true;

       this.email = StringLib.trimToNull ( email );
     }

     public synchronized void  setServer ( String  server   )
     //////////////////////////////////////////////////////////////////////
     {
       isDirty = true;

       this.server = StringLib.trimToNull ( server );
     }

     public synchronized void  setUsername ( String  username )
     //////////////////////////////////////////////////////////////////////
     {
       isDirty = true;

       this.username = StringLib.trimToNull ( username );
     }

     public synchronized void  setNewsgroup ( String  newsgroup )
     //////////////////////////////////////////////////////////////////////
     {
       isDirty = true;

       this.newsgroup = StringLib.trimToNull ( newsgroup );
     }

     public synchronized void  setPassword ( String  password )
     //////////////////////////////////////////////////////////////////////
     {
       isDirty = true;

       this.password = StringLib.trimToNull ( password );
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

       if ( email != null )
       {
         rootSmlNode.add ( new SmlNode ( "email", email ) );
       }

       if ( server != null )
       {
         rootSmlNode.add ( new SmlNode ( "server", server ) );
       }

       if ( username != null )
       {
         rootSmlNode.add ( new SmlNode ( "username", username ) );
       }

       if ( newsgroup != null )
       {
         rootSmlNode.add ( new SmlNode ( "newsgroup", newsgroup ) );
       }

       AgoracastField [ ]  agoracastFields = getAgoracastFields ( );

       if ( agoracastFields != null )
       {
         for ( int  i = 0; i < agoracastFields.length; i++ )
         {
           rootSmlNode.add ( agoracastFields [ i ].toSmlNode ( ) );
         }
       }

       return rootSmlNode;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }