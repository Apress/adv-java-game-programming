     package com.croftsoft.core.animation.painter;

     import com.croftsoft.core.animation.ComponentPainter;

     /*********************************************************************
     * A ComponentPainter with (x,y) coordinate accessor/mutator methods.
     *
     * @version
     *   2003-07-05
     * @since
     *   2002-03-05
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  XYPainter
       extends ComponentPainter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public int  getX ( );

     public int  getY ( );

     public void  setX ( int  x );

     public void  setY ( int  y );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
