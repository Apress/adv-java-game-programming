     package com.croftsoft.core.gui;

     import java.awt.*;
     import java.awt.event.*;
     import javax.swing.*;
     import javax.swing.border.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Displays a license agreement and an accept/decline button choice.
     *
     * @version
     *   2002-01-27
     * @since
     *   2001-07-20
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  LicensePanel
       extends JPanel
       implements ActionListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final JButton  acceptJButton;

     private final JButton  declineJButton;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *
     * @param  licenseAgreementText
     *
     *    Must not be null.
     *
     * @param  acceptActionListener
     *
     *    Called when the Accept button is clicked.  Must not be null.
     *
     * @param  declineActionListener
     *
     *    If null, the Decline button will simply trigger System.exit(0).
     *
     * @param  panelBackgroundColor
     *
     *    May be null.
     *
     * @param  splashImage
     *
     *    May be null.
     *********************************************************************/
     public  LicensePanel (
       String          licenseAgreementText,
       ActionListener  acceptActionListener,
       ActionListener  declineActionListener,
       Color           panelBackgroundColor,
       Image           splashImage )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ), true ); // isDoubleBuffered

       NullArgumentException.check ( licenseAgreementText );

       NullArgumentException.check ( acceptActionListener );

       if ( panelBackgroundColor != null )
       {
         setBackground ( panelBackgroundColor );
       }

       if ( splashImage != null )
       {
         add ( new JLabel ( new ImageIcon ( splashImage ) ), BorderLayout.NORTH );
       }

       JTextArea  jTextArea = new JTextArea ( );

       jTextArea.setEditable ( false );

       jTextArea.setLineWrap ( true );

       jTextArea.setWrapStyleWord ( true );

// How can I set the border to 2 characters wide?

       jTextArea.setBorder ( new EmptyBorder ( 4, 4, 4, 4 ) );

       jTextArea.setText ( licenseAgreementText );

       add ( new JScrollPane ( jTextArea ), BorderLayout.CENTER );

       acceptJButton = new JButton ( "Accept" );

       acceptJButton.addActionListener ( acceptActionListener );

       declineJButton = new JButton ( "Decline" );

       if ( declineActionListener == null )
       {
         declineActionListener = this;
       }

       declineJButton.addActionListener ( declineActionListener );

       JButton [ ]  jButtons
         = new JButton [ ] { declineJButton, acceptJButton };

       add ( new ButtonPanel2 ( jButtons ), BorderLayout.SOUTH );
     }

     /*********************************************************************
     * Convenience constructor.
     *********************************************************************/
     public  LicensePanel (
       String          licenseAgreementText,
       ActionListener  acceptActionListener,
       Color           panelBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         licenseAgreementText,
         acceptActionListener,
         ( ActionListener ) null,
         panelBackgroundColor,
         ( Image ) null );
     }

     /*********************************************************************
     * Convenience constructor.
     *********************************************************************/
     public  LicensePanel (
       String          licenseAgreementText,
       ActionListener  acceptActionListener )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         licenseAgreementText,
         acceptActionListener,
         ( Color ) null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public JButton  getAcceptJButton  ( ) { return acceptJButton;  }

     public JButton  getDeclineJButton ( ) { return declineJButton; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       if ( actionEvent.getSource ( ) == declineJButton )
       {
         System.exit ( 0 );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }