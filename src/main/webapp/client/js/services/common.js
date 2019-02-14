(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('CommonService', CommonService);
    
    CommonService.$inject = ['$http', '$q'];

    function CommonService($http, $q) {
        
        let service = {};
        
        service.basePath = 'http://localhost:8084/JNPP/';      
        service.basicPutRequest = basicPutRequest;
        service.basicPostRequest = basicPostRequest;
        service.basicDeleteRequest = basicDeleteRequest;
        
        return service;
        
        function basicPutRequest(servletUrl, data) {
            let url = service.basePath+servletUrl;
            let deferred = $q.defer();
            $http.put(url, data).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (response) {
                    deferred.reject(response.data);
                }
            );
            return deferred.promise;
        }
        
        function basicPostRequest(servletUrl, data) {
            let url = service.basePath+servletUrl;
            let deferred = $q.defer();
            $http.post(url, data).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (response) {
                    deferred.reject(response.data);
                }
            );
            return deferred.promise;
        }
        
        function basicDeleteRequest(servletUrl) {
            let url = service.basePath+servletUrl;
            let deferred = $q.defer();
            $http.delete(url).then(
                function () {
                    deferred.resolve();
                },
                function (response) {
                    deferred.reject(response.data);
                }
            );
            return deferred.promise;
        }
    }
 
})();
