     package com.croftsoft.apps.dice;

     import java.awt.Graphics;
     import java.awt.*;
     import java.util.*;
     import java.lang.Math;
     import java.awt.Color;
     import java.awt.Point;
     import java.awt.Rectangle;
     import java.util.*;
     import javax.swing.*;

     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.animation.*;
     import com.croftsoft.core.animation.component.*;

     /*********************************************************************
     * Artificial neural network perceptron demonstration.
     *
     * @version
     *   2002-03-23
     * @since
     *   1996-08-23
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  Dice
       extends JApplet
       implements Lifecycle, ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  VERSION = "2002-03-13";

     private static final String  APPLET_TITLE
       = "Dice";

     private static final String  INFO
       = "\n" + APPLET_TITLE + "\n"
       + "Copyright 2002 CroftSoft Inc\n"
       + "http://www.croftsoft.com/\n"
       + "Version " + VERSION + "\n";

     private static final int  FRAME_RATE = 24;

     private static final int  text_x = 10; 
     private static final int  text_y = 20;  // distance between lines

     //

     private AnimatedComponent  animatedComponent;

     private Rectangle          bounds = new Rectangle ( );

     //

     private int start_fights = 2000;
     private int fights_max   = 30;

     private Random  random = new Random ( );

     private long damage;
     private long count;
     private long sum;
     private double mean;

     private NPC npc1 = new NPC ( "Combatant_1" );
     private NPC npc2 = new NPC ( "Combatant_2" );

     private int fights = 0;
     private int fights_finished = 0;
     private long round_num = 0;

     private long hp_max = 100;

     private Point    fight_history   [ ] = new Point   [ fights_max ];
     private boolean  winloss_history [ ] = new boolean [ fights_max ];

     private Rectangle  r = new Rectangle ( text_x, 8 * text_y, 200, 200 );

     private double   w_ac          = 100.0;
     private double   w_hp          = 20.0;
     private double   bias          = 1000.0;
     private double   w_bias        = -50.0 * 20.0 / bias;
     private double   learn_rate    = 1.0;
//   private double   win_prob      = 0.5;
     private boolean  win_predicted = true;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getAppletInfo ( ) { return INFO; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( INFO );

       animatedComponent = new AnimatedComponent ( this, FRAME_RATE );

       animatedComponent.init ( );

       setContentPane ( animatedComponent );

       npc2.combat_stats.ac = 0;

       npc2.combat_stats.hp = 50;

       update_fight_history ( npc2.combat_stats.ac, npc2.combat_stats.hp );
     }

     public synchronized void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       animatedComponent.start ( );
     }

     public synchronized void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       animatedComponent.stop ( );
     }

     public synchronized void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       animatedComponent.destroy ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       fight ( );

       component.repaint ( );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       component.getBounds ( bounds );

       graphics.setColor ( Color.white );

       graphics.fillRect (
         bounds.x, bounds.y, bounds.width, bounds.height );

       graphics.setColor ( Color.black );

       graphics.drawString ( "Round " + round_num, text_x,     text_y );

       graphics.drawString ( npc1.toString ( )   , text_x, 2 * text_y );

       graphics.drawString ( npc2.toString ( )   , text_x, 3 * text_y );

       graphics.drawString (
         "Y = W_AC * AC + W_HP * HP + W_BIAS * BIAS", text_x, 4 * text_y );

       double  weighted_sum = w_ac * fight_history [ fights - 1 ].x
         + w_hp * fight_history [ fights - 1 ].y + w_bias * bias;

       graphics.drawString ( "  = " + w_ac + " * " + fight_history [ fights - 1 ].x
         + " + " + w_hp + " * " + fight_history [ fights - 1 ].y
         + " + " + w_bias + " * " + bias + " = " + weighted_sum, text_x, 5 * text_y );

       if ( round_num == 0 )
       {
         graphics.drawString ( "Combat begins.", text_x, 6 * text_y );
       }
       else if ( ( npc1.combat_stats.hp > 0 ) && ( npc2.combat_stats.hp <= 0 ) )
       {
         graphics.drawString ( npc1.name + " wins!", text_x, 6 * text_y );
       }
       else if ( ( npc2.combat_stats.hp > 0 ) && ( npc1.combat_stats.hp <= 0 ) )
       {
         graphics.drawString ( npc2.name + " wins!", text_x, 6 * text_y );
       }
       else
       {
         graphics.drawString ( "Combat continues.", text_x, 6 * text_y );
       }

       graphics.drawString ( "Win predicted:  " + win_predicted, text_x, 7 * text_y );

//     graphics.drawString ( "Win confidence:  " + win_prob, text_x, 7 * text_y );

       graphics.drawRect ( r.x, r.y, r.width, r.height );

       plot_Line ( -w_ac / w_hp, -w_bias * bias / w_hp,
         r, graphics, -10, 10, 0, hp_max );

       plot_ac_hp ( r, graphics );
     }

     public void update_fight_history ( long ac, long hp )
     //////////////////////////////////////////////////////////////////////
     {
       if ( fights == fights_max ) {
         for ( int index_fight = 0; index_fight < fights - 1; index_fight++ ) {
           fight_history [ index_fight ] = fight_history [ index_fight + 1 ];
           winloss_history [ index_fight ]
             = winloss_history [ index_fight + 1 ];
         }
       } else {
         fights++;
       }
       fight_history [ fights - 1 ] = new Point ( ( int ) ac, ( int ) hp );
     }

     public void fight ( ) {
     //////////////////////////////////////////////////////////////////////
       if ( ( npc1.combat_stats.hp > 0 ) && ( npc2.combat_stats.hp <= 0 ) ) {
         update_winloss_history ( true );
//         paint_buffered ( );
         npc1 = new NPC ( "Combatant_1", npc1.xp + 1 );
         npc2 = new NPC ( "Combatant_2", npc2.xp );
         prep_next_fight ( );
       } else if ( ( npc2.combat_stats.hp > 0 ) && ( npc1.combat_stats.hp <= 0 ) ) {
         update_winloss_history ( false );
//         paint_buffered ( );
         npc1 = new NPC ( "Combatant_1", npc1.xp );
         npc2 = new NPC ( "Combatant_2", npc2.xp + 1 );
         prep_next_fight ( );
       } else if ( round_num >= 0 ) {
         round ( npc1.combat_stats, npc2.combat_stats );
       }
       round_num++;
/*
       if ( round_num >= 0 ) {
         paint_buffered ( );
       }
*/
     }

     public boolean attacker_hits (
       long attacker_adjusted_thac0,
       long defender_adjusted_AC ) {
     //////////////////////////////////////////////////////////////////////
       long to_hit_roll = roll ( 1, 20, 0 );
       if ( to_hit_roll == 20 ) return true;
       if ( attacker_adjusted_thac0 - to_hit_roll <= defender_adjusted_AC )
         return true;
       return false;
     }

     public long attack (
       Combat_Stats  attacker_Combat_Stats,
       Combat_Stats  defender_Combat_Stats ) {
     //////////////////////////////////////////////////////////////////////
     // Returns the damage for the attack (0 if a miss).
     //////////////////////////////////////////////////////////////////////
       if ( !attacker_hits (
         attacker_Combat_Stats.thac0,
         defender_Combat_Stats.ac ) ) return 0;
       return roll ( attacker_Combat_Stats.damage_multiplier,
                     attacker_Combat_Stats.damage_base,
                     attacker_Combat_Stats.damage_bonus );
     }

     public void plot_ac_hp ( Rectangle  r, Graphics  g ) {
     //////////////////////////////////////////////////////////////////////
       Color  winloss_color;
     //////////////////////////////////////////////////////////////////////
       for ( int index_fight = 0; index_fight < fights - 1; index_fight++ ) {
         if ( winloss_history [ index_fight ] )
           winloss_color = java.awt.Color.green;
         else
           winloss_color = java.awt.Color.red;
         plot_xy ( winloss_color,
           ( double ) fight_history [ index_fight ].x,
           ( double ) fight_history [ index_fight ].y,
           r, g, -10, 10, 0, hp_max );
       }
       plot_xy ( java.awt.Color.blue,
           ( double ) fight_history [ fights - 1 ].x,
           ( double ) fight_history [ fights - 1 ].y,
           r, g, -10, 10, 1, 100 );
     }

     public void  plot_Line (
       double     m,
       double     b,
       Rectangle  r,
       Graphics   g,
       double     x0,
       double     x1,
       double     y0,
       double     y1 ) {
     //////////////////////////////////////////////////////////////////////
       double  x_scale = ( double ) r.width  / ( double ) ( x1 - x0 );
       double  y_scale = ( double ) r.height / ( double ) ( y1 - y0 );
     //////////////////////////////////////////////////////////////////////
       g.drawLine ( r.x,
         r.y + r.height - ( int ) ( ( m * x0 + b - y0 ) * y_scale ),
         r.x + r.width ,
         r.y + r.height - ( int ) ( ( m * x1 + b - y0 ) * y_scale ) );
     }

     public void  plot_xy (
       Color      c,
       double     x,
       double     y,
       Rectangle  r,
       Graphics   g,
       double     x0,
       double     x1,
       double     y0,
       double     y1 ) {
     //////////////////////////////////////////////////////////////////////
       double  x_scale = ( double ) r.width  / ( double ) ( x1 - x0 );
       double  y_scale = ( double ) r.height / ( double ) ( y1 - y0 );
       double  oval_size = ( x_scale < y_scale ) ? x_scale : y_scale;
       oval_size = ( oval_size > 4 ) ? oval_size : 4;

       Color  c_old = g.getColor ( );
       g.setColor ( c );
       g.fillOval ( r.x + ( int ) ( ( x - x0 ) * x_scale ),
         r.y + r.height - ( int ) ( ( y - y0 ) * y_scale ),
         ( int ) oval_size, ( int ) oval_size );
       g.setColor ( c_old );
     }

     public void  prep_next_fight ( ) {
     //////////////////////////////////////////////////////////////////////
       npc2.combat_stats.ac = roll ( 1, 21, -11 );
       npc2.combat_stats.hp = roll ( 1, hp_max, 0  );
       update_fight_history ( npc2.combat_stats.ac, npc2.combat_stats.hp );
//       win_prob = sigmoid ( ( w_ac * npc2.combat_stats.ac
//         + w_hp * npc2.combat_stats.hp + w_bias * bias ) / 1000.0 );
       if ( w_ac * npc2.combat_stats.ac
         + w_hp * npc2.combat_stats.hp + w_bias * bias >= 0.0 )
         win_predicted = true;
       else
         win_predicted = false;
       round_num = -1;
     }

     public long roll (
       long multiplier,
       long base,
       long offset ) {
     //////////////////////////////////////////////////////////////////////
       long temp = 0;
     //////////////////////////////////////////////////////////////////////
       for ( long index_roll = 0; index_roll < multiplier; index_roll++ ) {
         temp += 1 + Math.round (
           ( double ) ( base - 1 ) * random.nextDouble ( ) );
       }
       return temp + offset;
     }

     public boolean round (
       Combat_Stats combatant_1_Combat_Stats,
       Combat_Stats combatant_2_Combat_Stats ) {
     //////////////////////////////////////////////////////////////////////
     // Initiative 50%/50% chance for each.
     // Returns true if both combatants are still up.
     //////////////////////////////////////////////////////////////////////
       if ( roll ( 1, 2, 0 ) == 1 ) {
         if ( combatant_1_Combat_Stats.hp > 0 ) {
           combatant_2_Combat_Stats.hp
             -= attack ( combatant_1_Combat_Stats, combatant_2_Combat_Stats );
         }
         if ( combatant_2_Combat_Stats.hp > 0 ) {
           combatant_1_Combat_Stats.hp
             -= attack ( combatant_2_Combat_Stats, combatant_1_Combat_Stats );
         }
       } else {
         if ( combatant_2_Combat_Stats.hp > 0 ) {
           combatant_1_Combat_Stats.hp
             -= attack ( combatant_2_Combat_Stats, combatant_1_Combat_Stats );
         }
         if ( combatant_1_Combat_Stats.hp > 0 ) {
           combatant_2_Combat_Stats.hp
             -= attack ( combatant_1_Combat_Stats, combatant_2_Combat_Stats );
         }
       }
       return ( ( combatant_1_Combat_Stats.hp > 0 )
             && ( combatant_2_Combat_Stats.hp > 0 ) );
     }

//     public double  sigmoid ( double  a ) {
//     //////////////////////////////////////////////////////////////////////
//       return 1.0 / ( 1.0 + java.lang.Math.exp ( -a ) );
//     }

     public void update_winloss_history ( boolean  is_win ) {
     //////////////////////////////////////////////////////////////////////
       double  delta;
     //////////////////////////////////////////////////////////////////////
       winloss_history [ fights - 1 ] = is_win;
//       if ( is_win ) delta = -learn_rate * ( win_prob - 1.0 ) * ( win_prob ) * ( 1.0 - win_prob );
//         else        delta = -learn_rate * ( win_prob - 0.0 ) * ( win_prob ) * ( 1.0 - win_prob );
       if ( is_win && !win_predicted ) {
         delta = learn_rate;
       } else if ( !is_win && win_predicted ) {
         delta = -learn_rate;
       } else
         delta = 0.0;
       fights_finished++;
       delta = delta / fights_finished;
       w_ac   += delta * ( double ) fight_history [ fights - 1 ].x;
       w_hp   += delta * ( double ) fight_history [ fights - 1 ].y;
       w_bias += delta * bias;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
