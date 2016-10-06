     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import javax.swing.*;

     import com.croftsoft.core.gui.LicenseFrame;
     import com.croftsoft.core.lang.ClassLib;
     import com.croftsoft.core.util.log.PrintStreamLog;
     
     /*********************************************************************
     * The main class for Agoracast C2P.
     *
     * <p />
     *
     * @version
     *   2002-01-27
     * @since
     *   2001-05-17
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Agoracast
       implements ActionListener, AgoracastConstants, Runnable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private LicenseFrame  licenseFrame;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       new Agoracast ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Agoracast ( )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       String  licenseAgreementText = null;

       try
       {
         licenseAgreementText = ClassLib.getResourceAsText (
           Agoracast.class, AgoracastConstants.LICENSE_FILENAME );
       }
       catch ( IOException  ex )
       {
         System.err.println (
           "Failure loading " + AgoracastConstants.LICENSE_FILENAME );

         throw ex;
       }

       Image  frameIconImage = null;

       try
       {
         frameIconImage = ClassLib.getResourceAsImage ( Agoracast.class,
           AgoracastConstants.LICENSE_FRAME_ICON_IMAGE_NAME );
       }
       catch ( Exception  ex )
       {
         System.err.println ( "Failure loading "
           + AgoracastConstants.LICENSE_FRAME_ICON_IMAGE_NAME );

         ex.printStackTrace ( );
       }

       Image  splashImage = null;

       try
       {
         splashImage = ClassLib.getResourceAsImage ( Agoracast.class,
           AgoracastConstants.SPLASH_IMAGE_NAME );
       }
       catch ( Exception  ex )
       {
         System.err.println ( "Failure loading "
           + AgoracastConstants.SPLASH_IMAGE_NAME );

         ex.printStackTrace ( );
       }

       licenseFrame = new LicenseFrame (
         licenseAgreementText,
         this,
         ( ActionListener ) null,
         ( Color ) null,
         LICENSE_FRAME_TITLE,
         ( Dimension ) null,
         frameIconImage,
         splashImage );

       licenseFrame.setVisible ( true );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       SwingUtilities.invokeLater ( this );
     }

     public void  run ( )
     //////////////////////////////////////////////////////////////////////
     {
// Consider loading documentation only when needed.

       String  documentationText = "Documenation not found.";

       try
       {
         documentationText = ClassLib.getResourceAsText (
           Agoracast.class, AgoracastConstants.DOCUMENTATION_FILENAME );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }

       AgoracastMediator  agoracastMediator
         = new AgoracastMediator ( );

       agoracastMediator.init ( );

       new AgoracastFrame (
         agoracastMediator, documentationText ).setVisible ( true );

       licenseFrame.setVisible ( false );

       licenseFrame.dispose ( );

       licenseFrame = null;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }