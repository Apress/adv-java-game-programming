     package com.croftsoft.core.animation.component;

     import java.awt.Graphics2D;
     import java.awt.Rectangle;
     import java.awt.geom.Rectangle2D;
     import java.awt.image.BufferStrategy;

     import com.croftsoft.core.animation.AnimatedComponent;
     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.animation.RepaintCollector;
     import com.croftsoft.core.animation.factory.DefaultAnimationFactory;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.util.loop.FixedDelayLoopGovernor;
     import com.croftsoft.core.util.loop.LoopGovernor;

     /*********************************************************************
     * AnimatedComponent subclass that uses a BufferStrategy.
     *
     * @version
     *   2003-07-24
     * @since
     *   2002-03-08
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  BufferStrategyAnimatedComponent
       extends AnimatedComponent
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected final BufferStrategy  bufferStrategy;

     //

     private final Rectangle  componentBounds;

     private final Rectangle  clipBounds;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *********************************************************************/
     public  BufferStrategyAnimatedComponent (
       ComponentAnimator  componentAnimator,
       RepaintCollector   repaintCollector,
       LoopGovernor       loopGovernor,
       BufferStrategy     bufferStrategy )
     //////////////////////////////////////////////////////////////////////
     {
       super ( componentAnimator, repaintCollector, loopGovernor );

       NullArgumentException.check (
         this.bufferStrategy = bufferStrategy );

       componentBounds = new Rectangle ( );

       clipBounds      = new Rectangle ( );
     }

     public  BufferStrategyAnimatedComponent (
       ComponentAnimator  componentAnimator,
       BufferStrategy     bufferStrategy )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         componentAnimator,
         DefaultAnimationFactory.INSTANCE.createRepaintCollector ( ),
         new FixedDelayLoopGovernor ( 0, 0 ),
         bufferStrategy );
     }

     //////////////////////////////////////////////////////////////////////
     // protected methods
     //////////////////////////////////////////////////////////////////////

     protected void  animate ( )
     //////////////////////////////////////////////////////////////////////
     {
       componentAnimator.update ( this );

       int  count = repaintCollector.getCount ( );

       Rectangle [ ]  repaintRegions
         = repaintCollector.getRepaintRegions ( );

       getBounds ( componentBounds );

       Graphics2D  graphics2D
         = ( Graphics2D ) bufferStrategy.getDrawGraphics ( );

       for ( int  i = 0; i < count; i++ )
       {
         Rectangle  repaintRegion = repaintRegions [ i ];

         if ( !componentBounds.intersects ( repaintRegion ) )
         {
           continue;
         }

         Rectangle2D.intersect (
           componentBounds, repaintRegion, clipBounds );

         graphics2D.setClip ( clipBounds );

         componentAnimator.paint ( this, graphics2D );
       }

       bufferStrategy.show ( );

       graphics2D.dispose ( );

       repaintCollector.reset ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }