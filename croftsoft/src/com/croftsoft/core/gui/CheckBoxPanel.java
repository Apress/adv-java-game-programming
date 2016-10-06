     package com.croftsoft.core.gui;

     import java.awt.*;
     import java.awt.event.*;
     import java.util.*;
     import javax.swing.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * A JPanel of JCheckBoxes with identifying JLabels.
     *
     * <p>
     * Example:
     * <code>
     * <pre>
     * CheckBoxPanel  checkBoxPanel = new CheckBoxPanel (
     *   new String [ ] { "From", "To", "Date", "Subject" } );
     * </pre>
     * </code>
     * </p>
     *
     * @version
     *   2001-10-12
     * @since
     *   2001-08-08
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  CheckBoxPanel
       extends JPanel
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Map  labelNameToCheckBoxMap = new HashMap ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  CheckBoxPanel (
       String [ ]  labelNames,
       String [ ]  descriptors,
       Color       panelBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new GridBagLayout ( ), true );

       NullArgumentException.check ( labelNames );

       if ( panelBackgroundColor != null )
       {
         setBackground ( panelBackgroundColor );
       }

       if ( descriptors != null )
       {
         if ( descriptors.length != labelNames.length )
         {
           throw new IllegalArgumentException (
             "descriptors.length != labelNames.length" );
         }
       }

       GridBagConstraints  gridBagConstraints = new GridBagConstraints ( );

       gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

//     gridBagConstraints.ipadx = 10;

//     gridBagConstraints.ipady = 10;

       Insets  checkBoxInsets = new Insets (  0, 10,  0,  0 );

       Insets  labelInsets    = new Insets (  0,  0,  0, 10 );

       for ( int  i = 0; i < labelNames.length; i++ )
       {
         gridBagConstraints.gridx = 0;

         gridBagConstraints.gridy = i;

         gridBagConstraints.weightx = 0.0;

         gridBagConstraints.insets = checkBoxInsets;

         JCheckBox  jCheckBox = new JCheckBox ( );

         add ( jCheckBox, gridBagConstraints );

         gridBagConstraints.gridx = 1;

         if ( descriptors == null )
         {
           gridBagConstraints.weightx = 1.0;
         }

         gridBagConstraints.insets = labelInsets;

         String  labelName = labelNames [ i ];

         labelNameToCheckBoxMap.put ( labelName, jCheckBox );

         add ( new JLabel ( labelName ), gridBagConstraints );

         if ( descriptors != null )
         {
           gridBagConstraints.gridx = 2;

           gridBagConstraints.weightx = 1.0;

           add ( new JLabel ( descriptors [ i ] ), gridBagConstraints );
         }        

/*
Is this necessary? If so, modify in LabeledFieldsPanel2 as well.

         if ( panelBackgroundColor != null )
         {
           labelName.setBackground ( panelBackgroundColor );
         }
*/
       }
     }

     public  CheckBoxPanel ( String [ ]  labelNames )
     //////////////////////////////////////////////////////////////////////
     {
       this ( labelNames, null, null );
     }

     //////////////////////////////////////////////////////////////////////
     // accessors
     //////////////////////////////////////////////////////////////////////

     public String [ ]  getLabelNames ( )
     //////////////////////////////////////////////////////////////////////
     {
       return ( String [ ] )
         labelNameToCheckBoxMap.keySet ( ).toArray ( new String [ ] { } );
     }

     public String [ ]  getSelectedLabelNames ( )
     //////////////////////////////////////////////////////////////////////
     {
       java.util.List  selectedLabelNamesList = new ArrayList ( );

       Iterator  iterator = labelNameToCheckBoxMap.keySet ( ).iterator ( );

       while ( iterator.hasNext ( ) )
       {
         String  labelName = ( String ) iterator.next ( );

         JCheckBox  jCheckBox
           = ( JCheckBox ) labelNameToCheckBoxMap.get ( labelName );

         if ( jCheckBox.isSelected ( ) )
         {
           selectedLabelNamesList.add ( labelName );
         }
       }

       return ( String [ ] )
         selectedLabelNamesList.toArray ( new String [ ] { } );
     }

     public boolean  isSelected ( String  labelName )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( labelName );

       JCheckBox  jCheckBox
         = ( JCheckBox ) labelNameToCheckBoxMap.get ( labelName );

       if ( jCheckBox == null )
       {
         throw new IllegalArgumentException (
           "unknown labelName:  " + labelName );
       }

       return jCheckBox.isSelected ( );       
     }

     //////////////////////////////////////////////////////////////////////
     // mutators
     //////////////////////////////////////////////////////////////////////

     public void  setAllSelected ( boolean  selected )
     //////////////////////////////////////////////////////////////////////
     {
       Iterator  iterator = labelNameToCheckBoxMap.values ( ).iterator ( );

       while ( iterator.hasNext ( ) )
       {
         JCheckBox  jCheckBox = ( JCheckBox ) iterator.next ( );

         jCheckBox.setSelected ( selected );
       }       
     }

     public void  setSelected (
       String   labelName,
       boolean  selected )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( labelName );

       JCheckBox  jCheckBox
         = ( JCheckBox ) labelNameToCheckBoxMap.get ( labelName );

       if ( jCheckBox == null )
       {
         throw new IllegalArgumentException (
           "unknown labelName:  " + labelName );
       }

       jCheckBox.setSelected ( selected );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
