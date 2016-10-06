     package com.croftsoft.apps.mars.controller;

     import java.awt.*;
     import java.awt.event.*;

     import com.croftsoft.core.animation.clock.Timekeeper;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Adjusts the time factor.
     *
     * @version
     *   2003-04-18
     * @since
     *   2003-04-02
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  TimeController
       extends KeyAdapter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Timekeeper  timekeeper;

     private final double      timeFactorDefault;

     private final double      timeFactorDelta;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  TimeController (
       Timekeeper  timekeeper,
       double      timeFactorDefault,
       double      timeFactorDelta,
       Component   component )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.timekeeper = timekeeper );

       this.timeFactorDefault = timeFactorDefault;

       this.timeFactorDelta   = timeFactorDelta;

       component.addKeyListener ( this );

       component.requestFocus ( );
     }

     //////////////////////////////////////////////////////////////////////
     // interface KeyListener methods
     //////////////////////////////////////////////////////////////////////

     public void  keyPressed ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
       char  keyChar = keyEvent.getKeyChar ( );

       if ( ( keyChar == '+' )
         || ( keyChar == '=' )
         || ( keyChar == '-' ) )
       {
         double  timeFactor = timekeeper.getTimeFactor ( );

         if ( keyChar == '+' )
         {
           timeFactor += timeFactorDelta;
         }
         else if ( keyChar == '-' )
         {
           timeFactor -= timeFactorDelta;
         }
         else
         {
           timeFactor = timeFactorDefault;
         }

         timekeeper.setTimeFactor ( timeFactor );

         System.out.println ( "Time Factor:  " + timeFactor );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
