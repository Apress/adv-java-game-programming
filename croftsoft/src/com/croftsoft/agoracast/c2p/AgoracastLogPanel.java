     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import java.io.*;
     import javax.swing.*;

     import com.croftsoft.core.gui.LogPanel;
     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     *
     * <p />
     *
     * @version
     *   2001-08-13
     * @since
     *   2001-08-13
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastLogPanel
       extends LogPanel
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final AgoracastMediator  agoracastMediator;

     private boolean  enabled = false;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastLogPanel (
       AgoracastMediator  agoracastMediator,
       int                textLengthMax,
       Color              panelBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       super ( textLengthMax, panelBackgroundColor,
         AgoracastConstants.LOG_FONT );

       NullArgumentException.check (
         this.agoracastMediator = agoracastMediator );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  record ( String  message )
     //////////////////////////////////////////////////////////////////////
     {
       super.record ( message );

       if ( !enabled )
       {
         agoracastMediator.setTabEnabled (
           AgoracastConstants.TAB_INDEX_LOG, true );

         enabled = true;
       }
     }

     public synchronized void  record ( Throwable  throwable )
     //////////////////////////////////////////////////////////////////////
     {
       super.record ( throwable );
     }

     public synchronized void  record (
       String  message, Throwable  throwable )
     //////////////////////////////////////////////////////////////////////
     {
       super.record ( message, throwable );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }