     package com.croftsoft.core.animation.painter;

     import java.awt.Graphics2D;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.ComponentPainter;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.util.ArrayLib;

     /*********************************************************************
     * Makes a ComponentPainter array look like a single ComponentPainter.
     *
     * @version
     *   2003-07-05
     * @since
     *   2002-03-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ArrayComponentPainter
       implements ComponentPainter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private ComponentPainter [ ]  componentPainters;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  ArrayComponentPainter (
       ComponentPainter [ ]  componentPainters )
     //////////////////////////////////////////////////////////////////////
     {
       setComponentPainters ( componentPainters );
     }

     public  ArrayComponentPainter ( ) 
     //////////////////////////////////////////////////////////////////////
     {
       this ( new ComponentPainter [ 0 ] );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor/mutator methods
     //////////////////////////////////////////////////////////////////////

     public ComponentPainter [ ]  getComponentPainters ( )
     //////////////////////////////////////////////////////////////////////
     {
       return componentPainters;
     }

     public void  add ( ComponentPainter  componentPainter )
     //////////////////////////////////////////////////////////////////////
     {
       componentPainters = ( ComponentPainter [ ] )
         ArrayLib.append ( componentPainters, componentPainter );
     }

     public void  setComponentPainters (
       ComponentPainter [ ]  componentPainters )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.componentPainters = componentPainters );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Paints each element in the ComponentPainter array.
     *********************************************************************/
     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < componentPainters.length; i++ )
       {
         componentPainters [ i ].paint ( component, graphics );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }