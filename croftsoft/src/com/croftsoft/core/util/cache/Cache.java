     package com.croftsoft.core.util.cache;

     import java.io.*;

     import com.croftsoft.core.util.id.Id;

     /*********************************************************************
     * Stores the content and then returns an Id which may be used to
     * read the content later, if still available.
     *
     * @see
     *   SoftCache
     *
     * @version
     *   2003-06-07
     * @since
     *   1999-04-24
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public interface  Cache
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     *
     * "Validates" the content by
     *
     * <OL>
     *
     * <LI> confirming that identical content already exists in the cache;
     *      or, if otherwise necessary,
     *
     * <LI> storing a new copy of the content in the cache.
     *
     * </OL>
     *
     * @param  id
     *
     *   The content identifier passed to isAvailable() to determine if
     *   the content is already valid.  The parameter may be any Id
     *   object that potentially matches via the equals() method.
     *
     * @param  contentAccessor
     *
     *   An object capable of making content accessible via an InputStream.
     *   For example, a ContentAccessor might retrieve content from a
     *   website via a URL, a database or file storage, a remote object
     *   such as another cache, or even dynamically generate the content
     *   upon demand.  As yet another possibility, a ContentAccessor object
     *   may potentially attempt to access the content from several
     *   different sources sequentially until it is successful.
     *
     * @return
     *
     *   Returns an Id object for the validated content which may be
     *   used later for retrieval.
     *
     *   <P>
     *
     *   If valid content was already available in the cache, the returned
     *   Id object will be the id parameter.
     *
     *   <P>
     *
     *   If valid content was not already available and the content could
     *   not be accessed and stored via the contentAccessor, the returned
     *   value will be null.
     *
     *   <P>
     *
     *   If valid content was not already available and the content could
     *   be accessed and stored via the contentAccessor, the returned
     *   value will be a new Id object with values that may or may
     *   not equal that of the id object parameter, depending on
     *   the actual content available via the contentAccessor.
     *
     *********************************************************************/
     public Id  validate ( Id  id, ContentAccessor  contentAccessor )
       throws IOException;

     /*********************************************************************
     * Stores the contents and returns an Id to be used for retrieval.
     *
     * <P>
     *
     * Reads the stream until completion and closes it before return.
     *
     * @return
     *   Returns an Id to be used for later retrieval.
     *   Returns null if the storage was unsuccessful.
     *********************************************************************/
     public Id  store ( InputStream  in ) throws IOException;

     /*********************************************************************
     * Retrieves the content associated with this Id.
     *
     * @param  id
     *   Returns the content associated with this id or its equivalent.
     * @return
     *   May return null.
     *********************************************************************/
     public InputStream  retrieve ( Id  id ) throws IOException;

     /*********************************************************************
     * Returns false if the content is no longer available.
     *
     * @param  id
     *   An Id object to be used to retrieve the content.
     *********************************************************************/
     public boolean  isAvailable ( Id  id );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
