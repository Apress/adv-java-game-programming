     package com.croftsoft.core.util.cache;

     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.util.id.*;

     /*********************************************************************
     * A Cache implementation that is backed by a memory Map.
     *
     * <P>
     *
     * The InputStreams are read and stored to the Map as byte arrays.
     *
     * @see
     *   Cache
     * @see
     *   java.util.WeakHashMap
     * @see
     *   com.orbs.open.a.mpl.util.SoftHashMap
     *
     * @version
     *   1999-04-24
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  MemoryMapCache implements Cache
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private Map  map;

     //////////////////////////////////////////////////////////////////////
     // Constructor method
     //////////////////////////////////////////////////////////////////////

     public  MemoryMapCache ( Map  map )
     //////////////////////////////////////////////////////////////////////
     {
       if ( map == null )
       {
         throw new IllegalArgumentException ( "null map" );
       }

       this.map = map;
     }

     //////////////////////////////////////////////////////////////////////
     // Cache interface methods
     //////////////////////////////////////////////////////////////////////

     public Id  validate ( Id  id, ContentAccessor  contentAccessor )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       if ( isAvailable ( id ) ) return id;

       InputStream  inputStream = contentAccessor.getInputStream ( );

       if ( inputStream == null ) return null;

       return store ( inputStream );
     }

     public Id  store ( InputStream  in ) throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       Id  id = new IntId ( );

       map.put ( id, CacheLib.toByteArray ( in ) );

       return id;
     }

     public InputStream  retrieve ( Id  id ) throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       byte [ ]  content = ( byte [ ] ) map.get ( id );

       if ( content == null ) return null;

       return new ByteArrayInputStream ( content );
     }

     public boolean  isAvailable ( Id  id )
     //////////////////////////////////////////////////////////////////////
     {
       return map.containsKey ( id );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
