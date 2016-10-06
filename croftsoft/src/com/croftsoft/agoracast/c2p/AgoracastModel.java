     package com.croftsoft.agoracast.c2p;

     import java.awt.Color;
     import java.io.*;
     import java.util.*;

//   import com.croftsoft.core.gui.IdentifierDialog;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Pair;
//   import com.croftsoft.core.text.sml.*;

     /*********************************************************************
     *
     * <p />
     *
     * @version
     *   2001-09-12
     * @since
     *   2001-07-25
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  AgoracastModel
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public String  getEmail     ( );

     public String  getServer    ( );

     public String  getUsername  ( );

     public String  getNewsgroup ( );

     public String  getPassword  ( );

     public AgoracastField  getAgoracastField ( String  name );

     public AgoracastField [ ]  getAgoracastFields ( );

     public String [ ]  getAgoracastFieldNames ( );

//     public String [ ]  getColumnNames ( );

     public String  getDefaultDescription ( );

//   public Pair [ ]  getDefaultPairs ( );

     public Color  getPanelBackgroundColor ( );

     public Color  getTextFieldBackgroundColor ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  add ( AgoracastField  agoracastField );

     public void  setAgoracastFields (
       AgoracastField [ ]  agoracastFields );

     public void  setEmail ( String  email );

     public void  setServer ( String  server );

     public void  setUsername ( String  username );

     public void  setNewsgroup ( String  newsgroup );

     public void  setPassword ( String  password );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }