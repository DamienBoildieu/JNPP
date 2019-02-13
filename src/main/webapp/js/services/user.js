(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UserService', UserService);
 
    UserService.$inject = ['$http', '$q', 'CommonService', 'TranslatorService'];
    
    function UserService($http, $q, CommonService, TranslatorService) {
        
        let service = {};
        
        service.getGenders = getGenders;
        
        service.privateSignUp = privateSignUp;
        service.proSignUp = proSignUp;
        
        service.privatePassword = privatePassword;
        service.proPassword = proPassword;
        
        service.updateUserInfo = updateUserInfo;
        
        service.close = close;
        
        return service;      
        
        function getGenders() {
            let url = CommonService.basePath+'getGenders.htm';
            let deferred = $q.defer();
            $http.get(url).then(
                function (response) {
                    deferred.resolve(TranslatorService.translateGenders(response.data));
                },
                function () {
                    deferred.reject("Erreur rencontre dans le serveur");
                }
            );
            return deferred.promise;
        }
        
        function privateSignUp(data) {
            return CommonService.basicPostRequest('privateSignUp.htm', data);
        }
        
        function proSignUp(data) {
            return CommonService.basicPostRequest('proSignUp.htm', data);
        }
        
        function privatePassword(data) {
            return CommonService.basicPutRequest('privatePassword.htm', data);
        }
        
        function proPassword(data) {
            return CommonService.basicPutRequest('proPassword.htm', data);
        }
        
        function updateUserInfo(data) {
            return CommonService.basicPutRequest('updateUserInfo.htm', data);
        }
        
        function close(data) {
            let decoded = atob($http.defaults.headers.common['Authorization']);
            let splitted = decoded.split(":");
            if (data===splitted[1])
                return CommonService.basicDeleteRequest('deleteUser.htm', data);
            else
                return $q.defer().reject("Le mot de passe entré ne correspond pas "+
                    "à celui de la session").promise;
        }
    }
 
})();
 