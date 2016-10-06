     package com.croftsoft.core.animation.collector;

     import java.awt.Rectangle;

     import com.croftsoft.core.animation.RepaintCollector;
     import com.croftsoft.core.util.ArrayLib;

     /*********************************************************************
     * Simply collects the repaint requests.
     *
     * @version
     *   2002-12-01
     * @since
     *   2002-12-01
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  SimpleRepaintCollector
       implements RepaintCollector
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private int            count;

     private Rectangle [ ]  repaintRegions;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  SimpleRepaintCollector ( )
     //////////////////////////////////////////////////////////////////////
     {
       repaintRegions = new Rectangle [ 0 ];
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public int  getCount ( ) { return count; }

     public Rectangle [ ]  getRepaintRegions ( )
     //////////////////////////////////////////////////////////////////////
     {
       return repaintRegions;
     }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  repaint (
       int  x,
       int  y,
       int  width,
       int  height )
     //////////////////////////////////////////////////////////////////////
     {
       if ( count == repaintRegions.length )
       {
         repaintRegions = ( Rectangle [ ] ) ArrayLib.append (
           repaintRegions, new Rectangle ( x, y, width, height ) );
       }
       else
       {
         repaintRegions [ count ].setBounds ( x, y, width, height );        
       }

       count++;
     }

     public void  repaint ( )
     //////////////////////////////////////////////////////////////////////
     {
       repaint ( 0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE );
     }

     public void  reset ( )
     //////////////////////////////////////////////////////////////////////
     {
       count = 0;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
