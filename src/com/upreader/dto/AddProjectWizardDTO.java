package com.upreader.dto;

import com.upreader.context.Context;

import java.math.BigDecimal;
import java.util.ResourceBundle;

public class AddProjectWizardDTO {

    /*
     * Variables used in all steps
     */
    private String username;
    private Integer currentStep;

    /*
     * Variables used in step 1
     */
    private boolean step1_agreeTermsAndConditions;

    /*
     * Variables used in step 2
     */
    private String step2_storyTitle;
    private String step2_storyFormat;
    private String step2_storyType;
    private String step2_storyGenre;
    private String step2_storySubGenre;
    private String step2_storyCategory;
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
    private BigDecimal step3_maxShareValue;
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
        step3_maxShareValue                  = source.getStep3_maxShareValue();
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

        this.currentStep = new Integer(1);
        this.step1_agreeTermsAndConditions = false;

        step2_storyFormat                = "";
        step2_storyType                  = "";
        step2_storyGenre                 = "";
        step2_storySubGenre              = "";
        step2_storyCategory              = "";
        step2_storyTitle                 = upreaderResources.getString("addProjectWizard.storyTitle");
        step2_storyPitch                 = upreaderResources.getString("addProjectWizard.storyPitch");
        step2_storySynopsis              = upreaderResources.getString("addProjectWizard.storySynopsis");
        step2_storyChapters              = Integer.valueOf(1);
        step2_aproxChapterWordCount      = Integer.valueOf(1);
        step2_delayBetweenChapterUpdates = Integer.valueOf(1);
        step2_tags                       = new String[]{};
        step2_backstories                = new String[]{};

        step3_yearsOfSellingRightsToPlatform = Integer.valueOf(1);
        step3_percentRoyaltiesToPlatform     = Integer.valueOf(10);
        step3_sellEstimateUnitsPerYear       = Integer.valueOf(1);
        step3_ebookPrice          = BigDecimal.valueOf(0.01);
        step3_uploadedProofDocuments = new AmazonS3FileDetails[]{};
        step3_numberOfSharesValue = Integer.valueOf(1);
        step3_totalNumberOfShares = Integer.valueOf(100);
        step3_shareValue          = BigDecimal.valueOf(1.00);
        step3_maxShareValue       = BigDecimal.valueOf(5);
        step3_agreePrint          = false;
        step3_agreeTV             = false;
        step3_agreeAudioBook      = false;
        step3_agreeMovie          = false;
        step3_agreeUK             = false;
        step3_agreeEnteredValues  = false;
        step3_agreeCannotReturn   = false;

        step6_errorMessage        = "";
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

    public String getStep2_storyFormat() {
        return step2_storyFormat;
    }

    public void setStep2_storyFormat(String step2_storyFormat) {
        this.step2_storyFormat = step2_storyFormat;
    }

    public String getStep2_storyType() {
        return step2_storyType;
    }

    public void setStep2_storyType(String step2_storyType) {
        this.step2_storyType = step2_storyType;
    }

    public String getStep2_storyGenre() {
        return step2_storyGenre;
    }

    public void setStep2_storyGenre(String step2_storyGenre) {
        this.step2_storyGenre = step2_storyGenre;
    }

    public String getStep2_storySubGenre() {
        return step2_storySubGenre;
    }

    public void setStep2_storySubGenre(String step2_storySubGenre) {
        this.step2_storySubGenre = step2_storySubGenre;
    }

    public String getStep2_storyCategory() {
        return step2_storyCategory;
    }

    public void setStep2_storyCategory(String step2_storyCategory) {
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

    public BigDecimal getStep3_maxShareValue() {
        return step3_maxShareValue;
    }

    public void setStep3_maxShareValue(BigDecimal step3_maxShareValue) {
        this.step3_maxShareValue = step3_maxShareValue;
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
