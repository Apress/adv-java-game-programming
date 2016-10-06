     package com.croftsoft.core.animation.sprite;

     import java.awt.Graphics2D;
     import java.awt.Rectangle;
     import java.awt.Shape;
     import javax.swing.Icon;
     import javax.swing.JComponent;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.animation.ComponentUpdater;
     import com.croftsoft.core.animation.painter.NullComponentPainter;
     import com.croftsoft.core.animation.updater.NullComponentUpdater;

     /*********************************************************************
     * A Sprite implementation backed by an Icon.
     *
     * @version
     *   2002-07-11
     * @since
     *   2002-03-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  IconSprite
       extends AbstractSprite
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Rectangle  paintBounds;

     //

     private Icon  icon;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  IconSprite (
       double            x,
       double            y,
       double            z,
       double            heading,
       double            velocity,
       ComponentUpdater  componentUpdater,
       Icon              icon )
     //////////////////////////////////////////////////////////////////////
     {
       super ( x, y, z, heading, velocity, componentUpdater,
         NullComponentPainter.INSTANCE );

       paintBounds = new Rectangle ( );

       setX ( x );

       setY ( y );

       setIcon ( icon );
     }

     public  IconSprite ( Icon  icon )
     //////////////////////////////////////////////////////////////////////
     {
       this ( 0.0, 0.0, 0.0, 0.0, 0.0,
         NullComponentUpdater.INSTANCE, icon );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public Icon  getIcon ( ) { return icon; }

     public Shape  getCollisionShape ( )
     //////////////////////////////////////////////////////////////////////
     {
       return paintBounds;
     }

     public void  getCollisionBounds ( Rectangle  collisionBounds )
     //////////////////////////////////////////////////////////////////////
     {
       collisionBounds.setBounds ( paintBounds );
     }

     public void  getPaintBounds ( Rectangle  paintBounds )
     //////////////////////////////////////////////////////////////////////
     {
       paintBounds.setBounds ( this.paintBounds );
     }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setX ( double  x )
     //////////////////////////////////////////////////////////////////////
     {
       super.setX ( x );

       paintBounds.x = ( int ) x;
     }

     public void  setY ( double  y )
     //////////////////////////////////////////////////////////////////////
     {
       super.setY ( y );

       paintBounds.y = ( int ) y;
     }

     public void  setIcon ( Icon  icon )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.icon = icon );

       paintBounds.width  = icon.getIconWidth  ( );

       paintBounds.height = icon.getIconHeight ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       icon.paintIcon ( component, graphics, ( int ) x, ( int ) y );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }