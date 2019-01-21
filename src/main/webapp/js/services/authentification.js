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
            //let deferred = $q.defer();
            let data = {
                'login' : username,
                'pass' : password
            };
            let url = 'http://localhost:8080/UsersAngularJS/connectAngular.htm';
            return $http.post(url, data);/*.then(
                function (response) {
                    deferred.resolve(response);
                },
                function (errResponse) {
                   deferred.reject(errResponse);
                }
            );
            return deferred.promise;*/
            /*var response;
            UserService.GetByName(username)
                .then(function (user) {
                    if (user === "damien") {
                        if (password === "damien") {
                            response = { success: true };
                        } else {
                            response = { success: false, message: 'Erreur login / Mot de passe' };
                        }
                    }
                    callback(response);
                },
                function(err) {
                    response = { success: false, message: err};
                    callback(response);
                });*/
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