     package com.croftsoft.apps.chat.view;

     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import java.util.*;
     import javax.swing.*;

     import com.croftsoft.core.animation.AnimatedComponent;
     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.gui.LogPanel;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.lifecycle.Lifecycle;
     import com.croftsoft.core.util.consumer.NullConsumer;
     import com.croftsoft.core.util.queue.Queue;

     import com.croftsoft.apps.chat.ChatConstants;
     import com.croftsoft.apps.chat.controller.ChatController;
     import com.croftsoft.apps.chat.event.TalkEvent;
     import com.croftsoft.apps.chat.model.ChatGame;
     import com.croftsoft.apps.chat.model.ChatWorld;
     import com.croftsoft.apps.chat.model.seri.SeriChatGame;

     /*********************************************************************
     * Chat client user interface.
     *
     * @version
     *   2003-06-25
     * @since
     *   2000-04-20
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ChatPanel
       extends JPanel
       implements ComponentAnimator, Lifecycle
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final boolean  DEBUG = true;

     private static final Font     FONT
       = new Font ( "Arioso", Font.BOLD, 12 );

     private static final Color    BACKGROUND_COLOR = Color.BLACK;

     private static final Color    FOREGROUND_COLOR = Color.GREEN;

     private static final int      TEXT_LENGTH_MAX  = 10000;

     private static final int      DIVIDER_LOCATION = 300;

     //

     private final Queue              eventQueue;

     private final ChatController     chatController;

     private final ChatGame           chatGame;

     private final ChatSynchronizer   chatSynchronizer;

     private final JMenuBar           menuBar;

     private final ChatGameAnimator   chatGameAnimator;

     private final AnimatedComponent  animatedComponent;

     private final LogPanel           logPanel;

     private final JTextField         textField;

     private final JMenu              avatarMenu;

     private final JCheckBoxMenuItem  musicCheckBoxMenuItem;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public ChatPanel (
       Queue           eventQueue,
       ChatController  chatController )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new BorderLayout ( ) );

       NullArgumentException.check ( this.eventQueue = eventQueue );

       NullArgumentException.check (
         this.chatController = chatController );

       chatGame = new SeriChatGame ( );

       chatSynchronizer = new ChatSynchronizer (
         chatGame.getChatWorld ( ),
         NullConsumer.INSTANCE );

       avatarMenu = new JMenu ( "Avatar" );

       musicCheckBoxMenuItem = new JCheckBoxMenuItem ( "Music?", true );

       menuBar = createMenuBar ( );

       animatedComponent = new AnimatedComponent ( this );

       animatedComponent.setBackground ( BACKGROUND_COLOR );

       animatedComponent.setForeground ( FOREGROUND_COLOR );

       animatedComponent.setFont       ( FONT             );

       chatGameAnimator = new ChatGameAnimator (
         chatGame,
         animatedComponent,
         getClass ( ).getClassLoader ( ),
         ChatConstants.MEDIA_DIR );

       logPanel = new LogPanel (
         TEXT_LENGTH_MAX,
         BACKGROUND_COLOR,        
         FONT );

       textField = new JTextField ( );

       JPanel  southPanel = new JPanel ( new BorderLayout ( ) );

       southPanel.add (
         new JScrollPane ( logPanel ), BorderLayout.CENTER );

       southPanel.add ( textField, BorderLayout.SOUTH );

       JSplitPane  splitPane = new JSplitPane (
         JSplitPane.VERTICAL_SPLIT,
         animatedComponent,
         southPanel );

       splitPane.setOneTouchExpandable ( true );

       splitPane.setDividerLocation ( DIVIDER_LOCATION );

       add ( splitPane, BorderLayout.CENTER );

       // viewCanvas.load_graphics ( );
     }

     //////////////////////////////////////////////////////////////////////
     // interface lifecycle methods
     //////////////////////////////////////////////////////////////////////

     public void  init ( )
     //////////////////////////////////////////////////////////////////////
     {
       animatedComponent.init ( );

       animatedComponent.addComponentListener (
         new ComponentAdapter ( )
         {
           public void  componentResized (
             ComponentEvent  componentEvent )
           {
             animatedComponent.repaint ( );
           }
         } );

       chatController.setAvatarMenu ( avatarMenu );

       chatController.setMoveComponent ( animatedComponent );

       chatController.setMusicCheckBoxMenuItem ( musicCheckBoxMenuItem );

       chatController.setTalkTextField ( textField );
     }

     public void  start ( )
     //////////////////////////////////////////////////////////////////////
     {
       animatedComponent.start ( );
     }

     public void  stop ( )
     //////////////////////////////////////////////////////////////////////
     {
       animatedComponent.stop ( );
     }

     public void  destroy ( )
     //////////////////////////////////////////////////////////////////////
     {
       animatedComponent.destroy ( );
     }

     //////////////////////////////////////////////////////////////////////
     // accessor methods
     //////////////////////////////////////////////////////////////////////

     public JMenuBar  getMenuBar ( ) { return menuBar; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       chatGame.update ( );

       Object  o;

       while ( ( o = eventQueue.poll ( ) ) != null )
       {
         if ( o instanceof TalkEvent )
         {
           logPanel.record ( ( ( TalkEvent ) o ).getText ( ) );
         }
         else
         {
           chatSynchronizer.consume ( o );
         }
       }

       chatGameAnimator.update ( component );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       chatGameAnimator.paint ( component, graphics );
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private JMenuBar  createMenuBar ( )
     //////////////////////////////////////////////////////////////////////
     {
       JMenuBar  menuBar = new JMenuBar ( );

       //

       avatarMenu.setMnemonic ( KeyEvent.VK_A );

       menuBar.add ( avatarMenu );

       //

       ButtonGroup  buttonGroup = new ButtonGroup ( );

       for ( int  i = 0; i < ChatConstants.AVATAR_TYPES.length; i++ )
       {
         JRadioButtonMenuItem  radioButtonMenuItem
           = new JRadioButtonMenuItem (
           ChatConstants.AVATAR_TYPES [ i ],
           i == ChatConstants.DEFAULT_AVATAR_TYPE_INDEX );

         buttonGroup.add ( radioButtonMenuItem );

         avatarMenu.add ( radioButtonMenuItem );
       }

       //

       JMenu  optionsMenu = new JMenu ( "Options" );

       optionsMenu.setMnemonic ( KeyEvent.VK_O );

       menuBar.add ( optionsMenu );

       //

       optionsMenu.add ( musicCheckBoxMenuItem );

       return menuBar;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
