(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UserService', UserService);
 
    UserService.$inject = ['$rootScope', '$http', '$q', 'CommonService',
        'AuthentificationService'];
    
    function UserService($rootScope, $http, $q, CommonService, AuthentificationService) {
        
        let service = {};
        
        service.getGenders = getGenders;
        
        service.privateSignUp = privateSignUp;
        service.proSignUp = proSignUp;
        
        service.privatePassword = privatePassword;
        service.proPassword = proPassword;
        
        service.updateUserInfo = updateUserInfo;
        service.updatePassword = updatePassword;
        
        service.close = close;
        
        return service;      
        
        function getGenders() {
            return CommonService.basicGetRequest('genders.htm');
            /*let url = CommonService.basePath+'genders.htm';
            let deferred = $q.defer();
            $http.get(url).then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function () {
                    deferred.reject("Erreur rencontrée dans le serveur");
                }
            );
            return deferred.promise;*/
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
        
        function updatePassword(oldPassword, newPassword) {
            let decoded = atob($http.defaults.headers.common['Authorization']);
            let splitted = decoded.split(":");
            let url = CommonService.basePath+'updateUserPassword.htm';
            let deferred = $q.defer();
            if (oldPassword===splitted[1]) {               
                $http.put(url, 
                    {
                        newPassword : newPassword
                    }).then(
                    function (response) {
                        AuthentificationService.setCredentials(
                            {
                                user : $rootScope.globals.currentUser,
                                authorization : response.headers('Authorization')
                            });
                        deferred.resolve();
                    },
                    function (response) {
                        deferred.reject(response.data);
                    }
                );
                return deferred.promise;
            } else {
                deferred.reject("Le mot de passe entré ne correspond pas "+
                    "à celui de la session");
                return deferred.promise;
            }
        }
        
        function close(data) {
            let decoded = atob($http.defaults.headers.common['Authorization']);
            let splitted = decoded.split(":");
            if (data===splitted[1])
                return CommonService.basicDeleteRequest('deleteUser.htm');
            else
                return $q.defer().reject("Le mot de passe entré ne correspond pas "+
                    "à celui de la session").promise;
        }
    }
 
})();
 