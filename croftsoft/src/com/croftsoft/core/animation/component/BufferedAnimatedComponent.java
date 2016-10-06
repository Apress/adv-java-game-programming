     package com.croftsoft.core.animation.component;

     import java.awt.EventQueue;
     import java.awt.Graphics;
     import java.awt.Graphics2D;
     import java.awt.Rectangle;
     import java.awt.event.ComponentAdapter;
     import java.awt.event.ComponentEvent;
     import java.awt.image.VolatileImage;
     import java.lang.reflect.InvocationTargetException;

     import com.croftsoft.core.animation.AnimatedComponent;
     import com.croftsoft.core.animation.AnimationFactory;
     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.animation.RepaintCollector;
     import com.croftsoft.core.animation.factory.DefaultAnimationFactory;
     import com.croftsoft.core.awt.image.NullVolatileImage;
     import com.croftsoft.core.util.ArrayLib;
     import com.croftsoft.core.util.loop.LoopGovernor;

     /*********************************************************************
     * Triple-buffered animated Swing component.
     *
     * @version
     *   2003-07-23
     * @since
     *   2002-03-04
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  BufferedAnimatedComponent
       extends AnimatedComponent
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private boolean        doReset;

     private VolatileImage  activeImage;

     private VolatileImage  updateImage;

     private Graphics2D     activeGraphics;

     private Graphics2D     updateGraphics;

     private int            oldCount;

     private Rectangle [ ]  oldRepaintRegions;

     private Rectangle [ ]  newRepaintRegions;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *********************************************************************/
     public  BufferedAnimatedComponent (
       ComponentAnimator  componentAnimator,
       RepaintCollector   repaintCollector,
       LoopGovernor       loopGovernor )
     //////////////////////////////////////////////////////////////////////
     {
       super (
         componentAnimator,
         repaintCollector,
         loopGovernor );

       setDoubleBuffered ( false );

       activeImage = NullVolatileImage.INSTANCE;
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * @param  frequency
     *
     *   The targeted update frequency in loops per second.
     *********************************************************************/
     public  BufferedAnimatedComponent (
       ComponentAnimator  componentAnimator,
       AnimationFactory   animationFactory,
       double             frequency )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         componentAnimator,
         animationFactory.createRepaintCollector ( ),
         animationFactory.createLoopGovernor ( frequency ) );
     }

     /*********************************************************************
     * Convenience constructor.
     *********************************************************************/
     public  BufferedAnimatedComponent (
       ComponentAnimator  componentAnimator,
       AnimationFactory   animationFactory )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         componentAnimator,
         animationFactory.createRepaintCollector ( ),
         animationFactory.createLoopGovernor ( ) );
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * @param  frequency
     *
     *   The targeted update frequency in loops per second.
     *********************************************************************/
     public  BufferedAnimatedComponent (
       ComponentAnimator  componentAnimator,
       double             frequency )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         componentAnimator,
         DefaultAnimationFactory.INSTANCE,
         frequency );
     }

     /*********************************************************************
     * Convenience constructor.
     *********************************************************************/
     public  BufferedAnimatedComponent (
       ComponentAnimator  componentAnimator )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         componentAnimator,
         DefaultAnimationFactory.INSTANCE );
     }

     //////////////////////////////////////////////////////////////////////
     // interface Lifecycle methods
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       super.init ( );

       addComponentListener (
         new ComponentAdapter ( )
         {
           public void  componentResized ( ComponentEvent  componentEvent )
           {
             doReset = true;
           }
         } );

       oldRepaintRegions = new Rectangle [ ] { };

       newRepaintRegions = new Rectangle [ ] { };
     }

     //////////////////////////////////////////////////////////////////////
     // overridden JComponent methods
     //////////////////////////////////////////////////////////////////////

     public void  paintComponent ( Graphics  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       graphics.drawImage ( activeImage, 0, 0, null );
     }

     //////////////////////////////////////////////////////////////////////
     // protected methods
     //////////////////////////////////////////////////////////////////////

     protected void  loop ( )
     //////////////////////////////////////////////////////////////////////
     {
       doReset = true;

       while ( animationThread != null )
       {
         try
         {
           animateOffscreen ( );

           if ( doReset )
           {
             continue;
           }

           EventQueue.invokeAndWait ( animationRunner );

           loopGovernor.govern ( );

           if ( stopRequested )
           {
             synchronized ( this )
             {
               while ( stopRequested )
               {
                 wait ( );
               }
             }
           }
         }
         catch ( InterruptedException  ex )
         {
         }
         catch ( InvocationTargetException  ex )
         {
           ex.getCause ( ).printStackTrace ( );
         }
       }

       if ( activeImage != null )
       {
         activeImage.flush ( );

         activeImage = null;
       }

       if ( updateImage != null )
       {
         updateImage.flush ( );

         updateImage = null;
       }

       if ( updateGraphics != null )
       {
         updateGraphics.dispose ( );

         updateGraphics = null;
       }

       if ( activeGraphics != null )
       {
         activeGraphics.dispose ( );

         activeGraphics = null;
       }
     }

     protected void  animateOffscreen ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( doReset )
       {
         if ( activeGraphics != null )
         {
           activeGraphics.dispose ( );
         }

         if ( updateGraphics != null )
         {
           updateGraphics.dispose ( );
         }

         if ( updateImage != null )
         {
           updateImage.flush ( );
         }

         int  width  = getWidth  ( );

         int  height = getHeight ( );

         VolatileImage  oldActiveImage = activeImage;

         VolatileImage  newActiveImage
           = createVolatileImage ( width, height );

         if ( newActiveImage == null )
         {
           return;
         }

         activeGraphics = newActiveImage.createGraphics ( );

         if ( oldActiveImage != null )
         {
           activeGraphics.drawImage ( oldActiveImage, 0, 0, null );

           oldActiveImage.flush ( );
         }
   
         activeImage = newActiveImage;

         updateImage = createVolatileImage ( width, height );

         if ( updateImage == null )
         {
           return;
         }

         updateGraphics = updateImage.createGraphics ( );

         activeGraphics.setFont ( getFont ( ) );

         updateGraphics.setFont ( getFont ( ) );

         repaintCollector.repaint ( );

         doReset = false;
       }

       if ( stopRequested || animationThread == null )
       {
         return;
       }

       componentAnimator.update ( this );

       int  count = repaintCollector.getCount ( );

       Rectangle [ ]  repaintRegions
         = repaintCollector.getRepaintRegions ( );

       for ( int  i = 0; i < count; i++ )
       {
         if ( i == newRepaintRegions.length )
         {
           newRepaintRegions = ( Rectangle [ ] ) ArrayLib.append (
             newRepaintRegions, new Rectangle ( repaintRegions [ i ] ) );
         }
         else
         {
           newRepaintRegions [ i ].setBounds ( repaintRegions [ i ] );
         }
       }

       for ( int  i = 0; i < oldCount; i++ )
       {
         Rectangle  oldRepaintRegion = oldRepaintRegions [ i ];

         repaintCollector.repaint (
           oldRepaintRegion.x,
           oldRepaintRegion.y,
           oldRepaintRegion.width,
           oldRepaintRegion.height );
       }

       oldCount = count;

       Rectangle [ ]  tempRepaintRegions = oldRepaintRegions;

       oldRepaintRegions = newRepaintRegions;

       newRepaintRegions = tempRepaintRegions;

       count = repaintCollector.getCount ( );

       repaintRegions = repaintCollector.getRepaintRegions ( );

       for ( int  i = 0; i < count; i++ )
       {
         if ( doReset || stopRequested || animationThread == null )
         {
           return;
         }

         updateGraphics.setClip ( repaintRegions [ i ] );

         componentAnimator.paint ( this, updateGraphics );
       }

       if ( updateImage.contentsLost ( ) )
       {
         doReset = true;
       }
     }

     protected void  animate ( )
     //////////////////////////////////////////////////////////////////////
     {
       VolatileImage  tempImage = activeImage;

       activeImage = updateImage;

       updateImage = tempImage;

       Graphics2D  tempGraphics = activeGraphics;

       activeGraphics = updateGraphics;

       updateGraphics = tempGraphics;

       int  count = repaintCollector.getCount ( );

       Rectangle [ ]  repaintRegions
         = repaintCollector.getRepaintRegions ( );

       for ( int  i = 0; i < count; i++ )
       {
         paintImmediately ( repaintRegions [ i ] );
       }

       repaintCollector.reset ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
