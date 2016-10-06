     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.io.*;
     import javax.swing.*;

     import com.croftsoft.core.gui.IdentifierDialog;
     import com.croftsoft.core.lang.StringLib;
     import com.croftsoft.core.net.news.NntpConstants;
     import com.croftsoft.core.net.news.NntpLib;
     import com.croftsoft.core.net.news.NntpSocket;
     import com.croftsoft.core.security.Authentication;
     import com.croftsoft.core.security.Identifier;

     /*********************************************************************
     *
     * <p />
     *
     * @version
     *   2001-09-12
     * @since
     *   2001-07-26
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  authenticate (
       NntpSocket         nntpSocket,
       AgoracastMediator  agoracastMediator )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       boolean  isAuthenticated = false;

       while ( !isAuthenticated )
       {
         String  username = agoracastMediator.getUsername ( );

         String  password = agoracastMediator.getPassword ( );

         Authentication  authentication = null;

         if ( ( username != null )
           && !"".equals ( username.trim ( ) )
           && ( password != null )
           && !"".equals ( password.trim ( ) ) )
         {
           authentication = new Authentication ( username, password );
         }
         else
         {
           Frame  parentFrame = agoracastMediator.getParentFrame ( );

           authentication
             = promptForAuthentication ( agoracastMediator, parentFrame );

           if ( authentication != null )
           {
             agoracastMediator.setUsername ( authentication.getUsername ( ) );

             agoracastMediator.setPassword ( authentication.getPassword ( ) );
           }
         }

         if ( authentication != null )
         {
           try
           {
             NntpLib.authenticate (
               nntpSocket,
               authentication.getUsername ( ),
               authentication.getPassword ( ) );

             isAuthenticated = true;
           }
           catch ( SecurityException  ex )
           {
             agoracastMediator.setPassword ( null );
           }
         }
         else
         {
           throw new SecurityException ( "authentication canceled" );
         }
       }
     }

     public static Authentication  promptForAuthentication (
       AgoracastMediator  agoracastMediator,
       Frame              parentFrame )
     //////////////////////////////////////////////////////////////////////
     {
       IdentifierDialog  identifierDialog = new IdentifierDialog (
         parentFrame,
         AgoracastConstants.IDENTIFIER_DIALOG_TITLE,
         agoracastMediator.getUsername ( ),
         agoracastMediator.getPanelBackgroundColor ( ),
         agoracastMediator.getTextFieldBackgroundColor ( ) );

       Authentication  authentication
         = identifierDialog.getAuthentication ( );

       identifierDialog.dispose ( );

       return authentication;
     }

     public static void  setColor (
       JPanel             jPanel,
       AgoracastMediator  agoracastMediator )
     //////////////////////////////////////////////////////////////////////
     {
       Color  panelBackgroundColor
         = agoracastMediator.getPanelBackgroundColor ( );

       if ( panelBackgroundColor != null )
       {
         jPanel.setBackground ( panelBackgroundColor );
       }
     }

     public static void  setColor (
       JTextField         jTextField,
       AgoracastMediator  agoracastMediator )
     //////////////////////////////////////////////////////////////////////
     {
       Color  textFieldBackgroundColor
         = agoracastMediator.getTextFieldBackgroundColor ( );

       if ( textFieldBackgroundColor != null )
       {
         jTextField.setBackground ( textFieldBackgroundColor );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  AgoracastLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }