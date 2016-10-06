     package com.croftsoft.core.sql;

     import java.math.BigDecimal;
     import java.sql.*;
     import java.util.Vector;

     import com.croftsoft.core.lang.NullArgumentException;
     import com.croftsoft.core.lang.Pair;

     /*********************************************************************
     * A library of SQL manipulation methods.
     *
     * @version
     *   2002-09-16
     * @since
     *   2001-06-06
     * @author
     *   <a href="http://alumni.caltech.edu/~croft">David Wallace Croft</a>
     *********************************************************************/

     public final class  SqlLib
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * If set to true, debugging information will be printed to System.out.
     *********************************************************************/
     public static boolean  debug = false;

     /*********************************************************************
     * "All the major databases support VARCHAR lengths up to 254
     * characters." -- JDBC API Tutorial and Reference, 2nd Edition, p911.
     *********************************************************************/
     public static int  VARCHAR_LENGTH_MAX = 254;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Prints the result of the test method.
     *********************************************************************/
     public static void  main ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( test ( args ) );
     }

     /*********************************************************************
     * Test method.
     *********************************************************************/
     public static boolean  test ( String [ ]  args )
     //////////////////////////////////////////////////////////////////////
     {
       String  sql = createDeleteSql (
         "TABLE_USER",
         new Pair [ ] {
           new Pair ( "USERNAME", "croft" ) } );

       if ( !sql.equals (
         "DELETE FROM TABLE_USER WHERE USERNAME='croft'" ) )
       {
         System.out.println ( sql );

         return false;
       }

       sql = createInsertSql (
         "TABLE_USER",
         new String [ ] { "croft", "abc'123", null } );

       if ( !sql.equals (
         "INSERT INTO TABLE_USER VALUES('croft','abc''123',NULL)" ) )
       {
         System.out.println ( sql );

         return false;
       }

       sql = createSelectSql (
         new String [ ] { "FIRST_NAME", "LAST_NAME" },
         "TABLE_USER",
         new Pair ( "USERNAME", "croft" ) );

       if ( !sql.equals ( "SELECT FIRST_NAME,LAST_NAME FROM TABLE_USER"
         + " WHERE USERNAME='croft'" ) )
       {
         System.out.println ( sql );

         return false;
       }

       sql = createUpdateSql (
         "TABLE_USER",
         new Pair [ ] {
           new Pair ( "GENDER"  , "M"  ),
           new Pair ( "BIRTHDAY", null ) },
         new Pair [ ] {
           new Pair ( "USERNAME", "joe" ) } );

       if ( !sql.equals (
         "UPDATE TABLE_USER SET GENDER='M',BIRTHDAY=NULL"
         + " WHERE USERNAME='joe'" ) )
       {
         System.out.println ( sql );

         return false;
       }

       return true;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Counts the rows in a table that meet the WHERE condition.
     *
     * <p>
     * Example query:
     * <code>
     * <pre>
     * SELECT COUNT(*) FROM TABLE_USER WHERE GENDER='M'
     * </pre>
     * </code>
     * </p>
     *
     * @param  wherePair
     *
     *   If the wherePair object is null, all rows will be counted.
     *   Instance variable wherePair.value may be null.
     *   A non-null wherePair.value will converted by escapeQuotes().
     * 
     * @return
     *
     *   The count of rows selected.
     *
     * @throws NullArgumentException
     *
     *   If tableName is null.
     *********************************************************************/
     public static int  count (
       Connection  connection,
       String      tableName,
       Pair        wherePair )
       throws SQLException
     //////////////////////////////////////////////////////////////////////
     {
       return count ( connection, tableName,
         wherePair == null
           ? ( Pair [ ] ) null : new Pair [ ] { wherePair } );
     }

     /*********************************************************************
     * Counts the rows in a table that meet the WHERE condition.
     *
     * <p>
     * Example query:
     * <code>
     * <pre>
     * SELECT COUNT(*) FROM TABLE_USER WHERE GENDER='M' AND AGE='34'
     * </pre>
     * </code>
     * </p>
     *
     * @param  wherePairs
     *
     *   If the wherePairs object is null, all rows will be counted.
     *   If the length of wherePairs is greater than one, the where
     *     clause will be the conjuction ("AND") of the individual
     *     where pairs.
     *   A null wherePair.value will be translated as "IS NULL".
     *   A non-null wherePair.value will converted by escapeQuotes().
     * 
     * @return
     *
     *   The count of rows selected.
     *
     * @throws NullArgumentException
     *
     *   If tableName is null.
     *********************************************************************/
     public static int  count (
       Connection  connection,
       String      tableName,
       Pair [ ]    wherePairs )
       throws SQLException
     //////////////////////////////////////////////////////////////////////
     {
       String  querySql = createCountSql ( tableName, wherePairs );

       if ( debug )
       {
         System.out.println ( "SqlLib.count():  " + querySql );
       }

       Statement  statement = null;
       
       try
       {
         statement = connection.createStatement ( );

         ResultSet  resultSet = statement.executeQuery ( querySql );

         if ( resultSet.next ( ) )
         {
           return resultSet.getInt ( 1 );
         }
         else
         {
           return 0;
         }
       }
       finally
       {
         if ( statement != null )
         {
           statement.close ( );
         }
       }
     }

     /*********************************************************************
     * Creates an SQL SELECT COUNT(*) statement.
     *
     * Used to count the number of rows that meet the criterion.
     *
     * <p>
     * Example output:
     * <code>
     * <pre>
     * SELECT COUNT(*) FROM TABLE_USER WHERE GENDER='M'
     * </pre>
     * </code>
     * </p>
     *
     * @param  wherePair
     *
     *   If the wherePair object is null, no "where" clause will be
     *   appended, indicating that all rows in the table should be
     *   selected.
     *   Instance variable wherePair.value may be null.
     *   A non-null wherePair.value will converted by escapeQuotes().
     *
     * @throws NullArgumentException
     *
     *   If tableName is null.
     *********************************************************************/
     public static String  createCountSql (
       String      tableName,
       Pair        wherePair )
     //////////////////////////////////////////////////////////////////////
     {
       return createCountSql ( tableName,
         wherePair == null
           ? ( Pair [ ] ) null : new Pair [ ] { wherePair } );
     }

     /*********************************************************************
     * Creates an SQL SELECT COUNT(*) statement.
     *
     * Used to count the number of rows that meet the criteria.
     *
     * <p>
     * Example output:
     * <code>
     * <pre>
     * SELECT COUNT(*) FROM TABLE_USER WHERE GENDER='M' AND AGE='34'
     * </pre>
     * </code>
     * </p>
     *
     * @param  wherePairs
     *
     *   If the wherePairs object is null, no "where" clause will be
     *     appended, indicating that all rows in the table should be
     *     selected.
     *   If the length of wherePairs is greater than one, the where
     *     clause will be the conjuction ("AND") of the individual
     *     where pairs.
     *   A null wherePair.value will be translated as "IS NULL".
     *   A non-null wherePair.value will converted by escapeQuotes().
     *
     * @throws NullArgumentException
     *
     *   If tableName is null.
     *********************************************************************/
     public static String  createCountSql (
       String      tableName,
       Pair [ ]    wherePairs )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( tableName );

       StringBuffer  stringBuffer
         = new StringBuffer ( "SELECT COUNT(*) FROM " );

       stringBuffer.append ( tableName );

       appendWhereClause ( stringBuffer, wherePairs );

       return stringBuffer.toString ( );
     }

     /*********************************************************************
     * Creates an SQL DELETE statement.
     *
     * <p>
     * Example output:
     * <code>
     * <pre>
     * DELETE FROM TABLE_USER WHERE GENDER='M' AND AGE='34'
     * </pre>
     * </code>
     * </p>
     *
     * @param  wherePairs
     *
     *   If the wherePairs object is null, no "where" clause will be
     *     appended, indicating that all rows in the table should be
     *     selected.
     *   If the length of wherePairs is greater than one, the where
     *     clause will be the conjuction ("AND") of the individual
     *     where pairs.
     *   A null wherePair.value will be translated as "IS NULL".
     *   A non-null wherePair.value will converted by escapeQuotes().
     *
     * @throws NullArgumentException
     *
     *   If tableName is null.
     *********************************************************************/
     public static String  createDeleteSql (
       String    tableName,
       Pair [ ]  wherePairs )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( tableName );

       StringBuffer  stringBuffer = new StringBuffer ( );

       stringBuffer.append ( "DELETE FROM " );

       stringBuffer.append ( tableName );

       appendWhereClause ( stringBuffer, wherePairs );

       return stringBuffer.toString ( );
     }

     /*********************************************************************
     * Creates an SQL INSERT statement.
     *
     * <p>
     * Example output:
     * <code>
     * <pre>
     * INSERT INTO TABLE_USER VALUES('croft','abc''123',NULL)
     * </pre>
     * </code>
     * </p>
     *
     * @param  values
     *
     *   Individual elements of the array may be null.
     *   Non-null values will be converted by escapeQuotes().
     *
     * @throws NullArgumentException
     *
     *   If tableName or values is null.
     *********************************************************************/
     public static String  createInsertSql (
       String      tableName,
       String [ ]  values )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( tableName );

       NullArgumentException.check ( values    );

       StringBuffer  stringBuffer = new StringBuffer ( );

       stringBuffer.append ( "INSERT INTO " );

       stringBuffer.append ( tableName );

       stringBuffer.append ( " VALUES(" );

       for ( int  i = 0; i < values.length; i++ )
       {
         if ( i > 0 )
         {
           stringBuffer.append ( "," );           
         }

         if ( values [ i ] != null )
         {
           stringBuffer.append ( "'" );

           stringBuffer.append ( escapeQuotes ( values [ i ] ) );

           stringBuffer.append ( "'" );
         }
         else
         {
           stringBuffer.append ( "NULL" );
         }
       }

       stringBuffer.append ( ")" );

       return stringBuffer.toString ( );
     }

     /*********************************************************************
     * Creates an SQL SELECT statement.
     *
     * <p>
     * Example output:
     * <code>
     * <pre>
     * SELECT FIRST_NAME,LAST_NAME FROM TABLE_USER WHERE USERNAME='croft'
     * </pre>
     * </code>
     * </p>
     *
     * @param  selectFieldNames
     *
     *   Must not be null.  Elements must not be null.
     *   Use "*" to select all fields.
     *
     * @param  wherePair
     *
     *   If the wherePair object is null, no "where" clause will be
     *   appended, indicating that all rows in the table should be
     *   selected.
     *   Instance variable wherePair.value may be null.
     *   A non-null wherePair.value will converted by escapeQuotes().
     *
     * @throws NullArgumentException
     *
     *   If tableName, selectFieldNames, or an element of setFieldNames is
     *   null.
     *********************************************************************/
     public static String  createSelectSql (
       String [ ]  selectFieldNames,
       String      tableName,
       Pair        wherePair )
     //////////////////////////////////////////////////////////////////////
     {
       return createSelectSql (
         selectFieldNames, tableName,
         wherePair == null
           ? ( Pair [ ] ) null : new Pair [ ] { wherePair } );
     }

     /*********************************************************************
     * Creates an SQL SELECT statement.
     *
     * <p>
     * Example output:
     * <code>
     * <pre>
     * SELECT NAME,SALARY FROM TABLE_USER WHERE GENDER='M' AND AGE='34'
     * </pre>
     * </code>
     * </p>
     *
     * @param  selectFieldNames
     *
     *   Must not be null.  Elements must not be null.
     *   Use "*" to select all fields.
     *
     * @param  wherePairs
     *
     *   If the wherePairs object is null, no "where" clause will be
     *     appended, indicating that all rows in the table should be
     *     selected.
     *   If the length of wherePairs is greater than one, the where
     *     clause will be the conjuction ("AND") of the individual
     *     where pairs.
     *   A null wherePair.value will be translated as "IS NULL".
     *   A non-null wherePair.value will converted by escapeQuotes().
     *
     * @throws NullArgumentException
     *
     *   If tableName, selectFieldNames, or an element of setFieldNames is
     *   null.
     *********************************************************************/
     public static String  createSelectSql (
       String [ ]  selectFieldNames,
       String      tableName,
       Pair [ ]    wherePairs )
     //////////////////////////////////////////////////////////////////////
     {
       return createSelectSql (
         selectFieldNames, tableName, wherePairs, null );
     }

     /*********************************************************************
     * Creates an SQL SELECT statement.
     *
     * <p>
     * Example output:
     * <code>
     * <pre>
     * SELECT NAME,SALARY FROM TABLE_USER WHERE GENDER='M' AND AGE='34'
     * </pre>
     * </code>
     * </p>
     *
     * @param  selectFieldNames
     *
     *   Must not be null.  Elements must not be null.
     *   Use "*" to select all fields.
     *
     * @param  wherePairs
     *
     *   If the wherePairs object is null, no "where" clause will be
     *     appended, indicating that all rows in the table should be
     *     selected.
     *   If the length of wherePairs is greater than one, the where
     *     clause will be the conjuction ("AND") of the individual
     *     where pairs.
     *   A null wherePair.value will be translated as "IS NULL".
     *   A non-null wherePair.value will converted by escapeQuotes().
     *
     * @param  orderBy
     *
     *   The column name to use for ordering.
     *
     * @throws NullArgumentException
     *
     *   If tableName, selectFieldNames, or an element of setFieldNames is
     *   null.
     *********************************************************************/
     public static String  createSelectSql (
       String [ ]  selectFieldNames,
       String      tableName,
       Pair [ ]    wherePairs,
       String      orderBy )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( selectFieldNames  );

       NullArgumentException.check ( tableName );

       StringBuffer  stringBuffer = new StringBuffer ( );

       stringBuffer.append ( "SELECT " );

       for ( int  i = 0; i < selectFieldNames.length; i++ )
       {
         if ( i > 0 )
         {
           stringBuffer.append ( ',' );           
         }

         NullArgumentException.check (
           selectFieldNames [ i ], "selectFieldNames[" + i + "] is null" );

         stringBuffer.append ( selectFieldNames [ i ] );
       }

       stringBuffer.append ( " FROM " );

       stringBuffer.append ( tableName );

       appendWhereClause ( stringBuffer, wherePairs );

       if ( orderBy != null )
       {
         stringBuffer.append ( " ORDER BY " );

         stringBuffer.append ( orderBy );
       }

       return stringBuffer.toString ( );
     }

     /*********************************************************************
     * Creates an SQL UPDATE statement.
     *
     * <p>
     * Example output:
     * <code>
     * <pre>
     * UPDATE TABLE_USER SET GENDER='M',BIRTHDAY=NULL WHERE USERNAME='joe'
     * </pre>
     * </code>
     * </p>
     *
     * @param  setPairs
     *
     *   Must not be null.  Array elements must not be null.
     *   An element setPair.value may be null.
     *   A non-null setPair.value will converted by escapeQuotes().
     *
     * @param  wherePairs
     *
     *   If the wherePairs object is null, no "where" clause will be
     *     appended, indicating that all rows in the table should be
     *     selected.
     *   If the length of wherePairs is greater than one, the where
     *     clause will be the conjuction ("AND") of the individual
     *     where pairs.
     *   A null wherePair.value will be translated as "IS NULL".
     *   A non-null wherePair.value will converted by escapeQuotes().
     *
     * @throws NullArgumentException
     *
     *   If tableName, setPairs, or an element of setPairs is null.
     *********************************************************************/
     public static String  createUpdateSql (
       String    tableName,
       Pair [ ]  setPairs,
       Pair [ ]  wherePairs )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( tableName );

       NullArgumentException.check ( setPairs  );

       StringBuffer  stringBuffer = new StringBuffer ( );

       stringBuffer.append ( "UPDATE " );

       stringBuffer.append ( tableName );

       stringBuffer.append ( " SET " );

       for ( int  i = 0; i < setPairs.length; i++ )
       {
         if ( i > 0 )
         {
           stringBuffer.append ( ',' );           
         }

         Pair  setPair = setPairs [ i ];

         NullArgumentException.check (
           setPair, "setPairs[" + i + "] is null" );

         stringBuffer.append ( setPair.name );

         stringBuffer.append ( '=' );

         if ( setPair.value != null )
         {
           stringBuffer.append ( '\'' );

           stringBuffer.append ( escapeQuotes ( setPair.value ) );

           stringBuffer.append ( '\'' );
         }
         else
         {
           stringBuffer.append ( "NULL" );
         }
       }

       appendWhereClause ( stringBuffer, wherePairs );

       return stringBuffer.toString ( );
     }

     /*********************************************************************
     * Removes rows from a table.
     *
     * @param  wherePairs
     *
     *   See method createDeleteSql() for description.
     *
     * @return
     *
     *   The number of rows deleted.
     *********************************************************************/
     public static int  delete (
       Connection  connection,
       String      tableName,
       Pair [ ]    wherePairs )
       throws SQLException
     //////////////////////////////////////////////////////////////////////
     {
       String  updateSql = createDeleteSql ( tableName, wherePairs );

       if ( debug )
       {
         System.out.println ( "SqlLib.delete():  " + updateSql );
       }

       return executeUpdate ( connection, updateSql );
     }

     public static int  dropTable (
       Connection  connection,
       String      tableName )
       throws SQLException
     //////////////////////////////////////////////////////////////////////
     {
       String  updateSql = "DROP TABLE " + tableName;

       if ( debug )
       {
         System.out.println ( "SqlLib.dropTable():  " + updateSql );
       }

       return executeUpdate ( connection, updateSql );
     }

     /*********************************************************************
     * Doubles all single and double quotes in the original String.
     *
     * <p>
     * Used to prepare a String to be passed as an SQL statement variable.
     * For example, notice how the single quote is doubled inside the
     * the password "abc'123" which contains an apostrophe:
     * <code>
     * <pre>
     * INSERT INTO TABLE_USER VALUES ('croft', 'abc''123')
     * </pre>
     * </code>
     * </p>
     *********************************************************************/
     public static String  escapeQuotes ( String  originalString )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( originalString );

       StringBuffer  stringBuffer = new StringBuffer ( );

       int  originalStringLength = originalString.length ( );

       for ( int  i = 0; i < originalStringLength; i++ )
       {
         char  c = originalString.charAt ( i );

         stringBuffer.append ( c );

         if ( c == '\'' )
         {
           stringBuffer.append ( '\'' );
         }
         else if ( c == '"' )
         {
           stringBuffer.append ( '"' );
         }
       }

       return stringBuffer.toString ( );
     }

     /*********************************************************************
     * Executes an SQL update statement.
     *
     * <p>
     * This convenience method creates a new Statement instance,
     * executes the update, and then ensures that the Statement is closed
     * before return or abnormal exit.
     * </p>
     *
     * @return
     *
     *   The number of rows created.
     *********************************************************************/
     public static int  executeUpdate (
       Connection  connection,
       String      updateSql )
       throws SQLException
     //////////////////////////////////////////////////////////////////////
     {
       Statement  statement = null;
       
       try
       {
         statement = connection.createStatement ( );

         return statement.executeUpdate ( updateSql );
       }
       finally
       {
         if ( statement != null )
         {
           statement.close ( );
         }
       }
     }

     /*********************************************************************
     * Adds a row to a table.
     *
     * @param  values
     *
     *   Each value will be converted by escapeQuotes() before being used.
     * 
     * @return
     *
     *   The number of rows created.
     *********************************************************************/
     public static int  insert (
       Connection  connection,
       String      tableName,
       String [ ]  values )
       throws SQLException
     //////////////////////////////////////////////////////////////////////
     {
       String  updateSql = createInsertSql ( tableName, values );

       if ( debug )
       {
         System.out.println ( "SqlLib.insert():  " + updateSql );
       }

       return executeUpdate ( connection, updateSql );
     }

     /*********************************************************************
     * Returns the maximum column value.
     *
     * <p>
     * Example query:
     * <code>
     * <pre>
     * SELECT MAX(SALARY) FROM TABLE_EMPLOYEE WHERE DEPARTMENT_ID='3'
     * </pre>
     * </code>
     * </p>
     *
     * @param  columnName
     *
     *   Must not be null.
     *
     * @param  wherePair
     *
     *   If the wherePair object is null, all rows will be included.
     *   Instance variable wherePair.value may be null.
     *   A non-null wherePair.value will converted by escapeQuotes().
     * 
     * @return
     *
     *   The maximum column value as a BigDecimal or null if there are no
     *   non-null values selected to compare.
     *
     * @throws NullArgumentException
     *
     *   If columnName or tableName is null.
     *********************************************************************/
     public static BigDecimal  max (
       Connection  connection,
       String      columnName,
       String      tableName,
       Pair        wherePair )
       throws SQLException
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( columnName );

       NullArgumentException.check ( tableName );

       StringBuffer  stringBuffer = new StringBuffer ( "SELECT MAX(" );

       stringBuffer.append ( columnName );

       stringBuffer.append ( ") FROM " );

       stringBuffer.append ( tableName );

       appendWhereClause ( stringBuffer, wherePair );

       String  querySql = stringBuffer.toString ( );

       if ( debug )
       {
         System.out.println ( "SqlLib.max():  " + querySql );
       }

       Statement  statement = null;
       
       try
       {
         statement = connection.createStatement ( );

         ResultSet  resultSet = statement.executeQuery ( querySql );

         if ( resultSet.next ( ) )
         {
           return resultSet.getBigDecimal ( 1 );
         }
         else
         {
           return null;
         }
       }
       finally
       {
         if ( statement != null )
         {
           statement.close ( );
         }
       }
     }

     /*********************************************************************
     * Selects the first row that meets the where condition.
     *
     * <p>
     * Only returns the values for the first row selected.
     * </p>
     *
     * @param  selectFieldNames
     *
     *   Must not be null.  Elements must not be null.
     *   Use new String[]{"*"} to select all fields.
     *
     * @param  wherePair
     *
     *   See method createSelectSql() for description.
     *
     * @throws NullArgumentException
     *
     *   If tableName, selectFieldNames, or an element of setFieldNames is
     *   null.
     * 
     * @return
     *
     *   The field values for the first row selected or null if no row
     *   was selected.
     *********************************************************************/
     public static String [ ]  select (
       Connection  connection,
       String [ ]  selectFieldNames,
       String      tableName,
       Pair        wherePair )
       throws SQLException
     //////////////////////////////////////////////////////////////////////
     {
       return select (
         connection,
         selectFieldNames,
         tableName,
         wherePair == null
           ? ( Pair [ ] ) null : new Pair [ ] { wherePair } );
     }

     /*********************************************************************
     * Selects the first row that meets the where condition.
     *
     * <p>
     * Only returns the values for the first row selected.
     * </p>
     *
     * @param  selectFieldNames
     *
     *   Must not be null.  Elements must not be null.
     *   Use new String[]{"*"} to select all fields.
     *
     * @param  wherePairs
     *
     *   See method createSelectSql() for description.
     *
     * @throws NullArgumentException
     *
     *   If tableName, selectFieldNames, or an element of setFieldNames is
     *   null.
     * 
     * @return
     *
     *   The field values for the first row selected or null if no row
     *   was selected.
     *********************************************************************/
     public static String [ ]  select (
       Connection  connection,
       String [ ]  selectFieldNames,
       String      tableName,
       Pair [ ]    wherePairs )
       throws SQLException
     //////////////////////////////////////////////////////////////////////
     {
       return select ( connection,
         selectFieldNames, tableName, wherePairs, null, 0 );
     }

     /*********************************************************************
     * Returns the results of a SELECT query as a String array.
     *
     * <p>
     * This method lets you return multiple column values from a single row
     * or multiple row values from a single column.  The results are then
     * returned as a 1-dimensional String array.  If the input variable
     * <i>maxRows</i> is zero, just the column values from the first row
     * selected are returned.  If <i>maxRows</i> is greater than zero, up
     * to that number of row values from a single column are returned.
     * </p>
     *
     * @param  selectFieldNames
     *
     *   Must not be null.  Elements must not be null.
     *   Use new String[]{"*"} to select all fields.
     *
     * @param  wherePairs
     *
     *   See method createSelectSql() for description.
     *
     * @param  orderBy
     *
     *   The column name to use for ordering.
     *
     * @param  maxRows
     *
     *   If 0, multiple column values from a single row will be returned.
     *   If greater than 0, multiple row values from a single column
     *   will be returned, up to maxRows in length.
     *
     * @throws NullArgumentException
     *
     *   If tableName, selectFieldNames, or an element of setFieldNames is
     *   null.
     * 
     * @return
     *
     *   The field values for the first row selected or null if no row
     *   was selected.
     *********************************************************************/
     public static String [ ]  select (
       Connection  connection,
       String [ ]  selectFieldNames,
       String      tableName,
       Pair [ ]    wherePairs,
       String      orderBy,
       int         maxRows )
       throws SQLException
     //////////////////////////////////////////////////////////////////////
     {
       if ( maxRows < 0 )
       {
         throw new IllegalArgumentException ( "maxRows < 0" );
       }

       if ( maxRows > 0 && selectFieldNames.length > 1 )
       {
         throw new IllegalArgumentException (
           "selectFieldNames.length > 1 when maxRows > 0" );
       }

       String  querySql = createSelectSql (
         selectFieldNames, tableName, wherePairs, orderBy );

       if ( debug )
       {
         System.out.println ( "SqlLib.select():  " + querySql );
       }

       Statement  statement = null;
       
       try
       {
         statement = connection.createStatement ( );

         if ( maxRows > 0 )
         {
           statement.setFetchSize ( maxRows );

           statement.setMaxRows   ( maxRows );
         }
         else
         {
           statement.setFetchSize ( 1 );

           statement.setMaxRows   ( 1 );
         }

         ResultSet  resultSet = statement.executeQuery ( querySql );

         if ( !resultSet.next ( ) )
         {
           return null;
         }

         if ( maxRows > 0 )
         {
           Vector  stringVector = new Vector ( );

           stringVector.addElement ( resultSet.getString ( 1 ) );

           while ( resultSet.next ( ) )
           {
             stringVector.addElement ( resultSet.getString ( 1 ) );
           }

           String [ ]  values = new String [ stringVector.size ( ) ];

           stringVector.copyInto ( values );

           return values;
         }
         else
         {
           ResultSetMetaData  resultSetMetaData
             = resultSet.getMetaData ( );

           String [ ]  values
             = new String [ resultSetMetaData.getColumnCount ( ) ];

           for ( int  i = 0; i < values.length; i++ )
           {
             values [ i ] = resultSet.getString ( i + 1 );
           }

           return values;
         }
       }
       finally
       {
         if ( statement != null )
         {
           statement.close ( );
         }
       }
     }

     /*********************************************************************
     * Retrieves the String value at a given row and column.
     *
     * <p>
     * Only returns a single value for the first row selected.
     * </p>
     *
     * @param  selectFieldName
     *
     *   Must not be null.  If selectFieldName is "*", this method will
     *   return the value associated with the first field name only.
     *
     * @param  wherePair
     *
     *   See method createSelectSql() for description.
     *
     * @throws NullArgumentException
     *
     *   If tableName or selectFieldName is null.
     * 
     * @return
     *
     *   The column value for the first row selected or null if no
     *   row was selected.
     *********************************************************************/
     public static String  select (
       Connection  connection,
       String      selectFieldName,
       String      tableName,
       Pair        wherePair )
       throws SQLException
     //////////////////////////////////////////////////////////////////////
     {
       return select (
         connection,
         selectFieldName,
         tableName,
         wherePair == null
           ? ( Pair [ ] ) null : new Pair [ ] { wherePair } );
     }

     /*********************************************************************
     * Retrieves the String value at a given row and column.
     *
     * <p>
     * Only returns a single value for the first row selected.
     * </p>
     *
     * @param  selectFieldName
     *
     *   Must not be null.  If selectFieldName is "*", this method will
     *   return the value associated with the first field name only.
     *
     * @param  wherePairs
     *
     *   See method createSelectSql() for description.
     *
     * @throws NullArgumentException
     *
     *   If tableName or selectFieldName is null.
     * 
     * @return
     *
     *   The column value for the first row selected or null if no
     *   row was selected.
     *********************************************************************/
     public static String  select (
       Connection  connection,
       String      selectFieldName,
       String      tableName,
       Pair [ ]    wherePairs )
       throws SQLException
     //////////////////////////////////////////////////////////////////////
     {
       String [ ]  values = select ( connection,
         new String [ ] { selectFieldName }, tableName, wherePairs );

       return values == null ? null : values [ 0 ];
     }

     public static boolean  tableExists (
       Connection  connection,
       String      tableName )
       throws SQLException
     //////////////////////////////////////////////////////////////////////
     {
       DatabaseMetaData  databaseMetaData = connection.getMetaData ( );

       ResultSet  resultSet = databaseMetaData.getTables (
         ( String ) null,       // catalog
         ( String ) null,       // schemaPattern
         tableName,             // tableNamePattern
         ( String [ ] ) null ); // types

       return resultSet.next ( );
     }

     public static int  truncateTable (
       Connection  connection,
       String      tableName )
       throws SQLException
     //////////////////////////////////////////////////////////////////////
     {
       String  updateSql = "TRUNCATE TABLE " + tableName;

       if ( debug )
       {
         System.out.println ( "SqlLib.truncateTable():  " + updateSql );
       }

       return executeUpdate ( connection, updateSql );
     }

     /*********************************************************************
     * Updates rows in a table.
     *
     * @param  setPairs
     *
     *   Must not be null.  Array elements must not be null.
     *   An element setPair.value may be null.
     *   A non-null setPair.value will converted by escapeQuotes().
     *
     * @param  wherePair
     *
     *   See method createUpdateSql() for description.
     *
     * @throws NullArgumentException
     *
     *   If tableName, setPairs, or an element of setPairs is null.
     *
     * @return
     *
     *   The number of rows updated.
     *********************************************************************/
     public static int  update (
       Connection  connection,
       String      tableName,
       Pair [ ]    setPairs,
       Pair        wherePair )
       throws SQLException
     //////////////////////////////////////////////////////////////////////
     {
       return update (
         connection, tableName, setPairs, new Pair [ ] { wherePair } );
     }

     /*********************************************************************
     * Updates rows in a table.
     *
     * @param  setPairs
     *
     *   Must not be null.  Array elements must not be null.
     *   An element setPair.value may be null.
     *   A non-null setPair.value will converted by escapeQuotes().
     *
     * @param  wherePairs
     *
     *   See method createUpdateSql() for description.
     *
     * @throws NullArgumentException
     *
     *   If tableName, setPairs, or an element of setPairs is null.
     *
     * @return
     *
     *   The number of rows updated.
     *********************************************************************/
     public static int  update (
       Connection  connection,
       String      tableName,
       Pair [ ]    setPairs,
       Pair [ ]    wherePairs )
       throws SQLException
     //////////////////////////////////////////////////////////////////////
     {
       String  updateSql
         = createUpdateSql ( tableName, setPairs, wherePairs );

       if ( debug )
       {
         System.out.println ( "SqlLib.update():  " + updateSql );
       }

       return executeUpdate ( connection, updateSql );
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     private static void  appendWhereClause (
       StringBuffer  stringBuffer,
       Pair          wherePair )
     //////////////////////////////////////////////////////////////////////
     {
       appendWhereClause ( stringBuffer,
         wherePair == null
           ? ( Pair [ ] ) null : new Pair [ ] { wherePair } );
     }

     private static void  appendWhereClause (
       StringBuffer  stringBuffer,
       Pair [ ]      wherePairs )
     //////////////////////////////////////////////////////////////////////
     {
       if ( wherePairs != null )
       {
         stringBuffer.append ( " WHERE " );

         for ( int  i = 0; i < wherePairs.length; i++ )
         {
           Pair  wherePair = wherePairs [ i ];

           stringBuffer.append ( wherePair.name );

           if ( wherePair.value != null )
           {
             stringBuffer.append ( '=' );

             stringBuffer.append ( '\'' );

             stringBuffer.append ( escapeQuotes ( wherePair.value ) );

             stringBuffer.append ( '\'' );
           }
           else
           {
             stringBuffer.append ( " IS NULL" );
           }

           if ( i < wherePairs.length - 1 )
           {
             stringBuffer.append ( " AND " );
           }
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Static method library classes do not require instantiation.
     *********************************************************************/
     private  SqlLib ( ) { }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
