(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('LoginsController', LoginsController);
 
    LoginsController.$inject = ['$location'];
    
    function LoginsController($location) {}
 
})();