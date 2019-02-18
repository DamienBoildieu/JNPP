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
        
        $scope.movementsUrl = 'client/html/accounts/accountmovements.html';
        
        init();
        
        function init() {
            AccountService.getAccount($routeParams.accountRib).then(
                function(response) {
                    $scope.account = response.account;
                    $scope.movements = response.movements;
                    TranslatorService.transformMovements($scope.movements);
                },
                function(response) {
                    FlashService.Error(response);
                }
            );
        }
    }
})();