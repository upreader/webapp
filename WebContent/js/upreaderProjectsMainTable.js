/*
 * Main Widget for the projects main table
 *
 *     Using JS's Object Literal Pattern
 *     Used On:
 *            - projects workspace : projects.jsp
 */

//Initialize the angular application for the Upreader Projects page.
var upreaderPrjsAppModule = angular.module('upreaderPrjsApp', []);

//Initialize the common features of the projects table on the root scope of the angular application
upreaderPrjsAppModule.run( function($rootScope){
    $rootScope.projectsTableCommons = projectsTableCommons.init();
});

upreaderPrjsAppModule.doPostAsForm = function(http, url, params, retryLimit, callCallback){
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
                upreaderPrjsAppModule.doPostAsForm(http, url, params, retryLimit - 1, callCallback );
            }
        });
}

//Initialize a controller for the project table.
upreaderPrjsAppModule.controller('projectsTableController', ['$scope','$rootScope', '$http', function($scope,$rootScope, $http){
    $scope.predicate = 'title';

    //results array index is 0 based
    $formData = $.param({do: 'listPrjs', startPos: '0', endPos: $rootScope.projectsTableCommons.range});
    upreaderPrjsAppModule.doPostAsForm($http,$rootScope.projectsTableCommons.projectsUrl, $formData, 5, function(data) {
         $scope.projects = data;
    });
}]);

var projectsTableCommons = {
    'config' : {
        //default configuration
        'container' : $('#projectsTable'),
        'projectsUrl': '/upreader/i/s/p',
        'range': 10
     },
    'init' : function(config) {
        if (config && typeof(config) == 'object') {
            $.extend(projectsTableCommons.config, config);
        }

        projectsTableCommons.container  = projectsTableCommons.config.container;
        projectsTableCommons.projectsUrl = projectsTableCommons.config.projectsUrl;
        projectsTableCommons.range       = projectsTableCommons.config.range;
        projectsTableCommons.initialized = true;

        return this;
     },
    'showContentItem' : function() {},
    'selectItem' : function() {},
    'displayIRSStatusDetails' : function() {}
};