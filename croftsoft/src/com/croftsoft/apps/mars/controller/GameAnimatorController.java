     package com.croftsoft.apps.mars.controller;

     import java.awt.*;
     import java.awt.event.*;

     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.apps.mars.view.GameAnimator;

     /*********************************************************************
     * Toggles planned path display.
     *
     * @version
     *   2003-04-30
     * @since
     *   2003-04-30
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  GameAnimatorController
       extends KeyAdapter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final GameAnimator  gameAnimator;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  GameAnimatorController (
       GameAnimator  gameAnimator,
       Component     component )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.gameAnimator = gameAnimator );

       component.addKeyListener ( this );

       component.requestFocus ( );
     }

     //////////////////////////////////////////////////////////////////////
     // interface KeyListener methods
     //////////////////////////////////////////////////////////////////////

     public void  keyPressed ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
       if ( keyEvent.getKeyCode ( ) == KeyEvent.VK_P )
       {
         gameAnimator.togglePathAnimator ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }