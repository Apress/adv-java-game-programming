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
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-02-07
     *********************************************************************/

     public class  Tag3DFrame extends Frame
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public  Tag3DFrame (
       int       x,
       int       y,
       int       width,
       int       height,
       String    id,
       Canvas3D  canvas3D )
     //////////////////////////////////////////////////////////////////////
     {
       super ( "ORBS Tag3D (" + id + ")" );
       setBounds ( x, y, width, height );

       Panel  panel = new Panel ( );
       panel.setLayout ( new BorderLayout ( ) );

       panel.add ( "Center", canvas3D );

       add ( panel );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
