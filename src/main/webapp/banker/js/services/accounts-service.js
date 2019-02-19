(function () {
    'use strict';

    angular
        .module('app')
        .factory('AccountsService', AccountsService);

    AccountsService.$inject = ['RequestsService'];
    function AccountsService(RequestsService) {

        /***********************************************************************
         * Construction du service. */

        const service = {};

        service.getAccounts = getAccounts;

        return service;

        /***********************************************************************
         * Methodes du services. */

        function getAccounts() {            
            const url = RequestsService.url() + 'get-accounts.htm';
            return RequestsService.get(url);
        }

    }

})();
 