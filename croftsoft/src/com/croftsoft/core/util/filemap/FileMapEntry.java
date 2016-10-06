     package com.croftsoft.core.util.filemap;

     import java.io.*;
     import java.util.*;

     /*********************************************************************
     * This Map.Entry is required for the AbstractMap implementation of
     * FileMap.
     *
     * @see
     *   FileMapEntryIterator
     * @see
     *   FileMapEntrySet
     * @see
     *   java.util.Map.Entry
     *
     * @version
     *   1999-04-03
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  FileMapEntry implements Map.Entry
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private File    rootDirectory;
     private String  filename;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  FileMapEntry ( File  rootDirectory, String  filename )
     //////////////////////////////////////////////////////////////////////
     {
       this.rootDirectory = rootDirectory;
       this.filename      = filename;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  equals ( Object  o )
     //////////////////////////////////////////////////////////////////////
     {
       if ( o == null ) return false;

       if ( !o.getClass ( ).equals ( this.getClass ( ) ) ) return false;

       FileMapEntry  other = ( FileMapEntry ) o;

       if ( !filename.equals ( other.filename ) ) return false;

       if ( !rootDirectory.equals ( other.rootDirectory ) ) return false;

       if ( hashCode ( ) != other.hashCode ( ) ) return false;

       return true;
     }

     public Object  getKey ( )
     //////////////////////////////////////////////////////////////////////
     {
       return filename;
     }

     public Object  getValue ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         return new FileInputStream (
           new File ( rootDirectory, filename ) );
       }
       catch ( FileNotFoundException  ex )
       {
         return null;
       }
     }

     public int  hashCode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return filename.hashCode ( );
     }

     public Object  setValue ( Object  value )
     //////////////////////////////////////////////////////////////////////
     {
       throw new UnsupportedOperationException ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
