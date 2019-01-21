(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AuthentificationService', AuthentificationService);
 
    AuthentificationService.$inject = ['$cookies', '$rootScope', '$http', '$q'];
    function AuthentificationService($cookies, $rootScope, $http, $q) {
        var service = {};
 
        service.Login = Login;
        service.SetCredentials = SetCredentials;
        service.ClearCredentials = ClearCredentials;
 
        return service;
 
        function Login(username, password) {
            let data = {
                'login' : username,
                'pass' : password
            };
            let url = 'http://localhost:8084/JNPP/connectAngular.htm';
            return $http.post(url, data);
        }
 
        function SetCredentials(username, password) {
            $rootScope.globals = {
                currentUser: {
                    username: username,
                    password: password
                }
            };
            console.log('currentUser: username:' + $rootScope.globals.currentUser.username + 'pass:' + $rootScope.globals.currentUser.password
                    );
            $cookies.putObject('globals', $rootScope.globals);
        }
 
        function ClearCredentials() {
            $cookies.remove('globals');
        }
    }
 
})();