(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('AccountController', AccountController);
 
    AccountController.$inject = ['$rootScope', '$scope', '$location', '$routeParams','AuthentificationService',
        'AccountService', 'UserService', 'TranslatorService', 'FlashService'];
    
    function AccountController($rootScope, $scope, $location, $routeParams, AuthentificationService,
        AccountService, UserService, TranslatorService, FlashService) {
        AuthentificationService.connectedPage('/welcome');
        
        let vm = this;
        
        init();
        
        function init() {
            AccountService.getAccount($routeParams.accountRib).then(
                function(response) {
                    $scope.response = response;
                    console.log($scope.response);
                },
                function(response) {
                    FlashService.Error(response);
                }
            );
        }
    }
})();