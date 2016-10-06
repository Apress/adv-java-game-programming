     package com.croftsoft.apps.fraction;
     
     import java.applet.*;
     import java.awt.*;
     import java.awt.event.*;
     import java.awt.geom.*;
     import java.net.URL;
     import java.util.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.animation.AnimatedComponent;
     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.animation.factory.DefaultAnimationFactory;
     import com.croftsoft.core.animation.Sprite;
     import com.croftsoft.core.animation.animator.TileAnimator;
     import com.croftsoft.core.animation.clock.SystemClock;
     import com.croftsoft.core.animation.collector.*;
     import com.croftsoft.core.animation.sprite.IconSprite;
     import com.croftsoft.core.animation.sprite.TextSprite;
     import com.croftsoft.core.animation.updater.FrameRateUpdater;
     import com.croftsoft.core.awt.font.FontLib;
     import com.croftsoft.core.gui.FrameLib;
     import com.croftsoft.core.lang.ClassLib;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.math.MathConstants;

     /*********************************************************************
     * Main Fraction class.
     *
     * @version
     *   2003-09-29
     * @since
     *   2002-04-28
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Fraction
       extends JApplet
       implements ComponentAnimator, Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {
       
     //////////////////////////////////////////////////////////////////////
     // Applet constants
     //////////////////////////////////////////////////////////////////////

     private static final String  VERSION
       = "2003-09-29";

     private static final String  TITLE
       = "CroftSoft Fraction Action";

     private static final String  INFO
       = "\n" + TITLE + "\n"
       + "Version " + VERSION + "\n"
       + CroftSoftConstants.COPYRIGHT + "\n"
       + CroftSoftConstants.DEFAULT_LICENSE + "\n"
       + CroftSoftConstants.HOME_PAGE + "\n";

     //////////////////////////////////////////////////////////////////////
     // Frame constants
     //////////////////////////////////////////////////////////////////////

     private static final String  FRAME_TITLE
       = TITLE;

     private static final String  FRAME_ICON_FILENAME
       = "/images/croftsoft.png";
       
     private static final Dimension  FRAME_SIZE
       = null;

     private static final String  SHUTDOWN_CONFIRMATION_PROMPT
       = "Close " + TITLE + "?";

     //////////////////////////////////////////////////////////////////////
     // animation constants
     //////////////////////////////////////////////////////////////////////

     private static final String  INIT_ANSWER_TEXT = "Fraction Action";

     /** frames per second */
     private static final double  FRAME_RATE = 30.0;

     private static final String  MEDIA_DIR = "media/fraction/";

     private static final String  BONGO_AUDIO_FILENAME
       = MEDIA_DIR + "bongo.wav";

     private static final String  POP_AUDIO_FILENAME
       = MEDIA_DIR + "pop.wav";

     private static final Color   BACKGROUND_COLOR
       = Color.BLACK;

     private static final Color   FLOOR_COLOR
       = Color.GREEN;

     private static final Color   MOVEMENT_LINE_COLOR
       = Color.RED;

     private static final Color   QUESTION_COLOR
       = Color.GREEN;

     private static final Color   ANSWER_COLOR
       = Color.BLUE;

     private static final Color   SCORE_COLOR
       = Color.RED;

     private static final int     FLOORS = 12;

     private static final String  FONT_NAME = "Arioso";

     private static final int     FONT_STYLE = Font.BOLD;

     private static final String  FONT_NAME_SCORE = "Georgia";

     //////////////////////////////////////////////////////////////////////
     // instance variables
     //////////////////////////////////////////////////////////////////////

     private AnimatedComponent   animatedComponent;

     private TextSprite          questionTextSprite;

     private TextSprite          answerTextSprite;

     private TextSprite          scoreTextSprite;

     private FractionHeroSprite  fractionHeroSprite;

     private Rectangle           heroBounds;

     private FractionDoor [ ]    fractionDoors;

     private int                 floorHeight;

     private Rectangle           bounds;

     private Rectangle           paintBounds0;

     private Rectangle           paintBounds1;

     private FractionQuestion    fractionQuestion;

     private AudioClip           bongoAudioClip;

     private AudioClip           popAudioClip;

     private int                 score;

     /** pixels per frame */
     private int                 spriteVelocity;

     private boolean             boundsInitialized;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       JFrame  jFrame = new JFrame ( FRAME_TITLE );

       try
       {
         jFrame.setIconImage ( ClassLib.getResourceAsImage (
           Fraction.class, FRAME_ICON_FILENAME ) );
       }
       catch ( Exception  ex )
       {
       }

       Fraction  fraction = new Fraction ( );

       jFrame.setContentPane ( fraction );

       FrameLib.launchJFrameAsDesktopApp (
         jFrame,
         new Lifecycle [ ] { fraction },
         FRAME_SIZE,
         SHUTDOWN_CONFIRMATION_PROMPT );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getAppletInfo ( )
     //////////////////////////////////////////////////////////////////////
     {
       return INFO;
     }

     //////////////////////////////////////////////////////////////////////
     // interface Lifecycle methods
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( INFO );

       animatedComponent = new AnimatedComponent ( this, FRAME_RATE );

       animatedComponent.addComponentListener (
         new ComponentAdapter ( )
         {
           public void  componentResized ( ComponentEvent  componentEvent )
           {
             resetBounds ( );
           }
         } );

       Container  contentPane = getContentPane ( );

       contentPane.setLayout ( new BorderLayout ( ) );

       contentPane.add ( animatedComponent, BorderLayout.CENTER );

       validate ( );

       animatedComponent.init ( );

       bounds = animatedComponent.getBounds ( );

       paintBounds0 = new Rectangle ( );

       paintBounds1 = new Rectangle ( );

       heroBounds = new Rectangle ( );

       fractionHeroSprite = new FractionHeroSprite ( );

       animatedComponent.addMouseListener ( fractionHeroSprite );

       animatedComponent.addMouseMotionListener ( fractionHeroSprite );

       URL  bongoAudioURL = getClass ( ).getClassLoader ( )
         .getResource ( BONGO_AUDIO_FILENAME );

       bongoAudioClip = Applet.newAudioClip ( bongoAudioURL );

       URL  popAudioURL = getClass ( ).getClassLoader ( )
         .getResource ( POP_AUDIO_FILENAME );

       popAudioClip = Applet.newAudioClip ( popAudioURL );

       //

       fractionQuestion = new FractionQuestion ( FLOORS );

       questionTextSprite = new TextSprite ( TITLE );

       questionTextSprite.setColor ( QUESTION_COLOR );

       answerTextSprite = new TextSprite ( INIT_ANSWER_TEXT );

       answerTextSprite.setColor ( ANSWER_COLOR );

       answerTextSprite.setBackgroundColor ( BACKGROUND_COLOR );

       scoreTextSprite = new TextSprite ( "Score:  0" );

       scoreTextSprite.setColor ( SCORE_COLOR );
     }

     public void  start   ( ) { animatedComponent.start   ( ); }

     public void  stop    ( ) { animatedComponent.stop    ( ); }

     public void  destroy ( ) { animatedComponent.destroy ( ); }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !boundsInitialized )
       {
         resetBounds ( );
       }

       heroBounds.setBounds ( bounds );

       fractionHeroSprite.getPaintBounds ( paintBounds0 );

       boolean  crossedDoor = false;

       for ( int  j = 0; j < spriteVelocity; j++ )
       {
         int  x = fractionHeroSprite.getX ( );

         int  y = fractionHeroSprite.getY ( );

         for ( int  i = 0; i < fractionDoors.length; i++ )
         {
           FractionDoor  fractionDoor = fractionDoors [ i ];

           if ( fractionDoor.contains ( x, y ) )
           {
             crossedDoor = true;

             int  localHorizontalMovementY
               = fractionDoor.getHorizontalMovementY ( );

             int  localVerticalMovementX
               = fractionDoor.getVerticalMovementX ( );

             fractionHeroSprite.setHorizontalMovementY (
               localHorizontalMovementY );

             fractionHeroSprite.setVerticalMovementX (
               localVerticalMovementX );

             fractionHeroSprite.setY ( localHorizontalMovementY );

             fractionHeroSprite.setX ( localVerticalMovementX );

             if ( !fractionQuestion.getFirst ( ) )
             {
               if ( ( fractionQuestion.getFirstDenominator ( )
                 == fractionDoor.getDenominator ( ) )
                 && ( fractionQuestion.getFirstNumerator ( )
                 == fractionDoor.getNumerator ( ) ) )
               {
                 fractionQuestion.setFirst ( true );

                 fractionDoor.setOpen ( );

                 popAudioClip.play ( );

                 setHighlighted (
                   fractionQuestion.getFirstAnswerNumerator ( ),
                   fractionQuestion.getCommonDenominator ( ) );
               }
             }

             if ( fractionQuestion.getFirst ( )
               && !fractionQuestion.getSecond ( ) )
             {
               if ( ( fractionQuestion.getCommonDenominator ( )
                 == fractionDoor.getDenominator ( ) )
                 && ( fractionQuestion.getFirstAnswerNumerator ( )
                 == fractionDoor.getNumerator ( ) ) )
               {
                 fractionQuestion.setSecond ( true );

                 fractionDoor.setOpen ( );

                 popAudioClip.play ( );

                 setHighlighted (
                   fractionQuestion.getThirdAnswerNumerator ( ),
                   fractionQuestion.getCommonDenominator ( ) );
               }
             }

             if ( fractionQuestion.getSecond ( )
               && !fractionQuestion.getThird ( ) )
             {
               if ( ( fractionQuestion.getCommonDenominator ( )
                 == fractionDoor.getDenominator ( ) )
                 && ( fractionQuestion.getThirdAnswerNumerator ( )
                 == fractionDoor.getNumerator ( ) ) )
               {
                 fractionQuestion.setThird ( true );

                 fractionDoor.setOpen ( );

                 popAudioClip.play ( );

                 setHighlighted (
                   fractionQuestion.getThirdNumerator ( ),
                   fractionQuestion.getThirdDenominator ( ) );
               }
             }

             if ( fractionQuestion.getThird ( )
               && !fractionQuestion.getFourth ( ) )
             {
               if ( ( fractionQuestion.getThirdDenominator ( )
                 == fractionDoor.getDenominator ( ) )
                 && ( fractionQuestion.getThirdNumerator ( )
                 == fractionDoor.getNumerator ( ) ) )
               {
                 fractionQuestion.setFourth ( true );

                 bongoAudioClip.play ( );

                 scoreTextSprite.setText ( "Score:  " + ++score );

                 answerTextSprite.setText (
                   fractionQuestion.getFirstNumerator ( )
                   + "/"
                   + fractionQuestion.getFirstDenominator ( )
                   + " + "
                   + fractionQuestion.getSecondNumerator ( )
                   + "/"
                   + fractionQuestion.getSecondDenominator ( )
                   + " = " 
                   + ( fractionQuestion.getFourth ( )
                   ? fractionQuestion.getThirdNumerator ( ) + "/"
                   + fractionQuestion.getThirdDenominator ( ) : "?/?" ) );

                 reset ( );
               }
             }

             break;
           }
         }

         fractionHeroSprite.update ( component );
       }

       if ( crossedDoor )
       {
         component.repaint ( );
       }
       else
       {
         fractionHeroSprite.getPaintBounds ( paintBounds1 );

         paintBounds1.add ( paintBounds0 );

         component.repaint ( paintBounds1 );
       }

       String  firstAnswer
         = fractionQuestion.getFirst ( )
           ? ( fractionQuestion.getFirstAnswerNumerator ( ) + "/"
           + fractionQuestion.getCommonDenominator ( ) )
           : "?/?";

       String  secondAnswer
         = fractionQuestion.getSecond ( )
           ? ( fractionQuestion.getSecondAnswerNumerator ( ) + "/"
           + fractionQuestion.getCommonDenominator ( ) )
           : "?/?";

       String  thirdAnswer
         = fractionQuestion.getSecond ( )
           ? ( fractionQuestion.getThirdAnswerNumerator ( ) + "/"
           + fractionQuestion.getCommonDenominator ( ) )
           : "?/?";

       questionTextSprite.setText ( 
         fractionQuestion.getFirstNumerator ( )
         + "/"
         + fractionQuestion.getFirstDenominator ( )
         + " + "
         + fractionQuestion.getSecondNumerator ( )
         + "/"
         + fractionQuestion.getSecondDenominator ( )
         + " = " 
         + firstAnswer
         + " + "
         + secondAnswer
         + " = "
         + thirdAnswer
         + ( ( fractionQuestion.getThirdNumerator ( )
               == fractionQuestion.getThirdAnswerNumerator ( ) )
           && ( fractionQuestion.getThirdDenominator ( )
                == fractionQuestion.getCommonDenominator ( ) )
           ? ""
           : " = ?/?" ) );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics2D )
     //////////////////////////////////////////////////////////////////////
     {
       graphics2D.setColor ( BACKGROUND_COLOR );

       graphics2D.fillRect ( 0, 0, bounds.width, bounds.height );

       graphics2D.setColor ( MOVEMENT_LINE_COLOR );

       int  verticalMovementX
         = fractionHeroSprite.getVerticalMovementX ( );

       int  horizontalMovementY
         = fractionHeroSprite.getHorizontalMovementY ( );

       graphics2D.drawLine (
         verticalMovementX, 0, verticalMovementX, bounds.height - 1);

       for ( int  i = 0; i < fractionDoors.length; i++ )
       {
         fractionDoors [ i ].paint ( component, graphics2D );
       }

       for ( int  i = 1; i <= FLOORS; i++ )
       {
         int  y = i * floorHeight;

         graphics2D.setColor ( FLOOR_COLOR );

         graphics2D.drawLine ( 0, y, bounds.width, y );
       }

       graphics2D.setColor ( MOVEMENT_LINE_COLOR );

       graphics2D.drawLine (
         0,
         horizontalMovementY,
         bounds.width - 1,
         horizontalMovementY );

       questionTextSprite.paint ( component, graphics2D );

       answerTextSprite.paint ( component, graphics2D );

       scoreTextSprite.paint ( component, graphics2D );

       fractionHeroSprite.paint ( component, graphics2D );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private void  resetBounds ( )
     //////////////////////////////////////////////////////////////////////
     {
       animatedComponent.getBounds ( bounds );

       score = 0;

       scoreTextSprite.setText ( "Score:  0" );

       answerTextSprite.setText ( INIT_ANSWER_TEXT );

       // ( 600 / 5 ) / 30 = 4 pixels per frame = 120 pixels per second

       spriteVelocity
         = ( int ) Math.ceil ( ( bounds.width / 5 ) / FRAME_RATE );

       floorHeight = bounds.height / ( FLOORS + 1 );

       FontLib.setMaxFont (
         animatedComponent,
         "Score:  " + Integer.toString ( Integer.MAX_VALUE ),
         FONT_NAME_SCORE,
         FONT_STYLE,
         bounds.width / 2,
         floorHeight );

       scoreTextSprite.setFont ( animatedComponent.getFont ( ) );

       answerTextSprite.setFont ( animatedComponent.getFont ( ) );

       scoreTextSprite.setY ( floorHeight * FLOORS + floorHeight );

       questionTextSprite.setY ( 3 * floorHeight / 4 );

       answerTextSprite.setY ( floorHeight * FLOORS + floorHeight );

       FontLib.setMaxFont (
         animatedComponent,
         "?/? + ?/? = ?/? + ?/? = ?/? = ?/?",
         FONT_NAME_SCORE,
         FONT_STYLE,
         3 * bounds.width / 4,
         floorHeight );

       questionTextSprite.setFont ( animatedComponent.getFont ( ) );

       int  doorHeight = ( int ) ( floorHeight * 0.9 );

       int  doorSpacing = bounds.width / ( FLOORS + 1 );

       int  doorWidth
         = ( int ) ( doorHeight / MathConstants.GOLDEN_RATIO );

       fractionHeroSprite.setDiameter ( doorHeight );

       int  doorOffset = ( doorSpacing - doorWidth ) / 2;

       questionTextSprite.setX ( doorWidth + 2 * doorOffset );

       answerTextSprite.setX (
         bounds.width / 2 + doorWidth / 2 + doorOffset );

       scoreTextSprite.setX ( doorWidth + 2 * doorOffset );

       FontLib.setMaxFont (
         animatedComponent,
         Integer.toString ( FLOORS ),
         FONT_NAME,
         FONT_STYLE,
         doorWidth,
         doorHeight / 2 );

       java.util.List  fractionDoorList = new ArrayList ( );

       Graphics  graphics = animatedComponent.getGraphics ( );

       FontMetrics  fontMetrics = graphics.getFontMetrics ( );

       for ( int  i = 1; i <= FLOORS; i++ )
       {
         int  y = ( i - 1 ) * floorHeight;

         Rectangle2D  denominatorTextBounds = fontMetrics.getStringBounds (
           Integer.toString ( i ), graphics );

         int  denominatorY
           = y + floorHeight - doorHeight / 2
           - ( int ) denominatorTextBounds.getY ( );

         for ( int  j = 0; j <= i; j++ )
         {
           int  x = ( j * doorSpacing * FLOORS ) / i + doorOffset;

           Rectangle  rectangle = new Rectangle (
             x,
             y + floorHeight - doorHeight,
             doorWidth,
             doorHeight );

           Rectangle2D  numeratorTextBounds
             = fontMetrics.getStringBounds (
             Integer.toString ( j ), graphics );

           int  numeratorX = ( int )
             ( x + ( doorWidth - numeratorTextBounds.getWidth ( ) ) / 2 );

           int  numeratorY
             = y + floorHeight - doorHeight / 2
             - ( int ) ( numeratorTextBounds.getHeight ( )
             + numeratorTextBounds.getY ( ) );

           int  denominatorX = x + ( int )
             ( ( doorWidth - denominatorTextBounds.getWidth ( ) ) / 2 );

           fractionDoorList.add (
             new FractionDoor (
               rectangle,
               j,
               i,
               numeratorX,
               numeratorY,
               denominatorX,
               denominatorY,
               x + 4,
               y + floorHeight - doorHeight / 2,
               x + doorWidth - 4,
               y + floorHeight - doorHeight / 2,
               y + floorHeight,
               x + doorWidth / 2 ) );
         }
       }

       fractionDoors = ( FractionDoor [ ] )
         fractionDoorList.toArray ( new FractionDoor [ 0 ] );

       int  verticalMovementX
         = fractionDoors [ 0 ].getVerticalMovementX   ( );

       int  horizontalMovementY
         = fractionDoors [ 0 ].getHorizontalMovementY ( );

       fractionHeroSprite.setX ( verticalMovementX );

       fractionHeroSprite.setY ( horizontalMovementY );

       fractionHeroSprite.setVerticalMovementX ( verticalMovementX );

       fractionHeroSprite.setHorizontalMovementY ( horizontalMovementY );

       reset ( );

       boundsInitialized = true;

       animatedComponent.repaint ( );
     }

     private void  reset ( )
     //////////////////////////////////////////////////////////////////////
     {
       fractionQuestion.reset ( );

       for ( int  i = 0; i < fractionDoors.length; i++ )
       {
         fractionDoors [ i ].reset ( );
       }
    
       setHighlighted (
         fractionQuestion.getFirstNumerator   ( ),
         fractionQuestion.getFirstDenominator ( ) );

       animatedComponent.repaint ( );
     }

     private void  setHighlighted (
       int  numerator,
       int  denominator )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < fractionDoors.length; i++ )
       {
         FractionDoor  fractionDoor = fractionDoors [ i ];

         if ( ( denominator == fractionDoor.getDenominator ( ) )
           && ( numerator   == fractionDoor.getNumerator   ( ) ) )
         {
           fractionDoor.setHighlighted ( );

           break;
         }
       }

       animatedComponent.repaint ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
