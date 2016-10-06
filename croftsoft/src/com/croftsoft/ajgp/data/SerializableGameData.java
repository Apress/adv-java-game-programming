     package com.croftsoft.ajgp.data;

     import java.beans.XMLDecoder;
     import java.beans.XMLEncoder;
     import java.io.ByteArrayInputStream;
     import java.io.ByteArrayOutputStream;
     import java.io.Serializable;

     import com.croftsoft.core.lang.Testable;

     /*********************************************************************
     * Example data object for Data Persistence chapter.
     *
     * @version
     *   2003-03-13
     * @since
     *   2003-03-12
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SerializableGameData
       implements Serializable, GameData, Testable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 1L;

     //

     private int  health;

     private int  wealth;

     private int  wisdom;

     //////////////////////////////////////////////////////////////////////
     // static methods
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( test ( args ) );
     }

     public static boolean  test ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         final int  TEST_HEALTH = -1;

         GameData  gameData1 = new SerializableGameData ( );

         System.out.println ( "health = " + gameData1.getHealth ( ) );

         System.out.println ( "wealth = " + gameData1.getWealth ( ) );

         System.out.println ( "wisdom = " + gameData1.getWisdom ( ) );

         gameData1.setHealth ( TEST_HEALTH );

         ByteArrayOutputStream  byteArrayOutputStream
           = new ByteArrayOutputStream ( );

         XMLEncoder  xmlEncoder = new XMLEncoder ( byteArrayOutputStream );

         xmlEncoder.writeObject ( gameData1 );

         xmlEncoder.close ( );

         byte [ ]  xmlBytes = byteArrayOutputStream.toByteArray ( );

         System.out.println ( new String ( xmlBytes ) );

         XMLDecoder  xmlDecoder
           = new XMLDecoder ( new ByteArrayInputStream ( xmlBytes ) );

         GameData  gameData2 = ( GameData ) xmlDecoder.readObject ( );

         System.out.println ( "health = " + gameData2.getHealth ( ) );

         System.out.println ( "wealth = " + gameData2.getWealth ( ) );

         System.out.println ( "wisdom = " + gameData2.getWisdom ( ) );

         return gameData2.getHealth ( ) == TEST_HEALTH
           &&   gameData2.getWealth ( ) == DEFAULT_WEALTH
           &&   gameData2.getWisdom ( ) == DEFAULT_WISDOM;
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         return false;
       }
     }

     //////////////////////////////////////////////////////////////////////
     // no-argument constructor method
     //////////////////////////////////////////////////////////////////////

     public  SerializableGameData ( )
     //////////////////////////////////////////////////////////////////////
     {
       setHealth ( DEFAULT_HEALTH );

       setWealth ( DEFAULT_WEALTH );

       setWisdom ( DEFAULT_WISDOM );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public int  getHealth ( ) { return health; }

     public int  getWealth ( ) { return wealth; }

     public int  getWisdom ( ) { return wisdom; }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setHealth ( int  health )
     //////////////////////////////////////////////////////////////////////
     {
       if ( health < MINIMUM_HEALTH )
       {
         throw new IllegalArgumentException ( "health < minimum" );
       }

       this.health = health;
     }

     public void  setWealth ( int  wealth )
     //////////////////////////////////////////////////////////////////////
     {
       if ( wealth < MINIMUM_WEALTH )
       {
         throw new IllegalArgumentException ( "wealth < minimum" );
       }

       this.wealth = wealth;
     }

     public void  setWisdom ( int  wisdom )
     //////////////////////////////////////////////////////////////////////
     {
       if ( wisdom < MINIMUM_WISDOM )
       {
         throw new IllegalArgumentException ( "wisdom < minimum" );
       }

       this.wisdom = wisdom;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
