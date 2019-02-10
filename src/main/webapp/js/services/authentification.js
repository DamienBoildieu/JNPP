(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AuthentificationService', AuthentificationService);
 
    AuthentificationService.$inject = ['$cookies','$rootScope', '$http', 'CommonService'];
    function AuthentificationService($cookies, $rootScope, $http, CommonService) {
        let service = {};
 
        service.login = login;
        service.setCredentials = setCredentials;
        service.clearCredentials = clearCredentials;
        service.logout = logout;
        
        return service;
 
        function login(data) {
            return CommonService.basicRequest('connectAngular.htm', data);
        }
 
        function logout(callback) {
            let url = CommonService.basePath+'disconnectAngular.htm';
            $http.post(url).then(
                function() {
                    let response = {
                        success : true
                    };
                    callback(response);
                }
                ,
                function () {
                    let response = {
                        success : false
                    };
                    callback(response);
                }
            );
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