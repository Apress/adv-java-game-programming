     package com.croftsoft.core.animation;
     
     import java.applet.*;
     import java.awt.*;
     import java.awt.image.*;
     import java.awt.event.*;
     import java.awt.geom.*;
     import java.net.*;
     import java.util.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.animation.*;
     import com.croftsoft.core.animation.painter.ArrayComponentPainter;
     import com.croftsoft.core.animation.updater.ArrayComponentUpdater;
     import com.croftsoft.core.gui.LifecycleWindowListener;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;

     /*********************************************************************
     * Animated applet.
     *
     * @version
     *   2003-08-05
     * @since
     *   2003-03-07
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  AnimatedApplet
       extends JApplet
       implements ComponentAnimator, Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected final AnimationInit      animationInit;

     protected final AnimatedComponent  animatedComponent;

     //

     protected ArrayComponentUpdater  arrayComponentUpdater;

     protected ArrayComponentPainter  arrayComponentPainter;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       AnimationInit  animationInit = null;

       if ( ( args == null    )
         || ( args.length < 1 ) )
       {
         animationInit = new AnimationInit ( );
       }
       else
       {
         animationInit = AnimationInit.load ( args [ 0 ] );
       }
       
       launch ( new AnimatedApplet ( animationInit ) );
     }

     public static void  launch ( AnimatedApplet  animatedApplet )
     //////////////////////////////////////////////////////////////////////
     {
       AnimationInit  animationInit = animatedApplet.animationInit;

       LifecycleWindowListener.launchAppletAsDesktopApp (
         animatedApplet,
         animationInit.getFrameTitle ( ),
         animationInit.getFrameIconFilename ( ),
         animatedApplet.getClass ( ).getClassLoader ( ),
         true, // useFullScreenToggler,
         animationInit.getFrameSize ( ),
         animationInit.getShutdownConfirmationPrompt ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AnimatedApplet ( AnimationInit  animationInit )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.animationInit = animationInit );

       Double  frameRate = animationInit.getFrameRate ( );

       if ( frameRate == null )
       {
         animatedComponent = new AnimatedComponent ( this );
       }
       else
       {
         animatedComponent
           = new AnimatedComponent ( this, frameRate.doubleValue ( ) );
       }
     }

     public  AnimatedApplet ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new AnimationInit ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     // Overridden Applet methods
     //////////////////////////////////////////////////////////////////////

     public String  getAppletInfo ( )
     //////////////////////////////////////////////////////////////////////
     {
       return animationInit.getAppletInfo ( );
     }

     //////////////////////////////////////////////////////////////////////
     // interface Lifecycle methods
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       String  appletInfo = getAppletInfo ( );

       if ( appletInfo != null )
       {
         System.out.println ( appletInfo );
       }

       Color  backgroundColor = animationInit.getBackgroundColor ( );

       if ( backgroundColor != null )
       {
         animatedComponent.setBackground ( backgroundColor );
       }

       Color  foregroundColor = animationInit.getForegroundColor ( );

       if ( foregroundColor != null )
       {
         animatedComponent.setForeground ( foregroundColor );
       }

       Font  font = animationInit.getFont ( );

       if ( font != null )
       {
         animatedComponent.setFont ( font );
       }

       Cursor  cursor = animationInit.getCursor ( );

       if ( cursor != null )
       {
         animatedComponent.setCursor ( cursor );
       }

       arrayComponentUpdater = animationInit.getArrayComponentUpdater ( );       

       arrayComponentPainter = animationInit.getArrayComponentPainter ( );

       if ( arrayComponentUpdater == null )
       {
         arrayComponentUpdater = new ArrayComponentUpdater ( );
       }

       if ( arrayComponentPainter == null )
       {
         arrayComponentPainter = new ArrayComponentPainter ( );
       }

       Container  contentPane = getContentPane ( );

       contentPane.setLayout ( new BorderLayout ( ) );

       contentPane.add ( animatedComponent, BorderLayout.CENTER );

       animatedComponent.init ( );

       validate ( );
     }

     public void  start   ( ) { animatedComponent.start   ( ); }

     public void  stop    ( ) { animatedComponent.stop    ( ); }

     public void  destroy ( ) { animatedComponent.destroy ( ); }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       arrayComponentUpdater.update ( component );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       arrayComponentPainter.paint ( component, graphics );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  addComponentAnimator (
       ComponentAnimator  componentAnimator )
     //////////////////////////////////////////////////////////////////////
     {
       addComponentUpdater ( componentAnimator );

       addComponentPainter ( componentAnimator );
     }

     public void  addComponentPainter (
       ComponentPainter  componentPainter )
     //////////////////////////////////////////////////////////////////////
     {
       arrayComponentPainter.add ( componentPainter );
     }

     public void  addComponentUpdater (
       ComponentUpdater  componentUpdater )
     //////////////////////////////////////////////////////////////////////
     {
       arrayComponentUpdater.add ( componentUpdater );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }