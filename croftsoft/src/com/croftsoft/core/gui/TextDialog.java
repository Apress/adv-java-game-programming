     package com.croftsoft.core.gui;

     import com.croftsoft.core.lang.*;
     import java.awt.*;

     /*********************************************************************
     * @version
     *   1997-04-08
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     *********************************************************************/

     public class  TextDialog extends Dialog {
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private static final int  WIDTH_PAD = 13; // kludge

     private Frame      parent;
     private Label [ ]  labels;
     private Dimension  size_old;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  TextDialog (
       Frame       parent,
       String      title,
       boolean     modal,
       String [ ]  lines ) {
     //////////////////////////////////////////////////////////////////////
       super ( parent, title, modal );
       this.parent = parent;

       setBackground ( Color.lightGray );
       setLayout ( null );
       setResizable ( false );

       labels = new Label [ lines.length ];
       for ( int  i = 0; i < labels.length; i++ ) {
         add ( labels [ i ] = new Label ( lines [ i ], Label.CENTER ) );
       }

       show ( );
     }

     public  TextDialog (
       Frame       parent,
       String      title,
       boolean     modal,
       String      line ) {
     //////////////////////////////////////////////////////////////////////
       this ( parent, title, modal, StringLib.toStringArray ( line ) );
     }

     public void  layout ( ) {
     //////////////////////////////////////////////////////////////////////
       int  label_width  = 0;
       int  label_height = 0;
       for ( int  i = 0; i < labels.length; i++ ) {
         Dimension  preferredSize = labels [ i ].preferredSize ( );
         label_width = ( label_width > preferredSize.width )
           ? label_width : preferredSize.width;
         label_height = ( label_height > preferredSize.height )
           ? label_height : preferredSize.height;
       }
       Insets  insets = insets ( );

       for ( int  i = 0; i < labels.length; i++ ) {
         labels [ i ].reshape ( insets.left, insets.top + i * label_height,
           label_width, label_height );
       }

       Dimension  desired = new Dimension (
         label_width  + insets.left + insets.right + WIDTH_PAD,
         labels.length * label_height + insets.top  + insets.bottom );
       Dimension  size = size ( );
       if ( ( size_old == null ) || ( size.width != desired.width )
         || ( size.height != desired.height ) ) {
         size_old = size;
         WindowLib.centerAboveParent ( this, desired );
       }
     }

     public boolean  handleEvent ( Event  event ) {
     //////////////////////////////////////////////////////////////////////
       if ( event.id == Event.WINDOW_DESTROY ) {
         dispose ( );
         return true;
       }
       return super.handleEvent ( event );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
