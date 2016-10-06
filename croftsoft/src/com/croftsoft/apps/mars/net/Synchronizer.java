     package com.croftsoft.apps.mars.net;

     import java.io.*;
     import java.net.*;
     import java.util.*;

     import com.croftsoft.core.io.SerializableCoder;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.math.MathConstants;
     import com.croftsoft.core.net.http.msg.HttpMessageClient;
     import com.croftsoft.core.role.Consumer;
     import com.croftsoft.core.util.NullIterator;
     
     import com.croftsoft.apps.mars.model.GameAccessor;
     import com.croftsoft.apps.mars.model.NullGameAccessor;
     import com.croftsoft.apps.mars.model.TankAccessor;
     import com.croftsoft.apps.mars.model.WorldAccessor;
     import com.croftsoft.apps.mars.net.request.ViewRequest;

     /*********************************************************************
     * Network synchronizer.
     *
     * @version
     *   2003-06-02
     * @since
     *   2003-04-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Synchronizer
       implements GameAccessor, Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  DEFAULT_HOST = "localhost";

     private static final int     DEFAULT_PORT = 8080;

     private static final String  SERVLET_PATH = "servlet";

     private static final String  DEFAULT_PATH = "/mars/" + SERVLET_PATH;

     private static final String  USER_AGENT   = "CroftSoft Mars";

     private static final String  CONTENT_TYPE
       = "application/octet-stream";

     private static final long    POLLING_PERIOD_MIN
       = 100;

     private static final long    POLLING_PERIOD_MAX
       = MathConstants.MILLISECONDS_PER_DAY;

     private static final long    POLLING_PERIOD_INIT
       = POLLING_PERIOD_MIN;

     private static final double  POLLING_PERIOD_MULT
       = 2.0;

     private static final double  POLLING_PERIOD_DIVI
       = Double.POSITIVE_INFINITY;

     private static final long    POLLING_PERIOD_INCR
       = MathConstants.MILLISECONDS_PER_SECOND;

     //

     private final HttpMessageClient  httpMessageClient;

     //

     private GameAccessor  gameAccessor;

     private int           level;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Synchronizer (
       String  playerName,
       URL     codeBaseURL )
       throws IOException, MalformedURLException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( playerName );

       URL  servletURL = null;

       if ( codeBaseURL != null )
       {
         servletURL = new URL ( codeBaseURL, SERVLET_PATH );
       }
       else
       {
         servletURL
           = new URL ( "http", DEFAULT_HOST, DEFAULT_PORT, DEFAULT_PATH );
       }

       httpMessageClient = new HttpMessageClient (
         servletURL,
         USER_AGENT,
         SerializableCoder.INSTANCE,
         SerializableCoder.INSTANCE,
         CONTENT_TYPE,
         SerializableCoder.INSTANCE.encode (
           new ViewRequest ( playerName ) ),
         new Consumer ( )
         {
           public void  consume ( Object  o )
           {
             Synchronizer.this.consume ( o );
           }
         },
         POLLING_PERIOD_MIN,
         POLLING_PERIOD_MAX,
         POLLING_PERIOD_INIT,
         POLLING_PERIOD_MULT,
         POLLING_PERIOD_DIVI,
         POLLING_PERIOD_INCR );

       gameAccessor = NullGameAccessor.INSTANCE;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  replace ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       httpMessageClient.replace ( o );
     }

     //////////////////////////////////////////////////////////////////////
     // interface GameAccessor methods
     //////////////////////////////////////////////////////////////////////

     public int  getLevel ( )
     //////////////////////////////////////////////////////////////////////
     {
       return level;
     }

     public Iterator  getPath ( )
     //////////////////////////////////////////////////////////////////////
     {
       return NullIterator.INSTANCE;
     }

     public TankAccessor  getPlayerTankAccessor ( )
     //////////////////////////////////////////////////////////////////////
     {
       return gameAccessor.getPlayerTankAccessor ( );
     }

     public WorldAccessor  getWorldAccessor ( )
     //////////////////////////////////////////////////////////////////////
     {
       return gameAccessor.getWorldAccessor ( );
     }

     //////////////////////////////////////////////////////////////////////
     // interface Lifecycle methods
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       httpMessageClient.init ( );
     }

     public void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       httpMessageClient.start ( );
     }

     public void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       httpMessageClient.stop ( );
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       httpMessageClient.destroy ( );
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private void  consume ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       gameAccessor = ( GameAccessor ) o;

       level++;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
