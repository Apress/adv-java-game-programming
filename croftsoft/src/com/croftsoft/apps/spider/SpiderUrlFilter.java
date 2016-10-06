     package com.croftsoft.apps.spider;

     import java.net.URL;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.role.Filter;

     /*********************************************************************
     * Web spider URL filter.
     * 
     * @version
     *   2003-04-09
     * @since
     *   2003-04-09
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  SpiderUrlFilter
       implements Filter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected final Spider  spider;

     //

     protected boolean  sameHostOnly;

     protected boolean  samePortOnly;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SpiderUrlFilter ( Spider  spider )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.spider = spider );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setSameHostOnly ( boolean  sameHostOnly )
     //////////////////////////////////////////////////////////////////////
     {
       this.sameHostOnly = sameHostOnly;
     }

     public void  setSamePortOnly ( boolean  samePortOnly )
     //////////////////////////////////////////////////////////////////////
     {
       this.samePortOnly = samePortOnly;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  isFiltrate ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       URL  newURL = ( URL ) o;

       URL  currentURL = spider.getCurrentURL ( );

       if ( ( currentURL != null ) && sameHostOnly )
       {
         if ( !newURL.getHost ( ).equals ( currentURL.getHost ( ) ) )
         {
           return false;
         }
       }

       if ( ( currentURL != null ) && samePortOnly )
       {
         int  newPort = newURL.getPort ( );

         int  oldPort = currentURL.getPort ( );

         if ( newPort == -1 )
         {
           newPort = 80;
         }

         if ( oldPort == -1 )
         {
           oldPort = 80;
         }

         if ( newPort != oldPort )
         {
           return false;
         }
       }

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }