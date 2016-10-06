     package com.croftsoft.apps.quiz;

     import java.io.*;

     import com.croftsoft.core.io.FileLib;
     import com.croftsoft.core.io.SerializableLib;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Quizzes a student for test preparation.
     *
     * @version
     *   2001-07-10
     * @since
     *   2001-06-10
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  QuizLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  DEFAULT_QUIZ_FILENAME   = "quiz.xml";

     private static final String  DEFAULT_BACKUP_FILENAME = "quiz.bak";

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       String  quizFilename = DEFAULT_QUIZ_FILENAME;

       if ( ( args != null    )
         && ( args.length > 0 ) )
       {
         quizFilename = args [ 0 ];
       }

       String  backupFilename = DEFAULT_BACKUP_FILENAME;

       if ( ( args != null    )
         && ( args.length > 1 ) )
       {
         backupFilename = args [ 1 ];
       }

       Quiz  quiz = loadQuiz ( quizFilename );

       if ( quiz == null )
       {
         addToQuiz ( quiz = new Quiz ( ) );

         saveQuiz ( quizFilename, backupFilename, quiz );
       }
      
       System.out.println ( "1.  Take quiz" );
 
       System.out.println ( "2.  Add to quiz" );

       System.out.println ( "" );
       
       System.out.print ( "Choice? [1]:  " );

       String  choice = promptSingleLine ( );

       if ( "2".equals ( choice ) )
       {
         addToQuiz ( quiz );
 
         saveQuiz ( quizFilename, backupFilename, quiz );
       }
       else
       {       
         giveQuiz ( quiz );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  addToQuiz ( Quiz  quiz )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       QuizItem  quizItem = null;

       while ( ( quizItem = promptForQuizItem ( ) ) != null )
       {
         quiz.add ( quizItem );
       }
     }

     public static void  giveQuiz ( Quiz  quiz )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       QuizItem [ ]  quizItems = quiz.getQuizItems ( );

       for ( int  i = 0; i < quizItems.length; i++ )
       {
         QuizItem  quizItem = quizItems [ i ];

         System.out.println ( "Question:" );
  
         System.out.println ( quizItem.getQuestion ( ) );

         System.out.println ( "Answer:" );

         promptMultiLine ( );

         System.out.println ( "" );

         String  answer = quizItem.getAnswer ( );

         if ( answer != null )
         {
           System.out.println ( "Answer:" );

           System.out.println ( answer );

           System.out.println ( "" );
         }

         String  reference = quizItem.getReference ( );

         if ( reference != null )
         {
           System.out.println ( "Reference:" );

           System.out.println ( reference );

           System.out.println ( "" );
         }
       }
     }

     public static Quiz  loadQuiz ( String  quizFilename )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         String  quizString = FileLib.loadTextFile ( quizFilename );

         return Quiz.fromString ( quizString );
       }
       catch ( FileNotFoundException  ex )
       {
         return null;
       }
     }

     public static QuizItem  promptForQuizItem ( )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println (
         "Question (press F6 or Control-D on blank line to stop):" );

       String  question = promptMultiLine ( );

       if ( question == null )
       {
         return null;
       }

       System.out.println ( "Answer:" );

       String  answer = promptMultiLine ( );

       System.out.println ( "Reference:" );

       String  reference = promptMultiLine ( );

       return new QuizItem ( question, answer, reference );
     }

     public static String  promptMultiLine ( )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       BufferedReader  bufferedReader = new BufferedReader (
         new InputStreamReader ( System.in ) );

       StringBuffer  stringBuffer = new StringBuffer ( );

       String  line;

       while ( ( line = bufferedReader.readLine ( ) ) != null )
       {
         stringBuffer.append ( line );

         stringBuffer.append ( '\n' );
       }

       String  entry = stringBuffer.toString ( );

       entry = entry.trim ( );

       if ( "".equals ( entry ) )
       {
         entry = null;
       }

       return entry;
     }

     public static String  promptSingleLine ( )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       String  entry = new BufferedReader (
         new InputStreamReader ( System.in ) ).readLine ( );

       entry = entry.trim ( );

       if ( "".equals ( entry ) )
       {
         entry = null;
       }

       return entry;
     }

     public static void  saveQuiz (
       String  quizFilename,
       String  backupFilename,
       Quiz    quiz )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       File  file = new File ( quizFilename );

       if ( file.exists ( ) )
       {
         file.renameTo ( new File ( backupFilename ) );
       }

       FileLib.saveTextFile ( quizFilename, quiz.toString ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  QuizLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }