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
                    $scope.account = response.account;
                    $scope.movements = response.movements;
                },
                function(response) {
                    FlashService.Error(response);
                }
            );
        }
    }
})();