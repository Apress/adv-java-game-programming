     package com.croftsoft.core.gui;

     import java.awt.*;
//   import java.awt.event.*;
     import javax.swing.*;

     import com.croftsoft.core.lang.ThrowableLib;
     import com.croftsoft.core.util.log.Log;

     /*********************************************************************
     * A Log that records events to a JTextArea in a JPanel.
     *
     * <p />
     *
     * @version
     *   2003-05-04
     * @since
     *   2001-07-30
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  LogPanel
       extends JPanel
       implements Log
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final int  DEFAULT_TEXT_LENGTH_MAX = 10000;

     //

     private final int        textLengthMax;

     private final JTextArea  jTextArea;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  LogPanel (
       int    textLengthMax,
       Color  panelBackgroundColor,
//     Color  textFieldBackgroundColor,
       Font   textAreaFont )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ), true ); // isDoubleBuffered

       if ( textLengthMax < 0 )
       {
         throw new IllegalArgumentException ( "textLengthMax < 0" );
       }

       this.textLengthMax = textLengthMax;

       if ( panelBackgroundColor != null )
       {
         setBackground ( panelBackgroundColor );
       }

       add ( jTextArea = new JTextArea ( ), BorderLayout.CENTER );

       jTextArea.setEditable ( false );

       if ( textAreaFont != null )
       {
         jTextArea.setFont ( textAreaFont );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  record ( String  message )
     //////////////////////////////////////////////////////////////////////
     {
       jTextArea.append ( message + '\n' );

       trim ( );     
     }

     public synchronized void  record ( Throwable  throwable )
     //////////////////////////////////////////////////////////////////////
     {
       record ( ThrowableLib.getStackTrace ( throwable ) );
     }


     public synchronized void  record (
       String  message, Throwable  throwable )
     //////////////////////////////////////////////////////////////////////
     {
       record ( message + '\n'
         + ThrowableLib.getStackTrace ( throwable ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private synchronized void  trim ( )
     //////////////////////////////////////////////////////////////////////
     {
       String  text = jTextArea.getText ( );

       int  textLength = text.length ( );

       if ( textLength > textLengthMax )
       {
         jTextArea.setText (
           text.substring ( textLength - textLengthMax ) );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }