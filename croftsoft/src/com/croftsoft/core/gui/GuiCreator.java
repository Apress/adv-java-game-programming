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
     * Creates pre-configured GUI widgets.
     *
     * <p>
     * Useful for instantiating GUI widgets without creating a subclass.
     * </p>
     *
     * @version
     *   2001-09-21
     * @since
     *   2001-07-29
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  GuiCreator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Creates a JEditorPane for displaying HTML text.
     *
     * <p>
     * The pane in non-editable and the caret position is set to zero.
     * </p>
     *
     * <p>
     * <b>Reference:</b><br />
     * Kim Topley,
     * <a target="_blank" href=
     * "http://www.amazon.com/exec/obidos/ASIN/0130832928/croftsoft-20">
     * Core Swing:  Advanced Programming</a>,
     * 2000,
     * Chapter 4 "JEditorPane and the Swing HTML Package",
     * Section "Hypertext Links",
     * p476.
     * </p>
     *
     * @param  hyperlinkListener
     *
     *   May be null.
     *   
     *********************************************************************/
     public static JEditorPane  createHtmlPane (
       String             html,
       HyperlinkListener  hyperlinkListener )
     //////////////////////////////////////////////////////////////////////
     {
       JEditorPane  jEditorPane = new JEditorPane ( "text/html", html );

       jEditorPane.setEditable ( false );

       jEditorPane.setCaretPosition ( 0 );

       if ( hyperlinkListener != null )
       {
         jEditorPane.addHyperlinkListener ( hyperlinkListener );
       }

       return jEditorPane;
     }

     /*********************************************************************
     * Creates a JEditorPane for displaying HTML text.
     *
     * <p>
     * The pane in non-editable and the caret position is set to zero.
     * </p>
     *
     * <p>
     * <b>Reference:</b><br />
     * Kim Topley,
     * <a target="_blank" href=
     * "http://www.amazon.com/exec/obidos/ASIN/0130832928/croftsoft-20">
     * Core Swing:  Advanced Programming</a>,
     * 2000,
     * Chapter 4 "JEditorPane and the Swing HTML Package",
     * Section "Hypertext Links",
     * p476.
     * </p>
     *
     * @param  hyperlinkListener
     *
     *   May be null.
     *
     * @throws IOException
     *
     *   If URL is null or cannot be accessed.
     *********************************************************************/
     public static JEditorPane  createHtmlPane (
       URL                initialPage,
       HyperlinkListener  hyperlinkListener )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       JEditorPane  jEditorPane = new JEditorPane ( initialPage );

       jEditorPane.setEditable ( false );

       jEditorPane.setCaretPosition ( 0 );

       if ( hyperlinkListener != null )
       {
         jEditorPane.addHyperlinkListener ( hyperlinkListener );
       }

       return jEditorPane;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  GuiCreator ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }