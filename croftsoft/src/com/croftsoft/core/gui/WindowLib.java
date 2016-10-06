     package com.croftsoft.core.gui;

     import java.awt.*;

     /*********************************************************************
     * Supplementary static methods for the java.awt.Window class.
     *
     * @version
     *   2003-07-23
     * @since
     *   1998-10-28
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  WindowLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  centerOnScreen ( Window  window )
     //////////////////////////////////////////////////////////////////////
     {
       centerOnScreen ( window, window.getSize ( ) );
     }

     public static void  centerOnScreen (
       Window  window, Dimension  size )
     //////////////////////////////////////////////////////////////////////
     {
       Dimension  screenSize
         = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

       window.setBounds (
         ( screenSize.width  - size.width  ) / 2,
         ( screenSize.height - size.height ) / 2,
         size.width, size.height );
     }

     public static void  centerOnScreen (
       Window  window, double  screenRatio )
     //////////////////////////////////////////////////////////////////////
     {
       Dimension  screenSize
         = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

       int  width  = ( int ) ( screenSize.width  * screenRatio );

       int  height = ( int ) ( screenSize.height * screenRatio );

       window.setBounds (
         ( screenSize.width  - width  ) / 2,
         ( screenSize.height - height ) / 2,
         width,
         height );
     }

     public static void  centerAboveParent (
       Window  child, Dimension  size )
     //////////////////////////////////////////////////////////////////////
     {
       Container  parent = child.getParent ( );
       Rectangle  parentBounds = parent.getBounds ( );
       child.setBounds (
         parentBounds.x + ( parentBounds.width  - size.width  ) / 2,
         parentBounds.y + ( parentBounds.height - size.height ) / 2,
         size.width, size.height );
     }

/*
     public static void  centerAboveParent (
       Window  child, double  parentRatio )
     //////////////////////////////////////////////////////////////////////
     {
       Container  parent = child.getParent ( );

       Rectangle  parentBounds = parent.getBounds ( );

       int  width  = ( int ) ( parentBounds.width  * parentRatio );

       int  height = ( int ) ( parentBounds.height * parentRatio );

       child.setBounds (
         parentBounds.x + ( parentBounds.width  - width  ) / 2,
         parentBounds.y + ( parentBounds.height - height ) / 2,
         width,
         height );
     }
*/

     public static void  centerAboveParent ( Window  child )
     //////////////////////////////////////////////////////////////////////
     {
       centerAboveParent ( child, child.getSize ( ) );
     }

     public static Window  getParentWindow ( Component  component )
     //////////////////////////////////////////////////////////////////////
     {
       Component  parent = component;

       while ( ( parent = parent.getParent ( ) ) != null )
       {
         if ( parent instanceof Window )
         {
           break;
         }
       }

       return ( Window ) parent;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  WindowLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
