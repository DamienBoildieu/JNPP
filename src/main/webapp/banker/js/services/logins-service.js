(function () {
    'use strict';

    angular
        .module('app')
        .factory('LoginsService', LoginsService);

    LoginsService.$inject = ['RequestsService'];
    function LoginsService(RequestsService) {

        /***********************************************************************
         * Construction du service. */

        const service = {};

        service.getLogins = getLogins;

        return service;

        /***********************************************************************
         * Methodes du services. */

        function getLogins() {
            const url = RequestsService.url() + 'get-logins.htm';
            return RequestsService.get(url);
        }

    }

})();
 