     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import javax.swing.*;

     import com.croftsoft.core.gui.ButtonPanel2;
     import com.croftsoft.core.gui.LabeledFieldsPanel2;
     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Pair;
     import com.croftsoft.core.net.news.UsenetMessage;
     import com.croftsoft.core.text.sml.*;
     import com.croftsoft.core.util.log.Log;
     import com.croftsoft.core.util.pubsub.Subscriber;

     /*********************************************************************
     *
     * <p />
     *
     * @version
     *   2002-02-02
     * @since
     *   2001-07-25
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastPostPanel
       extends JPanel
       implements Subscriber
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final AgoracastMediator       agoracastMediator;

     private final CardLayout              cardLayout;

     private final AgoracastPrepPanel      agoracastPrepPanel;

     private final AgoracastTextPanel      agoracastTextPanel;

     private final AgoracastConfirmPanel   agoracastConfirmPanel;

     private final AgoracastSendPanel      agoracastSendPanel;

     private final AgoracastFieldsPanel    agoracastFieldsPanel;

     //

     private UsenetMessage  usenetMessage;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastPostPanel ( AgoracastMediator  agoracastMediator )
     //////////////////////////////////////////////////////////////////////
     {
       super ( true ); // isDoubleBuffered

       NullArgumentException.check (
         this.agoracastMediator = agoracastMediator );

       AgoracastLib.setColor ( this, agoracastMediator );

       agoracastPrepPanel
         = new AgoracastPrepPanel     ( agoracastMediator, this );

       agoracastTextPanel
         = new AgoracastTextPanel     ( agoracastMediator, this );

       agoracastConfirmPanel
         = new AgoracastConfirmPanel  ( agoracastMediator, this );

       agoracastSendPanel
         = new AgoracastSendPanel     ( agoracastMediator, this );

       agoracastFieldsPanel
         = new AgoracastFieldsPanel   ( agoracastMediator, this );

       cardLayout = new CardLayout ( );

       setLayout ( cardLayout );

       add ( agoracastPrepPanel    , "1" );

       add ( agoracastTextPanel    , "2" );

       add ( agoracastConfirmPanel , "3" );

       add ( agoracastSendPanel    , "4" );

       add ( agoracastFieldsPanel  , "5" );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  cancel ( )
     //////////////////////////////////////////////////////////////////////
     {
       cardLayout.show ( this, "1" );
     }

     public void  describe ( UsenetMessage  usenetMessage )
     //////////////////////////////////////////////////////////////////////
     {
       agoracastTextPanel.setUsenetMessage ( usenetMessage );

       cardLayout.show ( this, "2" );
     }

     public void  confirm ( UsenetMessage  usenetMessage )
     //////////////////////////////////////////////////////////////////////
     {
       this.usenetMessage = usenetMessage;

       String  text = usenetMessage.toString ( );

       // strip off the end-of-message-body period for display purposes

       text = text.substring ( 0, text.length ( ) - 5 );

       agoracastConfirmPanel.setText ( text );

       cardLayout.show ( this, "3" );
     }

     public void  post ( )
     //////////////////////////////////////////////////////////////////////
     {
       cardLayout.show ( this, "4" );

       agoracastSendPanel.send ( usenetMessage );
     }

     public void  addFields ( String [ ]  selectedFieldNames )
     //////////////////////////////////////////////////////////////////////
     {
       cardLayout.show ( this, "5" );

       agoracastFieldsPanel.setSelectedFieldNames ( selectedFieldNames );       
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  receive ( Object  message )
     //////////////////////////////////////////////////////////////////////
     {
       if ( message == AgoracastFieldsPanel.DONE_BUTTON_EVENT )
       {
         agoracastPrepPanel.setSelectedFieldNames (
           agoracastFieldsPanel.getSelectedFieldNames ( ) );

         cardLayout.show ( this, "1" );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }