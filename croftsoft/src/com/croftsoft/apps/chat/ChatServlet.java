     package com.croftsoft.apps.chat;

     import javax.servlet.*;

     import com.croftsoft.core.io.SerializableCoder;
     import com.croftsoft.core.servlet.HttpGatewayServlet;

     import com.croftsoft.apps.chat.server.ChatServer;

     /*********************************************************************
     * Chat servlet.
     *
     * @version
     *   2003-06-17
     * @since
     *   2000-04-27
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ChatServlet
       extends HttpGatewayServlet
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final ChatServer  chatServer;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     private  ChatServlet ( ChatServer  chatServer )
     //////////////////////////////////////////////////////////////////////
     {
       super (
         chatServer,
         SerializableCoder.INSTANCE,
         SerializableCoder.INSTANCE );

       this.chatServer = chatServer;
     }

     public  ChatServlet ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new ChatServer ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     // overridden Servlet methods
     //////////////////////////////////////////////////////////////////////

     public String  getServletInfo ( ) { return ChatConstants.INFO; }

     public void  init ( )
       throws ServletException
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( getServletInfo ( ) );

       chatServer.init ( );
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       chatServer.destroy ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
