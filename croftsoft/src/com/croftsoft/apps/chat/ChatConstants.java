     package com.croftsoft.apps.chat;

     import com.croftsoft.core.CroftSoftConstants;
     import com.croftsoft.core.math.MathConstants;

     /*********************************************************************
     * Chat constants.
     *
     * @version
     *   2003-06-25
     * @since
     *   2003-06-17
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public interface  ChatConstants
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public static final String  VERSION = "2003-06-25";

     public static final String  TITLE   = "CroftSoft Chat";

     public static final String  INFO
       = "\n" + TITLE
       + "\n" + CroftSoftConstants.COPYRIGHT
       + "\n" + CroftSoftConstants.HOME_PAGE
       + "\n" + "Version " + VERSION
       + "\n" + CroftSoftConstants.DEFAULT_LICENSE
       + "\n" + "Programming..:  David Wallace Croft"
       + "\n" + "Graphics.....:  Shannon Kristine Croft"
       + "\n";

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static final double  RADIUS = 20.0;

     public static final double  SPEED  = 85.0;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static final String  MEDIA_DIR = "apps/chat/avatar/";

     public static final String  AVATAR_IMAGE_FILENAME_EXTENSION = ".png";

     public static final String [ ]  AVATAR_TYPES = {
       "Cleric",
       "Thief",
       "Warrior",
       "Wizard" };

     public static final int     DEFAULT_AVATAR_TYPE_INDEX = 3;

     public static final String  DEFAULT_AVATAR_TYPE
       = AVATAR_TYPES [ DEFAULT_AVATAR_TYPE_INDEX ];

     public static final double  DEFAULT_AVATAR_X    = RADIUS;

     public static final double  DEFAULT_AVATAR_Y    = RADIUS;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public static final long  QUEUE_PULL_TIMEOUT
       = 30 * MathConstants.MILLISECONDS_PER_SECOND;

     public static final long  REQUEST_TIMEOUT
       = 2 * QUEUE_PULL_TIMEOUT;

     public static final long  USER_TIMEOUT
       = 2 * QUEUE_PULL_TIMEOUT;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
