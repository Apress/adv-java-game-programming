     package com.croftsoft.apps.dice;

     /*********************************************************************
     * Non-Player Character (NPC) data.
     *
     * @version
     *   2002-02-27
     * @since
     *   1996-08-23
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class NPC {
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     String        name;
     long          xp;
     Combat_Stats  combat_stats;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public NPC ( String  npc_name, long  npc_xp ) {
       this.name = npc_name;
       this.xp   = npc_xp;
       this.combat_stats = new Combat_Stats ( );
     }

     public NPC ( String npc_name ) {
       this ( npc_name, 0L );
     }

     public NPC ( ) {
       this ( "Un-named NPC" );
     }

     public String toString ( ) {
     //////////////////////////////////////////////////////////////////////
       return "Name = " + this.name + "    "
         + "    XP = " + this.xp
         + "    "      + this.combat_stats.toString ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }

