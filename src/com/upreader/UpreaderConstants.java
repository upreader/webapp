package com.upreader;

import com.google.common.collect.ImmutableMap;
import com.upreader.dto.SelectItem;

import java.util.*;

/**
 * Constants used throughout the platform
 */
public class UpreaderConstants {
    public Map constants=new HashMap();

    public static final String UPREADER_HOST = "http://www.upreader.com";
    public static final String UPREADER_SECURE_HOST = "http://www.upreader.com";
    public static final String UPREADER_CONTEXT = "/upreader";
    public static final String DATASOURCE_JNDI = "java:comp/env/jdbc/mysql";
    public static final String PERSISTANCEUNIT_JNDI = "java:comp/env/persistence";
    public static final String LOGIN_COOKIE_NAME = "upreaderAuthId";

    /**
     * Upreader House Rules By Rating
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
     * Project Types
     */
    public static String TYPE_FICTION = "1";
    public static String TYPE_NONFICTION = "2";

    /**
     * Status
     */
    public static String STATUS_FREE="1";
    public static String STATUS_WAITING="2";
    public static String STATUS_SHELVED="3";

    /**
     * Formats for upload
     */
     public static String PUBLIC_IMAGE = "1";
     public static String STORY = "2";
     public static String STORY_SAMPLE = "3";
     public static String COVER = "4";
     public static String PROOF_DOCUMENT = "5";
     public static String SERIAL_STORY = "6";

    /**
     * Project categories
     */
     public static String CATEGORY_NOVEL="1";
     public static String CATEGORY_FICTION="2";
     public static String CATEGORY_POETRY="3";
     public static String CATEGORY_CHILDREN_BOOKS="4";
     public static String CATEGORY_SELFHELP="5";
     public static String CATEGORY_BESTSELLER="6";

    /**
     * Project Genres
     */
    public static String GENRE_DRAMA="1";
        public static String SUBGENRE_DRAMA_COMINGOFAGE="1.1";
        public static String SUBGENRE_DRAMA_CHILDHOOD="1.2";
        public static String SUBGENRE_DRAMA_URBAN="1.3";
        public static String SUBGENRE_DRAMA_HISTORICAL="1.4";
    public static String GENRE_COMEDY="2";
        public static String SUBGENRE_COMEDY_SATIRE="2.1";
        public static String SUBGENRE_COMEDY_PARODY="2.2";
        public static String SUBGENRE_COMEDY_DARK="2.3";
        public static String SUBGENRE_COMEDY_ROMANTIC="2.4";
    public static String GENRE_ROMANCE="3";
    public static String GENRE_FAMILYSAGA="4";
    public static String GENRE_POLITICAL="5";
        public static String SUBGENRE_POLITICAL_CONSPIRACY="5.1";
        public static String SUBGENRE_POLITICAL_ANARCHIST="5.2";
        public static String SUBGENRE_POLITICAL_UTOPIA="5.3";
        public static String SUBGENRE_POLITICAL_DYSTOPIA="5.4";
    public static String GENRE_HISTORICAL="6";
    public static String GENRE_WAR="7";
    public static String GENRE_SPIRITUAL="8";
    public static String GENRE_ETHNIC_IMIGRANT="9";
    public static String GENRE_CHILDRENBOOK="10";
    public static String GENRE_PARANORMAL="11";
    public static String GENRE_ABSURD="12";
    public static String GENRE_SURREALISM="13";
    public static String GENRE_HORROR="14";
    public static String GENRE_THRILLER="15";
        public static String SUBGENRE_THRILLER_CRIME="15.1";
        public static String SUBGENRE_THRILLER_ACTION="15.2";
        public static String SUBGENRE_THRILLER_MYSTERY="15.3";
        public static String SUBGENRE_THRILLER_PSYCHOLOGICAL="15.4";
    public static String GENRE_ADVENTURE="16";
    public static String GENRE_SF="17";
        public static String SUBGENRE_SF_UTOPIA="17.1";
        public static String SUBGENRE_SF_DYSTOPIA="17.2";
        public static String SUBGENRE_SF_CYBERPUNK="17.3";
        public static String SUBGENRE_SF_STEAMPUNK="17.4";
        public static String SUBGENRE_SF_APOCALYPTIC="17.5";
        public static String SUBGENRE_SF_POSTAPOCALYPTIC="17.6";
        public static String SUBGENRE_SF_ANTHROPOLOGICAL="17.7";
        public static String SUBGENRE_SF_COMICAL="17.8";
    public static String GENRE_FANTASY="18";
        public static String SUBGENRE_FANTASY_DARK="18.1";
        public static String SUBGENRE_FANTASY_HISTORICAL="18.2";
        public static String SUBGENRE_FANTASY_MODERN="18.3";
        public static String SUBGENRE_FANTASY_SUPERHEROES="18.4";
        public static String SUBGENRE_FANTASY_VAMPIRES="18.4";
        public static String SUBGENRE_FANTASY_ZOMBIES="18.5";
        public static String SUBGENRE_FANTASY_MAGIC="18.6";
    public static String GENRE_EROTICA="19";
        public static String SUBGENRE_EROTICA_BDSM="19.1";
    public static String GENRE_LGBT="20";
    public static String GENRE_AVANTGARDE="21";
        public static String SUBGENRE_AVANTGARDE_EXPERIMENTAL="21.1";
    public static String GENRE_OTHER="22";
        public static String SUBGENRE_OTHER_BIZARRO="22.1";
        public static String SUBGENRE_OTHER_WEIRDO="22.2";
        public static String SUBGENRE_OTHER_ECLECTIC="22.3";
        public static String SUBGENRE_OTHER_PSYCHEDELIC="22.4";

    /**
     * Notification Groups keys
     */
     public static String PROJECT_NOTIFICATIONS  = "1";
     public static String PLATFORM_NOTIFICATIONS = "2";

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
        constants.put("TYPE_FICTION",TYPE_FICTION);
        constants.put("TYPE_NONFICTION",TYPE_NONFICTION);
        constants.put("STATUS_FREE",STATUS_FREE);
        constants.put("STATUS_WAITING",STATUS_WAITING);
        constants.put("STATUS_SHELVED",STATUS_SHELVED);
        constants.put("STORY", STORY);
        constants.put("STORY_SAMPLE", STORY_SAMPLE);
        constants.put("COVER", COVER);
        constants.put("PROOF_DOCUMENT", PROOF_DOCUMENT);
        constants.put("SERIAL_STORY", SERIAL_STORY);
        constants.put("CATEGORY_NOVEL",CATEGORY_NOVEL);
        constants.put("CATEGORY_FICTION",CATEGORY_FICTION);
        constants.put("CATEGORY_POETRY",CATEGORY_POETRY);
        constants.put("CATEGORY_CHILDREN_BOOKS",CATEGORY_CHILDREN_BOOKS);
        constants.put("CATEGORY_SELFHELP",CATEGORY_SELFHELP);
        constants.put("CATEGORY_BESTSELLER",CATEGORY_BESTSELLER);
        constants.put("RULES", UPREADER_HOUSE_RULES);
        constants.put("PROJECT_NOTIFICATIONS", PROJECT_NOTIFICATIONS);
        constants.put("PLATFORM_NOTIFICATIONS", PLATFORM_NOTIFICATIONS);
        constants.put("GENRE_DRAMA", GENRE_DRAMA);
        constants.put("GENRE_FAMILYSAGA", GENRE_FAMILYSAGA);
        constants.put("GENRE_COMEDY", GENRE_COMEDY);
        constants.put("GENRE_ROMANCE", GENRE_ROMANCE);
        constants.put("GENRE_POLITICAL",GENRE_POLITICAL);
        constants.put("GENRE_HISTORICAL",GENRE_HISTORICAL);
        constants.put("GENRE_WAR",GENRE_WAR);
        constants.put("GENRE_SPIRITUAL",GENRE_SPIRITUAL);
        constants.put("GENRE_ETHNIC_IMIGRANT",GENRE_ETHNIC_IMIGRANT);
        constants.put("GENRE_CHILDRENBOOK",GENRE_CHILDRENBOOK);
        constants.put("GENRE_PARANORMAL",GENRE_PARANORMAL);
        constants.put("GENRE_ABSURD",GENRE_ABSURD);
        constants.put("GENRE_SURREALISM",GENRE_SURREALISM);
        constants.put("GENRE_HORROR",GENRE_HORROR);
        constants.put("GENRE_THRILLER",GENRE_THRILLER);
        constants.put("GENRE_ADVENTURE",GENRE_ADVENTURE);
        constants.put("GENRE_SF",GENRE_SF);
        constants.put("GENRE_FANTASY",GENRE_FANTASY);
        constants.put("GENRE_EROTICA", GENRE_EROTICA);
        constants.put("GENRE_LGBT", GENRE_LGBT);
        constants.put("GENRE_AVANTGARDE",GENRE_AVANTGARDE);
        constants.put("GENRE_OTHER",GENRE_OTHER);
        return constants;
    }

    /*
     * Helper methods to be used with the constants.
     */
    public static String getLocalizedTypeResource(String type, Locale locale){
       ResourceBundle upreaderResources = UpreaderApplication.getInstance().getLocaleManager().getResources("com.upreader.i18n.UpreaderResources", locale);
       if(type.equals(UpreaderConstants.TYPE_FICTION)){
           return upreaderResources.getString("upreader.nomenclature.type.fiction");
       }
       if(type.equals(UpreaderConstants.TYPE_NONFICTION)){
           return upreaderResources.getString("upreader.nomenclature.type.nonfiction");
       }
       return "";
    }

    public static String getLocalizedFormatResource(String format, Locale locale){
        ResourceBundle upreaderResources = UpreaderApplication.getInstance().getLocaleManager().getResources("com.upreader.i18n.UpreaderResources", locale);
        if(format.equals(UpreaderConstants.STORY)){
            return upreaderResources.getString("upreader.nomenclature.format.story");
        }
        if(format.equals(UpreaderConstants.SERIAL_STORY)){
            return upreaderResources.getString("upreader.nomenclature.format.serialStory");
        }
        return "";
    }

    public static String getLocalizedCategoryResource(String category, Locale locale){
        ResourceBundle upreaderResources = UpreaderApplication.getInstance().getLocaleManager().getResources("com.upreader.i18n.UpreaderResources", locale);
        if(category.equals(UpreaderConstants.CATEGORY_NOVEL))
            return upreaderResources.getString("upreader.nomenclature.category.novel");
        if(category.equals(UpreaderConstants.CATEGORY_FICTION))
            return upreaderResources.getString("upreader.nomenclature.category.fiction");
        if(category.equals(UpreaderConstants.CATEGORY_POETRY))
            return upreaderResources.getString("upreader.nomenclature.category.poetry");
        if(category.equals(UpreaderConstants.CATEGORY_CHILDREN_BOOKS))
            return upreaderResources.getString("upreader.nomenclature.category.childrenBooks");
        if(category.equals(UpreaderConstants.CATEGORY_SELFHELP))
            return upreaderResources.getString("upreader.nomenclature.category.selfhelp");
        if(category.equals(UpreaderConstants.CATEGORY_BESTSELLER))
            return upreaderResources.getString("upreader.nomenclature.category.bestseller");
        return "";
    }

    public static String getLocalizedGenreResource(String genre, Locale locale){
        ResourceBundle upreaderResources = UpreaderApplication.getInstance().getLocaleManager().getResources("com.upreader.i18n.UpreaderResources", locale);
        if(genre.equals(UpreaderConstants.GENRE_DRAMA))
            return upreaderResources.getString("upreader.nomenclature.genre.drama");
        if(genre.equals(UpreaderConstants.GENRE_FAMILYSAGA))
            return upreaderResources.getString("upreader.nomenclature.genre.familySaga");
        if(genre.equals(UpreaderConstants.GENRE_COMEDY))
            return upreaderResources.getString("upreader.nomenclature.genre.comedy");
        if(genre.equals(UpreaderConstants.GENRE_ROMANCE))
            return upreaderResources.getString("upreader.nomenclature.genre.romance");
        if(genre.equals(UpreaderConstants.GENRE_POLITICAL))
            return upreaderResources.getString("upreader.nomenclature.genre.political");
        if(genre.equals(UpreaderConstants.GENRE_HISTORICAL))
            return upreaderResources.getString("upreader.nomenclature.genre.historical");
        if(genre.equals(UpreaderConstants.GENRE_WAR))
            return upreaderResources.getString("upreader.nomenclature.genre.war");
        if(genre.equals(UpreaderConstants.GENRE_SPIRITUAL))
            return upreaderResources.getString("upreader.nomenclature.genre.spiritual");
        if(genre.equals(UpreaderConstants.GENRE_ETHNIC_IMIGRANT))
            return upreaderResources.getString("upreader.nomenclature.genre.ethnicImigrant");
        if(genre.equals(UpreaderConstants.GENRE_CHILDRENBOOK))
            return upreaderResources.getString("upreader.nomenclature.genre.childrenBooks");
        if(genre.equals(UpreaderConstants.GENRE_PARANORMAL))
            return upreaderResources.getString("upreader.nomenclature.genre.paranormal");
        if(genre.equals(UpreaderConstants.GENRE_ABSURD))
            return upreaderResources.getString("upreader.nomenclature.genre.absurd");
        if(genre.equals(UpreaderConstants.GENRE_SURREALISM))
            return upreaderResources.getString("upreader.nomenclature.genre.surrealism");
        if(genre.equals(UpreaderConstants.GENRE_HORROR))
            return upreaderResources.getString("upreader.nomenclature.genre.horror");
        if(genre.equals(UpreaderConstants.GENRE_THRILLER))
            return upreaderResources.getString("upreader.nomenclature.genre.thriller");
        if(genre.equals(UpreaderConstants.GENRE_ADVENTURE))
            return upreaderResources.getString("upreader.nomenclature.genre.adventure");
        if(genre.equals(UpreaderConstants.GENRE_SF))
            return upreaderResources.getString("upreader.nomenclature.genre.sf");
        if(genre.equals(UpreaderConstants.GENRE_FANTASY))
            return upreaderResources.getString("upreader.nomenclature.genre.fantasy");
        if(genre.equals(UpreaderConstants.GENRE_EROTICA))
            return upreaderResources.getString("upreader.nomenclature.genre.erotica");
        if(genre.equals(UpreaderConstants.GENRE_LGBT))
            return upreaderResources.getString("upreader.nomenclature.genre.lgbt");
        if(genre.equals(UpreaderConstants.GENRE_AVANTGARDE))
            return upreaderResources.getString("upreader.nomenclature.genre.avantgarde");
        if(genre.equals(UpreaderConstants.GENRE_OTHER))
            return upreaderResources.getString("upreader.nomenclature.genre.other");
        return "";
    }

    public static List<SelectItem> getLocalizedSubGenresResource(String genre, Locale locale){
        ResourceBundle upreaderResources = UpreaderApplication.getInstance().getLocaleManager().getResources("com.upreader.i18n.UpreaderResources", locale);
        ArrayList<SelectItem> result = new ArrayList<SelectItem>();
        if(genre.equals(UpreaderConstants.GENRE_DRAMA)){
            result.add(new SelectItem(SUBGENRE_DRAMA_COMINGOFAGE, upreaderResources.getString("upreader.nomenclature.subgenre.drama.comingofage") ));
            result.add(new SelectItem(SUBGENRE_DRAMA_CHILDHOOD, upreaderResources.getString("upreader.nomenclature.subgenre.drama.childhood") ));
            result.add(new SelectItem(SUBGENRE_DRAMA_URBAN, upreaderResources.getString("upreader.nomenclature.subgenre.drama.urban") ));
            result.add(new SelectItem(SUBGENRE_DRAMA_HISTORICAL, upreaderResources.getString("upreader.nomenclature.subgenre.drama.historical") ));
            return result;
        }
        if(genre.equals(UpreaderConstants.GENRE_FAMILYSAGA))
            return result;
        if(genre.equals(UpreaderConstants.GENRE_COMEDY)){
            result.add(new SelectItem(SUBGENRE_COMEDY_SATIRE, upreaderResources.getString("upreader.nomenclature.subgenre.comedy.satire") ));
            result.add(new SelectItem(SUBGENRE_COMEDY_PARODY, upreaderResources.getString("upreader.nomenclature.subgenre.comedy.parody") ));
            result.add(new SelectItem(SUBGENRE_COMEDY_DARK, upreaderResources.getString("upreader.nomenclature.subgenre.comedy.dark") ));
            result.add(new SelectItem(SUBGENRE_COMEDY_ROMANTIC, upreaderResources.getString("upreader.nomenclature.subgenre.comedy.romantic") ));
            return result;
        }
        if(genre.equals(UpreaderConstants.GENRE_ROMANCE))
            return result;
        if(genre.equals(UpreaderConstants.GENRE_POLITICAL)){
            result.add(new SelectItem(SUBGENRE_POLITICAL_CONSPIRACY, upreaderResources.getString("upreader.nomenclature.subgenre.political.conspiracy") ));
            result.add(new SelectItem(SUBGENRE_POLITICAL_ANARCHIST, upreaderResources.getString("upreader.nomenclature.subgenre.political.anarchist") ));
            result.add(new SelectItem(SUBGENRE_POLITICAL_UTOPIA, upreaderResources.getString("upreader.nomenclature.subgenre.political.utopia") ));
            result.add(new SelectItem(SUBGENRE_POLITICAL_DYSTOPIA, upreaderResources.getString("upreader.nomenclature.subgenre.political.dystopia") ));
            return result;
        }
        if(genre.equals(UpreaderConstants.GENRE_HISTORICAL))
            return result;
        if(genre.equals(UpreaderConstants.GENRE_WAR))
            return result;
        if(genre.equals(UpreaderConstants.GENRE_SPIRITUAL))
            return result;
        if(genre.equals(UpreaderConstants.GENRE_ETHNIC_IMIGRANT))
            return result;
        if(genre.equals(UpreaderConstants.GENRE_CHILDRENBOOK))
            return result;
        if(genre.equals(UpreaderConstants.GENRE_PARANORMAL))
            return result;
        if(genre.equals(UpreaderConstants.GENRE_ABSURD))
            return result;
        if(genre.equals(UpreaderConstants.GENRE_SURREALISM))
            return result;
        if(genre.equals(UpreaderConstants.GENRE_HORROR))
            return result;
        if(genre.equals(UpreaderConstants.GENRE_THRILLER)){
            result.add(new SelectItem(SUBGENRE_THRILLER_CRIME, upreaderResources.getString("upreader.nomenclature.subgenre.thriller.crime") ));
            result.add(new SelectItem(SUBGENRE_THRILLER_ACTION, upreaderResources.getString("upreader.nomenclature.subgenre.thriller.action") ));
            result.add(new SelectItem(SUBGENRE_THRILLER_MYSTERY, upreaderResources.getString("upreader.nomenclature.subgenre.thriller.mystery") ));
            result.add(new SelectItem(SUBGENRE_THRILLER_PSYCHOLOGICAL, upreaderResources.getString("upreader.nomenclature.subgenre.thriller.psychological") ));
            return result;
        }
        if(genre.equals(UpreaderConstants.GENRE_ADVENTURE))
            return result;
        if(genre.equals(UpreaderConstants.GENRE_SF)){
            result.add(new SelectItem(SUBGENRE_SF_UTOPIA, upreaderResources.getString("upreader.nomenclature.subgenre.sf.utopia") ));
            result.add(new SelectItem(SUBGENRE_SF_DYSTOPIA, upreaderResources.getString("upreader.nomenclature.subgenre.sf.dystopia") ));
            result.add(new SelectItem(SUBGENRE_SF_CYBERPUNK, upreaderResources.getString("upreader.nomenclature.subgenre.sf.cyberpunk") ));
            result.add(new SelectItem(SUBGENRE_SF_STEAMPUNK, upreaderResources.getString("upreader.nomenclature.subgenre.sf.steampunk") ));
            result.add(new SelectItem(SUBGENRE_SF_APOCALYPTIC, upreaderResources.getString("upreader.nomenclature.subgenre.sf.apocalyptic") ));
            result.add(new SelectItem(SUBGENRE_SF_POSTAPOCALYPTIC, upreaderResources.getString("upreader.nomenclature.subgenre.sf.postapocalyptic") ));
            result.add(new SelectItem(SUBGENRE_SF_ANTHROPOLOGICAL, upreaderResources.getString("upreader.nomenclature.subgenre.sf.anthropological") ));
            result.add(new SelectItem(SUBGENRE_SF_COMICAL, upreaderResources.getString("upreader.nomenclature.subgenre.sf.comical") ));
            return result;
        }
        if(genre.equals(UpreaderConstants.GENRE_FANTASY)){
            result.add(new SelectItem(SUBGENRE_FANTASY_DARK, upreaderResources.getString("upreader.nomenclature.subgenre.fantasy.dark") ));
            result.add(new SelectItem(SUBGENRE_FANTASY_HISTORICAL, upreaderResources.getString("upreader.nomenclature.subgenre.fantasy.historical") ));
            result.add(new SelectItem(SUBGENRE_FANTASY_MODERN, upreaderResources.getString("upreader.nomenclature.subgenre.fantasy.modern") ));
            result.add(new SelectItem(SUBGENRE_FANTASY_SUPERHEROES, upreaderResources.getString("upreader.nomenclature.subgenre.fantasy.superheroes") ));
            result.add(new SelectItem(SUBGENRE_FANTASY_VAMPIRES, upreaderResources.getString("upreader.nomenclature.subgenre.fantasy.vampires") ));
            result.add(new SelectItem(SUBGENRE_FANTASY_ZOMBIES, upreaderResources.getString("upreader.nomenclature.subgenre.fantasy.zombies") ));
            result.add(new SelectItem(SUBGENRE_FANTASY_MAGIC, upreaderResources.getString("upreader.nomenclature.subgenre.fantasy.magic") ));
            return result;
        }
        if(genre.equals(UpreaderConstants.GENRE_EROTICA)){
            result.add(new SelectItem(SUBGENRE_EROTICA_BDSM, upreaderResources.getString("upreader.nomenclature.subgenre.erotica.bdsm") ));
            return result;
        }
        if(genre.equals(UpreaderConstants.GENRE_LGBT))
            return result;
        if(genre.equals(UpreaderConstants.GENRE_AVANTGARDE)){
            result.add(new SelectItem(SUBGENRE_AVANTGARDE_EXPERIMENTAL, upreaderResources.getString("upreader.nomenclature.subgenre.avantgarde.experimental") ));
            return result;
        }
        if(genre.equals(UpreaderConstants.GENRE_OTHER)){
            result.add(new SelectItem(SUBGENRE_OTHER_BIZARRO, upreaderResources.getString("upreader.nomenclature.subgenre.other.bizarro") ));
            result.add(new SelectItem(SUBGENRE_OTHER_WEIRDO, upreaderResources.getString("upreader.nomenclature.subgenre.other.weirdo") ));
            result.add(new SelectItem(SUBGENRE_OTHER_ECLECTIC, upreaderResources.getString("upreader.nomenclature.subgenre.other.eclectic") ));
            result.add(new SelectItem(SUBGENRE_OTHER_PSYCHEDELIC, upreaderResources.getString("upreader.nomenclature.subgenre.other.psychedelic") ));
            return result;
        }
        return result;
    }

}

