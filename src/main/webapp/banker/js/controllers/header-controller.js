(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('HeaderController', HeaderController);
 
    HeaderController.$inject = ['$location'];
    function HeaderController($location) {
        
        const vm = this;
        
        vm.isActive = function (path) {
            return $location.path() === path;
        };
        
    }
 
})();