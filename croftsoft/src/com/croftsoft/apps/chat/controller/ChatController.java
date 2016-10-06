     package com.croftsoft.apps.chat.controller;

     import java.awt.*;
     import java.awt.event.*;
     import javax.swing.*;

     import com.croftsoft.core.gui.event.UserInputAdapter;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.math.geom.Point2DD;
     import com.croftsoft.core.security.Authentication;
     import com.croftsoft.core.util.queue.Queue;

     import com.croftsoft.apps.chat.request.CreateModelRequest;
     import com.croftsoft.apps.chat.request.MoveRequest;
     import com.croftsoft.apps.chat.request.TalkRequest;

     /*********************************************************************
     * Chat controller.
     *
     * @version
     *   2003-06-17
     * @since
     *   2003-06-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ChatController
       extends UserInputAdapter
       implements ActionListener, ItemListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Authentication  authentication;

     private final Queue           requestQueue;

     //

     private JMenu              avatarMenu;

     private JComponent         moveComponent;

     private JCheckBoxMenuItem  musicCheckBoxMenuItem;

     private JTextField         talkTextField;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public ChatController (
       Authentication  authentication,
       Queue           requestQueue )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.authentication = authentication );

       NullArgumentException.check ( this.requestQueue = requestQueue );
     }

     //////////////////////////////////////////////////////////////////////
     // mutator methods
     //////////////////////////////////////////////////////////////////////

     public void  setAvatarMenu ( JMenu  avatarMenu )
     //////////////////////////////////////////////////////////////////////
     {
       if ( this.avatarMenu != null )
       {
         throw new IllegalStateException ( );
       }

       NullArgumentException.check ( this.avatarMenu = avatarMenu );

       Component [ ]  menuComponents = avatarMenu.getMenuComponents ( );

       for ( int  i = 0; i < menuComponents.length; i++ )
       {
         Component  menuComponent = menuComponents [ i ];

         if ( menuComponent instanceof JRadioButtonMenuItem )
         {
           ( ( JRadioButtonMenuItem ) menuComponent ).addItemListener (
             this );
         }
       }
     }

     public void  setMusicCheckBoxMenuItem (
       JCheckBoxMenuItem  musicCheckBoxMenuItem )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( musicCheckBoxMenuItem );

       if ( this.musicCheckBoxMenuItem != null )
       {
         this.musicCheckBoxMenuItem.removeItemListener ( this );
       }

       this.musicCheckBoxMenuItem = musicCheckBoxMenuItem;

       musicCheckBoxMenuItem.addItemListener ( this );
     }

     public void  setMoveComponent ( JComponent  moveComponent )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( moveComponent );

       if ( this.moveComponent != null )
       {
         this.moveComponent.removeMouseListener ( this );
       }

       this.moveComponent = moveComponent;

       moveComponent.addMouseListener ( this );
     }

     public void  setTalkTextField ( JTextField  talkTextField )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( talkTextField );

       if ( this.talkTextField != null )
       {
         this.talkTextField.removeActionListener ( this );
       }

       this.talkTextField = talkTextField;

       talkTextField.addActionListener ( this );
     }

     //////////////////////////////////////////////////////////////////////
     // interface ActionListener method
     //////////////////////////////////////////////////////////////////////

     public void  actionPerformed ( ActionEvent  actionEvent )
     //////////////////////////////////////////////////////////////////////
     {
       if ( actionEvent.getSource ( ) == talkTextField )
       {
         String  text = talkTextField.getText ( );

         talkTextField.setText ( "" );

         requestQueue.replace ( new TalkRequest ( authentication, text ) );
       }
       else
       {
         throw new IllegalArgumentException ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // interface ItemListener method
     //////////////////////////////////////////////////////////////////////

     public void  itemStateChanged ( ItemEvent  itemEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Object  item = itemEvent.getItem ( );

       if ( item == musicCheckBoxMenuItem )
       {
         System.out.println (
           "Music:  " + musicCheckBoxMenuItem.isSelected ( ) );
       }
       else if ( item instanceof JRadioButtonMenuItem )
       {
         JRadioButtonMenuItem  radioButtonMenuItem
           = ( JRadioButtonMenuItem ) item;

         if ( itemEvent.getStateChange ( ) == ItemEvent.SELECTED )
         {
           String  avatarType
             = radioButtonMenuItem.getText ( );

           requestQueue.replace ( new CreateModelRequest (
             authentication, avatarType, 0.0, 0.0 ) );
         }
       }
       else
       {
         throw new IllegalArgumentException ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // interface MouseListener method
     //////////////////////////////////////////////////////////////////////

     public void  mousePressed ( MouseEvent  mouseEvent )
     //////////////////////////////////////////////////////////////////////
     {
       if ( mouseEvent.getSource ( ) == moveComponent )
       {
         Point  mousePoint = mouseEvent.getPoint ( );

         requestQueue.replace (
           new MoveRequest (
             authentication,
             new Point2DD ( mousePoint.x, mousePoint.y ) ) );
       }
       else
       {
         throw new IllegalArgumentException ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
