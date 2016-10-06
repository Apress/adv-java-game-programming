     package com.croftsoft.ajgp.data;

     import java.io.IOException;
     import java.util.Properties;

     /*********************************************************************
     * Example of storing data using the Properties class.
     *
     * @version
     *   2003-03-25
     * @since
     *   2003-03-25
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  PropertiesExample
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static void  main ( String [ ]  args )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       Properties  properties = new Properties ( );

       properties.setProperty ( "health", "10" );

       properties.setProperty ( "wealth", "99" );

       properties.setProperty ( "wisdom", "18" );

       properties.store ( System.out, "Game Data" );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }