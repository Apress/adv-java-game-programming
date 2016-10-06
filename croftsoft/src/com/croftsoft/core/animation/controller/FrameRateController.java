     package com.croftsoft.core.animation.controller;

     import java.awt.*;
     import java.awt.event.*;

     import com.croftsoft.core.animation.animator.FrameRateAnimator;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Toggle the frame rate display.
     *
     * @version
     *   2003-08-02
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
       Component          component,
       FrameRateAnimator  frameRateAnimator )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.frameRateAnimator = frameRateAnimator );

       component.addKeyListener ( this );

       component.requestFocus ( );
     }

     public  FrameRateController ( Component  component )
     //////////////////////////////////////////////////////////////////////
     {
       this ( component, new FrameRateAnimator ( component ) );

       // initially off

       frameRateAnimator.toggle ( );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public FrameRateAnimator  getFrameRateAnimator ( )
     //////////////////////////////////////////////////////////////////////
     {
       return frameRateAnimator;
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