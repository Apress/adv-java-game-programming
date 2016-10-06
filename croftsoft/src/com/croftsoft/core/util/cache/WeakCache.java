     package com.croftsoft.core.util.cache;

     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.util.id.*;

     /*********************************************************************
     * A Cache implementation that is backed by a WeakHashMap.
     *
     * <P>
     *
     * This Cache will dump its content if the IDs are not strongly
     * reachable.
     *
     * @see
     *   java.util.WeakHashMap
     * @see
     *   MemoryMapCache
     *
     * @version
     *   1999-04-18
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  WeakCache extends MemoryMapCache
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public  WeakCache ( )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new WeakHashMap ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
