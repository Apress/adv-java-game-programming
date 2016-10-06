     package com.croftsoft.apps.mars.view;

     import java.awt.*;
     import javax.swing.JComponent;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.media.sound.AudioClipCache;

     import com.croftsoft.apps.mars.model.AmmoDumpAccessor;

     /*********************************************************************
     * The view for an AmmoDump.
     *
     * @version
     *   2003-04-17
     * @since
     *   2003-04-03
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AmmoDumpAnimator
       extends ModelAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final Color   COLOR                    = Color.YELLOW;

     private static final Color   HIT_COLOR                = Color.RED;

     private static final String  EXPLOSION_AUDIO_FILENAME = "explode.wav";

     //

     private final AmmoDumpAccessor  ammoDumpAccessor;

     private final AudioClipCache    audioClipCache;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AmmoDumpAnimator (
       AmmoDumpAccessor  ammoDumpAccessor,
       AudioClipCache    audioClipCache )
     //////////////////////////////////////////////////////////////////////
     {
       super ( ammoDumpAccessor, COLOR );

       this.ammoDumpAccessor = ammoDumpAccessor;

       NullArgumentException.check (
         this.audioClipCache = audioClipCache );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       super.update ( component );

       if ( ammoDumpAccessor.isExploding ( ) )
       {
         audioClipCache.play ( EXPLOSION_AUDIO_FILENAME );
       }
     }

     protected void  getRepaintRectangle ( Rectangle  repaintRectangle )
     //////////////////////////////////////////////////////////////////////
     {
       if ( ammoDumpAccessor.isExploding ( ) )
       {
         repaintRectangle.setBounds (
           ammoDumpAccessor.getExplosionShape ( ).getBounds ( ) );
       }
       else
       {
         super.getRepaintRectangle ( repaintRectangle );
       }
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       if ( ammoDumpAccessor.isExploding ( ) )
       {
         graphics.setColor ( HIT_COLOR );

         graphics.fill ( ammoDumpAccessor.getExplosionShape ( ) );
       }
       else
       {
         super.paint ( component, graphics );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }