     package com.croftsoft.core.awt.font;

     import java.awt.*;
     import java.awt.font.*;
     import java.awt.geom.*;

     /*********************************************************************
     * Library of static methods for manipulating Fonts.
     *
     * @version
     *   2003-02-10
     * @since
     *   2002-03-02
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  FontLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static Rectangle2D  getTextLayoutBounds (
       Component  component,
       String     text )
     //////////////////////////////////////////////////////////////////////
     {
       Graphics2D  graphics2D
         = ( Graphics2D ) component.getGraphics ( );

       FontRenderContext  fontRenderContext
         = graphics2D.getFontRenderContext ( );

       Font  font = component.getFont ( );

       TextLayout  textLayout = new TextLayout (
         text, font, fontRenderContext );

       Rectangle2D  textLayoutBounds = textLayout.getBounds ( );

       graphics2D.dispose ( );

       return textLayoutBounds;
     }

     /*********************************************************************
     * Sets the Graphics Font size so that it maximizes the bounded text.
     *********************************************************************/
     public static void  setMaxFont (
       Graphics  graphics,
       String    text,
       String    fontName,
       int       fontStyle,
       double    maxWidth,
       double    maxHeight )
     //////////////////////////////////////////////////////////////////////
     {
       int  fontSize = 2;

       while ( true )
       {
         graphics.setFont ( new Font ( fontName, fontStyle, fontSize ) );

         FontMetrics  fontMetrics = graphics.getFontMetrics ( );

         Rectangle2D  textBounds
           = fontMetrics.getStringBounds ( text, graphics );

         if ( ( textBounds.getWidth  ( ) > maxWidth  )
           || ( textBounds.getHeight ( ) > maxHeight ) )
         {
           graphics.setFont (
             new Font ( fontName, fontStyle, fontSize - 1 ) );

           break;
         }

         fontSize++;
       }
     }

     /*********************************************************************
     * Sets the Graphics Font size so that it maximizes the bounded text.
     *********************************************************************/
     public static void  setMaxFont (
       Component  component,
       String     text,
       String     fontName,
       int        fontStyle,
       double     maxWidth,
       double     maxHeight )
     //////////////////////////////////////////////////////////////////////
     {
       Graphics  graphics = component.getGraphics ( );

       setMaxFont (
         graphics, text, fontName, fontStyle, maxWidth, maxHeight );

       component.setFont ( graphics.getFont ( ) );

       graphics.dispose ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private FontLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }