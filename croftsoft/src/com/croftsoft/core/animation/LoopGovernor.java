     package com.croftsoft.core.animation;

     /*********************************************************************
     * Regulates the loop frequency by putting the Thread to sleep.
     *
     * @deprecated
     *   Moved to package com.croftsoft.core.util.loop.
     *
     * @version
     *   2002-05-22
     * @since
     *   2002-03-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  LoopGovernor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * This method puts the Thread to sleep for a limited time.
     *********************************************************************/
     public void  govern ( )
       throws InterruptedException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
