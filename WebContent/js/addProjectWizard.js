/*
 * Client side component for the add project wizard
 *
 */

//Initialize the angular application for the Upreader Projects page.
var upreaderAddPrjAppModule = angular.module('upreaderAddPrjApp', ['ui.bootstrap', 'ui.tinymce']);

upreaderAddPrjAppModule.filter('isEmpty', function() {
    return function(input) {
        if(input === null){return true;}
        if(input === undefined){return true;}
        if(input === ""){return true;}
        return false;
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
        $addProjectWizardPostData = $.param({do: targetAction, jsonWizData: angular.toJson($scope.wizardData) });
        upreaderAddPrjAppModule.doPostAsForm( $http,
                                              $rootScope.addProjectWizardCommons.projectsUrl,
                                              $addProjectWizardPostData, 5,
            function(data) {
                $scope.wizardData = data;
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
        $scope.uploadFileType = "3";
        $('.uploader').show(500);
    };

    $scope.uploadStory = function(){
        $scope.uploadFileType = "2";
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