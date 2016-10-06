     package com.croftsoft.apps.mars.net;

     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.util.NullIterator;

     import com.croftsoft.apps.mars.model.GameAccessor;
     import com.croftsoft.apps.mars.model.TankAccessor;
     import com.croftsoft.apps.mars.model.WorldAccessor;
     import com.croftsoft.apps.mars.model.seri.SeriTank;
     import com.croftsoft.apps.mars.model.seri.SeriWorld;

     /*********************************************************************
     * A snapshot of the Game.
     *
     * @version
     *   2003-05-31
     * @since
     *   2003-04-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  GameData
       implements GameAccessor, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final SeriWorld  seriWorld;

     private final SeriTank   playerSeriTank;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  GameData (
       SeriWorld  seriWorld,
       SeriTank   playerSeriTank )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.seriWorld = seriWorld );

       this.playerSeriTank = playerSeriTank;
     }

     //////////////////////////////////////////////////////////////////////
     // interface GameAccessor methods
     //////////////////////////////////////////////////////////////////////

     public int       getLevel ( ) { return 0; }

     public Iterator  getPath  ( ) { return NullIterator.INSTANCE; }

     public TankAccessor   getPlayerTankAccessor ( )
     //////////////////////////////////////////////////////////////////////
     {
       return playerSeriTank;
     }

     public WorldAccessor  getWorldAccessor ( )
     //////////////////////////////////////////////////////////////////////
     {
       return seriWorld;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
