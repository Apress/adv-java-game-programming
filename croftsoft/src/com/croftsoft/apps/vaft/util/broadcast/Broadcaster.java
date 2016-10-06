     package com.croftsoft.apps.vaft.util.broadcast;

     /*********************************************************************
     * <P>
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1998-04-05
     *********************************************************************/

     public interface  Broadcaster
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public boolean  addBroadcastListener ( BroadcastListener  bl );
     public boolean  removeBroadcastListener ( BroadcastListener  bl );
     public String   getBroadcastName ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
