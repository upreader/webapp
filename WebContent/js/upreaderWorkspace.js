/*
 * Client side component for the workspace page
 *
 */

//Initialize the angular application for the Upreader Projects page.
var upreaderWorkspaceAppModule = angular.module('upreaderWorkspaceApp', ['ui.bootstrap', 'ngynSelectKey']);
//Initialize the common features
upreaderWorkspaceAppModule.run( function($rootScope){
    $rootScope.workspaceCommons = workspaceCommons.init();
});

upreaderWorkspaceAppModule.filter('isEmpty', function() {
    return function(input) {
        if(input === null){return true;}
        if(input === undefined){return true;}
        if(input === ""){return true;}
        return false;
    }
});

upreaderWorkspaceAppModule.doPostAsForm = function(http, url, params, retryLimit, callCallback){
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
                upreaderWorkspaceAppModule.doPostAsForm(http, url, params, retryLimit - 1, callCallback );
            }
        });
}

//Initialize a controller for the view project page
upreaderWorkspaceAppModule.controller('upreaderWorkspaceController', ['$scope','$rootScope', '$http', '$location', function($scope,$rootScope, $http, $location){
    $scope.init = function(){
        //Get the userNotifications
        $workspaceNotificationsPostData = $.param({do: 'getNotificationsReceivedByUser', userId: $("#USER_ID").val() });
        upreaderWorkspaceAppModule.doPostAsForm( $http,
            $rootScope.workspaceCommons.controllerUrl,
            $workspaceNotificationsPostData, 5,
            function(data) {
                $scope.userNotifications = data;
            });

        $financialIncomePostData = $.param({do: 'getUserIncome', userId: $("#USER_ID").val() });
        upreaderWorkspaceAppModule.doPostAsForm( $http,
            $rootScope.workspaceCommons.controllerUrl,
            $financialIncomePostData, 5,
            function(data) {
                $scope.incomeFinancialData = data;
                console.log($scope.incomeFinancialData);
            });
    };

    $scope.init();
}]);


var workspaceCommons = {
    'config' : {
        //default configuration
        'container' : $('#page-workspace'),
        'controllerUrl': '/upreader/i/s/w'
    },
    'init' : function(config) {
        if (config && typeof(config) == 'object') {
            $.extend(workspaceCommons.config, config);
        }
        workspaceCommons.container     = workspaceCommons.config.container;
        workspaceCommons.controllerUrl = workspaceCommons.config.controllerUrl;
        workspaceCommons.initialized = true;

        return this;
    }
};
