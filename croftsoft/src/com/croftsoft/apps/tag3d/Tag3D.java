     package com.croftsoft.apps.tag3d;

     /*********************************************************************
     *
     * Tag3D main application class.
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-02-07
     *********************************************************************/

     public final class  Tag3D implements Tag3DConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       if ( System.getSecurityManager ( ) == null )
       {
         System.setSecurityManager ( new Tag3DSecurityManager ( ) );
       }

       Tag3DWorld  tag3DWorld = Tag3DWorld.build ( );

       String  id = ( args.length > 0 ? args [ 0 ] : DEFAULT_ID );

       Tag3DStateManager  tag3DStateManager
         = new Tag3DStateManager ( tag3DWorld, id );

       tag3DWorld.canvas3D.addKeyListener (
         new Tag3DKeyListener (
           DELTA_ROTATION,
           DELTA_TRANSLATION,
           id,
           tag3DStateManager,
           tag3DWorld.viewTransformGroup ) );

       new Tag3DFrame ( 0, 0, 256, 256, id, tag3DWorld.canvas3D ).show ( );

       if ( args.length > 1 ) tag3DStateManager.setupRemote ( args [ 1 ] );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
