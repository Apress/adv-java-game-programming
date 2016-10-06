     package com.croftsoft.core.gui.table;

     import java.awt.*;
     import javax.swing.*;
     import javax.swing.table.*;

     /*********************************************************************
     * Displays every other JTable row in a different color for contrast.
     *
     * <p>
     * Example:
     * <pre>
     * jTable.setDefaultRenderer (
     *   Object.class,
     *   new AlternatingRenderer (
     *     Color.black,
     *     new Color ( 204, 255, 204 ),
     *     Color.black,
     *     new Color ( 230, 230, 190 ),
     *     Color.black,
     *     Color.white ) );
     * </pre>
     * </p>
     *
     * <p>
     * <b>Reference</b><br />
     * John Zukowski,
     * <a target="_blank" href=
     *   "http://www.amazon.com/exec/obidos/ASIN/189311578X/croftsoft-20">
     * Definitive Guide to Swing for Java 2, Second Edition</a>,
     * 2000, Chapter 17, p715.
     * </p>
     *
     * @version
     *   2001-08-16
     * @since
     *   2001-08-16
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AlternatingRenderer
       implements TableCellRenderer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Color  selectedForegroundColor;

     private final Color  selectedBackgroundColor;

     private final Color  oddForegroundColor;

     private final Color  oddBackgroundColor;

     private final Color  evenForegroundColor;

     private final Color  evenBackgroundColor;

     //      

     private final DefaultTableCellRenderer  defaultTableCellRenderer;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * If a Color argument is null, the default Color will be used.
     *********************************************************************/
     public  AlternatingRenderer (
       Color    selectedForegroundColor,
       Color    selectedBackgroundColor,
       Color    oddForegroundColor,
       Color    oddBackgroundColor,
       Color    evenForegroundColor,
       Color    evenBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       this.selectedForegroundColor = selectedForegroundColor;

       this.selectedBackgroundColor = selectedBackgroundColor;

       this.oddForegroundColor      = oddForegroundColor;

       this.oddBackgroundColor      = oddBackgroundColor;

       this.evenForegroundColor     = evenForegroundColor;

       this.evenBackgroundColor     = evenBackgroundColor;

       //

       defaultTableCellRenderer = new DefaultTableCellRenderer ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Component  getTableCellRendererComponent (
       JTable   jTable,
       Object   value,
       boolean  isSelected,
       boolean  hasFocus,
       int      row,
       int      column )
     //////////////////////////////////////////////////////////////////////
     {
       Component  component
         = defaultTableCellRenderer.getTableCellRendererComponent (
         jTable,
         value,
         isSelected,
         hasFocus,
         row,
         column );

       ( ( JLabel ) component ).setOpaque ( true );

       Color  foregroundColor;

       Color  backgroundColor;

       if ( isSelected )
       {
         foregroundColor = selectedForegroundColor;

         backgroundColor = selectedBackgroundColor;
       }
       else
       {
         if ( row % 2 == 0 )
         {
           foregroundColor = evenForegroundColor;

           backgroundColor = evenBackgroundColor;
         }
         else
         {
           foregroundColor = oddForegroundColor;

           backgroundColor = oddBackgroundColor;
         }
       }

       if ( foregroundColor != null )
       {
         component.setForeground ( foregroundColor );
       }

       if ( backgroundColor != null )
       {
         component.setBackground ( backgroundColor );
       }

       return component;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }