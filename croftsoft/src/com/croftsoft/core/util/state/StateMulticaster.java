     package com.croftsoft.core.util.state;

     /*********************************************************************
     *
     * An interface for objects that mulicast State updates.
     * 
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1998-12-06
     *********************************************************************/

     public interface  StateMulticaster
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  update ( State  state );

     public boolean  addStateListener ( StateListener  stateListener );

     public boolean  removeStateListener ( StateListener  stateListener );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
