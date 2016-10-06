     package com.croftsoft.core.gui;

     import java.awt.*;
     import java.awt.event.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Allows the user to select an item from a list.
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     *
     * @version
     *   2001-08-08
     * @since
     *   2001-05-03
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SelectPanel
       extends Panel
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  BUTTON_TEXT_SELECT   = "Select";

     private static final String  BUTTON_TEXT_CANCEL   = "Cancel";

     private static final String  BUTTON_TEXT_PREVIOUS = "Previous";

     private static final String  BUTTON_TEXT_NEXT     = "Next";

     //

     private ActionListener  selectButtonActionListener;

     private ActionListener  cancelButtonActionListener;

     private ActionListener  previousButtonActionListener;

     private ActionListener  nextButtonActionListener;

     private Color           backgroundColor;

     //

     private Label   panelLabel;

     private List    list;

     private Button  selectButton;

     private Button  cancelButton;

     private Button  previousButton;

     private Button  nextButton;

     //

     private int  selectedIndex = -1;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *
     * @param  selectButtonActionListener
     * 
     *   May be null.
     *
     * @param  cancelButtonActionListener
     * 
     *   May be null.
     *
     * @param  previousButtonActionListener
     * 
     *   May be null.
     *
     * @param  nextButtonActionListener
     * 
     *   May be null.
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
     public SelectPanel (
       ActionListener  selectButtonActionListener,
       ActionListener  cancelButtonActionListener,
       ActionListener  previousButtonActionListener,
       ActionListener  nextButtonActionListener,
       String [ ]      items,
       boolean         enablePreviousButton,
       boolean         enableNextButton,
       String          title,
       Color           backgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ) );

       this.selectButtonActionListener   = selectButtonActionListener;

       this.cancelButtonActionListener   = cancelButtonActionListener;

       this.previousButtonActionListener = previousButtonActionListener;

       this.nextButtonActionListener     = nextButtonActionListener;

       setTitle ( title );

       this.backgroundColor = backgroundColor;

       if ( backgroundColor != null )
       {
         setBackground ( backgroundColor );
       }

       // create button panel

       Panel  buttonPanel = new ButtonPanel1 (
         new Button [ ] {
           selectButton   = new Button ( BUTTON_TEXT_SELECT   ),
           previousButton = new Button ( BUTTON_TEXT_PREVIOUS ),
           nextButton     = new Button ( BUTTON_TEXT_NEXT     ),
           cancelButton   = new Button ( BUTTON_TEXT_CANCEL   ) },
         backgroundColor );
         
       selectButton.setEnabled   ( false );

       cancelButton.setEnabled   ( false );

       previousButton.setEnabled ( false );

       nextButton.setEnabled     ( false );

       if ( selectButtonActionListener != null )
       {
         selectButton.addActionListener ( selectButtonActionListener );
       }

       if ( cancelButtonActionListener != null )
       {
         cancelButton.addActionListener ( cancelButtonActionListener );
       }

       if ( previousButtonActionListener != null )
       {
         previousButton.addActionListener ( previousButtonActionListener );
       }

       if ( nextButtonActionListener != null )
       {
         nextButton.addActionListener ( nextButtonActionListener );
       }

       add ( buttonPanel, BorderLayout.SOUTH );

       //

       setItems ( items, enablePreviousButton, enableNextButton );
     }

     /*********************************************************************
     * Convenience constructor.
     *********************************************************************/
     public SelectPanel ( Color  backgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         ( ActionListener ) null,
         ( ActionListener ) null,
         ( ActionListener ) null,
         ( ActionListener ) null,
         ( String [ ]     ) null,
         false,
         false,
         ( String         ) null,
         backgroundColor );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

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

     public synchronized void  setItems (
       String [ ]  items,
       boolean     enablePreviousButton,
       boolean     enableNextButton )
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

       previousButton.setEnabled ( enablePreviousButton );

       nextButton    .setEnabled ( enableNextButton     );
     }

     public synchronized void  replaceCancelButtonActionListener (
       ActionListener  actionListener )
     //////////////////////////////////////////////////////////////////////
     {
       replaceButtonActionListener (
         cancelButtonActionListener, actionListener, cancelButton );

       cancelButtonActionListener = actionListener;
     }

     public synchronized void  replaceSelectButtonActionListener (
       ActionListener  actionListener )
     //////////////////////////////////////////////////////////////////////
     {
       replaceButtonActionListener (
         selectButtonActionListener, actionListener, selectButton );

       selectButtonActionListener = actionListener;
     }

     public synchronized void  replacePreviousButtonActionListener (
       ActionListener  actionListener )
     //////////////////////////////////////////////////////////////////////
     {
       replaceButtonActionListener (
         previousButtonActionListener, actionListener, previousButton );

       selectButtonActionListener = actionListener;
     }

     public synchronized void  replaceNextButtonActionListener (
       ActionListener  actionListener )
     //////////////////////////////////////////////////////////////////////
     {
       replaceButtonActionListener (
         nextButtonActionListener, actionListener, nextButton );

       nextButtonActionListener = actionListener;
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private void  replaceButtonActionListener (
       ActionListener  oldActionListener,
       ActionListener  newActionListener,
       Button          button )
     //////////////////////////////////////////////////////////////////////
     {
       if ( oldActionListener != null )
       {
         button.removeActionListener ( oldActionListener );
       }

       if ( newActionListener == null )
       {
         button.setEnabled ( false );
       }
       else
       {
         button.setEnabled ( true );

         button.addActionListener ( newActionListener );
       }
     }       

     private synchronized void  resetList ( )
     //////////////////////////////////////////////////////////////////////
     {
       selectButton  .setEnabled ( false );

       previousButton.setEnabled ( false );

       nextButton    .setEnabled ( false );

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

       list.addItemListener (
         new ItemListener ( )
         {
           public void  itemStateChanged ( ItemEvent  itemEvent )
           {
             handleItemStateChanged ( );
           }
         } );

       add ( list, BorderLayout.CENTER );

       validate ( );
     }

     private synchronized void  handleItemStateChanged ( )
     //////////////////////////////////////////////////////////////////////
     {
       int  selectedIndex = list.getSelectedIndex ( );

       if ( selectedIndex > -1 )
       {
         if ( selectedIndex == this.selectedIndex )
         {
           list.deselect ( selectedIndex );

           this.selectedIndex = -1;

           selectButton.setEnabled ( false );
         }
         else
         {
           this.selectedIndex = selectedIndex;

           selectButton.setEnabled ( selectButtonActionListener != null );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }