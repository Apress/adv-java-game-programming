     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import javax.swing.*;
     import javax.swing.border.*;

     import com.croftsoft.core.gui.ButtonPanel2;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.net.news.UsenetMessage;

     /*********************************************************************
     * @version
     *   2001-08-13
     * @since
     *   2001-07-29
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastConfirmPanel
       extends JPanel
       implements ActionListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final AgoracastPostPanel  agoracastPostPanel;

     private final JTextArea  jTextArea;

     private final JButton    postButton;

     private final JButton    cancelButton;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *
     * @param  panelBackgroundColor
     *
     *    May be null.
     *********************************************************************/
     public  AgoracastConfirmPanel (
       AgoracastMediator   agoracastMediator,
       AgoracastPostPanel  agoracastPostPanel )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ), true ); // isDoubleBuffered

       NullArgumentException.check ( agoracastMediator );

       NullArgumentException.check (
         this.agoracastPostPanel = agoracastPostPanel );

       AgoracastLib.setColor ( this, agoracastMediator );

       jTextArea = new JTextArea ( );

       jTextArea.setEditable ( false );

// How do we know what font size is appropriate?

       jTextArea.setFont ( AgoracastConstants.LOG_FONT );

// How can I set the border to 2 characters wide?

       jTextArea.setBorder ( new EmptyBorder ( 4, 4, 4, 4 ) );

       add ( new JScrollPane ( jTextArea ), BorderLayout.CENTER );

       postButton   = new JButton ( "Confirm" );

       postButton.addActionListener ( this );

       cancelButton = new JButton ( "Cancel" );

       cancelButton.addActionListener ( this );

       add (
         new ButtonPanel2 (
           new JButton [ ] { cancelButton, postButton },
           agoracastMediator.getPanelBackgroundColor ( ) ),
         BorderLayout.SOUTH );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setText ( String  text )
     //////////////////////////////////////////////////////////////////////
     {
       jTextArea.setText ( text );
     }

     public void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = actionEvent.getSource ( );

       if ( source == postButton )
       {
         agoracastPostPanel.post ( );
       }
       else if ( source == cancelButton )
       {
         agoracastPostPanel.cancel ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }