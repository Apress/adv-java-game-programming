     package com.croftsoft.apps.wyrm.entity;

     import java.security.*;
     import java.util.*;

     import javax.ejb.*;

     /*********************************************************************
     * The User Bean.
     *
     * @version
     *   2002-10-31
     * @since
     *   2002-09-27
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public abstract class  UserBean
       implements EntityBean
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final Random  random = new SecureRandom ( );

     //

     public abstract Long     getId         ( );

     public abstract String   getUsername   ( );

     public abstract String   getPassword   ( );

     public abstract String   getFirstName  ( );

     public abstract String   getMiddleName ( );

     public abstract String   getLastName   ( );

     public abstract double   getCredits    ( );

     public abstract PcLocal  getPcLocal    ( );

     //

     public abstract void  setId         ( Long     id         );

     public abstract void  setUsername   ( String   username   );

     public abstract void  setPassword   ( String   password   );

     public abstract void  setFirstName  ( String   firstName  );

     public abstract void  setMiddleName ( String   middleName );

     public abstract void  setLastName   ( String   lastName   );

     public abstract void  setCredits    ( double   credits    );

     public abstract void  setPcLocal    ( PcLocal  pcLocal    );

     //

     public abstract Collection  ejbSelectUsernames ( )
       throws FinderException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String [ ]  ejbHomeGetUsernames ( )
       throws FinderException
     //////////////////////////////////////////////////////////////////////
     {
       return ( String [ ] )
         ejbSelectUsernames ( ).toArray ( new String [ 0 ] );
     }

     public Long  ejbCreate ( String  username )
       throws CreateException
     //////////////////////////////////////////////////////////////////////
     {
       setId ( new Long ( random.nextLong ( ) ) );

       setUsername ( username );

       return null;
     }

     public void  ejbPostCreate ( String  username ) { }

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
