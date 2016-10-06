     package com.croftsoft.apps.wyrm.entity;

     import java.security.SecureRandom;
     import java.util.Random;

     import javax.ejb.*;

     /*********************************************************************
     * The player character bean.
     *
     * @version
     *   2002-11-04
     * @since
     *   2002-09-30
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public abstract class  PcBean
       implements EntityBean
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public abstract Long    getId     ( );

     public abstract String  getName   ( );

     public abstract String  getState  ( );

     public abstract long    getHealth ( );

     public abstract long    getWealth ( );

     public abstract long    getLevel  ( );

     public abstract long    getExperience ( );

     //

     public abstract void  setId         ( Long    id         );

     public abstract void  setName       ( String  name       );

     public abstract void  setState      ( String  state      );

     public abstract void  setHealth     ( long    health     );

     public abstract void  setWealth     ( long    wealth     );

     public abstract void  setLevel      ( long    level      );

     public abstract void  setExperience ( long    experience );

     //

     private static final Random  random = new SecureRandom ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Long  ejbCreate ( )
       throws CreateException
     //////////////////////////////////////////////////////////////////////
     {
       setId ( new Long ( random.nextLong ( ) ) );

       return null;
     }

     public void  ejbPostCreate ( ) { }

     public void  setEntityContext ( EntityContext  ctx ) { }

     public void  unsetEntityContext ( ) { }

     public void  ejbActivate ( ) { }

     public void  ejbPassivate ( ) { }

     public void  ejbLoad ( ) { }

     public void  ejbStore ( ) { }

     public void  ejbRemove ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
