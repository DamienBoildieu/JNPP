(function () {
    'use strict';

    angular
        .module('app')
        .factory('AccountsService', AccountsService);

    AccountsService.$inject = ['$http', '$q'];
    function AccountsService($http, $q) {

        const service = {};

        service.getAll = getAll;

        return service;

        function getAll() {
            const url = 'http://localhost:8084/JNPP/banker/accounts.htm';
            const deferred = $q.defer();
            $http.get(url).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (error) {
                    deferred.reject("> Server error.");
                    console.log(error);
                }
            );
            return deferred.promise;
        }

    }

})();
 