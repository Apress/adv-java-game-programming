     package com.croftsoft.core.animation;

     import com.croftsoft.core.util.loop.LoopGovernor;

     /*********************************************************************
     * Creates animation objects.
     *
     * @version
     *   2003-11-08
     * @since
     *   2002-03-09
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  AnimationFactory
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public RepaintCollector  createRepaintCollector ( );

     public LoopGovernor      createLoopGovernor ( double  frequency );

     public LoopGovernor      createLoopGovernor ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }