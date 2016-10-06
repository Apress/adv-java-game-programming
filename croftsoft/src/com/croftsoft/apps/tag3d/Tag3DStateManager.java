     package com.croftsoft.apps.tag3d;

     import java.rmi.*;
     import java.util.HashMap;
     import java.util.Map;

     import javax.media.j3d.*;

     import com.croftsoft.core.media.j3d.Transform3DState;
     import com.croftsoft.core.util.state.*;

     /*********************************************************************
     * Bidirectional routing of Transform3DState updates.
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-02-07
     *********************************************************************/

     public class  Tag3DStateManager
       implements StateListener, StateMulticaster
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected Map               stateMap = new HashMap ( );

     protected StateMulticaster  stateMulticaster;
     protected Tag3DWorld        tag3DWorld;
     protected String            id;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Establishes the objects for bidirectional communication.
     *
     * @param  stateMulticaster
     *   All of Tag3DStateManager StateMulticaster interface method calls
     *   are relayed to the StateMulticaster implementation provided by
     *   this argument.
     * @param  tag3DWorld
     *   Updated when the State events are received via the StateListener
     *   method.
     * @param  id
     *   The unique identifier for this particular client.
     *********************************************************************/
     public  Tag3DStateManager (
       StateMulticaster  stateMulticaster,
       Tag3DWorld        tag3DWorld,
       String            id )
     //////////////////////////////////////////////////////////////////////
     {
       this.stateMulticaster = stateMulticaster;
       this.tag3DWorld       = tag3DWorld;
       this.id               = id;

       stateMap.put ( id, tag3DWorld.viewTransformGroup );

       addStateListener ( this );
     }

     /*********************************************************************
     * this ( new QueuedStateMulticaster ( ), tag3DWorld, id );
     *********************************************************************/
     public  Tag3DStateManager (
       Tag3DWorld        tag3DWorld,
       String            id )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new QueuedStateMulticaster ( ), tag3DWorld, id );
     }

     //////////////////////////////////////////////////////////////////////
     // StateListener interface method
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Handles Transform3DState update events from the StateMulticaster.
     * Updates the appropriate transformGroup in the stateMap as keyed by
     * the State key.  If the key does not exist in the stateMap, updates
     * the tag3DWorld with a newly created transformGroup and adds it to
     * the stateMap.
     *********************************************************************/
     public void  stateListen ( State  state )
     //////////////////////////////////////////////////////////////////////
     {
       if ( !( state instanceof Transform3DState ) ) return;

       String  key = ( String ) state.getKey ( );
       Transform3D  transform3D = new Transform3D ( );
       ( ( Transform3DState ) state ).getTransform3D ( transform3D );

       TransformGroup  transformGroup = null;

       synchronized ( stateMap )
       {
         transformGroup = ( TransformGroup ) stateMap.get ( key );

         // If no transformGroup currently exists for this I.D. key,
         // create one; otherwise, update it.

         if ( transformGroup == null )
         {
           transformGroup = tag3DWorld.addHead ( key, transform3D );
           stateMap.put ( key, transformGroup );
         }
         else
         {
           transformGroup.setTransform ( transform3D );
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     // StateMulticaster interface methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( State  state )
     //////////////////////////////////////////////////////////////////////
     {
       stateMulticaster.update ( state );
     }

     public boolean  addStateListener ( StateListener  stateListener )
     //////////////////////////////////////////////////////////////////////
     {
       return stateMulticaster.addStateListener ( stateListener );
     }

     public boolean  removeStateListener ( StateListener  stateListener )
     //////////////////////////////////////////////////////////////////////
     {
       return stateMulticaster.removeStateListener ( stateListener );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Sets up this Tag3DStateManager object to use a remote
     * StateMulticaster and subscribes itself as a remote StateListener.
     *
     * <P>
     *
     * If the remote StateMulticaster cannot be contacted, an attempt will
     * be made to establish this Tag3DStateManager object as the remote
     * StateMulticaster.
     *
     * @param  remoteName
     *   The RMI URL of the remote StateMulticaster.
     *********************************************************************/
     public void  setupRemote ( String  remoteName )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         StateMulticasterRemote  stateMulticasterRemote = null;

         try
         {
           stateMulticasterRemote
             = ( StateMulticasterRemote ) Naming.lookup ( remoteName );
           stateMulticaster
             = new StateMulticasterAmbassador ( stateMulticasterRemote );
           StateListenerRemote  stateListenerRemote
             = new StateListenerProxy ( this );
           Naming.rebind ( id, stateListenerRemote );
           addStateListener ( new StateListenerAmbassador (
             stateListenerRemote ) );
         }
         catch ( Exception  ex )
         {
         }

         if ( stateMulticasterRemote == null )
         {
           stateMulticasterRemote = new StateMulticasterProxy ( this );
           Naming.rebind ( remoteName, stateMulticasterRemote );
         }
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
