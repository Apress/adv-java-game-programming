     package com.croftsoft.core.util.state;

     /*********************************************************************
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1998-12-06
     *********************************************************************/

     public class  RelayStateMulticaster
       implements StateMulticaster, StateListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private StateMulticaster  stateMulticaster;

     private StateMulticaster  queuedStateMulticaster
       = new QueuedStateMulticaster ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  RelayStateMulticaster ( StateMulticaster  stateMulticaster )
     //////////////////////////////////////////////////////////////////////
     {
       this.stateMulticaster = stateMulticaster;
       queuedStateMulticaster.addStateListener ( this );
     }

     public void  update ( State  state )
     //////////////////////////////////////////////////////////////////////
     {
       queuedStateMulticaster.update ( state );
     }

     public boolean  addStateListener ( StateListener  stateListener )
     //////////////////////////////////////////////////////////////////////
     {
       return stateMulticaster.addStateListener ( stateListener );
     }

     public boolean  removeStateListener ( StateListener  stateListener )
     //////////////////////////////////////////////////////////////////////
     {
       return stateMulticaster.removeStateListener ( stateListener );
     }

     public void  stateListen ( State  state )
     //////////////////////////////////////////////////////////////////////
     {
       stateMulticaster.update ( state );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
