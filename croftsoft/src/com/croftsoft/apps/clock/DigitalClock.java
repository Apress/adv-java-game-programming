     package com.croftsoft.apps.clock;

     import java.awt.*;
     import java.awt.event.*;
     import java.awt.font.*;
     import java.awt.geom.*;
     import java.io.*;
     import java.util.*;
     import javax.swing.*;

     import com.croftsoft.core.awt.font.FontLib;
     import com.croftsoft.core.gui.FrameLib;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.text.DateFormatLib;

     /*********************************************************************
     * This digital clock applet/application displays the date and time.
     *
     * @version
     *   2002-03-06
     * @since
     *   1996
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class DigitalClock
       extends JApplet
       implements ComponentListener, Lifecycle, Runnable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  VERSION = "2002-03-06";

     private static final String  TITLE = "CroftSoft Clock";

     private static final String  INFO
       = "\n" + TITLE + "\n"
       + "Copyright 2002 CroftSoft Inc\n"
       + "http://www.croftsoft.com/\n"
       + "Version " + VERSION + "\n";

     private static final Dimension  FRAME_SIZE  = null;

     private static final String  FRAME_ICON_FILENAME = "/images/david.png";

     private static final String  SHUTDOWN_CONFIRMATION_PROMPT
       = "Close " + TITLE + "?";

     private static final String  FONT_NAME = "TimesRoman";

     private static final int     FONT_STYLE = Font.BOLD;

     private static final String  IDLE_DATE_TEXT = "CroftSoft";

     private static final String  IDLE_TIME_TEXT = "www.croftsoft.com";

     private static final String  ACTIVE_DATE_TEXT = "0000-00-00";

     private static final String  ACTIVE_TIME_TEXT = "00:00:00";

     //

     private JLabel  dateLabel;

     private JLabel  timeLabel;

     private Thread  thread;

     private String  resizeDateText;

     private String  resizeTimeText;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( INFO );

       JFrame  jFrame = new JFrame ( TITLE );

       try
       {
         FrameLib.setIconImage ( jFrame, FRAME_ICON_FILENAME );
       }
       catch ( IOException  ex )
       {
       }

       DigitalClock  digitalClock = new DigitalClock ( );

       jFrame.setContentPane ( digitalClock );

       FrameLib.launchJFrameAsDesktopApp (
         jFrame,
         new Lifecycle [ ] { digitalClock },
         FRAME_SIZE,
         SHUTDOWN_CONFIRMATION_PROMPT );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getAppletInfo ( ) { return INFO; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       resizeDateText = IDLE_DATE_TEXT;

       resizeTimeText = IDLE_TIME_TEXT;

       dateLabel = new JLabel ( IDLE_DATE_TEXT );

       dateLabel.setHorizontalAlignment ( SwingConstants.CENTER );

       timeLabel = new JLabel ( IDLE_TIME_TEXT );

       timeLabel.setHorizontalAlignment ( SwingConstants.CENTER );

       Container  contentPane = getContentPane ( );

       contentPane.setBackground ( Color.WHITE );

       contentPane.setForeground ( Color.BLACK );

       contentPane.setLayout ( new GridLayout ( 2, 1 ) );

       contentPane.add ( dateLabel );

       contentPane.add ( timeLabel );

       timeLabel.addComponentListener ( this );
     }

     public synchronized void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       thread = new Thread ( this );

       int  priority = thread.getPriority ( ) - 1;

       if ( priority >= Thread.MIN_PRIORITY )
       {
         thread.setPriority ( priority );
       }

       thread.setDaemon ( true );

       thread.start ( );
     }

     public synchronized void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       Thread  thread = this.thread;

       if ( thread != null )
       {
         this.thread = null;

         thread.interrupt ( );
       }
     }

     public synchronized void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       stop ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void run ( )
     //////////////////////////////////////////////////////////////////////
     {
       dateLabel.setText ( " " );

       timeLabel.setText ( " " );

       resizeDateText = ACTIVE_DATE_TEXT;

       resizeTimeText = ACTIVE_TIME_TEXT;

       resizeFonts ( );

       Thread  thread = Thread.currentThread ( );

       try
       {
         while ( thread == this.thread )
         {
           String  dateTime
             = DateFormatLib.toIsoDateFormat ( new Date ( ) );

           dateLabel.setText (
             dateTime.substring ( 0, dateTime.length ( ) - 9 ) );

           timeLabel.setText (
             dateTime.substring ( dateTime.length ( ) - 8 ) );

           thread.sleep ( 100 );
         }
       }
       catch ( InterruptedException  ex )
       {
       }
       finally
       {
         dateLabel.setText ( " " );

         timeLabel.setText ( " " );

         resizeDateText = IDLE_DATE_TEXT;

         resizeTimeText = IDLE_TIME_TEXT;

         resizeFonts ( );

         dateLabel.setText ( IDLE_DATE_TEXT );

         timeLabel.setText ( IDLE_TIME_TEXT );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentListener methods
     //////////////////////////////////////////////////////////////////////

     public void  componentResized ( ComponentEvent  componentEvent )
     //////////////////////////////////////////////////////////////////////
     {
       resizeFonts ( );
     }

     public void  componentHidden ( ComponentEvent  componentEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  componentMoved ( ComponentEvent  componentEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public void  componentShown ( ComponentEvent  componentEvent )
     //////////////////////////////////////////////////////////////////////
     {
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private synchronized void  resizeFonts ( )
     //////////////////////////////////////////////////////////////////////
     {
       resizeFont ( dateLabel, resizeDateText );

       resizeFont ( timeLabel, resizeTimeText );
     }

     private synchronized void  resizeFont (
       Component  component,
       String     text )
     //////////////////////////////////////////////////////////////////////
     {
       Rectangle  bounds = component.getBounds ( );

       FontLib.setMaxFont (
         component,
         text,
         FONT_NAME,
         FONT_STYLE,
         0.9 * bounds.width,
         0.9 * bounds.height );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
