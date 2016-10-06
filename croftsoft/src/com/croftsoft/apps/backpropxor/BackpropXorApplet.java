     package com.croftsoft.apps.backpropxor;

     import javax.swing.JApplet;

     /*********************************************************************
     * BackpropXor as a JApplet.
     *
     * @version
     *   2002-02-28
     * @since
     *   1996
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  BackpropXorApplet
       extends JApplet
       implements BackpropXorConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private BackpropXor  backpropXor;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getAppletInfo ( )
     //////////////////////////////////////////////////////////////////////
     {
       return APPLET_INFO;
     }

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       setContentPane ( backpropXor = new BackpropXor ( ) );

       backpropXor.init ( );
     }

     public void  start   ( ) { backpropXor.start   ( ); }

     public void  stop    ( ) { backpropXor.stop    ( ); }

     public void  destroy ( ) { backpropXor.destroy ( ); }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
