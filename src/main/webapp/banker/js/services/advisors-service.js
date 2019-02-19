(function () {
    'use strict';

    angular
        .module('app')
        .factory('AdvisorsService', AdvisorsService);

    AdvisorsService.$inject = ['RequestsService'];
    function AdvisorsService(RequestsService) {

        /***********************************************************************
         * Construction du service. */

        const service = {};

        service.getAdvisors = getAdvisors;
        service.getAdvisorClients = getAdvisorClients;
        service.addAdvisor = addAdvisor;
        
        return service;

        /***********************************************************************
         * Methodes du services. */

        function getAdvisors() {
            const url = RequestsService.url() + 'get-advisors.htm';
            return RequestsService.get(url);
        }
        
        function getAdvisorClients(firstname, lastname) {
            const url = RequestsService.url() + 'get-advisor-clients.htm';
            return RequestsService.get(url, 
                    {firstname: firstname, lastname: lastname});
        }
        
        function addAdvisor(advisor) {
            const url = RequestsService.url() + 'add-advisor.htm';
            return RequestsService.post(url, advisor);
        }

    }

})();
 
