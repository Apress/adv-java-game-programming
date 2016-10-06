     package com.croftsoft.core.util.state;

     import java.io.Serializable;
     import java.rmi.*;

     import com.croftsoft.core.lang.ex.*;

     /*********************************************************************
     *
     * An ambassador proxy for StateListenerRemote objects.
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1998-12-05
     *********************************************************************/

     public class  StateListenerAmbassador
       implements StateListener, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private StateListenerRemote  stateListenerRemote;

     private transient ExceptionListener  exceptionListener;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  StateListenerAmbassador (
       StateListenerRemote  stateListenerRemote,
       ExceptionListener    exceptionListener )
     //////////////////////////////////////////////////////////////////////
     {
       this.stateListenerRemote = stateListenerRemote;
       this.exceptionListener   = exceptionListener;
     }

     public  StateListenerAmbassador (
       StateListenerRemote  stateListenerRemote )
     //////////////////////////////////////////////////////////////////////
     {
       this ( stateListenerRemote, new PrintExceptionListener ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  stateListen ( State  state )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         stateListenerRemote.stateListen ( state );
       }
       catch ( Exception  ex )
       {
         if ( exceptionListener != null )
         {
           exceptionListener.threwException ( this, ex );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
