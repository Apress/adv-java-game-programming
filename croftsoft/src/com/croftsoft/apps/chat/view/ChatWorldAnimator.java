     package com.croftsoft.apps.chat.view;

     import java.awt.*;
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

     import com.croftsoft.apps.chat.model.ChatModelAccessor;

     /*********************************************************************
     * ComponentAnimator that maps Models to views.
     *
     * @version
     *   2003-06-11
     * @since
     *   2003-06-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ChatWorldAnimator
       extends WorldAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final ImageCache  imageCache;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ChatWorldAnimator (
       WorldAccessor  worldAccessor,
       ImageCache     imageCache )
     //////////////////////////////////////////////////////////////////////
     {
       super ( worldAccessor );

       NullArgumentException.check ( this.imageCache = imageCache );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     protected ComponentAnimator  createComponentAnimator (
       ModelAccessor  modelAccessor )
     //////////////////////////////////////////////////////////////////////
     {
       ChatModelAccessor  chatModelAccessor
         = ( ChatModelAccessor ) modelAccessor;

       return new ChatModelAnimator ( chatModelAccessor, imageCache );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
