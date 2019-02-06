(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('CommonService', CommonService);
    
    CommonService.$inject = ['$http', '$q'];

    function CommonService($http, $q) {
        
        let service = {};
        
        service.basePath = 'http://localhost:8084/JNPP/';
        
        service.getGenders = getGenders;
        
        return service;
        
        function getGenders() {
            let url = service.basePath+'getGenders.htm';
            let deferred = $q.defer();
            $http.post(url).then(
                function (response) {
                    deferred.resolve(response.data);
                }
            );
            return deferred.promise;
        }
    }
 
})();
