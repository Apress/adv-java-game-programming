     package com.croftsoft.apps.dice;

     /*********************************************************************
     * Combat statistics data.
     *
     * @version
     *   2002-02-27
     * @since
     *   1996-08-23
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class Combat_Stats {
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     long thac0             = 10;
     long ac                = 0;
     long damage_multiplier = 1;
     long damage_base       = 10;
     long damage_bonus      = 0;
     long hp                = 50;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String toString ( ) {
     //////////////////////////////////////////////////////////////////////
       return
         "THAC0 = "  + this.thac0             + "    " +
         "AC = "     + this.ac                + "    " +
         "Damage = " + this.damage_multiplier + "d"    +
                       this.damage_base       + "+"    +
                       this.damage_bonus      + "    " +
         "HP = "     + this.hp;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }

