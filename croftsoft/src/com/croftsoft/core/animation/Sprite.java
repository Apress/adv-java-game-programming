     package com.croftsoft.core.animation;

     import java.awt.Rectangle;
     import java.awt.Shape;

     /*********************************************************************
     * A ComponentAnimator representing a moving object.
     *
     * @version
     *   2003-07-11
     * @since
     *   2002-03-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  Sprite
       extends ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public double  getX        ( );

     public double  getY        ( );

     public double  getZ        ( );

     public double  getHeading  ( );

     public double  getVelocity ( );

     //

     public void   getCollisionBounds ( Rectangle  collisionBounds );

     public Shape  getCollisionShape  ( );

     public void   getPaintBounds     ( Rectangle  paintBounds );

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setX        ( double  x );

     public void  setY        ( double  y );

     public void  setZ        ( double  z );

     public void  setHeading  ( double  heading );

     public void  setVelocity ( double  velocity );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
