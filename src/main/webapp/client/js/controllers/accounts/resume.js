(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ResumeController', ResumeController);
 
    ResumeController.$inject = ['$scope', 'AuthentificationService', 'AccountService',
       'TranslatorService', 'FlashService'];
    
    function ResumeController($scope, AuthentificationService, AccountService,
        TranslatorService, FlashService) {
        AuthentificationService.connectedPage('/welcome');
        
        let vm = this;
        
        init();
        
        function init() {
            AccountService.getClientAccounts(
                function (response) {
                    if (response.success) {
                        $scope.accounts = response.message;
                        for (let account of $scope.accounts) {
                            if (account.type!=='SHARE') {
                                account.currency = TranslatorService.translateCurrency(account.currency);
                            }
                            account.type = TranslatorService.translateAccount(account.type);                      
                        }
                    } else {
                        FlashService.Error(response.message);
                    }

                }
            );
        }
    }
})();