     package com.croftsoft.core.gui;

     import java.awt.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Static method library for manipulating BufferCapabilities objects.
     *
     * @version
     *   2003-07-26
     * @since
     *   2003-07-26
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  BufferCapabilitiesLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  print ( BufferCapabilities  bufferCapabilities )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( "full screen required.........:  "
         + bufferCapabilities.isFullScreenRequired ( ) );

       System.out.println ( "multi buffer available.......:  "
         + bufferCapabilities.isMultiBufferAvailable ( ) );

       System.out.println ( "page flipping................:  "
         + bufferCapabilities.isPageFlipping ( ) );

       System.out.println ( "front buffer accelerated.....:  "
         + bufferCapabilities.getFrontBufferCapabilities ( )
         .isAccelerated ( ) );

       System.out.println ( "front buffer true volatile...:  "
         + bufferCapabilities.getFrontBufferCapabilities ( )
         .isTrueVolatile ( ) );

       System.out.println ( "back buffer accelerated......:  "
         + bufferCapabilities.getBackBufferCapabilities ( )
         .isAccelerated ( ) );

       System.out.println ( "back buffer true volatile....:  "
         + bufferCapabilities.getBackBufferCapabilities ( )
         .isTrueVolatile ( ) );

       System.out.println ( "flip contents................:  "
         + bufferCapabilities.getFlipContents ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  BufferCapabilitiesLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
