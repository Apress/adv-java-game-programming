     package com.croftsoft.ajgp.http;

     import java.io.*;

     import javax.servlet.*;
     import javax.servlet.http.*;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.io.Encoder;
     import com.croftsoft.core.io.Parser;
     import com.croftsoft.core.io.SerializableLib;
     import com.croftsoft.core.io.StringCoder;
     import com.croftsoft.core.role.Server;
     import com.croftsoft.core.servlet.HttpGatewayServlet;

     /*********************************************************************
     * High score servlet.
     *
     * @version
     *   2003-06-04
     * @since
     *   2003-06-01
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ScoreServlet
       extends HttpGatewayServlet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  VERSION = "2003-06-03";

     private static final String  TITLE   = "CroftSoft ScoreServlet";

     private static final String  SERVLET_INFO
       = "\n" + TITLE
       + "\n" + CroftSoftConstants.COPYRIGHT
       + "\n" + CroftSoftConstants.HOME_PAGE
       + "\n" + "Version " + VERSION
       + "\n" + CroftSoftConstants.DEFAULT_LICENSE
       + "\n";

     private static final String  PRIMARY_FILENAME = "score.dat";

     private static final String  BACKUP_FILENAME  = "score.bak";

     private static final String  CHAR_SET_NAME    = StringCoder.UTF_8;

     private static final String  GET_REQUEST      = "get";

     private static final String  SET_REQUEST      = "set ";

     private static final StringCoder  STRING_CODER
       = new StringCoder ( CHAR_SET_NAME );

     //

     private Long  highScoreLong;      

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  ScoreServlet ( )
     //////////////////////////////////////////////////////////////////////
     {
       super ( ( Server ) null, STRING_CODER, STRING_CODER );
     }

     //////////////////////////////////////////////////////////////////////
     // overridden Servlet methods
     //////////////////////////////////////////////////////////////////////

     public String  getServletInfo ( ) { return SERVLET_INFO; }

     public void  init ( )
       throws ServletException
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( SERVLET_INFO );

       try
       {
         highScoreLong = ( Long )
           SerializableLib.load ( PRIMARY_FILENAME, BACKUP_FILENAME );
       }
       catch ( FileNotFoundException  ex )
       {
       }
       catch ( Exception  ex )
       {
         log ( ex.getMessage ( ), ex );
       }

       if ( highScoreLong == null )
       {
         highScoreLong = new Long ( 0 );
       }
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         SerializableLib.save (
           highScoreLong, PRIMARY_FILENAME, BACKUP_FILENAME );
       }
       catch ( Exception  ex )
       {
         log ( ex.getMessage ( ), ex );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // overridden HttpGatewayServlet methods
     //////////////////////////////////////////////////////////////////////

     protected Object  serve ( Object  request )
     //////////////////////////////////////////////////////////////////////
     {
       String  requestString
         = ( ( String ) request ).trim ( ).toLowerCase ( );

       if ( requestString.equals ( GET_REQUEST ) )
       {
         return highScoreLong;
       }

       if ( requestString.startsWith ( SET_REQUEST ) )
       {
         String  newHighScoreString
           = requestString.substring ( SET_REQUEST.length ( ) );

         long  newHighScore = Long.parseLong ( newHighScoreString );

         synchronized ( this )
         {
           if ( newHighScore > highScoreLong.longValue ( ) )
           {
             highScoreLong = new Long ( newHighScore );
           }
         }

         return null;
       }

       throw new IllegalArgumentException ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
