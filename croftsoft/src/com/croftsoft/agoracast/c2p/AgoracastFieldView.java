     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import java.util.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Pair;
     import com.croftsoft.core.lang.StringLib;

     /*********************************************************************
     * @version
     *   2002-01-29
     * @since
     *   2001-09-13
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastFieldView
       implements ActionListener, ItemListener, KeyListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final ChangeEvent     changeEvent;

     private final ChangeListener  changeListener;

     private final JCheckBox    checkBox;

     private final JLabel       label;

     private final JComponent   component;

     private final JLabel       descriptorLabel;

     private final String       name;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastFieldView ( 
       AgoracastMediator  agoracastMediator,
       AgoracastField     agoracastField,
       boolean            isSelected,
       ChangeListener     changeListener )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( agoracastMediator );

       NullArgumentException.check ( agoracastField );

       NullArgumentException.check (
         this.changeListener = changeListener );

       changeEvent = new ChangeEvent ( this );

       checkBox = new JCheckBox ( );

       name = StringLib.trimToNull ( agoracastField.getName ( ) );

       label = new JLabel ( name );

       String [ ]  choices = agoracastField.getChoices ( );

       if ( ( choices != null )
         && ( choices.length > 0 ) )
       {
         JComboBox  jComboBox = new JComboBox ( choices );

         String  value = agoracastField.getValue ( );

         jComboBox.setSelectedItem ( value );

         jComboBox.addActionListener ( this );

         component = jComboBox;
       }
       else
       {
         JTextField  jTextField = new JTextField ( );

         jTextField.addKeyListener ( this );

         String  value = agoracastField.getValue ( );

         if ( value != null )
         {
           jTextField.setText ( value );
         }

         component = jTextField;

         AgoracastLib.setColor ( jTextField, agoracastMediator );
       }

       String  descriptor = agoracastField.getSemantic ( );

       if ( descriptor == null )
       {
         descriptor = "";
       }

       descriptorLabel = new JLabel ( descriptor );

       setSelected ( isSelected );

       checkBox.addItemListener ( this );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public JCheckBox   getCheckBox        ( ) { return checkBox;        }

     public JLabel      getLabel           ( ) { return label;           }

     public JComponent  getComponent       ( ) { return component;       }

     public JLabel      getDescriptorLabel ( ) { return descriptorLabel; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  isSelected ( )
     //////////////////////////////////////////////////////////////////////
     {
       return checkBox.isSelected ( );
     }

     public Pair  getPair ( )
     //////////////////////////////////////////////////////////////////////
     {
       return new Pair ( label.getText ( ), getValue ( ) );
     }

     public String  getName ( )
     //////////////////////////////////////////////////////////////////////
     {
       return name;
     }

     public String  getValue ( )
     //////////////////////////////////////////////////////////////////////
     {
       String  value = null;

       if ( component instanceof JTextField )
       {
         value = ( ( JTextField ) component ).getText ( );
       }
      
       if ( component instanceof JComboBox )
       {
         value
           = ( String ) ( ( JComboBox ) component ).getSelectedItem ( );
       }

       return StringLib.trimToNull ( value );
     }

     public synchronized void  setSelected ( boolean  selected )
     //////////////////////////////////////////////////////////////////////
     {
       checkBox.setSelected ( selected );

       label.setEnabled     ( selected );

       component.setEnabled ( selected );
     }

     public void  setValue ( String  value )
     //////////////////////////////////////////////////////////////////////
     {
       value = ( value != null ) ? value : "";

       if ( component instanceof JTextField )
       {
         ( ( JTextField ) component ).setText ( value );
       }
       else if ( component instanceof JComboBox )
       {
         ( ( JComboBox ) component ).setSelectedItem ( value );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       changeListener.stateChanged ( changeEvent );
     }

     public synchronized void  itemStateChanged ( ItemEvent  itemEvent )
     //////////////////////////////////////////////////////////////////////
     {
       boolean  selected = checkBox.isSelected ( );

       label.setEnabled     ( selected );

       component.setEnabled ( selected );

       changeListener.stateChanged ( changeEvent );
     }

     public synchronized void  keyTyped ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
       changeListener.stateChanged ( changeEvent );
     }

     public void  keyPressed ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  keyReleased ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
