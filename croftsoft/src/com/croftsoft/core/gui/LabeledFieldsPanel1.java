     package com.croftsoft.core.gui;

     import java.awt.*;
     import java.util.Hashtable;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * A Panel of TextFields with identifying Labels.
     *
     * <p>
     * Created TextField objects are available via an accessor method.
     * </p>
     *
     * <p>
     * Example:
     * <code>
     * <pre>
     * LabeledFieldsPanel1  labeledFieldsPanel1 = new LabeledFieldsPanel1 (
     *   new String [ ] { "username", "password" } );
     *
     * TextField  passwordTextField
     *   = labeledFieldsPanel1.getTextField ( "password" );
     *
     * passwordTextField.setEchoChar ( '*' );
     * </pre>
     * </code>
     * </p>
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     *
     * @version
     *   2001-08-08
     * @since
     *   2001-03-28
     * @author
     *   <a href="http://www.alumni.caltech.edu/~croft/">David W. Croft</a>
     *********************************************************************/

     public final class  LabeledFieldsPanel1
       extends Panel
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Hashtable  labelNameToTextFieldMap = new Hashtable ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  LabeledFieldsPanel1 (
       String [ ]  labelNames,
       Color       panelBackgroundColor,
       Color       textFieldBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ) );

       NullArgumentException.check ( labelNames );

       Panel  labelsPanel = new Panel (
         new GridLayout ( labelNames.length, 1, 4, 4 ) );

       Panel  fieldsPanel = new Panel (
         new GridLayout ( labelNames.length, 1, 4, 4 ) );

       for ( int  i = 0; i < labelNames.length; i++ )
       {
         String  labelName = labelNames [ i ];

         labelsPanel.add ( new Label ( labelName ) );

         TextField  textField = new TextField ( );

         labelNameToTextFieldMap.put ( labelName, textField );

         fieldsPanel.add ( textField );

         if ( textFieldBackgroundColor != null )
         {
           textField.setBackground ( textFieldBackgroundColor );
         }
       }

       if ( panelBackgroundColor != null )
       {
         setBackground ( panelBackgroundColor );
       }

       add ( labelsPanel, BorderLayout.WEST );

       add ( fieldsPanel, BorderLayout.CENTER );
     }

     public  LabeledFieldsPanel1 (
       String  labelName,
       Color   panelBackgroundColor,
       Color   textFieldBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new String [ ] { labelName },
         panelBackgroundColor, textFieldBackgroundColor );
     }

     public  LabeledFieldsPanel1 ( String [ ]  labelNames )
     //////////////////////////////////////////////////////////////////////
     {
       this ( labelNames, null, null );
     }

     public  LabeledFieldsPanel1 ( String  labelName )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new String [ ] { labelName }, null, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public TextField  getTextField ( String  labelName )
     //////////////////////////////////////////////////////////////////////
     {
       return ( TextField ) labelNameToTextFieldMap.get ( labelName );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
