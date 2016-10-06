     package com.croftsoft.core.text.sml;

     /*********************************************************************
     * Receives tokens from SML parser.
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     *
     * @version
     *   2001-05-10
     * @since
     *   2001-05-10
     * @author
     *   <a href="http://www.alumni.caltech.edu/~croft/">David W. Croft</a>
     *********************************************************************/

     public interface  SmlParseHandler
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  handleCData ( String  cData );

     public void  handleElementOpen ( String  elementName );

     public void  handleElementClose ( String  elementName );

     public void  handleParseError ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
