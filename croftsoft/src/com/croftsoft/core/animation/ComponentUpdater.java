     package com.croftsoft.core.animation;

     import javax.swing.JComponent;

     /*********************************************************************
     * Updates the state of a JComponent and calls repaint() as required.
     *
     * @version
     *   2002-03-23
     * @since
     *   2002-02-18
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  ComponentUpdater
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Updates the state of a JComponent and calls repaint() as required.
     *********************************************************************/
     public void  update ( JComponent  component );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
