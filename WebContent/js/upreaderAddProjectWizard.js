/*
 * Client side component for the add project wizard
 *
 */
//Initialize the angular application for the Upreader Projects page.
var upreaderAddPrjAppModule = angular.module('upreaderAddPrjApp', ['ui.utils','ui.bootstrap', 'ui.tinymce','ngynSelectKey']);

upreaderAddPrjAppModule.filter('isEmpty', function() {
    return function(input) {
        if(input === null){return true;}
        if(input === undefined){return true;}
        if(input === ""){return true;}
        if(input.length === 0){return true;}
        return false;
    }
});

upreaderAddPrjAppModule.filter('deadlineValue', function() {
    return function(input) {
        if(0 < input && input < 10000){return 30;}
        if(10000 <= input && input < 20000){return 45;}
        if(20000 <= input ){return 60;}
        return "";
    }
});

//Initialize the common features of the projects table on the root scope of the angular application
upreaderAddPrjAppModule.run( function($rootScope){
    $rootScope.addProjectWizardCommons = addProjectWizardCommons.init();
});

upreaderAddPrjAppModule.doPostAsForm = function(http, url, params, retryLimit, callCallback){
    if(retryLimit === 0 ){
        callCallback(false);
    }

    http.post(url, params,
        {
            headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
        }
    ).success(function(data) {
            callCallback(data);
        }).error(function(){
            if(retryLimit === 0){callCallback(false);}
            else{
                upreaderAddPrjAppModule.doPostAsForm(http, url, params, retryLimit - 1, callCallback );
            }
        });
}

//Initialize a controller for the add project wizard
upreaderAddPrjAppModule.controller('addProjectWizardController', ['$scope','$rootScope', '$http', '$location', function($scope,$rootScope, $http, $location){
    $scope.init = function(success, targetAction){
        if(targetAction === null || targetAction === undefined){targetAction = 'addingProject'}
        $scope.isAddingANewItem = true;
        $scope.isReadPilot = true;
        $scope.userRating = $("#USER_RATING").val();
        $scope.saleEstimatePerYear= $("#SALE_ESTIMATE_PER_YEAR").val();
        $scope.minBookPrice = $("#MIN_BOOK_PRICE").val();
        $scope.maxBookPrice = $("#MAX_BOOK_PRICE").val();
        $scope.minYearsSellingRightsToPlatform = $("#MIN_YEARS_SELLING_RIGHTS_TO_PLATFORM").val();
        $scope.minPercentRoyaltiesToPlatform = $("#MIN_PERCENT_ROYALTIES_TO_PLATFORM").val();
        $scope.minShares = $("#MIN_SHARES").val();
        $scope.maxShares = $("#MAX_SHARES").val();
        $scope.maxSharePrice = $("#SHARE_PRICE").val();
        $addProjectWizardPostData = $.param({do: targetAction, jsonWizData: angular.toJson($scope.wizardData) });
        upreaderAddPrjAppModule.doPostAsForm( $http,
                                              $rootScope.addProjectWizardCommons.projectsUrl,
                                              $addProjectWizardPostData, 5,
            function(data) {
                $scope.wizardData = data;
                /**
                 * Set default values according to the user rating
                 */
                if($scope.wizardData.step3_yearsOfSellingRightsToPlatform === 0){
                    $scope.wizardData.step3_yearsOfSellingRightsToPlatform = $scope.minYearsSellingRightsToPlatform;
                }
                if($scope.wizardData.step3_sellEstimateUnitsPerYear === 0){
                    $scope.wizardData.step3_sellEstimateUnitsPerYear = $scope.saleEstimatePerYear;
                }
                if($scope.wizardData.step3_ebookPrice === 0){
                    $scope.wizardData.step3_ebookPrice = $scope.minBookPrice;
                }
                if($scope.wizardData.step3_percentRoyaltiesToPlatform === 0){
                    $scope.wizardData.step3_percentRoyaltiesToPlatform = $scope.minPercentRoyaltiesToPlatform;
                }
                if($scope.wizardData.step3_numberOfSharesValue === 0){
                    $scope.wizardData.step3_numberOfSharesValue = $scope.minShares;
                }

                //Init croco preview on step 6
                if($scope.wizardData.currentStep === 6){
                    $scope.readPilotFlag=true;
                    $scope.crocoScript = "<script type='text/javascript' src='//crocodoc.com/webservice/document.js?session="+$scope.wizardData.step6_crocoSessionKeyForSampleOrPilot+"'></script>";
                }

                if(success !== null && success !== undefined){success(data)};
        });
    };

    /*
     * Step 2 Tag Management
     */
    $scope.addTag = function() {
        $scope.wizardData.step2_tags.push( $scope.newTag );
        $scope.newTag = "";
    };

    // This is the ng-click handler to remove an item
    $scope.removeTag = function ( idx ) {
        $scope.wizardData.step2_tags.splice( idx, 1 );
    };

    $scope.uploadStorySample = function(){
        $scope.uploadFileType = $("#STORY_SAMPLE").val();
        $('.uploader').show(500);
    };

    $scope.uploadStory = function(){
        $scope.uploadFileType = $("#STORY").val();
        $('.uploader').show(500);
    };

    $scope.closeUploader = function(){
        $('.uploader').hide(500);
    };

    $scope.submitUploadForm = function($event){
      $event.preventDefault();
      console.log("The form is now submitted");
    };

    /*
     * Step 3
     */
    $scope.uploadProofDocument =  function(){
        $scope.uploadFileType = "5";
        $('.uploader').show(500);
    };

    $scope.addProofDocument = function(fileItemDTO){
        $scope.wizardData.step3_uploadedProofDocuments.push( fileItemDTO );
        $scope.$apply();
        $('.uploader').hide(500);

    };

    /**
     * Step 6
     */
     $scope.readPreview = function(){
         $scope.readPilotFlag = !$scope.readPilotFlag;
         if($scope.docViewer === null || $scope.docViewer === undefined ){
             $scope.docViewer = new DocViewer({ id: "DocViewer", zoom: "auto", page: 1 });
         }
     };

    /*
     * Generic
     */
    $scope.moveToNextStep = function(step){
         $scope.wizardData.currentStep = step;
         $scope.init(function(data) {
            //need to reload the page to move to the next step.
            window.location.reload();
            window.scrollTo(0, top);
         }, 'addingProject');
    };

    $scope.confirmProjectPost = function(){
        $scope.init(function(data) {
            //need to reload the page to check the project a
            if($scope.wizardData.currentStep === 1){
                console.log("changing location");
                window.location = "workspace";
            }else{
                window.location.reload();
            }
        }, 'postingProject');
    };

    /*
     * Initialize the tinymce editor
     */
    $scope.tinymceOptions = {
        plugins: "fullscreen image filemanager",
        document_base_url: "http://www.upreader.com/upreader/"
    };

    /*
     * Backstory items
     */
    $scope.closePost = function(){
        $scope.postContent = "";
        $scope.isAddingANewItem = !$scope.isAddingANewItem;
    };

    $scope.addPost = function(postContent){
        $scope.wizardData.step2_backstories.push( postContent );
        $scope.postContent = "";
        $scope.isAddingANewItem = !$scope.isAddingANewItem;
    };
    /*
     * End backstory items post logic
     */

    //initialize the controller
    $scope.init();
}]);

var addProjectWizardCommons = {
    'config' : {
        //default configuration
        'container' : $('.page-addproject-wizard'),
        'projectsUrl': '/upreader/i/s/p'
    },
    'init' : function(config) {
        if (config && typeof(config) == 'object') {
            $.extend(addProjectWizardCommons.config, config);
        }

        addProjectWizardCommons.container   = addProjectWizardCommons.config.container;
        addProjectWizardCommons.projectsUrl = addProjectWizardCommons.config.projectsUrl;
        addProjectWizardCommons.initialized = true;

        return this;
    }
};