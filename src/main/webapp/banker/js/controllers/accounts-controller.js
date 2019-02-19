(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('AccountsController', AccountsController);
 
    AccountsController.$inject = ['AccountsService'];
    function AccountsController(AccountsService) {
        
        const vm = this;
        
        /***********************************************************************
         * Attributs du controller. */
        
        vm.accounts = new Array();
        
        /***********************************************************************
         * Constructeur du controller. */
        
        (function() {
            getAccounts();
        })();

        /***********************************************************************
         * Methodes privees du controller. */
        
        function getAccounts() {
            AccountsService.getAccounts().then(
                function(response) {
                    vm.accounts = response;
                }
            );            
        }
    
    }
 
})();