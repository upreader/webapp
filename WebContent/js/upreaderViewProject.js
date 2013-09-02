/*
 * Client side component for the view project page
 *
 */

//Initialize the angular application for the Upreader Projects page.
var upreaderViewPrjAppModule = angular.module('upreaderViewPrjApp', ['ui.bootstrap', 'ngynSelectKey']);

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
        $scope.isReadPilot = true;
    };

    $scope.init();
}]);