(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AuthentificationService', AuthentificationService);
 
    AuthentificationService.$inject = ['$cookies','$rootScope', 'CommonService'];
    function AuthentificationService($cookies, $rootScope, CommonService) {
        let service = {};
 
        service.login = login;
        service.setCredentials = setCredentials;
        service.ClearCredentials = ClearCredentials;
 
        return service;
 
        function login(data) {
            return CommonService.basicRequest('connectAngular.htm', data);
        }
 
        function setCredentials(name) {
            $rootScope.globals = {
                userName : name
            };
            console.log('currentUser:' + $rootScope.globals.userName);
            $cookies.putObject('globals', $rootScope.globals);
        }
 
        function ClearCredentials() {
            $cookies.remove('globals');
        }
    }
 
})();