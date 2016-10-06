     package com.croftsoft.core.media.j3d;

     import java.io.Serializable;

     import javax.media.j3d.Transform3D;

     import com.croftsoft.core.util.state.State;
     import com.croftsoft.core.util.state.StateLib;

     /*********************************************************************
     *
     * A State object that carries Transform3D data.
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-02-06
     *********************************************************************/

     public class  Transform3DState implements State, Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 1L;

     private Object      key;
     private double [ ]  transform;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Transform3DState (
       Object       key,
       Transform3D  transform3D )
     //////////////////////////////////////////////////////////////////////
     {
       this.key = key;
       transform = new double [ 16 ];
       transform3D.get ( transform );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Object  getKey ( ) { return key; }

     public void  getTransform3D ( Transform3D  transform3D )
     //////////////////////////////////////////////////////////////////////
     {
       transform3D.set ( transform );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Returns true if the classes and State keys are equal.
     *********************************************************************/
     public boolean  equals ( Object  other )
     //////////////////////////////////////////////////////////////////////
     {
       return StateLib.equals ( this, other );
     }

     /*********************************************************************
     * Returns the hash code of the State key.
     *********************************************************************/
     public int  hashCode ( )
     //////////////////////////////////////////////////////////////////////
     {
       return StateLib.hashCode ( this );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
