(function () {
    'use strict';

    angular
        .module('app')
        .factory('SavingbooksService', SavingbooksService);

    SavingbooksService.$inject = ['RequestsService'];
    function SavingbooksService(RequestsService) {

        /***********************************************************************
         * Construction du service. */
        
        const service = {};

        service.getSavingbooks = getSavingbooks;
        service.addSavingbook = addSavingbook;
        
        return service;

        /***********************************************************************
         * Methodes du services. */

        function getSavingbooks() {
            const url = RequestsService.url() + 'get-savingbooks.htm';
            return RequestsService.get(url);
        }
        
        function addSavingbook(savingbook) {
            const url = RequestsService.url() + 'add-savingbook.htm';
            return RequestsService.post(url, savingbook);
        }

    }

})();
 
