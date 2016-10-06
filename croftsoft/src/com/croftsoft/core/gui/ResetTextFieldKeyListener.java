     package com.croftsoft.core.gui;

     import java.awt.*;
     import java.awt.event.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Used to reset a text field upon key press after user input error.
     *
     * <p>
     * Example:
     * <pre>
     * String  text = textField.getText ( );
     *
     * if ( "".equals ( text.trim ( ) ) )
     * {
     *   textField.setBackground ( Color.red );
     *
     *   urlTextField.setText ( "data required" );
     *
     *   textField.addKeyListener (
     *     new ResetTextFieldKeyListener ( textField, Color.white ) );
     * }
     * </pre>
     * </p>
     *
     * <p>
     * Upon key press detection, ResetTextFieldKeyListener will
     * <ol>
     * <li> remove itself as a KeyListener from the TextField,
     * <li> set the TextField background to backgroundColor, and
     * <li> set the TextField to defaultText.
     * </ol>
     * </p>
     *
     * @version
     *   2001-03-23
     * @since
     *   2001-03-23
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     *********************************************************************/

     public final class  ResetTextFieldKeyListener
       extends KeyAdapter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private TextField  textField;

     private Color      backgroundColor;

     private String     defaultText;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ResetTextFieldKeyListener (
       TextField  textField,
       Color      backgroundColor,
       String     defaultText )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.textField = textField );

       NullArgumentException.check (
         this.backgroundColor = backgroundColor );

       NullArgumentException.check ( this.defaultText = defaultText );
     }

     /*********************************************************************
     * this ( textField, backgroundColor, "" );
     *********************************************************************/
     public  ResetTextFieldKeyListener (
       TextField  textField,
       Color      backgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       this ( textField, backgroundColor, "" );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  keyPressed ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
       textField.removeKeyListener ( this );

       textField.setBackground ( backgroundColor );

       textField.setText ( defaultText );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
