     package com.croftsoft.apps.template;

     import java.io.*;
     import java.util.*;

     import com.croftsoft.core.lang.StringLib;

     /*********************************************************************
     * Creates web pages from a template file and substitution map files.
     *
     * <P>
     *
     * Wherever the &lt;subst key="<I>key</I>"&gt;&lt;/subst&gt; occurs in
     * the template file, it is replaced with a value parsed from the
     * content mapping file to create a new *.html file.
     *
     * <P>
     *
     * Note that the key name is case-senstive.
     *
     * <P>
     *
     * Example Template File "template.html":
     * <PRE>
     * &lt;HTML&gt;
     * &lt;HEAD&gt;
     * &lt;SUBST KEY="HEAD"&gt;&lt;/SUBST&gt;
     * &lt;/HEAD&gt;
     * &lt;/HTML&gt;
     * &lt;BODY BGCOLOR="Blue"&gt;
     * &lt;CENTER&gt;
     * &lt;IMG SRC="/adbanner.gif"&gt;
     * &lt;/CENTER&gt;
     * &lt;P&gt;
     * &lt;SUBST KEY="BODY"&gt;&lt;/SUBST&gt;
     * &lt;/BODY&gt;
     * &lt;/HTML&gt;
     * </PRE>
     * Example Content Mapping File "filename.smap":
     * <PRE>
     * &lt;HTML&gt;
     * &lt;HEAD&gt;
     * &lt;TITLE&gt;
     * Welcome to my home page!
     * &lt;/TITLE&gt;
     * &lt;/HEAD&gt;
     * &lt;/HTML&gt;
     * &lt;BODY&gt;
     * My Home page
     * &lt;/BODY&gt;
     * &lt;/HTML&gt;
     * </PRE>
     * Example Resultant HTML File "filename.html":
     * <PRE>
     * &lt;HTML&gt;
     * &lt;HEAD&gt;
     * &lt;TITLE&gt;
     * Welcome to my home page!
     * &lt;/TITLE&gt;
     * &lt;/HEAD&gt;
     * &lt;/HTML&gt;
     * &lt;BODY BGCOLOR="Blue"&gt;
     * &lt;CENTER&gt;
     * &lt;IMG SRC="/adbanner.gif"&gt;
     * &lt;/CENTER&gt;
     * &lt;P&gt;
     * My Home page
     * &lt;/BODY&gt;
     * &lt;/HTML&gt;
     * </PRE>
     *
     * <P>
     *
     * Execute with the document root directory name as the command-line
     * argument (defaults to ".").  It will traverse
     * subdirectories as needed to create corresponding *.html files for
     * each *.smap file.  The template file in the current directory will
     * be used.  Subdirectories will use their parent template files unless
     * they have template files of their own.
     *
     * <P>
     *
     * Intended to operate like the JavaWebServer templating mechanism
     * except that the files are preprocessed.  Note that I am using
     * SUBST KEY="..." instead of SUBST DATA="..." as I intend to make
     * this more general-purpose over time.
     *
     * <P>
     *
     * <FONT COLOR="Red">
     * Current implementation limitations to be fixed later:
     *
     * <UL>
     * <LI> Weak parsing:  must be exactly
     *      "&lt;SUBST KEY="HEAD"&gt;&lt;/SUBST&gt; to work
     *      with no extra white space, no lower case letters (except for
     *      the case-sensitive key name), and no replaceable content
     *      between &lt;SUBST&gt; and &lt;/SUBST&gt; allowed.
     *      [fix using javax.swing.text.html parser?]
     * </UL>
     * </FONT>
     *
     * <P>
     *
     * @version
     *   2000-05-05
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     *********************************************************************/

     public class  Template
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private static final String  TEMPLATE_FILE_NAME = "template.html";

     private static final String  SMAP_EXTENSION     = ".smap";

     private final String      templateText;

     private       String [ ]  keys;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Recursively creates *.html files for each *.smap file.
     *
     * <P>
     *
     * @param  args
     *   First command-line argument is the document root directory;
     *   defaults to ".".
     *********************************************************************/
     public static void  main ( String [ ]  args ) throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       String  dirName = ".";

       if ( args.length > 0 )
       {
         dirName = args [ 0 ];
       }

       File  dir = new File ( dirName );

       if ( !dir.isDirectory ( ) )
       {
         throw new IllegalArgumentException (
           "\"" + dirName + "\" not a directory" );
       }

       generateFilesInBranch ( null, dir );
     }

     public static void  generateFilesInBranch (
       Template  parentTemplate,
       File      dir )
       throws IOException
     //////////////////////////////////////////////////////////////////////
     {
       if ( dir == null )
       {
         throw new IllegalArgumentException ( "null dir" );
       }

       Template  template = parentTemplate;

       File  templateFile = new File ( dir, TEMPLATE_FILE_NAME );

       if ( templateFile.exists ( ) )
       {
         System.out.println ( "Loading template from " + templateFile );

         String  templateText = com.orbs.open1.mit.io.FileLib.readFile (
           templateFile.toString ( ) );

         template = new Template ( templateText );
       }

       String [ ]  fileNames = dir.list ( );

       for ( int  i = 0; i < fileNames.length; i++ )
       {
         String  fileName = fileNames [ i ];

         File  sdatFile = new File ( dir, fileName );

         if ( sdatFile.isDirectory ( ) )
         {
           // recursive

           generateFilesInBranch ( template, sdatFile );

           continue;
         }

         if ( template == null )
         {
           continue;
         }

         if ( !fileName.endsWith ( SMAP_EXTENSION ) )
         {
           continue;
         }

         String  sdatText = com.orbs.open1.mit.io.FileLib.readFile (
           new File ( dir, fileName ).toString ( ) );

         Map  substitutionMap = new HashMap ( );

         String [ ]  keys = template.getKeys ( );

         for ( int  keyIndex = 0; keyIndex < keys.length; keyIndex++ )
         {
           String  value = extractTag ( sdatText, keys [ keyIndex ] );

           if ( value != null )
           {
             substitutionMap.put ( keys [ keyIndex ], value );
           }
         }

         String  htmlText = template.generate ( substitutionMap );

         String  htmlFileName
           = com.croftsoft.core.io.FileLib.pareExtension ( fileName )
           + ".html";

         File  htmlFile = new File ( dir, htmlFileName );

         System.out.println ( "Creating " + htmlFile );

         OutputStream  out = new FileOutputStream ( htmlFile );

         out.write ( htmlText.getBytes ( ) );

         out.close ( );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  Template ( String  templateText )
     //////////////////////////////////////////////////////////////////////
     {
       if ( templateText == null )
       {
         throw new IllegalArgumentException ( "null templateText" );
       }

       this.templateText = templateText;

       parse ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public String [ ]  getKeys ( )
     //////////////////////////////////////////////////////////////////////
     {
       return keys;
     }

     public String  generate ( Map  substitutionMap )
     //////////////////////////////////////////////////////////////////////
     {
       String  newText = templateText;

       for ( int  i = 0; i < keys.length; i++ )
       {
         String  key = keys [ i ];

         String  value = ( String ) substitutionMap.get ( key );

         if ( value != null )
         {
// this is weak:  case-sensitive and white-space sensitive

// also does not allow replaceable content between <SUBST> and </SUBST>

           newText = StringLib.replace ( newText,
             "<SUBST KEY=\"" + key + "\"></SUBST>", value );
         }
       }

       return newText;
     }

     //////////////////////////////////////////////////////////////////////
     // private methods
     //////////////////////////////////////////////////////////////////////

     /*********************************************************************
     * Finds all of the substitution keys in the template.
     *********************************************************************/
     private void  parse ( )
     //////////////////////////////////////////////////////////////////////
     {
       System.out.println ( "  Extracting keys" );

// this is weak:  case-sensitive and white-space sensitive

       String  lead = "<SUBST KEY=\"";

       Set  keySet = new HashSet ( );

       int  index0 = 0;

       while ( true )
       {
         index0 = templateText.indexOf ( lead, index0 );

         if ( index0 < 0 )
         {
           break;
         }

         index0 += lead.length ( );

         int  index1 = templateText.indexOf ( "\"", index0 );

         if ( index1 < 0 )
         {
           break;
         }

         String  key = templateText.substring ( index0, index1 );

         System.out.println ( "    key = \"" + key + "\"" );

         keySet.add ( key );
       }

       keys = ( String [ ] ) keySet.toArray ( new String [ ] { } );
     }

     private static String  extractTag (
       String  sdatText, String  tagName )
     //////////////////////////////////////////////////////////////////////
     {
// this is weak:  case-sensitive and white-space sensitive


       int  startIndex = sdatText.indexOf ( "<" + tagName + ">" );

       if ( startIndex < 0 )
       {
         return null;
       }

       startIndex += 2 + tagName.length ( );

       int  endIndex
         = sdatText.indexOf ( "</" + tagName + ">", startIndex );

       if ( endIndex < 0 )
       {
         return null;
       }

       return sdatText.substring ( startIndex, endIndex );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
