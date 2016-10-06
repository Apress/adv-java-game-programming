     package com.croftsoft.core.gui.list;

     import java.awt.*;
     import java.awt.event.*;

     /*********************************************************************
     * <P>
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1998-04-12
     *********************************************************************/

     public class  DoubleListDialog extends Dialog
       implements ActionListener, ItemListener, WindowListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private DoubleListDialogListener  doubleListDialogListener;
     private List    list_l;
     private List    list_r;
     private Button  accept_Button;
     private Button  cancel_Button;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  DoubleListDialog (
       Frame       parent,
       String      title,
       boolean     modal,
       boolean     multipleMode_left,
       boolean     multipleMode_right,
       String [ ]  items_left,
       String [ ]  items_right,
       String      accept_label,
       String      cancel_label,
       int         x,
       int         y,
       int         w,
       int         h,
       DoubleListDialogListener  doubleListDialogListener )
     //////////////////////////////////////////////////////////////////////
     {
       super ( parent, title, modal );
       this.doubleListDialogListener = doubleListDialogListener;
       setLayout ( null );
       accept_Button = new Button ( accept_label );
       cancel_Button = new Button ( cancel_label );
       int  rows = items_left.length > items_right.length
         ? items_left.length : items_right.length;
       list_l = new List ( rows, multipleMode_left  );
       list_r = new List ( rows, multipleMode_right );
       for ( int  i = 0; i < items_left.length; i++ )
         list_l.add ( items_left [ i ] );
       for ( int  i = 0; i < items_right.length; i++ )
         list_r.add ( items_right [ i ] );
       add ( accept_Button );
       add ( cancel_Button );
       add ( list_l );
       add ( list_r );
       accept_Button.setEnabled ( false );
       accept_Button.addActionListener ( this );
       cancel_Button.addActionListener ( this );
       list_l.addItemListener ( this );
       list_r.addItemListener ( this );
       addWindowListener ( this );
       setBounds ( x, y, w, h );
       show ( );
     }

     public synchronized void  itemStateChanged ( ItemEvent  e )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = e.getSource ( );
       if ( ( source == list_l ) || ( source == list_r ) )
       {
         accept_Button.setEnabled (
              ( list_l.getSelectedItems ( ).length > 0 )
           && ( list_r.getSelectedItems ( ).length > 0 ) );
       }
     }

     public synchronized void  actionPerformed ( ActionEvent  e )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = e.getSource ( );
       if ( source == accept_Button )
       {
         doubleListDialogListener.doubleListDialogAccept (
           list_l.getSelectedItems ( ),
           list_r.getSelectedItems ( ) );
         dispose ( );
       }
       else if ( source == cancel_Button )
       {
         dispose ( );
       }
     }

     public void  windowClosing ( WindowEvent  e )
     //////////////////////////////////////////////////////////////////////
     {
       dispose ( );
     }

     public synchronized void  dispose ( )
     //////////////////////////////////////////////////////////////////////
     {
       doubleListDialogListener.doubleListDialogAccept ( null, null );
       super.dispose ( );
     }

     public void  doLayout ( )
     //////////////////////////////////////////////////////////////////////
     {
       Rectangle  bounds = getBounds ( );
       Insets  insets = getInsets ( );

       Rectangle  r = new Rectangle ( insets.left, insets.top,
         bounds.width - insets.left - insets.right,
         bounds.height - insets.top - insets.bottom );

       int  button_height = accept_Button.getMinimumSize ( ).height;
       int  list_height = r.height - button_height;

       list_l.setBounds ( r.x, r.y, r.width / 2, list_height );
       list_r.setBounds (
         r.x + r.width / 2, r.y, r.width / 2, list_height );
       accept_Button.setBounds ( r.x, r.y + list_height,
         r.width / 2, button_height );
       cancel_Button.setBounds ( r.x + r.width / 2, r.y + list_height,
         r.width / 2, button_height );
     }

     public void  windowActivated   ( WindowEvent  e ) { }
     public void  windowClosed      ( WindowEvent  e ) { }
     public void  windowDeactivated ( WindowEvent  e ) { }
     public void  windowDeiconified ( WindowEvent  e ) { }
     public void  windowIconified   ( WindowEvent  e ) { }
     public void  windowOpened      ( WindowEvent  e ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
