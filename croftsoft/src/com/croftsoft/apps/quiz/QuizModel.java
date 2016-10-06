     package com.croftsoft.apps.quiz;

     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.Commissionable;
     import com.croftsoft.core.lang.lifecycle.InitializationException;
     import com.croftsoft.core.text.sml.*;

     /*********************************************************************
     * @version
     *   2001-07-10
     * @since
     *   2001-07-10
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  QuizModel
       implements Commissionable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final String  quizFilename;

     private final String  backupFilename;

     private Quiz  quiz;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  QuizModel (
       String  quizFilename,
       String  backupFilename )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.quizFilename = quizFilename );

       NullArgumentException.check (
         this.backupFilename = backupFilename );
     }

     public  QuizModel ( )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         QuizConstants.DEFAULT_QUIZ_FILENAME,
         QuizConstants.DEFAULT_BACKUP_FILENAME );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
System.out.println ( "QuizModel.init()" );

       try
       {
         quiz = QuizLib.loadQuiz ( quizFilename );
       }
       catch ( FileNotFoundException  ex )
       {
       }
       catch ( IOException  ex )
       {
         throw new InitializationException ( ex );
       }

       if ( quiz == null )
       {
         quiz = new Quiz ( );
       }
     }

     public synchronized void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
System.out.println ( "QuizModel.destroy()" );

       try
       {
         QuizLib.saveQuiz ( quizFilename, backupFilename, quiz );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Quiz  getQuiz ( ) { return quiz; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
