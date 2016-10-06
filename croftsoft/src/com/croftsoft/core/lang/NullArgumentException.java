     package com.croftsoft.core.lang;

     /*********************************************************************
     * Thrown to indicate that a method has been passed a null argument.
     *
     * <P>
     *
     * The static convenience method <i>check()</i> is a useful shorthand
     * notation for checking whether object constructor method arguments
     * are null:
     * <pre><code>
     * public  Book ( String  title )
     * {
     *   NullArgumentException.check ( this.title = title, "null title" );
     * }
     * </code></pre>
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft">David W. Croft</A>
     * @version
     *   2001-02-16
     *********************************************************************/

     public final class  NullArgumentException
       extends IllegalArgumentException
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Checks whether the argument is null.
     *
     * @throws NullArgumentException
     *   If the argument is null.
     *********************************************************************/
     public static void  check ( Object  argument )
     //////////////////////////////////////////////////////////////////////
     {
       if ( argument == null )
       {
         throw new NullArgumentException ( );
       }
     }

     /*********************************************************************
     * Checks whether the argument is null.
     *
     * @param  detailMessage
     *   The detail message provided if a NullArgumentExcepton is created.
     * @throws NullArgumentException
     *   If the argument is null.
     *********************************************************************/
     public static void  check (
       Object  argument,
       String  detailMessage )
     //////////////////////////////////////////////////////////////////////
     {
       if ( argument == null )
       {
         throw new NullArgumentException ( detailMessage );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  NullArgumentException ( )
     //////////////////////////////////////////////////////////////////////
     {
     }

     public  NullArgumentException ( String  detailMessage )
     //////////////////////////////////////////////////////////////////////
     {
       super ( detailMessage );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
