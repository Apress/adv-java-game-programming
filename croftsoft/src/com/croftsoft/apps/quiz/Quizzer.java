     package com.croftsoft.apps.quiz;

     import java.awt.*;
     import javax.swing.JFrame;
     import javax.swing.WindowConstants;

     import com.croftsoft.core.gui.FrameManager;
     import com.croftsoft.core.gui.ShutdownWindowListener;
     import com.croftsoft.core.lang.lifecycle.Destroyable;
     
     /*********************************************************************
     * The main class for Quizzer.
     *
     * <p />
     *
     * @version
     *   2001-10-03
     * @since
     *   2001-07-10
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Quizzer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       QuizModel  quizModel = new QuizModel ( );

       quizModel.init ( );

       JFrame  jFrame = new JFrame ( );

       jFrame.getContentPane ( ).add ( new QuizPanel ( quizModel ) );

       jFrame.setDefaultCloseOperation (
         WindowConstants.DO_NOTHING_ON_CLOSE );

       Dimension  size = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

       size.width  *= 0.8;

       size.height *= 0.8;

       FrameManager  frameManager = new FrameManager (
         jFrame, "Quizzer", size, null );

       jFrame.addWindowListener ( new ShutdownWindowListener (
         new Destroyable [ ] { quizModel, frameManager } ) );

       frameManager.init  ( );

       frameManager.start ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }