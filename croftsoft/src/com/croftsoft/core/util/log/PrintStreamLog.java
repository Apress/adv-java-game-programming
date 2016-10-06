     package com.croftsoft.core.util.log;

     import java.io.PrintStream;

     import com.croftsoft.core.lang.NullArgumentException;

     /*********************************************************************
     * Writes log entries out to a PrintStream.
     *
     * <p>
     * All methods are synchronized.
     * </p>
     *
     * <p>
     * Example:
     * <pre><code>
     * Log  log = new PrintStreamLog ( System.out );
     * </code></pre>
     * </p>
     *
     * @version
     *   2001-08-02
     * @since
     *   2001-02-27
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  PrintStreamLog
       implements Log
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final PrintStreamLog  SYSTEM_OUT_INSTANCE
       = new PrintStreamLog ( System.out );

     public static final PrintStreamLog  SYSTEM_ERR_INSTANCE
       = new PrintStreamLog ( System.err );

     private final PrintStream  printStream;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  PrintStreamLog ( PrintStream  printStream )
     //////////////////////////////////////////////////////////////////////
     {
       NullArgumentException.check ( this.printStream = printStream );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public synchronized void  record ( String  message )
     //////////////////////////////////////////////////////////////////////
     {
       printStream.println ( message );
     }

     public synchronized void  record ( Throwable  throwable )
     //////////////////////////////////////////////////////////////////////
     {
       throwable.printStackTrace ( printStream );
     }

     public synchronized void  record (
       String     message,
       Throwable  throwable )
     //////////////////////////////////////////////////////////////////////
     {
       printStream.println ( message );

       throwable.printStackTrace ( printStream );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
