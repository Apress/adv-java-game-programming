     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import javax.swing.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.util.pubsub.Subscriber;

     /*********************************************************************
     *
     * <p />
     *
     * @version
     *   2002-01-29
     * @since
     *   2001-07-31
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastBrowsePanel
       extends JPanel
       implements Subscriber
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final AgoracastMediator       agoracastMediator;

     private final CardLayout              cardLayout;

     private final AgoracastTablePanel     agoracastTablePanel;

     private final AgoracastDownloadPanel  agoracastDownloadPanel;

     private final AgoracastFieldsPanel    agoracastFieldsPanel;

     private final AgoracastSourcePanel    agoracastSourcePanel;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastBrowsePanel ( AgoracastMediator  agoracastMediator )
     //////////////////////////////////////////////////////////////////////
     {
       super ( true ); // isDoubleBuffered

       NullArgumentException.check (
         this.agoracastMediator = agoracastMediator );

       setLayout ( cardLayout = new CardLayout ( ) );

       AgoracastLib.setColor ( this, agoracastMediator );

       agoracastTablePanel
         = new AgoracastTablePanel ( agoracastMediator, this );

       add ( agoracastTablePanel, "0" );

       agoracastDownloadPanel
         = new AgoracastDownloadPanel ( agoracastMediator, this );

       add ( agoracastDownloadPanel, "1" );

       agoracastFieldsPanel
         = new AgoracastFieldsPanel ( agoracastMediator, this );

       add ( agoracastFieldsPanel, "2" );

       agoracastSourcePanel
         = new AgoracastSourcePanel ( agoracastMediator, this );

       add ( agoracastSourcePanel, "3" );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  download ( )
     //////////////////////////////////////////////////////////////////////
     {
       cardLayout.show ( this, "1" );

       agoracastDownloadPanel.download ( );
     }

     public synchronized void  showFieldsPanel ( String [ ]  columnNames )
     //////////////////////////////////////////////////////////////////////
     {
       cardLayout.show ( this, "2" );

       agoracastFieldsPanel.setSelectedFieldNames ( columnNames );
     }

     public synchronized void  showTable ( )
     //////////////////////////////////////////////////////////////////////
     {
       cardLayout.show ( this, "0" );

       String [ ]  columnNames
         = agoracastFieldsPanel.getSelectedFieldNames ( );

       agoracastTablePanel.updateTable ( columnNames );
     }

     public synchronized void  viewSource ( AgoracastData  agoracastData )
     //////////////////////////////////////////////////////////////////////
     {
       cardLayout.show ( this, "3" );

       agoracastSourcePanel.viewSource ( agoracastData );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  receive ( Object  message )
     //////////////////////////////////////////////////////////////////////
     {
       if ( message == AgoracastFieldsPanel.DONE_BUTTON_EVENT )
       {
         showTable ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }