     package com.croftsoft.core.animation.updater;

     import javax.swing.JComponent;

     import com.croftsoft.core.animation.ComponentUpdater;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.util.ArrayLib;

     /*********************************************************************
     * Makes a ComponentUpdater array look like a single ComponentUpdater.
     *
     * @version
     *   2003-07-05
     * @since
     *   2002-03-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ArrayComponentUpdater
       implements ComponentUpdater
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private ComponentUpdater [ ]  componentUpdaters;

     //////////////////////////////////////////////////////////////////////
     // constructor methods
     //////////////////////////////////////////////////////////////////////

     public  ArrayComponentUpdater (
       ComponentUpdater [ ]  componentUpdaters )
     //////////////////////////////////////////////////////////////////////
     {
       setComponentUpdaters ( componentUpdaters );
     }

     public  ArrayComponentUpdater ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new ComponentUpdater [ 0 ] );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor/mutator methods
     //////////////////////////////////////////////////////////////////////

     public ComponentUpdater [ ]  getComponentUpdaters ( )
     //////////////////////////////////////////////////////////////////////
     {
       return componentUpdaters;
     }

     public void  add ( ComponentUpdater  componentUpdater )
     //////////////////////////////////////////////////////////////////////
     {
       componentUpdaters = ( ComponentUpdater [ ] )
         ArrayLib.append ( componentUpdaters, componentUpdater );
     }

     public void  setComponentUpdaters (
       ComponentUpdater [ ]  componentUpdaters )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.componentUpdaters = componentUpdaters );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Updates each element in the ComponentUpdater array.
     *********************************************************************/
     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < componentUpdaters.length; i++ )
       {
         componentUpdaters [ i ].update ( component );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
