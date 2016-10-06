     package com.croftsoft.core.gui;

     import com.croftsoft.core.lang.*;
     import java.awt.*;
     import java.util.*;

     /*********************************************************************
     * @version
     *   1997-04-28
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     *********************************************************************/

     public class  TextCanvas extends Canvas {
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private Vector     line_Vector  = new Vector ( );
     private Vector     color_Vector = new Vector ( );
     private Dimension  size;
     private Graphics   graphics;
     private Image      offscreenImage;
     private Graphics   offscreenGraphics;

     private static final int  TEXT_INSET_LEFT = 5;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public TextCanvas (
       Color  foreground, Color  background, Font  font ) {
     //////////////////////////////////////////////////////////////////////
       setForeground ( foreground );
       setBackground ( background );
       setFont ( font );
     }
/*
     public synchronized void  dispose ( ) {
     //////////////////////////////////////////////////////////////////////
       if ( graphics != null ) graphics.dispose ( );
       if ( offscreenGraphics != null ) offscreenGraphics.dispose ( );
     }
*/
     public void  write ( String  line ) {
     //////////////////////////////////////////////////////////////////////
       write ( line, getForeground ( ) );
     }

     public void  write ( String  line, Color  color ) {
     //////////////////////////////////////////////////////////////////////
       String [ ]  lines = StringLib.toStringArray ( line );
       synchronized ( this ) {
         for ( int  i = 0; i < lines.length; i++ ) {
           line_Vector.addElement ( lines [ i ] );
           color_Vector.addElement ( color );
         }
       }
       update_offscreen ( );
       repaint ( );
     }

     private synchronized void  update_offscreen ( ) {
     //////////////////////////////////////////////////////////////////////
       if ( size == null ) return;
       int  font_height = getFontMetrics ( getFont ( ) ).getHeight ( );
       int  max_lines = size.height / font_height;
       if ( max_lines < 1 ) max_lines = 1;
       int  line_count;
       while ( ( line_count = line_Vector.size ( ) ) > max_lines ) {
         line_Vector.removeElementAt ( 0 );
         color_Vector.removeElementAt ( 0 );
       }
       if ( offscreenGraphics == null ) {
         offscreenImage = createImage ( size.width, size.height );
         if ( offscreenImage == null ) return;
         offscreenGraphics = offscreenImage.getGraphics ( );
       }
       offscreenGraphics.setColor ( getBackground ( ) );
       offscreenGraphics.fillRect ( 0, 0, size.width, size.height );
       offscreenGraphics.setFont ( getFont ( ) );
       for ( int  i = 0; i < line_count; i++ ) {
         offscreenGraphics.setColor (
           ( Color ) color_Vector.elementAt ( i ) );
         offscreenGraphics.drawString (
           ( String ) line_Vector.elementAt ( i ),
           TEXT_INSET_LEFT, font_height * ( i + 1 ) );
       }
     }

     public void  paint ( Graphics  g ) {
     //////////////////////////////////////////////////////////////////////
       try {
         if ( graphics == null ) graphics = getGraphics ( );
         if ( graphics == null ) return;
         if ( offscreenImage != null ) {
           graphics.drawImage ( offscreenImage, 0, 0, this );
         }
       } catch ( Exception  e ) { e.printStackTrace ( ); }
     }

     public synchronized void  reshape (
       int  x, int  y, int  width, int  height ) {
     //////////////////////////////////////////////////////////////////////
       try {
       super.reshape ( x, y, width, height );
       Dimension  size = size ( );
       if ( !size.equals ( this.size ) ) {
         this.size = size;
         if ( offscreenGraphics != null ) {
           offscreenGraphics.dispose ( );
           offscreenGraphics = null;
           offscreenImage = null;
           System.gc ( );
         }
         update_offscreen ( );
       }
       } catch ( Exception  e ) { e.printStackTrace ( ); }
     }

     public void  repaint ( ) {
     //////////////////////////////////////////////////////////////////////
       paint ( null );
     }

     public void  update ( Graphics  g ) {
     //////////////////////////////////////////////////////////////////////
       paint ( null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
