     package com.croftsoft.apps.quiz;

     import java.io.Serializable;
     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.text.sml.*;

     /*********************************************************************
     * A list of question/answer pairs.
     *
     * @version
     *   2001-06-10
     * @since
     *   2001-06-10
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Quiz
       implements Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 1L;

     private Vector  quizItemVector;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static Quiz  fromString ( String  quizString )
     //////////////////////////////////////////////////////////////////////
     {
       return fromSmlNode ( SmlNodeLib.parse ( quizString ) );

     }

     public static Quiz  fromSmlNode ( SmlNode  smlNode )
     //////////////////////////////////////////////////////////////////////
     {
       Vector  quizItemVector = new Vector ( );

       SmlNode  quizItemsSmlNode = smlNode.getChildNode ( "quizItems" );

       if ( quizItemsSmlNode != null )
       {
         int  index = 0;

         SmlNode  quizItemSmlNode;

         while ( true )
         {
           Object  child = quizItemsSmlNode.getChild ( index++ );

           if ( child == null )
           {
             break;
           }

           if ( child instanceof SmlNode )
           {
             quizItemVector.addElement (
               QuizItem.fromSmlNode ( ( SmlNode ) child ) );
           }
         }
       }

       return new Quiz ( quizItemVector );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Quiz ( Vector  quizItemVector )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.quizItemVector = quizItemVector );
     }

     public  Quiz ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new Vector ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized QuizItem [ ]  getQuizItems ( )
     //////////////////////////////////////////////////////////////////////
     {
       QuizItem [ ]  quizItems = new QuizItem [ quizItemVector.size ( ) ];

       quizItemVector.copyInto ( quizItems );

       return quizItems;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  add ( QuizItem  quizItem )
     //////////////////////////////////////////////////////////////////////
     {
       quizItemVector.addElement ( quizItem );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public SmlNode  toSmlNode ( )
     //////////////////////////////////////////////////////////////////////
     {
       SmlNode  rootSmlNode = new SmlNode ( "Quiz" );

       QuizItem [ ]  quizItems = getQuizItems ( );

       if ( quizItems != null )
       {
         SmlNode  quizItemsSmlNode = new SmlNode ( "quizItems" );

         rootSmlNode.add ( quizItemsSmlNode );

         for ( int  i = 0; i < quizItems.length; i++ )
         {
           quizItemsSmlNode.add ( quizItems [ i ].toSmlNode ( ) );
         }
       }

       return rootSmlNode;       
     }

     public String  toString ( )
     //////////////////////////////////////////////////////////////////////
     {
       return toSmlNode ( ).toString ( 0, 0 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
