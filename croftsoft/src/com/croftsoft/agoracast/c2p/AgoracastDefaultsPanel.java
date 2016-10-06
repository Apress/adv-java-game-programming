     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.gui.ButtonPanel2;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.ObjectLib;

     /*********************************************************************
     * @version
     *   2002-01-29
     * @since
     *   2001-09-12
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastDefaultsPanel
       extends JPanel
       implements ActionListener, ChangeListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final AgoracastMediator    agoracastMediator;

     private final AgoracastInputPanel  agoracastInputPanel;

     private final JButton              restoreButton;

     private final JButton              updateButton;

     private       boolean              reactToChanges;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastDefaultsPanel (
       AgoracastMediator  agoracastMediator )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ), true ); // isDoubleBuffered

       NullArgumentException.check (
         this.agoracastMediator = agoracastMediator );

       AgoracastLib.setColor ( this, agoracastMediator );

       reactToChanges = true;

       agoracastInputPanel = new AgoracastInputPanel ( agoracastMediator,
         agoracastMediator.getAgoracastFieldNames ( ),
         ( ChangeListener ) this );

       add ( new JScrollPane ( agoracastInputPanel ),
         BorderLayout.CENTER );

       restoreButton = new JButton ( "Restore" );

       restoreButton.setEnabled ( false );

       restoreButton.addActionListener ( this );

       updateButton = new JButton ( "Update" );

       updateButton.setEnabled ( false );

       updateButton.addActionListener ( this );

       add ( new ButtonPanel2 (
         new JButton [ ] { restoreButton, updateButton } ),
         BorderLayout.SOUTH );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getValue ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       return agoracastInputPanel.getValue ( name );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  stateChanged ( ChangeEvent  changeEvent )
     //////////////////////////////////////////////////////////////////////
     {
       if ( reactToChanges )
       {
         updateButton.setEnabled ( true );

         restoreButton.setEnabled ( true );

         reactToChanges = false;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = actionEvent.getSource ( );

       if ( source == restoreButton )
       {
         restoreButton.setEnabled ( false );

         updateButton.setEnabled ( false );

         agoracastInputPanel.useDefaults ( );

         reactToChanges = true;
       }
       else if ( source == updateButton )
       {
         restoreButton.setEnabled ( false );

         updateButton.setEnabled ( false );

         AgoracastField [ ]  agoracastFields
           = agoracastMediator.getAgoracastFields ( );

         for ( int  i = 0; i < agoracastFields.length; i++ )
         {
           AgoracastField  agoracastField = agoracastFields [ i ];

           String  name = agoracastField.getName ( );

           String  value = agoracastInputPanel.getValue ( name );

           if ( !ObjectLib.equivalent (
                  value, agoracastField.getValue ( ) ) )
           {
             AgoracastField  newAgoracastField = new AgoracastField (
               name,
               value,
               agoracastField.getType     ( ),
               agoracastField.isReverse   ( ),
               agoracastField.getChoices  ( ),
               agoracastField.getSemantic ( ) );

             agoracastMediator.add ( newAgoracastField );
           }
         }

         agoracastInputPanel.useDefaults ( );

         reactToChanges = true;  
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }