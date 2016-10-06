     package com.croftsoft.agoracast.c2p;

     import java.awt.*;
     import java.awt.event.*;
     import java.util.*;
     import javax.swing.*;
     import javax.swing.event.*;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Pair;

     /*********************************************************************
     * @version
     *   2002-01-29
     * @since
     *   2001-09-12
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  AgoracastInputPanel
       extends JPanel
       implements ChangeListener
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final ChangeEvent         changeEvent;

     private final ChangeListener      changeListener;

     private final AgoracastMediator   agoracastMediator;

     private final Map  nameToAgoracastFieldViewMap = new HashMap ( );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  AgoracastInputPanel (
       AgoracastMediator  agoracastMediator,
       String [ ]         fieldNames,
       ChangeListener     changeListener )
     //////////////////////////////////////////////////////////////////////
     {
       super ( new GridBagLayout ( ), true );

       NullArgumentException.check (
         this.agoracastMediator = agoracastMediator );

       NullArgumentException.check ( fieldNames );

       NullArgumentException.check (
         this.changeListener = changeListener );

       changeEvent = new ChangeEvent ( this );

       GridBagConstraints  gridBagConstraints = new GridBagConstraints ( );

       gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

       gridBagConstraints.ipadx = 0;

       gridBagConstraints.ipady = 0;

       gridBagConstraints.insets = new Insets ( 8, 8, 8, 8 );

       for ( int  i = 0; i < fieldNames.length; i++ )
       {
         AgoracastField  agoracastField
           = agoracastMediator.getAgoracastField ( fieldNames [ i ] );

         AgoracastFieldView  agoracastFieldView
           = new AgoracastFieldView (
           agoracastMediator, agoracastField, true,
           ( ChangeListener ) this );

         gridBagConstraints.gridx = 0;

         gridBagConstraints.gridy = i;

         gridBagConstraints.weightx = 0.0;

         add ( agoracastFieldView.getCheckBox ( ), gridBagConstraints );

         gridBagConstraints.gridx = 1;

         add ( agoracastFieldView.getLabel ( ), gridBagConstraints );

         gridBagConstraints.gridx = 2;

         gridBagConstraints.weightx = 1.0;

         add ( agoracastFieldView.getComponent ( ), gridBagConstraints );

         gridBagConstraints.gridx = 3;

         gridBagConstraints.weightx = 0.0;

         add ( agoracastFieldView.getDescriptorLabel ( ),
           gridBagConstraints );

         nameToAgoracastFieldViewMap.put (
           agoracastField.getName ( ), agoracastFieldView );
       }

       AgoracastLib.setColor ( this, agoracastMediator );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  setSelected (
       String   name,
       boolean  selected )
     //////////////////////////////////////////////////////////////////////
     {
       AgoracastFieldView  agoracastFieldView = ( AgoracastFieldView )
         nameToAgoracastFieldViewMap.get ( name );

       agoracastFieldView.setSelected ( selected );
     }

     public void  setSelectedAll ( boolean  selected )
     //////////////////////////////////////////////////////////////////////
     {
       Iterator  iterator
         = nameToAgoracastFieldViewMap.values ( ).iterator ( );

       while ( iterator.hasNext ( ) )
       {
         AgoracastFieldView  agoracastFieldView
           = ( AgoracastFieldView ) iterator.next ( );

         agoracastFieldView.setSelected ( selected );
       }
     }

     public boolean  isSelected ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       AgoracastFieldView  agoracastFieldView = ( AgoracastFieldView )
         nameToAgoracastFieldViewMap.get ( name );

       return agoracastFieldView.isSelected ( );
     }

     public boolean  hasSelectedField ( )
     //////////////////////////////////////////////////////////////////////
     {
       Iterator  iterator
         = nameToAgoracastFieldViewMap.values ( ).iterator ( );

       while ( iterator.hasNext ( ) )
       {
         AgoracastFieldView  agoracastFieldView
           = ( AgoracastFieldView ) iterator.next ( );

         if ( agoracastFieldView.isSelected ( ) )
         {
           return true;
         }
       }
       
       return false;
     }

     public String [ ]  getSelectedNames ( )
     //////////////////////////////////////////////////////////////////////
     {
       java.util.List  nameList = new ArrayList ( );

       Iterator  iterator
         = nameToAgoracastFieldViewMap.values ( ).iterator ( );

       while ( iterator.hasNext ( ) )
       {
         AgoracastFieldView  agoracastFieldView
           = ( AgoracastFieldView ) iterator.next ( );

         if ( agoracastFieldView.isSelected ( ) )
         {
           nameList.add ( agoracastFieldView.getName ( ) );
         }         
       }

       return ( String [ ] ) nameList.toArray ( new String [ ] { } );
     }

     public String  getValue ( String  name )
     //////////////////////////////////////////////////////////////////////
     {
       AgoracastFieldView  agoracastFieldView
         = ( AgoracastFieldView )
        nameToAgoracastFieldViewMap.get ( name );

       if ( agoracastFieldView == null )
       {
         return null;
       }

       return agoracastFieldView.getValue ( );
     }

     public void  setValue (
       String  name,
       String  value )
     //////////////////////////////////////////////////////////////////////
     {
       AgoracastFieldView  agoracastFieldView
         = ( AgoracastFieldView )
        nameToAgoracastFieldViewMap.get ( name );

       agoracastFieldView.setValue ( value );
     }

     public synchronized void  useDefaults ( )
     //////////////////////////////////////////////////////////////////////
     {
       Iterator  iterator
         = nameToAgoracastFieldViewMap.keySet ( ).iterator ( );

       while ( iterator.hasNext ( ) )
       {
         String  name = ( String ) iterator.next ( );

         AgoracastField  agoracastField
           = agoracastMediator.getAgoracastField ( name );

         AgoracastFieldView  agoracastFieldView = ( AgoracastFieldView )
           nameToAgoracastFieldViewMap.get ( name );

         agoracastFieldView.setValue ( agoracastField.getValue ( ) );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  stateChanged ( ChangeEvent  changeEvent )
     //////////////////////////////////////////////////////////////////////
     {
       changeListener.stateChanged ( this.changeEvent );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
