(function () {
    'use strict';

    angular
        .module('app')
        .factory('RequestsService', RequestsService);

    RequestsService.$inject = ['$http', '$q'];
    function RequestsService($http, $q) {

        const BASE_URL = 'http://localhost:8084/JNPP/';

        /***********************************************************************
         * Construction du service. */

        const service = {};

        service.url = url;
        service.get = get;
        service.post = post;
        
        return service;

        /***********************************************************************
         * Methodes du services. */
        
        /* Retourne une url de base. */
        function url() {
            return BASE_URL;
        }
        
        /* Effectue une requete get. */
        function get(url, params) {
            const deferred = $q.defer();
            $http.get(url, {params: params}).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function () {
                    deferred.reject();
                }
            );
            return deferred.promise;
        }
        
        /* Effectue une requete post. */
        function post(url, data) {
            const deferred = $q.defer();
            $http.post(url, data).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function () {
                    deferred.reject();
                }
            );
            return deferred.promise;
        }

    }

})();
