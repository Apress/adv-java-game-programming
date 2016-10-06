     package com.croftsoft.core.gui.table;

     import javax.swing.event.*;
     import javax.swing.table.*;

     /*********************************************************************
     * An abstract adapter class for interface TableColumnModelListener.
     *
     * <p>
     * The methods in this class are empty.  This class exists as a
     * convenience for creating TableColumnModelListener implementations.
     * Extend this class to create a TableColumnModelEvent listener and
     * override the methods for the events of interest.  If you implement
     * the TableColumnModelListener interface, you have to define all of
     * the methods in it.  This abstract class defines null methods for
     * them all, so you can only have to define methods for events you
     * care about.
     * Create a listener object using the extended class and then register
     * it with a TableColumnModel implementation using the
     * addTableColumnModelListener method.
     * </p>
     *
     * @see
     *   javax.swing.event.TableColumnModelListener
     * @see
     *   javax.swing.table.TableColumnModel
     *
     * @version
     *   2001-10-29
     * @since
     *   2001-10-29
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  TableColumnModelAdapter
       implements TableColumnModelListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  columnAdded            ( TableColumnModelEvent  e ) { }

     public void  columnMarginChanged    ( ChangeEvent            e ) { }

     public void  columnMoved            ( TableColumnModelEvent  e ) { }

     public void  columnRemoved          ( TableColumnModelEvent  e ) { }

     public void  columnSelectionChanged ( ListSelectionEvent     e ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }