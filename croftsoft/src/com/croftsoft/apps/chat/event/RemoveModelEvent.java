     package com.croftsoft.apps.chat.event;

     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.core.animation.model.ModelId;

     /*********************************************************************
     * A model was removed from the World.
     *
     * @version
     *   2003-06-07
     * @since
     *   2003-06-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  RemoveModelEvent
       extends AbstractEvent
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     private final ModelId  modelId;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  RemoveModelEvent ( ModelId  modelId )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.modelId = modelId );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public ModelId  getModelId ( ) { return modelId; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
