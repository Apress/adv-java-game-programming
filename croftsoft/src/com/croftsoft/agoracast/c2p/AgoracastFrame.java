     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.io.*;
     import javax.swing.JFrame;
     import javax.swing.WindowConstants;

     import com.croftsoft.core.gui.FrameManager;
     import com.croftsoft.core.gui.ShutdownWindowListener;
     import com.croftsoft.core.gui.WindowLib;
     import com.croftsoft.core.io.FileLib;
     import com.croftsoft.core.lang.ClassLib;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.util.log.PrintStreamLog;
     
     /*********************************************************************
     * <p />
     *
     * @version
     *   2001-08-06
     * @since
     *   2001-07-24
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastFrame
       extends JFrame
       implements AgoracastConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       new AgoracastFrame ( new AgoracastMediator ( ), "" )
         .setVisible ( true );
     }

     public  AgoracastFrame (
       AgoracastMediator  agoracastMediator,
       String             documentationText )
     //////////////////////////////////////////////////////////////////////
     {
       super ( FRAME_TITLE );

       NullArgumentException.check ( agoracastMediator );

       agoracastMediator.setParentFrame ( this );

       setDefaultCloseOperation ( WindowConstants.DO_NOTHING_ON_CLOSE );

       addWindowListener (
         new ShutdownWindowListener ( agoracastMediator ) );

       getContentPane ( ).add ( new AgoracastPanel (
         agoracastMediator, documentationText, this ) );

       Dimension  size = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

       size.width  *= 0.8;

       size.height *= 0.8;

       WindowLib.centerOnScreen ( this, size );

       try
       {
         Image  frameIconImage = ClassLib.getResourceAsImage (
           AgoracastFrame.class, FRAME_ICON_IMAGE_NAME );

         setIconImage ( frameIconImage );
       }
       catch ( IOException  ex )
       {
         ex.printStackTrace ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }