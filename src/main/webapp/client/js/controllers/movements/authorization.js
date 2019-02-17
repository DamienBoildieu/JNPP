(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('AuthorizationController', AuthorizationController);
 
    AuthorizationController.$inject = ['$scope', 'AuthorizationService',
        'AuthentificationService', 'AccountService', 'FlashService'];
    
    function AuthorizationController($scope, AuthorizationService,
        AuthentificationService, AccountService, FlashService) {
        AuthentificationService.connectedPage('/welcome');
        
        let vm = this;
        
        $scope.authorizationData = {};
        $scope.moneyAccounts =  [];
        $scope.authorizations = [];
        
        vm.addAuthorization = addAuthorization;
        vm.deleteAuthorization = deleteAuthorization;
        
        init();
        
        function init() {
            AccountService.getClientAccounts(
                function (response) {
                    if (response.success) {
                        for (let account of response.message) {
                            if (account.type!=='SHARE')
                                $scope.moneyAccounts.push(account);
                        }
                        if ($scope.moneyAccounts[0]) {
                            $scope.authorizationData = {ribFrom : $scope.moneyAccounts[0].rib}; 
                        }

                        setTimeout(function () {
                            $('select').formSelect();
                        }, 200);
                    } else
                       FlashService.Error(response.message); 
                }
            );            
            AuthorizationService.getAuthorizations().then(
                function (response) {
                    $scope.authorizations = response;
                },
                function (response) {
                    FlashService.Error(response); 
                }     
            );
            $(document).ready(function(){
                $('.collapsible').collapsible();
            });
        }
        
        function addAuthorization() {
            AuthorizationService.addAuthorization($scope.authorizationData).then(
                function(response) {
                    FlashService.Success('Votre autorisation de débit a été acceptée');
                    $scope.authorizations.push(response);
                },
                function (response) {
                    FlashService.Error(response); 
                }
            );
        }
        
        function deleteAuthorization(index) {
            AuthorizationService.deleteAuthorization($scope.authorizations[index]).then(
                function() {
                    FlashService.Success('Votre autorisation de débit a été supprimée');
                    $scope.authorizations.splice(index, 1);
                },
                function (response) {
                    FlashService.Error(response); 
                }
            );
        }
    }
})();

