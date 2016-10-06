     package com.croftsoft.apps.quiz;

     import java.awt.*;
     import javax.swing.*;

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

     public final class  QuizItemPanel
       extends JPanel
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

//   private QuizItem  quizItem;

     private JTextArea  questionJTextArea;

     private JTextArea  answerJTextArea;

     private JTextArea  referenceJTextArea;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  QuizItemPanel (
       Color  panelBackgroundColor,
       Color  textFieldBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new GridBagLayout ( ), true ); // isDoubleBuffered

// the panels currently ignore the colors

       questionJTextArea  = new JTextArea ( );

       answerJTextArea    = new JTextArea ( );

       referenceJTextArea = new JTextArea ( );

       GridBagConstraints  gridBagConstraints = new GridBagConstraints ( );

       gridBagConstraints.fill = GridBagConstraints.BOTH;

       gridBagConstraints.weightx = 1.0;

       gridBagConstraints.gridy = 0;

       gridBagConstraints.weighty = 1.0;

       add ( new JScrollPane ( questionJTextArea  ), gridBagConstraints );

       gridBagConstraints.gridy = 1;

       gridBagConstraints.weighty = 3.0;

       add ( new JScrollPane ( answerJTextArea    ), gridBagConstraints );

       gridBagConstraints.gridy = 2;

       gridBagConstraints.weighty = 1.0;

       add ( new JScrollPane ( referenceJTextArea ), gridBagConstraints );
     }

     public  QuizItemPanel ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setQuizItem ( QuizItem  quizItem )
     //////////////////////////////////////////////////////////////////////
     {
       String  question  = null;

       String  answer    = null;

       String  reference = null;

       if ( quizItem != null )
       {
         question  = quizItem.getQuestion  ( );

         answer    = quizItem.getAnswer    ( );

         reference = quizItem.getReference ( );
       }
       
       questionJTextArea .setText ( question  != null ? question  : "" );

       answerJTextArea   .setText ( answer    != null ? answer    : "" );

       referenceJTextArea.setText ( reference != null ? reference : "" );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }