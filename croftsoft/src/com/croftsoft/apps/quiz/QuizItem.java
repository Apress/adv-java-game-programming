     package com.croftsoft.apps.quiz;

     import java.io.Serializable;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.text.sml.*;

     /*********************************************************************
     * A question/answer pair with reference.
     *
     * @version
     *   2001-06-10
     * @since
     *   2001-06-10
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  QuizItem
       implements Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 1L;

     private String  question;

     private String  answer;

     private String  reference;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static QuizItem  fromSmlNode ( SmlNode  smlNode )
     //////////////////////////////////////////////////////////////////////
     {
       String  question  = smlNode.getString ( "question"  );

       String  answer    = smlNode.getString ( "answer"    );

       String  reference = smlNode.getString ( "reference" );

       return new QuizItem ( question, answer, reference );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  QuizItem (
       String  question,
       String  answer,
       String  reference )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.question = question );

       this.answer = answer;

       this.reference = reference;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getQuestion  ( ) { return question;  }

     public String  getAnswer    ( ) { return answer;    }

     public String  getReference ( ) { return reference; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setQuestion ( String  question )
     //////////////////////////////////////////////////////////////////////
     {
       this.question = question;
     }

     public void  setAnswer ( String  answer )
     //////////////////////////////////////////////////////////////////////
     {
       this.answer = answer;
     }

     public void  setReference ( String  reference )
     //////////////////////////////////////////////////////////////////////
     {
       this.reference = reference;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public SmlNode  toSmlNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       SmlNode  rootSmlNode = new SmlNode ( "QuizItem" );

       rootSmlNode.add ( new SmlNode ( "question", question ) );

       if ( answer != null )
       {
         rootSmlNode.add ( new SmlNode ( "answer", answer ) );
       }

       if ( reference != null )
       {
         rootSmlNode.add ( new SmlNode ( "reference", reference ) );
       }

       return rootSmlNode;       
     }

     public String  toString ( )
     //////////////////////////////////////////////////////////////////////
     {
       return toSmlNode ( ).toString ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
