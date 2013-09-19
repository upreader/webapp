package com.upreader.dto;

import com.upreader.UpreaderConstants;
import com.upreader.context.Context;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddProjectWizardDTO {

    /*
     * Variables used in all steps
     */
    private String username;
    private Integer currentStep;

    //LOVs
    private ArrayList<SelectItem> story_Format_LOV;
    private ArrayList<SelectItem> story_Type_LOV;
    private ArrayList<SelectItem> story_Genre_LOV;
    private ArrayList<SelectItem> story_Drama_SubGenre_LOV;
    private ArrayList<SelectItem> story_Comedy_SubGenre_LOV;
    private ArrayList<SelectItem> story_Political_SubGenre_LOV;
    private ArrayList<SelectItem> story_Thriller_SubGenre_LOV;
    private ArrayList<SelectItem> story_SF_SubGenre_LOV;
    private ArrayList<SelectItem> story_Fantasy_SubGenre_LOV;
    private ArrayList<SelectItem> story_Erotica_SubGenre_LOV;
    private ArrayList<SelectItem> story_AvantGarde_SubGenre_LOV;
    private ArrayList<SelectItem> story_Other_SubGenre_LOV;
    private ArrayList<SelectItem> story_No_SubGenre_LOV;
    private ArrayList<SelectItem> story_SelectSubgenre_LOV;

    private ArrayList<SelectItem> story_Category_LOV;

    /*
     * Variables used in step 1
     */
    private boolean step1_agreeTermsAndConditions;

    /*
     * Variables used in step 2
     */
    private String step2_storyTitle;
    private SelectItem step2_storyFormat;
    private SelectItem step2_storyType;
    private SelectItem step2_storyGenre;
    private SelectItem step2_storySubGenre;
    private SelectItem step2_storyCategory;
    private String step2_storyPitch;
    private String step2_storySynopsis;
    private AmazonS3FileDetails step2_uploadedStory;
    private AmazonS3FileDetails step2_uploadedSampleStory;
    private String[] step2_tags;
    private String[] step2_backstories;

    private Integer step2_storyChapters;
    private Integer step2_aproxChapterWordCount;
    private Integer step2_delayBetweenChapterUpdates;

    /*
     * Variables used in step 3
     */
    private Integer step3_yearsOfSellingRightsToPlatform;
    private Integer step3_sellEstimateUnitsPerYear;
    private BigDecimal step3_ebookPrice;
    private Integer step3_percentRoyaltiesToPlatform;
    private Integer step3_numberOfSharesValue;
    private Integer step3_totalNumberOfShares;
    private BigDecimal step3_shareValue;
    private AmazonS3FileDetails[]   step3_uploadedProofDocuments;
    private boolean step3_agreePrint;
    private boolean step3_agreeTV;
    private boolean step3_agreeAudioBook;
    private boolean step3_agreeMovie;
    private boolean step3_agreeUK;
    private boolean step3_agreeEnteredValues;
    private boolean step3_agreeCannotReturn;

    /*
     * Variables used in step 4
     */

    /*
     * Variables used in step 5
     */

    /*
     * Variables used in step 6
     */
    private String step6_errorMessage;

    public AddProjectWizardDTO(){
    }

    public AddProjectWizardDTO(Context context){
        setWizardDefaults(context);
    }

    public void sync(AddProjectWizardDTO source){
        username    = source.getUsername();
        currentStep = source.getCurrentStep();
        step1_agreeTermsAndConditions = source.isStep1_agreeTermsAndConditions();


        step2_storyTitle                 = source.getStep2_storyTitle();
        step2_storyFormat                = source.getStep2_storyFormat();
        step2_storyType                  = source.getStep2_storyType();
        step2_storyGenre                 = source.getStep2_storyGenre();
        step2_storySubGenre              = source.getStep2_storySubGenre();
        step2_storyCategory              = source.getStep2_storyCategory();
        step2_storyPitch                 = source.getStep2_storyPitch();
        step2_storySynopsis              = source.getStep2_storySynopsis();
        step2_storyChapters              = source.getStep2_storyChapters();
        step2_uploadedStory              = source.getStep2_uploadedStory();
        step2_uploadedSampleStory        = source.getStep2_uploadedSampleStory();
        step2_aproxChapterWordCount      = source.getStep2_aproxChapterWordCount();
        step2_delayBetweenChapterUpdates = source.getStep2_delayBetweenChapterUpdates();
        step2_tags                       = source.getStep2_tags();
        step2_backstories                = source.getStep2_backstories();

        step3_yearsOfSellingRightsToPlatform = source.getStep3_yearsOfSellingRightsToPlatform();
        step3_sellEstimateUnitsPerYear       = source.getStep3_sellEstimateUnitsPerYear();
        step3_ebookPrice                     = source.getStep3_ebookPrice();
        step3_uploadedProofDocuments         = source.getStep3_uploadedProofDocuments();
        step3_percentRoyaltiesToPlatform     = source.getStep3_percentRoyaltiesToPlatform();
        step3_numberOfSharesValue            = source.getStep3_numberOfSharesValue();
        step3_totalNumberOfShares            = source.getStep3_totalNumberOfShares();
        step3_shareValue                     = source.getStep3_shareValue();
        step3_agreePrint                     = source.isStep3_agreePrint();
        step3_agreeTV                        = source.isStep3_agreeTV();
        step3_agreeAudioBook                 = source.isStep3_agreeAudioBook();
        step3_agreeMovie                     = source.isStep3_agreeMovie();
        step3_agreeUK                        = source.isStep3_agreeUK();
        step3_agreeEnteredValues             = source.isStep3_agreeEnteredValues();
        step3_agreeCannotReturn              = source.isStep3_agreeCannotReturn();

        //do not want to push the same error message around
        step6_errorMessage                   = "";
    }

    /*
     * Set defaults values for wizard data
     */
    public void setWizardDefaults(Context context){
        ResourceBundle upreaderResources = context.getApplication().getLocaleManager().getResources("com.upreader.i18n.UpreaderResources", context.getLocale());

        populateLOVs(upreaderResources);

        this.currentStep = new Integer(1);
        this.step1_agreeTermsAndConditions = false;

        step2_storyFormat                = new SelectItem("", upreaderResources.getString("addProjectWizard.labelFormat"));
        step2_storyType                  = new SelectItem("", upreaderResources.getString("addProjectWizard.labelType"));
        step2_storyGenre                 = new SelectItem(story_SelectSubgenre_LOV, "", upreaderResources.getString("addProjectWizard.labelGenre"));
        step2_storySubGenre              = new SelectItem("", upreaderResources.getString("addProjectWizard.labelSubgenre"));
        step2_storyCategory              = new SelectItem("", upreaderResources.getString("addProjectWizard.labelCategory"));
        step2_storyTitle                 = upreaderResources.getString("addProjectWizard.storyTitle");
        step2_storyPitch                 = upreaderResources.getString("addProjectWizard.storyPitch");
        step2_storySynopsis              = upreaderResources.getString("addProjectWizard.storySynopsis");
        step2_storyChapters              = Integer.valueOf(1);
        step2_aproxChapterWordCount      = Integer.valueOf(1);
        step2_delayBetweenChapterUpdates = Integer.valueOf(1);
        step2_tags                       = new String[]{};
        step2_backstories                = new String[]{};

        step3_yearsOfSellingRightsToPlatform = Integer.valueOf(0);
        step3_percentRoyaltiesToPlatform     = Integer.valueOf(0);
        step3_sellEstimateUnitsPerYear       = Integer.valueOf(0);
        step3_ebookPrice          = BigDecimal.valueOf(0);
        step3_uploadedProofDocuments = new AmazonS3FileDetails[]{};
        step3_numberOfSharesValue = Integer.valueOf(0);
        step3_totalNumberOfShares = Integer.valueOf(0);
        step3_shareValue          = BigDecimal.valueOf(1);
        step3_agreePrint          = false;
        step3_agreeTV             = false;
        step3_agreeAudioBook      = false;
        step3_agreeMovie          = false;
        step3_agreeUK             = false;
        step3_agreeEnteredValues  = false;
        step3_agreeCannotReturn   = false;

        step6_errorMessage        = "";
    }

    public void populateLOVs(ResourceBundle upreaderResources){
        //TODO
        // more generic implementation to read the options from the resource bundle

        //Format LOV
        story_Format_LOV = new ArrayList<SelectItem>();
        story_Format_LOV.add( new SelectItem("", upreaderResources.getString("addProjectWizard.labelFormat")));
        story_Format_LOV.add( new SelectItem(UpreaderConstants.SERIAL_STORY, upreaderResources.getString("upreader.nomenclature.format.serialStory")) );
        story_Format_LOV.add( new SelectItem(UpreaderConstants.STORY, upreaderResources.getString("upreader.nomenclature.format.story")) );

       //Type LOV
        story_Type_LOV = new ArrayList<SelectItem>();
        story_Type_LOV.add( new SelectItem("", upreaderResources.getString("addProjectWizard.labelType")) );
        story_Type_LOV.add( new SelectItem(UpreaderConstants.TYPE_FICTION, upreaderResources.getString("upreader.nomenclature.type.fiction")) );
        story_Type_LOV.add( new SelectItem(UpreaderConstants.TYPE_NONFICTION, upreaderResources.getString("upreader.nomenclature.type.nonfiction")) );

        //Sub Genre LOV
        story_No_SubGenre_LOV = new ArrayList<SelectItem>();
        story_SelectSubgenre_LOV =  new ArrayList<SelectItem>();
        story_SelectSubgenre_LOV.add( new SelectItem("", upreaderResources.getString("addProjectWizard.labelSubgenre")));

        story_Drama_SubGenre_LOV = new ArrayList<SelectItem>();
        story_Drama_SubGenre_LOV.add( new SelectItem("", upreaderResources.getString("addProjectWizard.labelSubgenre")));
        story_Drama_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_DRAMA_COMINGOFAGE, upreaderResources.getString("upreader.nomenclature.subgenre.drama.comingofage")) );
        story_Drama_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_DRAMA_CHILDHOOD, upreaderResources.getString("upreader.nomenclature.subgenre.drama.childhood")) );
        story_Drama_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_DRAMA_URBAN, upreaderResources.getString("upreader.nomenclature.subgenre.drama.urban")) );
        story_Drama_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_DRAMA_HISTORICAL, upreaderResources.getString("upreader.nomenclature.subgenre.drama.historical")) );
        story_Comedy_SubGenre_LOV = new ArrayList<SelectItem>();
        story_Comedy_SubGenre_LOV.add( new SelectItem("", upreaderResources.getString("addProjectWizard.labelSubgenre")));
        story_Comedy_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_COMEDY_SATIRE, upreaderResources.getString("upreader.nomenclature.subgenre.comedy.satire")) );
        story_Comedy_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_COMEDY_PARODY, upreaderResources.getString("upreader.nomenclature.subgenre.comedy.parody")) );
        story_Comedy_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_COMEDY_DARK, upreaderResources.getString("upreader.nomenclature.subgenre.comedy.dark")) );
        story_Comedy_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_COMEDY_ROMANTIC, upreaderResources.getString("upreader.nomenclature.subgenre.comedy.romantic")) );
        story_Political_SubGenre_LOV = new ArrayList<SelectItem>();
        story_Political_SubGenre_LOV.add( new SelectItem("", upreaderResources.getString("addProjectWizard.labelSubgenre")));
        story_Political_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_POLITICAL_CONSPIRACY, upreaderResources.getString("upreader.nomenclature.subgenre.political.conspiracy")) );
        story_Political_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_POLITICAL_ANARCHIST, upreaderResources.getString("upreader.nomenclature.subgenre.political.anarchist")) );
        story_Political_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_POLITICAL_UTOPIA, upreaderResources.getString("upreader.nomenclature.subgenre.political.utopia")) );
        story_Political_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_POLITICAL_DYSTOPIA, upreaderResources.getString("upreader.nomenclature.subgenre.political.dystopia")) );
        story_Thriller_SubGenre_LOV = new ArrayList<SelectItem>();
        story_Thriller_SubGenre_LOV.add( new SelectItem("", upreaderResources.getString("addProjectWizard.labelSubgenre")));
        story_Thriller_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_THRILLER_CRIME, upreaderResources.getString("upreader.nomenclature.subgenre.thriller.crime")) );
        story_Thriller_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_THRILLER_ACTION, upreaderResources.getString("upreader.nomenclature.subgenre.thriller.action")) );
        story_Thriller_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_THRILLER_MYSTERY, upreaderResources.getString("upreader.nomenclature.subgenre.thriller.mystery")) );
        story_Thriller_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_THRILLER_PSYCHOLOGICAL, upreaderResources.getString("upreader.nomenclature.subgenre.thriller.psychological")) );
        story_SF_SubGenre_LOV = new ArrayList<SelectItem>();
        story_SF_SubGenre_LOV.add( new SelectItem("", upreaderResources.getString("addProjectWizard.labelSubgenre")));
        story_SF_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_SF_UTOPIA, upreaderResources.getString("upreader.nomenclature.subgenre.sf.utopia")) );
        story_SF_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_SF_DYSTOPIA, upreaderResources.getString("upreader.nomenclature.subgenre.sf.dystopia")) );
        story_SF_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_SF_CYBERPUNK, upreaderResources.getString("upreader.nomenclature.subgenre.sf.cyberpunk")) );
        story_SF_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_SF_STEAMPUNK, upreaderResources.getString("upreader.nomenclature.subgenre.sf.steampunk")) );
        story_SF_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_SF_APOCALYPTIC, upreaderResources.getString("upreader.nomenclature.subgenre.sf.apocalyptic")) );
        story_SF_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_SF_POSTAPOCALYPTIC, upreaderResources.getString("upreader.nomenclature.subgenre.sf.postapocalyptic")) );
        story_SF_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_SF_ANTHROPOLOGICAL, upreaderResources.getString("upreader.nomenclature.subgenre.sf.anthropological")) );
        story_SF_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_SF_COMICAL, upreaderResources.getString("upreader.nomenclature.subgenre.sf.comical")) );
        story_Fantasy_SubGenre_LOV = new ArrayList<SelectItem>();
        story_Fantasy_SubGenre_LOV.add( new SelectItem("", upreaderResources.getString("addProjectWizard.labelSubgenre")));
        story_Fantasy_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_FANTASY_DARK, upreaderResources.getString("upreader.nomenclature.subgenre.fantasy.dark")) );
        story_Fantasy_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_FANTASY_HISTORICAL, upreaderResources.getString("upreader.nomenclature.subgenre.fantasy.historical")) );
        story_Fantasy_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_FANTASY_MODERN, upreaderResources.getString("upreader.nomenclature.subgenre.fantasy.modern")) );
        story_Fantasy_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_FANTASY_SUPERHEROES, upreaderResources.getString("upreader.nomenclature.subgenre.fantasy.superheroes")) );
        story_Fantasy_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_FANTASY_VAMPIRES, upreaderResources.getString("upreader.nomenclature.subgenre.fantasy.vampires")) );
        story_Fantasy_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_FANTASY_ZOMBIES, upreaderResources.getString("upreader.nomenclature.subgenre.fantasy.zombies")) );
        story_Fantasy_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_FANTASY_MAGIC, upreaderResources.getString("upreader.nomenclature.subgenre.fantasy.magic")) );
        story_Erotica_SubGenre_LOV = new ArrayList<SelectItem>();
        story_Erotica_SubGenre_LOV.add( new SelectItem("", upreaderResources.getString("addProjectWizard.labelSubgenre")));
        story_Erotica_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_EROTICA_BDSM, upreaderResources.getString("upreader.nomenclature.subgenre.erotica.bdsm")) );
        story_AvantGarde_SubGenre_LOV = new ArrayList<SelectItem>();
        story_AvantGarde_SubGenre_LOV.add( new SelectItem("", upreaderResources.getString("addProjectWizard.labelSubgenre")));
        story_AvantGarde_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_AVANTGARDE_EXPERIMENTAL, upreaderResources.getString("upreader.nomenclature.subgenre.avantgarde.experimental")) );
        story_Other_SubGenre_LOV = new ArrayList<SelectItem>();
        story_Other_SubGenre_LOV.add( new SelectItem("", upreaderResources.getString("addProjectWizard.labelSubgenre")));
        story_Other_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_OTHER_BIZARRO, upreaderResources.getString("upreader.nomenclature.subgenre.other.bizarro")) );
        story_Other_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_OTHER_WEIRDO, upreaderResources.getString("upreader.nomenclature.subgenre.other.weirdo")) );
        story_Other_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_OTHER_ECLECTIC, upreaderResources.getString("upreader.nomenclature.subgenre.other.eclectic")) );
        story_Other_SubGenre_LOV.add( new SelectItem(UpreaderConstants.SUBGENRE_OTHER_PSYCHEDELIC, upreaderResources.getString("upreader.nomenclature.subgenre.other.psychedelic")) );

        //Genre LOV
        story_Genre_LOV = new ArrayList<SelectItem>();
        story_Genre_LOV.add( new SelectItem(story_SelectSubgenre_LOV, "", upreaderResources.getString("addProjectWizard.labelGenre")));
        story_Genre_LOV.add( new SelectItem(story_Drama_SubGenre_LOV, UpreaderConstants.GENRE_DRAMA, upreaderResources.getString("upreader.nomenclature.genre.drama")) );
        story_Genre_LOV.add( new SelectItem(story_Comedy_SubGenre_LOV, UpreaderConstants.GENRE_DRAMA, upreaderResources.getString("upreader.nomenclature.genre.comedy")) );
        story_Genre_LOV.add( new SelectItem(story_No_SubGenre_LOV, UpreaderConstants.GENRE_DRAMA, upreaderResources.getString("upreader.nomenclature.genre.romance")) );
        story_Genre_LOV.add( new SelectItem(story_No_SubGenre_LOV, UpreaderConstants.GENRE_DRAMA, upreaderResources.getString("upreader.nomenclature.genre.familySaga")) );
        story_Genre_LOV.add( new SelectItem(story_Political_SubGenre_LOV, UpreaderConstants.GENRE_DRAMA, upreaderResources.getString("upreader.nomenclature.genre.political")) );
        story_Genre_LOV.add( new SelectItem(story_No_SubGenre_LOV, UpreaderConstants.GENRE_HISTORICAL, upreaderResources.getString("upreader.nomenclature.genre.historical")) );
        story_Genre_LOV.add( new SelectItem(story_No_SubGenre_LOV, UpreaderConstants.GENRE_WAR, upreaderResources.getString("upreader.nomenclature.genre.war")) );
        story_Genre_LOV.add( new SelectItem(story_No_SubGenre_LOV, UpreaderConstants.GENRE_SPIRITUAL, upreaderResources.getString("upreader.nomenclature.genre.spiritual")) );
        story_Genre_LOV.add( new SelectItem(story_No_SubGenre_LOV, UpreaderConstants.GENRE_ETHNIC_IMIGRANT, upreaderResources.getString("upreader.nomenclature.genre.ethnicImigrant")) );
        story_Genre_LOV.add( new SelectItem(story_No_SubGenre_LOV, UpreaderConstants.GENRE_CHILDRENBOOK, upreaderResources.getString("upreader.nomenclature.genre.childrenBooks")) );
        story_Genre_LOV.add( new SelectItem(story_No_SubGenre_LOV, UpreaderConstants.GENRE_PARANORMAL, upreaderResources.getString("upreader.nomenclature.genre.paranormal")) );
        story_Genre_LOV.add( new SelectItem(story_No_SubGenre_LOV, UpreaderConstants.GENRE_ABSURD, upreaderResources.getString("upreader.nomenclature.genre.absurd")) );
        story_Genre_LOV.add( new SelectItem(story_No_SubGenre_LOV, UpreaderConstants.GENRE_SURREALISM, upreaderResources.getString("upreader.nomenclature.genre.surrealism")) );
        story_Genre_LOV.add( new SelectItem(story_No_SubGenre_LOV, UpreaderConstants.GENRE_HORROR, upreaderResources.getString("upreader.nomenclature.genre.horror")) );
        story_Genre_LOV.add( new SelectItem(story_Thriller_SubGenre_LOV, UpreaderConstants.GENRE_THRILLER, upreaderResources.getString("upreader.nomenclature.genre.thriller")) );
        story_Genre_LOV.add( new SelectItem(story_No_SubGenre_LOV, UpreaderConstants.GENRE_ADVENTURE, upreaderResources.getString("upreader.nomenclature.genre.adventure")) );
        story_Genre_LOV.add( new SelectItem(story_SF_SubGenre_LOV, UpreaderConstants.GENRE_SF, upreaderResources.getString("upreader.nomenclature.genre.sf")) );
        story_Genre_LOV.add( new SelectItem(story_Fantasy_SubGenre_LOV, UpreaderConstants.GENRE_FANTASY, upreaderResources.getString("upreader.nomenclature.genre.fantasy")) );
        story_Genre_LOV.add( new SelectItem(story_Erotica_SubGenre_LOV, UpreaderConstants.GENRE_EROTICA, upreaderResources.getString("upreader.nomenclature.genre.erotica")) );
        story_Genre_LOV.add( new SelectItem(story_No_SubGenre_LOV, UpreaderConstants.GENRE_LGBT, upreaderResources.getString("upreader.nomenclature.genre.lgbt")) );
        story_Genre_LOV.add( new SelectItem(story_AvantGarde_SubGenre_LOV, UpreaderConstants.GENRE_AVANTGARDE, upreaderResources.getString("upreader.nomenclature.genre.avantgarde")) );
        story_Genre_LOV.add( new SelectItem(story_Other_SubGenre_LOV, UpreaderConstants.GENRE_OTHER, upreaderResources.getString("upreader.nomenclature.genre.other")) );

        //Category LOV
        story_Category_LOV = new ArrayList<SelectItem>();
        story_Category_LOV.add( new SelectItem("", upreaderResources.getString("addProjectWizard.labelCategory")));
        story_Category_LOV.add( new SelectItem(UpreaderConstants.CATEGORY_NOVEL, upreaderResources.getString("upreader.nomenclature.category.novel")) );
        story_Category_LOV.add( new SelectItem(UpreaderConstants.CATEGORY_FICTION, upreaderResources.getString("upreader.nomenclature.category.fiction")) );
        story_Category_LOV.add( new SelectItem(UpreaderConstants.CATEGORY_POETRY, upreaderResources.getString("upreader.nomenclature.category.poetry")) );
        story_Category_LOV.add( new SelectItem(UpreaderConstants.CATEGORY_CHILDREN_BOOKS, upreaderResources.getString("upreader.nomenclature.category.childrenBooks")) );
        story_Category_LOV.add( new SelectItem(UpreaderConstants.CATEGORY_SELFHELP, upreaderResources.getString("upreader.nomenclature.category.selfhelp")) );
        story_Category_LOV.add( new SelectItem(UpreaderConstants.CATEGORY_BESTSELLER, upreaderResources.getString("upreader.nomenclature.category.bestseller")) );
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(Integer currentStep) {
        this.currentStep = currentStep;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<SelectItem> getStory_Format_LOV() {
        return story_Format_LOV;
    }

    public void setStory_Format_LOV(ArrayList<SelectItem> story_Format_LOV) {
        this.story_Format_LOV = story_Format_LOV;
    }

    public ArrayList<SelectItem> getStory_Category_LOV() {
        return story_Category_LOV;
    }

    public void setStory_Category_LOV(ArrayList<SelectItem> story_Category_LOV) {
        this.story_Category_LOV = story_Category_LOV;
    }

    public ArrayList<SelectItem> getStory_Genre_LOV() {
        return story_Genre_LOV;
    }

    public void setStory_Genre_LOV(ArrayList<SelectItem> story_Genre_LOV) {
        this.story_Genre_LOV = story_Genre_LOV;
    }

    public ArrayList<SelectItem> getStory_Type_LOV() {
        return story_Type_LOV;
    }

    public void setStory_Type_LOV(ArrayList<SelectItem> story_Type_LOV) {
        this.story_Type_LOV = story_Type_LOV;
    }

    public boolean isStep1_agreeTermsAndConditions() {
        return step1_agreeTermsAndConditions;
    }

    public void setStep1_agreeTermsAndConditions(boolean step1_agreeTermsAndConditions) {
        this.step1_agreeTermsAndConditions = step1_agreeTermsAndConditions;
    }

    public String getStep2_storyTitle() {
        return step2_storyTitle;
    }

    public void setStep2_storyTitle(String step2_storyTitle) {
        this.step2_storyTitle = step2_storyTitle;
    }

    public SelectItem getStep2_storyFormat() {
        return step2_storyFormat;
    }

    public void setStep2_storyFormat(SelectItem step2_storyFormat) {
        this.step2_storyFormat = step2_storyFormat;
    }

    public SelectItem getStep2_storyType() {
        return step2_storyType;
    }

    public void setStep2_storyType(SelectItem step2_storyType) {
        this.step2_storyType = step2_storyType;
    }

    public SelectItem getStep2_storyGenre() {
        return step2_storyGenre;
    }

    public void setStep2_storyGenre(SelectItem step2_storyGenre) {
        this.step2_storyGenre = step2_storyGenre;
    }

    public SelectItem getStep2_storySubGenre() {
        return step2_storySubGenre;
    }

    public void setStep2_storySubGenre(SelectItem step2_storySubGenre) {
        this.step2_storySubGenre = step2_storySubGenre;
    }

    public SelectItem getStep2_storyCategory() {
        return step2_storyCategory;
    }

    public void setStep2_storyCategory(SelectItem step2_storyCategory) {
        this.step2_storyCategory = step2_storyCategory;
    }

    public String getStep2_storyPitch() {
        return step2_storyPitch;
    }

    public void setStep2_storyPitch(String step2_storyPitch) {
        this.step2_storyPitch = step2_storyPitch;
    }

    public String getStep2_storySynopsis() {
        return step2_storySynopsis;
    }

    public void setStep2_storySynopsis(String step2_storySynopsys) {
        this.step2_storySynopsis = step2_storySynopsys;
    }

    public Integer getStep2_storyChapters() {
        return step2_storyChapters;
    }

    public void setStep2_storyChapters(Integer step2_storyChapters) {
        this.step2_storyChapters = step2_storyChapters;
    }

    public Integer getStep2_aproxChapterWordCount() {
        return step2_aproxChapterWordCount;
    }

    public void setStep2_aproxChapterWordCount(Integer step2_aproxChapterWordCount) {
        this.step2_aproxChapterWordCount = step2_aproxChapterWordCount;
    }

    public Integer getStep2_delayBetweenChapterUpdates() {
        return step2_delayBetweenChapterUpdates;
    }

    public void setStep2_delayBetweenChapterUpdates(Integer step2_delayBetweenChapterUpdates) {
        this.step2_delayBetweenChapterUpdates = step2_delayBetweenChapterUpdates;
    }

    public String[] getStep2_tags() {
        return step2_tags;
    }

    public void setStep2_tags(String[] step2_tags) {
        this.step2_tags = step2_tags;
    }

    public String[] getStep2_backstories() {
        return step2_backstories;
    }

    public void setStep2_backstories(String[] step2_backstories) {
        this.step2_backstories = step2_backstories;
    }

    public AmazonS3FileDetails getStep2_uploadedStory() {
        return step2_uploadedStory;
    }

    public void setStep2_uploadedStory(AmazonS3FileDetails step2_uploadedStory) {
        this.step2_uploadedStory = step2_uploadedStory;
    }

    public AmazonS3FileDetails getStep2_uploadedSampleStory() {
        return step2_uploadedSampleStory;
    }

    public void setStep2_uploadedSampleStory(AmazonS3FileDetails step2_uploadedSampleStory) {
        this.step2_uploadedSampleStory = step2_uploadedSampleStory;
    }

    public Integer getStep3_yearsOfSellingRightsToPlatform() {
        return step3_yearsOfSellingRightsToPlatform;
    }

    public Integer getStep3_sellEstimateUnitsPerYear() {
        return step3_sellEstimateUnitsPerYear;
    }

    public void setStep3_sellEstimateUnitsPerYear(Integer step3_sellEstimateUnitsPerYear) {
        this.step3_sellEstimateUnitsPerYear = step3_sellEstimateUnitsPerYear;
    }

    public void setStep3_yearsOfSellingRightsToPlatform(Integer step3_yearsOfSellingRightsToPlatform) {
        this.step3_yearsOfSellingRightsToPlatform = step3_yearsOfSellingRightsToPlatform;
    }

    public BigDecimal getStep3_ebookPrice() {
        return step3_ebookPrice;
    }

    public void setStep3_ebookPrice(BigDecimal step3_ebookPrice) {
        this.step3_ebookPrice = step3_ebookPrice;
    }

    public Integer getStep3_percentRoyaltiesToPlatform() {
        return step3_percentRoyaltiesToPlatform;
    }

    public void setStep3_percentRoyaltiesToPlatform(Integer step3_percentRoyaltiesToPlatform) {
        this.step3_percentRoyaltiesToPlatform = step3_percentRoyaltiesToPlatform;
    }

    public Integer getStep3_numberOfSharesValue() {
        return step3_numberOfSharesValue;
    }

    public void setStep3_numberOfSharesValue(Integer step3_numberOfSharesValue) {
        this.step3_numberOfSharesValue = step3_numberOfSharesValue;
    }

    public Integer getStep3_totalNumberOfShares() {
        return step3_totalNumberOfShares;
    }

    public void setStep3_totalNumberOfShares(Integer step3_totalNumberOfShares) {
        this.step3_totalNumberOfShares = step3_totalNumberOfShares;
    }

    public BigDecimal getStep3_shareValue() {
        return step3_shareValue;
    }

    public void setStep3_shareValue(BigDecimal step3_shareValue) {
        this.step3_shareValue = step3_shareValue;
    }

    public boolean isStep3_agreePrint() {
        return step3_agreePrint;
    }

    public void setStep3_agreePrint(boolean step3_agreePrint) {
        this.step3_agreePrint = step3_agreePrint;
    }

    public boolean isStep3_agreeTV() {
        return step3_agreeTV;
    }

    public void setStep3_agreeTV(boolean step3_agreeTV) {
        this.step3_agreeTV = step3_agreeTV;
    }

    public boolean isStep3_agreeAudioBook() {
        return step3_agreeAudioBook;
    }

    public void setStep3_agreeAudioBook(boolean step3_agreeAudioBook) {
        this.step3_agreeAudioBook = step3_agreeAudioBook;
    }

    public boolean isStep3_agreeMovie() {
        return step3_agreeMovie;
    }

    public void setStep3_agreeMovie(boolean step3_agreeMovie) {
        this.step3_agreeMovie = step3_agreeMovie;
    }

    public boolean isStep3_agreeUK() {
        return step3_agreeUK;
    }

    public void setStep3_agreeUK(boolean step3_agreeUK) {
        this.step3_agreeUK = step3_agreeUK;
    }

    public boolean isStep3_agreeEnteredValues() {
        return step3_agreeEnteredValues;
    }

    public void setStep3_agreeEnteredValues(boolean step3_agreeEnteredValues) {
        this.step3_agreeEnteredValues = step3_agreeEnteredValues;
    }

    public boolean isStep3_agreeCannotReturn() {
        return step3_agreeCannotReturn;
    }

    public void setStep3_agreeCannotReturn(boolean step3_agreeCannotReturn) {
        this.step3_agreeCannotReturn = step3_agreeCannotReturn;
    }

    public AmazonS3FileDetails[] getStep3_uploadedProofDocuments() {
        return step3_uploadedProofDocuments;
    }

    public void setStep3_uploadedProofDocuments(AmazonS3FileDetails[] step3_uploadedProofDocuments) {
        this.step3_uploadedProofDocuments = step3_uploadedProofDocuments;
    }

    public String getStep6_errorMessage() {
        return step6_errorMessage;
    }

    public void setStep6_errorMessage(String step6_errorMessage) {
        this.step6_errorMessage = step6_errorMessage;
    }
}
