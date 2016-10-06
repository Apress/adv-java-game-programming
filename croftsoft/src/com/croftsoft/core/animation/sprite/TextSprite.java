     package com.croftsoft.core.animation.sprite;

     import java.awt.Color;
     import java.awt.Component;
     import java.awt.Font;
     import java.awt.Graphics;
     import java.awt.Graphics2D;
     import java.awt.Rectangle;
     import java.awt.Shape;
     import java.awt.geom.Rectangle2D;
     import java.awt.font.FontRenderContext;
     import java.awt.font.TextLayout;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.ComponentUpdater;
     import com.croftsoft.core.animation.painter.NullComponentPainter;
     import com.croftsoft.core.animation.sprite.AbstractSprite;
     import com.croftsoft.core.animation.updater.NullComponentUpdater;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.ObjectLib;

     /*********************************************************************
     * A Sprite implementation that paints text.
     *
     * @version
     *   2003-07-11
     * @since
     *   2002-03-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  TextSprite
       extends AbstractSprite
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private String       text;

     private Font         font;

     private Color        color;

     private Color        backgroundColor;

     private Rectangle2D  textLayoutBounds;

     private Component    component;

     private String       oldText;

     private Font         oldFont;

     private Rectangle    backgroundArea;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  TextSprite (
       double            x,
       double            y,
       double            z,
       double            heading,
       double            velocity,
       ComponentUpdater  componentUpdater,
       String            text,
       Font              font,
       Color             color )
     //////////////////////////////////////////////////////////////////////
     {
       super ( x, y, z, heading, velocity, componentUpdater,
         NullComponentPainter.INSTANCE );

       setText  ( text  );

       setFont  ( font  );

       setColor ( color );

       backgroundArea = new Rectangle ( );
     }

     public  TextSprite ( String  text )
     //////////////////////////////////////////////////////////////////////
     {
       this ( 0.0, 0.0, 0.0, 0.0, 0.0, NullComponentUpdater.INSTANCE,
         text, null, null );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public Shape  getCollisionShape ( )
     //////////////////////////////////////////////////////////////////////
     {
// this is nasty inefficient

       Rectangle  paintBounds = new Rectangle ( );

       getPaintBounds ( paintBounds );

       return paintBounds;
     }

     public void  getPaintBounds ( Rectangle  paintBounds )
     //////////////////////////////////////////////////////////////////////
     {
       if ( paintBounds == null )
       {
         return;
       }

       Rectangle2D  textLayoutBounds = this.textLayoutBounds;

       if ( textLayoutBounds == null )
       {
         Component  component = this.component;

         if ( component != null )
         {
           Graphics2D  graphics2D
             = ( Graphics2D ) component.getGraphics ( );

           FontRenderContext  fontRenderContext
             = graphics2D.getFontRenderContext ( );

           Font  font = this.font;

           if ( font == null )
           {
             font = component.getFont ( );
           }

           TextLayout  textLayout = new TextLayout (
             text, font, fontRenderContext );

           textLayoutBounds = textLayout.getBounds ( );

           graphics2D.dispose ( );
         }
       }

       double  x = getX ( );

       double  y = getY ( );

       if ( textLayoutBounds != null )
       {
// I added the -1/+2 as a fudge.  Need to figure out how it works.

         paintBounds.setBounds (
           ( int ) Math.round ( x )
             + ( int ) Math.floor ( textLayoutBounds.getX ( ) ) - 1,
           ( int ) Math.round ( y )
             + ( int ) Math.floor ( textLayoutBounds.getY ( ) ) - 1,
           ( int ) Math.ceil ( textLayoutBounds.getWidth  ( ) ) + 2,
           ( int ) Math.ceil ( textLayoutBounds.getHeight ( ) ) + 2 );
       }
       else
       {
         paintBounds.x      = 0;

         paintBounds.y      = 0;

         paintBounds.width  = 0;

         paintBounds.height = 0;
       }
     }

     //

     public String  getText  ( ) { return text;  }

     public Font    getFont  ( ) { return font;  }

     public Color   getColor ( ) { return color; }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setBackgroundColor ( Color  backgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       this.backgroundColor = backgroundColor;
     }

     public void  setText ( String  text )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.text = text );

       if ( !text.equals ( oldText ) )
       {
         textLayoutBounds = null;
       }

       oldText = text;
     }

     public void  setFont ( Font  font )
     //////////////////////////////////////////////////////////////////////
     {
       this.font = font;

       if ( !ObjectLib.equivalent ( font, oldFont ) )
       {
         textLayoutBounds = null;
       }

       oldFont = font;
     }

     public void  setColor ( Color  color )
     //////////////////////////////////////////////////////////////////////
     {
       this.color = color;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

/*
     public boolean  contains (
       double  x,
       double  y )
     //////////////////////////////////////////////////////////////////////
     {
       getPaintBounds ( backgroundArea );

       return backgroundArea.contains ( x, y );
     }

     public boolean  intersects (
       double  x,
       double  y,
       double  width,
       double  height )
     //////////////////////////////////////////////////////////////////////
     {
       getPaintBounds ( backgroundArea );

       return backgroundArea.contains ( x, y, width, height );
     }
*/

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       this.component = component;

       if ( backgroundColor != null )
       {
         graphics.setColor ( backgroundColor );

         getPaintBounds ( backgroundArea );

         graphics.fillRect (
           backgroundArea.x,
           backgroundArea.y,
           backgroundArea.width,
           backgroundArea.height );
       }

       Color  color = this.color;

       if ( color == null )
       {
         color = component.getForeground ( );
       }

       graphics.setColor ( color );

       //

       Font  font = this.font;

       if ( font == null )
       {
         font = component.getFont ( );
       }

       graphics.setFont ( font );

       //

       graphics.drawString (
         text,
         ( int ) Math.round ( getX ( ) ),
         ( int ) Math.round ( getY ( ) ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
