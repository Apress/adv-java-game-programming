     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import javax.swing.*;
     import javax.swing.border.*;

     import com.croftsoft.core.gui.ButtonPanel2;
     import com.croftsoft.core.gui.LabeledFieldsPanel2;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Pair;
     import com.croftsoft.core.lang.StringLib;
     import com.croftsoft.core.net.news.UsenetMessage;

     /*********************************************************************
     * Queries the user for message subject and description.
     *
     * <p />
     *
     * @version
     *   2001-10-12
     * @since
     *   2001-07-25
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastTextPanel
       extends JPanel
       implements ActionListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  SUBJECT_FIELD_NAME = "Subject";

     private final AgoracastMediator    agoracastMediator;

     private final AgoracastPostPanel   agoracastPostPanel;

     private final LabeledFieldsPanel2  labeledFieldsPanel2;

     private final JTextArea            jTextArea;

     private final JButton              cancelButton;

     private final JButton              postButton;

     //

     private UsenetMessage  usenetMessage;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastTextPanel (
       AgoracastMediator   agoracastMediator,
       AgoracastPostPanel  agoracastPostPanel )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ), true ); // isDoubleBuffered

       NullArgumentException.check ( 
         this.agoracastMediator = agoracastMediator );

       NullArgumentException.check (
         this.agoracastPostPanel = agoracastPostPanel );

       AgoracastLib.setColor ( this, agoracastMediator );

       JPanel  centerPanel = new JPanel ( new BorderLayout ( ), true );

       labeledFieldsPanel2 = new LabeledFieldsPanel2 (
         new Pair [ ] { new Pair ( SUBJECT_FIELD_NAME, null ) },
         agoracastMediator.getPanelBackgroundColor ( ),
         agoracastMediator.getTextFieldBackgroundColor ( ) );

       centerPanel.add ( labeledFieldsPanel2, BorderLayout.NORTH );

       centerPanel.add ( new JScrollPane ( jTextArea = new JTextArea ( ) ),
         BorderLayout.CENTER );

       add ( centerPanel, BorderLayout.CENTER );

       jTextArea.setBorder ( new EmptyBorder ( 4, 4, 4, 4 ) );

       cancelButton = new JButton ( "Cancel" );

       cancelButton.addActionListener ( this );

       postButton = new JButton ( "Post" );

       postButton.addActionListener ( this );

       add ( new ButtonPanel2 ( new JButton [ ] {
         cancelButton, postButton } ), BorderLayout.SOUTH );

       add ( new JLabel ( AgoracastConstants.DESCRIBE_TEXT ),
         BorderLayout.NORTH );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  setUsenetMessage (
       UsenetMessage  usenetMessage )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.usenetMessage = usenetMessage );

       String  subject
         = usenetMessage.getHeader ( UsenetMessage.HEADER_SUBJECT );

       if ( subject == null )
       {
         subject = "";
       }

       labeledFieldsPanel2.setText (
         new Pair ( SUBJECT_FIELD_NAME, subject ) );

       String  defaultDescription = StringLib.trimToNull (
         agoracastMediator.getDefaultDescription ( ) );

       if ( defaultDescription != null )
       {
         jTextArea.setText ( defaultDescription );
       }
       else
       {
         jTextArea.setText ( "" );
       }
     }

     public synchronized void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = actionEvent.getSource ( );

       if ( source == cancelButton )
       {
         agoracastPostPanel.cancel ( );
       }
       else if ( source == postButton )
       {
         String  subject
           = labeledFieldsPanel2.getText ( SUBJECT_FIELD_NAME );

         if ( ( subject != null )
           && !"".equals ( subject.trim ( ) ) )
         {
           subject = AgoracastConstants.SUBJECT_PREFIX + " " + subject;         
         }
         else
         {
           subject = AgoracastConstants.SUBJECT_PREFIX;
         }

         usenetMessage.setHeader ( UsenetMessage.HEADER_SUBJECT, subject );

         StringBuffer  stringBuffer
           = new StringBuffer ( usenetMessage.getBody ( ) );

         String  text = StringLib.trimToNull ( jTextArea.getText ( ) );

         if ( text != null )
         {
           stringBuffer.append ( "\r\n" );

           stringBuffer.append ( text );

           stringBuffer.append ( "\r\n" );
         }

         stringBuffer.append ( "\r\n" );

         stringBuffer.append ( AgoracastConstants.FOOTER );

         usenetMessage.setBody ( stringBuffer.toString ( ) );

         agoracastPostPanel.confirm ( usenetMessage );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }