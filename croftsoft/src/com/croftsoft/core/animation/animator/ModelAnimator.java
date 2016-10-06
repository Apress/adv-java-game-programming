     package com.croftsoft.core.animation.animator;

     import java.awt.*;
     import java.awt.geom.Rectangle2D;
     import javax.swing.JComponent;

     import com.croftsoft.core.animation.ComponentAnimator;
     import com.croftsoft.core.lang.NullArgumentException;

     import com.croftsoft.core.animation.model.ModelAccessor;

     /*********************************************************************
     * The view for a Model.
     *
     * @version
     *   2003-06-06
     * @since
     *   2003-04-01
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public class  ModelAnimator
       implements ComponentAnimator
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected final ModelAccessor  modelAccessor;

     protected final Rectangle      oldRepaintRectangle;

     protected final Rectangle      newRepaintRectangle;

     //

     protected Color    color;

     protected boolean  previouslyActive;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  ModelAnimator (
       ModelAccessor  modelAccessor,
       Color          color )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.modelAccessor = modelAccessor );

       this.color = color;

       oldRepaintRectangle = new Rectangle ( );

       newRepaintRectangle = new Rectangle ( );
     }

     public  ModelAnimator ( ModelAccessor  modelAccessor )
     //////////////////////////////////////////////////////////////////////
     {
       this ( modelAccessor, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  update ( JComponent  component )
     //////////////////////////////////////////////////////////////////////
     {
       boolean  currentlyActive = modelAccessor.isActive ( );

       if ( !previouslyActive )
       {
         if ( currentlyActive )
         {
           // went from off to on

           getRepaintRectangle ( oldRepaintRectangle );

           component.repaint ( oldRepaintRectangle );

           previouslyActive = currentlyActive;
         }

         // otherwise stayed off
       }
       else if ( !currentlyActive )
       {
         // went from on to off

         component.repaint ( oldRepaintRectangle );

         previouslyActive = currentlyActive;
       }
       else
       {
         // stayed on

         getRepaintRectangle ( newRepaintRectangle );

         if ( !oldRepaintRectangle.equals ( newRepaintRectangle ) )
         {
           Rectangle2D.union (
             oldRepaintRectangle,
             newRepaintRectangle,
             oldRepaintRectangle );

           component.repaint ( oldRepaintRectangle );

           oldRepaintRectangle.setBounds ( newRepaintRectangle );
         }
         else if ( isUpdated ( ) )
         {
           component.repaint ( newRepaintRectangle );
         }
       }
     }

     protected void  getRepaintRectangle ( Rectangle  repaintRectangle )
     //////////////////////////////////////////////////////////////////////
     {
       repaintRectangle.setBounds (
         modelAccessor.getShape ( ).getBounds ( ) );
     }

     protected boolean  isUpdated ( )
     //////////////////////////////////////////////////////////////////////
     {
       return modelAccessor.isUpdated ( );
     }

     public void  paint (
       JComponent  component,
       Graphics2D  graphics )
     //////////////////////////////////////////////////////////////////////
     {
       if ( modelAccessor.isActive ( ) )
       {
         if ( color == null )
         {
           graphics.setColor ( component.getForeground ( ) );
         }
         else
         {
           graphics.setColor ( color );
         }

         graphics.fill ( modelAccessor.getShape ( ) );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }