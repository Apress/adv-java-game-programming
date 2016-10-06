     package com.croftsoft.core.animation.model.seri;

     import java.io.Serializable;

     import com.croftsoft.core.animation.model.Model;
     import com.croftsoft.core.animation.model.ModelId;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * The base abstract class for a game world object Model.
     *
     * @version
     *   2003-06-07
     * @since
     *   2003-03-30
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public abstract class  SeriModel
       implements Model, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

     //

     protected final ModelId  modelId;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SeriModel ( ModelId  modelId )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.modelId = modelId );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public ModelId  getModelId ( ) { return modelId; }

     //////////////////////////////////////////////////////////////////////
     // interface Comparable method
     //////////////////////////////////////////////////////////////////////

     public int  compareTo ( Object  other )
     //////////////////////////////////////////////////////////////////////
     {
       double  otherZ = ( ( Model ) other ).getZ ( );

       double  z = getZ ( );

       if ( z < otherZ )
       {
         return -1;
       }

       if ( z > otherZ )
       {
         return 1;
       }

       return 0;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
