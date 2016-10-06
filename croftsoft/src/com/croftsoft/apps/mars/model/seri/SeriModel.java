     package com.croftsoft.apps.mars.model.seri;

     import java.io.Serializable;

     import com.croftsoft.apps.mars.model.Model;

     /*********************************************************************
     * The base abstract class for a game world object Model.
     *
     * @version
     *   2003-04-16
     * @since
     *   2003-03-30
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public abstract class  SeriModel
       implements Comparable, Model, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 0L;

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