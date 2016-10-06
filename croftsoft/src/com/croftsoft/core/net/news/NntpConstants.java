     package com.croftsoft.core.net.news;

     import java.net.*;
     import java.io.*;
     import java.util.*;

     /*********************************************************************
     * Static constants for NNTP.
     *
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</A>
     * @version
     *   2001-07-23
     * @since
     *   2001-07-23
     *********************************************************************/

     public interface  NntpConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final int     NNTP_PORT         = 119;

     public static final String  COMMAND_ARTICLE   = "ARTICLE";
     public static final String  COMMAND_AUTHINFO  = "AUTHINFO";     
     public static final String  COMMAND_BODY      = "BODY";
     public static final String  COMMAND_GROUP     = "GROUP";
     public static final String  COMMAND_HEAD      = "HEAD";
     public static final String  COMMAND_HELP      = "HELP";
     public static final String  COMMAND_IHAVE     = "IHAVE";
     public static final String  COMMAND_LAST      = "LAST";
     public static final String  COMMAND_LIST      = "LIST";
     public static final String  COMMAND_NEWGROUPS = "NEWGROUPS";
     public static final String  COMMAND_NEWNEWS   = "NEWNEWS";
     public static final String  COMMAND_NEXT      = "NEXT";
     public static final String  COMMAND_POST      = "POST";
     public static final String  COMMAND_QUIT      = "QUIT";
     public static final String  COMMAND_SLAVE     = "SLAVE";
     public static final String  COMMAND_STAT      = "STAT";

     public static final String  CR_LF             = "\r\n";

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
