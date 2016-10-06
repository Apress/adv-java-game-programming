     package com.croftsoft.ajgp.http;
     
     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import java.net.*;
     import javax.swing.*;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.animation.AnimatedApplet;
     import com.croftsoft.core.animation.AnimationInit;
     import com.croftsoft.core.animation.animator.TextAnimator;
     import com.croftsoft.core.animation.painter.ColorPainter;
     import com.croftsoft.core.io.StringCoder;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.net.http.msg.HttpMessagePusher;
     import com.croftsoft.core.util.queue.ListQueue;
     import com.croftsoft.core.util.queue.Queue;

     /*********************************************************************
     * Applet that downloads high score from server.
     *
     * @version
     *   2003-06-04
     * @since
     *   2003-06-01
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ScoreApplet
       extends AnimatedApplet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     // applet constants

     private static final String  VERSION
       = "2003-06-04";

     private static final String  TITLE
       = "CroftSoft ScoreApplet";

     private static final String  APPLET_INFO
       = "\n" + TITLE
       + "\n" + "Version " + VERSION
       + "\n" + CroftSoftConstants.COPYRIGHT
       + "\n" + CroftSoftConstants.HOME_PAGE
       + "\n" + CroftSoftConstants.DEFAULT_LICENSE
       + "\n";

     private static final Font   FONT
       = new Font ( "Arioso", Font.BOLD, 30 );

     private static final Color  BACKGROUND_COLOR = Color.BLACK;

     private static final Color  FOREGROUND_COLOR = Color.GREEN;

     private static final int    DELTA_X          = 1;

     private static final int    DELTA_Y          = 1;

     // network constants

     private static final String  CHAR_SET_NAME = StringCoder.UTF_8;

     private static final String  CONTENT_TYPE  = "text/plain";

     private static final String  DEFAULT_HOST  = "localhost";

     private static final String  SERVLET_PATH  = "servlet";

     private static final String  DEFAULT_PATH  = "/score/" + SERVLET_PATH;

     private static final int     DEFAULT_PORT  = 8080;

     private static final String  REQUEST_GET   = "get";

     private static final String  REQUEST_SET   = "set ";

     private static final String  USER_AGENT    = "Score/1.0";

     //

     private final TextAnimator  textAnimator;

     private final Queue         outgoingQueue;

     private final Queue         incomingQueue;
 
     //

     private HttpMessagePusher  httpMessagePusher;

     private long               score;

     private long               highScore;

     private boolean            gameOver;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       launch ( new ScoreApplet ( ) );
     }

     public static AnimationInit  createAnimationInit ( )
     //////////////////////////////////////////////////////////////////////
     {
       AnimationInit  animationInit = new AnimationInit ( );

       animationInit.setAppletInfo      ( APPLET_INFO      );

       animationInit.setBackgroundColor ( BACKGROUND_COLOR );

       animationInit.setFont            ( FONT             );

       animationInit.setForegroundColor ( FOREGROUND_COLOR );

       animationInit.setFrameTitle      ( TITLE            );

       animationInit.setShutdownConfirmationPrompt ( null );

       return animationInit;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ScoreApplet ( )
     //////////////////////////////////////////////////////////////////////
     {
       super ( createAnimationInit ( ) );

       textAnimator = new TextAnimator ( );

       textAnimator.setDeltaX ( DELTA_X );

       textAnimator.setDeltaY ( DELTA_Y );

       outgoingQueue = new ListQueue ( );

       incomingQueue = new ListQueue ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       super.init ( );

       addComponentPainter ( new ColorPainter ( ) );

       addComponentAnimator ( textAnimator );

       URL  codeBaseURL = null;

       try
       {
         codeBaseURL = getCodeBase ( );
       }
       catch ( Exception  ex )
       {
       }

       URL  servletURL = null;

       try
       {
         if ( codeBaseURL != null )
         {
           servletURL = new URL ( codeBaseURL, SERVLET_PATH );
         }
         else
         {
           servletURL = new URL (
             "http", DEFAULT_HOST, DEFAULT_PORT, DEFAULT_PATH );
         }
       }
       catch ( MalformedURLException  ex )
       {
         ex.printStackTrace ( );
       }

       StringCoder  stringCoder = new StringCoder ( CHAR_SET_NAME );

       httpMessagePusher = new HttpMessagePusher (
         outgoingQueue,
         incomingQueue,
         servletURL,
         USER_AGENT,
         CONTENT_TYPE,
         stringCoder,
         stringCoder );

       httpMessagePusher.init ( );

       outgoingQueue.append ( REQUEST_GET );

       animatedComponent.addMouseListener ( 
         new MouseAdapter ( )
         {
           public void  mousePressed ( MouseEvent  mouseEvent )
           {
             gameOver = true;
           }
         } );
     }

     public void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       super.start ( );

       httpMessagePusher.start ( );
     }

     public void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       httpMessagePusher.stop ( );

       super.stop ( );
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       httpMessagePusher.destroy ( );

       super.destroy ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       super.update ( component );

       String  highScoreString = ( String ) incomingQueue.poll ( );

       if ( highScoreString != null )
       {
         try
         {
           highScore = Long.parseLong ( highScoreString );
         }
         catch ( NumberFormatException  ex )
         {
           System.err.println (
             "Unexpected server response:  " + highScoreString );
         }
       }

       if ( gameOver )
       {
         gameOver = false;

         if ( score > highScore )
         {
           highScore = score;

           outgoingQueue.append ( REQUEST_SET + highScore );
         }

         score = 0;
       }
       else
       {
         score++;
       }

       textAnimator.setText (
         "Score:  " + score + "  High Score:  " + highScore );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }