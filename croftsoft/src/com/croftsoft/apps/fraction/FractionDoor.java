     package com.croftsoft.apps.fraction;

     import java.applet.*;
     import java.awt.*;
     import java.awt.event.*;
     import java.awt.geom.*;
     import java.util.Random;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.animation.*;
     import com.croftsoft.core.animation.sprite.*;

     /*********************************************************************
     * A Fraction door.
     *
     * @version
     *   2002-07-21
     * @since
     *   2002-04-28
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  FractionDoor
       implements ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final Color   DOOR_FRAME_COLOR
       = Color.ORANGE;

     private static final Color   NUMBERS_COLOR
       = Color.BLACK;

     private static final Color   DOOR_COLOR_CLOSED
       = Color.LIGHT_GRAY;

     private static final Color   DOOR_COLOR_HIGHLIGHTED
       = Color.YELLOW;

     private static final Color   DOOR_COLOR_OPEN
       = Color.WHITE;

     //

     private final Rectangle  rectangle;

     private final int        numerator;

     private final int        denominator;

     private final String     numeratorStr;

     private final String     denominatorStr;

     private final int        numeratorX;

     private final int        numeratorY;

     private final int        denominatorX;

     private final int        denominatorY;

     private final int        lineX1;

     private final int        lineY1;

     private final int        lineX2;

     private final int        lineY2;

     private final int        horizontalMovementY;

     private final int        verticalMovementX;

     //

     private Color  doorColor;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  FractionDoor (
       Rectangle      rectangle,
       int            numerator,
       int            denominator,
       int            numeratorX,
       int            numeratorY,
       int            denominatorX,
       int            denominatorY,
       int            lineX1,
       int            lineY1,
       int            lineX2,
       int            lineY2,
       int            horizontalMovementY,
       int            verticalMovementX )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.rectangle = rectangle   );

       this.numerator   = numerator;

       this.denominator = denominator;

       this.numeratorStr   = Integer.toString ( numerator   );

       this.denominatorStr = Integer.toString ( denominator );

       this.numeratorX   = numeratorX;

       this.numeratorY   = numeratorY;

       this.denominatorX = denominatorX;

       this.denominatorY = denominatorY;

       this.lineX1 = lineX1;

       this.lineY1 = lineY1;

       this.lineX2 = lineX2;

       this.lineY2 = lineY2;

       this.horizontalMovementY = horizontalMovementY;

       this.verticalMovementX   = verticalMovementX;

       reset ( );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public int  getNumerator   ( ) { return numerator;   }

     public int  getDenominator ( ) { return denominator; }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setHighlighted ( )
     //////////////////////////////////////////////////////////////////////
     {
       doorColor = DOOR_COLOR_HIGHLIGHTED;
     }

     public void  setOpen ( )
     //////////////////////////////////////////////////////////////////////
     {
       doorColor = DOOR_COLOR_OPEN;
     }

     public void  reset ( )
     //////////////////////////////////////////////////////////////////////
     {
       doorColor = DOOR_COLOR_CLOSED;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  contains (
       int  x,
       int  y )
     //////////////////////////////////////////////////////////////////////
     {
       return ( x == verticalMovementX   )
         &&   ( y == horizontalMovementY );
     }

     public int  getHorizontalMovementY ( )
     //////////////////////////////////////////////////////////////////////
     {
       return horizontalMovementY;
     }

     public int  getVerticalMovementX ( )
     //////////////////////////////////////////////////////////////////////
     {
       return verticalMovementX;
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics2D )
     //////////////////////////////////////////////////////////////////////
     {
       graphics2D.setColor ( doorColor );

       graphics2D.fill ( rectangle );

       graphics2D.setColor ( DOOR_FRAME_COLOR );

       graphics2D.draw ( rectangle );

       graphics2D.setColor ( NUMBERS_COLOR );

       graphics2D.drawString (
         numeratorStr,
         numeratorX,
         numeratorY );

       graphics2D.drawLine (
         lineX1,
         lineY1,
         lineX2,
         lineY2 );

       graphics2D.drawString (
         denominatorStr,
         denominatorX,
         denominatorY );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }