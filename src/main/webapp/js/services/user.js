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
            return $http.post(url);
        }
    }
 
})();
 