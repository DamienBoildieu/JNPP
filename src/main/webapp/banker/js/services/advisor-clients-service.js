(function () {
    'use strict';

    angular
        .module('app')
        .factory('AdvisorClientsService', AdvisorClientsService);

    AdvisorClientsService.$inject = ['$http', '$q'];
    function AdvisorClientsService($http, $q) {

        const service = {};

        service.getAll = getAll;
        
        return service;

        function getAll(firstname, lastname) {
            const url = 'http://localhost:8084/JNPP/banker/advisor/clients.htm';
            const deferred = $q.defer();
            $http.get(url, {params: {firstname: firstname, lastname: lastname}})
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function () {
                    deferred.reject("> Server error.");
                }
            );
            return deferred.promise;
        }

    }

})();
 
