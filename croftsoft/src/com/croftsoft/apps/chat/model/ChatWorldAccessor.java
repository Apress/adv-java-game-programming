     package com.croftsoft.apps.chat.model;

     import com.croftsoft.core.animation.model.ModelAccessor;
     import com.croftsoft.core.animation.model.WorldAccessor;

     /*********************************************************************
     * Read-only access to the ChatWorld.
     *
     * @version
     *   2003-06-05
     * @since
     *   2003-06-05
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  ChatWorldAccessor
       extends WorldAccessor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Gets all ModelAccessors, active and inactive.
     *********************************************************************/
     public ModelAccessor [ ]  getModelAccessors (
       ModelAccessor [ ]  modelAccessors );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }