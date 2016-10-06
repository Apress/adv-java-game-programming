     package com.croftsoft.apps.sprite;
     
     import java.awt.*;
     import java.awt.event.*;
     import java.awt.image.*;
     import java.security.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.gui.BufferCapabilitiesLib;
     import com.croftsoft.core.gui.LifecycleWindowListener;
     import com.croftsoft.core.lang.ClassLib;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.animation.AnimatedComponent;

     /*********************************************************************
     * Main SpriteDemo class.
     *
     * @version
     *   2003-07-26
     * @since
     *   2001-03-04
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

// rename this to AnimationDemo

     public final class  SpriteDemo
       extends JApplet
       implements ChangeListener, ItemListener, Lifecycle, SpriteConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final BufferStrategy  bufferStrategy;

     //
       
     private AnimatedComponent   animatedComponent;

     private SpriteAnimator      spriteAnimator;

     private SpinnerNumberModel  spriteVelocitySpinnerNumberModel;

     private SpinnerNumberModel  frameRateSpinnerNumberModel;

     private JCheckBoxMenuItem   paintBackgroundCheckBox;

     private JCheckBoxMenuItem   paintBricksCheckBox;

     private JCheckBoxMenuItem   paintSpritesCheckBox;

     private JCheckBoxMenuItem   paintCloudsCheckBox;

     private JCheckBoxMenuItem   paintFogNightCheckBox;

     private JCheckBoxMenuItem   onlySpriteUpdatesCheckBox;

     private JCheckBoxMenuItem   onlySpriteRegionsCheckBox;

     private JCheckBoxMenuItem   useSystemClockCheckBox;

     private JCheckBoxMenuItem   useFixedDelayCheckBox;

     private JCheckBoxMenuItem   useSwingRepainterCheckBox;

     private JCheckBoxMenuItem   useDoubleBufferingCheckBox;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       JFrame  jFrame = new JFrame ( FRAME_TITLE );

       try
       {
         jFrame.setIconImage ( ClassLib.getResourceAsImage (
           SpriteDemo.class, FRAME_ICON_FILENAME ) );
       }
       catch ( Exception  ex )
       {
         ex.printStackTrace ( );
       }

       BufferStrategy  bufferStrategy = null;

       GraphicsConfiguration  graphicsConfiguration
         = jFrame.getGraphicsConfiguration ( );

       GraphicsDevice  graphicsDevice
         = graphicsConfiguration.getDevice ( );

       if ( graphicsDevice.isFullScreenSupported ( ) )
       {
         try
         {
           // jFrame.setUndecorated ( );

           // jFrame.setIgnoreRepaint ( true );

           graphicsDevice.setFullScreenWindow ( jFrame );

           System.out.println ( "full screen" );

           jFrame.createBufferStrategy ( 2 );

           bufferStrategy = jFrame.getBufferStrategy ( );

           BufferCapabilitiesLib.print (
             bufferStrategy.getCapabilities ( ) );
         }
         catch ( AccessControlException  ex )
         {
         }
       }

System.out.println ( bufferStrategy );

       SpriteDemo  spriteDemo = new SpriteDemo ( bufferStrategy );

       jFrame.setContentPane ( spriteDemo );

       if ( bufferStrategy == null )
       {
         LifecycleWindowListener.launchFrameAsDesktopApp (
           jFrame,
           new Lifecycle [ ] { spriteDemo },
           FRAME_SIZE,
           SHUTDOWN_CONFIRMATION_PROMPT );
       }
       else
       {
         jFrame.setDefaultCloseOperation (
           WindowConstants.DO_NOTHING_ON_CLOSE );

         jFrame.addWindowListener (
           new LifecycleWindowListener (
             new Lifecycle [ ] { spriteDemo },
             SHUTDOWN_CONFIRMATION_PROMPT ) );

//       jFrame.show ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  SpriteDemo ( BufferStrategy  bufferStrategy )
     //////////////////////////////////////////////////////////////////////
     {
       this.bufferStrategy = bufferStrategy;
     }

     public  SpriteDemo ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String  getAppletInfo ( )
     //////////////////////////////////////////////////////////////////////
     {
       return INFO;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( INFO );

       spriteAnimator = new SpriteAnimator ( bufferStrategy );

       animatedComponent = spriteAnimator.getAnimatedComponent ( );

       animatedComponent.init ( );

       //

       spriteVelocitySpinnerNumberModel = new SpinnerNumberModel (
         DEFAULT_SPRITE_VELOCITY,
         0.0,
         999.9,
         1.0 );

       spriteVelocitySpinnerNumberModel.addChangeListener ( this );

       //

       frameRateSpinnerNumberModel = new SpinnerNumberModel (
         DEFAULT_FRAME_RATE,
         0.1,
         999.9,
         1.0 );

       frameRateSpinnerNumberModel.addChangeListener ( this );

       //

       JPanel  southPanel = new JPanel ( new GridBagLayout ( ), true );

       GridBagConstraints  gridBagConstraints = new GridBagConstraints ( );

       //

       gridBagConstraints.gridx   = 0;

       gridBagConstraints.gridy   = 0;

       gridBagConstraints.weightx = 1.0;

       gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

       gridBagConstraints.insets = new Insets ( 1, 2, 2, 1 );

       southPanel.add (
         new JLabel ( "Sprite Velocity (pixels/sec)", SwingConstants.RIGHT ),
         gridBagConstraints );

       gridBagConstraints.gridx   = 1;

       gridBagConstraints.gridy   = 0;

       gridBagConstraints.weightx = 0.0;

       southPanel.add (
         new JSpinner ( spriteVelocitySpinnerNumberModel ),
         gridBagConstraints );

       //

       gridBagConstraints.gridx   = 0;

       gridBagConstraints.gridy   = 1;

       gridBagConstraints.weightx = 1.0;

       southPanel.add (
         new JLabel ( "Frame Rate (frames/sec)", SwingConstants.RIGHT ),
         gridBagConstraints );

       gridBagConstraints.gridx   = 1;

       gridBagConstraints.gridy   = 1;

       gridBagConstraints.weightx = 0.0;

       southPanel.add (
         new JSpinner ( frameRateSpinnerNumberModel ),
         gridBagConstraints );

       //

       JMenuBar  menuBar = new JMenuBar ( );

       setJMenuBar ( menuBar );

       //

       JMenu  optionsMenu = new JMenu ( "Options" );

       optionsMenu.setMnemonic ( KeyEvent.VK_O );

       menuBar.add ( optionsMenu );

       //

       paintBackgroundCheckBox
         = new JCheckBoxMenuItem ( "Paint background?", true );

       optionsMenu.add ( paintBackgroundCheckBox );

       paintBackgroundCheckBox.addItemListener ( this );

       //

       paintBricksCheckBox
         = new JCheckBoxMenuItem ( "Paint bricks?", true );

       optionsMenu.add ( paintBricksCheckBox );

       paintBricksCheckBox.addItemListener ( this );

       //

       paintSpritesCheckBox
         = new JCheckBoxMenuItem ( "Paint sprites?", true );

       optionsMenu.add ( paintSpritesCheckBox );

       paintSpritesCheckBox.addItemListener ( this );

       //

       paintCloudsCheckBox
         = new JCheckBoxMenuItem ( "Paint clouds?", true );

       optionsMenu.add ( paintCloudsCheckBox );

       paintCloudsCheckBox.addItemListener ( this );

       //

       paintFogNightCheckBox
         = new JCheckBoxMenuItem ( "Paint fog and night?", false );

       optionsMenu.add ( paintFogNightCheckBox );

       paintFogNightCheckBox.addItemListener ( this );

       //

       onlySpriteUpdatesCheckBox
         = new JCheckBoxMenuItem ( "Only sprite updates?", false );

       optionsMenu.add ( onlySpriteUpdatesCheckBox );

       onlySpriteUpdatesCheckBox.addItemListener ( this );

       //

       onlySpriteRegionsCheckBox
         = new JCheckBoxMenuItem ( "Only sprite regions?", false );

       optionsMenu.add ( onlySpriteRegionsCheckBox );

       onlySpriteRegionsCheckBox.addItemListener ( this );

       //

       useSystemClockCheckBox
         = new JCheckBoxMenuItem ( "Use system clock?", false );

       optionsMenu.add ( useSystemClockCheckBox );

       useSystemClockCheckBox.addItemListener ( this );

       //

       useFixedDelayCheckBox
         = new JCheckBoxMenuItem ( "Use fixed delay?", false );

       optionsMenu.add ( useFixedDelayCheckBox );

       useFixedDelayCheckBox.addItemListener ( this );

       //

       useSwingRepainterCheckBox
         = new JCheckBoxMenuItem ( "Use Swing RepaintManager?", false );

       optionsMenu.add ( useSwingRepainterCheckBox );

       useSwingRepainterCheckBox.addItemListener ( this );

       //

       useDoubleBufferingCheckBox
         = new JCheckBoxMenuItem ( "Use double-buffering?", true );

       optionsMenu.add ( useDoubleBufferingCheckBox );

       useDoubleBufferingCheckBox.addItemListener ( this );

       //

       Container  contentPane = getContentPane ( );

       contentPane.setLayout ( new BorderLayout ( ) );

       contentPane.add ( animatedComponent, BorderLayout.CENTER );

       contentPane.add ( southPanel, BorderLayout.SOUTH );

       validate ( );
     }

     public void  start   ( ) { animatedComponent.start   ( ); }

     public void  stop    ( ) { animatedComponent.stop    ( ); }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       animatedComponent.destroy ( );

       RepaintManager.currentManager ( animatedComponent )
         .setDoubleBufferingEnabled ( true );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  itemStateChanged ( ItemEvent  itemEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  item = itemEvent.getItem ( );

       if ( item == paintBackgroundCheckBox )
       {
         spriteAnimator.setPaintBackground (
           paintBackgroundCheckBox.isSelected ( ) );
       }
       else if ( item == paintBricksCheckBox )
       {
         spriteAnimator.setPaintBricks (
           paintBricksCheckBox.isSelected ( ) );
       }
       else if ( item == paintSpritesCheckBox )
       {
         spriteAnimator.setPaintSprites (
           paintSpritesCheckBox.isSelected ( ) );
       }
       else if ( item == paintCloudsCheckBox )
       {
         spriteAnimator.setPaintClouds (
           paintCloudsCheckBox.isSelected ( ) );
       }
       else if ( item == paintFogNightCheckBox )
       {
         spriteAnimator.setPaintFogNight (
           paintFogNightCheckBox.isSelected ( ) );
       }
       else if ( item == onlySpriteUpdatesCheckBox )
       {
         spriteAnimator.setOnlySpriteUpdates (
           onlySpriteUpdatesCheckBox.isSelected ( ) );
       }       
       else if ( item == onlySpriteRegionsCheckBox )
       {
         spriteAnimator.setOnlySpriteRegions (
           onlySpriteRegionsCheckBox.isSelected ( ) );
       }       
       else if ( item == useSystemClockCheckBox )
       {
         spriteAnimator.setUseSystemClock (
           useSystemClockCheckBox.isSelected ( ) );
       }       
       else if ( item == useFixedDelayCheckBox )
       {
         setLoopGovernor ( );
       }
       else if ( item == useSwingRepainterCheckBox )
       {
         spriteAnimator.setUseSwingRepaintCollector (
           useSwingRepainterCheckBox.isSelected ( ) );
       }
       else if ( item == useDoubleBufferingCheckBox )
       {
         spriteAnimator.setUseDoubleBuffering (
           useDoubleBufferingCheckBox.isSelected ( ) );
       }
     }

     public void  stateChanged ( ChangeEvent  changeEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  source = changeEvent.getSource ( );

       if ( source == spriteVelocitySpinnerNumberModel )
       {
         Double  value
           = ( Double ) spriteVelocitySpinnerNumberModel.getValue ( );

         spriteAnimator.setSpriteVelocity ( value.doubleValue ( ) );         
       }
       else if ( source == frameRateSpinnerNumberModel )
       {
         setLoopGovernor ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private void  setLoopGovernor ( )
     //////////////////////////////////////////////////////////////////////
     {
// what if the frequency is zero?

       spriteAnimator.setLoopGovernor (
         useFixedDelayCheckBox.isSelected ( ),
         ( ( Double ) frameRateSpinnerNumberModel.getValue ( ) )
           .doubleValue ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
