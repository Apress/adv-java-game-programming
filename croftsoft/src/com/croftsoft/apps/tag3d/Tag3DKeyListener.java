     package com.croftsoft.apps.tag3d;

     import java.awt.event.KeyEvent;
     import java.awt.event.KeyListener;

     import javax.media.j3d.Transform3D;
     import javax.media.j3d.TransformGroup;

     import com.croftsoft.core.media.j3d.Transform3DLib;
     import com.croftsoft.core.media.j3d.Transform3DState;
     import com.croftsoft.core.util.state.StateMulticaster;

     /*********************************************************************
     * Handles keyboard events from the Canvas3D.
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-02-07
     *********************************************************************/

     public class  Tag3DKeyListener implements KeyListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected double            deltaRotation;
     protected double            deltaTranslation;
     protected String            id;
     protected StateMulticaster  stateMulticaster;
     protected TransformGroup    viewTransformGroup;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * How keyboard input view effects and where the updates are broadcast.
     *
     * @param  deltaRotation
     *   The number of radians to rotate the view upon a keyboard input.
     * @param  deltaTranslation
     *   The distance to translate the view upon a keyboard input.
     * @param  id
     *   The unique identifier for the object controlled by the keyboard.
     * @param  stateMulticaster
     *   Destination for Transform3DState updates for this view.
     * @param  viewTransformGroup
     *   Contains the current view transform for the object controlled.
     *********************************************************************/
     public  Tag3DKeyListener (
       double            deltaRotation,
       double            deltaTranslation,
       String            id,
       StateMulticaster  stateMulticaster,
       TransformGroup    viewTransformGroup )
     //////////////////////////////////////////////////////////////////////
     {
       this.deltaRotation      = deltaRotation;
       this.deltaTranslation   = deltaTranslation;
       this.id                 = id;
       this.stateMulticaster   = stateMulticaster;
       this.viewTransformGroup = viewTransformGroup;
     }

     //////////////////////////////////////////////////////////////////////
     // KeyListener interface methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Interprets view transform inputs and broadcasts the updates.
     *
     * @param  keyEvent
     *   Arrow keys in combination with no other key, the Shift key, or
     *   the Alt key will rotate or translate the transform about the
     *   relative X, Y, and Z axes respectively for 6 degrees of freedom.
     * @return
     *   Sends a Transform3DState update to the StateMulticaster if needed.
     *********************************************************************/
     public synchronized void  keyPressed ( KeyEvent  keyEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Transform3D  transform3D = new Transform3D ( );
       viewTransformGroup.getTransform ( transform3D );

       if ( Transform3DLib.viewKeyPressed (
         transform3D, keyEvent, deltaRotation, deltaTranslation ) )
       {
         stateMulticaster.update (
           new Transform3DState ( id, transform3D ) );
       }
     }

     public void  keyReleased ( KeyEvent  keyEvent ) { }
     public void  keyTyped    ( KeyEvent  keyEvent ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
