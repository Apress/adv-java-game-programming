     package com.croftsoft.apps.chat.response;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Response to a MoveRequest.
     *
     * @version
     *   2003-06-10
     * @since
     *   2003-06-10
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  MoveResponse
       extends AbstractResponse
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final boolean  noModel;

     private final boolean  modelNotInWorld;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  MoveResponse (
       boolean  denied,
       boolean  noModel,
       boolean  modelNotInWorld )
     //////////////////////////////////////////////////////////////////////
     {
       super ( denied );

       this.noModel = noModel;

       this.modelNotInWorld = modelNotInWorld;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  getNoModel         ( ) { return noModel;         }

     public boolean  getModelNotInWorld ( ) { return modelNotInWorld; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
