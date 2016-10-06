     package com.croftsoft.core.gui;

     import java.awt.*;
     import java.awt.event.*;

     /*********************************************************************
     *
     * @version
     *   1998-11-01
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  ExitOnClosingWindowAdapter extends WindowAdapter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  windowClosing ( WindowEvent  windowEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Window  window = windowEvent.getWindow ( );
       window.dispose ( );
       System.exit ( 0 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
