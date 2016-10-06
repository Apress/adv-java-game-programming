     package com.croftsoft.core.servlet;

     import java.io.*;

     import javax.servlet.*;
     import javax.servlet.http.*;

     import com.croftsoft.core.io.Encoder;
     import com.croftsoft.core.io.Parser;
     import com.croftsoft.core.role.Server;

     /*********************************************************************
     * Relays HTTP requests to a Server.
     *
     * @version
     *   2003-06-03
     * @since
     *   2003-05-13
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public abstract class  HttpGatewayServlet
       extends HttpServlet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Server   server;

     private final Parser   parser;

     private final Encoder  encoder;

     //////////////////////////////////////////////////////////////////////
     // protected and public constructor methods
     //////////////////////////////////////////////////////////////////////

     protected  HttpGatewayServlet (
       Server   server,
       Parser   parser,
       Encoder  encoder )
     //////////////////////////////////////////////////////////////////////
     {
       this.server  = server;

       this.parser  = parser;

       this.encoder = encoder;
     }

     public  HttpGatewayServlet ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( ( Server ) null, ( Parser ) null, ( Encoder ) null );
     }

     //////////////////////////////////////////////////////////////////////
     // protected Encoder/Parser/Server methods that may be overridden
     //////////////////////////////////////////////////////////////////////

     protected byte [ ]  encode ( Object  object )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return encoder.encode ( object );
     }

     protected Object  parse (
       InputStream  inputStream,
       int          contentLength )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       return parser.parse ( inputStream, contentLength );
     }

     protected Object  serve ( Object  request )
     //////////////////////////////////////////////////////////////////////
     {
       return server.serve ( request );
     }

     //////////////////////////////////////////////////////////////////////
     // public final service method
     //////////////////////////////////////////////////////////////////////

     public final void  service (
       HttpServletRequest   httpServletRequest,
       HttpServletResponse  httpServletResponse )
       throws IOException, ServletException
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         Object  request = null;

         try
         {
           request = parse (
             httpServletRequest.getInputStream ( ), -1 );
         }
         catch ( IOException  ex )
         {
           httpServletResponse.setStatus (
               HttpServletResponse.SC_BAD_REQUEST );
         }

         if ( request != null )
         {
           Object  response = serve ( request );

           if ( response != null )
           {
             httpServletResponse.setStatus ( HttpServletResponse.SC_OK );

             byte [ ]  bytes = encode ( response );

             httpServletResponse.getOutputStream ( ).write ( bytes );
           }
           else
           {
             httpServletResponse.setStatus (
               HttpServletResponse.SC_ACCEPTED );
           }
         }
       }
       catch ( Exception  ex )
       {
         httpServletResponse.setStatus (
           HttpServletResponse.SC_INTERNAL_SERVER_ERROR );

         log ( ex.getMessage ( ), ex );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
