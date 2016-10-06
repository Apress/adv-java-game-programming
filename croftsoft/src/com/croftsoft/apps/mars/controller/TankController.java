     package com.croftsoft.apps.mars.controller;

     import java.awt.*;
     import java.awt.event.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.gui.event.UserInputAdapter;
     import com.croftsoft.core.math.geom.Point2DD;

     import com.croftsoft.apps.mars.ai.TankOperator;
     
     /*********************************************************************
     * Handles user inputs to control the player tank.
     *
     * @version
     *   2003-04-17
     * @since
     *   2003-03-20
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  TankController
       extends UserInputAdapter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final TankOperator  tankOperator;

     private final Point2DD      destination;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  TankController (
       TankOperator  tankOperator,
       Component     component )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.tankOperator = tankOperator );

       NullArgumentException.check ( component );

       component.addKeyListener ( this );

       component.addMouseListener ( this );

       component.addMouseMotionListener ( this );

       component.requestFocus ( );

       destination = new Point2DD ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  keyPressed ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
       tankOperator.fire ( );
     }

     public void  mouseMoved ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Point  mousePoint = mouseEvent.getPoint ( );

       destination.setXY ( mousePoint.x, mousePoint.y );

       tankOperator.go ( destination );
     }

     public void  mousePressed ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
       tankOperator.fire ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }