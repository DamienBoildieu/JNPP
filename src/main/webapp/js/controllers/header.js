(function () {
    'use strict';
 
    angular
        .module('app-banker')
        .controller('HeaderController', HeaderController);
 
    HeaderController.$inject = ['$scope', '$location'];
    function HeaderController($scope, $location) {
        
        $scope.isActive = function (viewLocation) {
            console.log('viewLocation = ' + viewLocation);
            console.log('$location.path() = ' + $location.path());
            return viewLocation === $location.path();
        };
        
    }
 
})();