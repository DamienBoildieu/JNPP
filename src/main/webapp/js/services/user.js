(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UserService', UserService);
 
    UserService.$inject = ['$http', '$q', 'CommonService'];
    
    function UserService($http, $q, CommonService) {
        
        let service = {};
        
        service.getGenders = getGenders;
        service.privateSignUp = privateSignUp;
        service.proSignUp = proSignUp;
        
        return service;      
        
        function getGenders() {
            let url = CommonService.basePath+'getGenders.htm';
            let deferred = $q.defer();
            $http.get(url).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function () {
                    deferred.reject("Erreur rencontre dans le serveur");
                }
            );
            return deferred.promise;
        };
        
        function privateSignUp(data) {
            return CommonService.basicRequest('privateSignUpAngular.htm', data);
        };
        
        function proSignUp(data) {
            return CommonService.basicRequest('proSignUpAngular.htm', data);
        };
    }
 
})();
 