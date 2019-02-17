(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AuthorizationService', AuthorizationService);
 
    AuthorizationService.$inject = ['$http', '$q', 'CommonService'];
    
    function AuthorizationService($http, $q, CommonService) {
        
        let service = {};
        
        service.getAuthorizations = getAuthorizations;
        service.addAuthorization = addAuthorization;
        service.deleteAuthorization = deleteAuthorization;
        
        return service;
        
        
        function getAuthorizations() {
            return CommonService.basicGetRequest('clientAuthorizations.htm');
        }
        
        function addAuthorization(data) {
            return CommonService.basicPostRequest('addAuthorization.htm', data);
        } 
        
        function deleteAuthorization(data) {
            let url = CommonService.basePath+'deleteAuthorization.htm';
            let deferred = $q.defer();
            $http.delete(url, {
                data : data,
                headers: {
                    "Content-Type": "application/json;charset=utf-8"
                }
            }).then(
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