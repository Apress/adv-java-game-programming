     package com.croftsoft.core.media.sound;

     import java.applet.*;
     import java.net.*;
     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Loads, caches, and plays AudioClips with master mute toggle.
     *
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *
     * @version
     *   2003-04-04
     *
     * @since
     *   2003-04-01
     *********************************************************************/

     public final class  AudioClipCache
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final ClassLoader  classLoader;

     private final String       mediaDir;

     private final Map          nameToAudioClipMap;

     //

     private boolean    muted;

     private AudioClip  audioClip;

     //////////////////////////////////////////////////////////////////////
     // Constructor methods
     //////////////////////////////////////////////////////////////////////

     public  AudioClipCache (
       ClassLoader  classLoader,
       String       mediaDir,
       Map          nameToAudioClipMap )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.classLoader = classLoader );

       this.mediaDir = mediaDir;

       NullArgumentException.check (
         this.nameToAudioClipMap = nameToAudioClipMap );
     }

     public  AudioClipCache (
       ClassLoader  classLoader,
       String       mediaDir )
     //////////////////////////////////////////////////////////////////////
     {
       this ( classLoader, mediaDir, new HashMap ( ) );
     }

     public  AudioClipCache ( ClassLoader  classLoader )
     //////////////////////////////////////////////////////////////////////
     {
       this ( classLoader, ( String ) null, new HashMap ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public boolean  isMuted ( ) { return muted; }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  clear ( ) { nameToAudioClipMap.clear ( ); }

     public void  setMuted ( boolean  muted )
     //////////////////////////////////////////////////////////////////////
     {
       this.muted = muted;

       if ( muted )
       {
         stop ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  play ( String  audioClipName )
     //////////////////////////////////////////////////////////////////////
     {
       if ( muted )
       {
         return;
       }

       stop ( );       

       audioClip = ( AudioClip ) nameToAudioClipMap.get ( audioClipName );

       if ( audioClip == null )
       {
         URL  audioClipURL = classLoader.getResource (
           mediaDir == null ? audioClipName : mediaDir + audioClipName );

         audioClip = Applet.newAudioClip ( audioClipURL );

         nameToAudioClipMap.put ( audioClipName, audioClip );
       }

       audioClip.play ( );       
     }

     public synchronized void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( audioClip != null )
       {
         audioClip.stop ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
