     package com.croftsoft.apps.spider;

     import java.io.*;
     import java.net.*;
     import java.util.*;
     import javax.swing.text.MutableAttributeSet;
     import javax.swing.text.html.HTML;
     import javax.swing.text.html.HTMLEditorKit;
     import javax.swing.text.html.parser.DocumentParser;
     import javax.swing.text.html.parser.DTD;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.role.Consumer;
     import com.croftsoft.core.role.Filter;

     /*********************************************************************
     * Web spider.
     * 
     * @version
     *   2003-04-09
     * @since
     *   2003-04-09
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  Spider
       implements Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  THREAD_NAME    = "Spider";

     private static final long    DOWNLOAD_DELAY = 1000;

     //

     private final Consumer  urlConsumer;

     private final Stack     stack;

     private final Set       knownSet;

     private final HTMLEditorKit.ParserCallback  parserCallback;

     //

     private Filter   urlFilter;

     private Filter   contentTypeFilter;

     private Thread   thread;

     private boolean  stopRequested;

     private URL      currentURL;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       Spider  spider = new Spider (
         new Consumer ( )
         {
           public void  consume ( Object  o )
           {
             System.out.println ( o );
           }
         } );

       SpiderUrlFilter  spiderUrlFilter = new SpiderUrlFilter ( spider );

       spiderUrlFilter.setSameHostOnly ( true );

       spiderUrlFilter.setSamePortOnly ( true );

       spider.setUrlFilter ( spiderUrlFilter );

       spider.setContentTypeFilter (
         new Filter ( )
         {
           public boolean  isFiltrate ( Object  o )
           {
             return ( ( String ) o ).equals ( "text/html" );
           }
         } );

       spider.push ( args [ 0 ] );

       spider.init ( );

       spider.start ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Spider (
       Consumer  urlConsumer,
       Filter    urlFilter,
       Filter    contentTypeFilter )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.urlConsumer = urlConsumer );

       setUrlFilter ( urlFilter );

       setContentTypeFilter ( contentTypeFilter );

       stack = new Stack ( );

       knownSet = new HashSet ( );

       parserCallback = new HTMLEditorKit.ParserCallback ( )
         {
           public void  handleSimpleTag (
             HTML.Tag             t,
             MutableAttributeSet  a,
             int                  pos )
           {
             if ( t == HTML.Tag.A )
             {
               push ( ( String ) a.getAttribute ( HTML.Attribute.HREF ) );
             }

             super.handleSimpleTag ( t, a, pos );
           }
         };
     }

     public  Spider ( Consumer  urlConsumer )
     //////////////////////////////////////////////////////////////////////
     {
       this ( urlConsumer, null, null );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public URL  getCurrentURL ( ) { return currentURL; }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public boolean  push ( String  urlString )
     //////////////////////////////////////////////////////////////////////
     {
       if ( urlString == null )
       {
         return false;
       }

       URL  newURL = null;

       try
       {
         if ( !urlString.trim ( ).toLowerCase ( ).startsWith ( "http:" ) )
         {
           String  externalForm = currentURL.toExternalForm ( );

           if ( !externalForm.endsWith ( "/" ) )
           {
             externalForm += "/";
           }

           newURL = new URL ( new URL ( externalForm ), urlString );
         }
         else
         {
           newURL = new URL ( urlString );
         }

         if ( newURL.getProtocol ( ).equals ( "http" )
           && ( ( urlFilter == null )
             || urlFilter.isFiltrate ( newURL ) ) )
         {
           String  externalForm = newURL.toExternalForm ( );

           if ( externalForm.endsWith ( "/" ) )
           {
             externalForm
               = externalForm.substring ( 0, externalForm.length ( ) - 1 );

             newURL = new URL ( externalForm );
           }

           // trim off the leading "http://"

           externalForm = externalForm.substring ( 7 );

           if ( !knownSet.contains ( externalForm ) )
           {
             stack.push ( newURL );

             knownSet.add ( externalForm );

             return true;
           }
         }
       }
       catch ( MalformedURLException  ex )
       {
       }

       return false;
     }

     public void  setContentTypeFilter ( Filter  contentTypeFilter )
     //////////////////////////////////////////////////////////////////////
     {
       this.contentTypeFilter = contentTypeFilter;
     }

     public void  setUrlFilter ( Filter  urlFilter )
     //////////////////////////////////////////////////////////////////////
     {
       this.urlFilter = urlFilter;
     }

     //////////////////////////////////////////////////////////////////////
     // interface Lifecycle methods
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public synchronized void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       stopRequested = false;

       if ( thread == null )
       {
         thread = new Thread (
           new Runnable ( )
           {
             public void  run ( )
             {
               loop ( );
             }
           },
           THREAD_NAME );

         thread.start ( );
       }
       else
       {
         notify ( );
       }
     }

     public synchronized void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       stopRequested = true;

       thread.interrupt ( );
     }

     public synchronized void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       thread = null;

       stopRequested = false;

       notify ( );
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private void  loop ( )
     //////////////////////////////////////////////////////////////////////
     {
       while ( thread != null )
       {
         try
         {
           spiderNext ( );

           if ( stopRequested )
           {
             synchronized ( this )
             {
               while ( stopRequested )
               {
                 wait ( );
               }
             }
           }
         }
         catch ( InterruptedException  ex )
         {
         }
       }
     }

     private void  spiderNext ( )
       throws InterruptedException
     //////////////////////////////////////////////////////////////////////
     {
       if ( stack.size ( ) < 1 )
       {
         stop ( );

         destroy ( );
       }

       Thread.sleep ( DOWNLOAD_DELAY );

       currentURL = ( URL ) stack.pop ( );

       try
       {
         HttpURLConnection  httpURLConnection
           = ( HttpURLConnection ) currentURL.openConnection ( );

         String  contentType = null;

         try
         {
           contentType = httpURLConnection.getContentType ( );

           if ( contentType.equals ( "text/html" ) )
           {
             BufferedReader  bufferedReader = new BufferedReader (
               new InputStreamReader ( currentURL.openStream ( ) ) );

             try
             {
               DocumentParser  documentParser
                 = new DocumentParser ( DTD.getDTD ( "html32" ) );

               documentParser.parse (
                 bufferedReader, parserCallback, true );
             }
             finally
             {
               bufferedReader.close ( );
             }
           }
         }
         finally
         {
           httpURLConnection.disconnect ( );
         }

         if ( ( contentTypeFilter == null )
           || ( contentType != null )
           && contentTypeFilter.isFiltrate ( contentType ) )
         {
           Thread.sleep ( DOWNLOAD_DELAY );

           urlConsumer.consume ( currentURL );
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }