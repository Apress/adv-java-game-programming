     package com.croftsoft.core.gui;

     import java.awt.*;
     import java.awt.event.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Allows the user to manipulate a list of items via control buttons.
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     *
     * @version
     *   2001-08-08
     * @since
     *   2001-06-04
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  ListControlPanel
       extends Panel
       implements ItemListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private Button [ ]  buttons;

     private Color       backgroundColor;

     //

     private Label   panelLabel;

     private List    list;

     //

     private int  selectedIndex = -1;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *
     * @param  buttons
     *
     *   Must not be null.
     *
     * @param  items
     * 
     *   May be null.
     *
     * @param  title
     * 
     *   May be null.
     *
     * @param  backgroundColor
     * 
     *   May be null.
     *********************************************************************/
     public  ListControlPanel (
       Button [ ]  buttons,
       String [ ]  items,
       String      title,
       Color       backgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ) );

       NullArgumentException.check ( this.buttons = buttons );

       setTitle ( title );

       this.backgroundColor = backgroundColor;

       if ( backgroundColor != null )
       {
         setBackground ( backgroundColor );
       }

       // create button panel

       Panel  buttonPanel = new ButtonPanel1 ( buttons, backgroundColor );
         
       add ( buttonPanel, BorderLayout.SOUTH );

       //

       setItems ( items );
     }

     public  ListControlPanel (
       String [ ]  buttonLabels,
       String      title,
       Color       backgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       this ( ButtonLib.createButtonArray ( buttonLabels ),
         null, title, backgroundColor );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Button [ ]  getButtons ( )
     //////////////////////////////////////////////////////////////////////
     {
       return buttons;
     }

     public int  getSelectedIndex ( )
     //////////////////////////////////////////////////////////////////////
     {
       return list.getSelectedIndex ( );
     }

     public String  getSelectedItem ( )
     //////////////////////////////////////////////////////////////////////
     {
       return list.getSelectedItem ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  setTitle ( String  title )
     //////////////////////////////////////////////////////////////////////
     {
       if ( title == null )
       {
         if ( panelLabel != null )
         {
           remove ( panelLabel );
         }
       }
       else
       {
         if ( panelLabel == null )
         {
           panelLabel = new Label ( title );

           add ( panelLabel, BorderLayout.NORTH );
         }
         else
         {
           panelLabel.setText ( title );
         }
       }
     }

     public synchronized void  setItems ( String [ ]  items )
     //////////////////////////////////////////////////////////////////////
     {
       resetList ( );

       if ( items != null )
       {
         for ( int  i = 0; i < items.length; i++ )
         {
           list.add ( items [ i ] );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  itemStateChanged ( ItemEvent  itemEvent )
     //////////////////////////////////////////////////////////////////////
     {
       int  selectedIndex = list.getSelectedIndex ( );

       if ( selectedIndex > -1 )
       {
         if ( selectedIndex == this.selectedIndex )
         {
           list.deselect ( selectedIndex );

           this.selectedIndex = -1;
         }
         else
         {
           this.selectedIndex = selectedIndex;
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private synchronized void  resetList ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( list != null )
       {
         remove ( list );
       }

       selectedIndex = -1;

       list = new List ( 4, false );

       if ( backgroundColor != null )
       {
         list.setBackground ( backgroundColor );
       }

       list.addItemListener ( this );

       add ( list, BorderLayout.CENTER );

       validate ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }