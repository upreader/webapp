package com.upreader;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Constants used throughout the platform
 */
public class UpreaderConstants {
    public Map constants=new HashMap();

    public static final String UPREADER_HOST = "http://www.upreader.com";
    public static final String UPREADER_SECURE_HOST = "https://www.upreader.com";
    public static final String UPREADER_CONTEXT = "/upreader";
    public static final String DATASOURCE_JNDI = "java:comp/env/jdbc/mysql";
    public static final String PERSISTANCEUNIT_JNDI = "java:comp/env/persistence";
    public static final String LOGIN_COOKIE_NAME = "upreaderAuthId";

    /**
     * Upreader House Rules
     */
    public static final ImmutableMap<String, ImmutableMap<String, String>> UPREADER_HOUSE_RULES =
            ImmutableMap.<String, ImmutableMap<String, String>>builder()
                .put("1", ImmutableMap.<String, String>builder()
                                .put("SALE_ESTIMATE_PER_YEAR","500")
                                .put("MIN_BOOK_PRICE","1.99")
                                .put("MAX_BOOK_PRICE","3.99")
                                .put("MIN_YEARS_SELLING_RIGHTS_TO_PLATFORM","6") //min years given to platform
                                .put("MIN_PERCENT_ROYALTIES_TO_PLATFORM","50")
                                .put("MIN_SHARES","1")
                                .put("MAX_SHARES","25")
                                .put("SHARE_PRICE","1")
                                .build())
                .put("2", ImmutableMap.<String, String>builder()
                                .put("SALE_ESTIMATE_PER_YEAR","2000")
                                .put("MIN_BOOK_PRICE","1.99")
                                .put("MAX_BOOK_PRICE","5.99")
                                .put("MIN_YEARS_SELLING_RIGHTS_TO_PLATFORM","5") //min years given to platform
                                .put("MIN_PERCENT_ROYALTIES_TO_PLATFORM","40")
                                .put("MIN_SHARES","2")
                                .put("MAX_SHARES","30")
                                .put("SHARE_PRICE","2")
                                .build())
                .put("3", ImmutableMap.<String, String>builder()
                                .put("SALE_ESTIMATE_PER_YEAR","5000")
                                .put("MIN_BOOK_PRICE","1.99")
                                .put("MAX_BOOK_PRICE","6.99")
                                .put("MIN_YEARS_SELLING_RIGHTS_TO_PLATFORM","4") //min years given to platform
                                .put("MIN_PERCENT_ROYALTIES_TO_PLATFORM","35")
                                .put("MIN_SHARES","3")
                                .put("MAX_SHARES","40")
                                .put("SHARE_PRICE","3")
                                .build())
                .put("4", ImmutableMap.<String, String>builder()
                                .put("SALE_ESTIMATE_PER_YEAR","10000")
                                .put("MIN_BOOK_PRICE","1.99")
                                .put("MAX_BOOK_PRICE","7.99")
                                .put("MIN_YEARS_SELLING_RIGHTS_TO_PLATFORM","3") //min years given to platform
                                .put("MIN_PERCENT_ROYALTIES_TO_PLATFORM","30")
                                .put("MIN_SHARES","4")
                                .put("MAX_SHARES","50")
                                .put("SHARE_PRICE","4")
                                .build())
                .put("5", ImmutableMap.<String, String>builder()
                                .put("SALE_ESTIMATE_PER_YEAR","20000")
                                .put("MIN_BOOK_PRICE","1.99")
                                .put("MAX_BOOK_PRICE","8.99")
                                .put("MIN_YEARS_SELLING_RIGHTS_TO_PLATFORM","2") //min years given to platform
                                .put("MIN_PERCENT_ROYALTIES_TO_PLATFORM","25")
                                .put("MIN_SHARES","5")
                                .put("MAX_SHARES","60")
                                .put("SHARE_PRICE","5")
                                .build())
                .put("6", ImmutableMap.<String, String>builder()
                                .put("SALE_ESTIMATE_PER_YEAR","40000")
                                .put("MIN_BOOK_PRICE","1.99")
                                .put("MAX_BOOK_PRICE","9.99")
                                .put("MIN_YEARS_SELLING_RIGHTS_TO_PLATFORM","1") //min years given to platform
                                .put("MIN_PERCENT_ROYALTIES_TO_PLATFORM","10")
                                .put("MIN_SHARES","6")
                                .put("MAX_SHARES","70")
                                .put("SHARE_PRICE","6")
                                .build())
                .build();

    /**
     * User roles
     */
	public static final String ROLE_PROSPECTOR = "prospector";
	public static final String ROLE_UPREADER = "upreader";
	public static final String ROLE_AUTHOR = "author";
    public static final String ROLE_PUBLISHER = "publisher";
	public static final String ROLE_EDITOR = "editor";
	public static final String ROLE_ADMIN = "admin";

    /**
     * Keys for objects stored in HTTP session
     */
	public static final String SESSION_USER = "user";
	public static final String SESSION_NEWPROJECT = "_newproject_";
    public static final String SESSION_NEWPROJECT_WIZ = "wizardData";  //DTO used on addProjectWizard Steps
    public static final String SESSION_MONITOR_BOARD_DATA = "mbData";

    /**
     * File types for upload
     */
     public static String PUBLIC_IMAGE = "1";
     public static String STORY = "2";
     public static String STORY_SAMPLE = "3";
     public static String COVER = "4";
     public static String PROOF_DOCUMENT = "5";
     public static String SERIAL_STORY = "6";

    /**
     * Options used during project creation
     */

    /**
     * Configuration used for the tinyMCE File Manager plugin
     */
    //**********************
    //Path configuration
    //**********************
    //public static final String BASE_URL="http://www.upreader.com"; //url base of site if you want only relative url leave empty
    //public static final String UPLOAD_DIR = "/upload/"; // path from base_url to upload base dir
    //public static final String CURRENT_PATH = "/upload/"; // relative path from filemanager folder to upload files folder
    public static final String IMAGES_PATH_INSIDE_UPREADER_BUCHET="images/";
    public static final String THUMBS_PATH_INSIDE_AN_IMAGES_FOLDER="thumbs/";

    public static final String MAX_UPLOAD_SIZE="100"; //Mb
    public static final String SHOW_FOLDER_SIZE="true";
    public static final String SHOW_SORTING_BAR="true";

    //**********************
    //Image config
    //**********************
    //parameter passed on editor
    public static final String IMAGE_DIMENSSION_PASSING="1";
    //set max width pixel or the max height pixel for all images
    //If you set dimension limit, automatically the images that exceed this limit are convert to limit, instead
    //if the images are lower the dimension is maintained
    //if you don't have limit set both to 0
    public static final String IMAGE_MAX_WIDTH="0";
    public static final String IMAGE_MAX_HEIGHT="0";

    //Automatic resizing //
    //If you set true $image_resizing the script convert all images uploaded in image_width x image_height resolution
    //If you set width or height to 0 the script calcolate automatically the other size
    public static final String IMAGE_RESIZING="false";
    public static final String IMAGE_WIDTH="600";
    public static final String IMAGE_HEIGHT="0";

    //******************
    //Default layout setting
    //
    // 0 => boxes
    // 1 => list (1 column)
    // 2 => columns list (multiple columns depending on the width of the page)
    //
    // YOU CAN ALSO PASS THIS PARAMETERS USING SESSION VAR => $_SESSION["VIEW"]=
    //
    //******************
    public static final String DEFAULT_VIEW="0";

    //******************
    //Permits config
    //******************
    public static final String DELETE_FILE="true";
    public static final String EDIT_FILE="false";
    public static final String CREATE_FOLDER="false";
    public static final String DELETE_FOLDER="true";
    public static final String UPLOAD_FILES="true";
    public static final String RENAME_FILES="true";
    public static final String RENAME_FOLDERS="true";

    //**********************
    //Allowed extensions
    //**********************
    public static final String EXT_IMG   = "'jpg', 'jpeg', 'png', 'gif', 'bmp', 'tiff'"; //Images
    public static final String EXT_FILE  = "'doc', 'docx', 'pdf', 'xls', 'xlsx', 'txt', 'csv','html','psd','sql','log','fla','xml','ade','adp','ppt','pptx'"; //Files
    public static final String EXT_VIDEO = "'mov', 'mpeg', 'mp4', 'avi', 'mpg','wma'"; //Videos
    public static final String EXT_MUSIC = "'mp3', 'm4a', 'ac3', 'aiff', 'mid'"; //Music
    public static final String EXT_MISC  = "'zip', 'rar','gzip'"; //Archives
    public static final String EXT_BOOKS  = "'pdf', 'doc','docx'"; //Documents

    public static final String EXT=EXT_IMG + "," + EXT_FILE + "," + EXT_VIDEO + "," + EXT_MUSIC + "," + EXT_MISC; //allowed extensions

    //**********************
    // Hidden files and folders
    //**********************
    // set the names of any folders you want hidden (eg "hidden_folder1", "hidden_folder2" ) Remember all folders with these names will be hidden (you can set any exceptions in .config file)
    public static final String HIDDEN_FOLDERS = "";
    // set the names of any files you want hidden. Remember these names will be hidden in all folders (eg "this_document.pdf", "that_image.jpg" )
    public static final String HIDDEN_FILES = "";

    /*******************
     * JAVA upload
     *******************/
    public static final String JAVA_UPLOAD="false";
    public static final String MAX_JAVA_SIZE_UPLOAD="200"; //Gb

    public Map getValues(){
        constants.put("PUBLIC_IMAGE", PUBLIC_IMAGE);
        constants.put("STORY", STORY);
        constants.put("STORY_SAMPLE", STORY_SAMPLE);
        constants.put("COVER", COVER);
        constants.put("PROOF_DOCUMENT", PROOF_DOCUMENT);
        constants.put("SERIAL_STORY", SERIAL_STORY);
        constants.put("RULES", UPREADER_HOUSE_RULES);
        return constants;
    }
}

