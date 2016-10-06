     package com.croftsoft.core.animation;

     import java.awt.Graphics2D;
     import javax.swing.JComponent;

     /*********************************************************************
     * An object that knows how and where to paint a graphical JComponent.
     *
     * @version
     *   2002-03-23
     * @since
     *   2002-02-25
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  ComponentPainter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  paint (
       JComponent  component,
       Graphics2D  graphics );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
