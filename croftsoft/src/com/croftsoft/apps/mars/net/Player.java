     package com.croftsoft.apps.mars.net;

     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.apps.mars.model.seri.SeriTank;

     /*********************************************************************
     * Player data.
     *
     * @version
     *   2003-06-13
     * @since
     *   2003-04-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Player
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final String    name;

     private final SeriTank  seriTank;

     //

     private GameData  gameData;

     private long      lastRequestTime;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Player (
       String    name,
       SeriTank  seriTank )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.name     = name     );

       NullArgumentException.check ( this.seriTank = seriTank );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public synchronized long  getLastRequestTime ( )
     //////////////////////////////////////////////////////////////////////
     {
       return lastRequestTime;
     }

     public String    getName     ( ) { return name;     }

     public SeriTank  getSeriTank ( ) { return seriTank; }

     public GameData  getGameData ( ) { return gameData; }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setGameData ( GameData  gameData )
     //////////////////////////////////////////////////////////////////////
     {
       this.gameData = gameData;
     }

     public synchronized void  setLastRequestTime ( long  lastRequestTime )
     //////////////////////////////////////////////////////////////////////
     {
       this.lastRequestTime = lastRequestTime;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
