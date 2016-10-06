     package com.croftsoft.core.util;

     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.role.Server;

     /*********************************************************************
     * Maps requests to a delegate Server based upon the request Class.
     *
     * @version
     *   2003-06-05
     * @since
     *   2003-06-05
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ClassMapServer
       implements Server
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Map  classToServerMap;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public ClassMapServer ( Map  classToServerMap )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.classToServerMap = classToServerMap );
     }

     public ClassMapServer ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new HashMap ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Server  put (
       Class   c,
       Server  server )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( c );

       NullArgumentException.check ( server );

       return ( Server ) classToServerMap.put ( c, server );
     }

     public Object  serve ( Object  request )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( request );

       Server  server = ( Server )
         classToServerMap.get ( request.getClass ( ) );

       if ( server == null )
       {
         throw new IllegalArgumentException ( );
       }

       return server.serve ( request );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }