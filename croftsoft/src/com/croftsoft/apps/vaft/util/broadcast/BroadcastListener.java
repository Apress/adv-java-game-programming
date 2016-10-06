     package com.croftsoft.apps.vaft.util.broadcast;

     import java.util.EventListener;

     /*********************************************************************
     * <P>
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1998-04-06
     *********************************************************************/

     public interface  BroadcastListener extends EventListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  broadcastSent ( BroadcastEvent  broadcastEvent );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
