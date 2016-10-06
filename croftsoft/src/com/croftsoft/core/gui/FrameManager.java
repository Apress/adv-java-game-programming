     package com.croftsoft.core.gui;
     
     import java.awt.*;

     import com.croftsoft.core.lang.ClassLib;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     
     /*********************************************************************
     * Lifecycle management for a Frame.
     *
     * <p />
     *
     * @version
     *   2001-07-24
     * @since
     *   2001-05-17
     * @author
     *   <a href="http://www.alumni.caltech.edu/~croft/">David W. Croft</a>
     *********************************************************************/

     public final class  FrameManager
       implements Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private Frame      frame;

     private String     title;

     private String     iconName;

     private Dimension  size;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       FrameManager  frameManager = new FrameManager ( "Frame Manager" );

       frameManager.init ( );

       frameManager.getFrame ( ).addWindowListener (
         new ShutdownWindowListener ( frameManager ) );

       frameManager.start ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  FrameManager (
       Frame      frame,
       String     title,
       Dimension  size,
       String     iconName )
     //////////////////////////////////////////////////////////////////////
     {
       this.frame    = frame;

       this.title    = title;

       this.size     = size;

       this.iconName = iconName;
     }

     public  FrameManager ( String  title )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null, title, null, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( frame == null )
       {
         frame = new Frame ( );
       }

       if ( title != null )
       {
         frame.setTitle ( title );
       }

       if ( iconName != null )
       {
         try
         {
           Image  frameIconImage
             = ClassLib.getResourceAsImage ( getClass ( ), iconName );

           frame.setIconImage ( frameIconImage );
         }
         catch ( Exception  ex )
         {
           ex.printStackTrace ( );
         }
       }

       if ( size == null )
       {
         size = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
       }

       WindowLib.centerOnScreen ( frame, size );
     }
     
     public synchronized void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       frame.show ( );
     }
     
     public synchronized void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       frame.setVisible ( false );
     }

     public synchronized void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       stop ( );

       frame.dispose ( );

       frame = null;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Frame  getFrame ( ) { return frame; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }