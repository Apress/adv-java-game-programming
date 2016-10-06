     package com.croftsoft.core.media.j3d;

     import java.awt.event.KeyEvent;

     import javax.media.j3d.*;
     import javax.vecmath.*;

     /*********************************************************************
     *
     * Static method library to manipulate Transform3D data.
     * Primarily focuses on transforming a view through the 6 degrees of
     * freedom.
     *
     * <P>
     *
     * <B>Reference:</B>
     * <P>
     * 
     * Foley, et al., <U>Computer Graphics:  Principles and Practice</U>.
     * Provides the mathematics for the 3D rotation matrices.
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1998-12-06
     *********************************************************************/

     public class  Transform3DLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /** Relative X axis */
     public static final int  X = 0;

     /** Relative Y axis */
     public static final int  Y = 1;

     /** Relative Z axis */
     public static final int  Z = 2;

     private  Transform3DLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Rotates the view by the given rotation matrix.
     *
     * @param  transform3D
     *   The transform containing the current rotation matrix.
     * @param  rotation
     *   The rotation matrix to be muliplied.
     * @return
     *    The transform3D argument has its current rotation matrix replaced
     *    by its old value as multiplied by the rotation argument.
     *********************************************************************/
     public static void  viewRotate (
       Transform3D  transform3D, Matrix3d  rotation )
     //////////////////////////////////////////////////////////////////////
     {
       Matrix3d  oldRotation = new Matrix3d ( );
       transform3D.get ( oldRotation );
       oldRotation.mul ( rotation );
       transform3D.setRotation ( oldRotation );
     }

     /*********************************************************************
     * Pitches the view by a given number of radians.
     *
     * @param  transform3D
     *   The transform containing the current rotation matrix.
     * @param  radians
     *   The angle to rotate the transform about its relative X axis.
     * @return
     *    The rotation matrix is updated in the transform3D argument.
     *********************************************************************/
     public static void  viewPitch (
       Transform3D  transform3D, double  radians )
     //////////////////////////////////////////////////////////////////////
     {
       double  sin = Math.sin ( radians );
       double  cos = Math.cos ( radians );

       viewRotate ( transform3D, new Matrix3d (
         1.0, 0.0,  0.0,
         0.0, cos, -sin,
         0.0, sin,  cos ) );
     }

     /*********************************************************************
     * Yaws the view by a given number of radians.
     *
     * @param  transform3D
     *   The transform containing the current rotation matrix.
     * @param  radians
     *   The angle to rotate the transform about its relative Y axis.
     * @return
     *    The rotation matrix is updated in the transform3D argument.
     *********************************************************************/
     public static void  viewYaw (
       Transform3D  transform3D, double  radians )
     //////////////////////////////////////////////////////////////////////
     {
       double  sin = Math.sin ( radians );
       double  cos = Math.cos ( radians );

       viewRotate ( transform3D, new Matrix3d (
          cos, 0.0, sin,
          0.0, 1.0, 0.0,
         -sin, 0.0, cos ) );
     }

     /*********************************************************************
     * Rolls the view by a given number of radians.
     *
     * @param  transform3D
     *   The transform containing the current rotation matrix.
     * @param  radians
     *   The angle to rotate the transform about its relative Z axis.
     * @return
     *    The rotation matrix is updated in the transform3D argument.
     *********************************************************************/
     public static void  viewRoll  (
       Transform3D  transform3D, double  radians )
     //////////////////////////////////////////////////////////////////////
     {
       double  sin = Math.sin ( radians );
       double  cos = Math.cos ( radians );

       viewRotate ( transform3D, new Matrix3d (
         cos, -sin, 0.0,
         sin,  cos, 0.0,
         0.0,  0.0, 1.0 ) );
     }

     /*********************************************************************
     * Translates the view by a given distance along a relative axis.
     *
     * @param  transform3D
     *   The transform containing the current rotation and translation
     *   matrix.
     * @param  dimension
     *   The relative axis to translate along.
     *   Use the public constants X, Y, and Z provided by this class.
     * @return
     *    The translation vector is updated in the transform3D argument.
     *********************************************************************/
     public static void  viewTranslate (
       Transform3D  transform3D,
       int          dimension,
       double       distance )
     //////////////////////////////////////////////////////////////////////
     {
       Matrix3d  rotation    = new Matrix3d ( );
       Vector3d  translation = new Vector3d ( );
       transform3D.get ( rotation, translation );

       // Get the alignment of the rotated relative axes.
       Vector3d  r = new Vector3d ( );
       rotation.getColumn ( dimension, r );

       r.scale ( distance );

       translation.add ( r );

       transform3D.setTranslation ( translation );
     }

     /*********************************************************************
     * Transforms the view based upon a user keyboard input.
     *
     * @param  transform3D
     *   The transform containing the current rotation and translation
     *   matrices, usually the user's view transform.
     * @param  keyEvent
     *    Arrow keys in combination with no other key, the Shift key, or
     *    the Alt key will rotate or translate the transform about the
     *    relative X, Y, and Z axes respectively for 6 degrees of freedom.
     * @param  deltaRotation
     *    The number of radians to rotate the view upon a keyboard input.
     * @param  deltaTranslation
     *    The distance to translate the view upon a keyboard input.
     * @return
     *    Returns true if any changes were made to transform3D argument.
     *********************************************************************/
     public static boolean  viewKeyPressed (
       Transform3D  transform3D,
       KeyEvent     keyEvent,
       double       deltaRotation,
       double       deltaTranslation )
     //////////////////////////////////////////////////////////////////////
     {
       boolean  moved = false;

       int  keyCode = keyEvent.getKeyCode ( );

       if ( keyEvent.isAltDown ( ) )
       {
         switch ( keyCode )
         {
           case KeyEvent.VK_UP   :
             viewTranslate ( transform3D, Z, -deltaTranslation );
             moved = true;
             break;
           case KeyEvent.VK_DOWN :
             viewTranslate ( transform3D, Z,  deltaTranslation );
             moved = true;
             break;
           case KeyEvent.VK_LEFT :
             viewRoll ( transform3D,  deltaRotation );
             moved = true;
             break;
           case KeyEvent.VK_RIGHT:
             viewRoll ( transform3D, -deltaRotation );
             moved = true;
             break;
         }
       }
       else if ( keyEvent.isShiftDown ( ) )
       {
         switch ( keyCode )
         {
           case KeyEvent.VK_UP   :
             viewTranslate (
               transform3D, Y,  deltaTranslation );
             moved = true;
             break;
           case KeyEvent.VK_DOWN :
             viewTranslate (
               transform3D, Y, -deltaTranslation );
             moved = true;
             break;
           case KeyEvent.VK_LEFT :
             viewYaw ( transform3D,  deltaRotation );
             moved = true;
             break;
           case KeyEvent.VK_RIGHT:
             viewYaw ( transform3D, -deltaRotation );
             moved = true;
             break;
         }
       }
       else
       {
         switch ( keyCode )
         {
           case KeyEvent.VK_UP   :
             viewPitch  ( transform3D, -deltaRotation );
             moved = true;
             break;
           case KeyEvent.VK_DOWN :
             viewPitch  ( transform3D,  deltaRotation );
             moved = true;
             break;
           case KeyEvent.VK_LEFT :
             viewTranslate (
               transform3D, X, -deltaTranslation );
             moved = true;
             break;
           case KeyEvent.VK_RIGHT:
             viewTranslate (
               transform3D, X,  deltaTranslation );
             moved = true;
             break;
         }
       }

       return moved;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
