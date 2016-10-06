     package com.croftsoft.core.util.cache;

     import java.io.*;
     import java.lang.ref.*;
     import java.util.*;

     import com.croftsoft.core.util.id.Id;

     /*********************************************************************
     * A Cache implementation that dumps its content when memory is low.
     *
     * <P>
     *
     * Backed by a WeakCache.  Note that the content will not be dumped if
     * its Id remains strongly reachable.
     *
     * <P>
     *
     * @see
     *   WeakCache
     * @see
     *   java.lang.ref.SoftReference
     *
     * @version
     *   1999-04-20
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  SoftCache implements Cache
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private WeakCache       weakCache      = new WeakCache      ( );
     private ReferenceQueue  referenceQueue = new ReferenceQueue ( );
     private Set             softSet        = new HashSet        ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Id  validate ( Id  id, ContentAccessor  contentAccessor )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       clearQueue ( );

       if ( isAvailable ( id ) ) return id;

       InputStream  inputStream = contentAccessor.getInputStream ( );

       if ( inputStream == null ) return null;

       return store ( inputStream );
     }

/*
     public Id  store ( InputStream  in, Id  id ) throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       clearQueue ( );

       id = weakCache.store ( in, id );

       softSet.add ( new SoftReference ( id, referenceQueue ) );

       return id;
     }
*/

     public Id  store ( InputStream  in ) throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       clearQueue ( );

       Id  id = weakCache.store ( in );

       softSet.add ( new SoftReference ( id, referenceQueue ) );

       return id;
     }

     public InputStream  retrieve ( Id  id ) throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       clearQueue ( );

       return weakCache.retrieve ( id );
     }

     public boolean  isAvailable ( Id  id )
     //////////////////////////////////////////////////////////////////////
     {
       clearQueue ( );

       return weakCache.isAvailable ( id );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  clearQueue ( )
     //////////////////////////////////////////////////////////////////////
     {
       Reference  reference = null;
       while ( ( reference = referenceQueue.poll ( ) ) != null )
       {
         softSet.remove ( reference );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
