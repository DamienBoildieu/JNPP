(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AuthentificationService', AuthentificationService);
 
    AuthentificationService.$inject = ['$cookies','$rootScope', 'CommonService'];
    function AuthentificationService($cookies, $rootScope, CommonService) {
        var service = {};
 
        service.login = login;
        service.setCredentials = setCredentials;
        service.ClearCredentials = ClearCredentials;
 
        return service;
 
        function login(data) {
            return CommonService.basicRequest('connectAngular.htm', data);
        }
 
        function setCredentials(username, password) {
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