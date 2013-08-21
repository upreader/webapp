package com.upreader;

/**
 * Constants used throughout the platform
 */
public class UpreaderConstants {
    public static final String LOGIN_COOKIE_NAME = "upreaderAuthId";

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
    public static final String SESSION_NEWPROJECT_WIZ = "wizardData";


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
    public static final String CREATE_FOLDER="true";
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
    public static final String JAVA_UPLOAD="true";
    public static final String MAX_JAVA_SIZE_UPLOAD="200"; //Gb
}

