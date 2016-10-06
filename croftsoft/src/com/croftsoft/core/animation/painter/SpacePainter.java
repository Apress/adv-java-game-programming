     package com.croftsoft.core.animation.painter;

     import java.awt.Color;
     import java.awt.Graphics;
     import java.awt.Graphics2D;
     import java.awt.Image;
     import java.awt.Rectangle;
     import java.util.Random;
     import javax.swing.JComponent;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.animation.ComponentPainter;

     /*********************************************************************
     * Fills an area of the component with stars against black space.
     *
     * @version
     *   2003-07-05
     * @since
     *   2002-03-28
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SpacePainter
       implements ComponentPainter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final double  DEFAULT_STAR_DENSITY = 0.01;

     //

     /** Probability of a star at any given pixel. */
     private final double  starDensity;

     private final Random  random;

     private final long    seed;

     //

     private Rectangle  paintArea;

     private boolean    useComponentBounds;

     private Image      image;

     private int        width;

     private int        height;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor method.
     *
     * @param  paintArea
     *
     *    If null, component.getBounds() will be used.
     *********************************************************************/
     public  SpacePainter (
       Rectangle  paintArea,
       double     starDensity )
     //////////////////////////////////////////////////////////////////////
     {
       setPaintArea ( paintArea );

       this.starDensity = starDensity;

       random = new Random ( );

       seed = random.nextLong ( );
     }

     /*********************************************************************
     * Convenience constructor method.
     *********************************************************************/
     public  SpacePainter ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null, DEFAULT_STAR_DENSITY );
     }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * @param  paintArea
     *
     *    If null, component.getBounds() will be used.
     *********************************************************************/
     public void  setPaintArea ( Rectangle  paintArea )
     //////////////////////////////////////////////////////////////////////
     {
       useComponentBounds = ( paintArea == null );

       if ( useComponentBounds )
       {
         this.paintArea = new Rectangle ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       if ( useComponentBounds )
       {
         component.getBounds ( paintArea );

         if ( ( width  != paintArea.width  )
           || ( height != paintArea.height ) )
         {
           if ( image != null )
           {
             image.flush ( );

             image = null;
           }
         }
       }

       if ( image == null )
       {
         width  = paintArea.width;

         height = paintArea.height;

         createImage ( component );
       }

       graphics.drawImage ( image, paintArea.x, paintArea.y, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private void  createImage ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       image = component.createImage ( width, height );

       Graphics  graphics = image.getGraphics ( );
       
       graphics.setColor ( Color.BLACK );

       graphics.fillRect ( 0, 0, width, height );

       random.setSeed ( seed );

       graphics.setColor ( Color.WHITE );

       for ( int  x = 0; x < width; x++ )
       {
         for ( int  y = 0; y < height; y++ )
         {
           if ( random.nextDouble ( ) <= starDensity )
           {
             graphics.fillOval ( x, y, 1, 1 );
           }
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
