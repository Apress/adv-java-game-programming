     package com.croftsoft.core.animation.collector;

     import java.awt.Rectangle;
     import java.awt.geom.Rectangle2D;

     import com.croftsoft.core.animation.RepaintCollector;
     import com.croftsoft.core.util.ArrayLib;

     /*********************************************************************
     * Coalesces repaint requests that intersect.
     *
     * @version
     *   2003-09-05
     * @since
     *   2003-02-03
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  CoalescingRepaintCollector
       implements RepaintCollector
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final Rectangle [ ]  ALL_REGIONS = new Rectangle [ ] {
       new Rectangle ( Integer.MAX_VALUE, Integer.MAX_VALUE ) };

     //

     private int            count;

     private boolean        repaintAll;

     private Rectangle [ ]  repaintRegions;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  CoalescingRepaintCollector ( )
     //////////////////////////////////////////////////////////////////////
     {
       repaintRegions = new Rectangle [ 0 ];
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public int  getCount ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( repaintAll )
       {
         return 1;
       }

       boolean  hasIntersections = true;

       while ( hasIntersections )
       {
         hasIntersections = false;

         iLoop:

         for ( int  i = 0; i < count - 1; i++ )
         {
           Rectangle  iRectangle = repaintRegions [ i ];

           for ( int  j = i + 1; j < count; j++ )
           {
             Rectangle  jRectangle = repaintRegions [ j ];

             if ( iRectangle.intersects ( jRectangle ) )
             {
               hasIntersections = true;

               Rectangle2D.union ( iRectangle, jRectangle, iRectangle );

               repaintRegions [ j ] = repaintRegions [ count - 1 ];

               repaintRegions [ count - 1 ] = jRectangle;

               count--;

               break iLoop;
             }
           }
         }
       }

       return count;
     }

     public Rectangle [ ]  getRepaintRegions ( )
     //////////////////////////////////////////////////////////////////////
     {
       return repaintAll ? ALL_REGIONS : repaintRegions;
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
       if ( repaintAll )
       {
         return;
       }

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
       repaintAll = true;
     }

     public void  reset ( )
     //////////////////////////////////////////////////////////////////////
     {
       count = 0;

       repaintAll = false;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
