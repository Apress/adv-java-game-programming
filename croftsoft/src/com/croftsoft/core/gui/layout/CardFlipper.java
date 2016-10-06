     package com.croftsoft.core.gui.layout;

     import java.awt.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Provides an abstraction wrapper for the CardLayout.show() method.
     *
     * <p>
     * Use when you do not want to give the caller a reference to the
     * parentContainer.
     * </p>
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     *
     * @version
     *   2001-03-26
     * @since
     *   2001-03-26
     * @author
     *   <a href="http://www.alumni.caltech.edu/~croft/">David W. Croft</a>
     *********************************************************************/

     public final class  CardFlipper
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private Container   parentContainer;

     private CardLayout  cardLayout;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  CardFlipper (
       Container   parentContainer,
       CardLayout  cardLayout )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.parentContainer = parentContainer );

       NullArgumentException.check ( this.cardLayout = cardLayout );
     }

     /*********************************************************************
     * this ( parentContainer,
     *   ( CardLayout ) parentContainer.getLayout ( ) );
     *********************************************************************/
     public  CardFlipper ( Container   parentContainer )
     //////////////////////////////////////////////////////////////////////
     {
       this ( parentContainer,
         ( CardLayout ) parentContainer.getLayout ( ) );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * @throws NullArgumentException
     *   If cardName is null.
     *********************************************************************/
     public void  flipCard ( String  cardName )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( cardName );

       cardLayout.show ( parentContainer, cardName );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
