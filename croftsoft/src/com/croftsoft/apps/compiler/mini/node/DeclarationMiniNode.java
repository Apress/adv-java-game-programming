     package com.croftsoft.apps.compiler.mini.node;

     /*********************************************************************
     * Abstract parse tree node for the Mini programming language.
     *
     * @see
     *   MiniNode
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-04-27
     *********************************************************************/

     public interface  DeclarationMiniNode extends MiniNode
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public NameSequenceMiniNode  getNameSequenceMiniNode ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
