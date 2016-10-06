     package com.croftsoft.apps.wyrm.servlet;

     // J2SE
     import java.io.*;
     import java.net.*;
     import java.util.*;
     import javax.naming.*;
     import javax.rmi.*;
     import javax.xml.transform.*;
     import javax.xml.transform.dom.*;
     import javax.xml.transform.stream.*;

     // J2EE
     import javax.servlet.*;
     import javax.servlet.http.*;

     // Standard Extensions
     import javax.xml.bind.JAXBContext;
     import javax.xml.bind.JAXBException;
     import javax.xml.bind.Marshaller;
     import javax.xml.bind.Validator;

     // CroftSoft Core
     import com.croftsoft.core.lang.ClassLib;

     // Application
     import com.croftsoft.apps.wyrm.WyrmConstants;
     import com.croftsoft.apps.wyrm.server.WyrmServerLocal;
     import com.croftsoft.apps.wyrm.server.WyrmServerLocalHome;
     import com.croftsoft.apps.wyrm.xjc.*;

     /*********************************************************************
     * Wyrm Servlet class.
     *
     * @version
     *   2002-11-03
     * @since
     *   2002-10-16
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public final class  WyrmServlet
       extends HttpServlet
       implements WyrmConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  WYRM_SERVER_LOCAL_HOME
       = "WyrmServerLocalHome";

     //

     private static final String  PARAM_PASSWORD   = "password";

     private static final String  PARAM_PASSWORD_2 = "password2";

     private static final String  PARAM_REQUEST    = "request";

     private static final String  PARAM_USERNAME   = "username";

     //

     private static final String  SESSION_USERNAME = "username";

     private static final String  SESSION_PASSWORD = "password";

     //
     
     private static final String  ENCODING_CHARSET = "utf-8";

     private static final String  XSLT_FILE = "/xsl/browser.xsl";

     private static final String  JAXB_CONTEXT_PATH
       = "com.croftsoft.apps.wyrm.xjc";

     private static final String  SCHEMA_LOCATION_NAMESPACE
       = "http://croftsoft.com/apps/wyrm/xjc ";

     private static final String  XSD_DIR = "/xsd/";

     private static final String  XSD_EXT = ".xsd";

     //

     private WyrmServerLocal  wyrmServerLocal;

     private JAXBContext      jaxbContext;

     private Templates        templates;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
       throws ServletException
     //////////////////////////////////////////////////////////////////////
     {
       try
       {

         // Do I need to use narrow with a local home interface?

         WyrmServerLocalHome  wyrmServerLocalHome
           = ( WyrmServerLocalHome ) PortableRemoteObject.narrow (
           new InitialContext ( ).lookup ( WYRM_SERVER_LOCAL_HOME ),
           WyrmServerLocalHome.class );

         wyrmServerLocal = wyrmServerLocalHome.create ( );

         jaxbContext = JAXBContext.newInstance ( JAXB_CONTEXT_PATH );

         TransformerFactory  transformerFactory
           = TransformerFactory.newInstance ( );

         URL  xsltURL = getServletContext ( ).getResource ( XSLT_FILE );

         templates = transformerFactory.newTemplates (
           new StreamSource ( xsltURL.toExternalForm ( ) ) );
       }
       catch ( Exception  ex )
       {
         throw new ServletException ( ex );
       }
     }

     public void  service (
       HttpServletRequest   httpServletRequest,
       HttpServletResponse  httpServletResponse )
       throws IOException, ServletException
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         Object  request = toRequest ( httpServletRequest );

         // System.out.println ( "Request:  " + request );

         Object  response = wyrmServerLocal.serve ( request );

         // System.out.println ( "Response:  " + response );

         if ( response == null )
         {
           httpServletResponse.setStatus (
             HttpServletResponse.SC_NO_CONTENT );
         }
         else
         {
           httpServletResponse.setStatus ( HttpServletResponse.SC_OK );

           // Is a Validator thread safe?  If so, move creation to init().
 
           Validator  validator = jaxbContext.createValidator ( );

           if ( !validator.validateRoot ( response ) )
           {
             throw new Exception ( "validateRoot returned false" );
           }

           Marshaller  marshaller = jaxbContext.createMarshaller ( );

           // Since the JAXB_ENCODING property is UTF-8 by default,
           // setting this is unnecessary when the ENCODING_CHARSET
           // is UTF-8.
           //
           // marshaller.setProperty (
           //   Marshaller.JAXB_ENCODING, ENCODING_CHARSET );
           
           // JAXB_FORMATTED_OUTPUT is false by default.
           //
           // marshaller.setProperty (
           //   Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE );

/*
This is broken since the JAXB_SCHEMA_LOCATION uses the "xsi" namespace
but does not define it.

           String  shortName
             = ClassLib.getShortName ( response.getClass ( ) );

           // Trim off "Impl" at the end of the class name.

           String  schemaLocationHint
             = httpServletRequest.getScheme ( )
             + "://"
             + httpServletRequest.getServerName ( )
             + ":"
             + httpServletRequest.getServerPort ( )
             + httpServletRequest.getContextPath ( )
             + XSD_DIR
             + shortName.substring ( 0, shortName.length ( ) - 4 )
             + XSD_EXT;

           String  jaxbSchemaLocation
             = SCHEMA_LOCATION_NAMESPACE
             + schemaLocationHint;

           marshaller.setProperty (
             Marshaller.JAXB_SCHEMA_LOCATION, jaxbSchemaLocation );
*/           

           DOMResult  domResult = new DOMResult ( );

           marshaller.marshal ( response, domResult );

           DOMSource  domSource = new DOMSource ( domResult.getNode ( ) );

           // Templates is thread safe; Transformer is not.

           Transformer  transformer = templates.newTransformer ( );

           httpServletResponse.setContentType (
             "text/xml; charset=" + ENCODING_CHARSET );

           transformer.transform (
             domSource,
             new StreamResult ( httpServletResponse.getWriter ( ) ) );           
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( httpServletResponse.getWriter ( ) );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private Object  toRequest ( HttpServletRequest  httpServletRequest )
       throws JAXBException
     //////////////////////////////////////////////////////////////////////
     {
       String  requestType
         = httpServletRequest.getParameter ( PARAM_REQUEST );

       String  password = null;

       String  username = null;
       
       HttpSession  httpSession = httpServletRequest.getSession ( );

       if ( requestType == null )
       {
         requestType = TYPE_STATE;
       }
       
       if ( requestType.equals ( TYPE_CREATE_USER )
         || requestType.equals ( TYPE_LOGIN       ) )
       {
         username = httpServletRequest.getParameter ( PARAM_USERNAME );

         password = httpServletRequest.getParameter ( PARAM_PASSWORD );

         if ( requestType.equals ( TYPE_CREATE_USER ) )
         {
           String  password2
             = httpServletRequest.getParameter ( PARAM_PASSWORD_2 );

           if ( ( password != null )
             && !password.equals ( password2 ) )
           {
             password = null;
           }
         }

         httpSession.setAttribute ( SESSION_USERNAME, username );

         httpSession.setAttribute ( SESSION_PASSWORD, password );
       }
       else
       {      
         password = ( String ) httpSession.getAttribute ( SESSION_PASSWORD );

         username = ( String ) httpSession.getAttribute ( SESSION_USERNAME );

         if ( requestType.equals ( TYPE_LOGOUT ) )
         {
           httpSession.invalidate ( );
         }
       }

       Request  request = ObjectFactory.createRequest ( );

       request.setType ( requestType );

       request.setUsername ( username );

       request.setPassword ( password );

       return request;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }