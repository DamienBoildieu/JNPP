(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('CommonService', CommonService);
    
    CommonService.$inject = ['$http', '$q'];

    function CommonService($http, $q) {
        
        let service = {};
        
        service.basePath = 'http://localhost:8080/JNPP/';      
        service.basicRequest = basicRequest;
        
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
        
    }
 
})();
