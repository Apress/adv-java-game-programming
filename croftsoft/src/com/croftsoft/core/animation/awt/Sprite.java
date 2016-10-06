     package com.croftsoft.core.animation.awt;

     import java.awt.*;

     /*********************************************************************
     * @version
     *   1997-04-19
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     *********************************************************************/

     public class  Sprite {
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     SpriteCanvas  spriteCanvas;
     Image         image;
     int           x;
     int           y;
     int           z;
     int           w;
     int           h;
     long          id;

     protected Image         scratchImage;
     protected Graphics      scratchGraphics;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Sprite ( SpriteCanvas  spriteCanvas, Image  image,
       int  x, int  y, int  z, int  w, int  h, long  id ) {
     //////////////////////////////////////////////////////////////////////
       this.spriteCanvas = spriteCanvas;
       this.image        = image;
       this.x            = x;
       this.y            = y;
       this.z            = z;
       this.w            = w;
       this.h            = h;
       this.id           = id;
     }

     public void  setImage ( Image  image ) {
     //////////////////////////////////////////////////////////////////////
       try {
         this.image = image;
// Assumes new image same size!
         spriteCanvas.slip ( this, 0, 0 );
       } catch ( Exception  e ) { e.printStackTrace ( ); }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
