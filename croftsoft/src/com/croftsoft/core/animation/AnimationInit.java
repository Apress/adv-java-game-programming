     package com.croftsoft.core.animation;

     import java.awt.*;
     import java.io.*;
     import java.beans.*;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.animation.animator.*;
     import com.croftsoft.core.animation.painter.*;
     import com.croftsoft.core.animation.updater.*;
     import com.croftsoft.core.lang.Testable;
     
     /*********************************************************************
     * Animation initializer.
     *
     * @version
     *   2003-08-02
     * @since
     *   2003-03-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  AnimationInit
       implements Serializable, Testable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     public static final String             DEFAULT_APPLET_INFO
       = CroftSoftConstants.DEFAULT_APPLET_INFO;

     public static final Color              DEFAULT_BACKGROUND_COLOR
       = Color.WHITE;

     public static final Cursor             DEFAULT_CURSOR
       = null; // new Cursor ( Cursor.CROSSHAIR_CURSOR );
       
     public static final Font               DEFAULT_FONT
       = new Font ( "Courier New", Font.BOLD, 10 );

     public static final Color              DEFAULT_FOREGROUND_COLOR
       = Color.BLACK;

     public static final String             DEFAULT_FRAME_ICON_FILENAME
       = null;

     public static final Double             DEFAULT_FRAME_RATE
       = null;

     public static final Dimension          DEFAULT_FRAME_SIZE
       = null;

     public static final String             DEFAULT_FRAME_TITLE
       = null;

     public static final String  DEFAULT_SHUTDOWN_CONFIRMATION_PROMPT
       = "Exit?";

     public static final ArrayComponentUpdater
       DEFAULT_ARRAY_COMPONENT_UPDATER = new ArrayComponentUpdater ( );

     public static final ArrayComponentPainter
       DEFAULT_ARRAY_COMPONENT_PAINTER = new ArrayComponentPainter ( );

     //////////////////////////////////////////////////////////////////////
     // instance variables
     //////////////////////////////////////////////////////////////////////

private TextAnimator  textAnimator;

     private String                 appletInfo;

     private ArrayComponentPainter  arrayComponentPainter;

     private ArrayComponentUpdater  arrayComponentUpdater;

     private Color                  backgroundColor;

     private Cursor                 cursor;

     private Font                   font;

     private Color                  foregroundColor;

     private String                 frameIconFilename;

     private Double                 frameRate;

     private Dimension              frameSize;

     private String                 frameTitle;

     private String                 shutdownConfirmationPrompt;

     //////////////////////////////////////////////////////////////////////
     // static methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Test method.
     *********************************************************************/
     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( test ( args ) );
     }

     /*********************************************************************
     * Test method.
     *********************************************************************/
     public static boolean  test ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       final String  TEST = "test";

       try
       {
         AnimationInit  animationInit1 = new AnimationInit ( );

         animationInit1.setAppletInfo ( TEST );

         ArrayComponentPainter  arrayComponentPainter
           = animationInit1.getArrayComponentPainter ( );

         ArrayComponentUpdater  arrayComponentUpdater
           = animationInit1.getArrayComponentUpdater ( );

         ColorPainter  colorPainter = new ColorPainter ( );

         TextAnimator  textAnimator = new TextAnimator ( );

         animationInit1.setTextAnimator ( textAnimator );

         textAnimator.setText ( DEFAULT_APPLET_INFO );

         textAnimator.setDeltaX ( 1 );

         textAnimator.setDeltaY ( 1 );

         arrayComponentPainter.add ( colorPainter );

         arrayComponentPainter.add ( textAnimator );

         arrayComponentUpdater.add ( textAnimator );

         animationInit1.setCursor (
           new Cursor ( Cursor.MOVE_CURSOR ) );

         animationInit1.setBackgroundColor ( Color.BLUE );

         animationInit1.setFont (
           new Font ( "Courier", Font.BOLD, 10 ) );

         animationInit1.setForegroundColor ( Color.RED );

         animationInit1.setFrameIconFilename ( TEST );

         animationInit1.setFrameSize ( new Dimension ( 600, 400 ) );

         animationInit1.setFrameTitle ( TEST );

         animationInit1.setShutdownConfirmationPrompt ( TEST );

         ByteArrayOutputStream  byteArrayOutputStream
           = new ByteArrayOutputStream ( );

         XMLEncoder  xmlEncoder = new XMLEncoder ( byteArrayOutputStream );

         xmlEncoder.writeObject ( animationInit1 );

         xmlEncoder.close ( );

         byte [ ]  xmlBytes = byteArrayOutputStream.toByteArray ( );

         System.out.println ( new String ( xmlBytes ) );

         XMLDecoder  xmlDecoder
           = new XMLDecoder ( new ByteArrayInputStream ( xmlBytes ) );

         AnimationInit  animationInit2
           = ( AnimationInit ) xmlDecoder.readObject ( );

         return true;
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         return false;
       }
     }

     public static AnimationInit  load ( String  filename )
       throws FileNotFoundException
     //////////////////////////////////////////////////////////////////////
     {
       XMLDecoder  xmlDecoder = new XMLDecoder (
         new BufferedInputStream ( new FileInputStream ( filename ) ) );

       AnimationInit  AnimationInit
         = ( AnimationInit ) xmlDecoder.readObject ( );

       xmlDecoder.close ( );

       return AnimationInit;
     }

     public static void  save (
       AnimationInit  animationInit,
       String         filename )
       throws FileNotFoundException
     //////////////////////////////////////////////////////////////////////
     {
       XMLEncoder  xmlEncoder = new XMLEncoder (
         new BufferedOutputStream (
           new FileOutputStream ( filename ) ) );

       AnimationInit  AnimationInit
         = new AnimationInit ( animationInit );

       xmlEncoder.writeObject ( AnimationInit );

       xmlEncoder.close ( );
     }

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  AnimationInit ( AnimationInit  animationInit )
     //////////////////////////////////////////////////////////////////////
     {
       if ( animationInit == null )
       {
         appletInfo                 = DEFAULT_APPLET_INFO;

         arrayComponentPainter      = DEFAULT_ARRAY_COMPONENT_PAINTER;

         arrayComponentUpdater      = DEFAULT_ARRAY_COMPONENT_UPDATER;

         backgroundColor            = DEFAULT_BACKGROUND_COLOR;

         cursor                     = DEFAULT_CURSOR;

         font                       = DEFAULT_FONT;

         foregroundColor            = DEFAULT_FOREGROUND_COLOR;

         frameIconFilename          = DEFAULT_FRAME_ICON_FILENAME;

         frameRate                  = DEFAULT_FRAME_RATE;

         frameSize                  = DEFAULT_FRAME_SIZE;

         frameTitle                 = DEFAULT_FRAME_TITLE;

         shutdownConfirmationPrompt = DEFAULT_SHUTDOWN_CONFIRMATION_PROMPT;
       }
       else
       {
         appletInfo
           = animationInit.getAppletInfo ( );

         arrayComponentPainter
           = animationInit.getArrayComponentPainter ( );

         arrayComponentUpdater
           = animationInit.getArrayComponentUpdater ( );

         backgroundColor
           = animationInit.getBackgroundColor ( );

         cursor
           = animationInit.getCursor ( );

         font
           = animationInit.getFont ( );

         foregroundColor
           = animationInit.getForegroundColor ( );

         frameIconFilename
           = animationInit.getFrameIconFilename ( );

         frameRate
           = animationInit.getFrameRate ( );

         frameSize
           = animationInit.getFrameSize ( );

         frameTitle
           = animationInit.getFrameTitle ( );

         shutdownConfirmationPrompt
           = animationInit.getShutdownConfirmationPrompt ( );
       }
     }

     public  AnimationInit ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

public TextAnimator  getTextAnimator ( ) { return textAnimator; }

public void  setTextAnimator ( TextAnimator textAnimator )
  { this.textAnimator = textAnimator; }

     public String  getAppletInfo ( )
     //////////////////////////////////////////////////////////////////////
     {
       return appletInfo;
     }

     public ArrayComponentPainter  getArrayComponentPainter ( )
     //////////////////////////////////////////////////////////////////////
     {
       return arrayComponentPainter;
     }

     public ArrayComponentUpdater  getArrayComponentUpdater ( )
     //////////////////////////////////////////////////////////////////////
     {
       return arrayComponentUpdater;
     }
       
     public Color  getBackgroundColor ( )
     //////////////////////////////////////////////////////////////////////
     {
       return backgroundColor;
     }

     public Cursor  getCursor ( )
     //////////////////////////////////////////////////////////////////////
     {
       return cursor;
     }

     public Font  getFont ( )
     //////////////////////////////////////////////////////////////////////
     {
       return font;
     }

     public Color  getForegroundColor ( )
     //////////////////////////////////////////////////////////////////////
     {
       return foregroundColor;
     }

     public String  getFrameIconFilename ( )
     //////////////////////////////////////////////////////////////////////
     {
       return frameIconFilename;
     }
     
     public Double  getFrameRate ( )
     //////////////////////////////////////////////////////////////////////
     {
       return frameRate;
     }

     public Dimension  getFrameSize ( )
     //////////////////////////////////////////////////////////////////////
     {
       return frameSize;
     }

     public String  getFrameTitle ( )
     //////////////////////////////////////////////////////////////////////
     {
       return frameTitle;
     }

     public String  getShutdownConfirmationPrompt ( )
     //////////////////////////////////////////////////////////////////////
     {
       return shutdownConfirmationPrompt;
     }
       
     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setAppletInfo ( String  appletInfo )
     //////////////////////////////////////////////////////////////////////
     {
       this.appletInfo = appletInfo;
     }

     public void  setArrayComponentPainter (
       ArrayComponentPainter  arrayComponentPainter )
     //////////////////////////////////////////////////////////////////////
     {
       this.arrayComponentPainter = arrayComponentPainter;
     }

     public void  setArrayComponentUpdater (
       ArrayComponentUpdater  arrayComponentUpdater )
     //////////////////////////////////////////////////////////////////////
     {
       this.arrayComponentUpdater = arrayComponentUpdater;
     }

     public void  setBackgroundColor ( Color  backgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       this.backgroundColor = backgroundColor;
     }

     public void  setCursor ( Cursor  cursor )
     //////////////////////////////////////////////////////////////////////
     {
       this.cursor = cursor;
     }

     public void  setFont ( Font  font )
     //////////////////////////////////////////////////////////////////////
     {
       this.font = font;
     }

     public void  setForegroundColor ( Color  foregroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       this.foregroundColor = foregroundColor;
     }

     public void  setFrameIconFilename ( String  frameIconFilename )
     //////////////////////////////////////////////////////////////////////
     {
       this.frameIconFilename = frameIconFilename;
     }

     public void  setFrameRate ( Double  frameRate )
     //////////////////////////////////////////////////////////////////////
     {
       this.frameRate = frameRate;
     }

     public void  setFrameSize ( Dimension  frameSize )
     //////////////////////////////////////////////////////////////////////
     {
       this.frameSize = frameSize;
     }

     public void  setFrameTitle ( String  frameTitle )
     //////////////////////////////////////////////////////////////////////
     {
       this.frameTitle = frameTitle;
     }

     public void  setShutdownConfirmationPrompt (
       String  shutdownConfirmationPrompt )
     //////////////////////////////////////////////////////////////////////
     {
       this.shutdownConfirmationPrompt = shutdownConfirmationPrompt;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }