     package com.croftsoft.core.animation.collector;

     import java.awt.Rectangle;

     import com.croftsoft.core.animation.RepaintCollector;

     /*********************************************************************
     * Repaints entire component if there is a repaint request of any size.
     *
     * <p>
     * Another way to think of this RepaintCollector implementation is
     * "all-or-nothing".
     * </p>
     *
     * @version
     *   2002-12-01
     * @since
     *   2002-11-30
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  BooleanRepaintCollector
       implements RepaintCollector
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final Rectangle [ ]  REPAINT_REGIONS
       = new Rectangle [ ] {
       new Rectangle ( Integer.MAX_VALUE, Integer.MAX_VALUE ) };

     //

     private boolean  doRepaint;

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public int  getCount ( )
     //////////////////////////////////////////////////////////////////////
     {
       return doRepaint ? 1 : 0;
     }

     public Rectangle [ ]  getRepaintRegions ( )
     //////////////////////////////////////////////////////////////////////
     {
       return REPAINT_REGIONS;
     }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  repaint (
       int  x,
       int  y,
       int  width,
       int  height )
     //////////////////////////////////////////////////////////////////////
     {
       doRepaint = true;
     }

     public void  repaint ( )
     //////////////////////////////////////////////////////////////////////
     {
       doRepaint = true;
     }

     public void  reset ( )
     //////////////////////////////////////////////////////////////////////
     {
       doRepaint = false;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
