     package com.croftsoft.ajgp.data;

     /*********************************************************************
     * Example data interface for Data Persistence chapter.
     *
     * @version
     *   2003-03-12
     * @since
     *   2003-03-12
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  GameData
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final int  DEFAULT_HEALTH = 10;

     public static final int  DEFAULT_WEALTH = 99;

     public static final int  DEFAULT_WISDOM = 18;

     //

     public static final int  MINIMUM_HEALTH = -10;

     public static final int  MINIMUM_WEALTH =   0;

     public static final int  MINIMUM_WISDOM =   3;

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public int  getHealth ( );

     public int  getWealth ( );

     public int  getWisdom ( );

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setHealth ( int  health );

     public void  setWealth ( int  wealth );

     public void  setWisdom ( int  wisdom );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }