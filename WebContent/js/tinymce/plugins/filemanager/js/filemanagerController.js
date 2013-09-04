var tinyfileManagerAppModule = angular.module('tinyfileManagerApp', []);

tinyfileManagerAppModule.doPostAsForm = function(http, url, params, retryLimit, callCallback){
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

tinyfileManagerAppModule.controller('tinyfileManagerController', ['$scope','$rootScope', '$http', function($scope,$rootScope, $http){
    $scope.currentListing = angular.fromJson($("#s3FolderContents").val());
}]);