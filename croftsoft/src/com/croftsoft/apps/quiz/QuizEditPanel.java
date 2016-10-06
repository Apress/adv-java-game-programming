     package com.croftsoft.apps.quiz;

     import java.awt.*;
     import java.awt.event.*;
     import javax.swing.*;

//   import com.croftsoft.core.gui.LabeledFieldsJPanel;
     import com.croftsoft.core.gui.ButtonPanel2;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     *
     * <p />
     *
     * @version
     *   2001-07-11
     * @since
     *   2001-07-10
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  QuizEditPanel
       extends JPanel
       implements ActionListener, Runnable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final QuizModel  quizModel;

     private final JButton  nextJButton = new JButton ( "Next"     );

     private final JButton  prevJButton = new JButton ( "Previous" );

     private final JButton  saveJButton = new JButton ( "Save"     );

     private final JButton [ ]  jButtons
       = new JButton [ ] { prevJButton, saveJButton, nextJButton };

     private final QuizItemPanel  quizItemPanel;

     //

     private QuizItem [ ]  quizItems;

     private int  quizItemIndex;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  QuizEditPanel (
       QuizModel  quizModel,       
       Color      panelBackgroundColor,
       Color      textFieldBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ), true ); // isDoubleBuffered

       NullArgumentException.check ( this.quizModel = quizModel );

// ignoring colors for now

       quizItemPanel = new QuizItemPanel ( );

       Quiz  quiz = quizModel.getQuiz ( );

       if ( quiz != null )
       {
         quizItems = quiz.getQuizItems ( );
       }
       else
       {
         throw new IllegalArgumentException ( "quizModel has null quiz" );
       }

       if ( ( quizItems != null    )
         && ( quizItems.length > 0 ) )
       {
         quizItemPanel.setQuizItem ( quizItems [ 0 ] );
       }       

       add ( quizItemPanel, BorderLayout.CENTER );

       ButtonPanel2  buttonPanel2 = new ButtonPanel2 ( jButtons );

       for ( int  i = 0; i < jButtons.length; i++ )
       {
         JButton  jButton = jButtons [ i ];

         jButton.setEnabled ( false );

         jButton.addActionListener ( this );
       }

       if ( quizItems.length > 1 )
       {
         nextJButton.setEnabled ( true );
       }

       add ( buttonPanel2, BorderLayout.SOUTH );
     }

     public  QuizEditPanel ( QuizModel  quizModel )
     //////////////////////////////////////////////////////////////////////
     {
       this ( quizModel, null, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = actionEvent.getSource ( );

       if ( source == nextJButton )
       {
         if ( quizItemIndex < quizItems.length - 1 )
         {
           quizItemIndex++;

           SwingUtilities.invokeLater ( this );
         }

         nextJButton.setEnabled ( quizItemIndex < quizItems.length - 1 );

         prevJButton.setEnabled ( true );
       }
       else if ( source == prevJButton )
       {
         if ( quizItemIndex > 0 )
         {
           quizItemIndex--;

           SwingUtilities.invokeLater ( this );
         }

         prevJButton.setEnabled ( quizItemIndex > 0 );

         nextJButton.setEnabled ( true );
       }
       else
       {
         System.out.println ( source );
       }
     }
     
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  run ( )
     //////////////////////////////////////////////////////////////////////
     {
       quizItemPanel.setQuizItem ( quizItems [ quizItemIndex ] );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }