     package com.croftsoft.core.gui;

     import java.awt.*;
     import javax.swing.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * A JPanel of JButtons evenly distributed in a single horizontal row.
     *
     * <p>
     * Uses GridBagLayout instead of GridLayout to ensure even distribution
     * during layout of the Buttons when the JPanel is odd-sized.
     * </p>
     *
     * <p>
     * Example:
     * <code>
     * <pre>
     * buttonPanel2 = new ButtonPanel2 (
     *   new JButton [ ] {
     *     playJButton  = new JButton ( "Play"  ),
     *     stopJButton  = new JButton ( "Stop"  ),
     *     pauseJButton = new JButton ( "Pause" ) } );
     * </pre>
     * </code>
     * </p>
     *
     * @version
     *   2001-07-10
     * @since
     *   2001-07-10
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  ButtonPanel2
       extends JPanel
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
     *   If jButtons is null or jButtons[i] is null.
     *********************************************************************/
     public  ButtonPanel2 (
       JButton [ ]  jButtons,
       Color        panelBackgroundColor )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new GridBagLayout ( ) );

       NullArgumentException.check ( jButtons );

       GridBagConstraints  gridBagConstraints = new GridBagConstraints ( );

       gridBagConstraints.weightx = 1.0;

       gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

       for ( int  i = 0; i < jButtons.length; i++ )
       {
         JButton  jButton = jButtons [ i ];

         NullArgumentException.check (
           jButton, "jButtons[" + i + "] is null" );

         gridBagConstraints.gridx = i;

         add ( jButton, gridBagConstraints );
       }

       if ( panelBackgroundColor != null )
       {
         setBackground ( panelBackgroundColor );
       }
     }

     /*********************************************************************
     * this ( jButtons, null );
     *********************************************************************/
     public  ButtonPanel2 ( JButton [ ]  jButtons )
     //////////////////////////////////////////////////////////////////////
     {
       this ( jButtons, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
