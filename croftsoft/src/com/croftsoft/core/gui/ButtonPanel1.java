     package com.croftsoft.core.gui;

     import java.awt.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * A Panel of Buttons evenly distributed in a single horizontal row.
     *
     * <p>
     * Uses GridBagLayout instead of GridLayout to ensure even distribution
     * during layout of the Buttons when the Panel is odd-sized.
     * </p>
     *
     * <p>
     * Example:
     * <code>
     * <pre>
     * buttonPanel = new ButtonPanel1 (
     *   new Button [ ] {
     *     playButton  = new Button ( "Play"  ),
     *     stopButton  = new Button ( "Stop"  ),
     *     pauseButton = new Button ( "Pause" ) } );
     * </pre>
     * </code>
     * </p>
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     *
     * @version
     *   2001-08-08
     * @since
     *   2001-04-16
     * @author
     *   <a href="http://www.alumni.caltech.edu/~croft/">David W. Croft</a>
     *********************************************************************/

     public final class  ButtonPanel1
       extends Panel
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * @param  panelBackgroundColor
     *
     *   If null, the default will be used.
     *
     * @throws NullArgumentException
     *
     *   If buttons is null or buttons[i] is null.
     *********************************************************************/
     public  ButtonPanel1 (
       Button [ ]  buttons,
       Color       panelBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new GridBagLayout ( ) );

       NullArgumentException.check ( buttons );

       GridBagConstraints  gridBagConstraints = new GridBagConstraints ( );

       gridBagConstraints.weightx = 1.0;

       gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

       for ( int  i = 0; i < buttons.length; i++ )
       {
         Button  button = buttons [ i ];

         NullArgumentException.check (
           button, "buttons[" + i + "] is null" );

         gridBagConstraints.gridx = i;

         add ( button, gridBagConstraints );
       }

       if ( panelBackgroundColor != null )
       {
         setBackground ( panelBackgroundColor );
       }
     }

     /*********************************************************************
     * this ( buttons, null );
     *********************************************************************/
     public  ButtonPanel1 ( Button [ ]  buttons )
     //////////////////////////////////////////////////////////////////////
     {
       this ( buttons, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
