(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UserService', UserService);
 
    UserService.$inject = ['CommonService', '$http', '$q'];
    
    function UserService(CommonService, $http, $q) {
        
        let service = {};
        
        service.getGenders = getGenders;
        
        return service;
 
        function getGenders() {
            let url = CommonService.basePath+'getgenders.htm';
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
 