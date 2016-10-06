     package com.croftsoft.core.gui;

     import java.awt.*;
     import java.awt.event.*;
     import javax.swing.JOptionPane;

     import com.croftsoft.core.lang.lifecycle.Destroyable;

     /*********************************************************************
     * Performs a graceful shutdown of a program when the window is closed.
     *
     * <p>
     * <ol>
     * <li> Prompts for shutdown confirmation.
     * <li> Calls the window hide() method.
     * <li> Calls the destroy() method, in array order, of each of the
     *      Destroyable instances passed via the constructor argument.
     *      Any exceptions are caught, printed, and ignored.
     * <li> Calls the window dispose() method.
     * <li> Calls System.exit(0).
     * </ol>
     * </p>
     *
     * <p>
     * Example:
     * <code>
     * <pre>
     * jFrame.setDefaultCloseOperation (
     *   javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE );
     *
     * jFrame.addWindowListener (
     *   new ShutdownWindowAdapter ( this, "Exit Program?" ) );
     * </pre>
     * </code>
     * </p>
     *
     * @version
     *   2003-07-22
     * @version
     *   2001-03-06
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  ShutdownWindowAdapter
       extends WindowAdapter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final Destroyable [ ]  destroyables;

     private final String           shutdownConfirmationPrompt;

     private final String           shutdownConfirmationTitle;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *
     * @param  destroyables
     *   May be null.
     * @param  shutdownConfirmationPrompt
     *   If null, no shutdown confirmation prompt dialog will be given.
     * @param  shutdownConfirmationTitle
     *   If null, the shutdownConfirmationPrompt value will be used.
     *********************************************************************/
     public  ShutdownWindowAdapter (
       Destroyable [ ]  destroyables,
       String           shutdownConfirmationPrompt,
       String           shutdownConfirmationTitle )
     //////////////////////////////////////////////////////////////////////
     {
       this.destroyables               = destroyables;

       this.shutdownConfirmationPrompt = shutdownConfirmationPrompt;

       this.shutdownConfirmationTitle  = shutdownConfirmationTitle;
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * <code>
     * <pre>
     * this ( destroyables, shutdownConfirmationPrompt, null );
     * </pre>
     * </code>
     *********************************************************************/
     public  ShutdownWindowAdapter (
       Destroyable [ ]  destroyables,
       String           shutdownConfirmationPrompt )
     //////////////////////////////////////////////////////////////////////
     {
       this ( destroyables, shutdownConfirmationPrompt, null );
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * <code>
     * <pre>
     * this (
     *   new Destroyable [ ] { destroyable },
     *   shutdownConfirmationPrompt );
     * </pre>
     * </code>
     *********************************************************************/
     public  ShutdownWindowAdapter (
       Destroyable  destroyable,
       String       shutdownConfirmationPrompt )
     //////////////////////////////////////////////////////////////////////
     {
       this (
         new Destroyable [ ] { destroyable },
         shutdownConfirmationPrompt );
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * <code>
     * <pre>
     * this ( destroyables, null );
     * </pre>
     * </code>
     *********************************************************************/
     public  ShutdownWindowAdapter ( Destroyable [ ]  destroyables )
     //////////////////////////////////////////////////////////////////////
     {
       this ( destroyables, null );
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * <code>
     * <pre>
     * this ( new Destroyable [ ] { destroyable } );
     * </pre>
     * </code>
     *********************************************************************/
     public  ShutdownWindowAdapter ( Destroyable  destroyable )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new Destroyable [ ] { destroyable } );
     }

     public  ShutdownWindowAdapter ( )
     //////////////////////////////////////////////////////////////////////
     {
       this ( ( Destroyable [ ] ) null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  windowClosing ( WindowEvent  windowEvent )
     //////////////////////////////////////////////////////////////////////
     {
       Window  window = windowEvent.getWindow ( );

       if ( shutdownConfirmationPrompt != null )
       {
         int  confirm = JOptionPane.showOptionDialog ( window,
           shutdownConfirmationPrompt, 
           shutdownConfirmationTitle != null
             ? shutdownConfirmationTitle : shutdownConfirmationPrompt,
           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
           null, null, null );

         if ( confirm != JOptionPane.YES_OPTION )
         {
           return;
         }
       }

       window.hide ( );

       if ( destroyables != null )
       {
         for ( int  i = 0; i < destroyables.length; i++ )
         {
           Destroyable  destroyable = destroyables [ i ];

           if ( destroyable != null )
           {
             try
             {
               destroyable.destroy ( );
             }
             catch ( Exception  ex )
             {
               ex.printStackTrace ( );
             }
           }
         }
       }

       window.dispose ( );

       System.exit ( 0 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
