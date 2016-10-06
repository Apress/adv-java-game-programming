     package com.croftsoft.core.animation.factory;

     import com.croftsoft.core.animation.AnimationFactory;
     import com.croftsoft.core.animation.RepaintCollector;
     import com.croftsoft.core.animation.collector.*;
     import com.croftsoft.core.util.loop.LoopGovernor;
     import com.croftsoft.core.util.loop.WindowedLoopGovernor;

     /*********************************************************************
     * Creates the default animation objects.
     *
     * @version
     *   2003-11-08
     * @since
     *   2002-03-09
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  DefaultAnimationFactory
       implements AnimationFactory
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final DefaultAnimationFactory  INSTANCE
       = new DefaultAnimationFactory ( );

     public static final double  DEFAULT_FRAME_RATE = 85.0;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public RepaintCollector  createRepaintCollector ( )
     //////////////////////////////////////////////////////////////////////
     {
       return new CoalescingRepaintCollector ( );
     }

     public LoopGovernor  createLoopGovernor ( double  frequency )
     //////////////////////////////////////////////////////////////////////
     {
       return new WindowedLoopGovernor ( frequency );
     }

     public LoopGovernor  createLoopGovernor ( )
     //////////////////////////////////////////////////////////////////////
     {
       return new WindowedLoopGovernor ( DEFAULT_FRAME_RATE );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  DefaultAnimationFactory ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
