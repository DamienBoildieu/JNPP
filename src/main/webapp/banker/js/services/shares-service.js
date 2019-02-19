(function () {
    'use strict';

    angular
        .module('app')
        .factory('SharesService', SharesService);

    SharesService.$inject = ['RequestsService'];
    function SharesService(RequestsService) {

        /***********************************************************************
         * Construction du service. */

        const service = {};

        service.getShares = getShares;
        service.addShare = addShare;
        
        return service;

        /***********************************************************************
         * Methodes du services. */

        function getShares() {
            const url = RequestsService.url() + 'get-shares.htm';
            return RequestsService.get(url);
        }
        
        function addShare(share) {
            const url = RequestsService.url() + 'add-share.htm';
            return RequestsService.post(url, share);
        }

    }

})();
 
