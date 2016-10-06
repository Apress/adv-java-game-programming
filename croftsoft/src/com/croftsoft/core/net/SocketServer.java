     package com.croftsoft.core.net;

     import java.io.*;
     import java.net.*;
     import java.util.*;

     /*********************************************************************
     * <P>
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   1997-04-19
     *********************************************************************/

     public interface  SocketServer extends Runnable {
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setPortServer ( PortServer  portServer );
     public void  setSocket     ( Socket  socket         );
     public void  setUniqueID   ( long  uniqueID         );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
