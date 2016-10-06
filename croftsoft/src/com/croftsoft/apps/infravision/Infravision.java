     package com.croftsoft.apps.infravision;

     import java.applet.Applet;
     import java.awt.Graphics;
     import java.awt.*;
     import java.awt.Color;
     import java.awt.Point;
     import java.awt.Rectangle;

     import com.croftsoft.core.gui.plot.PlotLib;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.math.RandomLib;

     /*********************************************************************
     * Goblins hunt kobolds in the dark using infravision.
     *
     * @version
     *   2002-03-02
     * @since
     *   1996-08-23
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  Infravision
       extends Applet
       implements Lifecycle, Runnable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     boolean  goblin_vision_on = false;

     private Point  lower_left  = new Point (   0,   0 );
     private Point  upper_right = new Point ( 100, 100 );

     int walls_border_count = 2 * ( upper_right.x - lower_left.x + 1 )
                            + 2 * ( upper_right.y - lower_left.y + 1 ) - 4;
     int walls_count     = walls_border_count + 100;
     int goblins_count   = 100;
     int kobolds_count   = 100;

     boolean [ ]  goblin_alive = new boolean [ goblins_count ];
     boolean [ ]  kobold_alive = new boolean [ kobolds_count ];

     int  goblins_alive_count = goblins_count;
     int  kobolds_alive_count = kobolds_count;

     Point [ ]  goblin_place = new Point [ goblins_count ];
     Point [ ]  kobold_place = new Point [ kobolds_count ];
     Point [ ]  wall_place   = new Point [ walls_count   ];

     Thread    runner;
     Image     offscreenImage;
     Graphics  offscreenGraphics;

     static final int  ETHER  = 0;
     static final int  WALL   = 1;
     static final int  GOBLIN = 2;
     static final int  KOBOLD = 3;

     private int    space_contents [ ] [ ]
       = new int [ upper_right.x - lower_left.x + 1 ]
                 [ upper_right.y - lower_left.y + 1 ];

     private Rectangle  r = new Rectangle ( );

     private Point  margin_top_left     = new Point ( 10, 10 );
     private Point  margin_bottom_right = new Point ( 20, 20 );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       r = new Rectangle ( margin_top_left.x, margin_top_left.y,
         getSize ( ).width  - margin_bottom_right.x,
         getSize ( ).height - margin_bottom_right.y );

       offscreenImage = createImage ( getSize ( ).width, getSize ( ).height );

       offscreenGraphics = offscreenImage.getGraphics ( );

       init_walls   ( );

       goblins_init ( );

       kobolds_init ( );
     }

     public void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       runner = new Thread ( this );

       int  priority = runner.getPriority ( ) - 1;

       if ( priority >= Thread.MIN_PRIORITY )
       {
         runner.setPriority ( priority );
       }

       runner.setDaemon ( true );

       runner.start ( );
     }

     public synchronized void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       Thread  thread = runner;

       if ( thread != null )
       {
         runner = null;

         thread.interrupt ( );
       }
     }

     public synchronized void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       stop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  run ( )
     //////////////////////////////////////////////////////////////////////
     {
       Thread  thread = Thread.currentThread ( );

       try
       {
         while ( thread == runner )
         {
           kobolds_move ( );

           goblins_move ( );

           paint ( offscreenGraphics );

           Graphics  g = this.getGraphics ( );

           g.drawImage ( offscreenImage, 0, 0, this );

           Thread.sleep ( 1000 );
         }
       }
       catch ( InterruptedException e )
       {
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Point  goblin_move_direction ( int  index_goblin ) {
     //////////////////////////////////////////////////////////////////////
       Point    direction         = new Point ( 0, 0 );
       Point    proposed_location = new Point ( 0, 0 );
       boolean  target_found      = false;
     //////////////////////////////////////////////////////////////////////
       if ( goblin_vision_on ) {
         outer_loop:
         for ( int index_x = -1;
                   index_x <= 1;
                   index_x++ ) {
           for ( int index_y = -1;
                     index_y <= 1;
                     index_y++ ) {
             proposed_location.x = goblin_place [ index_goblin ].x + index_x;
             proposed_location.y = goblin_place [ index_goblin ].y + index_y;
             if ( space_contents
                    [ proposed_location.x ]
                    [ proposed_location.y ] == KOBOLD ) {
               target_found = true;
               direction.x = index_x;
               direction.y = index_y;
               break outer_loop;
             }
           }
         }
       }
       if ( !target_found ) {
         direction.x = ( int ) RandomLib.roll ( 1, 3, -2 );
         direction.y = ( int ) RandomLib.roll ( 1, 3, -2 );
       }
       return new Point ( direction.x, direction.y );
     }

     public void  goblin_move ( int  index_goblin ) {
     //////////////////////////////////////////////////////////////////////
       boolean  abort_move = false;
       Point    move_direction;
       Point    new_place = new Point ( 0, 0 );
     //////////////////////////////////////////////////////////////////////
       new_place.x = goblin_place [ index_goblin ].x;
       new_place.y = goblin_place [ index_goblin ].y;
       move_direction = goblin_move_direction ( index_goblin );
       new_place.x += move_direction.x;
       new_place.y += move_direction.y;
       if ( new_place.x < lower_left.x  ) abort_move = true;
       if ( new_place.x > upper_right.x ) abort_move = true;
       if ( new_place.y < lower_left.y  ) abort_move = true;
       if ( new_place.y > upper_right.y ) abort_move = true;
       if ( !abort_move ) {
         if ( space_contents [ new_place.x ] [ new_place.y ] != ETHER ) {
           if ( space_contents [ new_place.x ] [ new_place.y ] == KOBOLD ) {
              kill_kobold ( new_place );
           } else abort_move = true;
         }
       }
       if ( !abort_move ) {
         space_contents
           [ goblin_place [ index_goblin ].x ]
           [ goblin_place [ index_goblin ].y ] = ETHER;
         goblin_place [ index_goblin ].x = new_place.x;
         goblin_place [ index_goblin ].y = new_place.y;
         space_contents [ new_place.x ] [ new_place.y ] = GOBLIN;
       }
     }

     public void  goblins_move ( ) {
     //////////////////////////////////////////////////////////////////////
       for ( int index_goblin = 0;
                 index_goblin < goblins_count;
                 index_goblin++ ) {
         if ( goblin_alive [ index_goblin ] ) {
           goblin_move ( index_goblin );
         }
       }
     }

     public Point  kobold_move_direction ( int  index_kobold ) {
     //////////////////////////////////////////////////////////////////////
       Point  direction = new Point ( 0, 0 );
     //////////////////////////////////////////////////////////////////////
       direction.x = ( int ) RandomLib.roll ( 1, 3, -2 );
       direction.y = ( int ) RandomLib.roll ( 1, 3, -2 );
       return new Point ( direction.x, direction.y );
     }

     public void  kobold_move ( int  index_kobold ) {
     //////////////////////////////////////////////////////////////////////
       boolean  abort_move = false;
       Point    move_direction;
       Point    new_place = new Point ( 0, 0 );
     //////////////////////////////////////////////////////////////////////
       new_place.x = kobold_place [ index_kobold ].x;
       new_place.y = kobold_place [ index_kobold ].y;
       move_direction = kobold_move_direction ( index_kobold );
       new_place.x += move_direction.x;
       new_place.y += move_direction.y;
       if ( new_place.x < lower_left.x  ) abort_move = true;
       if ( new_place.x > upper_right.x ) abort_move = true;
       if ( new_place.y < lower_left.y  ) abort_move = true;
       if ( new_place.y > upper_right.y ) abort_move = true;
       if ( !abort_move ) {
         if ( space_contents [ new_place.x ] [ new_place.y ] != ETHER ) {
           if ( space_contents [ new_place.x ] [ new_place.y ] == GOBLIN ) {
             kill_goblin ( new_place );
           } else abort_move = true;
         }
       }
       if ( !abort_move ) {
         space_contents
           [ kobold_place [ index_kobold ].x ]
           [ kobold_place [ index_kobold ].y ] = ETHER;
         kobold_place [ index_kobold ].x = new_place.x;
         kobold_place [ index_kobold ].y = new_place.y;
         space_contents [ new_place.x ] [ new_place.y ] = KOBOLD;
       }
     }

     public void  kobolds_move ( ) {
     //////////////////////////////////////////////////////////////////////
       for ( int index_kobold = 0;
                 index_kobold < kobolds_count;
                 index_kobold++ ) {
         if ( kobold_alive [ index_kobold ] ) {
           kobold_move ( index_kobold );
         }
       }
     }

     public void kill_goblin ( Point  goblin_Point ) {
     //////////////////////////////////////////////////////////////////////
       for ( int index_goblin = 0;
                 index_goblin < goblins_count;
                 index_goblin++ ) {
         if ( goblin_alive [ index_goblin ] ) {
           if ( ( goblin_place [ index_goblin ].x == goblin_Point.x )
             && ( goblin_place [ index_goblin ].y == goblin_Point.y ) ) {
             goblin_alive [ index_goblin ] = false;
             space_contents [ goblin_Point.x ] [ goblin_Point.y ] = ETHER;
             goblins_alive_count--;
             break;
           }
         }
       }
     }

     public void kill_kobold ( Point  kobold_Point ) {
     //////////////////////////////////////////////////////////////////////
       for ( int index_kobold = 0;
                 index_kobold < kobolds_count;
                 index_kobold++ ) {
         if ( kobold_alive [ index_kobold ] ) {
           if ( ( kobold_place [ index_kobold ].x == kobold_Point.x )
             && ( kobold_place [ index_kobold ].y == kobold_Point.y ) ) {
             kobold_alive [ index_kobold ] = false;
             space_contents [ kobold_Point.x ] [ kobold_Point.y ] = ETHER;
             kobolds_alive_count--;
             break;
           }
         }
       }
     }

     public void kobolds_init ( ) {
     //////////////////////////////////////////////////////////////////////
       for ( int index_kobold = 0;
                 index_kobold < kobolds_count;
                 index_kobold++ ) {
         do {
           kobold_place [ index_kobold ] = new Point (
             ( int ) RandomLib.roll (
               1, upper_right.x - lower_left.x + 1, -1 ),
             ( int ) RandomLib.roll (
               1, upper_right.y - lower_left.y + 1, -1 ) );
         } while ( space_contents
           [ kobold_place [ index_kobold ].x ]
           [ kobold_place [ index_kobold ].y ] != ETHER );
         space_contents
           [ kobold_place [ index_kobold ].x ]
           [ kobold_place [ index_kobold ].y ] = KOBOLD;
         kobold_alive [ index_kobold ] = true;
       }
     }

     public void goblins_init ( ) {
     //////////////////////////////////////////////////////////////////////
       for ( int index_goblin = 0;
                 index_goblin < goblins_count;
                 index_goblin++ ) {
         do {
           goblin_place [ index_goblin ] = new Point (
             ( int ) RandomLib.roll ( 1, upper_right.x - lower_left.x + 1, -1 ),
             ( int ) RandomLib.roll ( 1, upper_right.y - lower_left.y + 1, -1 ) );
         } while ( space_contents
           [ goblin_place [ index_goblin ].x ]
           [ goblin_place [ index_goblin ].y ] != ETHER );
         space_contents
           [ goblin_place [ index_goblin ].x ]
           [ goblin_place [ index_goblin ].y ] = GOBLIN;
         goblin_alive [ index_goblin ] = true;
       }
     }

     public void init_walls ( ) {
     //////////////////////////////////////////////////////////////////////
       int wall_length_x = upper_right.x - lower_left.x + 1;
       int wall_length_y = upper_right.y - lower_left.y - 1;
     //////////////////////////////////////////////////////////////////////
       for ( int index_wall = 0;
                 index_wall < wall_length_x;
                 index_wall++ ) {
         wall_place [ index_wall ]
           = new Point ( lower_left.x + index_wall, lower_left.y  );
         wall_place [ index_wall + wall_length_x ]
           = new Point ( lower_left.x + index_wall, upper_right.y );
         space_contents
           [ wall_place [ index_wall ].x ]
           [ wall_place [ index_wall ].y ] = WALL;
         space_contents
           [ wall_place [ index_wall + wall_length_x ].x ]
           [ wall_place [ index_wall + wall_length_x ].y ] = WALL;
       }
       for ( int index_wall = 0;
                 index_wall < wall_length_y;
                 index_wall++ ) {
         wall_place [ index_wall + 2 * wall_length_x ]
           = new Point ( lower_left.x, lower_left.y + 1 + index_wall );
         wall_place [ index_wall + 2 * wall_length_x + wall_length_y ]
           = new Point ( upper_right.x, lower_left.y + 1 + index_wall );
         space_contents
           [ wall_place [ index_wall + 2 * wall_length_x ].x ]
           [ wall_place [ index_wall + 2 * wall_length_x ].y ] = WALL;
         space_contents
           [ wall_place [ index_wall + 2 * wall_length_x + wall_length_y ].x ]
           [ wall_place [ index_wall + 2 * wall_length_x + wall_length_y ].y ] = WALL;
       }
       for ( int index_wall = walls_border_count;
                 index_wall < walls_count;
                 index_wall++ ) {
         do {
           wall_place [ index_wall ] = new Point (
             ( int ) RandomLib.roll ( 1, upper_right.x - lower_left.x + 1, -1 ),
             ( int ) RandomLib.roll ( 1, upper_right.y - lower_left.y + 1, -1 ) );
         } while ( space_contents
           [ wall_place [ index_wall ].x ]
           [ wall_place [ index_wall ].y ] != ETHER );
         space_contents
           [ wall_place [ index_wall ].x ]
           [ wall_place [ index_wall ].y ] = WALL;
       }
     }

     public boolean  mouseDown ( Event e, int x, int y ) {
     //////////////////////////////////////////////////////////////////////
       goblin_vision_on = !goblin_vision_on;
/*
       for ( int index_bug = 0;
                 index_bug < bugs_max;
                 index_bug++ ) {
         if ( bug_energy [ index_bug ] <= 0 ) {
           double  scale_x
             = r.width  / ( upper_right.x - lower_left.x + 1 );
           double  scale_y
             = r.height / ( upper_right.y - lower_left.y + 1 );
           bug_location [ index_bug ]
             = PlotLib.graphics_to_plot_transform (
             new Point ( x, y ), r, this.getGraphics ( ),
             lower_left.x, upper_right.x, lower_left.y, upper_right.y );
System.out.println ( "New critter at " + bug_location [ index_bug ].x
  + "," + bug_location [ index_bug ].y );
           bug_energy [ index_bug ] = baby_energy;
           for ( int index_gene = 0;
                     index_gene < genes_max;
                     index_gene++ ) {
             bug_genes_x [ index_bug ] [ index_gene ]
               = ( RandomLib.roll ( 1, 2, 0 ) == 1 );
             bug_genes_y [ index_bug ] [ index_gene ]
               = ( Dice.roll ( 1, 2, 0 ) == 1 );
           }
           break;
         }
       }
*/
       return true;
     }

     public void  paint ( Graphics g ) {
     //////////////////////////////////////////////////////////////////////
       g.setColor ( java.awt.Color.black );
       g.fillRect ( 0, 0, getSize ( ).width, getSize ( ).height );
       g.setColor ( java.awt.Color.white );
//       g.drawRect ( r.x, r.y, r.width, r.height );
       plot_kobolds ( r, g );
       plot_goblins ( r, g );
       plot_wall ( r, g );
       g.drawString (
         "Kobolds:  " + kobolds_alive_count + "    " +
         "Goblins:  " + goblins_alive_count + "    " +
         "Goblin vision on:  " + goblin_vision_on,
         r.x, r.y + r.height + 10 );
     }

     public void  plot_goblins ( Rectangle  r, Graphics  g ) {
     //////////////////////////////////////////////////////////////////////
       for ( int index_goblin = 0;
                 index_goblin < goblins_count;
                 index_goblin++ ) {
         if ( goblin_alive [ index_goblin ] ) {
           PlotLib.xy ( java.awt.Color.magenta,
             goblin_place [ index_goblin ].x,
             goblin_place [ index_goblin ].y,
             r, g,
             lower_left.x, upper_right.x, lower_left.y, upper_right.y,
             1, true );
         }
       }
     }

     public void  plot_kobolds ( Rectangle  r, Graphics  g ) {
     //////////////////////////////////////////////////////////////////////
       for ( int index_kobold = 0;
                 index_kobold < kobolds_count;
                 index_kobold++ ) {
         if ( kobold_alive [ index_kobold ] ) {
           PlotLib.xy ( java.awt.Color.green,
             kobold_place [ index_kobold ].x,
             kobold_place [ index_kobold ].y,
             r, g,
             lower_left.x, upper_right.x, lower_left.y, upper_right.y,
             1, true );
         }
       }
     }

     public void  plot_wall ( Rectangle  r, Graphics  g ) {
     //////////////////////////////////////////////////////////////////////
       for ( int index_wall = 0;
                 index_wall < walls_count;
                 index_wall++ ) {
         PlotLib.xy ( java.awt.Color.gray,
           wall_place [ index_wall ].x,
           wall_place [ index_wall ].y,
           r, g,
           lower_left.x, upper_right.x, lower_left.y, upper_right.y,
           1, true );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
