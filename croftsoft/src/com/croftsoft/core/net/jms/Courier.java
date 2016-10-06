     package com.croftsoft.core.net.jms;
     
     // imported J2SE packages
     
     import java.io.Serializable;
     import javax.naming.Context;
     import javax.naming.InitialContext;
     import javax.naming.NamingException;
     import java.util.*;

     // imported J2EE packages
     
     import javax.jms.JMSException;
     import javax.jms.Message;
     import javax.jms.MessageListener;
     import javax.jms.ObjectMessage;
     import javax.jms.Session;
     import javax.jms.Topic;
     import javax.jms.TopicConnection;
     import javax.jms.TopicConnectionFactory;
     import javax.jms.TopicPublisher;
     import javax.jms.TopicSession;
     import javax.jms.TopicSubscriber;

     // imported Whoola packages
     
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.util.queue.ListQueue;
     import com.croftsoft.core.util.queue.Queue;
     
     /*********************************************************************
     * Exchanges serializable Objects with a Topic via Queues.
     *
     * <p />
     *
     * @version
     *   2001-05-02
     * @since
     *   2001-02-22
     * @author
     *   <a href="http://www.alumni.caltech.edu/~croft/">David W. Croft</a>
     *********************************************************************/

     public final class  Courier
       implements Lifecycle, MessageListener, Runnable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {
       
     private static final String
       DEFAULT_JNDI_TOPIC_CONNECTION_FACTORY_NAME
       = "jms/TopicConnectionFactory";
     
     private static final String  DEFAULT_JNDI_TOPIC_NAME = "jms/Topic";
     
     private static final int  STATE_UNINITIALIZED = 0;
     
     private static final int  STATE_INITIALIZED   = 1;
     
     private static final int  STATE_STARTED       = 2;
     
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     
     private final Queue   incomingQueue;
     
     private final Queue   outgoingQueue;
     
     private final String  jndiTopicName;
     
     private final String  jndiTopicConnectionFactoryName;
     
     private int  state = STATE_UNINITIALIZED;
     
     private TopicConnection  topicConnection;
     
     private TopicSession     topicSession;
     
     private TopicPublisher   topicPublisher;
     
     private TopicSubscriber  topicSubscriber;
     
     private Thread   thread;
     
     private Object   lockObject = new Object ( );
     
     private boolean  isOkToRun = false;
     
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     
     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       Queue  incomingQueue = new ListQueue ( new ArrayList ( ) );
       
       Queue  outgoingQueue = new ListQueue ( new ArrayList ( ) );
       
       Serializable  outgoingSerializable = "Test";
       
       if ( args.length > 0 )
       {
         outgoingSerializable = args [ 0 ];
       }
       
       String  jndiTopicName = DEFAULT_JNDI_TOPIC_NAME;
       
       if ( args.length > 1 )
       {
         jndiTopicName = args [ 1 ];
       }
       
       String  jndiTopicConnectionFactoryName
         = DEFAULT_JNDI_TOPIC_CONNECTION_FACTORY_NAME;
       
       if ( args.length > 2 )
       {
         jndiTopicConnectionFactoryName = args [ 2 ];
       }
       
       Lifecycle  lifecycle = new Courier ( incomingQueue, outgoingQueue,
         jndiTopicName, jndiTopicConnectionFactoryName );
       
       System.out.println ( "Initializing..." );
       
       lifecycle.init ( );
       
       System.out.println ( "Starting..." );
       
       lifecycle.start ( );
       
       System.out.println ( "Transmitting..." );
       
       outgoingQueue.append ( outgoingSerializable );
       
       System.out.println ( "Receiving..." );
       
       try
       {
         System.out.println ( incomingQueue.pull ( ) );
       }
       catch ( InterruptedException  ex )
       {
         ex.printStackTrace ( );
       }
       
       System.out.println ( "Stopping..." );
       
       lifecycle.stop ( );
       
       System.out.println ( "Destroying..." );
       
       lifecycle.destroy ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Courier (
       Queue   incomingQueue,
       Queue   outgoingQueue,
       String  jndiTopicName,
       String  jndiTopicConnectionFactoryName )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.incomingQueue = incomingQueue );
       
       NullArgumentException.check ( this.outgoingQueue = outgoingQueue );
       
       NullArgumentException.check ( this.jndiTopicName = jndiTopicName );
       
       NullArgumentException.check ( this.jndiTopicConnectionFactoryName
         = jndiTopicConnectionFactoryName );
     }
     
     public  Courier (
       Queue   incomingQueue,
       Queue   outgoingQueue,
       String  jndiTopicName )
     //////////////////////////////////////////////////////////////////////
     {
       this ( incomingQueue, outgoingQueue, jndiTopicName,
         DEFAULT_JNDI_TOPIC_CONNECTION_FACTORY_NAME );
     }
     
     public  Courier (
       Queue  incomingQueue,
       Queue  outgoingQueue )
     //////////////////////////////////////////////////////////////////////
     {
       this ( incomingQueue, outgoingQueue, DEFAULT_JNDI_TOPIC_NAME );
     }
     
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     
     public synchronized void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( state != STATE_UNINITIALIZED )
       {
         throw new IllegalStateException ( "already initialized" );
       }
       
       try
       {
         Context  context = new InitialContext ( );
         
         TopicConnectionFactory  topicConnectionFactory
           = ( TopicConnectionFactory ) context.lookup (
           jndiTopicConnectionFactoryName );
       
         Topic  topic = ( Topic ) context.lookup ( jndiTopicName );

         topicConnection = topicConnectionFactory.createTopicConnection ( );
       
         topicSession = topicConnection.createTopicSession (
           false, Session.AUTO_ACKNOWLEDGE );
       
         topicPublisher = topicSession.createPublisher ( topic );
         
         String  messageSelector = null;
         
         boolean  noLocal = true;
       
         topicSubscriber = topicSession.createSubscriber (
           topic, messageSelector, noLocal );
       
         topicSubscriber.setMessageListener ( this );
       
         state = STATE_INITIALIZED;
       }
       catch ( NamingException  ex )
       {
         ex.printStackTrace ( );
       }
       catch ( JMSException  ex )
       {
         ex.printStackTrace ( );
         
// Do we need to close some stuff here?
         
// Do I need a finalize method?         
       }
     }
     
     public synchronized void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( state != STATE_INITIALIZED )
       {
         throw new IllegalStateException (
           "not initialized or already started" );
       }
       
       // Use of the lockObject ensures that a new thread cannot be started
       // until the previously running thread has completed.
       
       synchronized ( lockObject )
       {
         isOkToRun = true;
         
         try
         {
           topicConnection.start ( );
       
           thread = new Thread ( this );
       
           thread.start ( );
       
           state = STATE_STARTED;
         }
         catch ( JMSException  ex )
         {
// do some cleanup here?           
           ex.printStackTrace ( );
         }
       }
     }
     
     public void  onMessage ( Message  message )
     //////////////////////////////////////////////////////////////////////
     {
       // No need to synchronize as the Session passes messages serially.
       
       try
       {
         if ( message instanceof ObjectMessage )
         {
           Object  messageObject
             = ( ( ObjectMessage ) message ).getObject ( );
           
           incomingQueue.append ( messageObject );
         }
         else
         {
// ... else what?           
         }
       }
       catch ( Exception  ex )
       {
         // must catch all Exceptions
         
         ex.printStackTrace ( );
       }
     }
     
     public void  run ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( thread != Thread.currentThread ( ) )
       {
         throw new IllegalStateException ( "call start() instead" );
       }
       
       // Use of the lockObject ensures that a new thread cannot be started
       // until the previously running thread has completed.
       
       synchronized ( lockObject )
       {
         while ( isOkToRun )
         {
           try
           {
             Serializable  outgoingSerializable
               = ( Serializable ) outgoingQueue.pull ( );
             
             ObjectMessage  objectMessage
               = topicSession.createObjectMessage ( );
       
             objectMessage.setObject ( outgoingSerializable );
       
             topicPublisher.publish ( objectMessage );
           }
           catch ( InterruptedException  ex )
           {
             // Will exit loop if isOkToRun is now false.
           }
           catch ( JMSException  ex )
           {
             ex.printStackTrace ( );
             
// What kind of cleanup and state transition here?             
             
             isOkToRun = false;
           }
         }
       }
     }
     
     public synchronized void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( state != STATE_STARTED )
       {
         throw new IllegalStateException ( "not started" );
       }
       
       isOkToRun = false;
       
       thread.interrupt ( );
         
       thread = null;
       
       try
       {
         topicConnection.stop ( );
       }
       catch ( JMSException  ex )
       {
         ex.printStackTrace ( );
         
// what kind of clean-up here?         
       }
       
       state = STATE_INITIALIZED;
     }
     
     public synchronized void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( state != STATE_INITIALIZED )
       {
         throw new IllegalStateException ( "not initialized" );
       }
       
       try
       {
         topicSubscriber.close ( );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
       
// what about others?
       
       try
       {
         topicConnection.close ( );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
       
       state = STATE_UNINITIALIZED;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
