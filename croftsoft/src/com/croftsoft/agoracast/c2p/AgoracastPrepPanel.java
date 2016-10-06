     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import java.util.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.gui.ButtonPanel2;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Pair;
     import com.croftsoft.core.lang.StringLib;
     import com.croftsoft.core.net.news.UsenetMessage;

     /*********************************************************************
     *
     * <p />
     *
     * @version
     *   2002-02-03
     * @since
     *   2001-07-25
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastPrepPanel
       extends JPanel
       implements ActionListener, ChangeListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final AgoracastMediator    agoracastMediator;

     private final AgoracastPostPanel   agoracastPostPanel;

     //

     private final JComboBox            jComboBox;

     private final JLabel               categoryDescriptionLabel;

     private final JButton              defaultsButton;

     private final JButton              fieldsButton;

     private final JButton              postButton;

     //

     private String               categoryName;

     private AgoracastInputPanel  agoracastInputPanel;

     private JScrollPane          inputScrollPane;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastPrepPanel (
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

       JPanel  northPanel = new JPanel ( new GridBagLayout ( ), true );

       GridBagConstraints  gridBagConstraints = new GridBagConstraints ( );

       gridBagConstraints.weightx = 0.0;

       northPanel.add ( new JLabel ( "Category:  " ), gridBagConstraints );

       gridBagConstraints.insets = new Insets ( 0, 8, 0, 0 );

       gridBagConstraints.gridx = 1;

       gridBagConstraints.weightx = 0.0;

       northPanel.add ( jComboBox
         = new JComboBox ( AgoracastConstants.CHOICES_CATEGORY ),
         gridBagConstraints );

       gridBagConstraints.gridx = 2;

       gridBagConstraints.weightx = 1.0;

       gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

       northPanel.add (
         categoryDescriptionLabel = new JLabel ( ),
         gridBagConstraints );

       categoryDescriptionLabel.setHorizontalAlignment ( SwingConstants.LEFT );

       add ( northPanel, BorderLayout.NORTH );

       jComboBox.addActionListener ( this );

       defaultsButton = new JButton ( "Defaults" );

       defaultsButton.addActionListener ( this );

       fieldsButton = new JButton ( "Add Fields" );

       fieldsButton.addActionListener ( this );

       postButton = new JButton ( "Post" );

       postButton.setEnabled ( false );

       postButton.addActionListener ( this );

       add ( new ButtonPanel2 ( new JButton [ ] {
         defaultsButton, fieldsButton, postButton } ),
         BorderLayout.SOUTH );

       String  categoryName = ( String ) jComboBox.getSelectedItem ( );

       setAgoracastCategory (
         agoracastMediator.getAgoracastCategory ( categoryName ) );  
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  setSelectedFieldNames (
       String [ ]  selectedFieldNames )
     //////////////////////////////////////////////////////////////////////
     {
       if ( inputScrollPane != null )
       {
         remove ( inputScrollPane );
       }

       java.util.List  pairList = null;

       if ( agoracastInputPanel != null )
       {
         pairList = new ArrayList ( );

         for ( int  i = 0; i < selectedFieldNames.length; i++ )
         {
           String  name = selectedFieldNames [ i ];

           String  value = agoracastInputPanel.getValue ( name );

           if ( value != null )
           {
             pairList.add ( new Pair ( name, value ) );
           }
         }
       }

       agoracastInputPanel = new AgoracastInputPanel (
         agoracastMediator, selectedFieldNames,
         ( ChangeListener ) this );

       if ( pairList != null )
       {
         Iterator  iterator = pairList.iterator ( );

         while ( iterator.hasNext ( ) )
         {
           Pair  pair = ( Pair ) iterator.next ( );

           agoracastInputPanel.setValue ( pair.name, pair.value );
         }
       }

       inputScrollPane = new JScrollPane ( agoracastInputPanel );

       add ( inputScrollPane, BorderLayout.CENTER );

       agoracastInputPanel.setSelectedAll ( true );

       postButton.setEnabled ( agoracastInputPanel.hasSelectedField ( ) );

       validate ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  stateChanged ( ChangeEvent  changeEvent )
     //////////////////////////////////////////////////////////////////////
     {
       postButton.setEnabled (
         agoracastInputPanel.hasSelectedField ( ) );
     }

     public synchronized void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = actionEvent.getSource ( );

       if ( source == jComboBox )
       {
         String  categoryName = ( String ) jComboBox.getSelectedItem ( );

         final AgoracastCategory  agoracastCategory
           = agoracastMediator.getAgoracastCategory ( categoryName );

         setAgoracastCategory ( agoracastCategory );
       }
       else if ( source == defaultsButton )
       {
         agoracastInputPanel.useDefaults ( );

         postButton.setEnabled (
           agoracastInputPanel.hasSelectedField ( ) );
       }
       else if ( source == fieldsButton )
       {
         agoracastPostPanel.addFields (
           agoracastInputPanel.getSelectedNames ( ) );
       }
       else if ( source == postButton )
       {
         post ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private synchronized void  setAgoracastCategory (
       AgoracastCategory  agoracastCategory )
     //////////////////////////////////////////////////////////////////////
     {
       categoryName = agoracastCategory.getName ( );

       String  description = agoracastCategory.getDescription ( );

       categoryDescriptionLabel.setText (
         description != null ? description : "" );

       setSelectedFieldNames ( agoracastCategory.getFieldNames ( ) );
     }

     private void  post ( )
     //////////////////////////////////////////////////////////////////////
     {
       int  maxLength = AgoracastConstants.FIELD_NAME_CATEGORY.length ( );

       String [ ]  names = agoracastInputPanel.getSelectedNames ( );

       Arrays.sort ( names );

       for ( int  i = 0; i < names.length; i++ )
       {
         String  name = names [ i ];

         if ( name.length ( ) > maxLength )
         {
           maxLength = name.length ( );
         }
       }

       StringBuffer  stringBuffer = new StringBuffer ( );

       writeFieldPair ( stringBuffer, maxLength,
         AgoracastConstants.FIELD_NAME_CATEGORY, categoryName );

       for ( int  i = 0; i < names.length; i++ )
       {
         String  name  = names [ i ];

         String  value = agoracastInputPanel.getValue ( name );

         writeFieldPair ( stringBuffer, maxLength, name, value );
       }

// Need to make sure no null values are being passed

       UsenetMessage  usenetMessage = new UsenetMessage (
         agoracastMediator.getEmail ( ),
         agoracastMediator.getNewsgroup ( ),
         categoryName, // subject
         stringBuffer.toString ( ) );

       usenetMessage.setHeader (
         UsenetMessage.HEADER_FOLLOWUP_TO, "poster" );

       agoracastPostPanel.describe ( usenetMessage );
     }

     private static void  writeFieldPair (
       StringBuffer  stringBuffer,
       int           maxLength,
       String        name,
       String        value )
     //////////////////////////////////////////////////////////////////////
     {
       if ( value != null )
       {
         stringBuffer.append ( StringLib.padRight (
           name + " ", '.', maxLength + 4 ) );

         stringBuffer.append ( ":  " );

         stringBuffer.append ( value );

         stringBuffer.append ( "\r\n" );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
