     package com.croftsoft.core.gui;

     import java.awt.*;
     import java.awt.event.*;
     import java.util.*;
     import javax.swing.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Pair;

     /*********************************************************************
     * A JPanel of JTextFields with identifying JLabels.
     *
     * <p>
     * Created JTextField objects are available via an accessor method.
     * </p>
     *
     * <p>
     * Example:
     * <code>
     * <pre>
     * LabeledFieldsPanel2  labeledFieldsPanel2 = new LabeledFieldsPanel2 (
     *   new String [ ] { "username", "password" } );
     *
     * JTextField  passwordJTextField
     *   = labeledFieldsPanel2.getJTextField ( "password" );
     *
     * passwordJTextField.setEchoChar ( '*' );
     * </pre>
     * </code>
     * </p>
     *
     * @version
     *   2001-07-30
     * @since
     *   2001-07-06
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  LabeledFieldsPanel2
       extends JPanel
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Hashtable  labelNameToTextFieldMap = new Hashtable ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  LabeledFieldsPanel2 (
       Pair [ ]  pairs,
       Color     panelBackgroundColor,
       Color     textFieldBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new GridBagLayout ( ), true );

       NullArgumentException.check ( pairs );

       GridBagConstraints  gridBagConstraints = new GridBagConstraints ( );

       gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

//       gridBagConstraints.ipadx = 10;

//       gridBagConstraints.ipady = 10;

       gridBagConstraints.insets = new Insets ( 10, 10, 10, 10 );

       for ( int  i = 0; i < pairs.length; i++ )
       {
         gridBagConstraints.gridx = 0;

         gridBagConstraints.gridy = i;

         gridBagConstraints.weightx = 0.0;

         String  labelName = pairs [ i ].name;

         add ( new JLabel ( labelName ), gridBagConstraints );

         JTextField  jTextField = new JTextField ( );

         if ( pairs [ i ].value != null )
         {
           jTextField.setText ( pairs [ i ].value );
         }

         labelNameToTextFieldMap.put ( labelName, jTextField );

         gridBagConstraints.gridx = 1;

         gridBagConstraints.weightx = 1.0;

         add ( jTextField, gridBagConstraints );

         if ( textFieldBackgroundColor != null )
         {
           jTextField.setBackground ( textFieldBackgroundColor );
         }
       }

       if ( panelBackgroundColor != null )
       {
         setBackground ( panelBackgroundColor );
       }
     }

     public  LabeledFieldsPanel2 (
       String [ ]  labelNames,
       Color       panelBackgroundColor,
       Color       textFieldBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       this ( Pair.toPairs ( labelNames ),
         panelBackgroundColor, textFieldBackgroundColor );
     }

     public  LabeledFieldsPanel2 (
       String  labelName,
       Color   panelBackgroundColor,
       Color   textFieldBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new String [ ] { labelName },
         panelBackgroundColor, textFieldBackgroundColor );
     }

     public  LabeledFieldsPanel2 ( String [ ]  labelNames )
     //////////////////////////////////////////////////////////////////////
     {
       this ( labelNames, null, null );
     }

     public  LabeledFieldsPanel2 ( String  labelName )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new String [ ] { labelName }, null, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public JTextField  getJTextField ( String  labelName )
     //////////////////////////////////////////////////////////////////////
     {
       JTextField  jTextField
         = ( JTextField ) labelNameToTextFieldMap.get ( labelName );

       if ( jTextField == null )
       {
         throw new IllegalArgumentException ( labelName );
       }

       return jTextField;
     }

     public String  getText ( String  labelName )
     //////////////////////////////////////////////////////////////////////
     {
       return getJTextField ( labelName ).getText ( );        
     }

     public void  setText ( Pair  pair )
     //////////////////////////////////////////////////////////////////////
     {
       getJTextField ( pair.name ).setText (
         pair.value != null ? pair.value : "" );
     }

     public void  setText ( Pair [ ]  pairs )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < pairs.length; i++ )
       {
         setText ( pairs [ i ] );
       }
     }

     /*********************************************************************
     * Adds the KeyListener to all of the JTextFields.
     *********************************************************************/
     public void  addKeyListener ( KeyListener  keyListener )
     //////////////////////////////////////////////////////////////////////
     {
       Iterator  iterator
         = labelNameToTextFieldMap.values ( ).iterator ( );

       while ( iterator.hasNext ( ) )
       {
         JTextField  jTextField = ( JTextField ) iterator.next ( );

         jTextField.addKeyListener ( keyListener );
       }
     }

     /*********************************************************************
     * Removes the KeyListener from all of the JTextFields.
     *********************************************************************/
     public void  removeKeyListener ( KeyListener  keyListener )
     //////////////////////////////////////////////////////////////////////
     {
       Iterator  iterator
         = labelNameToTextFieldMap.values ( ).iterator ( );

       while ( iterator.hasNext ( ) )
       {
         JTextField  jTextField = ( JTextField ) iterator.next ( );

         jTextField.removeKeyListener ( keyListener );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
