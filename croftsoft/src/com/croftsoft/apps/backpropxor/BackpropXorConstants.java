     package com.croftsoft.apps.backpropxor;

     import java.awt.Dimension;

     /*********************************************************************
     * BackpropXor constants.
     *
     * @version
     *   2002-02-28
     * @since
     *   2002-02-28
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  BackpropXorConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  VERSION = "2002-02-28";

     public static final String  TITLE = "CroftSoft BackpropXOR";

     public static final String  APPLET_INFO
       = "\n" + TITLE + "\n"
       + "\u00A9 2002 CroftSoft Inc\n"
       + "http://www.croftsoft.com/\n"
       + "Version " + VERSION + "\n";

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static final long  REPAINT_PERIOD = 1000;

     public static final String  SHUTDOWN_CONFIRMATION_PROMPT
       = "Close BackpropXOR?";

     public static final Dimension  FRAME_SIZE = null;

// change this to fill frame?
     public static final Dimension  SIZE = new Dimension ( 600, 400 );

     public static final int  OVAL_SIZE = 4;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static final int  ITERATIONS_PER_EPOCH = 100;

//     BA Z N X N X N X N A X A N B N O O
//        E O A B B A O A N N   X   X R N
//        R R         R N D O   B   A   E
//        O             D   R
//     00 0 1 0 1 0 1 0 1 0 1 0 1 0 1 0 1
//     01 0 0 1 1 0 0 1 1 0 0 1 1 0 0 1 1
//     10 0 0 0 0 1 1 1 1 0 0 0 0 1 1 1 1
//     11 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1

     public static final String [ ]  FUNCTION_NAMES = {
       "ZERO",        
       "NOR",
       "XA",
       "NB",
       "XB",
       "NA",
       "XOR",
       "NAND",
       "AND",
       "XNOR",
       "A",
       "NXB",
       "B",
       "NXA",
       "OR",
       "ONE" };

     public static final int  INITIAL_FUNCTION = 8; // AND

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static final int  FONT_SIZE = 10;

     public static final int  Y = 60;

     public static final int  XTAB = FONT_SIZE * 7;

     public static final int  YTAB
       = ( int ) ( ( double ) FONT_SIZE * 1.5 );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
