/*
 * Client side component for the add project wizard
 *
 */

//Initialize the angular application for the Upreader Projects page.
var upreaderAddPrjAppModule = angular.module('upreaderAddPrjApp', []);

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
upreaderAddPrjAppModule.controller('addProjectWizardController', ['$scope','$rootScope', '$http', function($scope,$rootScope, $http){
    $scope.init = function(success, targetAction){
        if(targetAction === null || targetAction === undefined){targetAction = 'addingProject'}
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
     * Generic actions
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
            //need to reload the page to check the project status
            window.location.reload();
            window.scrollTo(0, top);
        }, 'postingProject');
    };

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