     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import java.util.*;
     import javax.swing.*;

     import com.croftsoft.core.gui.ButtonPanel2;
     import com.croftsoft.core.gui.CheckBoxPanel;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Pair;
     import com.croftsoft.core.lang.StringLib;
     import com.croftsoft.core.util.log.Log;
     import com.croftsoft.core.util.pubsub.Subscriber;

     /*********************************************************************
     * Allows the user to select/deselect fields from a list.
     *
     * <p />
     *
     * @version
     *   2002-01-29
     * @since
     *   2001-10-11
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastFieldsPanel
       extends JPanel
       implements ActionListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final Object  DONE_BUTTON_EVENT = new Object ( );

     //

     private final AgoracastMediator   agoracastMediator;

     private final Subscriber          eventSubscriber;

     private final CheckBoxPanel       checkBoxPanel;

     private final JButton             doneButton;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastFieldsPanel (
       AgoracastMediator  agoracastMediator,
       Subscriber         eventSubscriber )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ), true ); // isDoubleBuffered

       NullArgumentException.check (
         this.agoracastMediator = agoracastMediator );

       NullArgumentException.check (
         this.eventSubscriber = eventSubscriber );

       AgoracastLib.setColor ( this, agoracastMediator );

       AgoracastField [ ]  agoracastFields
         = agoracastMediator.getAgoracastFields ( );

       String [ ]  fieldNames  = new String [ agoracastFields.length ];

       String [ ]  descriptors = new String [ agoracastFields.length ];

       for ( int  i = 0; i < agoracastFields.length; i++ )
       {
         fieldNames [ i ]  = agoracastFields [ i ].getName ( );

         descriptors [ i ] = agoracastFields [ i ].getSemantic ( );
       }

       checkBoxPanel = new CheckBoxPanel ( fieldNames, descriptors,
         agoracastMediator.getPanelBackgroundColor ( ) );

       add ( new JScrollPane ( checkBoxPanel ), BorderLayout.CENTER );

       doneButton = new JButton ( "Done" );

       doneButton.addActionListener ( this );

       add ( doneButton, BorderLayout.SOUTH );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized String [ ]  getFieldNames ( )
     //////////////////////////////////////////////////////////////////////
     {
       return checkBoxPanel.getLabelNames ( );
     }

     public synchronized String [ ]  getSelectedFieldNames ( )
     //////////////////////////////////////////////////////////////////////
     {
       return checkBoxPanel.getSelectedLabelNames ( );
     }

     public synchronized boolean  isSelected ( String  fieldName )
     //////////////////////////////////////////////////////////////////////
     {
       return checkBoxPanel.isSelected ( fieldName );
     }

     public synchronized void  setSelected (
       String   fieldName,
       boolean  selected )
     //////////////////////////////////////////////////////////////////////
     {
       checkBoxPanel.setSelected ( fieldName, selected );
     }

     public synchronized void  setSelectedFieldNames (
       String [ ]  selectedFieldNames )
     //////////////////////////////////////////////////////////////////////
     {
       checkBoxPanel.setAllSelected ( false );

       for ( int  i = 0; i < selectedFieldNames.length; i++ )
       {
         checkBoxPanel.setSelected ( selectedFieldNames [ i ], true );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         Object  source = actionEvent.getSource ( );

         if ( source == doneButton )
         {
           eventSubscriber.receive ( DONE_BUTTON_EVENT );
         }
       }
       catch ( Exception  ex )
       {
         agoracastMediator.getLog ( ).record ( ex );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }