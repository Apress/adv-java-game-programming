     package com.croftsoft.core.animation;

     import java.awt.EventQueue;
     import java.awt.Graphics;
     import java.awt.Graphics2D;
     import java.awt.Rectangle;
     import java.lang.reflect.InvocationTargetException;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.factory.DefaultAnimationFactory;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.util.loop.LoopGovernor;

     /*********************************************************************
     * Animated Swing component.
     *
     * @version
     *   2003-07-23
     * @since
     *   2002-03-10
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  AnimatedComponent
       extends JComponent
       implements Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  ANIMATION_THREAD_NAME = "Animation";

     //

     protected final Runnable  animationRunner;

     //

     protected ComponentAnimator  componentAnimator;

     protected RepaintCollector   repaintCollector;

     protected LoopGovernor       loopGovernor;

     protected Thread             animationThread;

     protected boolean            stopRequested;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *********************************************************************/
     public  AnimatedComponent (
       ComponentAnimator  componentAnimator,
       RepaintCollector   repaintCollector,
       LoopGovernor       loopGovernor )
     //////////////////////////////////////////////////////////////////////
     {
       setComponentAnimator ( componentAnimator );

       setRepaintCollector ( repaintCollector );

       setLoopGovernor ( loopGovernor );

       setOpaque ( true );

       animationRunner =
         new Runnable ( )
         {
           public void  run ( )
           {
             animate ( );
           }
         };         
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * @param  frequency
     *
     *   The targeted update frequency in loops per second.
     *********************************************************************/
     public  AnimatedComponent (
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
     public  AnimatedComponent (
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
     public  AnimatedComponent (
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
     public  AnimatedComponent ( ComponentAnimator  componentAnimator )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         componentAnimator,
         DefaultAnimationFactory.INSTANCE );
     }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public synchronized ComponentAnimator  setComponentAnimator (
       ComponentAnimator  componentAnimator )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( componentAnimator );

       ComponentAnimator  oldComponentAnimator = this.componentAnimator;

       this.componentAnimator = componentAnimator;

       return oldComponentAnimator;
     }

     public synchronized RepaintCollector  setRepaintCollector (
       RepaintCollector  repaintCollector )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( repaintCollector );

       RepaintCollector  oldRepaintCollector = this.repaintCollector;

       this.repaintCollector = repaintCollector;

       return oldRepaintCollector;
     }

     public synchronized LoopGovernor  setLoopGovernor (
       LoopGovernor  loopGovernor )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( loopGovernor );

       LoopGovernor  oldLoopGovernor = this.loopGovernor;

       this.loopGovernor = loopGovernor;

       return oldLoopGovernor;
     }

     //////////////////////////////////////////////////////////////////////
     // interface Lifecycle methods
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public synchronized void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       stopRequested = false;

       if ( animationThread == null )
       {
         animationThread = new Thread (
           new Runnable ( )
           {
             public void  run ( )
             {
               loop ( );
             }
           },
           ANIMATION_THREAD_NAME );

         animationThread.setPriority ( Thread.MIN_PRIORITY );

         animationThread.setDaemon ( true );

         animationThread.start ( );
       }
       else
       {
         notify ( );
       }
     }

     public synchronized void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       stopRequested = true;

       animationThread.interrupt ( );
     }

     public synchronized void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       animationThread = null;

       stopRequested = false;

       notify ( );
     }

     //////////////////////////////////////////////////////////////////////
     // overridden JComponent methods
     //////////////////////////////////////////////////////////////////////

     public void  paintComponent ( Graphics  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       componentAnimator.paint ( this, ( Graphics2D ) graphics );
     }

     public void  repaint ( )
     //////////////////////////////////////////////////////////////////////
     {
       repaintCollector.repaint ( );
     }

     public void  repaint ( long  tm )
     //////////////////////////////////////////////////////////////////////
     {
       repaintCollector.repaint ( );
     }

     public void  repaint (
       int  x,
       int  y,
       int  width,
       int  height )
     //////////////////////////////////////////////////////////////////////
     {
       repaintCollector.repaint ( x, y, width, height );
     }

     public void  repaint (
       long  tm,
       int   x,
       int   y,
       int   width,
       int   height )
     //////////////////////////////////////////////////////////////////////
     {
       repaintCollector.repaint ( x, y, width, height );
     }

     public void  repaint ( Rectangle  r )
     //////////////////////////////////////////////////////////////////////
     {
       repaintCollector.repaint ( r.x, r.y, r.width, r.height );
     }

     //////////////////////////////////////////////////////////////////////
     // protected methods
     //////////////////////////////////////////////////////////////////////

     protected void  loop ( )
     //////////////////////////////////////////////////////////////////////
     {
       while ( animationThread != null )
       {
         try
         {
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
     }

     protected void  animate ( )
     //////////////////////////////////////////////////////////////////////
     {
       componentAnimator.update ( this );

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
