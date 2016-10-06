     package com.croftsoft.core.util.queue;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Testable;

     /*********************************************************************
     * Black box testing of Queue implementations.
     *
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     * @version
     *   2003-05-22
     * @since
     *   2003-05-22
     *********************************************************************/

     public final class  QueueTest
       implements Testable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( test ( args ) );
     }

     public static boolean  test ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         if ( !test ( new ListQueue ( ) ) )
         {
           return false;
         }

         if ( !test ( new VectorQueue ( ) ) )
         {
           return false;
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         return false;
       }

       return true;
     }

     public static boolean  test ( Queue  queue )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( queue );

       try
       {
         Object  object = null;

         System.out.println ( "Empty queue pull with 3 second timeout" );

         object = queue.pull ( 3000 );

         if ( object != null )
         {
           return false;
         }

         System.out.println ( "Empty queue poll" );

         object = queue.poll ( );

         if ( object != null )
         {
           return false;
         }

         System.out.println ( "Non-empty queue pull" );

         if ( !queue.append ( new Object ( ) ) )
         {
           return false;
         }

         object = queue.pull ( );

         if ( object == null )
         {
           return false;
         }

         System.out.println ( "Queue replace" );

         queue.replace ( new String ( "test" ) );

         queue.replace ( new String ( "test" ) );

         object = queue.pull ( );

         if ( object == null )
         {
           return false;
         }

         object = queue.poll ( );

         if ( object != null )
         {
           return false;
         }

         System.out.println ( "Negative timeout argument" );

         boolean  passed = false;

         try
         {
           queue.pull ( -1 );
         }
         catch ( IllegalArgumentException  ex )
         {
           passed = true;
         }

         if ( !passed )
         {
           return false;
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         return false;
       }

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  QueueTest ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
