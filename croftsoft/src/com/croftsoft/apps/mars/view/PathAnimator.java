     package com.croftsoft.apps.mars.view;

     import java.applet.*;
     import java.awt.*;
     import java.awt.geom.*;
     import java.awt.image.*;
     import java.net.*;
     import java.util.*;
     import javax.swing.*;

     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.geom.PointXY;

     import com.croftsoft.apps.mars.ai.StateSpaceNode;
     import com.croftsoft.apps.mars.model.GameAccessor;

     /*********************************************************************
     * Paints the planned path.
     *
     * @version
     *   2003-04-30
     * @since
     *   2003-04-30
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  PathAnimator
       implements ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final GameAccessor  gameAccessor;

     private final double        tankRadius;

     //

     private boolean  enabled;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *********************************************************************/
     public  PathAnimator (
       GameAccessor  gameAccessor,
       double        tankRadius )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.gameAccessor = gameAccessor );

       this.tankRadius = tankRadius;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  toggle ( )
     //////////////////////////////////////////////////////////////////////
     {
       enabled = !enabled;
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       if ( enabled )
       {
         component.repaint ( );
       }
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !enabled )
       {
         return;
       }

       Iterator  iterator = gameAccessor.getPath ( );

       while ( iterator.hasNext ( ) )
       {
         StateSpaceNode  stateSpaceNode
           = ( StateSpaceNode ) iterator.next ( );

         PointXY  pointXY = stateSpaceNode.getPointXY ( );

         graphics.setColor ( Color.RED );

         graphics.drawOval (
           ( int ) ( pointXY.getX ( ) - tankRadius ),
           ( int ) ( pointXY.getY ( ) - tankRadius ),
           ( int ) ( 2 * tankRadius ),
           ( int ) ( 2 * tankRadius ) );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
