     package com.croftsoft.core.util.cache;

     import java.io.*;

     /*********************************************************************
     * An object capable of making content accessible via an InputStream.
     *
     * <P>
     *
     * For example, a ContentAccessor might retrieve content from a
     * website via a URL, a database or file storage, a remote object
     * such as another cache, or even dynamically generate the content
     * upon demand.
     *
     * <P>
     *
     * As yet another possibility, a ContentAccessor object
     * may potentially attempt to access the content from several
     * different sources sequentially until it is successful.
     *
     * @see
     *   URLContentAccessor
     *
     * @version
     *   1999-04-24
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public interface  ContentAccessor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public InputStream  getInputStream ( ) throws IOException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
