(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('HeaderController', HeaderController);
 
    HeaderController.$inject = ['$location'];
    function HeaderController($location) {
        
        var vm = this;
        
        vm.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
        
    }
 
})();