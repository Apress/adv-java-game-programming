     package com.croftsoft.core.lang;

     /*********************************************************************
     * A collection of static methods to manipulate Thread objects.
     *
     * @version
     *   1999-10-02
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  ThreadLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Puts the current Thread to sleep for a number of milliseconds.
     *
     * @return
     *   Returns false if InterruptedException was thrown.
     *********************************************************************/
     public static boolean  sleep ( long  millis )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         Thread.currentThread ( ).sleep ( millis );
       }
       catch ( InterruptedException  ex )
       {
         return false;
       }

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
