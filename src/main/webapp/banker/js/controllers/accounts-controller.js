(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('AccountsController', AccountsController);
 
    AccountsController.$inject = ['AccountsService'];
    function AccountsController(AccountsService) {
        
        var vm = this;
        
        vm.accounts = new Array();
        getAccounts();
        
        function getAccounts() {
            AccountsService.getAll().then(
                function(accounts) {
                    vm.accounts = accounts;
                }
            );            
        }
    
    }
 
})();