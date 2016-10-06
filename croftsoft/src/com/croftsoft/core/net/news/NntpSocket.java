     package com.croftsoft.core.net.news;

     import java.net.*;
     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.util.log.Log;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Implements NNTP as given by RFC 977.
     *
     * @see
     *   "Common NNTP Extensions (AUTHINFO),
     *   <a target=&quot;blank&quot;
     * href=&quot;http://www.mibsoftware.com/userkt/nntpext/0032.htm&quot;>
     *            http://www.mibsoftware.com/userkt/nntpext/0032.htm</a>"
     *
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</A>
     * @version
     *   2001-08-03
     * @since
     *   1997-05-10
     *********************************************************************/

     public class  NntpSocket extends Socket
       implements NntpConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private BufferedReader  bufferedReader;

     private PrintStream     printStream;

     private String          responseCode;

     private Vector          response_text;

     private boolean         postingAllowed;

//   private Vector          newsgroup_Vector;

     private Log             log;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Be sure to call command_QUIT() when done.
     *********************************************************************/
     public  NntpSocket (
       String  nntpServerHost,
       Log     log )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       super ( nntpServerHost, NNTP_PORT );

       this.log = log;

       setSoLinger ( true, 30 ); // timeout after 30 seconds

       bufferedReader = new BufferedReader (
         new InputStreamReader ( getInputStream ( ) ) );

       printStream = new PrintStream ( getOutputStream ( ) );

       responseCode = bufferedReader.readLine ( );

       record ( responseCode );

       if ( responseCode.startsWith ( "200" ) )
       {
         postingAllowed = true;
       }
       else if ( !responseCode.startsWith ( "201" ) )
       {
         throw new IOException ( "Connection failed:  " + responseCode );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public BufferedReader  getBufferedReader ( )
     //////////////////////////////////////////////////////////////////////
     {
       return bufferedReader;
     }

     /*********************************************************************
     * Returns the response code for the last command.
     *********************************************************************/
     public String  getResponseCode ( ) { return responseCode; }

     /*********************************************************************
     * Returns the response text, a Vector of String, for the last command.
     *********************************************************************/
     public Vector  response_text ( ) { return response_text; }

     /*********************************************************************
     * Returns true if posting to this server is allowed.
     *********************************************************************/
     public boolean  getPostingAllowed ( ) { return postingAllowed; }

     /*********************************************************************
     * Returns a Vector of Newsgroup.
     *********************************************************************/
//   public Vector  newsgroup_Vector ( ) { return newsgroup_Vector; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Loads the response_text from the NNTPSocket InputStream.
     * Decodes it as well.
     *********************************************************************/
     public synchronized Vector  load_response_text ( )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       response_text = new Vector ( );

       String  line;

int  count = 0;

       while ( ( line = bufferedReader.readLine ( ) ) != null )
       {
         if ( line.equals ( "." ) ) break;

         line = NntpLib.decodeLine ( line );

         response_text.addElement ( line );

if ( ( ++count % 1000 ) == 0 ) System.out.print ( "." );

       }

System.out.println ( "\n" + count + " newsgroups" );

       return response_text;
     }

     /*********************************************************************
     * Sends command + CR_LF to the server.
     * Returns the responseCode.
     *********************************************************************/
     public synchronized String  command ( String  command )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       printStream.print ( command + CR_LF );

       record ( command + CR_LF );

       responseCode = bufferedReader.readLine ( );

       record ( responseCode );

       return responseCode;
     }

     /*********************************************************************
     *********************************************************************/
/*
     public synchronized String  command_GROUP ( String  group_name )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       responseCode = command ( COMMAND_GROUP + " " + group_name );

       if ( responseCode.startsWith ( "211" ) )
       {
         
         StringTokenizer  st = new StringTokenizer ( responseCode );

         try
         {
           st.nextToken ( );
           int  estimated_articles = Integer.parseInt ( st.nextToken ( ) );
           int  first_article = Integer.parseInt ( st.nextToken ( ) );
           int  last_article = Integer.parseInt ( st.nextToken ( ) );
           String  new_group_name = st.nextToken ( );

           if ( newsgroup_Vector != null )
           {
             Enumeration  e = newsgroup_Vector.elements ( );

             while ( e.hasMoreElements ( ) )
             {
               Newsgroup  newsgroup = ( Newsgroup ) e.nextElement ( );

               if ( newsgroup.getGroupName ( ).equals ( new_group_name ) )
               {
                 newsgroup_Vector.removeElement ( newsgroup );

                 break;
               }
             }
           } else newsgroup_Vector = new Vector ( );
           newsgroup_Vector.insertElementAt ( new Newsgroup (
             estimated_articles, first_article, last_article,
             new_group_name ), 0 );
         } catch ( Exception  e ) { e.printStackTrace ( ); }
       }
       return responseCode;
     }
*/

     /*********************************************************************
     * Loads the newsgroup_Vector.  Returns the responseCode.
     *********************************************************************/
/*
     public synchronized String  command_LIST ( ) throws IOException {
     //////////////////////////////////////////////////////////////////////
       newsgroup_Vector = new Vector ( );
       responseCode = command ( COMMAND_LIST );
       if ( responseCode.startsWith ( "2" ) ) {
         try { load_response_text ( ); }
         catch ( IOException  e ) { e.printStackTrace ( ); }
         Enumeration  e = response_text.elements ( );
         while ( e.hasMoreElements ( ) ) {
           String  line = ( String ) e.nextElement ( );
           StringTokenizer  st = new StringTokenizer ( line );
           try {
             while ( st.hasMoreTokens ( ) ) {
               String  group_name = st.nextToken ( );
               int  last_article = Integer.parseInt ( st.nextToken ( ) );
               int  first_article = Integer.parseInt ( st.nextToken ( ) );
               boolean  posting_ok
                 = st.nextToken ( ).equalsIgnoreCase ( "y" );
               newsgroup_Vector.addElement ( new Newsgroup (
                 group_name, last_article, first_article, posting_ok ) );
             }
           } catch ( Exception  ex ) { ex.printStackTrace ( ); }
         }
       }
       return responseCode;
     }
*/

     /*********************************************************************
     * Sends COMMAND_QUIT and then closes the NNTPSocket.
     *********************************************************************/
     public synchronized String  command_QUIT ( ) throws IOException {
     //////////////////////////////////////////////////////////////////////
       responseCode = command ( COMMAND_QUIT );
       close ( );
       return responseCode;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Log  getLog ( ) { return log; }

     public void  setLog ( Log  log ) { this.log = log; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private void  record ( String  entry )
     //////////////////////////////////////////////////////////////////////
     {
       Log  log = this.log;

       if ( log != null )
       {
         log.record ( entry );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
