     package com.croftsoft.core.gui;

     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import java.net.*;
     import javax.swing.*;
     import javax.swing.event.*;
     import javax.swing.border.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Creates a JPanel containing a scrollable JTextArea.
     *
     * @version
     *   2001-09-21
     * @since
     *   2001-09-21
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  TextPanel
       extends JPanel
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final JTextArea  jTextArea;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *
     * @param  text
     *
     *   May be null.
     *
     * @param  panelBackgroundColor
     *
     *   May be null.
     *********************************************************************/
     public  TextPanel (
       String  text,
       Color   panelBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ), true ); // isDoubleBuffered

       if ( panelBackgroundColor != null )
       {
         setBackground ( panelBackgroundColor );
       }

       jTextArea = new JTextArea ( );

       jTextArea.setEditable ( false );

       jTextArea.setLineWrap ( true );

       jTextArea.setWrapStyleWord ( true );

// How can I set the border to 2 characters wide?

       jTextArea.setBorder ( new EmptyBorder ( 4, 4, 4, 4 ) );

       if ( text != null )
       {
         jTextArea.setText ( text );
       }

       add ( new JScrollPane ( jTextArea ), BorderLayout.CENTER );
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * <pre>
     * this ( null, panelBackgroundColor );
     * </pre>
     *
     * @param  panelBackgroundColor
     *
     *    May be null.
     *********************************************************************/
     public  TextPanel ( Color  panelBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null, panelBackgroundColor );
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * <pre>
     * this ( null, null );
     * </pre>
     *********************************************************************/
     public  TextPanel ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public JTextArea  getJTextArea ( ) { return jTextArea; }

     public void  setText ( String  text )
     //////////////////////////////////////////////////////////////////////
     {
       jTextArea.setText ( text );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }