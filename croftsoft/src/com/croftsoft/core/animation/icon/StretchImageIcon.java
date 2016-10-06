     package com.croftsoft.core.animation.icon;

     import java.awt.Component;
     import java.awt.Graphics;
     import java.awt.Image;
     import java.net.URL;
     import javax.swing.ImageIcon;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * ImageIcon that stretches its painting across the entire Component.
     *
     * @version
     *   2002-03-16
     * @since
     *   2002-02-17
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  StretchImageIcon
       extends ImageIcon
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public  StretchImageIcon ( Image  image )
     //////////////////////////////////////////////////////////////////////
     {
       super ( image );
     }

     public  StretchImageIcon ( URL  location )
     //////////////////////////////////////////////////////////////////////
     {
       super ( location );
     }

     public void  paintIcon (
       Component  component,
       Graphics   graphics,
       int        x,
       int        y )
     //////////////////////////////////////////////////////////////////////
     {
       graphics.drawImage ( getImage ( ), 0, 0,
         component.getWidth ( ), component.getHeight ( ), component );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
