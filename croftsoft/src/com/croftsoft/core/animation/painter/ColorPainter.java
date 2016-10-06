     package com.croftsoft.core.animation.painter;

     import java.awt.Color;
     import java.awt.Graphics2D;
     import java.awt.Shape;
     import java.io.Serializable;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.ComponentPainter;

     /*********************************************************************
     * Fills an area of the JComponent with a Color.
     *
     * <p>
     * Useful for setting the background Color of a Component.  Note that
     * if you are also using an opaque (non-transparent) background Image,
     * using a background Color could be a waste of CPU cycles if it is
     * completely covered by the background Image.
     * </p>
     *
     * <p>
     * Semi-transparent Colors may be useful for darkening or color
     * tinting a scene.
     * </p>
     *
     * @version
     *   2003-08-05
     * @since
     *   2002-02-18
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ColorPainter
       implements ComponentPainter, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private Color  color;

     private Shape  shape;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *
     * @param  color
     *
     *   If null, the component background color will be used.
     *
     * @param  shape
     *
     *   If null, the entire component will be colored.
     *********************************************************************/
     public  ColorPainter (
       Color  color,
       Shape  shape )
     //////////////////////////////////////////////////////////////////////
     {
       this.color = color;

       this.shape = shape;
     }

     public  ColorPainter ( Color  color )
     //////////////////////////////////////////////////////////////////////
     {
       this.color = color;
     }

     public  ColorPainter ( )
     //////////////////////////////////////////////////////////////////////
     {
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public Color  getColor ( )
     //////////////////////////////////////////////////////////////////////
     {
       return color;
     }

     public Shape  getShape ( )
     //////////////////////////////////////////////////////////////////////
     {
       return shape;
     }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setColor ( Color  color )
     //////////////////////////////////////////////////////////////////////
     {
       this.color = color;
     }

     public void  setShape ( Shape  shape )
     //////////////////////////////////////////////////////////////////////
     {
       this.shape = shape;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       if ( color == null )
       {
         graphics.setColor ( component.getBackground ( ) );
       }
       else
       {
         graphics.setColor ( color );
       }

       if ( shape == null )
       {
         graphics.fillRect ( 0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE );
       }
       else
       {
         // graphics.fill(Shape) does not like Integer.MAX_VALUE

         graphics.fill ( shape );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
