(function () {
    'use strict';

    angular
        .module('app')
        .factory('BooksService', BooksService);

    BooksService.$inject = ['$http', '$q'];
    function BooksService($http, $q) {

        const service = {};

        service.getAll = getAll;
        service.add = add;
        
        return service;

        function getAll() {
            const url = 'http://localhost:8084/JNPP/banker/books.htm';
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
        
        function add(share) {
            const url = 'http://localhost:8084/JNPP/banker/books.htm';
            const deferred = $q.defer();
            $http.post(url, share).then(
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
 
