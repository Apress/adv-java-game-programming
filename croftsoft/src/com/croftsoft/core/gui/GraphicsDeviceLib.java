     package com.croftsoft.core.gui;

     import java.awt.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Library of static methods for manipulating a GraphicDevice.
     *
     * @version
     *   2003-07-26
     * @since
     *   2003-07-25
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  GraphicsDeviceLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static boolean  changeDisplayMode (
       GraphicsDevice   graphicsDevice,
       DisplayMode [ ]  desiredDisplayModes )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( graphicsDevice );

       NullArgumentException.check ( desiredDisplayModes );

       if ( !graphicsDevice.isDisplayChangeSupported ( ) )
       {
         return false;
       }

       DisplayMode  currentDisplayMode = graphicsDevice.getDisplayMode ( );

       DisplayMode [ ]  supportedDisplayModes
         = graphicsDevice.getDisplayModes ( );

       for ( int  i = 0; i < desiredDisplayModes.length; i++ )
       {
         DisplayMode  desiredDisplayMode = desiredDisplayModes [ i ];

         if ( DisplayModeLib.matches (
           currentDisplayMode, desiredDisplayMode ) )
         {
           return false;
         }

         for ( int  j = 0; j < supportedDisplayModes.length; j++ )
         {
           DisplayMode  supportedDisplayMode = supportedDisplayModes [ j ];

           if ( DisplayModeLib.matches (
             supportedDisplayMode, desiredDisplayMode ) )
           {
             graphicsDevice.setDisplayMode ( supportedDisplayMode );

             return true;
           }
         }
       }

       return false;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  GraphicsDeviceLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }