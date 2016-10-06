     package com.croftsoft.core.animation;

     import java.awt.Rectangle;
     import javax.swing.JComponent;

     /*********************************************************************
     * Collects repaint requests.
     *
     * @version
     *   2002-12-07
     * @since
     *   2002-03-09
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  RepaintCollector
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public int  getCount ( );

     public Rectangle [ ]  getRepaintRegions ( );

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  repaint ( );

     public void  repaint (
       int  x,
       int  y,
       int  width,
       int  height );

     public void  reset ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }