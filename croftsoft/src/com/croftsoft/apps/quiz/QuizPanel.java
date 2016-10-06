     package com.croftsoft.apps.quiz;

     import java.awt.BorderLayout;
     import java.awt.Color;
     import javax.swing.JMenu;
     import javax.swing.JMenuBar;
     import javax.swing.JPanel;
     import javax.swing.JTabbedPane;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
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

     public final class  QuizPanel
       extends JPanel
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final QuizModel  quizModel;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  QuizPanel (
       QuizModel  quizModel,
       Color      panelBackgroundColor,
       Color      textFieldBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ), true ); // isDoubleBuffered

       NullArgumentException.check ( this.quizModel = quizModel );

/*
       JMenuBar  jMenuBar = new JMenuBar ( );

       jMenuBar.add ( new JMenu ( "File" ) );

       jMenuBar.add ( new JMenu ( "About" ) );

       add ( jMenuBar, BorderLayout.NORTH );
*/

       JTabbedPane  jTabbedPane = new JTabbedPane ( );

       jTabbedPane.addTab ( "Edit Quiz", new QuizEditPanel ( quizModel ) );

       jTabbedPane.addTab ( "Take Quiz", new QuizTakePanel ( quizModel ) );

       add ( jTabbedPane, BorderLayout.CENTER );
     }

     public  QuizPanel ( QuizModel  quizModel )
     //////////////////////////////////////////////////////////////////////
     {
       this ( quizModel, null, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }