     package com.croftsoft.apps.chat.model;

     import com.croftsoft.core.animation.model.Impassable;
     import com.croftsoft.core.animation.model.Model;
     import com.croftsoft.core.math.geom.PointXY;
     import com.croftsoft.core.role.Consumer;

     /*********************************************************************
     * Provides methods for manipulating the Models in the game.
     *
     * @version
     *   2003-06-18
     * @since
     *   2003-06-05
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  ChatModel
       extends Model, ChatModelAccessor, Impassable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  setActive ( boolean  active );

     public void  setDestination ( PointXY  pointXY );

     public void  setEventConsumer ( Consumer  eventConsumer );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
