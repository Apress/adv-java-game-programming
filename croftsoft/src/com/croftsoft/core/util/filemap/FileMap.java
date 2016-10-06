     package com.croftsoft.core.util.filemap;

     import java.io.*;
     import java.util.*;

     /*********************************************************************
     * A Map that stores the values in files within a directory.
     *
     * Uses filenames as the keys and InputStreams as the values.
     *
     * @see
     *   FilenameKeyGenerator
     * @see
     *   java.util.AbstractMap
     *
     * @version
     *   1999-04-03
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  FileMap extends AbstractMap
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private File  rootDirectory;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( test ( ) );
     }

     public static boolean  test ( )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         String  filename = "com.orbs.util.filemap.FileMap_test.tmp";

         FileMap  fileMap = new FileMap ( new File ( "." ) );

         fileMap.put ( filename,
           new ByteArrayInputStream ( "Hello".getBytes ( ) ) );

         InputStream  in = ( InputStream ) fileMap.get ( filename );

         int  i;
         while ( ( i = in.read ( ) ) > -1 ) System.out.write ( i );

         in.close ( );

         System.out.println ( "" );

         fileMap.remove ( filename );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );

         return false;
       }

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  FileMap ( File  rootDirectory )
     //////////////////////////////////////////////////////////////////////
     {
       this.rootDirectory = rootDirectory;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Set  entrySet ( )
     //////////////////////////////////////////////////////////////////////
     {
       return new FileMapEntrySet ( rootDirectory );
     }

// synchronize?

     public synchronized Object  put ( Object  key, Object  value )
     //////////////////////////////////////////////////////////////////////
     {
       File                  file = null;
       BufferedOutputStream  out  = null;
       InputStream           in   = null;

       try
       {
         String  filename = ( String ) key;

         file = new File ( rootDirectory, filename );

         out = new BufferedOutputStream ( new FileOutputStream ( file ) );

         in = ( InputStream ) value;

         int  i;
         while ( ( i = in.read ( ) ) > -1 ) out.write ( i );
       }
       catch ( IOException  ioex )
       {
         try { out.close   ( ); } catch ( Exception  ex ) { }
         try { file.delete ( ); } catch ( Exception  ex ) { }
         throw new RuntimeException ( ioex.getMessage ( ) );
       }
       finally
       {
         try { in.close  ( ); } catch ( Exception  ex ) { }
         try { out.close ( ); } catch ( Exception  ex ) { }
       }

       return null;
     }

     public synchronized Object  remove ( Object  key )
     //////////////////////////////////////////////////////////////////////
     {
       String  filename = ( String ) key;

       File  file = new File ( rootDirectory, filename );

       file.delete ( );

       return null;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
