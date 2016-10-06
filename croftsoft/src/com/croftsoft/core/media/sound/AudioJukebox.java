     package com.croftsoft.core.media.sound;

     import java.applet.*;
     import java.net.*;
     import java.util.*;

     /*********************************************************************
     * Manages the play of multiple AudioClips.
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-11-26
     *********************************************************************/

     public final class  AudioJukebox
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Map        nameToClipMap;

     private       AudioClip  audioClip;

     //////////////////////////////////////////////////////////////////////
     // Static methods
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
       throws MalformedURLException
     //////////////////////////////////////////////////////////////////////
     {
       randomPlay ( 1000, args );
     }

     public static void  randomPlay (
       long        playTime,
       String [ ]  audioClipNames )
       throws MalformedURLException
     //////////////////////////////////////////////////////////////////////
     {
       Map  nameToClipMap = new HashMap ( );

       for ( int  i = 0; i < audioClipNames.length; i++ )
       {
         URL  audioClipURL = nameToClipMap.getClass ( ).getResource (
           audioClipNames [ i ] );

         AudioClip  audioClip = Applet.newAudioClip ( audioClipURL );

         nameToClipMap.put ( audioClipNames [ i ], audioClip );
       }

       AudioJukebox  audioJukebox = new AudioJukebox ( nameToClipMap );

       Random  random = new Random ( );

       while ( true )
       {
         audioJukebox.play (
           audioClipNames [ random.nextInt ( audioClipNames.length ) ] );

         try
         {
           Thread.sleep ( playTime );
         }
         catch ( InterruptedException  ex )
         {
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     // Constructor methods
     //////////////////////////////////////////////////////////////////////

     public  AudioJukebox ( Map  nameToClipMap )
     //////////////////////////////////////////////////////////////////////
     {
       if ( nameToClipMap == null )
       {
         throw new IllegalArgumentException ( "null nameToClipMap" );
       }

       this.nameToClipMap = nameToClipMap;
     }

     //////////////////////////////////////////////////////////////////////
     // Instance methods
     //////////////////////////////////////////////////////////////////////

//   addAudioClip()

//   removeAudioClip()

//   public void  play ( String  audioClipName, int  priority.....? )

     public void  play ( String  audioClipName )
     //////////////////////////////////////////////////////////////////////
     {
       stop ( );

       audioClip = ( AudioClip ) nameToClipMap.get ( audioClipName );

       try
       {
         audioClip.play ( );
       }
       catch ( NullPointerException  ex )
       {
       }
     }

     public void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         audioClip.stop ( );
       }
       catch ( NullPointerException  ex )
       {
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
