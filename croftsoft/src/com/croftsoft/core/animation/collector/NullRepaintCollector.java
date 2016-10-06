     package com.croftsoft.core.animation.collector;

     import java.awt.Rectangle;

     import com.croftsoft.core.animation.RepaintCollector;

     /*********************************************************************
     * A Null Object RepaintCollector implemenation.
     *
     * @version
     *   2002-12-01
     * @since
     *   2002-12-01
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  NullRepaintCollector
       implements RepaintCollector
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final NullRepaintCollector  INSTANCE
       = new NullRepaintCollector ( );

     private static final Rectangle [ ]  EMPTY_ARRAY = new Rectangle [ 0 ];

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public int  getCount ( ) { return 0; }

     public Rectangle [ ]  getRepaintRegions ( ) { return EMPTY_ARRAY; }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  repaint ( int  x, int  y, int  width, int  height ) { }

     public void  repaint ( ) { }

     public void  reset   ( ) { }

     //////////////////////////////////////////////////////////////////////
     // private constructor method
     //////////////////////////////////////////////////////////////////////

     private  NullRepaintCollector ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
