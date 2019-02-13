(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AuthentificationService', AuthentificationService);
 
    AuthentificationService.$inject = ['$location', '$cookies','$rootScope', '$http', 'CommonService'];
    function AuthentificationService($location, $cookies, $rootScope, $http, CommonService) {
        let service = {};
 
        if ($rootScope.currentUser)
            service.isLogged = true;
        else
            service.isLogged = false;
        service.login = login;
        service.logout = logout;
        service.connectedPage = connectedPage;
        service.unconnectedPage = unconnectedPage;
        service.setCredentials = setCredentials;
        service.clearCredentials = clearCredentials;
        
        return service;
 
        function login(data, callback) {
            if (!service.isLogged) {
                let url = CommonService.basePath+'connect.htm';
                $http.post(url, data).then(
                    function(response) {
                        let message = {
                            success : true
                        };
                        service.setCredentials(response.data);
                        $http.defaults.headers.common['Authorization'] = response.headers('Authorization');
                        service.isLogged = true;
                        callback(message);
                    },
                    function(response) {
                        let message = {
                            success : false,
                            message : response.data
                        };
                        callback(message);
                    }
                );               
            } else {
                let message = {
                    success : false,
                    message : 'Vous êtes déjà connecté'
                };
                callback(message);
            }
  
        }
 
        function logout() {
            if (service.isLogged) {
                service.isLogged = false;
                service.clearCredentials();
                delete $http.defaults.headers.common['Authorization'];
            }
        }
        
        function connectedPage(redirect) {
            if (!service.isLogged)
                $location.path(redirect);
        }
        
        function unconnectedPage(redirect) {
            if (service.isLogged)
                $location.path(redirect);
        }
        
        function setCredentials(credentials) {
            $rootScope.globals = {
                currentUser : credentials
            };
            $cookies.putObject('globals', $rootScope.globals);
        }
        
        function clearCredentials() {
            delete $rootScope.globals;
            $cookies.remove('globals');
        }
    }
 
})();