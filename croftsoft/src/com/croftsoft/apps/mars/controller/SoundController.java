     package com.croftsoft.apps.mars.controller;

     import java.awt.*;
     import java.awt.event.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.media.sound.AudioClipCache;

     import com.croftsoft.apps.mars.UserData;

     /*********************************************************************
     * Toggles sound.
     *
     * @version
     *   2003-04-04
     * @since
     *   2003-04-02
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SoundController
       extends KeyAdapter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final AudioClipCache  audioClipCache;

     private final UserData        userData;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SoundController (
       AudioClipCache  audioClipCache,
       UserData        userData,
       Component       component )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.audioClipCache = audioClipCache );

       NullArgumentException.check ( this.userData = userData );

       component.addKeyListener ( this );

       component.requestFocus ( );
     }

     //////////////////////////////////////////////////////////////////////
     // interface KeyListener methods
     //////////////////////////////////////////////////////////////////////

     public void  keyPressed ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
       if ( keyEvent.getKeyCode ( ) == KeyEvent.VK_S )
       {
         boolean  muted = !userData.isMuted ( );

         userData.setMuted ( muted );

         audioClipCache.setMuted ( muted );

         System.out.println ( "Sound " + ( muted ? "off" : "on" ) );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }