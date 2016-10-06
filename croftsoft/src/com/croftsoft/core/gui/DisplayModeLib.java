     package com.croftsoft.core.gui;

     import java.awt.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Library of static methods for manipulating DisplayMode objects.
     *
     * @version
     *   2003-07-25
     * @since
     *   2003-02-21
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  DisplayModeLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Template matching of a supported to a desired DisplayMode.
     *
     * @param  desiredDisplayMode
     *
     *   Values of zero in desiredDisplayMode are treated as wildcards.
     *********************************************************************/
     public static boolean  matches (
       DisplayMode  supportedDisplayMode,
       DisplayMode  desiredDisplayMode )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( supportedDisplayMode );

       NullArgumentException.check ( desiredDisplayMode   );

       int  desiredWidth = desiredDisplayMode.getWidth ( );

       if ( ( desiredWidth != 0 )
         && ( desiredWidth != supportedDisplayMode.getWidth ( ) ) )
       {
         return false;
       }

       int  desiredHeight = desiredDisplayMode.getHeight ( );

       if ( ( desiredHeight != 0 )
         && ( desiredHeight != supportedDisplayMode.getHeight ( ) ) )
       {
         return false;
       }

       int  desiredRefreshRate   = desiredDisplayMode  .getRefreshRate ( );

       int  supportedRefreshRate = supportedDisplayMode.getRefreshRate ( );

       if ( ( desiredRefreshRate != 0 )
         && ( desiredRefreshRate != supportedRefreshRate ) )
       {
         return false;
       }

       int  desiredBitDepth   = desiredDisplayMode  .getBitDepth ( );

       int  supportedBitDepth = supportedDisplayMode.getBitDepth ( );

       if ( ( desiredBitDepth != 0 )
         && ( desiredBitDepth != supportedBitDepth ) )
       {
         return false;
       }

       return true;
     }

     public static void  print ( DisplayMode  displayMode )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( displayMode );

       System.out.println ( "width..........:  "
         + displayMode.getWidth  ( ) );

       System.out.println ( "height.........:  "
         + displayMode.getHeight ( ) );

       System.out.println ( "bit depth......:  "
         + displayMode.getBitDepth ( ) );

       System.out.println ( "refresh rate...:  "
         + displayMode.getRefreshRate ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  DisplayModeLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }