     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import java.beans.*;
     import java.io.*;
     import javax.swing.*;
     import javax.swing.event.*;
     import javax.swing.table.*;
     import java.util.*;

     import com.croftsoft.core.gui.ButtonPanel2;
     import com.croftsoft.core.gui.LabeledFieldsPanel2;
     import com.croftsoft.core.gui.table.AlternatingRenderer;
     import com.croftsoft.core.gui.table.TableColumnModelAdapter;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.util.ArrayLib;

     /*********************************************************************
     * Displays the downloaded data in a table.
     *
     * <p />
     *
     * @version
     *   2001-11-09
     * @since
     *   2001-07-31
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastTablePanel
       extends JPanel
       implements ActionListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  CATEGORY_ALL   = "--- ALL ---";

     //

     private final AgoracastMediator     agoracastMediator;

     private final AgoracastBrowsePanel  agoracastBrowsePanel;

     private final JButton               columnsButton;

     private final JButton               downloadButton;

     private final JComboBox             jComboBox;

     //

     private AgoracastTableModel   agoracastTableModel;

     private JTable                jTable;

     private JScrollPane           jScrollPane;

     private String                category;

     private String [ ]            columnNames;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastTablePanel (
       AgoracastMediator     agoracastMediator,
       AgoracastBrowsePanel  agoracastBrowsePanel )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ), true ); // isDoubleBuffered

       NullArgumentException.check (
         this.agoracastMediator = agoracastMediator );

       NullArgumentException.check (
         this.agoracastBrowsePanel = agoracastBrowsePanel );

       AgoracastLib.setColor ( this, agoracastMediator );

       updateTable ( null );

       columnsButton = new JButton ( "Add and Remove Columns" );

       columnsButton.addActionListener ( this );

       downloadButton = new JButton ( "Download New Data" );

       downloadButton.addActionListener ( this );

       add (
         new ButtonPanel2 (
           new JButton [ ] { columnsButton, downloadButton } ),
         BorderLayout.SOUTH );

       String [ ]  categories = ( String [ ] ) ArrayLib.prepend (
         AgoracastConstants.CHOICES_CATEGORY, CATEGORY_ALL );

       jComboBox = new JComboBox ( categories );

       jComboBox.addActionListener ( this );

       JPanel  categoryPanel = new JPanel ( new FlowLayout ( ), true );

       categoryPanel.add ( new JLabel ( "Category:  " ) );

       categoryPanel.add ( jComboBox );

       JPanel  northPanel = new JPanel ( new BorderLayout ( ), true );

       northPanel.add ( categoryPanel, BorderLayout.NORTH );

       northPanel.add (
         new JLabel ( AgoracastConstants.TABLE_TEXT ), BorderLayout.SOUTH );

       add ( northPanel, BorderLayout.NORTH );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = actionEvent.getSource ( );

       if ( source == downloadButton )
       {
         agoracastBrowsePanel.download ( );
       }
       else if ( source == columnsButton )
       {
         agoracastBrowsePanel.showFieldsPanel ( columnNames );
       }
       else if ( source == jComboBox )
       {
         handleCategorySelection ( );
       }
     }

     public synchronized void  updateTable ( String [ ]  columnNames )
     //////////////////////////////////////////////////////////////////////
     {
       if ( ( columnNames == null    )
         || ( columnNames.length < 1 ) )
       {
         // get the default columns for the category

         AgoracastCategory  agoracastCategory
           = agoracastMediator.getAgoracastCategory ( category );

         columnNames = agoracastCategory.getColumnNames ( );
       }

       this.columnNames = columnNames;

       if ( jScrollPane != null )
       {
         remove ( jScrollPane );
       }

       agoracastTableModel = new AgoracastTableModel (
         agoracastMediator, category, columnNames );

       jTable = new JTable ( agoracastTableModel );

// The following hack attempts to size the column widths proportional
// to their max String lengths.  Should be replaced by a better
// algorithm or a method that allows the user to persistently set the
// width.

       TableColumnModel  tableColumnModel = jTable.getColumnModel ( );

       int  columnCount = tableColumnModel.getColumnCount ( );

       int  rowCount = agoracastTableModel.getRowCount ( );

       for ( int  i = 0; i < columnCount; i++ )
       {
         TableColumn  tableColumn = tableColumnModel.getColumn ( i );

         int  maxCharacterWidth
           = agoracastTableModel.getColumnName ( i ).length ( );

         for ( int  j = 0; j < rowCount; j++ )
         {
           String  value = ( String )
             ( agoracastTableModel.getValueAt ( j, i ) );

           if ( value != null )
           {
             int  length = value.length ( );

             if ( length > maxCharacterWidth )
             {
               maxCharacterWidth = length;
             }        
           }
         }

         tableColumn.setPreferredWidth ( maxCharacterWidth * 15 );
       }

       jTable.getSelectionModel ( ).setSelectionMode (
         ListSelectionModel.SINGLE_SELECTION );

       JTableHeader  jTableHeader = jTable.getTableHeader ( );

       jTableHeader.setUpdateTableInRealTime ( false );

       jTableHeader.addMouseListener (
         new MouseAdapter ( )
         {
           public void  mouseClicked ( MouseEvent  mouseEvent )
           {
             handleSortColumn ( mouseEvent );
           }
         } );

       jTable.addMouseListener (
         new MouseAdapter ( )
         {
           public void  mouseClicked ( MouseEvent  mouseEvent )
           {
             handleRowSelection ( mouseEvent );
           }
         } );

       jTable.setDefaultRenderer (
         Object.class,
         new AlternatingRenderer (
           AgoracastConstants.SELECTED_FOREGROUND_COLOR,
           AgoracastConstants.SELECTED_BACKGROUND_COLOR,
           AgoracastConstants.ODD_FOREGROUND_COLOR,
           AgoracastConstants.ODD_BACKGROUND_COLOR,
           AgoracastConstants.EVEN_FOREGROUND_COLOR,
           AgoracastConstants.EVEN_BACKGROUND_COLOR ) );

       jTable.setRequestFocusEnabled ( false );

       // Runs faster if unregistered.
       // p717, Zukowski, "Definitive Guide to Swing for Java 2, 2nd Ed."

       ToolTipManager.sharedInstance ( ).unregisterComponent ( jTable );

       jScrollPane = new JScrollPane ( jTable );

       add ( jScrollPane, BorderLayout.CENTER );

       revalidate ( );
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private synchronized void  handleRowSelection ( MouseEvent  e )
     //////////////////////////////////////////////////////////////////////
     {
       if ( e.getClickCount ( ) == 2 )
       {
         agoracastBrowsePanel.viewSource (
           agoracastTableModel.getAgoracastData (
           jTable.getSelectedRow ( ) ) );
       }
     }

     /*********************************************************************
     * See Robinson & Vorobiev, "Swing", 2000, p602.
     *********************************************************************/
     private synchronized void  handleSortColumn ( MouseEvent  e )
     //////////////////////////////////////////////////////////////////////
     {
       TableColumnModel  tableColumnModel = jTable.getColumnModel ( );

       int  columnModelIndex
         = tableColumnModel.getColumnIndexAtX ( e.getX ( ) );

       int  modelIndex = tableColumnModel.getColumn (
         columnModelIndex ).getModelIndex ( );

       agoracastTableModel.sort ( modelIndex );
     }

     private synchronized void  handleCategorySelection ( )
     //////////////////////////////////////////////////////////////////////
     {
       category = ( String ) jComboBox.getSelectedItem ( );

       if ( CATEGORY_ALL.equals ( category ) )
       {
         category = null;
       }

       AgoracastCategory  agoracastCategory
         = agoracastMediator.getAgoracastCategory ( category );

       updateTable ( agoracastCategory.getColumnNames ( ) ); 
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }