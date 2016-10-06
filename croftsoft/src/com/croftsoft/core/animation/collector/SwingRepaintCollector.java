     package com.croftsoft.core.animation.collector;

     import java.awt.Rectangle;
     import javax.swing.JComponent;
     import javax.swing.RepaintManager;

     import com.croftsoft.core.animation.RepaintCollector;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Routes repaint requests back to the standard Swing RepaintManager.
     *
     * @version
     *   2002-12-07
     * @since
     *   2002-03-13
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  SwingRepaintCollector
       implements RepaintCollector
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final Rectangle [ ]  EMPTY_ARRAY = new Rectangle [ 0 ];

     //

     private final JComponent  component;

     //////////////////////////////////////////////////////////////////////
     // constructor method
     //////////////////////////////////////////////////////////////////////

     public  SwingRepaintCollector ( JComponent  jComponent )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.component = jComponent );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public Rectangle [ ]  getRepaintRegions ( ) { return EMPTY_ARRAY; }

     public int  getCount ( ) { return 0; }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  repaint ( )
     //////////////////////////////////////////////////////////////////////
     {
       RepaintManager.currentManager ( component ).markCompletelyDirty (
         component );
     }

     public void  repaint (
       final int  x,
       final int  y,
       final int  width,
       final int  height )
     //////////////////////////////////////////////////////////////////////
     {
       RepaintManager.currentManager ( component ).addDirtyRegion (
         component, x, y, width, height );
     }

     public void  reset ( )
     //////////////////////////////////////////////////////////////////////
     {
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
