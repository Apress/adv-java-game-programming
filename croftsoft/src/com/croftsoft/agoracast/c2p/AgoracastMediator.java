     package com.croftsoft.agoracast.c2p;

     import java.awt.Color;
     import java.awt.Frame;
     import java.io.*;
     import javax.swing.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Pair;
     import com.croftsoft.core.lang.StringLib;
     import com.croftsoft.core.lang.lifecycle.Commissionable;
     import com.croftsoft.core.lang.lifecycle.InitializationException;
     import com.croftsoft.core.util.ArrayLib;
     import com.croftsoft.core.util.log.Log;
     import com.croftsoft.core.util.log.PrintStreamLog;

     /*********************************************************************
     *
     * <p />
     *
     * @version
     *   2001-11-09
     * @since
     *   2001-08-01
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastMediator
       implements Commissionable, AgoracastModel
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private Log                  log;

     private AgoracastConfig      agoracastConfig;

     private AgoracastNewsrc      agoracastNewsrc;

     private AgoracastMemory      agoracastMemory;

     private Frame                parentFrame;

     private JTabbedPane          jTabbedPane;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastMediator ( )
     //////////////////////////////////////////////////////////////////////
     {
       log = PrintStreamLog.SYSTEM_ERR_INSTANCE;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         agoracastConfig
           = AgoracastConfig.load ( AgoracastConstants.CONFIG_FILENAME );
       }
       catch ( FileNotFoundException  ex )
       {
       }
       catch ( Exception  ex )
       {
         log.record ( ex );
       }

       if ( agoracastConfig == null )
       {
         agoracastConfig = new AgoracastConfig ( );
       }

       try
       {
         agoracastNewsrc
           = AgoracastNewsrc.load ( AgoracastConstants.NEWSRC_FILENAME );
       }
       catch ( FileNotFoundException  ex )
       {
       }
       catch ( Exception  ex )
       {
         log.record ( ex );
       }

       if ( agoracastNewsrc == null )
       {
         agoracastNewsrc = new AgoracastNewsrc ( );
       }

       try
       {
         agoracastMemory = AgoracastMemory.load (
           AgoracastConstants.DATABASE_FILENAME );
       }
       catch ( FileNotFoundException  ex )
       {
       }
       catch ( Exception  ex )
       {
         log.record ( ex );
       }

       if ( agoracastMemory == null )
       {
         agoracastMemory = new AgoracastMemory ( );
       }
     }

     public synchronized void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       log = PrintStreamLog.SYSTEM_ERR_INSTANCE;
  
       try
       {
         agoracastConfig.saveIfDirty (
           AgoracastConstants.CONFIG_FILENAME );
       }
       catch ( Exception  ex )
       {
         log.record ( ex );
       }

       try
       {
         agoracastNewsrc.saveIfDirty (
           AgoracastConstants.NEWSRC_FILENAME );
       }
       catch ( Exception  ex )
       {
         log.record ( ex );
       }

       try
       {
         agoracastMemory.saveIfDirty (
           AgoracastConstants.DATABASE_FILENAME );
       }
       catch ( Exception  ex )
       {
         log.record ( ex );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Log  getLog ( )
     //////////////////////////////////////////////////////////////////////
     {
       return log;
     }

     public AgoracastDatabase  getAgoracastDatabase ( )
     //////////////////////////////////////////////////////////////////////
     {
       return agoracastMemory;
     }

     public AgoracastNewsrc  getAgoracastNewsrc ( )
     //////////////////////////////////////////////////////////////////////
     {
       return agoracastNewsrc;
     }

     public Frame  getParentFrame ( )
     //////////////////////////////////////////////////////////////////////
     {
       return parentFrame;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  setLog ( Log  log )
     //////////////////////////////////////////////////////////////////////
     {
       this.log = log;
     }

     public synchronized void  setParentFrame ( Frame  parentFrame )
     //////////////////////////////////////////////////////////////////////
     {
       this.parentFrame = parentFrame;
     }

     public synchronized void  setJTabbedPane ( JTabbedPane  jTabbedPane )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.jTabbedPane = jTabbedPane );

       String  server    = StringLib.trimToNull ( getServer    ( ) );

       String  email     = StringLib.trimToNull ( getEmail     ( ) );

       if ( server == null )
       {
         setTabEnabled ( AgoracastConstants.TAB_INDEX_BROWSE  , false );

         setTabEnabled ( AgoracastConstants.TAB_INDEX_DEFAULTS, false );

         setTabEnabled ( AgoracastConstants.TAB_INDEX_POST    , false );
       }
       else if ( email == null )
       {
         setTabEnabled ( AgoracastConstants.TAB_INDEX_DEFAULTS, false );

         setTabEnabled ( AgoracastConstants.TAB_INDEX_POST    , false );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  setTabEnabled ( 
       int      index,
       boolean  enabled )
     //////////////////////////////////////////////////////////////////////
     {
       jTabbedPane.setEnabledAt ( index, enabled );
     }

     //////////////////////////////////////////////////////////////////////
     // interface AgoracastModel accessor methods
     //////////////////////////////////////////////////////////////////////

     public String  getEmail     ( )
     //////////////////////////////////////////////////////////////////////
     {
       return agoracastConfig.getEmail ( );
     }

     public String  getServer    ( )
     //////////////////////////////////////////////////////////////////////
     {
       return agoracastConfig.getServer ( );
     }

     public String  getUsername  ( )
     //////////////////////////////////////////////////////////////////////
     {
       return agoracastConfig.getUsername ( );
     }

     public String  getNewsgroup ( )
     //////////////////////////////////////////////////////////////////////
     {
       String  newsgroup
         = StringLib.trimToNull ( agoracastConfig.getNewsgroup ( ) );

       return newsgroup != null
         ? newsgroup : AgoracastConstants.DEFAULT_NEWSGROUP;
     }

     public String  getPassword  ( )
     //////////////////////////////////////////////////////////////////////
     {
       return agoracastConfig.getPassword ( );
     }

     public AgoracastField  getAgoracastField ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       return agoracastConfig.getAgoracastField ( name );
     }

     public AgoracastField [ ]  getAgoracastFields ( )
     //////////////////////////////////////////////////////////////////////
     {
       return agoracastConfig.getAgoracastFields ( );
     }

     public String [ ]  getAgoracastFieldNames ( )
     //////////////////////////////////////////////////////////////////////
     {
       return agoracastConfig.getAgoracastFieldNames ( );
     }

     public String  getDefaultDescription ( )
     //////////////////////////////////////////////////////////////////////
     {
       return agoracastConfig.getDefaultDescription ( );
     }

     public Color  getPanelBackgroundColor ( )
     //////////////////////////////////////////////////////////////////////
     {
       return agoracastConfig.getPanelBackgroundColor ( );
     }

     public Color  getTextFieldBackgroundColor ( )
     //////////////////////////////////////////////////////////////////////
     {
       return agoracastConfig.getTextFieldBackgroundColor ( );
     }

     //////////////////////////////////////////////////////////////////////
     // interface AgoracastModel accessor methods
     //////////////////////////////////////////////////////////////////////

     public void  add ( AgoracastField  agoracastField )
     //////////////////////////////////////////////////////////////////////
     {
       agoracastConfig.add ( agoracastField );
     }

     public void  setAgoracastFields (
       AgoracastField [ ]  agoracastFields )
     //////////////////////////////////////////////////////////////////////
     {
       agoracastConfig.setAgoracastFields ( agoracastFields );
     }

     public synchronized void  setEmail ( String  email )
     //////////////////////////////////////////////////////////////////////
     {
       email = StringLib.trimToNull ( email );

       agoracastConfig.setEmail ( email );

       setTabEnabled ( AgoracastConstants.TAB_INDEX_DEFAULTS,
         email != null && getServer ( ) != null );

       setTabEnabled ( AgoracastConstants.TAB_INDEX_POST,
         email != null && getServer ( ) != null );
     }

     public synchronized void  setServer ( String  server )
     //////////////////////////////////////////////////////////////////////
     {
       server = StringLib.trimToNull ( server );

       agoracastConfig.setServer ( server );

       setTabEnabled ( AgoracastConstants.TAB_INDEX_BROWSE,
         server != null );

       setTabEnabled ( AgoracastConstants.TAB_INDEX_DEFAULTS,
         server != null && getEmail ( ) != null );

       setTabEnabled ( AgoracastConstants.TAB_INDEX_POST,
         server != null && getEmail ( ) != null );
     }

     public void  setUsername ( String  username )
     //////////////////////////////////////////////////////////////////////
     {
       agoracastConfig.setUsername ( username );
     }

     public void  setNewsgroup ( String  newsgroup )
     //////////////////////////////////////////////////////////////////////
     {
       agoracastConfig.setNewsgroup (
         StringLib.trimToNull ( newsgroup ) );
     }

     public void  setPassword ( String  password )
     //////////////////////////////////////////////////////////////////////
     {
       agoracastConfig.setPassword ( password );
     }

     public AgoracastCategory  getAgoracastCategory ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       if ( name == null )
       {
         return AgoracastConstants.CATEGORY_ALL;
       }

       for ( int  i = 0; i < AgoracastConstants.CATEGORIES.length; i++ )
       {
         AgoracastCategory  agoracastCategory
           = AgoracastConstants.CATEGORIES [ i ];

         if ( agoracastCategory.getName ( ).equals ( name ) )
         {
           return agoracastCategory;
         }
       }
 
       return null;
     }

     //////////////////////////////////////////////////////////////////////
     // Miscellaneous
     //////////////////////////////////////////////////////////////////////

// redo this whole mess, isColumn should not be an attribute of AgoracastField

/*

     public synchronized void  setColumnNames ( String [ ]  columnNames )
     //////////////////////////////////////////////////////////////////////
     {
       AgoracastField [ ]  agoracastFields = getAgoracastFields ( );

       for ( int  i = 0; i < agoracastFields.length; i++ )
       {
         String  fieldName = agoracastFields [ i ].getName ( );

         boolean  isColumn = ArrayLib.contains ( columnNames, fieldName );

         setFieldIsColumn ( fieldName, isColumn );
       }
     }

// redo this whole mess, isColumn should not be an attribute of AgoracastField

     public synchronized void  setFieldIsColumn (
       String   fieldName,
       boolean  isColumn )
     //////////////////////////////////////////////////////////////////////
     {
       AgoracastField  agoracastField = getAgoracastField ( fieldName );

       if ( isColumn != agoracastField.isColumn ( ) )
       {
         AgoracastField  newAgoracastField = new AgoracastField (
           agoracastField.getName     ( ),
           agoracastField.getValue    ( ),
           isColumn,
           agoracastField.getType     ( ),
           agoracastField.isReverse   ( ),
           agoracastField.getChoices  ( ),
           agoracastField.isPosted    ( ),
           agoracastField.getSemantic ( ) );

         add ( newAgoracastField );
       }
     }
*/

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }