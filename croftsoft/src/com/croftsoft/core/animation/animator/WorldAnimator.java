     package com.croftsoft.core.animation.animator;

     import java.awt.*;
     import java.util.*;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.core.animation.model.ModelAccessor;
     import com.croftsoft.core.animation.model.WorldAccessor;

     /*********************************************************************
     * ComponentAnimator that maps Models to views.
     *
     * @version
     *   2003-06-18
     * @since
     *   2003-04-14
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public abstract class  WorldAnimator
       implements ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final WorldAccessor  worldAccessor;

     private final Map            modelAccessorToComponentAnimatorMap;

     private final Set            oldModelAccessorSet;

     //

     private ModelAccessor [ ]  modelAccessors;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  WorldAnimator ( WorldAccessor  worldAccessor )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.worldAccessor = worldAccessor );

       modelAccessorToComponentAnimatorMap = new HashMap ( );

       oldModelAccessorSet = new HashSet ( );

       modelAccessors = new ModelAccessor [ 0 ];
     }

     //////////////////////////////////////////////////////////////////////
     // interface ComponentAnimator methods
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       if ( worldAccessor.isCleared ( ) )
       {
         component.repaint ( );

         oldModelAccessorSet.clear ( );

         modelAccessorToComponentAnimatorMap.clear ( );
       }

       modelAccessors = worldAccessor.getModelAccessors ( modelAccessors );       

       for ( int  i = 0; i < modelAccessors.length; i++ )
       {
         ModelAccessor  modelAccessor = modelAccessors [ i ];

         if ( modelAccessor == null )
         {
           break;
         }

         oldModelAccessorSet.remove ( modelAccessor );

         ComponentAnimator  componentAnimator
           = getComponentAnimator ( modelAccessor );

         componentAnimator.update ( component );
       }

       Iterator  iterator = oldModelAccessorSet.iterator ( );

       while ( iterator.hasNext ( ) )
       {
         ModelAccessor  modelAccessor
           = ( ModelAccessor ) iterator.next ( );

         modelAccessorToComponentAnimatorMap.remove ( modelAccessor );
       }

       oldModelAccessorSet.clear ( );

       for ( int  i = 0; i < modelAccessors.length; i++ )
       {
         ModelAccessor  modelAccessor = modelAccessors [ i ];

         if ( modelAccessor == null )
         {
           break;
         }

         oldModelAccessorSet.add ( modelAccessor );
       }       
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < modelAccessors.length; i++ )
       {
         ModelAccessor  modelAccessor = modelAccessors [ i ];

         if ( modelAccessor == null )
         {
           break;
         }

         ComponentAnimator  componentAnimator
           = getComponentAnimator ( modelAccessor );

         componentAnimator.paint ( component, graphics );
       }
     }

     //////////////////////////////////////////////////////////////////////
     // protected methods
     //////////////////////////////////////////////////////////////////////

     protected ComponentAnimator  getComponentAnimator (
       ModelAccessor  modelAccessor )
     //////////////////////////////////////////////////////////////////////
     {
       ComponentAnimator  componentAnimator = ( ComponentAnimator )
         modelAccessorToComponentAnimatorMap.get ( modelAccessor );

       if ( componentAnimator == null )
       {
         componentAnimator = createComponentAnimator ( modelAccessor );

         modelAccessorToComponentAnimatorMap.put (
           modelAccessor, componentAnimator );
       }

       return componentAnimator;
     }

     protected ComponentAnimator  createComponentAnimator (
       ModelAccessor  modelAccessor )
     //////////////////////////////////////////////////////////////////////
     {
       return new ModelAnimator ( modelAccessor );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
