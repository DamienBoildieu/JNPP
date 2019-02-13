(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AuthentificationService', AuthentificationService);
 
    AuthentificationService.$inject = ['$location', '$cookies','$rootScope', '$http', 'CommonService'];
    function AuthentificationService($location, $cookies, $rootScope, $http, CommonService) {
        let service = {};
 
        service.isLogged = false;
        service.login = login;
        service.logout = logout;
        service.connectedPage = connectedPage;
        service.unconnectedPage = unconnectedPage;
        
        return service;
 
        function login(data, callback) {
            if (!service.isLogged) {
                let url = CommonService.basePath+'connect.htm';
                $http.post(url, data).then(
                    function(response) {
                        let message = {
                            success : true
                        };
                        $rootScope.globals = {
                            currentUser : response.data
                        };
                        $cookies.putObject('globals', $rootScope.globals);
                        $http.defaults.headers.common['Authorization'] = $rootScope.globals.currentUser.login;
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
                $cookies.remove('globals');
            }
        }
        
        function connectedPage(redirect) {
            console.log(service.isLogged);
            if (!service.isLogged)
                $location.path(redirect);
        }
        
        function unconnectedPage(redirect) {
            if (service.isLogged)
                $location.path(redirect);
        }
    }
 
})();