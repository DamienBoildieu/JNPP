(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('HomeController', HomeController);
 
    HomeController.$inject = ['AuthentificationService'];
    
    function HomeController(AuthentificationService) {
        AuthentificationService.connectedPage('/welcome');
    }
})();