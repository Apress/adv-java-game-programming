     package com.croftsoft.apps.chat.user.seri;

     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.animation.model.ModelId;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.util.queue.ListQueue;
     import com.croftsoft.core.util.queue.Queue;

     import com.croftsoft.apps.chat.user.User;
     import com.croftsoft.apps.chat.user.UserId;

     /*********************************************************************
     * Serializable User implementation.
     *
     * @version
     *   2003-06-18
     * @since
     *   2003-06-05
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  SeriUser
       implements Serializable, User
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private static final int  MAX_QUEUE_SIZE = 10;

     //

     private final UserId  userId;

     private final String  username;

     private final Queue   messageQueue;

     private final Queue   requestQueue;

     //

     private long     eventIndex;

     private long     lastRequestTime;

     private ModelId  modelId;

     private String   password;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SeriUser (
       UserId  userId,
       String  username,
       String  password )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.userId   = userId   );

       NullArgumentException.check ( this.username = username );

       setPassword ( password );

       messageQueue = new ListQueue ( new LinkedList ( ), MAX_QUEUE_SIZE );

       requestQueue = new ListQueue ( new LinkedList ( ), MAX_QUEUE_SIZE );

       eventIndex = new Random ( ).nextLong ( );

       updateLastRequestTime ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized long  getEventIndex ( ) { return eventIndex; }

     public synchronized long  getLastRequestTime ( )
     //////////////////////////////////////////////////////////////////////
     {
       return lastRequestTime;
     }

     //

     public Queue    getMessageQueue ( ) { return messageQueue; }

     public Queue    getRequestQueue ( ) { return requestQueue; }

     public ModelId  getModelId      ( ) { return modelId;      }

     public String   getPassword     ( ) { return password;     }

     public UserId   getUserId       ( ) { return userId;       }

     public String   getUsername     ( ) { return username;     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized long  nextEventIndex ( )
     //////////////////////////////////////////////////////////////////////
     {
       return ++eventIndex;
     }

     public void  setModelId ( ModelId  modelId )
     //////////////////////////////////////////////////////////////////////
     {
       this.modelId = modelId;
     }

     public void  setPassword ( String  password )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.password = password );
     }

     public void  updateLastRequestTime ( )
     //////////////////////////////////////////////////////////////////////
     {
       lastRequestTime = System.currentTimeMillis ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
