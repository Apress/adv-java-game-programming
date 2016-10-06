     package com.croftsoft.core.util.consumer;

     import com.croftsoft.core.role.Consumer;

     /*********************************************************************
     * A singleton null object implementation of a Consumer.
     *
     * @version
     *   2003-06-10
     * @since
     *   2003-06-10
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  NullConsumer
       implements Consumer
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final NullConsumer  INSTANCE = new NullConsumer ( );

     public void  consume ( Object  o ) { }

     private  NullConsumer ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }