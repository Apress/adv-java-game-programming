     package com.croftsoft.apps.tag3d;

     import java.applet.*;
     import java.awt.*;
     import java.awt.event.*;
     import java.rmi.Naming;
     import java.util.HashMap;
     import java.util.Map;

     import javax.media.j3d.*;
     import javax.vecmath.*;

     import com.sun.j3d.utils.applet.MainFrame;
     import com.sun.j3d.utils.behaviors.mouse.*;
     import com.sun.j3d.utils.geometry.Box;
     import com.sun.j3d.utils.geometry.ColorCube;
     import com.sun.j3d.utils.image.TextureLoader;
     import com.sun.j3d.utils.universe.*;

     import com.croftsoft.core.media.j3d.Transform3DLib;
     import com.croftsoft.core.media.j3d.Transform3DState;
     import com.croftsoft.core.util.state.*;

     /*********************************************************************
     *
     * The Tag3D universe and methods to build it.
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-02-07
     *********************************************************************/

     public class  Tag3DWorld
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected Canvas3D        canvas3D;
     protected Locale          locale;
     protected TransformGroup  viewTransformGroup;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static Tag3DWorld  build ( )
     //////////////////////////////////////////////////////////////////////
     {
       Canvas3D  canvas3D = new Canvas3D ( null );

       VirtualUniverse  virtualUniverse = new VirtualUniverse ( );
       Locale  locale = new Locale ( virtualUniverse );

       PhysicalBody  physicalBody = new PhysicalBody ( );
       PhysicalEnvironment  physicalEnvironment
         = new PhysicalEnvironment ( );

       View  view = new View ( );

       // Antialiasing slows things down dramatically.
       // view.setSceneAntialiasingEnable ( true );

       view.addCanvas3D ( canvas3D );
       view.setPhysicalBody ( physicalBody );
       view.setPhysicalEnvironment ( physicalEnvironment );
       BranchGroup  viewBranchGroup = new BranchGroup ( );

       Transform3D  transform3D = new Transform3D ( );

       ViewPlatform  viewPlatform = new ViewPlatform ( );

       TransformGroup  viewTransformGroup
         = new TransformGroup ( transform3D );

       viewTransformGroup.setCapability (
         TransformGroup.ALLOW_TRANSFORM_READ );
       viewTransformGroup.setCapability (
         TransformGroup.ALLOW_TRANSFORM_WRITE );

       viewTransformGroup.addChild ( viewPlatform );
       viewBranchGroup.addChild ( viewTransformGroup );

       view.attachViewPlatform ( viewPlatform );

       locale.addBranchGraph ( viewBranchGroup );
       locale.addBranchGraph ( createSceneGraph ( ) );

       return new Tag3DWorld ( canvas3D, locale, viewTransformGroup );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static BranchGroup  createSceneGraph ( )
     //////////////////////////////////////////////////////////////////////
     {
       BranchGroup  rootBranchGroup = new BranchGroup ( );

       BoundingSphere  boundingSphere
         = new BoundingSphere ( new Point3d ( 0.0, 0.0, 0.0 ), 10.0 );

       Clip  clip = new Clip ( 10000.0 );
       clip.setApplicationBounds (
         new BoundingSphere ( new Point3d ( 0.0, 0.0, 0.0 ), 10000.0 ) );
       rootBranchGroup.addChild ( clip );

/*
       Background  background = new Background ( 0.9f, 1.0f, 0.9f );
       background.setApplicationBounds (
         new BoundingSphere ( new Point3d ( 0.0, 0.0, 0.0 ), 5.0 ) );
       rootBranchGroup.addChild ( background );
*/


/*
       Fog  fog = new ExponentialFog ( 0.0f, 0.1f, 0.2f );
       fog.setInfluencingBounds ( boundingSphere );
       rootBranchGroup.addChild ( fog );

       Light  light = new PointLight ( );
       light.setInfluencingBounds ( boundingSphere );
       rootBranchGroup.addChild ( light );
*/

       int  distance = 10;

       SharedGroup  sharedGroup = new SharedGroup ( );
       sharedGroup.addChild ( new ColorCube ( 0.5 ) );
       sharedGroup.compile ( );

       for ( int  x = -2 * distance; x < 3 * distance; x += distance )
       for ( int  y = -2 * distance; y < 3 * distance; y += distance )
       for ( int  z = -2 * distance; z < 3 * distance; z += distance )
       {
         if ( ( x == 0 ) && ( y == 0 ) && ( z == 0 ) ) continue;
         Transform3D  transform3D = new Transform3D ( );
         transform3D.setTranslation ( new Vector3d ( x, y, z ) );

         TransformGroup  transformGroup
           = new TransformGroup ( transform3D );

         transformGroup.addChild ( new Link ( sharedGroup ) );

         rootBranchGroup.addChild ( transformGroup );
       }

       rootBranchGroup.compile ( );

       return rootBranchGroup;
     }

     public static void  addHead (
       String          id,
       TransformGroup  transformGroup,
       Component       component )
     //////////////////////////////////////////////////////////////////////
     {
       Font3D font3D = new Font3D (
         new Font ( "Courier", Font.PLAIN, 1 ),
         new FontExtrusion ( ) );
       Text3D text3D = new Text3D ( font3D, id,
         new Point3f ( 0.0f, -1.2f, 0.0f ),
         Text3D.ALIGN_CENTER, Text3D.PATH_RIGHT );
       Shape3D shape3D = new Shape3D ( );
       Appearance  appearance = new Appearance ( );
       Material  material = new Material ( );
       material.setEmissiveColor ( new Color3f ( 1.0f, 0.0f, 0.0f ) );
       material.setLightingEnable ( true );
       appearance.setMaterial ( material );
       shape3D.setGeometry ( text3D );
       shape3D.setAppearance ( appearance );

       BoundingSphere  boundingSphere
         = new BoundingSphere ( new Point3d ( 0.0, 0.0, 0.0 ), 10.0 );

       TransformGroup  textTransformGroup
         = new TransformGroup ( new Transform3D ( ) );
       Alpha  rotationAlpha = new Alpha (
         -1, Alpha.INCREASING_ENABLE,
         0, 0,
         4000, 0, 0,
         0, 0, 0 );
       RotationInterpolator  rotationInterpolator
         = new RotationInterpolator (
         rotationAlpha, textTransformGroup, new Transform3D ( ),
         0.0f, 2.0f * (float) Math.PI );
       rotationInterpolator.setSchedulingBounds ( boundingSphere );
       textTransformGroup.setCapability (
         TransformGroup.ALLOW_TRANSFORM_WRITE );
       textTransformGroup.addChild ( rotationInterpolator );
       textTransformGroup.addChild ( shape3D );
       transformGroup.addChild ( textTransformGroup );

       Text3D  chatText3D = new Text3D ( font3D, "Hello!",
         new Point3f ( 0.0f, 1.2f, 0.0f ),
         Text3D.ALIGN_CENTER, Text3D.PATH_RIGHT );
       Shape3D  chatShape3D = new Shape3D ( );
       chatShape3D.setGeometry ( chatText3D );
       chatShape3D.setAppearance ( appearance );
       TransformGroup  chatTransformGroup
         = new TransformGroup ( new Transform3D ( ) );
       chatTransformGroup.setCapability (
         TransformGroup.ALLOW_TRANSFORM_WRITE );
       Billboard  billboard = new Billboard ( chatTransformGroup );
       billboard.setSchedulingBounds ( boundingSphere );
       chatTransformGroup.addChild ( chatShape3D );
//     chatTransformGroup.addChild ( billboard );
       transformGroup.addChild ( chatTransformGroup );
           

       transformGroup.setCapability (
         TransformGroup.ALLOW_TRANSFORM_READ );
       transformGroup.setCapability (
         TransformGroup.ALLOW_TRANSFORM_WRITE );

       MouseRotate  mouseRotate = new MouseRotate ( );
       mouseRotate.setTransformGroup ( transformGroup );
       transformGroup.addChild ( mouseRotate );
       mouseRotate.setSchedulingBounds ( boundingSphere );

       // Create the zoom behavior node
       MouseZoom  mouseZoom = new MouseZoom ( );
       mouseZoom.setTransformGroup ( transformGroup );
       transformGroup.addChild ( mouseZoom );
       mouseZoom.setSchedulingBounds ( boundingSphere );

       // Create the translate behavior node
       MouseTranslate  mouseTranslate = new MouseTranslate ( );
       mouseTranslate.setTransformGroup ( transformGroup );
       transformGroup.addChild ( mouseTranslate );
       mouseTranslate.setSchedulingBounds ( boundingSphere );


       Appearance app = new Appearance ( );
       Texture  tex = new TextureLoader (
         "images/face.jpg", component ).getTexture ( );
       app.setTexture(tex);
       if ( tex == null )
       {
         System.out.println("Warning: Texture is disabled");
       }
       Box textureCube = new Box ( 0.5f, 0.5f, 0.5f,
         Box.GENERATE_TEXTURE_COORDS, app );
       transformGroup.addChild ( textureCube );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Tag3DWorld (
       Canvas3D        canvas3D,
       Locale          locale,
       TransformGroup  viewTransformGroup )
     //////////////////////////////////////////////////////////////////////
     {
       this.canvas3D           = canvas3D;
       this.locale             = locale;
       this.viewTransformGroup = viewTransformGroup;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public TransformGroup  addHead (
       String          id,
       Transform3D     transform3D )
     //////////////////////////////////////////////////////////////////////
     {
       TransformGroup  transformGroup = new TransformGroup ( transform3D );
       addHead ( id, transformGroup, canvas3D );

       BranchGroup  rootBranchGroup = new BranchGroup ( );
       rootBranchGroup.addChild ( transformGroup );
       rootBranchGroup.compile ( );
       locale.addBranchGraph ( rootBranchGroup );

       return transformGroup;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
