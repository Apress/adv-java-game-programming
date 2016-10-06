     package com.croftsoft.core.net.news;

     import java.util.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Newsgroup metadata.
     *
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</A>
     * @version
     *   2001-08-03
     * @since
     *   1997-05-11
     *********************************************************************/

     public final class  Newsgroup
       implements java.io.Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 1L;

     //

     private final long     estimated;

     private final long     firstArticle;

     private final long     lastArticle;

     private final String   groupName;

     //

//   private final String   serverName;

//   private final boolean  postingAllowed;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Parses the GROUP command resonse to get the newsgroup metadata.
     *
     * <pre>
     * 211 n f l s group selected
     *      (n = estimated number of articles in group,
     *      f = first article number in the group,
     *      l = last article number in the group,
     *      s = name of the group.)
     * 411 no such news group
     * </pre>
     *********************************************************************/
     public static Newsgroup  parse ( String  groupCommandResponse )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( groupCommandResponse );

       try
       {
         StringTokenizer  stringTokenizer
           = new StringTokenizer ( groupCommandResponse, " " );

         String  responseCode = stringTokenizer.nextToken ( );

         if ( "411".equals ( responseCode ) )
         {
           return null;
         }

         if ( !"211".equals ( responseCode ) )
         {
           throw new IllegalArgumentException ( groupCommandResponse );
         }

         long  estimated
           = Long.parseLong ( stringTokenizer.nextToken ( ) );

         long  first
           = Long.parseLong ( stringTokenizer.nextToken ( ) );

         long  last
           = Long.parseLong ( stringTokenizer.nextToken ( ) );

         String  group = stringTokenizer.nextToken ( );

         return new Newsgroup ( estimated, first, last, group );
       }
       catch ( Exception  ex )
       {
         throw new IllegalArgumentException ( groupCommandResponse );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Newsgroup (
       long     estimated,
       long     firstArticle,
       long     lastArticle,
       String   groupName )
     //////////////////////////////////////////////////////////////////////
     {
       this.estimated    = estimated;

       this.firstArticle = firstArticle;

       this.lastArticle  = lastArticle;

       NullArgumentException.check ( this.groupName  = groupName  );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public long     getEstimated      ( ) { return estimated;      }

     public long     getFirstArticle   ( ) { return firstArticle;   }

     public long     getLastArticle    ( ) { return lastArticle;    }

     public String   getGroupName      ( ) { return groupName;      }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
