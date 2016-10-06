     package com.croftsoft.apps.chat.view;

     import java.awt.*;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.animation.ComponentPainter;
     import com.croftsoft.core.animation.painter.ColorPainter;
     import com.croftsoft.core.awt.image.ImageCache;
     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.apps.chat.model.ChatGameAccessor;

     /*********************************************************************
     * ChatGame ComponentAnimator.
     *
     * @version
     *   2003-06-11
     * @since
     *   2003-06-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ChatGameAnimator
       implements ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final Color  BACKGROUND_COLOR = Color.BLACK;

     //

     private final ChatGameAccessor   chatGameAccessor;

     private final ComponentPainter   backgroundColorPainter;

     private final ChatWorldAnimator  chatWorldAnimator;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ChatGameAnimator (
       ChatGameAccessor  chatGameAccessor,
       JComponent        component,
       ClassLoader       classLoader,
       String            mediaDir )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.chatGameAccessor = chatGameAccessor );

       backgroundColorPainter = new ColorPainter ( BACKGROUND_COLOR );

       chatWorldAnimator = new ChatWorldAnimator (
         chatGameAccessor.getChatWorldAccessor ( ),
         new ImageCache (
           Transparency.BITMASK,
           component,
           classLoader,
           mediaDir ) );
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       chatWorldAnimator.update ( component );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       backgroundColorPainter.paint ( component, graphics );

       chatWorldAnimator.paint ( component, graphics );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
