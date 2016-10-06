     package com.croftsoft.core.gui;

     import java.awt.Dimension;
     import java.awt.Frame;
     import java.awt.event.WindowAdapter;
     import java.awt.event.WindowEvent;
     import java.io.IOException;
     import javax.swing.JFrame;
     import javax.swing.WindowConstants;

     import com.croftsoft.core.lang.ClassLib;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;

     /*********************************************************************
     * Library of static methods for manipulating Frame and JFrame objects.
     *
     * @version
     *   2002-12-24
     * @since
     *   2002-02-16
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  FrameLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Associates a LifecycleWindowListener with the JFrame and launches.
     *
     * @deprecated
     *
     *   This method has been moved to class LifecycleWindowListener.
     *********************************************************************/
     public static void  launchJFrameAsDesktopApp (
       JFrame               jFrame,
       final Lifecycle [ ]  lifecycles,
       Dimension            frameSize,
       String               shutdownConfirmationPrompt )
     //////////////////////////////////////////////////////////////////////
     {
       LifecycleWindowListener.launchFrameAsDesktopApp (
         jFrame, lifecycles, frameSize, shutdownConfirmationPrompt );
     }

     public static void  setIconImage (
       Frame   frame,
       String  iconImageFilename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       frame.setIconImage ( ClassLib.getResourceAsImage (
           FrameLib.class, iconImageFilename ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  FrameLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }