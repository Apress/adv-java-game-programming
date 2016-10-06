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
     *   2001-10-03
     * @since
     *   2001-07-10
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  QuizTakePanel
       extends JPanel
       implements ActionListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final QuizModel  quizModel;

     //

     private final JTextArea  questionTextArea;

     private final JTextArea  guessTextArea;

     private final JTextArea  answerTextArea;

     private final JTextArea  referenceTextArea;

     private final JButton  nextJButton  = new JButton ( "Next"     );

     private final JButton  answerButton = new JButton ( "Answer"   );

     private final JButton  prevJButton  = new JButton ( "Previous" );

     private final JButton [ ]  jButtons
       = new JButton [ ] { prevJButton, answerButton, nextJButton };

     //

     private QuizItem [ ]  quizItems;

     private int  quizItemIndex;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  QuizTakePanel ( QuizModel  quizModel )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ), true ); // isDoubleBuffered

       NullArgumentException.check ( this.quizModel = quizModel );

       JPanel  textAreaPanel
         = new JPanel ( new GridLayout ( 4, 1, 2, 2 ), true );

       textAreaPanel.add ( new JScrollPane (
         questionTextArea = new JTextArea ( ) ) );

       textAreaPanel.add ( new JScrollPane (
         guessTextArea = new JTextArea ( ) ) );

       textAreaPanel.add ( new JScrollPane (
         answerTextArea = new JTextArea ( ) ) );

       textAreaPanel.add ( new JScrollPane (
         referenceTextArea = new JTextArea ( ) ) );

       add ( textAreaPanel, BorderLayout.CENTER );

       //

       questionTextArea.setEditable ( false );

       guessTextArea.setEditable ( true );

       answerTextArea.setEditable ( false );

       referenceTextArea.setEditable ( false );

       //

       ButtonPanel2  buttonPanel2 = new ButtonPanel2 ( jButtons );

       for ( int  i = 0; i < jButtons.length; i++ )
       {
         JButton  jButton = jButtons [ i ];

         jButton.setEnabled ( false );

         jButton.addActionListener ( this );
       }

       add ( buttonPanel2, BorderLayout.SOUTH );

       reset ( );
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

           setQuizItem ( quizItemIndex );
         }

         nextJButton.setEnabled ( quizItemIndex < quizItems.length - 1 );

         prevJButton.setEnabled ( true );
       }
       else if ( source == prevJButton )
       {
         if ( quizItemIndex > 0 )
         {
           quizItemIndex--;

           setQuizItem ( quizItemIndex );
         }

         prevJButton.setEnabled ( quizItemIndex > 0 );

         nextJButton.setEnabled ( true );
       }
       else if ( source == answerButton )
       {
         answerButton.setEnabled ( false );

         answerTextArea.setEnabled ( true );

         answerTextArea.setText (
           quizItems [ quizItemIndex ].getAnswer ( ) );

         referenceTextArea.setEnabled ( true );

         referenceTextArea.setText (
           quizItems [ quizItemIndex ].getReference ( ) );
       }
       else
       {
         System.out.println ( source );
       }
     }
     
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private synchronized void  reset ( )
     //////////////////////////////////////////////////////////////////////
     {
       quizItemIndex = 0;

       prevJButton.setEnabled ( false );

       Quiz  quiz = quizModel.getQuiz ( );

       if ( quiz != null )
       {
         quizItems = quiz.getQuizItems ( );
       }
       else
       {
         return;
       }

       if ( ( quizItems == null )
         || ( quizItems.length < 1 ) )
       {
         return;
       }

       if ( quizItems.length > 1 )
       {
         nextJButton.setEnabled ( true );
       }

       setQuizItem ( 0 );
     }

     private void  setQuizItem ( int  index )
     //////////////////////////////////////////////////////////////////////
     {
       QuizItem  quizItem = quizItems [ index ];

       questionTextArea.setText ( quizItem.getQuestion ( ) );

       guessTextArea.setText ( "" );

       answerTextArea.setText ( "" );

       referenceTextArea.setText ( "" );

       answerTextArea.setEnabled ( false );

       referenceTextArea.setEnabled ( false );

       answerButton.setEnabled ( true );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }