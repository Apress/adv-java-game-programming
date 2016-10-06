     package com.croftsoft.core.gui;

     import java.awt.*;
     import java.awt.event.*;

     import com.croftsoft.core.lang.lifecycle.Destroyable;

     /*********************************************************************
     * Performs a graceful shutdown of a program when the window is closed.
     *
     * <p>
     * <ol>
     * <li> Sets window visibility to false.</li>
     * <li> Calls the destroy() method, in array order, of each of the
     *      Destroyable instances passed via the constructor argument.
     *      Any exceptions are caught, printed, and ignored.</li>
     * <li> Calls the window dispose() method.</li>
     * <li> Calls System.exit(0).</li>
     * </ol>
     * </p>
     *
     * <p>
     * Example:
     * <code>
     * <pre>
     * frame.addWindowListener (
     *   new ShutdownWindowListener ( destroyables ) );
     * </pre>
     * </code>
     * </p>
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     *
     * @version
     *   2001-07-20
     * @since
     *   2001-03-06
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  ShutdownWindowListener
       extends WindowAdapter
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private Destroyable [ ]  destroyables;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor.
     *
     * @param  destroyables
     *   May be null.
     *********************************************************************/
     public  ShutdownWindowListener ( Destroyable [ ]  destroyables )
     //////////////////////////////////////////////////////////////////////
     {
       this.destroyables = destroyables;
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
     public  ShutdownWindowListener ( Destroyable  destroyable )
     //////////////////////////////////////////////////////////////////////
     {
       this ( new Destroyable [ ] { destroyable } );
     }

     /*********************************************************************
     * Convenience constructor.
     *
     * <code>
     * <pre>
     * this ( ( Destroyable [ ] ) null );
     * </pre>
     * </code>
     *********************************************************************/
     public  ShutdownWindowListener ( )
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

       window.setVisible ( false );

       if ( destroyables != null )
       {
         for ( int  i = 0; i < destroyables.length; i++ )
         {
           try
           {
             destroyables [ i ].destroy ( );
           }
           catch ( Exception  ex )
           {
             ex.printStackTrace ( );
           }
         }
       }

       window.dispose ( );

       System.exit ( 0 );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
