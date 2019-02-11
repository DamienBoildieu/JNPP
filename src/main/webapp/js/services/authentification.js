(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AuthentificationService', AuthentificationService);
 
    AuthentificationService.$inject = ['$cookies','$rootScope', '$http', '$q', 'CommonService'];
    function AuthentificationService($cookies, $rootScope, $http, $q, CommonService) {
        let service = {};
 
        service.login = login;
        service.setCredentials = setCredentials;
        service.clearCredentials = clearCredentials;
        service.logout = logout;
        
        return service;
 
        function login(data) {
            return CommonService.basicRequest('connect.htm', data);
        }
 
        function logout() {
            let url = CommonService.basePath+'disconnect.htm';
            let deferred = $q.defer();
            $http.get(url).then(
                function() {
                    deferred.resolve();
                }
                ,
                function () {
                    deferred.reject();
                }
            );
            return deferred.promise;
        }
        
        function setCredentials(name) {
            $rootScope.globals = {
                userName : name
            };
            console.log('currentUser:' + $rootScope.globals.userName);
            $cookies.putObject('globals', $rootScope.globals);
        }
 
        function clearCredentials() {
            $cookies.remove('globals');
        }
    }
 
})();