     package com.croftsoft.apps.color;

     import java.applet.Applet;
     import java.awt.*;

     import com.croftsoft.core.lang.lifecycle.Lifecycle;

     /*********************************************************************
     * Displays colors with RGB and HSB values.
     *
     * @version
     *   2002-02-27
     * @since
     *   1996-08-22
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  ColorTest
       extends Applet
       implements Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     Canvas  swatch;
     ColorControls  rgbControls, hsbControls;

     public void init ( ) {
     //////////////////////////////////////////////////////////////////////
       Color  theColor = new Color ( 0, 0, 0 );
       float [ ]  hsb = Color.RGBtoHSB ( theColor.getRed ( ),
         theColor.getGreen ( ), theColor.getBlue ( ),
         ( new float [ 3 ] ) );
       setLayout ( new GridLayout ( 1, 3, 10, 10 ) );
       swatch = new Canvas ( );
       swatch.setBackground ( theColor );
       rgbControls = new ColorControls ( this,
         "Red (0-255)", "Green (0-255)", "Blue (0-255)",
         theColor.getRed ( ), theColor.getGreen ( ),
         theColor.getBlue ( ) );
       hsbControls = new ColorControls ( this,
         "Hue (0-360)", "Saturation (0-100)", "Brightness (0-100)",
         ( int ) ( hsb [ 0 ] * 360 ),
         ( int ) ( hsb [ 1 ] * 100 ),
         ( int ) ( hsb [ 2 ] * 100 ) );
       add ( swatch );
       add ( rgbControls );
       add ( hsbControls );
     }

     public Insets  insets ( ) {
     //////////////////////////////////////////////////////////////////////
       return new Insets ( 5, 5, 5, 5 );
     }

     void  update ( ColorControls  in ) {
     //////////////////////////////////////////////////////////////////////
       Color   c;
       String  v1 = in.f1.getText ( );
       String  v2 = in.f2.getText ( );
       String  v3 = in.f3.getText ( );
     //////////////////////////////////////////////////////////////////////
       if ( in == rgbControls ) {
         c = new Color (
           Integer.parseInt ( v1 ),
           Integer.parseInt ( v2 ),
           Integer.parseInt ( v3 ) );
         swatch.setBackground ( c );
         float [ ]  hsb = Color.RGBtoHSB (
           c.getRed ( ), c.getGreen ( ), c.getBlue ( ),
           ( new float [ 3 ] ) );
         hsb [ 0 ] *= 360;
         hsb [ 1 ] *= 100;
         hsb [ 2 ] *= 100;
         hsbControls.f1.setText ( String.valueOf ( ( int ) hsb [ 0 ] ) );
         hsbControls.f2.setText ( String.valueOf ( ( int ) hsb [ 1 ] ) );
         hsbControls.f3.setText ( String.valueOf ( ( int ) hsb [ 2 ] ) );
       } else {
         int  f1 = Integer.parseInt ( v1 );
         int  f2 = Integer.parseInt ( v2 );
         int  f3 = Integer.parseInt ( v3 );
         c = Color.getHSBColor (
           ( float ) f1 / 360,
           ( float ) f2 / 100,
           ( float ) f3 / 100 );
         swatch.setBackground ( c );
         rgbControls.f1.setText ( String.valueOf ( c.getRed   ( ) ) );
         rgbControls.f2.setText ( String.valueOf ( c.getGreen ( ) ) );
         rgbControls.f3.setText ( String.valueOf ( c.getBlue  ( ) ) );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }

     class ColorControls extends Panel {
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
       ColorTest  outerparent;
       TextField  f1, f2, f3;

     ColorControls (
       ColorTest  target,
       String     l1,
       String     l2,
       String     l3,
       int        v1,
       int        v2,
       int        v3 ) {
     //////////////////////////////////////////////////////////////////////
     // Constructor method
     //////////////////////////////////////////////////////////////////////
       this.outerparent = target;
       setLayout ( new GridLayout ( 3, 2, 10, 10 ) );
       f1 = new TextField ( String.valueOf ( v1 ), 10 );
       f2 = new TextField ( String.valueOf ( v2 ), 10 );
       f3 = new TextField ( String.valueOf ( v3 ), 10 );
       add ( new Label ( l1, Label.RIGHT ) );
       add ( f1 );
       add ( new Label ( l2, Label.RIGHT ) );
       add ( f2 );
       add ( new Label ( l3, Label.RIGHT ) );
       add ( f3 );
     }

     public boolean  action ( Event  evt, Object  arg ) {
     //////////////////////////////////////////////////////////////////////
       if ( evt.target instanceof TextField ) {
         outerparent.update ( this );
         outerparent.repaint ( );
         return true;
       } else return false;
     }

     public Insets  insets ( ) {
     //////////////////////////////////////////////////////////////////////
       return new Insets ( 5, 5, 0, 0 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }

