     package com.croftsoft.core.sql;

     import java.io.Serializable;
     import java.sql.*;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Initializer object for creating JDBC Connections.
     *
     * <p>
     * Java 1.1 compatible.
     * </p>
     *
     * @version
     *   2001-06-13
     * @since
     *   2001-06-13
     * @author
     *   <a target="_blank" href="http://croftsoft.com/">David W. Croft</a>
     *********************************************************************/

     public final class  ConnectionInit
       implements Serializable
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final long  serialVersionUID = 1L;

     public final String  jdbcDriverClassName;

     public final String  jdbcUrlString;

     public final String  username;

     public final String  password;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Main constructor method.
     *
     * @throws NullArgumentException
     *
     *   If any of the arguments is null.
     *********************************************************************/
     public  ConnectionInit (
       String  jdbcDriverClassName,
       String  jdbcUrlString,
       String  username,
       String  password )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check (
         this.jdbcDriverClassName = jdbcDriverClassName );

       NullArgumentException.check ( this.jdbcUrlString = jdbcUrlString );

       NullArgumentException.check ( this.username      = username      );

       NullArgumentException.check ( this.password      = password      );
     }

     /*********************************************************************
     * Convenience constructor that takes an array of String arguments.
     *
     * <p>
     * The argument ordering is the same as the main constructor method.
     * </p>
     *
     * @throws NullArgumentException
     *
     *   If the args is null or any of its elements is null.
     *
     * @throws IllegalArgumentException
     *
     *   If args.length != 4.
     *********************************************************************/
     public  ConnectionInit ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( args );

       if ( args.length != 4 )
       {
         throw new IllegalArgumentException ( "args.length != 4" );
       }

       NullArgumentException.check (
         this.jdbcDriverClassName = args [ 0 ] );

       NullArgumentException.check ( this.jdbcUrlString = args [ 1 ] );

       NullArgumentException.check ( this.username      = args [ 2 ] );

       NullArgumentException.check ( this.password      = args [ 3 ] );
     }
    
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Convenience method for creating connections.
     *
     * <p>
     * Does not retain reference to newly created Connection.
     * </p>
     *********************************************************************/
     public Connection  createConnection ( )
       throws ClassNotFoundException, SQLException
     //////////////////////////////////////////////////////////////////////
     {
       Class.forName ( jdbcDriverClassName );

       return DriverManager.getConnection (
         jdbcUrlString, username, password );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
