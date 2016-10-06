     package com.croftsoft.core.util.state;

     import java.io.Serializable;
     import java.rmi.*;

     import com.croftsoft.core.lang.ex.*;

     /*********************************************************************
     *
     * An ambassador proxy for StateMulticasterRemote objects.
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1998-12-05
     *********************************************************************/

     public class  StateMulticasterAmbassador
       implements StateMulticaster, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private StateMulticasterRemote  stateMulticasterRemote;

     private transient ExceptionListener  exceptionListener;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  StateMulticasterAmbassador (
       StateMulticasterRemote  stateMulticasterRemote,
       ExceptionListener       exceptionListener )
     //////////////////////////////////////////////////////////////////////
     {
       this.stateMulticasterRemote = stateMulticasterRemote;
       this.exceptionListener      = exceptionListener;
     }

     public  StateMulticasterAmbassador (
       StateMulticasterRemote  stateMulticasterRemote )
     //////////////////////////////////////////////////////////////////////
     {
       this ( stateMulticasterRemote, new PrintExceptionListener ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( State  state )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         stateMulticasterRemote.update ( state );
       }
       catch ( Exception  ex )
       {
         if ( exceptionListener != null )
         {
           exceptionListener.threwException ( this, ex );
         }
       }
     }

     public boolean  addStateListener ( StateListener  stateListener )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         return stateMulticasterRemote.addStateListener ( stateListener );
       }
       catch ( Exception  ex )
       {
         if ( exceptionListener != null )
         {
           exceptionListener.threwException ( this, ex );
         }
         return false;
       }
     }

     public boolean  removeStateListener ( StateListener  stateListener )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         return
           stateMulticasterRemote.removeStateListener ( stateListener );
       }
       catch ( Exception  ex )
       {
         if ( exceptionListener != null )
         {
           exceptionListener.threwException ( this, ex );
         }
         return false;
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
