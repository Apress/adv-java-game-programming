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
     * Roadrunner enemy Sprite.
     *
     * @version
     *   2003-09-10
     * @since
     *   2003-09-10
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  EnemySprite
       extends IconSprite
       implements Constants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Timekeeper  timekeeper;

     private final Rectangle   componentBounds;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  EnemySprite (
       Icon        enemyIcon,
       Timekeeper  timekeeper )
     //////////////////////////////////////////////////////////////////////
     {
       super ( enemyIcon );

       this.timekeeper = timekeeper;

       componentBounds = new Rectangle ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       double  timeDelta = timekeeper.getTimeDelta ( );

       if ( timeDelta > TIME_DELTA_MAX )
       {
         timeDelta = TIME_DELTA_MAX;
       }

       double  spaceDelta = timeDelta * ENEMY_VELOCITY;

       x += spaceDelta;

       y += spaceDelta;

       component.getBounds ( componentBounds );

       if ( ( x < componentBounds.x                         )
         || ( x > componentBounds.x + componentBounds.width ) )
       {
         x = 0;
       }

       if ( ( y < componentBounds.y                          )
         || ( y > componentBounds.y + componentBounds.height ) )
       {
         y = 0;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }