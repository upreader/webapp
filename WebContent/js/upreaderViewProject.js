/*
 * Client side component for the view project page
 *
 */

//Initialize the angular application for the Upreader Projects page.
var upreaderViewPrjAppModule = angular.module('upreaderViewPrjApp', ['ui.bootstrap', 'ngynSelectKey']);
//Initialize the common features
upreaderViewPrjAppModule.run( function($rootScope){
    $rootScope.viewProjectCommons = viewProjectCommons.init();
});

upreaderViewPrjAppModule.filter('isEmpty', function() {
    return function(input) {
        if(input === null){return true;}
        if(input === undefined){return true;}
        if(input === ""){return true;}
        return false;
    }
});

upreaderViewPrjAppModule.doPostAsForm = function(http, url, params, retryLimit, callCallback){
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
                upreaderViewPrjAppModule.doPostAsForm(http, url, params, retryLimit - 1, callCallback );
            }
        });
}

//Initialize a controller for the view project page
upreaderViewPrjAppModule.controller('viewProjectController', ['$scope','$rootScope', '$http', '$location', function($scope,$rootScope, $http, $location){
    $scope.init = function(){
        $scope.readPilotFlag = true;
        $incrementProjectNoViewsPostData = $.param({do: 'incrementNoViews', projectId: $("#PROJECT_ID").val() });
        upreaderViewPrjAppModule.doPostAsForm( $http,
            $rootScope.viewProjectCommons.controllerUrl,
            $incrementProjectNoViewsPostData, 5,
            function(data) {
                //handle response if necessary
            });
    };

    $scope.downloadStory = function(){

    };

    $scope.readPreview = function(){
        $scope.readPilotFlag = !$scope.readPilotFlag;
        if($scope.docViewer === null || $scope.docViewer === undefined ){
            $scope.docViewer = new DocViewer({ id: "DocViewer", zoom: "auto", page: 1 });
        }
    };

    $scope.init();
}]);

var viewProjectCommons = {
    'config' : {
        //default configuration
        'controllerUrl': '/upreader/i/s/p'
    },
    'init' : function(config) {
        if (config && typeof(config) == 'object') {
            $.extend(viewProjectCommons.config, config);
        }

        viewProjectCommons.controllerUrl = viewProjectCommons.config.controllerUrl;
        viewProjectCommons.initialized = true;

        return this;
    }
};