(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('CommonService', CommonService);
    
    CommonService.$inject = ['$http', '$q'];

    function CommonService($http, $q) {
        
        let service = {};
        
        service.basePath = 'http://localhost:8084/JNPP/';      
        service.basicRequest = basicRequest;
        service.basicRequestCallback = basicRequestCallback;
        
        return service;
        
        function basicRequest(servletUrl, data) {
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
        };
        
        function basicRequestCallback(servletUrl, data, callback) {
            let url = service.basePath+servletUrl;
            $http.post(url, data).then(
                function (response) {
                    let message = {
                        success : true,
                        data : response.data
                    };
                    callback(message);
                },
                function (response) {
                    let message = {
                        success : false,
                        data : response.data
                    };
                    callback(message);
                }
            );            
        }
        
    }
 
})();
