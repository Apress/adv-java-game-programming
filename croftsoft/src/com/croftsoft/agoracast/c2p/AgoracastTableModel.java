     package com.croftsoft.agoracast.c2p;

     import java.awt.event.*;
     import java.io.*;
     import javax.swing.*;
     import javax.swing.table.*;
     import java.util.*;

     import com.croftsoft.core.gui.table.RowSortComparator;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     *
     * <p />
     *
     * @version
     *   2002-01-29
     * @since
     *   2001-07-31
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastTableModel
       extends AbstractTableModel
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

//   private final AgoracastMediator    agoracastMediator;

     private final String [ ]           columnNames;

     private final String [ ]           columnNamesLowerCase;

     private final AgoracastData [ ]    agoracastDatas;

     private final AgoracastComparator  agoracastComparator;

     private final int                  columnCount;

     private final int                  rowCount;

     private final boolean [ ]          columnsReversed;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastTableModel (
       AgoracastMediator  agoracastMediator,
       String             category,
       String [ ]         columnNames )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( agoracastMediator );

       NullArgumentException.check ( this.columnNames = columnNames );

       columnCount = columnNames.length;

       columnNamesLowerCase = new String [ columnCount ];

       for ( int  i = 0; i < columnCount; i++ )
       {
         columnNamesLowerCase [ i ] = columnNames [ i ].toLowerCase ( );
       }

       AgoracastDatabase  agoracastDatabase
         = agoracastMediator.getAgoracastDatabase ( );

       if ( category != null )
       {
         agoracastDatas
           = agoracastDatabase.getAgoracastDatasForCategory ( category );
       }
       else
       {
         agoracastDatas = agoracastDatabase.getAgoracastDatas ( );
       }

       rowCount = agoracastDatas.length;

       agoracastComparator = new AgoracastComparator (
         agoracastMediator, "", false );

       columnsReversed = new boolean [ columnCount ];

       sort ( 0 );

//     agoracastDatabase.addEventObserver ( this );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int getColumnCount ( )
     //////////////////////////////////////////////////////////////////////
     {
       return columnCount;
     }

     public String  getColumnName ( int  column )
     //////////////////////////////////////////////////////////////////////
     {
       return columnNames [ column ];
     }

     public int  getRowCount ( )
     //////////////////////////////////////////////////////////////////////
     {
       return rowCount;
     }

     public Object  getValueAt ( int  row, int  column )
     //////////////////////////////////////////////////////////////////////
     {
       return agoracastDatas [ row ]
         .getValue ( columnNamesLowerCase [ column ] );
     }

     public boolean  isCellEditable (
       int  row,
       int  column )
     //////////////////////////////////////////////////////////////////////
     {
       return false;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  sort ( int  columnIndex )
     //////////////////////////////////////////////////////////////////////
     {
       columnsReversed [ columnIndex ] = !columnsReversed [ columnIndex ];

       agoracastComparator.setReverse ( columnsReversed [ columnIndex ] );

       agoracastComparator.setSortField (
         columnNamesLowerCase [ columnIndex ] );

       Arrays.sort ( agoracastDatas, agoracastComparator );

       fireTableDataChanged ( );
     }

     public synchronized AgoracastData  getAgoracastData ( int  tableRow )
     //////////////////////////////////////////////////////////////////////
     {
       return agoracastDatas [ tableRow ];
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

/*
     public synchronized void  observeEvent ( Object  event )
     //////////////////////////////////////////////////////////////////////
     {
       AgoracastEvent  agoracastEvent = ( AgoracastEvent ) event;

       if ( agoracastEvent.getType ( ) == AgoracastEvent.TYPE_ADD )
       {
         String  articleId = agoracastEvent.getArticleId ( );

         add ( agoracastDatabase.getAgoracastData ( articleId ) );
       }
     }
*/

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }