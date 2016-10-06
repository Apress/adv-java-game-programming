     package com.croftsoft.apps.road;
     
     import com.croftsoft.core.animation.clock.Timekeeper;
     import com.croftsoft.core.animation.sprite.IconSprite;

     import java.applet.*;
     import java.awt.*;
     import java.awt.event.*;
     import java.awt.geom.*;
     import java.io.*;
     import java.net.URL;
     import java.util.*;
     import javax.swing.*;
     import javax.swing.event.*;

     /*********************************************************************
     * Roadrunner Sprite.
     *
     * @version
     *   2003-09-10
     * @since
     *   2003-08-05
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  RunnerSprite
       extends IconSprite
       implements Constants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Timekeeper  timekeeper;

     //

     private Point  mousePoint;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  RunnerSprite (
       Icon        runnerIcon,
       Timekeeper  timekeeper,
       Component   component )
     //////////////////////////////////////////////////////////////////////
     {
       super ( runnerIcon );

       this.timekeeper = timekeeper;

       // initialize mouse input

       component.addMouseMotionListener (
         new MouseMotionAdapter ( )
         {
           public void  mouseMoved ( MouseEvent  mouseEvent )
           {
             mousePoint = mouseEvent.getPoint ( );
           }
         } );

       component.addMouseListener (
         new MouseAdapter ( )
         {
           public void  mouseExited ( MouseEvent  mouseEvent )
           {
             mousePoint = null;
           }
         } );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       double  timeDelta = timekeeper.getTimeDelta ( );

       if ( mousePoint != null )
       {
         double  deltaX = mousePoint.x - x - TILE_SIZE / 2;

         double  deltaY = mousePoint.y - y - TILE_SIZE / 2;

         if ( timeDelta > TIME_DELTA_MAX )
         {
           timeDelta = TIME_DELTA_MAX;
         }

         double  spaceDelta = timeDelta * RUNNER_VELOCITY;

         if ( deltaX > 0 )
         {
           x = spaceDelta >  deltaX ? x + deltaX : x + spaceDelta;
         }
         else if ( deltaX < 0 )
         {
           x = spaceDelta > -deltaX ? x + deltaX : x - spaceDelta;
         }

         if ( deltaY > 0 )
         {
           y = spaceDelta >  deltaY ? y + deltaY : y + spaceDelta;
         }
         else if ( deltaY < 0 )
         {
           y = spaceDelta > -deltaY ? y + deltaY : y - spaceDelta;
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }