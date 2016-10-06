     package com.croftsoft.core.gui;

     import java.awt.*;
     import java.awt.event.*;
     import javax.swing.*;

     import com.croftsoft.core.gui.WindowLib;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.MathConstants;

     /*********************************************************************
     * Displays a license agreement and an accept/decline button choice.
     *
     * @version
     *   2002-01-27
     * @since
     *   2001-07-24
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  LicenseFrame
       extends JFrame
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  DEFAULT_FRAME_TITLE
       = "License Agreement";

     /*********************************************************************
     * Main constructor.
     *
     * @param  licenseAgreementText
     *
     *    Must not be null.
     *
     * @param  acceptActionListener
     *
     *    Called when the Accept button is clicked.  Must not be null.
     *
     * @param  declineActionListener
     *
     *    If null, the Decline button will simply trigger System.exit(0).
     *
     * @param  panelBackgroundColor
     *
     *    May be null.
     *
     * @param  frameTitle
     *
     *    May be null.
     *
     * @param  size
     *
     *    May be null.
     *
     * @param  frameIconImage
     *
     *    May be null.
     *
     * @param  splashImage
     *
     *    May be null.
     *********************************************************************/
     public  LicenseFrame (
       String          licenseAgreementText,
       ActionListener  acceptActionListener,
       ActionListener  declineActionListener,
       Color           panelBackgroundColor,
       String          frameTitle,
       Dimension       size,
       Image           frameIconImage,
       Image           splashImage )
     //////////////////////////////////////////////////////////////////////
     {
       super ( frameTitle != null ? frameTitle : DEFAULT_FRAME_TITLE );

       LicensePanel  licensePanel = new LicensePanel (
         licenseAgreementText,
         acceptActionListener,
         declineActionListener,
         panelBackgroundColor,
         splashImage );

       getContentPane ( ).add ( licensePanel );

// This doesn't seem to work in Java 1.3.
//
//     getRootPane ( ).setDefaultButton (
//       licensePanel.getAcceptJButton ( ) );

       setDefaultCloseOperation ( WindowConstants.DO_NOTHING_ON_CLOSE );

       addWindowListener ( new ShutdownWindowListener ( ) );

       if ( frameIconImage != null )
       {
         setIconImage ( frameIconImage );
       }

       if ( size == null )
       {
         size = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

         if ( splashImage != null )
         {
           int  imageWidth = splashImage.getWidth ( this );

           if ( imageWidth < 0.8 * size.width )
           {
             size.width = imageWidth;
           }
           else
           {
             size.width *= 0.8;
           }

           int  goldenHeight
             = ( int ) ( size.width * MathConstants.GOLDEN_RATIO );

           if ( goldenHeight < 0.8 * size.height )
           {
             size.height = goldenHeight;
           }
           else
           {
             size.height *= 0.8;
           }
         }
         else
         {
           size.width  *= 0.8;

           size.height *= 0.8;
         }
       }

       WindowLib.centerOnScreen ( this, size );
     }

     /*********************************************************************
     * Convenience constructor.
     *********************************************************************/
     public  LicenseFrame (
       String          licenseAgreementText,
       ActionListener  acceptActionListener,
       Color           panelBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         licenseAgreementText,
         acceptActionListener,
         ( ActionListener ) null,
         panelBackgroundColor,
         ( String    ) null,
         ( Dimension ) null,
         ( Image     ) null,
         ( Image     ) null );
     }

     /*********************************************************************
     * Convenience constructor.
     *********************************************************************/
     public  LicenseFrame (
       String          licenseAgreementText,
       ActionListener  acceptActionListener )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         licenseAgreementText,
         acceptActionListener,
         ( Color ) null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }