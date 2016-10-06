     package com.croftsoft.apps.mars.net;

     import java.awt.*;
     import java.awt.event.*;

     import com.croftsoft.core.gui.event.UserInputAdapter;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.geom.Point2DD;

     import com.croftsoft.apps.mars.net.request.FireRequest;
     import com.croftsoft.apps.mars.net.request.MoveRequest;

     /*********************************************************************
     * Handles user inputs and sends requests over the net.
     *
     * @version
     *   2003-05-13
     * @since
     *   2003-04-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  NetController
       extends UserInputAdapter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final String        playerName;

     private final Synchronizer  synchronizer;

     private final FireRequest   fireRequest;

     private final MoveRequest   moveRequest;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  NetController (
       String        playerName,
       Synchronizer  synchronizer,
       Component     component )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.playerName   = playerName   );

       NullArgumentException.check ( this.synchronizer = synchronizer );

       NullArgumentException.check ( component );

       component.addMouseListener ( this );

       component.addMouseMotionListener ( this );

       component.addKeyListener ( this );

       component.requestFocus ( );

       fireRequest = new FireRequest ( playerName );

       moveRequest = new MoveRequest ( playerName );
     }

     //////////////////////////////////////////////////////////////////////
     // interface KeyListener methods
     //////////////////////////////////////////////////////////////////////

     public void  keyPressed ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
       if ( keyEvent.getKeyChar ( ) == ' ' )
       {
         fire ( );
       }
     }

     public void  mouseMoved ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Point  mousePoint = mouseEvent.getPoint ( );

       moveRequest.setDestination ( mousePoint.x, mousePoint.y );

       synchronizer.replace ( moveRequest );
     }

     public void  mousePressed ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
       fire ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private void  fire ( )
     //////////////////////////////////////////////////////////////////////
     {
       synchronizer.replace ( fireRequest );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
