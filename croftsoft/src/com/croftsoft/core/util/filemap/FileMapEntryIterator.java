     package com.croftsoft.core.util.filemap;

     import java.io.*;
     import java.util.*;

     /*********************************************************************
     * This Iterator is required for the AbstractMap implementation of
     * FileMap.
     *
     * @see
     *   FileMap
     * @see
     *   java.util.AbstractSet
     *
     * @version
     *   1999-04-03
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  FileMapEntryIterator implements Iterator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private File        rootDirectory;

     private String [ ]  files;
     private int         index = 0;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  FileMapEntryIterator ( File  rootDirectory )
     //////////////////////////////////////////////////////////////////////
     {
       this.rootDirectory = rootDirectory;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized boolean  hasNext ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( files == null ) files = rootDirectory.list ( );

       if ( files == null ) return false;

       return ( index < files.length );
     }

     public synchronized Object  next ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( files == null ) files = rootDirectory.list ( );

       if ( files == null ) return null;

       if ( index >= files.length ) return null;
       
       return new FileMapEntry ( rootDirectory, files [ index++ ] );
     }

     public synchronized void  remove ( )
     //////////////////////////////////////////////////////////////////////
     {
       if ( files == null ) files = rootDirectory.list ( );

       if ( files == null ) return;

       if ( index >= files.length ) return;
       
       new File ( files [ index ] ).delete ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
