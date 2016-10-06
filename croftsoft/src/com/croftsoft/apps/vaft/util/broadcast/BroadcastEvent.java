     package com.croftsoft.apps.vaft.util.broadcast;

     import java.util.EventObject;

     /*********************************************************************
     * <P>
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1998-04-05
     *********************************************************************/

     public class  BroadcastEvent extends EventObject
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private  String  announcement;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  BroadcastEvent (
       Broadcaster  broadcaster, String  announcement )
     //////////////////////////////////////////////////////////////////////
     {
       super ( broadcaster );
       this.announcement = announcement;
     }

     public Broadcaster  getBroadcaster ( )
     //////////////////////////////////////////////////////////////////////
     {
       return ( Broadcaster ) getSource ( );
     }

     public String  getAnnouncement ( ) { return announcement; }

     public String  toString ( )
     //////////////////////////////////////////////////////////////////////
     {
       return getBroadcaster ( ).getBroadcastName ( )
         + ":  \"" + announcement + "\"";
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
