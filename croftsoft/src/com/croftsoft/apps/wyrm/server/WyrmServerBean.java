     package com.croftsoft.apps.wyrm.server;

     import java.rmi.*;
     import java.util.*;
     import javax.naming.*;
     import javax.rmi.*;

     import javax.ejb.*;

     import javax.xml.bind.JAXBException;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.MathLib;

     import com.croftsoft.apps.wyrm.WyrmConstants;
     import com.croftsoft.apps.wyrm.entity.PcLocal;
     import com.croftsoft.apps.wyrm.entity.PcLocalHome;
     import com.croftsoft.apps.wyrm.entity.UserLocal;
     import com.croftsoft.apps.wyrm.entity.UserLocalHome;
     import com.croftsoft.apps.wyrm.xjc.*;

     /*********************************************************************
     * Wyrm server stateless session EJB.
     *
     * @version
     *   2002-11-04
     * @since
     *   2002-10-17
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public class  WyrmServerBean
       implements SessionBean, WyrmConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  JNDI_PC_LOCAL_HOME
       = "java:comp/env/ejb/PcLocalHome";

     private static final String  JNDI_USER_LOCAL_HOME
       = "java:comp/env/ejb/UserLocalHome";

     private static final String  JNDI_INIT_HEALTH
       = "java:comp/env/initial_health";

     //

     private static final String  STATE_DUNGEON   = "dungeon";

     private static final String  STATE_HEALER    = "healer";

     private static final String  STATE_TOWN      = "town";

     //

     private static final double  INIT_USER_CREDITS = 0.0;

     //

     private static final String  INIT_PC_NAME       = "Unnamed Hero";

     private static final String  INIT_PC_STATE      = STATE_TOWN;

     private static final long    INIT_PC_HEALTH     = 10;

     private static final long    INIT_PC_WEALTH     = 100;

     private static final long    INIT_PC_LEVEL      = 0;

     private static final long    INIT_PC_EXPERIENCE = 0;

     //

     private static final int  HEALING_COST = 10;

     //

     private static final Random  random = new Random ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Object  serve ( Object  requestObject )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         NullArgumentException.check ( requestObject );

         if ( !( requestObject instanceof Request ) )
         {
           String  type = requestObject.getClass ( ).getName ( );

           return createResponse (
             type,
             false,
             "unknown request type:  " + type,
             null );
         }

         Request  request = ( Request ) requestObject;

         String  requestType = request.getType ( );

         if ( requestType == null )
         {
           return createResponse (
             null,
             false,
             "null request type",
             null );
         }

         requestType = requestType.trim ( );

         String  username = request.getUsername ( );

         String  password = request.getPassword ( );

         if ( requestType.equals ( TYPE_CREATE_USER ) )
         {
           return serveRequestCreateUser ( username, password );
         }

         if ( username == null )
         {
           return createResponse (
             TYPE_LOGIN,
             false,
             "Welcome to the Wyrm!",
             null );
         }

         if ( password == null )
         {
           return createResponse (
             TYPE_LOGIN,
             false,
             "Null password.",
             username );
         }

         UserLocal  userLocal = getUserLocal ( username, password );

         if ( userLocal == null )
         {
           return createResponse (
             TYPE_LOGIN,
             false,
             "Unknown username or incorrect password.",
             username );
         }

         try
         {
           if ( requestType.equals ( TYPE_ACCOUNT ) )
           {
             return serveRequestAccount ( userLocal );
           }

           if ( requestType.equals ( TYPE_DESTROY_USER ) )
           {
             return serveRequestDestroyUser ( userLocal );
           }

           if ( requestType.equals ( TYPE_DUNGEON ) )
           {
             return serveRequestDungeon ( userLocal );
           }

           if ( requestType.equals ( TYPE_FIGHT ) )
           {
             return serveRequestFight ( userLocal );
           }

           if ( requestType.equals ( TYPE_FLEE ) )
           {
             return serveRequestFlee ( userLocal );
           }

           if ( requestType.equals ( TYPE_HEAL ) )
           {
             return serveRequestHeal ( userLocal );
           }

           if ( requestType.equals ( TYPE_HEALER ) )
           {
             return serveRequestHealer ( userLocal );
           }

           if ( requestType.equals ( TYPE_LOGIN ) )
           {
             return serveRequestLogin ( userLocal );
           }

           if ( requestType.equals ( TYPE_LOGOUT ) )
           {
             return serveRequestLogout ( username );
           }

           if ( requestType.equals ( TYPE_STATE ) )
           {
             return serveRequestState ( userLocal );
           }

           if ( requestType.equals ( TYPE_TOWN ) )
           {
             return serveRequestTown ( userLocal );
           }
         }
         catch ( IllegalStateException  ex )
         {
           ResponseState  responseState = serveRequestState ( userLocal );

           responseState.setMessage (
             "Your request is not allowed from here." );

           return responseState;
         }

         return createResponse (
           requestType,
           false,
           "unknown request type:  " + requestType,
           username );
       }
       catch ( Exception  ex )
       {
         // Throwing EJBException will cause the transaction to be
         // rolled back automatically.
         // See p433, Monson-Haefel, "Enterprise Java Beans", 3rd Ed., 2001

         throw new EJBException ( ex );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private Object  serveRequestAccount ( UserLocal  userLocal )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       ResponseAccount  responseAccount
         = ObjectFactory.createResponseAccount ( );

       responseAccount.setUsername   ( userLocal.getUsername   ( ) );

       responseAccount.setFirstName  ( userLocal.getFirstName  ( ) );

       responseAccount.setMiddleName ( userLocal.getMiddleName ( ) );

       responseAccount.setLastName   ( userLocal.getLastName   ( ) );

       responseAccount.setCredits    ( userLocal.getCredits    ( ) );

       return responseAccount;
     }

     private Object  serveRequestCreateUser (
       String  username,
       String  password )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       if ( username == null )
       {
         return createResponse (
           TYPE_CREATE_USER,
           false,
           null,
           null );
       }

       boolean  granted
         = ( password != null )
         && !"".equals ( username.trim ( ) )
         && !"".equals ( password.trim ( ) )
         && username.equals ( username.toLowerCase ( ) )
         && username.equals ( username.trim ( ) );

// TODO:  Need to also check that username just letters, numbers,
// and underscore with no white space as well.

       if ( !granted )
       {
         return createResponse (
           TYPE_CREATE_USER,
           false,
           "Bad username or password.",
           username );
       }

       Context  context = new InitialContext ( );

       Object  obj = context.lookup ( JNDI_USER_LOCAL_HOME );

       UserLocalHome  userLocalHome = ( UserLocalHome )
         PortableRemoteObject.narrow ( obj, UserLocalHome.class );

       UserLocal  userLocal = null;

       try
       {
         userLocal = userLocalHome.findByUsername ( username );

         return createResponse (
           TYPE_CREATE_USER,
           false,
           "Username already in use.",
           username );
       }
       catch ( ObjectNotFoundException  ex )
       {
       }       

       userLocal = userLocalHome.create ( username );

       userLocal.setPassword ( password );

       userLocal.setCredits ( INIT_USER_CREDITS );

       createPcLocal ( userLocal );

       return serveRequestState ( userLocal );
     }

     private Object  serveRequestDestroyUser ( UserLocal  userLocal )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       PcLocal  pcLocal = userLocal.getPcLocal ( );

       // This prevents a database persistent memory leak.

       if ( pcLocal != null )
       {
         pcLocal.remove ( );
       }

       String  username = userLocal.getUsername ( );

       userLocal.remove ( );

       return createResponse (
         TYPE_DESTROY_USER,
         true,
         "User " + username + " destroyed.",
         username );
     }

     private Object  serveRequestDungeon ( UserLocal  userLocal )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       PcLocal  pcLocal = getPcLocal ( userLocal );

       checkState ( pcLocal, STATE_TOWN );

       pcLocal.setState ( STATE_DUNGEON );

       ResponseState  responseState = serveRequestState ( userLocal );

       responseState.setMessage ( "You have entered the dungeon." );

       return responseState;
     }

     private Object  serveRequestFight ( UserLocal  userLocal )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       PcLocal  pcLocal = getPcLocal ( userLocal );

       checkState ( pcLocal, STATE_DUNGEON );

       boolean  victory = random.nextBoolean ( );

       String  message = null;

       if ( victory )
       {
         message = "You conquer and find treasure.";

         pcLocal.setWealth ( pcLocal.getWealth ( ) + 1 );

         long  experience = pcLocal.getExperience ( ) + 1;

         pcLocal.setExperience ( experience );

         long  level = pcLocal.getLevel ( );

         if ( MathLib.log ( experience, 10 ) >= level + 1 )
         {
           pcLocal.setLevel ( ++level );
         }
       }
       else
       {
         message = "You have been wounded.";

         pcLocal.setHealth ( pcLocal.getHealth ( ) - 1 );
       }

       ResponseState  responseState = serveRequestState ( userLocal );

       responseState.setMessage ( message );

       return responseState;
     }

     private Object  serveRequestFlee ( UserLocal  userLocal )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       PcLocal  pcLocal = getPcLocal ( userLocal );

       checkState ( pcLocal, STATE_DUNGEON );

       pcLocal.setState ( STATE_TOWN );

       pcLocal.setHealth ( pcLocal.getHealth ( ) - 1 );

       ResponseState  responseState = serveRequestState ( userLocal );

       responseState.setMessage ( "You are wounded as you turn to flee." );

       return responseState;
     }

     private Object  serveRequestHeal ( UserLocal  userLocal )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       PcLocal  pcLocal = getPcLocal ( userLocal );

       checkState ( pcLocal, STATE_HEALER );

       pcLocal.setWealth ( pcLocal.getWealth ( ) - HEALING_COST );

       pcLocal.setHealth ( INIT_PC_HEALTH );

       pcLocal.setState ( STATE_TOWN );

       ResponseState  responseState = serveRequestState ( userLocal );

       responseState.setMessage (
         "You return to the town square after being healed." );

       return responseState;
     }

     private Object  serveRequestHealer ( UserLocal  userLocal )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       PcLocal  pcLocal = getPcLocal ( userLocal );

       checkState ( pcLocal, STATE_TOWN );

       pcLocal.setState ( STATE_HEALER );

       ResponseState  responseState = serveRequestState ( userLocal );

       responseState.setMessage ( "You enter the Healer's hut." );

       return responseState;
     }

     private Object  serveRequestLogin ( UserLocal  userLocal )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       ResponseState  responseState = serveRequestState ( userLocal );

       responseState.setMessage ( "Welcome to the Wyrm!" );

       return responseState;
     }

     private Object  serveRequestLogout ( String  username )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       return createResponse (
         TYPE_LOGOUT,
         true,
         "Goodbye.",
         username );
     }

     private Object  serveRequestTown ( UserLocal  userLocal )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       PcLocal  pcLocal = getPcLocal ( userLocal );

       checkState ( pcLocal, STATE_HEALER );

       pcLocal.setState ( STATE_TOWN );

       ResponseState  responseState = serveRequestState ( userLocal );

       responseState.setMessage ( "You enter the town square." );

       return responseState;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     private UserLocal  getUserLocal (
       String  username,
       String  password )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       // Can I move some of this code to ejbCreate() so that it is only
       // executed once?

       InitialContext  initialContext = new InitialContext ( );

       Object  obj = initialContext.lookup ( JNDI_USER_LOCAL_HOME );

       UserLocalHome  userLocalHome = ( UserLocalHome )
         PortableRemoteObject.narrow ( obj, UserLocalHome.class );

       UserLocal  userLocal = null;

       try
       {
         userLocal = userLocalHome.findByUsername ( username );
       }
       catch ( ObjectNotFoundException  ex )
       {
         // Can I use userLocalHome.userExists(username) instead?

         return null;
       }

       if ( !password.equals ( userLocal.getPassword ( ) ) )
       {
         return null;
       }

       return userLocal;
     }

     private ResponseState  serveRequestState ( UserLocal  userLocal )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       ResponseState  responseState
         = ObjectFactory.createResponseState ( );

       responseState.setUsername ( userLocal.getUsername ( ) );

       responseState.setCredits ( userLocal.getCredits ( ) );

       PcLocal  pcLocal = getPcLocal ( userLocal );

       responseState.setState  ( pcLocal.getState  ( ) );

       responseState.setPcName ( pcLocal.getName   ( ) );

       responseState.setHealth ( pcLocal.getHealth ( ) );

       responseState.setWealth ( pcLocal.getWealth ( ) );

       responseState.setLevel  ( pcLocal.getLevel  ( ) );

       responseState.setExperience ( pcLocal.getExperience ( ) );

       responseState.setMessage ( "" );

       return responseState;
     }

     private PcLocal  getPcLocal ( UserLocal  userLocal )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       PcLocal  pcLocal = userLocal.getPcLocal ( );

       if ( pcLocal == null )
       {
         pcLocal = createPcLocal ( userLocal );
       }

       return pcLocal;
     }

     private PcLocal  createPcLocal ( UserLocal  userLocal )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       InitialContext  initialContext = new InitialContext ( );

       Object  obj = initialContext.lookup ( JNDI_PC_LOCAL_HOME );

       PcLocalHome  pcLocalHome = ( PcLocalHome )
         PortableRemoteObject.narrow ( obj, PcLocalHome.class );

       PcLocal  pcLocal = pcLocalHome.create ( );

       pcLocal.setName       ( INIT_PC_NAME       );

       pcLocal.setState      ( INIT_PC_STATE      );

       pcLocal.setHealth     ( INIT_PC_HEALTH     );

       pcLocal.setWealth     ( INIT_PC_WEALTH     );

       pcLocal.setLevel      ( INIT_PC_LEVEL      );

       pcLocal.setExperience ( INIT_PC_EXPERIENCE );

       userLocal.setPcLocal ( pcLocal );

       Long  envInitHealth
         = ( Long ) initialContext.lookup ( JNDI_INIT_HEALTH );

       if ( envInitHealth != null )
       {
         pcLocal.setHealth ( envInitHealth.longValue ( ) );
       }

       return pcLocal;
     }

     private void  checkState (
       PcLocal  pcLocal,
       String   desiredState )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       String  state = pcLocal.getState ( );

       if ( state == null )
       {
         state = INIT_PC_STATE;
       }

       if ( !state.equals ( desiredState ) )
       {
         throw new IllegalStateException ( );
       }       
     }

     private Response  createResponse (
       String   type,
       boolean  granted,
       String   message,
       String   username )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       Response  response = ObjectFactory.createResponse ( );

       response.setType ( type );

       response.setGranted ( granted );

       response.setMessage ( message != null ? message : "" );

       response.setUsername ( username != null ? username : "" );

       return response;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  ejbActivate  ( ) { }

     public void  ejbCreate    ( ) { }

     public void  ejbPassivate ( ) { }

     public void  ejbRemove    ( ) { }

     public void  setSessionContext ( SessionContext  sessionContext ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
