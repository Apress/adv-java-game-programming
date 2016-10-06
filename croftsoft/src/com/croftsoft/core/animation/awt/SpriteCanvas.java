     package com.croftsoft.core.animation.awt;

// Need to make sure slip and disappear work when the backgroundImage
// is null without leaving a snail trail.

// See other comment below about when window minimized.

     import com.croftsoft.core.awt.GraphicsLib;  // for rasterize()

     import java.awt.*;
     import java.util.*;

     /*********************************************************************
     * If you use a backgroundImage, you must dispose of its graphics
     * yourself.
     * <P>
     * @version
     *   1997-04-28
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     *********************************************************************/

     public class  SpriteCanvas extends Canvas {
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     protected Dimension  size;

     private Color      background_Color;

     private Hashtable  sprite_Hashtable = new Hashtable ( );

     private Graphics   graphics;
     private Image      offscreenImage;
     private Graphics   offscreenGraphics;
     private Image      backgroundImage;

     private boolean    graphics_initialized;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SpriteCanvas ( Color  background_Color ) {
     //////////////////////////////////////////////////////////////////////
       this.background_Color = background_Color;
       setBackground ( background_Color );
     }

     public void  setBackgroundImage ( Image  backgroundImage ) {
     //////////////////////////////////////////////////////////////////////
       this.backgroundImage = backgroundImage;
       redraw_all ( );
     }

     public void  paint ( Graphics  g ) {
     //////////////////////////////////////////////////////////////////////
       if ( !graphics_initialized ) init_graphics ( );
       graphics.drawImage ( offscreenImage, 0, 0, this );
     }

     public synchronized void  init_graphics ( ) {
     //////////////////////////////////////////////////////////////////////
       try {
         graphics_initialized = false;
         clear_graphics ( );
         graphics = getGraphics ( );
         if ( graphics == null ) return;
         size = size ( );
         offscreenImage = createImage ( size.width, size.height );
         if ( offscreenImage == null ) return;
         offscreenGraphics = offscreenImage.getGraphics ( );
         redraw_all ( );
         graphics_initialized = true;
       } catch ( Exception  e ) { e.printStackTrace ( ); }
     }

     public synchronized void  reshape (
       int  x, int  y, int  width, int  height ) {
     //////////////////////////////////////////////////////////////////////
       try {
         super.reshape ( x, y, width, height );
         if ( !size ( ).equals ( this.size ) ) init_graphics ( );
       } catch ( Exception  e ) { e.printStackTrace ( ); }
     }

     private synchronized void  clear_graphics ( ) {
     //////////////////////////////////////////////////////////////////////
       try {
         if ( offscreenGraphics != null ) {
           offscreenGraphics.dispose ( );
           offscreenGraphics = null;
         }
         offscreenImage = null;
       } catch ( Exception  e ) { e.printStackTrace ( ); }
     }

     public synchronized void  redraw_all ( ) {
     //////////////////////////////////////////////////////////////////////
       try {
         if ( offscreenGraphics == null ) return;
         offscreenGraphics.setColor ( background_Color );
         offscreenGraphics.fillRect ( 0, 0, size.width, size.height );
         if ( backgroundImage != null ) {
           offscreenGraphics.drawImage ( backgroundImage, 0, 0, this );
         }
         Enumeration  e = sprite_Hashtable.elements ( );
         while ( e.hasMoreElements ( ) ) {
           Sprite  sprite = ( Sprite ) e.nextElement ( );
           offscreenGraphics.drawImage (
             sprite.image, sprite.x, sprite.y, this );
         }
         graphics.drawImage ( offscreenImage, 0, 0, this );
       } catch ( Exception  e ) { e.printStackTrace ( ); }
     }

     public void  repaint ( ) {
     //////////////////////////////////////////////////////////////////////
       paint ( null );
     }

     public void  update ( Graphics  g ) {
     //////////////////////////////////////////////////////////////////////
       paint ( null );
     }

     private void  double_drawImage (
       Image  image,
       int    x,
       int    y ) {
     //////////////////////////////////////////////////////////////////////
       try {
         if ( graphics == null ) init_graphics ( );
// Is this extra check a problem for speed?
         if ( graphics == null ) return;
         graphics.drawImage ( image, x, y, this );
// For some reason, when the window is minimized, the offscreenGraphics
// is null!!!!!!!!!!!!!!!??????/
         if ( offscreenGraphics != null ) {
           offscreenGraphics.drawImage ( image, x, y, this );
         }
       } catch ( Exception  e ) { e.printStackTrace ( ); }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  add ( Sprite  sprite ) {
     //////////////////////////////////////////////////////////////////////
       sprite_Hashtable.put ( new Long ( sprite.id ), sprite );
       sprite.scratchImage = createImage ( sprite.w + 1, sprite.h + 1 );
       sprite.scratchGraphics = sprite.scratchImage.getGraphics ( );
       sprite.scratchGraphics.setColor ( background_Color );
       double_drawImage ( sprite.image, sprite.x, sprite.y );
     }

     public synchronized void  remove ( Sprite  sprite ) {
     //////////////////////////////////////////////////////////////////////
       sprite_Hashtable.remove ( new Long ( sprite.id ) );
       disappear ( sprite );
       sprite.scratchGraphics.dispose ( );
       sprite.scratchGraphics = null;
       sprite.scratchImage = null;
     }

     public Sprite  get_sprite ( long  sprite_id ) {
     //////////////////////////////////////////////////////////////////////
       return ( Sprite ) sprite_Hashtable.get ( new Long ( sprite_id ) );
     }

     public void  setImage ( long  sprite_id, Image  image ) {
     //////////////////////////////////////////////////////////////////////
       Sprite  sprite
         = ( Sprite ) sprite_Hashtable.get ( new Long ( sprite_id ) );
       sprite.setImage ( image );
     }

     public synchronized void  slide (
       long  sprite_id, int  dest_x, int  dest_y ) {
     //////////////////////////////////////////////////////////////////////
       try {
         Sprite  sprite
           = ( Sprite ) sprite_Hashtable.get ( new Long ( sprite_id ) );
         slide ( sprite, dest_x, dest_y );
       } catch ( Exception  e ) { e.printStackTrace ( ); }
     }

     public synchronized void  slide (
       Sprite  sprite, int  dest_x, int  dest_y ) {
     //////////////////////////////////////////////////////////////////////
       try {
       if ( ( sprite.x == dest_x ) && ( sprite.y == dest_y ) ) return;
       Point [ ]  points = GraphicsLib.rasterize (
         new Point ( sprite.x, sprite.y ), new Point ( dest_x, dest_y ) );
       for ( int  i = 0; i < points.length; i++ ) {
         slip ( sprite,
           points [ i ].x - sprite.x, points [ i ].y - sprite.y );
       }
       } catch ( Exception  e ) { e.printStackTrace ( ); }
     }

     /*********************************************************************
     * Slips the sprite one pixel along the axes.<BR>
     * Distances dist_x and dist_y should be -1, 0, or +1.
     *********************************************************************/
     public synchronized void  slip (
       Sprite  sprite, int  dist_x, int  dist_y ) {
     //////////////////////////////////////////////////////////////////////
       try {
       synchronized ( sprite ) {
         int  pos_x = 0, pos_y = 0;
         int  corner_x = sprite.x;
         int  corner_y = sprite.y;
         if ( dist_x > 0 ) {
           pos_x = 1;
         } else if ( dist_x < 0 ) {
           corner_x -= 1;
         }
         if ( dist_y > 0 ) {
           pos_y = 1;
         } else if ( dist_y < 0 ) {
           corner_y -= 1;
         }
         sprite.scratchGraphics.fillRect (
           0, 0, sprite.w + 1, sprite.h + 1 );
         if ( backgroundImage != null ) {
           sprite.scratchGraphics.drawImage (
             backgroundImage, -corner_x, -corner_y, this );
         }
         sprite.scratchGraphics.drawImage (
           sprite.image, pos_x, pos_y, this );
         sprite.x += ( dist_x > 0 ? 1 : ( dist_x < 0 ? -1 : 0 ) );
         sprite.y += ( dist_y > 0 ? 1 : ( dist_y < 0 ? -1 : 0 ) );
         double_drawImage ( sprite.scratchImage, corner_x, corner_y );
       }
       } catch ( Exception  e ) { e.printStackTrace ( ); }
     }

     public void  disappear ( Sprite  sprite ) {
     //////////////////////////////////////////////////////////////////////
       try {
         synchronized ( sprite ) {
           sprite.scratchGraphics.fillRect (
             0, 0, sprite.w + 1, sprite.h + 1 );
           if ( backgroundImage != null ) {
             sprite.scratchGraphics.drawImage (
               backgroundImage, -sprite.x, -sprite.y, this );
           }
           double_drawImage ( sprite.scratchImage, sprite.x, sprite.y );
         }
       } catch ( Exception  e ) { e.printStackTrace ( ); }
     }

     public void  finalize ( ) throws Throwable {
     //////////////////////////////////////////////////////////////////////
       clear_graphics ( );
       super.finalize ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
