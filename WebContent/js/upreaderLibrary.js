/*
 * Client side component for the library page
 *
 */

//Initialize the angular application for the Upreader Library page.
var upreaderLibraryAppModule = angular.module('upreaderLibraryApp', ['ui.bootstrap', 'ngynSelectKey']);
//Initialize the common features
upreaderLibraryAppModule.run( function($rootScope){
    $rootScope.libraryCommons = libraryCommons.init();
});

upreaderLibraryAppModule.filter('isEmpty', function() {
    return function(input) {
        if(input === null){return true;}
        if(input === undefined){return true;}
        if(input === ""){return true;}
        return false;
    }
});

upreaderLibraryAppModule.doPostAsForm = function(http, url, params, retryLimit, callCallback){
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
                upreaderLibraryAppModule.doPostAsForm(http, url, params, retryLimit - 1, callCallback );
            }
        });
}

upreaderLibraryAppModule.checkArrayForEmpty = function (my_arr){
    var count = 0;
    for(var i=0;i<my_arr.length;i++){
        if(my_arr[i] === "" || my_arr[i] === null || my_arr[i] === undefined)
            count++;
    }
    return count;
}

//Initialize a controller for the view project page
upreaderLibraryAppModule.controller('upreaderLibraryController', ['$scope','$rootScope', '$http', '$location', function($scope,$rootScope, $http, $location){
    $scope.init = function(){
         $scope.genres = angular.fromJson($("#genres-data").val());
         $scope.projectsByGenre = {};
         $scope.filteredProjectsByGenre = {};
         $scope.filters={};
         $scope.subGenreFilter={};
         for(var idx=0; idx < $scope.genres.length; idx++){
             $scope.subGenreFilter[$scope.genres[idx]] = "";
             $scope.projectsByGenre[$scope.genres[idx]] = angular.fromJson($("#projects-for-genre-"+$scope.genres[idx]).val());
         }
         $scope.filters['category']=[];
         $scope.filters['genre']=[];
         $scope.filters['subgenre']=[];
         $scope.filters['author']=[];
         $scope.filters['tag']=[];
    };

    $scope.showFilters = function(){
        console.log($scope.filters);
    }

    $scope.filterProjects = function(item) {
        var authorFilterCheck = false;
        var categoryFilterCheck = false;
        var genreFilterCheck = false;
        var subgenreFilterCheck = false;
        if( $scope.filters['author'].length == 0  ||
           ($scope.filters['author'].length > 0 && $scope.filters['author'].indexOf(item.authorid.toString()) != -1) ||
           ($scope.filters['author'].length === upreaderLibraryAppModule.checkArrayForEmpty($scope.filters['author']))
          ){
            authorFilterCheck = true;
        }

        if( $scope.filters['category'].length == 0  ||
            ($scope.filters['category'].length > 0 && $scope.filters['category'].indexOf(item.category.toString()) != -1) ||
            ($scope.filters['category'].length === upreaderLibraryAppModule.checkArrayForEmpty($scope.filters['category']))
            ){
            categoryFilterCheck = true;
        }

        if( $scope.filters['genre'].length == 0  ||
            ($scope.filters['genre'].length > 0 && $scope.filters['genre'].indexOf(item.genre.toString()) != -1) ||
            ($scope.filters['genre'].length === upreaderLibraryAppModule.checkArrayForEmpty($scope.filters['genre']))
            ){
            genreFilterCheck = true;
        }

        if( $scope.filters['subgenre'].length == 0  ||
            ($scope.filters['subgenre'].length > 0 && $scope.filters['subgenre'].indexOf(item.subgenre.toString()) != -1) ||
            ($scope.filters['subgenre'].length === upreaderLibraryAppModule.checkArrayForEmpty($scope.filters['subgenre']))
            ){
            subgenreFilterCheck = true;
        }

        if(authorFilterCheck === true && categoryFilterCheck===true && genreFilterCheck === true && subgenreFilterCheck === true){
            return true;
        }else{
            return false;
        }
    };

    $scope.init();
}]);


var libraryCommons = {
    'config' : {
        //default configuration
        'container' : $('#page-library'),
        'controllerUrl': '/upreader/i/s/l'
    },
    'init' : function(config) {
        if (config && typeof(config) == 'object') {
            $.extend(libraryCommons.config, config);
        }
        libraryCommons.container     = libraryCommons.config.container;
        libraryCommons.controllerUrl = libraryCommons.config.controllerUrl;
        libraryCommons.initialized = true;

        return this;
    }
};
