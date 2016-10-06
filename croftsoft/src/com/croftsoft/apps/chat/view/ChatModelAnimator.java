     package com.croftsoft.apps.chat.view;

     import java.awt.*;
     import java.awt.geom.*;
     import java.io.*;
     import java.util.*;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.animation.animator.ModelAnimator;
     import com.croftsoft.core.animation.animator.WorldAnimator;
     import com.croftsoft.core.animation.model.ModelAccessor;
     import com.croftsoft.core.animation.model.WorldAccessor;
     import com.croftsoft.core.animation.painter.ColorPainter;
     import com.croftsoft.core.awt.image.ImageCache;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.media.sound.AudioClipCache;

     import com.croftsoft.apps.chat.ChatConstants;
     import com.croftsoft.apps.chat.model.ChatModelAccessor;

     /*********************************************************************
     * ComponentAnimator for a ChatModel.
     *
     * @version
     *   2003-07-23
     * @since
     *   2003-06-11
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ChatModelAnimator
       extends ModelAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final ChatModelAccessor  chatModelAccessor;

     private final ImageCache         imageCache;

     private final AffineTransform    affineTransform;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ChatModelAnimator (
       ChatModelAccessor  chatModelAccessor,
       ImageCache         imageCache )
     //////////////////////////////////////////////////////////////////////
     {
       super ( chatModelAccessor );

       this.chatModelAccessor = chatModelAccessor;

       NullArgumentException.check ( this.imageCache = imageCache );

       affineTransform = new AffineTransform ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       if ( chatModelAccessor.isActive ( ) )
       {
         affineTransform.setToTranslation (
           chatModelAccessor.getCenterX ( ) - ChatConstants.RADIUS,
           chatModelAccessor.getCenterY ( ) - ChatConstants.RADIUS );

         try
         {
           graphics.drawImage (
             imageCache.get (
               chatModelAccessor.getAvatarType ( ).toLowerCase ( )
               + ChatConstants.AVATAR_IMAGE_FILENAME_EXTENSION ),
             affineTransform,
             null );
         }
         catch ( IOException  ex )
         {
           ex.printStackTrace ( );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
