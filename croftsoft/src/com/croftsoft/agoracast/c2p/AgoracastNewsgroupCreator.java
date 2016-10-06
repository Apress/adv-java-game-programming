     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import javax.swing.*;

     import com.croftsoft.core.net.news.NntpLib;
     import com.croftsoft.core.net.news.UsenetLib;
     import com.croftsoft.core.net.news.UsenetMessage;
     import com.croftsoft.core.security.PreIdentifier;
     import com.croftsoft.core.util.log.PrintStreamLog;

     /*********************************************************************
     * Creates the Agoracast newsgroup.
     *
     * <p />
     *
     * @version
     *   2001-07-30
     * @since
     *   2001-07-29
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastNewsgroupCreator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  EMAIL
       = "david@croftsoft.com";

     private static final String  NEWSGROUP
       = AgoracastConstants.DEFAULT_NEWSGROUP;

     private static final String  SHORT_DESCRIPTION
       = "Decentralized XML message exchange.";

     private static final String  CHARTER
       = "All posts must be in human-readable XML-based Agoracast format."
       + "\r\n"
       + "See http://agoracast.org/ for more information.";

     private static final String  JUSTIFICATION
       = "To create a decentralized exchange of XML messages.";

     private static final String  PROPONENT
       = "David Wallace Croft (" + EMAIL + ")";

     private static final boolean  IS_BOOSTER = false;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       String  nntpServer = args [ 0 ];

       String  username   = args.length > 1 ? args [ 1 ] : null;

       String  password   = args.length > 2 ? args [ 2 ] : null;

       UsenetMessage  usenetMessage = UsenetLib.createNewGroupMessage (
         EMAIL,
         NEWSGROUP,
         SHORT_DESCRIPTION,
         CHARTER,
         JUSTIFICATION,
         PROPONENT,
         IS_BOOSTER );

       NntpLib.post (
         nntpServer,
         new PreIdentifier ( username, password ),
         usenetMessage,
         PrintStreamLog.SYSTEM_OUT_INSTANCE );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  AgoracastNewsgroupCreator ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }