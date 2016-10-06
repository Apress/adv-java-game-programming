     package com.croftsoft.core.animation.painter;

     import java.awt.Graphics2D;
     import java.awt.Image;
     import javax.swing.JComponent;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.animation.*;

     /*********************************************************************
     * Stretches the Image across the entire Component.
     *
     * @version
     *   2002-03-23
     * @since
     *   2002-02-17
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  StretchPainter
       implements ComponentPainter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Image  image;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  StretchPainter ( Image  image )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.image = image );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       graphics.drawImage ( image, 0, 0,
         component.getWidth ( ), component.getHeight ( ), component );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
