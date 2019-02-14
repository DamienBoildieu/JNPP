(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AuthentificationService', AuthentificationService);
 
    AuthentificationService.$inject = ['$location', '$cookies','$rootScope', '$http', 
        'CommonService', 'NotifyService'];
    function AuthentificationService($location, $cookies, $rootScope, $http, 
        CommonService, NotifyService) {
        let service = {};
 
        service.isLogged = false;
        service.login = login;
        service.logout = logout;
        service.reconnect = reconnect;
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
                        service.setCredentials(
                            {
                                user : response.data,
                                authorization : response.headers('Authorization')
                            });
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
            }
        }       
        
        function reconnect(authorization) {
        let decoded = atob(authorization);
            let splitted = decoded.split(':');
            let login = splitted[0].split(' ')[1];
            let password = splitted[1];
            service.login(
                { 
                    username : login,
                    password : password
                },
                function (message) {
                    if (message.success)
                         NotifyService.notify('logInOutEvent');
                     else
                         service.clearCredentials();
                }
            );    
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
                currentUser : credentials.user
            };
            $http.defaults.headers.common['Authorization'] = credentials.authorization;
            $cookies.putObject('authorization', credentials.authorization);
        }
        
        function clearCredentials() {
            delete $rootScope.globals;
            delete $http.defaults.headers.common['Authorization'];
            $cookies.remove('authorization');
        }
    }
 
})();