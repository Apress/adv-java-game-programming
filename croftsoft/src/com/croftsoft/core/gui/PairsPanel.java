     package com.croftsoft.core.gui;

     import java.awt.*;
     import java.awt.event.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.gui.LabeledFieldsPanel2;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Pair;

     /*********************************************************************
     * A JPanel for entering and editing name-value pairs.
     *
     * <p>
     * Includes "Restore" and "Update" buttons and an optional panel
     * for displaying help in HTML.
     * </p>
     *
     * <p>
     * Useful for manipulating user configuration properties.
     * </p>
     *
     * @version
     *   2002-01-29
     * @since
     *   2001-07-25
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  PairsPanel
       extends JPanel
       implements ActionListener, KeyListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private       Pair [ ]             nameValuePairs;

     private final ChangeListener       changeListener;
 
     private final ChangeEvent          changeEvent;

     private final boolean              trimWhiteSpace;

     private final LabeledFieldsPanel2  labeledFieldsPanel2;

     private final JButton              restoreButton;

     private final JButton              updateButton;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  PairsPanel (
       Pair [ ]        nameValuePairs,
       String          helpText,
       ChangeListener  changeListener,
       boolean         trimWhiteSpace,
       Color           panelBackgroundColor,
       Color           textFieldBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ), true ); // isDoubleBuffered

       NullArgumentException.check (
         this.nameValuePairs = nameValuePairs );

       this.changeListener = changeListener;

       this.trimWhiteSpace = trimWhiteSpace;

       if ( panelBackgroundColor != null )
       {
         setBackground ( panelBackgroundColor );
       }

       changeEvent = new ChangeEvent ( this );

       JPanel  centerPanel = new JPanel ( new BorderLayout ( ), true );

       labeledFieldsPanel2
         = new LabeledFieldsPanel2 (
         nameValuePairs,
         panelBackgroundColor,
         textFieldBackgroundColor );

       labeledFieldsPanel2.addKeyListener ( this );

       if ( helpText != null )
       {
         centerPanel.add (
           new JScrollPane ( labeledFieldsPanel2 ), BorderLayout.WEST );

         centerPanel.add ( new JScrollPane ( GuiCreator.createHtmlPane (
           helpText, null ) ), BorderLayout.CENTER );

         add ( centerPanel, BorderLayout.CENTER );
       }
       else
       {
         add (
           new JScrollPane ( labeledFieldsPanel2 ), BorderLayout.CENTER );
       }

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

     public  PairsPanel (
       Pair [ ]        nameValuePairs,
       ChangeListener  changeListener )
     //////////////////////////////////////////////////////////////////////
     {
       this ( nameValuePairs, null, changeListener, true, null, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getText ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       return labeledFieldsPanel2.getText ( name );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  setText ( Pair  pair )
     //////////////////////////////////////////////////////////////////////
     {
       labeledFieldsPanel2.setText ( pair );
     }

     public synchronized void  setText ( Pair [ ]  nameValuePairs )
     //////////////////////////////////////////////////////////////////////
     {
       labeledFieldsPanel2.setText ( nameValuePairs );
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
       updateButton.setEnabled ( true );

       restoreButton.setEnabled ( true );

       labeledFieldsPanel2.removeKeyListener ( this );  
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

         for ( int  i = 0; i < nameValuePairs.length; i++ )
         {
           labeledFieldsPanel2.setText ( nameValuePairs [ i ] );
         }

         labeledFieldsPanel2.addKeyListener ( this );
       }
       else if ( source == updateButton )
       {
         restoreButton.setEnabled ( false );

         updateButton.setEnabled ( false );

         for ( int  i = 0; i < nameValuePairs.length; i++ )
         {
           Pair  pair = nameValuePairs [ i ];

           String  value = labeledFieldsPanel2.getText ( pair.name );

           if ( trimWhiteSpace )
           {
             value = value.trim ( );
           }

           if ( "".equals ( value ) )
           {
             value = null;
           }

           nameValuePairs [ i ] = new Pair ( pair.name, value );

           setText ( nameValuePairs [ i ] );
         }

         labeledFieldsPanel2.addKeyListener ( this );

         if ( changeListener != null )
         {
           changeListener.stateChanged ( changeEvent );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }