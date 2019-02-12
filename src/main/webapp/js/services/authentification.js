(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AuthentificationService', AuthentificationService);
 
    AuthentificationService.$inject = ['$location', '$cookies','$rootScope', '$http', 
        '$q', 'CommonService'];
    function AuthentificationService($location, $cookies, $rootScope, $http, $q, CommonService) {
        let service = {};
 
        service.isLogged = false;
        service.login = login;
        service.setCredentials = setCredentials;
        service.clearCredentials = clearCredentials;
        service.logout = logout;
        service.connectedPage = connectedPage;
        service.unconnectedPage = unconnectedPage;
        
        return service;
 
        function login(data, callback) {
            CommonService.basicRequest('connect.htm', data).then(
                function(response) {
                    let message = {
                        success : true,
                        message : response
                    };
                    service.isLogged = true;
                    callback(message);
                },
                function(response) {
                    let message = {
                        success : false,
                        message : response
                    };
                    callback(message);
                }
            );
        }
 
        function logout() {
            let url = CommonService.basePath+'disconnect.htm';
            let deferred = $q.defer();
            $http.get(url).then(
                function() {
                    service.isLogged = false;
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
            $cookies.putObject('globals', $rootScope.globals);
        }
 
        function clearCredentials() {
            $cookies.remove('globals');
        }
        
        function connectedPage(redirect) {
            if (!service.isConnected)
                $location.path(redirect);
        }
        
        function unconnectedPage(redirect) {
            if (service.isConnected)
                $location.path(redirect);
        }
    }
 
})();