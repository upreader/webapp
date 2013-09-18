/*
 * Client side component for the profile page
 *
 */

function trim(str) {
    return str.replace(/^\s+|\s+$/g,"");
}

//Initialize the angular application for the Upreader Profile page.
var upreaderProfileAppModule = angular.module('upreaderProfileApp', ['ui.bootstrap', 'ngynSelectKey']);
//Initialize the common features
upreaderProfileAppModule.run( function($rootScope){
    $rootScope.profileCommons = profileCommons.init();
});

upreaderProfileAppModule.filter('isEmpty', function() {
    return function(input) {
        if(input === null){return true;}
        if(input === undefined){return true;}
        if(input === ""){return true;}
        return false;
    }
});

upreaderProfileAppModule.doPostAsForm = function(http, url, params, retryLimit, callCallback){
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
                upreaderProfileAppModule.doPostAsForm(http, url, params, retryLimit - 1, callCallback );
            }
        });
}

//Initialize a controller for the view project page
upreaderProfileAppModule.controller('upreaderProfileController', ['$scope','$rootScope', '$http', '$location', function($scope,$rootScope, $http, $location){
    $scope.init = function(){
        $scope.isEditMode=false;
        $scope.userData = angular.fromJson($("#user-profile-data").val());
    };

    $scope.updateUserProfile = function(){
        $updateUserProfilePostData = $.param({do: 'updateUserProfile', userprofiledata: angular.toJson($scope.userData) });
        upreaderProfileAppModule.doPostAsForm( $http,
            $rootScope.profileCommons.controllerUrl,
            $updateUserProfilePostData, 5,
            function(success) {
                if(trim(success) === "true"){
                   $scope.isEditMode = false;
                }
            });
    };

    $scope.init();
}]);


var profileCommons = {
    'config' : {
        //default configuration
        'container' : $('#page-profile'),
        'controllerUrl': '/upreader/i/s/up'
    },
    'init' : function(config) {
        if (config && typeof(config) == 'object') {
            $.extend(profileCommons.config, config);
        }
        profileCommons.container     = profileCommons.config.container;
        profileCommons.controllerUrl = profileCommons.config.controllerUrl;
        profileCommons.initialized = true;

        return this;
    }
};
