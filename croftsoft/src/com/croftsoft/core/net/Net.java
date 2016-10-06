     package com.croftsoft.core.net;

     import com.croftsoft.core.lang.*;
     
     /*********************************************************************
     * <P>
     * A collection of constants and static methods to supplement
     * the java.net package.
     * <P>
     * @version
     *   1997-04-18
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     *********************************************************************/

     public final class  Net {
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Removes everything between bracket pairs plus the brackets.
     *********************************************************************/
     public static final String  strip_HTML_tags ( String  s ) {
     //////////////////////////////////////////////////////////////////////
       String  stripped = new String ( s );
       int  index = -1;
       int  end_index;
       while ( ( index = stripped.indexOf ( '<' ) ) >= 0 ) {
         if ( ( end_index = stripped.indexOf ( '>', index ) ) >= 0 ) {
           stripped = StringLib.remove ( stripped, index, end_index );
         } else break;
       }
       return stripped;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
