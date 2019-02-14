(function () {
    'use strict';

    angular
        .module('app')
        .factory('AdvisorsService', AdvisorsService);

    AdvisorsService.$inject = ['$http', '$q'];
    function AdvisorsService($http, $q) {

        const service = {};

        service.getAll = getAll;
        service.add = add;
        
        return service;

        function getAll() {
            const url = 'http://localhost:8084/JNPP/banker/advisors.htm';
            const deferred = $q.defer();
            $http.get(url).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (response) {
                    deferred.reject("> Server error.");
                    console.log(response);
                }
            );
            return deferred.promise;
        }
        
        function add(advisor) {
            const url = 'http://localhost:8084/JNPP/banker/advisors.htm';
            const deferred = $q.defer();
            $http.post(url, advisor).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (response) {
                    deferred.reject(response.data);
                }
            );
            return deferred.promise;
        }

    }

})();
 
