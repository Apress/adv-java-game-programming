     package com.croftsoft.agoracast.c2p;

     import java.awt.Color;
     import java.awt.Font;

     import com.croftsoft.core.lang.Pair;

     /*********************************************************************
     * Static constants for Agoracast C2P.
     *
     * <p />
     *
     * @version
     *   2002-01-29
     * @since
     *   2001-07-22
     * @author
     *   <a href="http://croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  AgoracastConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  DOCUMENTATION_URL
       = "http://croftsoft.com/agoracast/doc/";

     //

     public static final String  LICENSE_FILENAME = "/license.txt";

     public static final String  LICENSE_FRAME_TITLE
       = "Agoracast License Agreement";

     public static final String  LICENSE_FRAME_ICON_IMAGE_NAME
       = "/icon.gif";

     public static final String  SPLASH_IMAGE_NAME
       = "/splash.jpg";

     //

     public static final String  FRAME_TITLE
       = "Agoracast";

     public static final String  FRAME_ICON_IMAGE_NAME
       = LICENSE_FRAME_ICON_IMAGE_NAME;

     //

     public static final String  CONFIG_FILENAME   = ".agoracast_config";

     public static final String  DATABASE_FILENAME = ".agoracast_database";

     public static final String  NEWSRC_FILENAME   = ".agoracast_newsrc";

     //

     public static final String  CONFIG_EMAIL     = "E-Mail Address";

     public static final String  CONFIG_SERVER    = "News Server";

     public static final String  CONFIG_USERNAME  = "Username";

     public static final String  CONFIG_NEWSGROUP = "Newsgroup";

     public static final String  DEFAULT_NEWSGROUP = "alt.marketplace";

     //

     public static final String  CONFIG_HELP_EMAIL
       = "<h3>" + CONFIG_EMAIL + "</h3>"
       + "<p>"
       + "All newsgroup messages need an associated e-mail address.  "
       + "Enter your e-mail address in this field in order to post "
       + "messages."
       + "</p>"
       + "<p>"
       + "You will not be able to post messages until you enter an "
       + "e-mail address."
       + "</p>";

     public static final String  CONFIG_HELP_SERVER
       = "<h3>" + CONFIG_SERVER + "</h3>"
       + "<p>"
       + "Provide the name of your local newsgroup server, "
       + "usually provided to you by your Internet Service Provider"
       + " (ISP) or campus network administrator."
       + "</p>"
       + "<p>"
       + "You will not be able to browse or post messages until you"
       + " select a server."
       + "</p>";

     public static final String  CONFIG_HELP_USERNAME
       = "<h3>" + CONFIG_USERNAME + "</h3>"
       + "<p>"
       + "Some newsgroup servers require a username and password.  "
       + "If your newsgroup server requires authentication, Agoracast "
       + "will prompt you for your password the first time you connect "
       + "to a server."
       + "</p>"
       + "<p>"
       + "Not all newsgroup servers require an authenticated account "
       + "in order to use them.  Querying a search engine for "
       + "\"open newsgroup servers\" will yield lists of publicly "
       + "accessible newsgroup servers."
       + "</p>";

     public static final String  CONFIG_HELP_NEWSGROUP
       = "<h3>" + CONFIG_NEWSGROUP + "</h3>"
       + "<p>"
       + "This is the topic you will use for your uploaded and "
       + "downloaded messages.  "
       + "You will generally want to leave this at the default value "
       + "of \"alt.marketplace\", a well-established newsgroup that is "
       + "carried on most newsgroup servers.  You might want to change "
       + "this, however, if alt.marketplace is not carried on your "
       + "newsgroup server."
       + "</p>"
       + "<p>"
       + "You might also you wish to change this if you want to use a "
       + "more local, focused, or private newsgroup in order to "
       + "participate in a more local, focused, or private electronic "
       + "marketplace.  If you are the first one to select a specialized "
       + "newsgroup instead of the default, you need to let other Agoracast "
       + "users know that you are posting and scanning for messages there.  "
       + "As there is currently no automated means of advertising these "
       + "specialty newsgroups, it is recommended that you use the common "
       + "default newsgroup instead."
       + "</p>";

     public static final String  CONFIG_HELP_TEXT
       = "<h2>Configuration</h2>"
       + "The configuration panel is used to edit your newsgroup "
       + "server connection settings.  These settings are changed "
       + "when you click on the \"Update\" button."
       + "Clicking on \"Restore\" will restore the previously saved "
       + "values as read from a local file."
       + CONFIG_HELP_SERVER
       + CONFIG_HELP_EMAIL
       + CONFIG_HELP_USERNAME
       + CONFIG_HELP_NEWSGROUP;

     //

     public static final int  TAB_INDEX_BROWSE   = 2;

     public static final int  TAB_INDEX_DEFAULTS = 3;

     public static final int  TAB_INDEX_POST     = 4;

     public static final int  TAB_INDEX_LOG      = 5;

     //

     public static final String  FIELD_NAME_AGE      = "age";

     public static final String  FIELD_NAME_ASKING   = "asking";

     public static final String  FIELD_NAME_BIDDING  = "bidding";

     public static final String  FIELD_NAME_CATEGORY = "category";

     public static final String  FIELD_NAME_CITY     = "city";

     public static final String  FIELD_NAME_COUNTRY  = "country";

     public static final String  FIELD_NAME_EMAIL    = "email";

     public static final String  FIELD_NAME_GENDER   = "gender";

     public static final String  FIELD_NAME_ISBN     = "isbn";

     public static final String  FIELD_NAME_MANUFACTURER
       = "manufacturer";

     public static final String  FIELD_NAME_MODEL
       = "model";

     public static final String  FIELD_NAME_NAME     = "name";

     public static final String  FIELD_NAME_PHONE    = "phone";

     public static final String  FIELD_NAME_RACE     = "race";

     public static final String  FIELD_NAME_RATE     = "rate";

     public static final String  FIELD_NAME_RELIGION = "religion";

     public static final String  FIELD_NAME_RENT     = "rent";

     public static final String  FIELD_NAME_STATE    = "state";

     public static final String  FIELD_NAME_TITLE    = "title";

     public static final String  FIELD_NAME_URL      = "url";

     public static final String  FIELD_NAME_YEAR
       = "year";

     public static final String  FIELD_NAME_ZIPCODE  = "zipcode";

     //

     public static final AgoracastCategory  CATEGORY_ALL
       = new AgoracastCategory (
         "-- ALL --",
         "All categories.",
         new String [ ] {
           FIELD_NAME_ASKING,
           FIELD_NAME_BIDDING,
           FIELD_NAME_NAME,
           FIELD_NAME_ZIPCODE },
         new String [ ] {
           FIELD_NAME_ASKING,
           FIELD_NAME_BIDDING,
           FIELD_NAME_CATEGORY,
           FIELD_NAME_NAME,
           FIELD_NAME_ZIPCODE } );

     public static final AgoracastCategory [ ]  CATEGORIES
       = new AgoracastCategory [ ] {
       new AgoracastCategory (
         "book-for-sale",
         "Book for sale.",
         new String [ ] {
           FIELD_NAME_ASKING,
           FIELD_NAME_EMAIL,
           FIELD_NAME_ISBN,
           FIELD_NAME_TITLE,
           FIELD_NAME_ZIPCODE },
         new String [ ] {
           FIELD_NAME_ASKING,
           FIELD_NAME_ISBN,
           FIELD_NAME_TITLE,
           FIELD_NAME_ZIPCODE } ),
       new AgoracastCategory (
         "car-for-sale",
         "Car for sale.",
         new String [ ] {
           FIELD_NAME_MANUFACTURER,
           FIELD_NAME_MODEL,
           FIELD_NAME_YEAR,
           FIELD_NAME_ASKING,
           FIELD_NAME_EMAIL,
           FIELD_NAME_CITY,
           FIELD_NAME_STATE,
           FIELD_NAME_COUNTRY,
           FIELD_NAME_ZIPCODE },
         new String [ ] {
           FIELD_NAME_MANUFACTURER,
           FIELD_NAME_MODEL,
           FIELD_NAME_YEAR,
           FIELD_NAME_ASKING,
           FIELD_NAME_CITY,
           FIELD_NAME_STATE,
           FIELD_NAME_COUNTRY,
           FIELD_NAME_ZIPCODE } ),
       new AgoracastCategory (
         "carpool",
         "Ride sharing.",
         new String [ ] {
           FIELD_NAME_EMAIL,
           FIELD_NAME_CITY,
           FIELD_NAME_STATE,
           FIELD_NAME_COUNTRY,
           FIELD_NAME_ZIPCODE },
         new String [ ] {
           FIELD_NAME_EMAIL,
           FIELD_NAME_CITY,
           FIELD_NAME_STATE,
           FIELD_NAME_COUNTRY,
           FIELD_NAME_ZIPCODE } ),
       new AgoracastCategory (
         "domain-name",
         "Domain name for sale.",
         new String [ ] {
           FIELD_NAME_NAME,
           FIELD_NAME_ASKING,
           FIELD_NAME_EMAIL },
         new String [ ] {
           FIELD_NAME_NAME,
           FIELD_NAME_ASKING,
           FIELD_NAME_EMAIL } ),
       new AgoracastCategory (
         "firearm-for-sale",
         "Firearm for sale.",
         new String [ ] {
           FIELD_NAME_ASKING,
           FIELD_NAME_EMAIL,
           FIELD_NAME_NAME,
           FIELD_NAME_ZIPCODE },
         new String [ ] {
           FIELD_NAME_ASKING,
           FIELD_NAME_NAME,
           FIELD_NAME_ZIPCODE } ),
       new AgoracastCategory (
         "for-sale",
         "Miscellaneous item for sale.",
         new String [ ] {
           FIELD_NAME_NAME,
           FIELD_NAME_ASKING,
           FIELD_NAME_EMAIL },
         new String [ ] {
           FIELD_NAME_NAME,
           FIELD_NAME_ASKING,
           FIELD_NAME_EMAIL } ),
       new AgoracastCategory (
         "job-offered",
         "Job openings.",
         new String [ ] {
           FIELD_NAME_TITLE,
           FIELD_NAME_BIDDING,
           FIELD_NAME_RATE,
           FIELD_NAME_EMAIL,
           FIELD_NAME_CITY,
           FIELD_NAME_STATE,
           FIELD_NAME_COUNTRY,
           FIELD_NAME_ZIPCODE },
         new String [ ] {
           FIELD_NAME_TITLE,
           FIELD_NAME_BIDDING,
           FIELD_NAME_RATE,
           FIELD_NAME_CITY,
           FIELD_NAME_STATE } ),
       new AgoracastCategory (
         "job-wanted",
         "Job seekers.",
         new String [ ] {
           FIELD_NAME_ASKING,
           FIELD_NAME_CITY,
           FIELD_NAME_COUNTRY,
           FIELD_NAME_EMAIL,
           FIELD_NAME_NAME,
           FIELD_NAME_PHONE,
           FIELD_NAME_RATE,
           FIELD_NAME_STATE,
           FIELD_NAME_TITLE,
           FIELD_NAME_ZIPCODE },
         new String [ ] {
           FIELD_NAME_ASKING,
           FIELD_NAME_CITY,
           FIELD_NAME_COUNTRY,
           FIELD_NAME_PHONE,
           FIELD_NAME_RATE,
           FIELD_NAME_STATE,
           FIELD_NAME_TITLE } ),
       new AgoracastCategory (
         "house-for-rent",
         "House for rent.",
         new String [ ] {
           FIELD_NAME_RENT,
           FIELD_NAME_EMAIL,
           FIELD_NAME_CITY,
           FIELD_NAME_STATE,
           FIELD_NAME_COUNTRY,
           FIELD_NAME_ZIPCODE },
         new String [ ] {
           FIELD_NAME_RENT,
           FIELD_NAME_CITY,
           FIELD_NAME_STATE,
           FIELD_NAME_COUNTRY,
           FIELD_NAME_ZIPCODE } ),
       new AgoracastCategory (
         "house-for-sale",
         "House for sale.",
         new String [ ] {
           FIELD_NAME_ASKING,
           FIELD_NAME_EMAIL,
           FIELD_NAME_CITY,
           FIELD_NAME_STATE,
           FIELD_NAME_COUNTRY,
           FIELD_NAME_ZIPCODE },
         new String [ ] {
           FIELD_NAME_ASKING,
           FIELD_NAME_CITY,
           FIELD_NAME_STATE,
           FIELD_NAME_COUNTRY,
           FIELD_NAME_ZIPCODE } ),
       new AgoracastCategory (
         "personal-ad",
         "Personal ad.",
         new String [ ] {
           FIELD_NAME_NAME,
           FIELD_NAME_AGE,
           FIELD_NAME_GENDER,
           FIELD_NAME_RACE,
           FIELD_NAME_RELIGION,
           FIELD_NAME_EMAIL,
           FIELD_NAME_CITY,
           FIELD_NAME_STATE,
           FIELD_NAME_COUNTRY,
           FIELD_NAME_ZIPCODE },
         new String [ ] {
           FIELD_NAME_AGE,
           FIELD_NAME_GENDER,
           FIELD_NAME_RACE,
           FIELD_NAME_RELIGION,
           FIELD_NAME_CITY,
           FIELD_NAME_STATE,
           FIELD_NAME_COUNTRY,
           FIELD_NAME_ZIPCODE } ),
       new AgoracastCategory (
         "wanted",
         "Miscellaneous item wanted.",
         new String [ ] {
           FIELD_NAME_NAME,
           FIELD_NAME_BIDDING,
           FIELD_NAME_EMAIL },
         new String [ ] {
           FIELD_NAME_NAME,
           FIELD_NAME_BIDDING,
           FIELD_NAME_EMAIL } ) };

     public static final String [ ]  CHOICES_CATEGORY
       = AgoracastCategory.getNames ( CATEGORIES );

     public static final String [ ]  CHOICES_GENDER = {
       "male",
       "female",
       "other" };

     public static final AgoracastField [ ]  DEFAULT_FIELDS
       = new AgoracastField [ ] {
         new AgoracastField (
           FIELD_NAME_AGE,      // name
           null,                // value
           AgoracastField.TYPE_NUMBER,
           false,               // isReverse
           null,                // choices
           "Age in years" ),    // semantic
         new AgoracastField (
           FIELD_NAME_ASKING,   // name
           null,                // value
           AgoracastField.TYPE_NUMBER,
           false,               // isReverse
           null,                // choices
           "Asking price ($)" ), // semantic
         new AgoracastField (
           FIELD_NAME_BIDDING,  // name
           null,                // value
           AgoracastField.TYPE_NUMBER,
           false,               // isReverse
           null,                // choices
           "Bidding price ($)" ), // semantic
         new AgoracastField (
           FIELD_NAME_CATEGORY, // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           CHOICES_CATEGORY,    // choices
           "message type" ),    // semantic
         new AgoracastField (
           FIELD_NAME_CITY,     // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           null,                // choices
           "City" ),            // semantic
         new AgoracastField (
           FIELD_NAME_COUNTRY,  // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           null,                // choices
           "Country (US=United States)" ), // semantic
         new AgoracastField (
           FIELD_NAME_EMAIL,    // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           null,                // choices
           "contact e-mail" ),  // semantic
         new AgoracastField (
           FIELD_NAME_GENDER,   // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           CHOICES_GENDER,      // choices
           "Gender (sex)" ),    // semantic
         new AgoracastField (
           FIELD_NAME_ISBN,     // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           null,                // choices
           "International Standard Book Number" ),    // semantic
         new AgoracastField (
           FIELD_NAME_MANUFACTURER, // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           null,                // choices
           "Manufacturer" ),    // semantic
         new AgoracastField (
           FIELD_NAME_MODEL,    // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           null,                // choices
           "Model" ),           // semantic
         new AgoracastField (
           FIELD_NAME_NAME,     // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           null,                // choices
           "Product or service name" ), // semantic
         new AgoracastField (
           FIELD_NAME_PHONE,    // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           null,                // choices
           "contact phone number" ),  // semantic
         new AgoracastField (
           FIELD_NAME_RACE,     // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           null,                // choices
           "Race" ), // semantic
         new AgoracastField (
           FIELD_NAME_RATE,   // name
           null,                // value
           AgoracastField.TYPE_NUMBER,
           false,               // isReverse
           null,                // choices
           "Hourly rate ($)" ), // semantic
         new AgoracastField (
           FIELD_NAME_RELIGION, // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           null,                // choices
           "Religion" ), // semantic
         new AgoracastField (
           FIELD_NAME_RENT,     // name
           null,                // value
           AgoracastField.TYPE_NUMBER,
           false,               // isReverse
           null,                // choices
           "monthly rent" ),  // semantic
         new AgoracastField (
           FIELD_NAME_STATE,    // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           null,                // choices
           "State (TX=Texas)" ), // semantic
         new AgoracastField (
           FIELD_NAME_TITLE,    // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           null,                // choices
           "Title" ),           // semantic
         new AgoracastField (
           FIELD_NAME_URL,      // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           null,                // choices
           "URL for more info" ), // semantic
         new AgoracastField (
           FIELD_NAME_YEAR,     // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           null,                // choices
           "Year" ),            // semantic
         new AgoracastField (
           FIELD_NAME_ZIPCODE,  // name
           null,                // value
           AgoracastField.TYPE_STRING,
           false,               // isReverse
           null,                // choices
           "Zip or Postal Code" ) }; // semantic

     //

     public static final String  DOCUMENTATION_FILENAME = "/doc.html";

     //
 
     public static final String  SUBJECT_PREFIX = "[Agoracast]";

     public static final String  SUBJECT_PREFIX_LOWER_CASE
       = SUBJECT_PREFIX.toLowerCase ( );

     public static final String  FOOTER
       = "--" + "\r\n"
       + "Agoracast:  "
       + "Building the World Wide Exchange (http://agoracast.com/)";

     //

     public static final int  LOG_TEXT_LENGTH_MAX = 10000;

     public static final String  IDENTIFIER_DIALOG_TITLE = FRAME_TITLE;

     //

     public static final Font  LOG_FONT
       = new Font ( "Monospaced", Font.PLAIN, 12 );

     //

     public static final String  DESCRIBE_TEXT
       = "Description (optional):";

     public static final String  TABLE_TEXT
       = "Click on a column name to sort.  "
       + "Double-click on a row to view the message.";

     //

     public static final int  DOWNLOAD_MAX = 500;

     //

     public static final Color  SELECTED_FOREGROUND_COLOR = Color.black;

     public static final Color  SELECTED_BACKGROUND_COLOR
       = new Color ( 204, 255, 204 );

     public static final Color  ODD_FOREGROUND_COLOR      = Color.black;

     public static final Color  ODD_BACKGROUND_COLOR     
       = new Color ( 230, 230, 190 );

     public static final Color  EVEN_FOREGROUND_COLOR     = Color.black;

     public static final Color  EVEN_BACKGROUND_COLOR     = Color.white;

     //

     public static final Color  DEFAULT_PANEL_BACKGROUND_COLOR
       = null;

     public static final Color  DEFAULT_TEXTFIELD_BACKGROUND_COLOR
       = null;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }