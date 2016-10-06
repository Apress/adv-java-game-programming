     package com.croftsoft.apps.mars.net;

     import javax.servlet.*;
     import javax.servlet.http.*;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.beans.XmlBeanCoder;
     import com.croftsoft.core.io.SerializableCoder;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.servlet.HttpGatewayServlet;

     /*********************************************************************
     * Mars servlet.
     *
     * @version
     *   2003-06-13
     * @since
     *   2003-04-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  MarsServlet
       extends HttpGatewayServlet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  VERSION = "2003-06-13";

     private static final String  TITLE   = "CroftSoft Mars Server";

     private static final String  SERVLET_INFO
       = "\n" + TITLE
       + "\n" + CroftSoftConstants.COPYRIGHT
       + "\n" + CroftSoftConstants.HOME_PAGE
       + "\n" + "Version " + VERSION
       + "\n" + CroftSoftConstants.DEFAULT_LICENSE
       + "\n";

     private static final String  GAME_INIT_PATH    = "/WEB-INF/mars.xml";

     private static final String  PRIMARY_FILENAME  = "mars.dat";

     private static final String  FALLBACK_FILENAME = "mars.bak";

     //

     private final MarsServer  marsServer;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  MarsServlet ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new MarsServer ( PRIMARY_FILENAME, FALLBACK_FILENAME ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getServletInfo ( )
     //////////////////////////////////////////////////////////////////////
     {
       return SERVLET_INFO;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
       throws ServletException
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( SERVLET_INFO );

       try
       {
         GameInit  gameInit = ( GameInit ) XmlBeanCoder.decodeFromXml (
           getServletContext ( ).getResourceAsStream ( GAME_INIT_PATH ) );

         marsServer.setGameInitAccessor ( gameInit );

         marsServer.init ( );
       }
       catch ( Exception  ex )
       {
         throw ( ServletException )
           new UnavailableException ( ex.getMessage ( ) ).initCause ( ex );
       }
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         marsServer.destroy ( );
       }
       catch ( Exception  ex )
       {
         log ( ex.getMessage ( ), ex );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private  MarsServlet ( MarsServer  marsServer )
     //////////////////////////////////////////////////////////////////////
     {
       super (
         marsServer,
         SerializableCoder.INSTANCE,
         SerializableCoder.INSTANCE );

       NullArgumentException.check ( this.marsServer = marsServer );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
