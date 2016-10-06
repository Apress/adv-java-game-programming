     package com.croftsoft.apps.mars.controller;

     import java.awt.*;
     import java.awt.event.*;

     import com.croftsoft.core.animation.animator.FrameRateAnimator;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Toggle the frame rate display.
     *
     * @version
     *   2003-04-11
     * @since
     *   2003-04-11
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  FrameRateController
       extends KeyAdapter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final FrameRateAnimator  frameRateAnimator;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  FrameRateController (
       FrameRateAnimator  frameRateAnimator,
       Component          component )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.frameRateAnimator = frameRateAnimator );

       component.addKeyListener ( this );

       component.requestFocus ( );
     }

     //////////////////////////////////////////////////////////////////////
     // interface KeyListener methods
     //////////////////////////////////////////////////////////////////////

     public void  keyPressed ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
       if ( keyEvent.getKeyCode ( ) == KeyEvent.VK_F )
       {
         frameRateAnimator.toggle ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
