     package com.croftsoft.core.gui;

     import java.awt.*;
     import java.awt.event.*;
     import javax.swing.*;

     import com.croftsoft.core.lang.StringLib;
     import com.croftsoft.core.security.Authentication;
     import com.croftsoft.core.security.Identifier;

     /*********************************************************************
     * Prompts the user for a username/password pair.
     *
     * <p />
     *
     * @version
     *   2001-08-16
     * @since
     *   2001-07-30
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  IdentifierDialog
       extends JDialog
       implements ActionListener, Identifier, KeyListener, Runnable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final int  FIELD_WIDTH = 10;

     //

     private JTextField      usernameField;

     private JPasswordField  passwordField;

     private final JButton   okButton;

     private boolean         isOk;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       JFrame  jFrame = new JFrame ( "frame" );

       WindowLib.centerOnScreen ( jFrame, 0.8 );

       jFrame.setVisible ( true );

       IdentifierDialog  identifierDialog = new IdentifierDialog (
         jFrame,
         "title",
         "",
         null,
         null );

       System.out.println ( identifierDialog.getAuthentication ( ) );

       System.exit ( 0 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  IdentifierDialog (
       Frame   frame,
       String  title,
       String  username,
       Color   panelBackgroundColor,
       Color   textFieldBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       super ( frame, title, true ); // modal

       if ( panelBackgroundColor != null )
       {
         setBackground ( panelBackgroundColor );
       }

       Container  contentPane = getContentPane ( );

       contentPane.setLayout ( new GridBagLayout ( ) );

       GridBagConstraints  gridBagConstraints = new GridBagConstraints ( );

       gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

//     gridBagConstraints.ipadx = 10;

//     gridBagConstraints.ipady = 10;

       gridBagConstraints.insets = new Insets ( 10, 10, 10, 10 );

       //

       if ( StringLib.trimToNull ( username ) == null )
       {
         addUsernameField ( username,
           contentPane, gridBagConstraints, textFieldBackgroundColor );

         addPasswordField (
           contentPane, gridBagConstraints, textFieldBackgroundColor );
       }
       else
       {
         addPasswordField (
           contentPane, gridBagConstraints, textFieldBackgroundColor );

         addUsernameField ( username,
           contentPane, gridBagConstraints, textFieldBackgroundColor );
       }

       //

       gridBagConstraints.gridx = 0;

       gridBagConstraints.gridy = 2;

       gridBagConstraints.weightx = 0.0;

       JButton  cancelButton = new JButton ( "Cancel" );

       cancelButton.addActionListener ( this );

       contentPane.add ( cancelButton, gridBagConstraints );

       gridBagConstraints.gridx = 1;

       gridBagConstraints.gridy = 2;

       okButton = new JButton ( "OK" );

       okButton.addActionListener ( this );

       contentPane.add ( okButton, gridBagConstraints );

       //

       pack ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Authentication  getAuthentication ( )
     //////////////////////////////////////////////////////////////////////
     {
       WindowLib.centerAboveParent ( this );

       okButton.setEnabled ( false );

       isOk = false;
       
       passwordField.setText ( "" );

       setVisible ( true );

       if ( !isOk )
       {
         return null;
       }

       String  username = usernameField.getText ( );

       char [ ]  chars = passwordField.getPassword ( );

       String  password = new String ( chars );

       passwordField.setText ( "" );

       for ( int  i = 0; i < chars.length; i++ )
       {
         chars [ i ] = 0;
       }

       return new Authentication ( username, password );
     }

     public void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = actionEvent.getSource ( );

       if ( source == okButton )
       {
         isOk = true;
       }

       SwingUtilities.invokeLater ( this );
     }

     public void  run ( )
     //////////////////////////////////////////////////////////////////////
     {
       setVisible ( false );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  keyPressed ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  keyReleased ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public synchronized void  keyTyped ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
       SwingUtilities.invokeLater (
         new Runnable ( )
         {
           public void  run ( ) { checkFields ( ); }
         } );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private void  addUsernameField (
       String              username,
       Container           contentPane,
       GridBagConstraints  gridBagConstraints,
       Color               textFieldBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       gridBagConstraints.gridx   = 0;

       gridBagConstraints.gridy   = 0;

       gridBagConstraints.weightx = 0.0;

       gridBagConstraints.fill    = GridBagConstraints.NONE;

       contentPane.add ( new JLabel ( "Username" ), gridBagConstraints );

       usernameField = new JTextField ( FIELD_WIDTH );

       usernameField.addKeyListener ( this );

       if ( username != null )
       {
         usernameField.setText ( username );
       }

       gridBagConstraints.gridx   = 1;

       gridBagConstraints.weightx = 1.0;

       gridBagConstraints.fill    = GridBagConstraints.HORIZONTAL;

       if ( textFieldBackgroundColor != null )
       {
         usernameField.setBackground ( textFieldBackgroundColor );
       }

       contentPane.add ( usernameField, gridBagConstraints );
     }

     private void  addPasswordField (
       Container           contentPane,
       GridBagConstraints  gridBagConstraints,
       Color               textFieldBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       gridBagConstraints.gridx   = 0;

       gridBagConstraints.gridy   = 1;

       gridBagConstraints.weightx = 0.0;

       gridBagConstraints.fill    = GridBagConstraints.NONE;

       contentPane.add ( new JLabel ( "Password" ), gridBagConstraints );

       passwordField = new JPasswordField ( FIELD_WIDTH );

       passwordField.addKeyListener ( this );

       gridBagConstraints.gridx   = 1;

       gridBagConstraints.weightx = 1.0;

       gridBagConstraints.fill    = GridBagConstraints.HORIZONTAL;

       if ( textFieldBackgroundColor != null )
       {
         passwordField.setBackground ( textFieldBackgroundColor );
       }

       contentPane.add ( passwordField, gridBagConstraints );
     }

     private synchronized void  checkFields ( )
     //////////////////////////////////////////////////////////////////////
     {
       String  username = usernameField.getText ( );

       char [ ]  password = passwordField.getPassword ( );

       okButton.setEnabled (
            ( username != null )
         && !"".equals ( username.trim ( ) )
         && ( password != null )
         && ( password.length > 0 ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }