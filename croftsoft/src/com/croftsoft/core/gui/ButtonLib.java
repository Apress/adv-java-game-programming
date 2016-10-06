     package com.croftsoft.core.gui;

     import java.awt.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * A library of static methods to manipulate Button objects.
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     *
     * @version
     *   2001-06-04
     * @since
     *   2001-06-04
     * @author
     *   <a href="http://www.alumni.caltech.edu/~croft/">David W. Croft</a>
     *********************************************************************/

     public final class  ButtonLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static Button [ ]  createButtonArray ( String [ ]  labels )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( labels );

       Button [ ]  buttons = new Button [ labels.length ];

       for ( int  i = 0; i < labels.length; i++ )
       {
         buttons [ i ] = new Button ( labels [ i ] );
       }

       return buttons;
     }


     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private  ButtonLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }